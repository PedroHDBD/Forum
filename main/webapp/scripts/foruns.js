document.addEventListener("DOMContentLoaded", () => {
	function carregarForuns() {
		$.ajax({
			url: "/ProjetoTCC/api/control?acao=ListarForuns",
			method: "POST",
			dataType: "json",
			success: function(forums) {
				const $forumList = $("#forum-list");
				const $template = $("#forum-template").contents();

				$forumList.empty();

				forums.forEach(forum => {
					const $forumElement = $template.clone();

					$forumElement.find(".forum-nome").text(forum.nome);
					const $botao = $forumElement.find(".seguirBtn");
					const $icone = $botao.find("i");

					$.ajax({
						url: "/ProjetoTCC/api/LikeControl",
						type: "GET",
						dataType: "json",
						data: {
							id: forum.idForum,
							idNome: "idForum",
							tabela: "UsuarioForum"
						},
						success: function(response) {
							if (response && response.curtido === true) {
								$icone.removeClass("bi-award").addClass("bi-award-fill text-danger");
							}
						},
						error: function(err) {
							console.warn("GET LikeControl falhou");
						}
					});
					
					$forumElement.find(".forumBtn").on("click", () => {
						const params = new URLSearchParams({
							idForum: forum.idForum,
						});
						window.location.href = `topicos.jsp?${params.toString()}`;
					});

					$forumElement.find(".seguirBtn").on("click", () => {

						const acao = $icone.hasClass("bi-award")
							? "seguir"
							: "naoSeguir";

						$.post("/ProjetoTCC/api/LikeControl", {
							acao: acao,
							id: forum.idForum,
							idNome: "idForum",
							tabelaLike: "UsuarioForum",
							tabelaParent: "Forum"
						}, function() {

							$icone.toggleClass("bi-award bi-award-fill text-danger");

						});
					});

					$forumList.append($forumElement);
				});
			},
			error: function() {
				$("#forum-list").text("Erro ao carregar fóruns.");
			}
		});
	}
	carregarForuns();
});