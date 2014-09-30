package web.bean;



import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import web.usuario.Usuario;

import web.usuario.UsuarioRN;


@ManagedBean(name = "UsuarioBean")
@RequestScoped
public class UsuarioBean {
	
	private Usuario user = new Usuario();
	private String confirmarSenha;
	private List<Usuario> lista;
/////////////////////////////////////////////////////////////////////
	private String userEmail;
	private String password;
	
	//private EntityManager entityDAO;  
    private Usuario usuario; 

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	///////////////////////////////////////////////////////////////////
	
	public String novo(){
		
		this.user = new Usuario();
		this.user.setAtivo(true);
		this.user.setDataCadastro(new Date(System.currentTimeMillis()));
		return "usuarioCadastro";
	}

	public String salvar(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		UsuarioRN userRN = new UsuarioRN();
		String senha = this.user.getSenha();
		
		if(!senha.equals(this.confirmarSenha)){
			FacesMessage facesMessage = new FacesMessage("A senha nao foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		/*String verificarEmail = null;
		 verificarEmail = user.getEmail();
		boolean valido =  userRN.verificarUsuario(verificarEmail);
		System.out.print(valido);
		System.out.println(verificarEmail);
		if(valido == false){
			FacesMessage facesMessage = new FacesMessage("Email já cadastrado");
			context.addMessage(null, facesMessage);
			return "/public/usuarioCadastro.xhtml";
		}*/
		
		userRN.salvar(this.user);
		
		return "usuarioSucesso";
	}
	
	public String editar(){
		
		this.confirmarSenha = this.user.getSenha();
		
		return "usuarioCadastro";
		
	}
	public List<Usuario> getLista(){
		
		
		if(this.lista == null){
			
			UsuarioRN userRN = new UsuarioRN();
			
			
			this.lista = userRN.listUser();
	
			
			}
		
		
		return this.lista;
	}
	
	
	
	public String excluir(){
		
		UsuarioRN userRN = new UsuarioRN();
		userRN.excluir(this.user);
		this.lista = null;
		
		return null;
	}
	
	public String paginas(){
		
		return "/private/controleUsuario";
	}
	
public String ativar(){
		
		if(this.user.isAtivo())
			this.user.setAtivo(false);
		else
			this.user.setAtivo(true);
			
			return null;
		
	}

public String permitir(Usuario user, String permissao){
	this.user = user;
	java.util.Set<String> permissoes = this.user.getPermissao();
	if(permissoes.contains(permissao)){
		permissoes.remove(permissao);
	}
	else{
		permissoes.add(permissao);
	}
	return null;
}

public String login(){
	
	FacesContext context = FacesContext.getCurrentInstance();
	UsuarioRN userRN = new UsuarioRN();
	
	//Usuario emailpesquisado = userRN.consulta(this.getUsuario().getEmail(), this.getUsuario().getSenha());
	Usuario emailpesquisado = userRN.consultar(this.userEmail);
	if(emailpesquisado == null){
		return "false";
	}
	
	
	else if (emailpesquisado.getSenha().equals(this.getPassword())) {  
		// Senha correta  
		
		return "/private/default";
		}
	//return "True";
	
	else {  
		FacesMessage facesMessage = new FacesMessage("Usuario ou senha nao foi confirmada corretamente");
		context.addMessage(null, facesMessage);
		   return "false";  
		}  
	
}

	public String registraSaida() {
	FacesContext fc = FacesContext . getCurrentInstance ();
	ExternalContext ec = fc. getExternalContext ();
	HttpSession session = ( HttpSession )ec. getSession ( false );
	session . removeAttribute ("usuario");
	return "teste";
	
}


	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
