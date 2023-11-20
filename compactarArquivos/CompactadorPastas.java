package br.com.evonetwork.compactarArquivos;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class CompactadorPastas {
	
	//Constantes
	static final int TAMANHO_BUFFER = 4096; // 4kb
	public static final int DEFAULT_BUFFER_SIZE = 8192;
	
	private static void baixarArquivos(BigDecimal numOs, ArrayList<byte[]> arquivos, ArrayList<String> nomeArquivos) throws MGEModelException, IOException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rset = null;
		SessionHandle hnd = null;
		
		try {
			hnd = JapeSession.open();
			hnd.setFindersMaxRows(-1);
			EntityFacade entity = EntityFacadeFactory.getDWFFacade();
			jdbc = entity.getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.appendSql("SELECT ARQUIVO, CONTEUDO FROM TSIATA WHERE CODATA = "+numOs+" AND CONTEUDO IS NOT NULL AND TIPO = 'O'");

			rset = sql.executeQuery();

			while (rset.next()) {
				arquivos.add(rset.getBytes("CONTEUDO"));
				nomeArquivos.add(numOs+"_"+rset.getString("ARQUIVO").trim());
			}
			
		} catch (Exception e) {
			MGEModelException.throwMe(e);
		} finally {
			JdbcUtils.closeResultSet(rset);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

   //m�todo para compactar arquivo
   public static byte[] compactarParaZip(ContextoAcao ca) throws IOException, MGEModelException {
	    int cont;
	    final byte[] dados = new byte[TAMANHO_BUFFER];
	    
	    BigDecimal numOs = null;
	    
	    ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
		ArrayList<String> nomeArquivos = new ArrayList<String>();
		ArrayList<BigDecimal> numOsArrayList = new ArrayList<BigDecimal>();
	    
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    final ZipOutputStream saida = new ZipOutputStream(bos);
	    
	    for (int i = 0; i < ca.getLinhas().length; i++) {
            Registro linha = ca.getLinhas()[i];
            numOs = (BigDecimal) linha.getCampo("NUMOS");
            
            // verificando se NUMOS já foi baixado
            int jaExiste = 0;
            for (BigDecimal numOsDoArray : numOsArrayList) {
				if(numOsDoArray.compareTo(numOs) == 0) {
					jaExiste = 1;
					break;
				}
			}
            
            if(jaExiste == 1) {
            	continue;
            }
            
            numOsArrayList.add(numOs);
            baixarArquivos(numOs, arquivos, nomeArquivos);
            
            verificarArquivosRepetidos(nomeArquivos);
            
            int j = 0;
            for (final byte[] arqEntrada : arquivos) {
    	        InputStream streamDeEntrada = new ByteArrayInputStream(arqEntrada);
    	        final BufferedInputStream origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
    	        final ZipEntry entry = new ZipEntry(numOs+"/"+nomeArquivos.get(j));
    	        saida.putNextEntry(entry);

    	        while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
    	            saida.write(dados, 0, cont);
    	        }
    	        origem.close();
    	        j++;
    	    }
            arquivos.clear();
            nomeArquivos.clear();
		}

	    saida.close();
	    
		return bos.toByteArray();
	}

	private static void verificarArquivosRepetidos(ArrayList<String> nomeArquivos) {
		for (int i=0; i<nomeArquivos.size(); i++) {
			String nome = nomeArquivos.get(i);
			for(int j=0; j<nomeArquivos.size(); j++) {
				if(nome.equals(nomeArquivos.get(j)) && i != j) {
					String partes[] = nome.split("\\.");
					String renomear = "";
					for(int k = 0; k < partes.length; k++) {
						if(k == partes.length-1) {
							renomear += "(1)."+partes[k];
						} else {
							renomear += partes[k]+"-";
						}
					}
					nomeArquivos.set(i, renomear);
					break;
				}
			}
		}
	}
}