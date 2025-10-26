package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import database.DBQuery;

@WebServlet("/api/LoginControl")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginControl() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		DBQuery dbQuery = new DBQuery("usuario", "idUsuario, senha", "idUsuario");
		ResultSet rs = dbQuery.select("email = '" + email + "'");

		try {
			if (rs.next()) {
				int idUsuario = rs.getInt("idUsuario");
				String senhaHash = rs.getString("senha");

				if (BCrypt.checkpw(senha, senhaHash)) {
				    request.getSession().setAttribute("idUsuario", idUsuario);
				    response.sendRedirect(request.getContextPath() + "/view/foruns.jsp");
				} else {
				    request.setAttribute("erroSenha", "Senha incorreta");
				    response.sendRedirect(request.getContextPath() + "/view/login.jsp?erroSenha=1");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				request.setAttribute("erroEmail", "Usuário não encontrado");
			    response.sendRedirect(request.getContextPath() + "/view/login.jsp?erroEmail=1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}