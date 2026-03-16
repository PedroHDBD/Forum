package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DBQuery;
import model.Forum;

@WebServlet("/api/ForumControl")
public class ForumControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForumControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String acao = request.getParameter("acao");

		if ("BuscarNomeForum".equals(acao)) {
			String idForum = request.getParameter("idForum");

			DBQuery dbQuery = new DBQuery("Forum", "nome", "idForum");
			ResultSet resultSet = dbQuery.select("idForum = " + idForum);

			try {
				if (resultSet.next()) {
					String nome = resultSet.getString("nome");

					String json = new Gson().toJson(new NomeForum(nome));
					response.getWriter().write(json);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fórum não encontrado.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar nome do fórum.");
			}
		}

		if ("BuscarNomeForumComTopico".equals(acao)) {
			String idTopico = request.getParameter("idTopico");

			DBQuery dbQuery = new DBQuery("Topico", "idForum", "idTopico");
			ResultSet resultSet = dbQuery.select("idTopico = " + idTopico);
			int idForum = 0;

			try {
				if (resultSet.next()) {
					idForum = resultSet.getInt("idForum");
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fórum não encontrado.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar nome do fórum.");
			}

			dbQuery = new DBQuery("Forum", "nome, idForum", "idForum");
			resultSet = dbQuery.select("idForum = " + idForum);

			try {
				if (resultSet.next()) {
					Forum forum = new Forum();
					forum.setNome(resultSet.getString("nome"));
					forum.setIdForum(resultSet.getInt("idForum"));
					String json = new Gson().toJson(forum);
					response.getWriter().write(json);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fórum não encontrado.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar nome do fórum.");
			}

		}
	}

	private class NomeForum {
		String nome;

		NomeForum(String nome) {
			this.nome = nome;
		}
	}
}