package br.com.evonetwork.utils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class PegarDynamicVO implements EventoProgramavelJava{

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		EntityFacade dwfFacadeP = EntityFacadeFactory.getDWFFacade();
        final DynamicVO dynamicPAP = (DynamicVO)dwfFacadeP.getDefaultValueObjectInstance("ParceiroProspect");
        dynamicPAP.setProperty("AD_NROUNICO", "alguma coisa");		
	}

	@Override
	public void beforeCommit(TransactionContext tranCtx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
