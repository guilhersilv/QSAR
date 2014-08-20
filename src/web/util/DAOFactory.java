package web.util;

import web.conexao.HibernateUtil;
import web.usuario.UsuarioCrud;
import web.usuario.UsuarioDAO;

public class DAOFactory {

	
			
		public static UsuarioDAO criarUsuarioDAO(){
			
			UsuarioCrud usuarioDAO = new UsuarioCrud();
			usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
			return usuarioDAO;
		}
	

}
