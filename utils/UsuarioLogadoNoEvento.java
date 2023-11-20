package br.com.evonetwork.utils;

import java.math.BigDecimal;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.ws.ServiceContext;

public class UsuarioLogadoNoEvento {
	BigDecimal usuarioLogado = ((AuthenticationInfo)ServiceContext.getCurrent().getAutentication()).getUserID();
}
