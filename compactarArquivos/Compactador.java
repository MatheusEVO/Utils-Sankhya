package br.com.evonetwork.compactarArquivos;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compactador {
	
	//Constantes
	static final int TAMANHO_BUFFER = 4096; // 4kb
	public static final int DEFAULT_BUFFER_SIZE = 8192;

   //método para compactar arquivo
   public static byte[] compactarParaZip(ArrayList<byte[]> arqEntradas, ArrayList<String> nomeArquivos) throws IOException {
	    int cont;
	    final byte[] dados = new byte[TAMANHO_BUFFER];

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    final ZipOutputStream saida = new ZipOutputStream(bos);
	    int i=0;

	    for (final byte[] arqEntrada : arqEntradas) {
	        InputStream streamDeEntrada = new ByteArrayInputStream(arqEntrada);
	        final BufferedInputStream origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
	        final ZipEntry entry = new ZipEntry(nomeArquivos.get(i)); //nome do arquivo
	        saida.putNextEntry(entry);

	        while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
	            saida.write(dados, 0, cont);
	        }
	        origem.close();
	        i++;
	    }

	    saida.close();
	    
		return bos.toByteArray();
	}
}