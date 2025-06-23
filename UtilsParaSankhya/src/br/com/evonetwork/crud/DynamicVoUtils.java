package br.com.evonetwork.crud;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class DynamicVoUtils {

	public void pegarVoPelaPk() throws Exception {
		// pega o VO mas não é possivel alterar a VO

		BigDecimal idNota = new BigDecimal(1000);
		BigDecimal idItem = new BigDecimal(1000);
		DynamicVO item = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA).findByPK(idNota, idItem);

	}

	public void pegarVoPelaPk(BigDecimal nufin) throws Exception {
		// pega a VO e é possivel fazer update
		PersistentLocalEntity persistent = EntityFacadeFactory.getDWFFacade().findEntityByPrimaryKey("Financeiro",
				new Object[] { nufin });
		DynamicVO financeiroVO = (DynamicVO) persistent.getValueObject();

		financeiroVO.setProperty("AD_INIBIFATURA", "S");

		persistent.setValueObject((EntityVO) financeiroVO);
	}

	public void criarVo() throws Exception {
		BigDecimal nureneg = new BigDecimal(1000);
		BigDecimal renegociacao = new BigDecimal(1000);

		EntityFacade dwfFacadeP = EntityFacadeFactory.getDWFFacade();
		DynamicVO dynamicUnid = (DynamicVO) dwfFacadeP.getDefaultValueObjectInstance("AD_RASTRENORIG");
		dynamicUnid.setProperty("NURENEG", nureneg);
		dynamicUnid.setProperty("NURENEGORIG", renegociacao);
		PersistentLocalEntity createEntity = dwfFacadeP.createEntity("AD_RASTRENORIG", (EntityVO) dynamicUnid);
		@SuppressWarnings("unused")
		DynamicVO save = (DynamicVO) createEntity.getValueObject();
	}

	public void pegarColecaoVos() throws Exception {
		// pega a coleção de VO mas não é possivel fazer alteração
		BigDecimal numOs = new BigDecimal(1000);
		EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
		FinderWrapper finder = new FinderWrapper("AD_TCSOSEUNID", "this.NUMOS = " + numOs);
		Collection<DynamicVO> unidadesConsumidoras = (Collection<DynamicVO>) dwfEntityFacade
				.findByDynamicFinderAsVO(finder);
	}

	public void pegarColecaoVosTeste() throws Exception {
		// pega a coleção de VO mas não é possivel fazer alteração

		BigDecimal codEmp = null;
		BigDecimal codFunc = null;
		
		EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
		DynamicVO novoTomadorVO = (DynamicVO) dwfEntityFacade.getDefaultValueObjectInstance("Tomador");
		FinderWrapper finder = new FinderWrapper("","this.CODEMP = ? AND this.CODFUNC = ? AND this.DTFIM IS NULL", new Object[] { codEmp, codFunc });
		
		Collection<DynamicVO>  tomadores = (Collection<DynamicVO>) dwfEntityFacade.findByDynamicFinder(finder);

	}
}
