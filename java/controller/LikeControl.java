package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DBQuery;

@WebServlet("/api/LikeControl")
public class LikeControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LikeControl() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String acao = request.getParameter("acao");
		int idUsuario = (int) request.getSession().getAttribute("idUsuario");
		String idPublicacao = request.getParameter("idPublicacao");

		DBQuery dbQuery = new DBQuery("LikePubli", "idUsuario, idPublicacao", "");

		try {

			if ("adicionarLike".equals(acao)) {
				ResultSet rsCheck = dbQuery.select("idUsuario = " + idUsuario + " AND idPublicacao = " + idPublicacao);

				if (!rsCheck.next()) {
					String[] like = { String.valueOf(idUsuario), idPublicacao };
					dbQuery.insert(like);
					dbQuery.increment("Publicacao", "idPublicacao", idPublicacao, "numLikes");

				}

				response.setStatus(HttpServletResponse.SC_OK);
			}

			if ("removerLike".equals(acao)) {

				dbQuery.delete("idUsuario = " + idUsuario + " AND idPublicacao = " + idPublicacao);

				dbQuery.decrement("Publicacao", "idPublicacao", idPublicacao, "numLikes");

				response.setStatus(HttpServletResponse.SC_OK);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");

		int idUsuario = (int) request.getSession().getAttribute("idUsuario");

		String id = request.getParameter("id");
		String idNome = request.getParameter("idNome");
		String tabela = request.getParameter("tabela");

		if (!tabela.equals("LikePubli") && !tabela.equals("LikeComentario")) {
			response.sendError(400, "Tabela inválida.");
			return;
		}

		DBQuery dbQuery = new DBQuery(tabela, "idUsuario, " + idNome, "");

		boolean curtiu = false;

		try {
			ResultSet rs = dbQuery.select("idUsuario = " + idUsuario + " AND " + idNome + " = " + id);

			curtiu = rs.next();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.getWriter().write(new Gson().toJson(Map.of("curtido", curtiu)));
	}
}