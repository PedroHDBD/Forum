package model;

import java.sql.Timestamp;

public class Comentario {
	private int idComentario;
	private int idUsuario;
	private int idPublicacao;
	private String texto;
	private int numCurtidas;
	private Timestamp data;
	private String username;
	public int getIdComentario() {
		return idComentario;
	}
	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdPublicacao() {
		return idPublicacao;
	}
	public void setIdPublicacao(int idPublicacao) {
		this.idPublicacao = idPublicacao;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getNumCurtidas() {
		return numCurtidas;
	}
	public void setNumCurtidas(int numCurtidas) {
		this.numCurtidas = numCurtidas;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
