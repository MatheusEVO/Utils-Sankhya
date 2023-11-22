package br.com.evonetwork.utils;

import java.math.BigDecimal;
import java.sql.CallableStatement;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class ChamarProcedure {
	
	public static void EVO_AD_INSTPROSP_CALC_ALSOL(BigDecimal numos, BigDecimal codpap) {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		try {
			hnd = JapeSession.open();
			EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();

			jdbc = dwfFacade.getJdbcWrapper();
			jdbc.openSession();

			String procedure = "{call sankhya.EVO_AD_INSTPROSP_CALC_ALSOL(?,?)}";
			CallableStatement cstmt = jdbc.getConnection().prepareCall(procedure);
			cstmt.setQueryTimeout(60);

			cstmt.setBigDecimal(1, numos);
			cstmt.setBigDecimal(2, codpap);

			cstmt.execute();

			// BigDecimal nunota = (BigDecimal) cstmt.getObject(6); //pega o retorno da procedure

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}
}
