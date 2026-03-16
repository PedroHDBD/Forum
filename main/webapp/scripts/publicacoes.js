$(document).ready(function () {

	const urlParams = new URLSearchParams(window.location.search);
	const idTopico = Number(urlParams.get('idTopico'));

	if (isNaN(idTopico)) {
		console.error("idTopico inválido. URL atual:", window.location.href);
		return;
	}

	let forum = null;
	let idForum = null;
	let forumNome = "";

	$.ajax({
		url: "/ProjetoTCC/api/ForumControl",
		method: "GET",
		dataType: "json",
		data: {
			acao: "BuscarNomeForumComTopico",
			idTopico: idTopico
		},
		success: function (forumData) {
			forum = forumData;
			forumNome = forum.nome;
			idForum = forum.idForum;

			carregarCabecalhoTopico();
			carregarPublicacoes();
		},
		error: function (err) {
			console.error("Erro ao buscar fórum:", err);
		}
	});

	function carregarCabecalhoTopico() {

		$.ajax({
			url: "/ProjetoTCC/api/control",
			method: "POST",
			dataType: "json",
			data: {
				acao: "ResgatarTitulo",
				idTopico: idTopico
			},
			success: function (topico) {

				const $header = $("#header");
				const dataFormatada = formatarData(topico.data);

				$header.find(".h5Header")
					.text(forumNome + " - " + topico.titulo);

				$("#spanforumNome").text("Por: " + topico.username);
				$("#spanData").text(dataFormatada);

				configurarAcoesTopico($header, topico);
			}
		});
	}

	function configurarAcoesTopico($header, topico) {
		
		const $acoes = $header.find("#acoes");

		if (topico.idUsuario !== window.idUsuario) return;

		const template = document.querySelector("#acoesTopicoTemplate");

		const $clone = $(template.content.cloneNode(true));

		$clone.find(".excluirTopicoButton").on("click", function () {

			if (!confirm("Excluir este tópico?")) return;

			$.ajax({
				url: `/ProjetoTCC/api/TopicoControl?idTopico=${idTopico}`,
				type: "DELETE",
				success: function () {
					window.location.href =
						`topicos.jsp?idForum=${idForum}`;
				}
			});
		});

		$clone.find(".editarTopicoButton").on("click", function () {
			window.location.href =
				`editarTopico.jsp?idTopico=${idTopico}`;
		});

		$acoes.prepend($clone);	}

	function carregarPublicacoes() {

		PublicacaoService.carregar({
			url: `/ProjetoTCC/api/PublicacaoControl?acao=listarPublicacoes&idTopico=${idTopico}`,
			containerSelector: "#publicacoes-list",
			idUsuario: window.idUsuario
		});
	}

	$(document).on("submit", "form", function (e) {
		e.preventDefault();
	});

	$(document).on("keydown", "input, textarea", function (e) {
		if (e.key === "Enter") {
			e.preventDefault();
		}
	});

	$(document).on("click", "#voltar", function () {
		if (idForum) {
			window.location.href =
				`topicos.jsp?idForum=${idForum}`;
		}
	});

	$(".adicionarPublicacaoButton").on("click", function () {
		window.location.href =
			`adicionarPublicacao.jsp?idTopico=${idTopico}`;
	});

	function formatarData(dataUTC) {

		const data = new Date(dataUTC);

		return `${data.getUTCDate().toString().padStart(2, '0')}/` +
			`${(data.getUTCMonth() + 1).toString().padStart(2, '0')}/` +
			`${data.getUTCFullYear().toString().slice(-2)} ` +
			`${data.getUTCHours().toString().padStart(2, '0')}:` +
			`${data.getUTCMinutes().toString().padStart(2, '0')}`;
	}

});