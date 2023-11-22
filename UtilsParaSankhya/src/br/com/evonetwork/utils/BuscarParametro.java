package br.com.evonetwork.utils;

import br.com.sankhya.modelcore.util.MGECoreParameter;

public class BuscarParametro {
	@SuppressWarnings("unused")
	public void buscarParametro() throws Exception {
		String parametro = MGECoreParameter.getParameterAsString("LEXIOIDPROC");
	}
}
