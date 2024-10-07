package br.com.evonetwork.utils;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class DynamicVoUtils {

	public void pegarVoPelaPk() throws Exception {
		BigDecimal codpap = new BigDecimal(1000);
		JapeWrapper prospectDAO = JapeFactory.dao("ParceiroProspect");
		DynamicVO prospectVO = prospectDAO.findByPK(codpap);

		BigDecimal idNota = new BigDecimal(1000);
		BigDecimal idItem = new BigDecimal(1000);
		DynamicVO item = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA).findByPK(idNota, idItem);

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
		BigDecimal numOs = new BigDecimal(1000);
		EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
		FinderWrapper finder = new FinderWrapper("AD_TCSOSEUNID", "this.NUMOS = " + numOs);
		Collection<DynamicVO> unidadesConsumidoras = (Collection<DynamicVO>) dwfEntityFacade.findByDynamicFinderAsVO(finder);

	}
}
