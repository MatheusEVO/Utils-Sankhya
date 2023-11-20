package br.com.evonetwork.utils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

public class CriarRegistro {
	public void criarRegistro() throws Exception {
		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);//em casos de deadlock, esta sess o cai
			JapeWrapper empresaDAO = JapeFactory.dao("Empresa");
			@SuppressWarnings("unused")
			DynamicVO save = empresaDAO.create()
				.set("RAZAOABREV", "Sankhya")
				.set("NOMEFANTASIA", "Sankhya gestão")
				.set("RAZAOSOCIAL", "Sankhya Gestão de Negócios")
				.set("TELEFONE", "(34) 3239-0700")
				.save();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
	}
}
