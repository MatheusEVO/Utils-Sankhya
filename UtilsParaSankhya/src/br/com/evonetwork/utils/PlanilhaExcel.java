package br.com.evonetwork.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;


public class PlanilhaExcel {
	public static File criarPlanilha(ContextoAcao ca) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			Workbook workbook = new XSSFWorkbook();

			Sheet sheet1 = workbook.createSheet("Nome da aba");
			Row headerRow1 = sheet1.createRow(0);
			
			//nome dos campos da primeira linha
			ArrayList<String> campos = new ArrayList<>();
			campos.add("A");
			campos.add("B");
			campos.add("C");
			
			for (int i = 0; i < campos.size(); i++) {
				Cell headerCell1 = headerRow1.createCell(i);
				headerCell1.setCellValue(campos.get(i));
			}
			for (int i = 0; i < 3; i++) {
				sheet1.autoSizeColumn(i);
			}

			workbook.write(bos);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		File tempFile = File.createTempFile("planilha", ".xlsx");
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			bos.writeTo(fos);
		}
		return tempFile;
		
	}
}
