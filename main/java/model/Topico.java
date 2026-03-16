package model;

import java.sql.Timestamp;

public class Topico {
	
	private int idTopico;
    private String titulo;
    private Timestamp data;
    private int idForum;
    private int idUsuario;
    private String username;
	
    public int getIdTopico() {
		return idTopico;
	}
	public void setIdTopico(int idTopico) {
		this.idTopico = idTopico;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public int getIdForum() {
		return idForum;
	}
	public void setIdForum(int idForum) {
		this.idForum = idForum;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}

