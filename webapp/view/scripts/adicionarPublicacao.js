document.addEventListener("DOMContentLoaded", (event) => {

	function addPublicacao() {

		const urlParams = new URLSearchParams(window.location.search);
		const idTopico = parseInt(urlParams.get('idTopico'));

		$.ajax({
			url: "/ProjetoTCC/api/ForumControl?acao=BuscarNomeForumComTopico&idTopico=" + idTopico,
			method: "GET",
			dataType: "json",
			success: function(forum) {
				const h5 = document.getElementById('h5');
				h5.textContent = `${forum.nome} - Adicionar Publicação`;

				$(document).on("click", "#voltar", () => {
					if (forum) {
						window.location.href = `publicacoes.jsp?idTopico=` + idTopico;
					} else {
						console.error("Variável 'topico' ainda não foi carregada.");
					}
				});

			},
			error: function() {
				document.getElementById('h5').textContent = "Erro ao carregar nome do fórum";
			}

		});
	}
	$(document).ready(function() {
		addPublicacao();
		$(document).on('submit', '#adicionarPublicacao', function(e) {
			e.preventDefault();
			adicionarPublicacao($(this));
		});
	});


})

function adicionarPublicacao(form) {

	const texto = form.find('textarea[name="texto"]').val().trim();

	if (!texto) {
		alert("Preencha todos os campos.");
		return;
	}

	$.ajax({
		url: '/ProjetoTCC/api/PublicacaoControl?acao=adicionarPublicacao&idTopico=' + idTopico,
		type: 'POST',
		data: { texto: texto },
		success: function() {
			window.location.href = './publicacoes.jsp?idTopico=' + idTopico;
		},
		error: function(xhr, status, error) {
			console.error("Erro:", status, error);
			console.error("Resposta do servidor:", xhr.responseText);
		}
	});
}