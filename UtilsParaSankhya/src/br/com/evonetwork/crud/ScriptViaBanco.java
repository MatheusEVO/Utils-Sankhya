package br.com.evonetwork.crud;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class ScriptViaBanco {

	public void scriptViaBanco(BigDecimal codusu) throws Exception {
		try {
			EntityFacade entity = EntityFacadeFactory.getDWFFacade();
			JdbcWrapper jdbc = entity.getJdbcWrapper();
			jdbc.openSession();
			
			/*
			 * A String scriptUpdate pode ser um comando DELETE, INSERT ou UPDATE.
			 * 
			 * OBS: Como é um comando SQL, se existirem "travas" feitas por java
			 * esse script irá as "ignorar"
			 * */
			String scriptUpdate = "DELETE FROM TDDPER WHERE CODUSU = " + codusu;
			
			PreparedStatement script = jdbc.getPreparedStatement(scriptUpdate);
			script.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	
}
