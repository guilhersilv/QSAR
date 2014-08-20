package filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(servletNames={"faces Servlet"})
public class ControleDeAcesso implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		// Obter a sessao do usuario
	    HttpSession session = ((HttpServletRequest)request).getSession();
	    
	    // Verificar se a chave de autenticacao esta presente na requisicao
	    if(session.getAttribute("usuario") != null)
	    {
	      // Redirecionar para o proximo filtro da corrente ou para o recurso
	      chain.doFilter(request, response);
	    }
	    else
	    {
	      // Invalidar a sessao do usuario
	      session.invalidate();
	      
	      // Informar o usuario do erro
	      request.setAttribute("error", "Voc&ecirc; precisa estar autenticado para acessar o recurso!");
	      
	      // Redirecionar a requisicao para a pagina de login
	      request.getRequestDispatcher("login.xhtml").forward(request, response);
	    }
}	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
