package web.conexao;

//import java.sql.Date;


//import web.usuario.Usuario;
//import web.usuario.UsuarioCrud;
//import web.usuario.UsuarioDAO;

import java.util.List;

import javax.swing.JOptionPane;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.Criteria;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import web.usuario.Usuario;



public class TesteClasses {

	/**
	 * @param args
	 */
/**
 * @param args
 */
/**
 * @param args
 */
/**
 * @param args
 * @throws SystemException 
 * @throws HeuristicRollbackException 
 * @throws HeuristicMixedException 
 * @throws RollbackException 
 * @throws SecurityException 
 */



public static void main(String[] args)  {
		
		
	/*UsuarioRN userCrud = new UsuarioRN();
	
		
		String[] nomes = {"junior", "kely", "junior"};
		String[] emails = {"sol@gmail.com.br", "lua@hotmail.com.br", "venus@jose.com.br"};
		String[] senha = {"12345","78945","65432"};
		String[] login = {"PoderosoSol","AmorosaLua","VenusK"};
		String[] fones = {"(61) 3333-44", "(61) 7777-5555", "(61) 9090-2525"};
		
		
		
		Usuario contatoAnnotations = null;
		
		for (int i = 0; i < nomes.length; i++) {
			contatoAnnotations = new Usuario();
			contatoAnnotations.setNome(nomes[i]);
			contatoAnnotations.setEmail(emails[i]);
			contatoAnnotations.setSenha(senha[i]);
			contatoAnnotations.setLogin(login[i]);
			contatoAnnotations.setTelefone(fones[i]);			
			contatoAnnotations.setDataCadastro(new Date(System.currentTimeMillis()));
			contatoAnnotations.setAtivo(false);
			userCrud.salvar(contatoAnnotations);
		}
	List<web.usuario.Usuario> contatos = userCrud.listUser();
	
	// Itere nessa lista e imprima as informações dos contatos:
	
	for (web.usuario.Usuario contato : contatos) {
	System.out.println("Nome: " + contato.getNome());
	System.out.println("Email: " + contato.getEmail());
	System.out.println("Senha: " + contato.getSenha());
	System.out.println("Ativo: " + contato.getLogin());
	System.out.println("Codigo: " + contato.getCodigo() + "\n");
	}
		System.out.println("Total de registros cadastrados: " + userCrud.listUser().size());
		
	}
*/
//String username = "stkjunior@hotmail.com";
//String  senha ="123123";
//senha="123123";  
   

	

			//List<Usuario> consulta;
		Session session = null;
		//session = (Session) HibernateUtil.getSessionFactory();   
	    // session.beginTransaction(); 
		Transaction transacao = null;
	
		session = HibernateUtil.getSessionFactory().openSession();
		transacao = session.beginTransaction();
	   
	 
			//String autenticar = "SELECT email,senha FROM Usuario WHERE Usuario.email=:email";
		
			//Criteria hql = (Criteria) session.createQuery(autenticar);
			//hql.uniqueResult();
			
			//System.out.println("kkk"+hql.list());

String nomes = "Ciclano";
			
		Criteria criteira = session.createCriteria(Usuario.class).add(  
	            Restrictions.ilike("login", nomes));  
	  
	    @SuppressWarnings("unchecked")
		List<Usuario> resultados = criteira.list();  
	  
	    if (resultados == null) {  
	        JOptionPane.showMessageDialog(null, "Nenhum registro encontrado!");  
	    }  
	  
	
	 
	   
			
			 System.out.println("kkkkkkkk " + resultados.size());
	  
			 transacao.commit();
} 


} 

