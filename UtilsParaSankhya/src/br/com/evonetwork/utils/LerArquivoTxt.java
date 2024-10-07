package br.com.evonetwork.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class LerArquivoTxt {
	
	public static String lerArquivoTxt(Class<?> classe, String caminho) throws IOException {
		//String caminhoQuery = "br/com/evonetwork/resource/query_BuscarFaturasAcaoAgendada.txt";
		// caminho do arquivo dentro do diretorio do projeto
		
		StringBuilder conteudo = new StringBuilder();

		if (Files.exists(Paths.get(caminho))) {
			return new String(Files.readAllBytes(Paths.get(caminho)), StandardCharsets.UTF_8);
		} else {
			try (InputStream inputStream = classe.getClassLoader().getResourceAsStream(caminho)) {
				if (inputStream == null) {
					throw new IOException("Arquivo não encontrado: " + caminho);
				}

				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
				String linha;
				while ((linha = reader.readLine()) != null) {
					conteudo.append(linha).append("\n");
				}
			}
		}
		return conteudo.toString();
	}
	
}
