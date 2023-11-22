package br.com.evonetwork.utils;

import java.io.IOException;

import com.sankhya.util.SessionFile;
import com.sankhya.util.UIDGenerator;

import br.com.sankhya.ws.ServiceContext;

public class GerarLinkParaBaixarArquivo {
	public void gerarLink() throws IOException {
		String chave = "text_" + UIDGenerator.getNextID();
		
		byte[] arquivo = null;
		
		SessionFile sessionFile = SessionFile.createSessionFile("arquivo.txt", "txt", arquivo);
		
		ServiceContext.getCurrent().putHttpSessionAttribute(chave, sessionFile);
		
		String link = "<a id=\"alink\" href=\"/mge/visualizadorArquivos.mge?chaveArquivo="+chave+"\" target=\"_blank\">Clique aqui para baixar o arquivo.";
		
		System.out.println("Link: "+link);
	}
}
