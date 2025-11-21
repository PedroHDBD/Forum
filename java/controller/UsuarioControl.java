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
import model.Usuario;

@WebServlet("/api/UsuarioControl")

@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,   // 1MB
	    maxFileSize = 5 * 1024 * 1024,     // 5MB
	    maxRequestSize = 6 * 1024 * 1024   // 6MB
	)
public class UsuarioControl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public UsuarioControl() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Integer idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
		if (idUsuario == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"erro\": \"Usuário não logado\"}");
			return;
		}

		DBQuery dbQuery = new DBQuery("Usuario", "nome, username, foto", "idUsuario");
		ResultSet resultSet = dbQuery.select("idUsuario = " + idUsuario);

		Usuario usuario = new Usuario();

		try {
			if (resultSet.next()) {
				usuario.setNome(resultSet.getString("nome"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setImagem(resultSet.getString("foto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String json = new Gson().toJson(usuario);
		response.getWriter().write(json);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		Integer idUsuario = (Integer) request.getSession().getAttribute("idUsuario");

		if (idUsuario == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

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
					+ "usuarios";

			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists())
				uploadDir.mkdirs();

			imagemPart.write(uploadPath + File.separator + nomeArquivo);
			caminhoImagem = "uploads/usuarios/" + nomeArquivo;

			DBQuery dbQuery = new DBQuery("Usuario", "foto, idUsuario", "idUsuario");
			String[] valores = { caminhoImagem, String.valueOf(idUsuario) };

			dbQuery.update(valores);

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
