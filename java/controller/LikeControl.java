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

@WebServlet("/api/LikeControl")
public class LikeControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LikeControl() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String acao = request.getParameter("acao");

		int idUsuario = (int) request.getSession().getAttribute("idUsuario");
		String idPublicacao = request.getParameter("idPublicacao");

		DBQuery dbQuery = new DBQuery("likepubli", "idUsuario, idPublicacao", "idUsuario");

		if ("adicionarLike".equals(acao)) {
			String[] like = { String.valueOf(idUsuario), idPublicacao };
			dbQuery.insert(like);
			response.setStatus(HttpServletResponse.SC_OK);
			dbQuery.incrementPublicacao(idPublicacao, "numLikes");
		}

		if ("removerLike".equals(acao)) {
			String[] like = { String.valueOf(idUsuario), idPublicacao };
			dbQuery.delete(like);
			dbQuery.decrementPublicacao(idPublicacao, "numLikes");
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}