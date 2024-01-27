package br.com.evonetwork.utils;

import java.util.Base64;

public class GerarLinkParaTela {
	public static String retornarLink(String campoPk, String valorPk, String idTela, String linkBase, String mensagem) {
		String minhaString = "Isso é uma string com aspas duplas: \"texto dentro das aspas\"";
		
		
		String jsonPk = "'{\""+campoPk+"\":"+valorPk+"}'";
		String encodedString =  Base64.getEncoder().encodeToString(idTela.getBytes());
		String pkEncoded = Base64.getEncoder().encodeToString(jsonPk.getBytes());
		
		// banco oracle
		String link =  "<a target=\"_top\" href=\""+linkBase+"mge/system.jsp#app/"+encodedString+"/"+pkEncoded+"\">Clique aqui</a>";

		
		//banco sql server
		//String link =  "'<a target=\"_top\" href=\"+linkBase+"'mge/system.jsp#app/'+"+encodedString+"||'/'+"+pkEncoded+"+'\">Clique aqui</a>'";

		return link;
	}
}
