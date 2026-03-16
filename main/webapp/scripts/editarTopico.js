document.addEventListener("DOMContentLoaded", (event) => {

	let forumNome = "";
	let idForum = null;
	let forum = null;
	const urlParams = new URLSearchParams(window.location.search);
	const idTopico = parseInt(urlParams.get('idTopico'));

	$.ajax({
		url: "/ProjetoTCC/api/ForumControl?acao=BuscarNomeForumComTopico&idTopico=" + idTopico,
		method: "GET",
		dataType: "json",
		success: function(forumData) {
			forum = forumData;
			forumNome = forum.nome;
			idForum = forum.idForum;
			editarTopico();
		},
		error: function(xhr, status, error) {
			console.error("Erro na requisição BuscarNomeForumComTopico:", status, error);
			console.log("Resposta do servidor:", xhr.responseText);
		}
	});

	function editarTopico() {

		$("#voltar").on("click", () => {
			if (forum) {
				const params = new URLSearchParams({ idTopico: idTopico });
				window.location.href = `publicacoes.jsp?${params.toString()}`;
			} else {
				console.error("Variável 'forum' ainda não foi carregada.");
			}
		});

		$.ajax({
			url: "/ProjetoTCC/api/control?acao=ResgatarTitulo&idTopico=" + idTopico,
			method: "POST",
			dataType: "json",
			success: function(topico) {
				document.getElementById("h5").textContent = forumNome + " - " + topico.titulo + " - Editar tópico";
				document.getElementById("tituloAtual").value = topico.titulo;

				$(document).on("click", ".editarTopicoButton", function(e) {
					e.preventDefault();
					$.ajax({
						url: '/ProjetoTCC/api/TopicoControl',
						type: 'POST',
						data: {
							acao: 'editar',
							idTopico: idTopico,
							titulo: document.getElementById("tituloAtual").value
						},
						success: function() {
							window.location.href = './publicacoes.jsp?idTopico=' + idTopico;
						},
						error: function(xhr, status, error) {
							alert("Erro ao editar o tópico: " + xhr.status + " - " + error);
						}
					});
				});
			}
		});
	}
});