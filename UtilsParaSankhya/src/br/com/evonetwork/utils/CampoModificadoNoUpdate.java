package br.com.evonetwork.utils;

import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;

public class CampoModificadoNoUpdate {
	public void funcao(PersistenceEvent event) {
		ModifingFields md = event.getModifingFields();
		if (md.isModifing("CAMPOMODIFICADO")) {
			//TODO
		}
	}
}
