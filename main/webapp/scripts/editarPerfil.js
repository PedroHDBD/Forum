document.addEventListener("DOMContentLoaded", (event) => {

		$(document).on("click", ".editarUsernameButton", function(e) {
		e.preventDefault();

		const novoUsername = document.getElementById("username").value;
		const novoNome = document.getElementById("nome").value;

		$.ajax({
			url: '/ProjetoTCC/api/UsuarioControl',
			type: 'POST',
			data: {
				acao: 'editarUsername',
				username: novoUsername
			},
			success: function() {
				document.getElementById("username").value = novoUsername;
				alert("Dados atualizados com sucesso!");
			},
			error: function(xhr, status, error) {
				alert("Erro ao editar o username: " + xhr.status + " - " + error);
			}
		});
		
		$.ajax({
			url: '/ProjetoTCC/api/UsuarioControl',
			type: 'POST',
			data: {
				acao: 'editarNome',
				nome: novoNome

			},
			success: function() {
				document.getElementById("nome").value = novoNome;
			},
			error: function(xhr, status, error) {
				alert("Erro ao editar o nome: " + xhr.status + " - " + error);
			}
		});
	});
});