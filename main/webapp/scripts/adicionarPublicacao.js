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
  const imagem = form.find('input[name="imagem"]')[0].files[0];
  
  if (imagem && imagem.size > 5 * 1024 * 1024) {
    alert("A imagem deve ter no máximo 5MB.");
    return;
  }

  if (!texto) {
    alert("Preencha o texto da publicação.");
    return;
  }

  const formData = new FormData();
  formData.append('acao', 'adicionarPublicacao');
  formData.append('idTopico', idTopico);
  formData.append('texto', texto);

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
      console.error("Erro:", status, error);
      console.error("Resposta do servidor:", xhr.responseText);
    }
  });
}