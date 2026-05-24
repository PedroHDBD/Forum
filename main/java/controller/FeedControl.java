package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DBQuery;
import model.Publicacao;
import model.Usuario;

@WebServlet("/api/FeedControl")
public class FeedControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String acao = request.getParameter("acao");

		if (!"listarFeed".equals(acao)) return;

		Integer idUsuario = (Integer) request.getSession().getAttribute("idUsuario");

		if (idUsuario == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		int limit = 5;
		int offset = 0;

		try {
			limit = Integer.parseInt(request.getParameter("limit"));
			offset = Integer.parseInt(request.getParameter("offset"));
		} catch (Exception e) {
		}

		String sql =
			"SELECT p.idPublicacao, p.texto, p.data, p.imagem, p.numLikes, p.numComentarios, " +
			"u.idUsuario, u.nome, u.username, u.foto " +
			"FROM Publicacao p " +
			"JOIN Usuario u ON p.idUsuario = u.idUsuario " +
			"JOIN Topico t ON p.idTopico = t.idTopico " +
			"JOIN UsuarioForum uf ON uf.idForum = t.idForum " +
			"WHERE uf.idUsuario = " + idUsuario + " " +
			"ORDER BY p.data DESC " +
			"LIMIT " + limit + " OFFSET " + offset;

		DBQuery db = new DBQuery();
		ResultSet rs = db.query(sql);

		if (rs == null) return;

		ArrayList<Publicacao> lista = new ArrayList<>();

		try {
			while (rs.next()) {

				Publicacao pub = new Publicacao();
				pub.setIdPublicacao(rs.getInt("idPublicacao"));
				pub.setTexto(rs.getString("texto"));
				pub.setData(rs.getString("data"));
				pub.setImagem(rs.getString("imagem"));
				pub.setNumLikes(rs.getInt("numLikes"));
				pub.setNumComentarios(rs.getInt("numComentarios"));

				Usuario user = new Usuario();
				user.setIdUsuario(rs.getInt("idUsuario"));
				user.setNome(rs.getString("nome"));
				user.setUsername(rs.getString("username"));
				user.setImagem(rs.getString("foto"));

				pub.setUsuario(user);
				lista.add(pub);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.getWriter().write(new Gson().toJson(lista));
	}
}