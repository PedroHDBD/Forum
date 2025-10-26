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
import model.Topico;

@WebServlet("/api/TopicoControl")
public class TopicoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TopicoControl() {
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
		String acao = request.getParameter("acao");
		String idPublicacao = request.getParameter("idPublicacao");

		if ("BuscarTopicoComPublicacao".equals(acao)) {
			DBQuery dbQuery = new DBQuery("Publicacao", "idTopico", "idPublicacao");
			ResultSet resultSet = dbQuery.select("idPublicacao = " + idPublicacao);
			int idTopico = 0;

			try {
				if (resultSet.next()) {
					idTopico = resultSet.getInt("idTopico");
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fórum não encontrado.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar nome do fórum.");
			}

			dbQuery = new DBQuery("Topico", "titulo, idTopico", "idTopico");
			resultSet = dbQuery.select("idTopico = " + idTopico);

			try {
				if (resultSet.next()) {
					Topico topico = new Topico();
					topico.setTitulo(resultSet.getString("titulo"));
					topico.setIdTopico(resultSet.getInt("idTopico"));
					String json = new Gson().toJson(topico);
					response.getWriter().write(json);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tópico não encontrado.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar tópico.");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (request.getSession().getAttribute("idUsuario") == null) {
			response.sendRedirect("../view/login.jsp");
			return;
		}
		String acao = request.getParameter("acao");

		if ("adicionarTopico".equals(acao)) {

			String idForum = request.getParameter("idForum");
			int idUsuario = (int) request.getSession().getAttribute("idUsuario");
			String titulo = request.getParameter("titulo");
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

			dbquery = new DBQuery("Topico", "titulo, idForum, idUsuario, username", "idTopico");
			String[] topico = { titulo, idForum, String.valueOf(idUsuario), username };
			int idNovoTopico = dbquery.insert(topico);

			dbquery = new DBQuery("Publicacao", "idTopico, texto, idUsuario, username", "idPublicacao");
			String[] publicacao = { String.valueOf(idNovoTopico), texto, String.valueOf(idUsuario), username };
			dbquery.insert(publicacao);

			String idTopico = Integer.toString(idNovoTopico);
			request.getSession().setAttribute("idTopico", idTopico);
			request.getSession().setAttribute("idForum", idForum);

			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("{\"status\": \"ok\", \"idTopico\": " + idTopico + "}");
		}

		if ("editar".equals(acao)) {
			String idTopico = request.getParameter("idTopico");
			String titulo = request.getParameter("titulo");

			DBQuery query = new DBQuery("Topico", "titulo, idTopico", "idTopico");
			String[] topico = { String.valueOf(titulo), String.valueOf(idTopico) };

			query.update(topico);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("{}");
		}
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

		String idTopico = request.getParameter("idTopico");

		DBQuery dbQuery = new DBQuery("Topico", "idTopico", "idTopico");
		String[] topico = { idTopico };
		dbQuery.delete(topico);

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write("{}");

	}
}
