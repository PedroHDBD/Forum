package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import model.Forum;
import model.Topico;

@WebServlet("/api/TopicoControl")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)

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
			String username = (String) request.getSession().getAttribute("username");
			String titulo = request.getParameter("titulo");
			String texto = request.getParameter("texto");
			
			System.out.println("\n\n\n" + idForum + "\n\n\n");

			DBQuery dbquery = new DBQuery("Topico", "titulo, idForum, idUsuario, username", "idTopico");
			String[] topico = { titulo, idForum, String.valueOf(idUsuario), username };
			int idNovoTopico = dbquery.insert(topico);
			
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
				dbquery = new DBQuery("Publicacao", "idTopico, texto, idUsuario, imagem", "idPublicacao");
				String[] publicacao = { String.valueOf(idNovoTopico), texto, String.valueOf(idUsuario),caminhoImagem };
				dbquery.insert(publicacao);
				response.setStatus(HttpServletResponse.SC_OK);

			} else {
				dbquery = new DBQuery("Publicacao", "idTopico, texto, idUsuario", "idPublicacao");
				String[] publicacao = { String.valueOf(idNovoTopico), texto, String.valueOf(idUsuario) };
				dbquery.insert(publicacao);
				response.setStatus(HttpServletResponse.SC_OK);
			}

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
