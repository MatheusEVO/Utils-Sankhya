package br.com.evonetwork.utils;

import java.sql.PreparedStatement;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class PrepareStatment {

	public void prepareStatment() throws Exception {
		try {
			EntityFacade entity = EntityFacadeFactory.getDWFFacade();
			JdbcWrapper jdbc = entity.getJdbcWrapper();
			jdbc.openSession();
			String scriptUpdate = "UPDATE TGFCAB SET PENDENTE = 'N' WHERE NUNOTA = 1";
			PreparedStatement pstmUpdate = jdbc.getPreparedStatement(scriptUpdate);
			pstmUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
