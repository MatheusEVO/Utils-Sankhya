package br.com.evonetwork.utils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class PegarVO {
	public void getVO() {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		
		try {
			hnd = JapeSession.open();
			
			EntityFacade dwEntityFacade = EntityFacadeFactory.getDWFFacade();
			jdbc = dwEntityFacade.getJdbcWrapper();
			
			JapeWrapper instanciaDAO = JapeFactory.dao(DynamicEntityNames.PARCEIRO);
			DynamicVO registroVO = instanciaDAO.findOne("CODPARC = 1");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
