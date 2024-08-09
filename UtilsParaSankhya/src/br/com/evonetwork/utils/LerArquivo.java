package br.com.evonetwork.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class LerArquivo {
	public static InputStream getLerArquivo(InputStream inputStream) throws Exception {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StringBuffer buf = new StringBuffer();
			InputStream in = inputStream;
			byte[] b = new byte[2048];
			int length;
			boolean hasFileInfo = false;
			boolean writeDirectly = false;
			int offset = 0;
			while ((length = in.read(b)) > 0) {
				if (writeDirectly) {
					baos.write(b, 0, length);
				} else {
					offset = buf.length();
					buf.append(new String(b));
					if (!hasFileInfo && "__start_fileinformation__".equals(buf.substring(0, 25))) {
						hasFileInfo = true;
					}

					if (hasFileInfo) {
						int i = buf.indexOf("__end_fileinformation__");
						if (i > -1) {
							i += 23;// tamanho do "__end_fileinformation__"
							i -= offset; // O quanto ja havia sido lido antes
							baos.write(b, i, length - i);
							writeDirectly = true;
						}
					} else {
						baos.write(b, 0, length);
						writeDirectly = true;
					}
				}
			}
			baos.flush();
			in.close();
			inputStream = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return inputStream;
	}
}
