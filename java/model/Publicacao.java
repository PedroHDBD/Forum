package model;

import java.sql.Timestamp;

public class Publicacao {

	private int idPublicacao;
	private int idTopico;
	private String texto;
	private int numLikes;
	private int numSalvamentos;
	private int numComentarios;
	private String data;
	private int idUsuario;
	private String username;
	
	public int getIdPublicacao() {
		return idPublicacao;
	}
	public void setIdPublicacao(int idPublicacao) {
		this.idPublicacao = idPublicacao;
	}
	public int getIdTopico() {
		return idTopico;
	}
	public void setIdTopico(int idTopico) {
		this.idTopico = idTopico;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getNumLikes() {
		return numLikes;
	}
	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}
	public int getNumSalvamentos() {
		return numSalvamentos;
	}
	public void setNumSalvamentos(int numSalvamentos) {
		this.numSalvamentos = numSalvamentos;
	}
	public int getNumComentarios() {
		return numComentarios;
	}
	public void setNumComentarios(int numComentarios) {
		this.numComentarios = numComentarios;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
