package br.com.evonetwork.utils;

import com.sankhya.util.SessionFile;
import com.sankhya.util.UIDGenerator;

import br.com.evonetwork.compactararquivos.CompactadorPastas;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.ws.ServiceContext;

public class GerarLinkDownload implements AcaoRotinaJava{

	@Override
	public void doAction(ContextoAcao ca) throws Exception {
		System.out.println("***EVO - DOWNLOAD DE DOCUMENTOS DA VENDA (VALIDAÇÃO DE VENDA) - INICIO***");

		String chave = "text_" + UIDGenerator.getNextID();

		byte[] zip = CompactadorPastas.compactarParaZip(ca);

		SessionFile sessionFile = SessionFile.createSessionFile("documentos.zip", "zip", zip);

		ServiceContext.getCurrent().putHttpSessionAttribute(chave, sessionFile);

		ca.setMensagemRetorno("<a id=\"alink\" href=\"/mge/visualizadorArquivos.mge?chaveArquivo=" + chave
				+ "\" target=\"_blank\">Clique aqui para baixar o arquivo.");

		System.out.println("***EVO - DOWNLOAD DE DOCUMENTOS DA VENDA (VALIDAÇÃO DE VENDA) - FIM***");		
	}

}
