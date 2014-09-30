package web.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import web.conexao.HibernateUtil;

public class UsuarioRN {

	Usuario user = new Usuario();
	
	private List<SelectItem> suaLista;
	 //UsuarioCrud UsuarioDAO;
	 UsuarioDAO userCrud = new UsuarioCrud();
	private Usuario usuarioPermissao;

	
	
	 public void salvar(Usuario user){

		Integer codigo = user.getCodigo();
		
		if(codigo == null || codigo == 0 ){
			user.getPermissao().add("ROLE_USUARIO");//adicionando na coluna permissao_user no banco 
			this.userCrud.salvar(user);
		}else{
			this.userCrud.atualizar(user);
		}
		
		
		}
	
	@SuppressWarnings("null")
	public void atualizar(Usuario user){
		Session sessao = null;
		
		if(user.getPermissao() == null || user.getPermissao().size() == 0){
			usuarioPermissao = this.carregar(user.getCodigo());
			user.setPermissao(usuarioPermissao.getPermissao());
			sessao.evict(usuarioPermissao);
		}
		this.userCrud.atualizar(user);
	}
	public void excluir(Usuario user){
		this.userCrud.excluir(user);
	}
	
	public List<Usuario> listUser(){
		return this.userCrud.listUser();
	}
	

	@SuppressWarnings({ "unchecked", "null" })
	public Usuario buscaPorLogin(int valor){
		Session sessao = null;
		
		List<Usuario> lista_pessoa = new ArrayList<Usuario>();
		lista_pessoa = sessao.createCriteria(Usuario.class).list();
		 @SuppressWarnings("unused")
		int tamanho_lista = lista_pessoa.size();
		 suaLista = new ArrayList<SelectItem>();
		 
		 for(Usuario sc : lista_pessoa){  
	           SelectItem si = new SelectItem();  
	           si.setLabel(sc.getNome());  
	           si.setValue(sc.getLogin());  
	                      
	           suaLista.add(si);  
	        }  
	        return (Usuario) lista_pessoa;  
	    }  
	
public Usuario consultar(String login){
	Session session = null;
	Transaction transacao = null;
	session = HibernateUtil.getSessionFactory().openSession();
	transacao = session.beginTransaction();

	
	Criteria criteria = session.createCriteria(Usuario.class);
	//return this.userCrud.consultar(login);
	 transacao.commit();
			return (Usuario) criteria.add(Restrictions.eq("login", login)).uniqueResult();
}
		
	
@SuppressWarnings({ "unchecked", "null" })
public List<Usuario> consulta(){
	
	Session session = null;
	String autenticar = "SELECT email,senha FROM Usuario WHERE Usuario.email=:email";
	Criteria hql = (Criteria) session.createQuery(autenticar);
	hql.uniqueResult();
	
	return hql.list();
	
}
 
	public Usuario carregar(Integer codigo){
		return this.userCrud.carregar(codigo);
	}

	
	public boolean verificarUsuario (String userEmail){  
        
      boolean valido = true;  
       
        
		/*Session sessao = null;
		@SuppressWarnings({ "unchecked", "null" })
		List<Usuario> query = sessao.createQuery("FROM Usuario u WHERE u.email = :userEmail")  
		        .setParameter("userEmail", userEmail).list();  
          
        Usuario user =  (Usuario) query; 
        if(user != null){  
            valido = "true";  
        } 
        else{
        return;}
        return; */
   		
        Usuario user = this.userCrud.consultar(userEmail);
    	if(user != null){  
            valido = false;  
        } 
       
        return valido;
        //List<Usuario> query = sessao.createQuery("FROM Usuario u WHERE u.email = :userEmail AND c.senha =:key")//.setParameter("key",listaEmail)
    }  

	
	
}
