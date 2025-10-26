package controller;

import com.google.gson.Gson;
import database.DBQuery;
import model.Comentario;
import model.Forum;
import model.Publicacao;
import model.Topico;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/control")
public class Control extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String acao = request.getParameter("acao");

		if (request.getSession().getAttribute("idUsuario") == null) {
			response.sendRedirect("../view/login.jsp");
		}

		if ("ListarForuns".equals(acao)) {
			ListarForuns(response);
		} else if ("ListarTopicos".equals(acao)) {
			ListarTopicos(request, response);
		} else if ("ResgatarTitulo".equals(acao)) {
			ResgatarTitulo(request, response);
		}
	}

	private void ListarForuns(HttpServletResponse response) throws IOException {
		List<Forum> forums = new ArrayList<>();
		DBQuery dbQuery = new DBQuery("Forum", "idForum, nome", "idForum");
		ResultSet resultSet = dbQuery.select("");

		try {
			while (resultSet.next()) {
				Forum forum = new Forum();
				forum.setIdForum(resultSet.getInt("idForum"));
				forum.setNome(resultSet.getString("nome"));
				forums.add(forum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String json = new Gson().toJson(forums);
		response.getWriter().write(json);
	}

	private void ListarTopicos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Topico> topicos = new ArrayList<>();
		String idForum = request.getParameter("idForum");
		DBQuery dbQuery = new DBQuery("Topico", "idTopico, titulo, data, idForum, idUsuario, username", "idTopico");
		ResultSet resultSet = dbQuery.select("idForum = " + idForum);

		try {
			while (resultSet.next()) {
				Topico topico = new Topico();
				topico.setIdForum(resultSet.getInt("idForum"));
				topico.setIdTopico(resultSet.getInt("idTopico"));
				topico.setTitulo(resultSet.getString("titulo"));
				topico.setData(resultSet.getTimestamp("data"));
				topico.setIdUsuario(resultSet.getInt("idUsuario"));
				topico.setUsername(resultSet.getString("username"));
				System.out.println("\n\n\n" + topico.getTitulo());

				topicos.add(topico);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String json = new Gson().toJson(topicos);
		response.getWriter().write(json);
	}

	private void ResgatarTitulo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idTopico = request.getParameter("idTopico");
		DBQuery dbQuery = new DBQuery("Topico", "titulo, idUsuario, data", "idTopico");
		ResultSet resultSet = dbQuery.select("idTopico = " + idTopico);
		Topico topico = new Topico();

		try {
			while (resultSet.next()) {
				topico.setTitulo(resultSet.getString("titulo"));
				topico.setData(resultSet.getTimestamp("data"));
				topico.setIdUsuario(resultSet.getInt("idUsuario"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String json = new Gson().toJson(topico);
		response.getWriter().write(json);
	}

}