package br.com.evonetwork.crud;

import java.sql.ResultSet;

import com.ibm.icu.math.BigDecimal;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class NativeSQL {

	public String fazerConsulta() throws Exception {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rset = null;
		SessionHandle hnd = null;
		String dado = "";
		try {
			hnd = JapeSession.open();
			hnd.setFindersMaxRows(-1);
			EntityFacade entity = EntityFacadeFactory.getDWFFacade();
			jdbc = entity.getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.appendSql("SELECT MAX(NROREMESSA) FROM AD_REMESSASERASA");
			System.out.println("SQL: " + sql.toString());

			rset = sql.executeQuery();

			while (rset.next()) {
				dado = rset.getString(1);
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
		return dado;
	}

	private void fazerContsultaSQLExterno() throws Exception {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rset = null;
		SessionHandle hnd = null;
	
		try {
			jdbc.openSession();
			NativeSql nativeSql = new NativeSql(jdbc);
			nativeSql.loadSql(getClass(), "sql/consulta.sql");
			nativeSql.setNamedParameter("PARAMETRO", new BigDecimal(1));
			ResultSet rs = nativeSql.executeQuery();
			while (rs.next()) {
				rs.getBigDecimal("");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			jdbc.closeSession();
		}
	}
}
