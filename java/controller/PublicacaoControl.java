package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DBQuery;
import model.Publicacao;

/**
 * Servlet implementation class PublicacaoControl
 */
@WebServlet("/api/PublicacaoControl")
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

			List<Publicacao> publicacoes = new ArrayList<>();
			String idTopico = request.getParameter("idTopico");

			DBQuery dbQuery = new DBQuery("Publicacao",
					"data, idPublicacao, idTopico, idUsuario, numComentarios, numLikes, numSalvamentos, texto, username",
					"idPublicacao");
			ResultSet resultSet = dbQuery.select("idTopico = " + idTopico);

			try {
				while (resultSet.next()) {
					Publicacao publicacao = new Publicacao();
					publicacao.setData(resultSet.getTimestamp("data").toInstant().toString());
					publicacao.setIdPublicacao(resultSet.getInt("idPublicacao"));
					publicacao.setIdTopico(resultSet.getInt("idTopico"));
					publicacao.setIdUsuario(resultSet.getInt("idUsuario"));
					publicacao.setNumComentarios(resultSet.getInt("numComentarios"));
					publicacao.setNumLikes(resultSet.getInt("numLikes"));
					publicacao.setNumSalvamentos(resultSet.getInt("numSalvamentos"));
					publicacao.setTexto(resultSet.getString("texto"));
					publicacao.setUsername(resultSet.getString("username"));
					System.out.println("\n\n" + publicacao.getData());
					publicacoes.add(publicacao);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String json = new Gson().toJson(publicacoes);
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if ("adicionarPublicacao".equals(acao)) {

			String idTopico = request.getParameter("idTopico");
			int idUsuario = (int) request.getSession().getAttribute("idUsuario");
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

			dbquery = new DBQuery("Publicacao", "texto, idTopico, idUsuario, username", "idPublicacao");
			String[] publicacao = { texto, idTopico, String.valueOf(idUsuario), username };
			dbquery.insert(publicacao);

			response.setStatus(HttpServletResponse.SC_OK);
		}

		if ("editar".equals(acao)) {
			String idPublicacao = request.getParameter("idPublicacao");
			String texto = request.getParameter("texto");

			DBQuery query = new DBQuery("Publicacao", "texto, idPublicacao", "idPublicacao");
			String[] publicacao = { texto, idPublicacao };

			query.update(publicacao);
			response.setStatus(HttpServletResponse.SC_OK);
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
