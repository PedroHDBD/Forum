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
import model.Usuario;

@WebServlet("/api/UsuarioControl")
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

        DBQuery dbQuery = new DBQuery("usuario", "nome, username", "idUsuario");
        ResultSet resultSet = dbQuery.select("idUsuario = " + idUsuario);

        Usuario usuario = new Usuario();

        try {
            if (resultSet.next()) {
                usuario.setNome(resultSet.getString("nome"));
                usuario.setUsername(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(usuario);
        response.getWriter().write(json);
    }
}

