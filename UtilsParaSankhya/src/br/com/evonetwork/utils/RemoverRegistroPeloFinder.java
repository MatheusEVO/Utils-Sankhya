package br.com.evonetwork.utils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;

public class RemoverRegistroPeloFinder {
	public void removerRegistros() throws Exception {
		SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			JapeWrapper configDao = JapeFactory.dao(DynamicEntityNames.CONFIGURACAO_RECURSO);
			configDao.deleteByCriteria("CHAVE = 1 AND CODUSU = 1");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally {
			JapeSession.close(hnd);
		}
	}
}
