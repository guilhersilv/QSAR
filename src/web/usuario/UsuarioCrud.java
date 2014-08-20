package web.usuario;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import web.conexao.HibernateUtil;




public class UsuarioCrud implements UsuarioDAO{

	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	public void salvar(Usuario user) {
		Session sessao = null;
		Transaction transacao = null;

		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.save(user);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi poss�vel inserir o usuario. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de inser��o. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public void atualizar(Usuario user) {
		Session sessao = null;
		Transaction transacao = null;

		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.update(user);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi poss�vel alterar o usuario. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de atualiza��o. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public void excluir(Usuario user) {
		Session sessao = null;
		Transaction transacao = null;

		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.delete(user);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi poss�vel excluir o usuario. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de exclus�o. Mensagem: " + e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listUser() {
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<Usuario> resultado = null;

		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Usuario");
			resultado = consulta.list();
			transacao.commit();
			return resultado;
		} catch (HibernateException e) {
			System.out.println("N�o foi poss�vel selecionar usuario. Erro: " + e.getMessage());
			throw new HibernateException(e);
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de consulta. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public Usuario buscaPorLogin(int valor) {
		Usuario user = null;
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;

		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Usuario where codigo = :parametro");
			consulta.setInteger("parametro", valor);
			user = (Usuario) consulta.uniqueResult();
			transacao.commit();
			return user;
		} catch (HibernateException e) {
			System.out.println("N�o foi poss�vel buscar usuario. Erro: " + e.getMessage());
		} finally {
			try {
				session.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de buscar. Mensagem: " + e.getMessage());
			}
		}
		return user;
	}
	public Usuario carregar(Integer codigo){
		return null;
	
	}

	
		
		


	public Usuario consultar(String login) {
		
		Usuario user = null;
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;

	
			try {
				sessao = HibernateUtil.getSessionFactory().openSession();
				transacao = sessao.beginTransaction();
				consulta = sessao.createQuery("select email from Usuario u where u.email = :parametro");
				consulta.setString("parametro", login);
				user = (Usuario) consulta.uniqueResult();
				transacao.commit();
				return user;
			} catch (HibernateException e) {
				System.out.println("N�o foi poss�vel buscar usuario. Erro: " + e.getMessage());
			} finally {
				try {
					sessao.close();
				} catch (Throwable e) {
					System.out.println("Erro ao fechar opera��o de buscar. Mensagem: " + e.getMessage());
				}
			}
			
		//String hql = "select u from Usuario u where u.login = :login";
		//Query consulta = this.session.createQuery(hql);
		//consulta.setString("login", login);
		
		
		//return(Usuario) consulta.uniqueResult();
			return user;
	}	


	
}
