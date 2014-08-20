package web.usuario;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name = "nome", length = 50, nullable = true)
	private String nome;
	
	
	@Column(name = "login", length = 50, nullable = true)
	private String login;
	
	@org.hibernate.annotations.NaturalId
	@Column(name = "email", length = 50, nullable = true, unique = true)
	private String email;
	
	@Column(name = "senha", length = 50, nullable = true)
	private String senha;
	
	@Column(name = "telefone", length = 50, nullable = true)
	private String telefone;
	
	@Column(name = "dataCadastro")
	private Date dataCadastro;
	
	@Column(name = "ativo")
	private boolean ativo;
	
	@ElementCollection(targetClass = String.class)
	@JoinTable(name="usuario_permissao", uniqueConstraints ={@UniqueConstraint(columnNames = {"codigo_user","permissao_user"})},
	joinColumns = @JoinColumn(name = "codigo_user")) @Column(name="permissao_user", length=50)
	private Set<String> permissao = new HashSet<String>();
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Set<String> getPermissao() {
		return permissao;
	}
	public void setPermissao(Set<String> permissao) {
		this.permissao = permissao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((permissao == null) ? 0 : permissao.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (permissao == null) {
			if (other.permissao != null)
				return false;
		} else if (!permissao.equals(other.permissao))
			return false;
		return true;
	}
	
	
	
}
