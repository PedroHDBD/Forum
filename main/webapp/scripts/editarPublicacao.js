document.addEventListener("DOMContentLoaded", (event) => {
	const urlParams = new URLSearchParams(window.location.search);
	const idPublicacao = parseInt(urlParams.get('idPublicacao'));
	let forumNome = "";
	let idTopico = null;
	let forum = null;
	let publicacaoTexto = null;

	$.ajax({
		url: "/ProjetoTCC/api/TopicoControl?acao=BuscarTopicoComPublicacao&idPublicacao=" + idPublicacao,
		method: "GET",
		dataType: "json",
		success: function(topicoData) {
			const nomeTopico = topicoData.titulo;
			idTopico = topicoData.idTopico;

			$.ajax({
				url: "/ProjetoTCC/api/ForumControl?acao=BuscarNomeForumComTopico&idTopico=" + idTopico,
				method: "GET",
				dataType: "json",
				success: function(forumData) {
					forum = forumData;
					forumNome = forum.nome;
					editarPublicacao(nomeTopico);
				},
				error: function(xhr, status, error) {
					console.error("Erro na requisição BuscarNomeForumComTopico:", status, error);
					console.log("Resposta do servidor:", xhr.responseText);
				}
			});

			$(document).on("click", "#voltar", () => {
				const params = new URLSearchParams({ idTopico: idTopico });
				window.location.href = `publicacoes.jsp?${params.toString()}`;
			});
		},
		error: function(xhr, status, error) {
			console.error("Erro na requisição BuscarTopicoComPublicacao:", status, error);
			console.log("Resposta do servidor:", xhr.responseText);
		}
	});

	function editarPublicacao(publicacaoNome) {

		$.ajax({
			url: "/ProjetoTCC/api/PublicacaoControl?acao=BuscarTextoPublicacao&idPublicacao=" + idPublicacao,
			method: "GET",
			dataType: "json",
			success: function(publicacaoData) {
				publicacaoTexto = publicacaoData.texto;
				document.getElementById("textoAtual").value = publicacaoTexto;
				$('#h5').text(forumNome + " - " + publicacaoNome + " - Editar publicação");
			}
		});

		$(document).on("click", ".editarPublicacaoButton", function(e) {
			e.preventDefault();
			
			const textoAtual = document.getElementById("textoAtual").value
			
			const input = document.getElementById('imagem');
			
			let imagem = null;

			if (input.files && input.files.length > 0) {
			    imagem = input.files[0]; 
			}

			if (imagem && imagem.size > 5 * 1024 * 1024) {
			  alert("A imagem deve ter no máximo 5MB.");
			  return;
			}
			
			const formData = new FormData();
			formData.append('acao', 'editar');
			formData.append('idPublicacao', idPublicacao);
			formData.append('texto', textoAtual);

			if (imagem) {
			  formData.append('imagem', imagem);
			}
			
			$.ajax({
				url: '/ProjetoTCC/api/PublicacaoControl',
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				success: function() {
					window.location.href = './publicacoes.jsp?idTopico=' + idTopico;
				},
				error: function(xhr, status, error) {
					alert("Erro ao editar o publicacao: " + xhr.status + " - " + error);
				}
			});
		});
	}
});