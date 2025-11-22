package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import database.DBQuery;
import model.Publicacao;
import model.Usuario;

/**
 * Servlet implementation class PublicacaoControl
 */

@WebServlet("/api/PublicacaoControl")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)

public class PublicacaoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicacaoControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String acao = request.getParameter("acao");

		if ("listarPublicacoes".equals(acao)) {

			String idTopico = request.getParameter("idTopico");

			DBQuery db = new DBQuery();

			String sql = "SELECT p.idPublicacao, p.texto, p.data, p.imagem, p.numLikes, p.numComentarios,"
					+ "u.idUsuario, u.nome, u.username, u.foto " + "FROM Publicacao p "
					+ "JOIN Usuario u ON p.idUsuario = u.idUsuario " + "WHERE p.idTopico = " + idTopico + " "
					+ "ORDER BY p.data DESC";

			ResultSet rs = db.query(sql);

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

			String json = new Gson().toJson(lista);
			response.getWriter().write(json);
		}

		if ("BuscarTextoPublicacao".equals(acao)) {
			String idPublicacao = request.getParameter("idPublicacao");

			DBQuery dbQuery = new DBQuery("Publicacao", "texto", "idPublicacao");
			ResultSet resultSet = dbQuery.select("idPublicacao = " + idPublicacao);

			try {
				while (resultSet.next()) {
					Publicacao publicacao = new Publicacao();
					publicacao.setTexto(resultSet.getString("texto"));

					String json = new Gson().toJson(publicacao);
					response.getWriter().write(json);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if ("BuscarNumLikes".equals(acao)) {
			String idPublicacao = request.getParameter("id");

			DBQuery dbQuery = new DBQuery("Publicacao", "numLikes", "idPublicacao");
			ResultSet resultSet = dbQuery.select("idPublicacao = " + idPublicacao);

			try {
				while (resultSet.next()) {
					Publicacao publicacao = new Publicacao();
					publicacao.setNumLikes(resultSet.getInt("numLikes"));

					String json = new Gson().toJson(publicacao);
					response.getWriter().write(json);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if ("adicionarPublicacao".equals(acao)) {

			String idTopico = request.getParameter("idTopico");
			int idUsuario = (int) request.getSession().getAttribute("idUsuario");
			String texto = request.getParameter("texto");

			Part imagemPart = request.getPart("imagem");
			String caminhoImagem = null;

			if (imagemPart != null && imagemPart.getSize() > 0) {

				if (imagemPart.getSize() > (5 * 1024 * 1024)) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.setContentType("application/json");
					response.getWriter().write("{\"erro\":\"A imagem deve ter no máximo 5MB.\"}");
					return;
				}

				String nomeArquivo = UUID.randomUUID().toString() + "_" + imagemPart.getSubmittedFileName();
				String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator
						+ "publicacoes";

				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists())
					uploadDir.mkdirs();

				imagemPart.write(uploadPath + File.separator + nomeArquivo);
				caminhoImagem = "uploads/publicacoes/" + nomeArquivo;
			}

			if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
				DBQuery dbquery = new DBQuery("Publicacao", "texto, idTopico, idUsuario, imagem", "idPublicacao");
				String[] publicacao = { texto, idTopico, String.valueOf(idUsuario), caminhoImagem };
				dbquery.insert(publicacao);
				response.setStatus(HttpServletResponse.SC_OK);

			} else {
				DBQuery dbquery = new DBQuery("Publicacao", "texto, idTopico, idUsuario", "idPublicacao");
				String[] publicacao = { texto, idTopico, String.valueOf(idUsuario) };
				dbquery.insert(publicacao);
				response.setStatus(HttpServletResponse.SC_OK);
			}
		}

		if ("editar".equals(acao)) {
			String idPublicacao = request.getParameter("idPublicacao");
			String texto = request.getParameter("texto");
			
			Part imagemPart = request.getPart("imagem");
			String caminhoImagem = null;

			if (imagemPart != null && imagemPart.getSize() > 0) {

				if (imagemPart.getSize() > (5 * 1024 * 1024)) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.setContentType("application/json");
					response.getWriter().write("{\"erro\":\"A imagem deve ter no máximo 5MB.\"}");
					return;
				}

				String nomeArquivo = UUID.randomUUID().toString() + "_" + imagemPart.getSubmittedFileName();
				String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator
						+ "publicacoes";

				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists())
					uploadDir.mkdirs();

				imagemPart.write(uploadPath + File.separator + nomeArquivo);
				caminhoImagem = "uploads/publicacoes/" + nomeArquivo;
			}

			if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
				DBQuery query = new DBQuery("Publicacao", "texto, idPublicacao, imagem", "idPublicacao");
				String[] publicacao = { texto, idPublicacao, caminhoImagem};
				query.update(publicacao);
				response.setStatus(HttpServletResponse.SC_OK);

			} else {
				DBQuery query = new DBQuery("Publicacao", "texto, idPublicacao", "idPublicacao");
				String[] publicacao = { texto, idPublicacao };
				query.update(publicacao);
				response.setStatus(HttpServletResponse.SC_OK);
			}
			
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idPublicacao = request.getParameter("idPublicacao");

		DBQuery dbQuery = new DBQuery("Publicacao", "idPublicacao", "idPublicacao");
		String[] publicacao = { idPublicacao };
		dbQuery.delete(publicacao);

	}
}
