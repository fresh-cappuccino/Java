package alunosJSF.alunosJSF;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TesteJPA {
	@PersistenceContext(unitName = "ALUNOSJSF_JTA_PU")
//	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ALUNOSJSF_JTA_PU");
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getNome() throws Exception{
		long findId = 696969;
		PessoaTeste p = new PessoaTeste();
		
		try {
			p.setNome("DSA");
//			p.setIdBanco(696969);
			p.setTpDoc("F");
			p.setTpPessoa("F");
			
			System.out.println("<- <- <- <- <- <- <-");
			
//			p = em.find(Pessoa.class, findId);
			em.persist(p);
//			em.flush();
			
//			p.setNome(p.getNome().concat(" MASAMI"));
			System.out.println(p.getNome());
			System.out.println(p);
			
//			em.merge(p);
//			em.remove(p);
//			return p.getNome() == null ? "NULL" : p.getNome().equals("") ? "NOME" : p.getNome();
		} catch(Exception e) {
			System.out.println("---------------");
			System.out.println("FOI NÃO!");
			System.out.println("---------------");
			
			throw new Exception();
//			return p.getNome() == null ? "NULL" : p.getNome().equals("") ? "NOME" : p.getNome();
		}
		
		p.setNome(p.getNome().concat(" MASAMI"));
		
		return p.getNome();
//		em.close();
//		System.out.println("PPP");
//		return "NOME";.
	}
}
