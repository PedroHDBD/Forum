document.addEventListener("DOMContentLoaded", (event) => {

	function carregarTopicos() {

		const urlParams = new URLSearchParams(window.location.search);
		const idForum = parseInt(urlParams.get('idForum'));

		$.ajax({
			url: "/ProjetoTCC/api/ForumControl?acao=BuscarNomeForum&idForum=" + idForum,
			method: "GET",
			dataType: "json",
			success: function(forum) {
				const h5 = document.getElementById('h5');
				h5.textContent = `${forum.nome} - Adicionar Tópico`;
			},
			error: function() {
				document.getElementById('h5').textContent = "Erro ao carregar nome do fórum";
			}

		});
	}
	$(document).ready(function() {
		carregarTopicos();
		$(document).on('submit', '#adicionarTopicoForm', function(e) {
			e.preventDefault();
			adicionarTopico($(this));
		});
	});
})

function adicionarTopico(form) {

	const titulo = form.find('input[name="titulo"]').val().trim();
	const texto = form.find('textarea[name="texto"]').val().trim();

	const urlParams = new URLSearchParams(window.location.search);
	const idForum = parseInt(urlParams.get('idForum'));

	if (!titulo || !texto) {
		alert("Preencha todos os campos.");
		return;
	}

	const formData = form.serializeArray();

	formData.push({ name: "idForum", value: idForum });
	formData.push({ name: "acao", value: "adicionarTopico" });

	$.ajax({
		url: '/ProjetoTCC/api/TopicoControl',
		type: 'POST',
		data: $.param(formData),
		dataType: "json",
		success: function(response) {
			window.location.href = './publicacoes.jsp?idTopico=' + response.idTopico;
		},
		error: function(xhr, status, error) {
			console.error("Erro:", status, error);
			console.error("Resposta do servidor:", xhr.responseText);
		}
	});
}