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
	const imagem = form.find('input[name="imagem"]')[0].files[0];

	if (imagem && imagem.size > 5 * 1024 * 1024) {
		alert("A imagem deve ter no máximo 5MB.");
		return;
	}

	const urlParams = new URLSearchParams(window.location.search);
	const idForum = parseInt(urlParams.get('idForum'));

	if (!titulo || !texto) {
		alert("Preencha todos os campos.");
		return;
	}

	const formData = new FormData();

	if (imagem) {
		formData.append('imagem', imagem);
	}

	formData.append('idForum', idForum);
	formData.append('acao', "adicionarTopico");
	formData.append('titulo', titulo);
	formData.append('texto', texto);

	$.ajax({
		url: '/ProjetoTCC/api/TopicoControl',
		type: 'POST',
		data: formData,
		processData: false, 
		contentType: false,  
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