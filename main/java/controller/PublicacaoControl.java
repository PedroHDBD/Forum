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
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import database.DBConnection;
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

		    if (idTopico == null || idTopico.isEmpty()) {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "idTopico inválido");
		        return;
		    }

		    String sql =
		        "SELECT p.idPublicacao, p.texto, p.data, p.imagem, p.numLikes, p.numComentarios, " +
		        "u.idUsuario, u.nome, u.username, u.foto " +
		        "FROM Publicacao p " +
		        "JOIN Usuario u ON p.idUsuario = u.idUsuario " +
		        "WHERE p.idTopico = " + idTopico + " " +
		        "ORDER BY p.data DESC";

		    DBQuery db = new DBQuery();
		    ResultSet rs = db.query(sql);

		    if (rs == null) {
		        System.out.println("\n\n\nERRO: ResultSet null\n\n\n");
		        return;
		    }

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

		if ("BuscarTextoPublicacao".equals(acao)) {

		    String idPublicacao = request.getParameter("idPublicacao");

		    if (idPublicacao == null || idPublicacao.isEmpty()) return;

		    DBQuery db = new DBQuery("Publicacao", "texto", "idPublicacao");
		    ResultSet rs = db.select("idPublicacao = " + idPublicacao);

		    if (rs == null) return;

		    try {
		        if (rs.next()) {
		            Publicacao p = new Publicacao();
		            p.setTexto(rs.getString("texto"));
		            response.getWriter().write(new Gson().toJson(p));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		if ("BuscarNumLikes".equals(acao)) {

		    String idPublicacao = request.getParameter("id");

		    if (idPublicacao == null || idPublicacao.isEmpty()) return;

		    DBQuery db = new DBQuery("Publicacao", "numLikes", "idPublicacao");
		    ResultSet rs = db.select("idPublicacao = " + idPublicacao);

		    if (rs == null) return;

		    try {
		        if (rs.next()) {
		            Publicacao p = new Publicacao();
		            p.setNumLikes(rs.getInt("numLikes"));
		            response.getWriter().write(new Gson().toJson(p));
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
			String username = (String) request.getSession().getAttribute("username");
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
				DBQuery dbquery = new DBQuery("Publicacao", "texto, idTopico, idUsuario, imagem, username",
						"idPublicacao");
				String[] publicacao = { texto, idTopico, String.valueOf(idUsuario), caminhoImagem, username };
				dbquery.insert(publicacao);
				response.setStatus(HttpServletResponse.SC_OK);

			} else {
				DBQuery dbquery = new DBQuery("Publicacao", "texto, idTopico, idUsuario, username", "idPublicacao");
				String[] publicacao = { texto, idTopico, String.valueOf(idUsuario), username };
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
				String[] publicacao = { texto, idPublicacao, caminhoImagem };
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
