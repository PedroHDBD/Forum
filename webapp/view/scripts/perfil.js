document.addEventListener("DOMContentLoaded", (event) => {
	function carregarTopicos() {
		
		$.ajax({
			url: "/ProjetoTCC/api/UsuarioControl",
			method: "GET",
			dataType: "json",
			success: function(usuario) {
				document.getElementById('nome').textContent = usuario.nome;
				document.getElementById('username').textContent = usuario.username;
				document.getElementById('nome').textContent = usuario.nome;
			},
			error: function() {
			    document.getElementById('h5').textContent = "Erro ao carregar usuário";
			}
		});
	}
	$(document).ready(function() {
		carregarTopicos();
	});
});