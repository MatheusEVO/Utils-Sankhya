package br.com.evonetwork.utils;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.util.JapeSessionContext;

public class ForcarUsuarioSUP implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		BigDecimal usuarioLogado = BigDecimal.ZERO;
		JapeSessionContext.putProperty("usuario_logado", usuarioLogado);
	}

}
