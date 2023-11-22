package br.com.evonetwork.utils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

public class UpdatePeloFinder {
	public void update() throws Exception {
		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			JapeWrapper servicoExecutadoDAO = JapeFactory.dao("TCSITE");
			DynamicVO servico = servicoExecutadoDAO.findOne("NUMITEM = 1 AND NUMOS = 1");
			servicoExecutadoDAO.prepareToUpdate(servico)
				.set("VALORSERVICO", 1)
				.update();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
	}
}
