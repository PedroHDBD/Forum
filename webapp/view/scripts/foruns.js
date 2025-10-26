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

					$forumElement.find("button").on("click", () => {
						const params = new URLSearchParams({
							idForum: forum.idForum,
						});
						window.location.href = `topicos.jsp?${params.toString()}`;
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