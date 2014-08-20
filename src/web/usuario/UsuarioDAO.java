package web.usuario;

import java.util.List;

public interface UsuarioDAO {

	
	public void salvar(Usuario user);
	public void atualizar(Usuario user);
	public void excluir(Usuario user);
	
	public List<Usuario> listUser();
	public Usuario buscaPorLogin(int valor);
	public Usuario carregar(Integer codigo);
	public Usuario consultar(String login);
}
