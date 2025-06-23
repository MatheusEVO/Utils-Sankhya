package br.com.evonetwork.crud;


import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.facades.DWFFacade;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class EntityFacadeUtils {
	
	public void buscarDadoPk() throws Exception {
		EntityFacade ef = EntityFacadeFactory.getDWFFacade();
		DynamicVO vo = (DynamicVO) ef.findEntityByPrimaryKeyAsVO("NomeDaEntidade",
				new Object[] { BigDecimal.ZERO, BigDecimal.TEN });
		vo.getProperty("NomeDoCampo");
		vo.asBigDecimal("NomeDoCampoNumero");
	}

	public void atualizarDados() throws Exception {
		EntityFacade ef = EntityFacadeFactory.getDWFFacade();
		PersistentLocalEntity pe = ef.findEntityByPrimaryKey("nome da entidade", 
				new Object[] { BigDecimal.ZERO, BigDecimal.ZERO});
		DynamicVO vo = (DynamicVO) pe.getValueObject();
		vo.setProperty("campo", "valor do campo");
		pe.setValueObject((EntityVO) vo);
		
	}
	
	public void atualizarDados2() throws Exception {
		EntityFacade ef = EntityFacadeFactory.getDWFFacade();
		FinderWrapper fw = new FinderWrapper("nome da entidade", " where");
		
//		FinderWrapper fw2 = new FinderWrapper("nome da entidade", 
//				" campo = ?", new Object[] {BigDecimal.ZERO, BigDecimal.TEN});
		
		Collection<PersistentLocalEntity> colecao = ef.findByDynamicFinder(fw);
		
		for (PersistentLocalEntity persistentLocal : colecao) {
			DynamicVO vo = (DynamicVO) persistentLocal.getValueObject();
			
			vo.setProperty("campo", "valor campo");
			vo.setProperty("campo", "valor campo");
			vo.setProperty("campo", "valor campo");
			vo.setProperty("campo", "valor campo");
			
			persistentLocal.setValueObject((EntityVO)vo);
		}
	}
	
	public void buscarCollectionPleoCriterio() throws Exception {
		EntityFacade ef = EntityFacadeFactory.getDWFFacade();
		FinderWrapper finder = new FinderWrapper("NomeDaEntidade", "this.CAMPO1 = ? AND CAMPO2 = ?",
				new Object[] { BigDecimal.ZERO, BigDecimal.TEN });
		Collection<DynamicVO> colecaoVO = ef.findByDynamicFinderAsVO(finder);
		
		for (DynamicVO vo : colecaoVO) {
			vo.getProperty("NomeDoCampo");
			vo.asBigDecimal("NomeDoCampoNumero");
		}
	}
	
}
