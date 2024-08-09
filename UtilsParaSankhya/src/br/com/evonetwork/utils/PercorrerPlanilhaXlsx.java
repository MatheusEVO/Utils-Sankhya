package br.com.evonetwork.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class PercorrerPlanilhaXlsx {
	private static void importacaoUnidadeConsumidora(File file, BigDecimal numos) throws Exception {
		HashMap<Integer, String> map = new HashMap<>();

		map.put(getPosicaoNoAlfabeto("A".toUpperCase()), "UNIDADECONSUMIDORA");
		map.put(getPosicaoNoAlfabeto("B".toUpperCase()), "DEMANDAKW");

		try {
			XSSFWorkbook workbook;
			try {
				//InputStream stream = new ByteArrayInputStream(bytes);
//				workbook = new XSSFWorkbook(getLerArquivo(stream));

				workbook = new XSSFWorkbook(new FileInputStream(file));
			} catch (Exception e) {
				System.out.println(file);
				e.printStackTrace();
				throw new Exception("Arquivo não localizado!");
			}

			// pega a primeira aba ou seleciona uma aba de acordo com o nome dela
			XSSFSheet sheet = workbook.getSheetAt(0);
//			XSSFSheet sheet = workbook.getSheet(sheetName);

//			if (sheet == null) {
//				throw new Exception("A aba " + sheetName + " não foi encontrada.");
//			}

			Iterator<Row> rowIterator = (Iterator<Row>) sheet.iterator();
			while (rowIterator.hasNext()) {
				Row linha = rowIterator.next();
				int ultimaCelula = linha.getLastCellNum();

				if (linha.getRowNum() >= 1) {
					EntityFacade dwfFacadeP = EntityFacadeFactory.getDWFFacade();
					
					//cria o VO do qual se quer fazer a importacao
					DynamicVO dynamicUnid = (DynamicVO) dwfFacadeP.getDefaultValueObjectInstance("AD_TCSOSEUNID");
					Boolean salvarUc = false;

					for (int i = 0; i < ultimaCelula; i++) {
						Cell cell = linha.getCell(i);
						boolean colunaVazia = cell == null || cell.getCellType() == 3
								|| (cell.getCellType() == 1 && cell.getStringCellValue().trim().isEmpty());

						if (!colunaVazia) {
							String conteudo = null;
							switch (cell.getCellType()) {
							case 0:
								conteudo = String
										.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()).toBigInteger());
								break;
							case 1:
								conteudo = cell.getStringCellValue();
								break;
							case 2:
								conteudo = cell.getStringCellValue();
								break;
							default:
								cell.setCellType(1);
								conteudo = cell.getStringCellValue();
								break;
							}
							String campo = map.get(cell.getColumnIndex() + 1);

							StringBuilder sb = new StringBuilder();
							sb.append("cell.getCellType(): " + cell.getCellType());
							sb.append(" / Linha: " + (linha.getRowNum() + 1));
							sb.append(" / Coluna: " + (cell.getColumnIndex() + 1));
							sb.append(" / Conteudo: " + conteudo);
							sb.append(" / Campo: " + campo);
							System.out.println(sb.toString());

							String tipoCampo = getTipoCampo("AD_TCSOSEUNID", campo);
							switch (tipoCampo) {
							case "BigDecimal":
								dynamicUnid.setProperty(campo, new BigDecimal(conteudo));
								salvarUc = true;
								break;
							case "String":
								dynamicUnid.setProperty(campo, conteudo);
								salvarUc = true;
								break;
							case "Timestamp":
								dynamicUnid.setProperty(campo, convertExcelSerialDateToTimestamp(conteudo));
								salvarUc = true;
								break;
							default:
								throw new Exception("tipo não encontrado...");
							}
						}
					}
					if (salvarUc) {
						dynamicUnid.setProperty("NUMOS", numos);
						PersistentLocalEntity createEntity = dwfFacadeP.createEntity("AD_TCSOSEUNID",
								(EntityVO) dynamicUnid);
						DynamicVO save = (DynamicVO) createEntity.getValueObject();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getTipoCampo(String tabela, String campo) throws Exception {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rset = null;
		SessionHandle hnd = null;

		String tipoCampo = null;

		try {
			hnd = JapeSession.open();
			hnd.setFindersMaxRows(-1);
			EntityFacade entity = EntityFacadeFactory.getDWFFacade();
			jdbc = entity.getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT  ");
			sb.append("     NOMECAMPO ");
			sb.append("     ,NUCAMPO ");
			sb.append("     ,TIPCAMPO ");
			sb.append("     ,DESCRCAMPO ");
			sb.append("     ,CASE  ");
			sb.append(" 		WHEN TIPCAMPO = 'D' THEN 'Timestamp'  ");
			sb.append(" 		WHEN TIPCAMPO = 'F' THEN 'BigDecimal'  ");
			sb.append(" 		WHEN TIPCAMPO = 'I' THEN 'BigDecimal'  ");
			sb.append(" 		WHEN TIPCAMPO = 'S' THEN 'String'  ");
			sb.append(" 		WHEN TIPCAMPO = 'T' THEN 'String'  ");
			sb.append(" 		END AS TIPCAMPOJAVA  ");
			sb.append(" 	,CASE  ");
			sb.append(" 	    WHEN TIPCAMPO = 'B' THEN 'Conteúdo Binário' ");
			sb.append(" 		WHEN TIPCAMPO = 'C' THEN 'Texto Longo (CLOB)' ");
			sb.append(" 		WHEN TIPCAMPO = 'D' THEN 'Data' ");
			sb.append(" 		WHEN TIPCAMPO = 'F' THEN 'Número Decimal' ");
			sb.append(" 		WHEN TIPCAMPO = 'H' THEN 'Data e Hora' ");
			sb.append(" 		WHEN TIPCAMPO = 'I' THEN 'Número Inteiro' ");
			sb.append(" 		WHEN TIPCAMPO = 'S' THEN 'Texto' ");
			sb.append(" 		WHEN TIPCAMPO = 'T' THEN 'Hora' ");
			sb.append("     	END AS TIPCAMPOSQL ");
			sb.append(" FROM TDDCAM  ");
			sb.append(" WHERE NOMETAB = '" + tabela + "' ");
			sb.append(" AND CALCULADO = 'N' ");
			sb.append(" AND NOMECAMPO = '" + campo + "' ");

			sql.appendSql(sb.toString());
			System.out.println("SQL: " + sql.toString());

			rset = sql.executeQuery();

			while (rset.next()) {
				tipoCampo = rset.getString("TIPCAMPOJAVA");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			JdbcUtils.closeResultSet(rset);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		return tipoCampo;
	}

	public static int getPosicaoNoAlfabeto(String letraString) {

		char letra = letraString.toUpperCase().charAt(0);

		// Verifica se a letra é maiúscula
		if (letra >= 'A' && letra <= 'Z') {
			return letra - 'A' + 1;
		}
		// Verifica se a letra é minúscula
		else if (letra >= 'a' && letra <= 'z') {
			return letra - 'a' + 1;
		} else {
			throw new IllegalArgumentException("O caractere fornecido não é uma letra válida.");
		}
	}

	public static Timestamp convertExcelSerialDateToTimestamp(String serialDateString) {

		Integer serialDate = Integer.valueOf(serialDateString);
		// A data base do Excel é 1900-01-01
		LocalDate baseDate = LocalDate.of(1900, 1, 1);

		// Ajuste para o bug do Excel (1º de janeiro de 1900 é o dia 1, mas o Excel
		// trata erroneamente 1900 como ano bissexto)
		LocalDate resultDate = baseDate.plusDays(serialDate - 2);

		// Converter LocalDate para LocalDateTime à meia-noite
		LocalDateTime resultDateTime = resultDate.atStartOfDay();

		// Converter LocalDateTime para Timestamp
		Timestamp timestamp = Timestamp.valueOf(resultDateTime);

		return timestamp;
	}
}
