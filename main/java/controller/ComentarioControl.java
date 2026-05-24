package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import model.Publicacao;
import model.Usuario;

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

		String acao = request.getParameter("acao");

		if ("ListarComentarios".equals(acao)) {

			List<Comentario> comentarios = new ArrayList<>();
			String idPublicacao = request.getParameter("idPublicacao");
			DBQuery db = new DBQuery();

			int limit = 5;
			int offset = 0;

			try {
				limit = Integer.parseInt(request.getParameter("limit"));
				offset = Integer.parseInt(request.getParameter("offset"));
			} catch (Exception e) {
			}

			String sql =
				"SELECT c.idComentario, c.idPublicacao, c.idUsuario, " +
				"c.texto, c.data, c.numLikes, " +
				"u.idUsuario AS idUsuarioUsuario, " +
				"u.username, u.foto " +
				"FROM Comentario c " +
				"JOIN Usuario u ON c.idUsuario = u.idUsuario " +
				"WHERE c.idPublicacao = " + idPublicacao + " " +
				"ORDER BY c.data ASC " +
				"LIMIT " + limit + " OFFSET " + offset;

			ResultSet rs = db.query(sql);

			try {
				while (rs.next()) {

					Comentario comentario = new Comentario();

					comentario.setIdComentario(rs.getInt("idComentario"));
					comentario.setIdPublicacao(rs.getInt("idPublicacao"));
					comentario.setTexto(rs.getString("texto"));
					comentario.setData(rs.getTimestamp("data"));
					comentario.setNumLikes(rs.getInt("numLikes"));

					Usuario usuario = new Usuario();
					usuario.setIdUsuario(rs.getInt("idUsuarioUsuario"));
					usuario.setUsername(rs.getString("username"));
					usuario.setImagem(rs.getString("foto"));

					comentario.setUsuario(usuario);

					comentarios.add(comentario);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			String json = new Gson().toJson(comentarios);
			response.getWriter().write(json);
		}

		if ("BuscarNumLikes".equals(acao)) {
			String idComentario = request.getParameter("id");

			DBQuery dbQuery = new DBQuery("Comentario", "numLikes", "idComentario");
			ResultSet resultSet = dbQuery.select("idComentario = " + idComentario);

			try {
				while (resultSet.next()) {
					Comentario comentario = new Comentario();
					comentario.setNumLikes(resultSet.getInt("numLikes"));

					String json = new Gson().toJson(comentario);
					response.getWriter().write(json);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

		String idComentario = request.getParameter("idComentario");

		DBQuery dbQuery = new DBQuery();

		String idPublicacao = null;

		try {

			ResultSet rs = dbQuery.query("SELECT idPublicacao FROM Comentario WHERE idComentario = " + idComentario);

			if (rs.next()) {
				idPublicacao = rs.getString("idPublicacao");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		DBQuery deleteQuery = new DBQuery("Comentario", "idComentario", "idComentario");
		String[] comentario = { idComentario };
		deleteQuery.delete(comentario);

		if (idPublicacao != null) {
			deleteQuery.decrement("Publicacao", "idPublicacao", idPublicacao, "numComentarios");
		}

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
			return;
		}

		String idPublicacao = request.getParameter("idPublicacao");
		int idUsuario = (int) request.getSession().getAttribute("idUsuario");
		String texto = request.getParameter("texto");

		String username = "";
		String foto = "";

		DBQuery dbquery = new DBQuery("usuario", "username, foto", "idUsuario");
		ResultSet rsUsuario = dbquery.select("idUsuario = " + idUsuario);

		try {
			if (rsUsuario != null && rsUsuario.next()) {
				username = rsUsuario.getString("username");
				foto = rsUsuario.getString("foto");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbquery = new DBQuery("Comentario", "idPublicacao, texto, idUsuario, username", "idComentario");
		String[] comentarioInsert = { idPublicacao, texto, String.valueOf(idUsuario), username };

		int idComentarioGerado = dbquery.insert(comentarioInsert);

		dbquery.increment("Publicacao", "idPublicacao", idPublicacao, "numComentarios");

		Comentario comentario = new Comentario();
		comentario.setIdComentario(idComentarioGerado);
		comentario.setIdPublicacao(Integer.parseInt(idPublicacao));
		comentario.setTexto(texto);
		comentario.setData(new Timestamp(System.currentTimeMillis()));
		comentario.setNumLikes(0);

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		usuario.setUsername(username);
		usuario.setImagem(foto);

		comentario.setUsuario(usuario);

		Gson gson = new Gson();
		String json = gson.toJson(comentario);

		response.getWriter().write(json);
	}

}
