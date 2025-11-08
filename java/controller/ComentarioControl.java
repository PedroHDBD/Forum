package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DBQuery;
import model.Comentario;

@WebServlet("/api/ComentarioControl")
public class ComentarioControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ComentarioControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		if (request.getSession().getAttribute("idUsuario") == null) {
			response.sendRedirect("../view/login.jsp");
			return;
		}

		List<Comentario> comentarios = new ArrayList<>();
		String idPublicacao = request.getParameter("idPublicacao");
		DBQuery dbQuery = new DBQuery("Comentario", "idComentario, idPublicacao, idUsuario, texto, data, numCurtidas, username", "idComentario");
		ResultSet resultSet = dbQuery.select("idPublicacao = " + idPublicacao);

		try {
			while (resultSet.next()) {
				Comentario comentario = new Comentario();
				comentario.setIdPublicacao(resultSet.getInt("idPublicacao"));
				comentario.setIdComentario(resultSet.getInt("idComentario"));
				comentario.setTexto(resultSet.getString("texto"));
				comentario.setData(resultSet.getTimestamp("data"));
				comentario.setIdUsuario(resultSet.getInt("idUsuario"));
				comentario.setNumCurtidas(resultSet.getInt("numCurtidas"));
				comentario.setUsername(resultSet.getString("username"));
				comentarios.add(comentario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String json = new Gson().toJson(comentarios);
		response.getWriter().write(json);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (request.getSession().getAttribute("idUsuario") == null) {
			response.sendRedirect("../view/login.jsp");
			return;
		}

		String idComentario = request.getParameter("idComentario");
		String idPublicacao = request.getParameter("idPublicacao");

		DBQuery dbQuery = new DBQuery("Comentario", "idComentario", "idComentario");
		String[] comentario = { idComentario };
		dbQuery.delete(comentario);

		dbQuery.decrement("Publicacao", "idPublicacao", idPublicacao, "numComentarios");
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write("{}");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (request.getSession().getAttribute("idUsuario") == null) {
			response.sendRedirect("../view/login.jsp");
		}

		String idPublicacao = request.getParameter("idPublicacao");
		int idUsuario = (int) request.getSession().getAttribute("idUsuario");
		String texto = request.getParameter("texto");
		
		DBQuery dbquery = new DBQuery("usuario", "username", "idUsuario");
		ResultSet rsUsuario = dbquery.select("idUsuario = " + idUsuario);
		String username = "";

		try {
			if (rsUsuario != null && rsUsuario.next()) {
				username = rsUsuario.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbquery = new DBQuery("Comentario", "idPublicacao, texto, idUsuario, username", "idComentario");
		String[] comentario = { String.valueOf(idPublicacao), texto, String.valueOf(idUsuario), username };
		dbquery.insert(comentario);

		dbquery.increment("Publicacao", "idPublicacao", idPublicacao, "numComentarios");

		Gson gson = new Gson();
		String json = gson.toJson(comentario);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
