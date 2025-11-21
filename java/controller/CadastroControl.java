package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBQuery;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/api/CadastroControl")
public class CadastroControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CadastroControl() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String username = request.getParameter("username");


		try {
			DBQuery dbQuery = new DBQuery("usuario", "idUsuario, nome, email, senha, username", "idUsuario");
			ResultSet rs = dbQuery.select("email = '" + email + "'");

			if (rs != null && rs.next()) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				request.setAttribute("erroEmail", "Email já cadastrado");
			    response.sendRedirect(request.getContextPath() + "/view/cadastro.jsp?erroEmail=1");
				return;
			}

		    ResultSet rsUsername = dbQuery.select("username = '" + username + "'");
		    if (rsUsername != null && rsUsername.next()) {
		        response.setStatus(HttpServletResponse.SC_CONFLICT);
		        request.setAttribute("erroUsername", "Username já cadastrado");
		        response.sendRedirect(request.getContextPath() + "/view/cadastro.jsp?erroUsername=1");
		        return;
		    }
		    
			String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

			String imagem = "uploads/usuarios/profile.png";
			
			String[] usuario = { nome, email, senhaHash, username, imagem};
			dbQuery = new DBQuery("usuario", "nome, email, senha, username, imagem", "idUsuario");
			dbQuery.insert(usuario);

			dbQuery = new DBQuery("usuario", "idUsuario, nome, email, senha, username", "idUsuario");
			ResultSet novo = dbQuery.select("email = '" + email + "'");

			if (novo != null && novo.next()) {
				int idUsuario = novo.getInt("idUsuario");
				request.getSession().setAttribute("idUsuario", idUsuario);
				response.setStatus(HttpServletResponse.SC_OK);
		        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Erro ao cadastrar usuário.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Erro no banco de dados.");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Erro inesperado.");
		}
	}
}
