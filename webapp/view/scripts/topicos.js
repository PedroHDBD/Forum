document.addEventListener("DOMContentLoaded", (event) => {
	function carregarTopicos() {

		const urlParams = new URLSearchParams(window.location.search);
		const idForum = parseInt(urlParams.get('idForum'));

		const topicoList = document.getElementById('topico-list');

		const idUsuario = topicoList.getAttribute('data-idusuario');

		let nome;

		$.ajax({
			url: "/ProjetoTCC/api/ForumControl?acao=BuscarNomeForum&idForum=" + idForum,
			method: "GET",
			dataType: "json",
			success: function(forum) {
				const h5 = document.getElementById('h5');
				h5.textContent = `${forum.nome} - Tópicos`;
				nome = forum.nome;
			},
			error: function() {
				document.getElementById('h5').textContent = "Erro ao carregar nome do fórum";
			}

		});

		$.ajax({
			url: "/ProjetoTCC/api/control?acao=ListarTopicos&idForum=" + idForum,
			method: "POST",
			dataType: "json",
			success: function(topicos) {
				const $topicoList = $("#topico-list");

				const topicoTemplate = document.querySelector("#topico-template").content;
				const botoesTemplate = document.querySelector("#autor-botoes-template").content;

				$topicoList.empty();

				topicos.forEach(topico => {
					const dataUTC = topico.data;
					const dataLocal = new Date(dataUTC);
					const dia = dataLocal.getUTCDate().toString().padStart(2, '0');
					const mes = (dataLocal.getUTCMonth() + 1).toString().padStart(2, '0');
					const ano = dataLocal.getUTCFullYear().toString().slice(-2);
					const hora = dataLocal.getUTCHours().toString().padStart(2, '0');
					const minutos = dataLocal.getUTCMinutes().toString().padStart(2, '0');
					const dataFormatada = `${dia}/${mes}/${ano} ${hora}:${minutos}`;

					const $topico = $(topicoTemplate.cloneNode(true));

					$topico.find(".titulo-topico").text(topico.titulo);
					$topico.find(".autor-topico").html("<div><span class='fw-bolder pe-1' >" + topico.username + "</span>");
					$topico.find(".data-topico").text(dataFormatada);


					$topico.find("button").on("click", () => {
						const params = new URLSearchParams({
							idTopico: topico.idTopico,
						});
						window.location.href = `publicacoes.jsp?${params.toString()}`;
					});

					if (idUsuario == topico.idUsuario) {
						$topico.find(".botaoSubmit").addClass("rounded-start-pill");

						const $botoesAutor = $(botoesTemplate.cloneNode(true));

						$botoesAutor.find(".excluirTopicoButton").on("click", () => {
							if (confirm("Tem certeza que deseja excluir este tópico?")) {
								fetch(`/ProjetoTCC/api/TopicoControl?idTopico=${topico.idTopico}`, {
									method: "DELETE"
								})
									.then(response => {
										if (response.ok) {
											carregarTopicos();
										} else {
											alert("Erro ao excluir o tópico.");
										}
									})
									.catch(error => {
										console.error("Erro na exclusão:", error);
										alert("Erro ao excluir o tópico.");
									});
							}
						});

						$botoesAutor.find(".editarTopicoButton").on("click", () => {
							const params = new URLSearchParams({
								idTopico: topico.idTopico,
							});
							window.location.href = `editarTopico.jsp?${params.toString()}`;
						})

						$topico.find(".divTopico").append($botoesAutor);
					} else {
						$topico.find(".botaoSubmit").addClass("rounded-5");
					}

					$topicoList.append($topico);

				});
			}
		});
	}
	$(document).ready(function() {
		carregarTopicos();
	});
});