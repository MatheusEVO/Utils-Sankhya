package br.com.evonetwork.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.sankhya.modelcore.util.MGECoreParameter;

public class RespostaAPI {

	public void conexaoAPI(BigDecimal empresa) throws Exception {
		String urlBase = MGECoreParameter.getParameterAsString("URLBASECOSD");
		int grupo = MGECoreParameter.getParameterAsInt("GRUPOCOSD");
		String strUrl = urlBase + "/grupo/" + grupo + "/empresa/" + empresa + "/CRMretorno/3/0";
		String auth = "BASIC " + MGECoreParameter.getParameterAsString("AUTHCOSD");
		System.out.println("URL: " + strUrl + "\n Auth: " + auth);
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setRequestProperty("Authorization", auth);
	}
	
	@SuppressWarnings("unused")
	private static String getResponseBody(HttpURLConnection conn) {
		BufferedReader br = null;
		StringBuilder body = null;
		String line = "";
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			body = new StringBuilder();
			while ((line = br.readLine()) != null)
				body.append(line);
			return body.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unused")
	private static String getErrorStream(HttpURLConnection conn) throws Exception {
		InputStream errorstream = conn.getErrorStream();
		String response = "";
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(errorstream, "UTF-8"));
		while ((line = br.readLine()) != null) {
			response += line;
		}
		return response;
	}
}
