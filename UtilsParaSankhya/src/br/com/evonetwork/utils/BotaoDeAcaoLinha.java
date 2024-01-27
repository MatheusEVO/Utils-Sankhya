package br.com.evonetwork.utils;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;

public class BotaoDeAcaoLinha implements AcaoRotinaJava{

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		try {
			Registro[] linhas = contexto.getLinhas();

			for (Registro linha : linhas) {

				BigDecimal numcontrato = (BigDecimal) linha.getCampo("NUMCONTRATO");
				
				boolean simNao = contexto.confirmarSimNao("texto 1", "texto 2", 1);
				if (simNao) {
				 //TODO
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
