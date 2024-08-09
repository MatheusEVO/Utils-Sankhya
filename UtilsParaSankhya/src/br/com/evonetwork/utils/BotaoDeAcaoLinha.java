package br.com.evonetwork.utils;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;

public class BotaoDeAcaoLinha implements AcaoRotinaJava{

	@Override
	public void doAction(ContextoAcao ca) throws Exception {

	}
	
	private void loopLinhas(ContextoAcao ca) {
		try {
			Registro[] linhas = ca.getLinhas();

			for (Registro linha : linhas) {

				BigDecimal numcontrato = (BigDecimal) linha.getCampo("NUMCONTRATO");
				
				boolean simNao = ca.confirmarSimNao("texto 1", "texto 2", 1);
				if (simNao) {
				 //TODO
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void validarLinhasSelecionadas(ContextoAcao ca) throws Exception {
		if (ca.getLinhas().length > 1)
			throw new Exception("Selecione apenas uma negociação para alterar o produto.");
		if (ca.getLinhas().length == 0)
			throw new Exception("Selecione uma negociação para alterar o produto.");
	}
}
