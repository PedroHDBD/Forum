window.PublicacaoService = (function() {

	function carregar(config) {

		const {
			url,
			containerSelector,
			idUsuario
		} = config;

		$.ajax({
			url: url,
			method: "GET",
			dataType: "json",
			success: function(publicacoes) {
				renderizarPublicacoes(publicacoes, containerSelector, idUsuario);
			},
			error: function(err) {
				console.error("Erro ao carregar publicações:", err);
			}
		});
	}

	function renderizarPublicacoes(publicacoes, containerSelector, idUsuario) {

		const template = document.querySelector("#publicacao-template").content;
		const $lista = $(containerSelector);
		$lista.empty();

		publicacoes.forEach(publicacao => {

			const $col = $(`
				<div class="col-12 col-md-10 col-lg-10 col-xl-10 p-4"></div>
			`);

			const $publicacao = $(template.cloneNode(true)).find(".publicacao");

			preencherPublicacao($publicacao, publicacao);
			configurarLikesPublicacao($publicacao, publicacao.idPublicacao);
			configurarAcoesPublicacao($publicacao, publicacao, idUsuario);
			configurarAdicaoComentario($publicacao, publicacao.idPublicacao, idUsuario);
			carregarComentarios($publicacao, publicacao, idUsuario);

			$col.append($publicacao);
			$lista.append($col);
		});
	}

	function preencherPublicacao($publicacao, publicacao) {

		const data = formatarData(publicacao.data);

		$publicacao.find(".span-publicacao-autor").text(publicacao.usuario.username);
		$publicacao.find(".span-publicacao-data").text(data);
		$publicacao.find(".publicacao-texto").text(publicacao.texto);

		if (publicacao.imagem) {
			$publicacao.find(".imagem")
				.attr("src", "/ProjetoTCC/" + publicacao.imagem)
				.show();
		} else {
			$publicacao.find(".imagem").hide();
		}

		$publicacao.find(".fotoPerfil")
			.attr("src", "/ProjetoTCC/" + publicacao.usuario.foto);
	}

	function formatarData(dataUTC) {

		const data = new Date(dataUTC);

		return `${data.getUTCDate().toString().padStart(2, '0')}/` +
			`${(data.getUTCMonth() + 1).toString().padStart(2, '0')}/` +
			`${data.getUTCFullYear().toString().slice(-2)} ` +
			`${data.getUTCHours().toString().padStart(2, '0')}:` +
			`${data.getUTCMinutes().toString().padStart(2, '0')}`;
	}

	function configurarLikesPublicacao($publicacao, idPublicacao) {

		const template = document.querySelector("#curtirPublicacaoTemplate").content;
		const $clone = $(template.cloneNode(true));

		const $botao = $clone.find(".curtirPublicacaoButton");
		const $icone = $botao.find("i");
		const $numLikes = $clone.find(".numLikes");

		$.ajax({
			url: "/ProjetoTCC/api/LikeControl",
			type: "GET",
			dataType: "json",
			data: {
				id: idPublicacao,
				idNome: "idPublicacao",
				tabela: "LikePubli"
			},
			success: function(response) {
				if (response && response.curtido === true) {
					$icone.removeClass("bi-heart").addClass("bi-heart-fill text-danger");
				}
			},
			error: function(err) {
				console.warn("GET LikeControl falhou");
			}
		});

		buscarNumLikes($numLikes, idPublicacao, "PublicacaoControl");

		$botao.on("click", function() {

			const acao = $icone.hasClass("bi-heart")
				? "adicionarLike"
				: "removerLike";

			$.post("/ProjetoTCC/api/LikeControl", {
				acao: acao,
				id: idPublicacao,
				idNome: "idPublicacao",
				tabelaLike: "LikePubli",
				tabelaParent: "publicacao"
			}, function() {

				$icone.toggleClass("bi-heart bi-heart-fill text-danger");
				buscarNumLikes($numLikes, idPublicacao, "PublicacaoControl");

			});
		});

		$publicacao.find(".headerPublicacao").append($clone);
	}

	function configurarAcoesPublicacao($publicacao, publicacao, idUsuario) {

		if (publicacao.usuario.idUsuario !== idUsuario) return;

		const template = document.querySelector("#acoesPublicacaoTemplate").content;
		const $clone = $(template.cloneNode(true));

		const $excluir = $clone.find(".excluirPublicacaoButton");
		const $editar = $clone.find(".editarPublicacaoButton");

		$excluir.on("click", function() {

			if (confirm("Deseja excluir esta publicação?")) {

				$.ajax({
					url: `/ProjetoTCC/api/PublicacaoControl?idPublicacao=${publicacao.idPublicacao}`,
					type: "DELETE",
					success: function() {
						$publicacao.remove();
					}
				});
			}
		});

		$editar.on("click", function() {

			window.location.href =
				`editarPublicacao.jsp?idPublicacao=${publicacao.idPublicacao}`;
		});

		$publicacao.find(".headerPublicacao").append($clone);
	}

	function carregarComentarios($publicacao, publicacao, idUsuario) {

		if (publicacao.numComentarios <= 0) {
			$publicacao.find(".comentariosDiv").hide();
			return;
		}

		$.get("/ProjetoTCC/api/ComentarioControl", {
			acao: "ListarComentarios",
			idPublicacao: publicacao.idPublicacao
		}, function(comentarios) {

			comentarios.forEach(comentario => {

				renderizarComentario($publicacao, comentario, idUsuario);

			});
		});
	}

	function renderizarComentario($publicacao, comentario, idUsuario) {

		const template = document.querySelector("#comentario-template").content;
		const $comentario = $(template.cloneNode(true)).find(".comentario");

		$comentario.find(".comentarioUsername")
			.text(comentario.usuario.username);

		$comentario.find(".comentarioTexto")
			.text(comentario.texto);

		$comentario.find(".comentarioData")
			.text(formatarData(comentario.data));

		$comentario.find(".imagemPerfilComentario")
			.attr("src", "/ProjetoTCC/" + comentario.usuario.foto);

		configurarLikesComentario($comentario, comentario.idComentario);
		configurarExclusaoComentario($comentario, comentario, idUsuario);

		$publicacao.find(".comentariosDiv")
			.show()
			.append($comentario);
	}

	function configurarAdicaoComentario($publicacao, idPublicacao, idUsuario) {

		const $form = $publicacao.find(".adicionarComentarioForm");
		const $input = $form.find("input[name='texto']");

		$form.on("submit", function(e) {

			e.preventDefault();

			const formData = $form.serialize();

			$.ajax({
				url: "/ProjetoTCC/api/ComentarioControl?idPublicacao=" + idPublicacao,
				type: "POST",
				data: formData,

				success: function(comentarioCriado) {

					renderizarComentario($publicacao, comentarioCriado, idUsuario);

					$input.val("");
				},

				error: function(xhr) {

					alert("Erro ao adicionar comentário: " + xhr.responseText);
				}
			});
		});
	}

	function configurarLikesComentario($comentario, idComentario) {

		const template = document.querySelector("#curtirComentarioTemplate").content;
		const $clone = $(template.cloneNode(true));

		const $botao = $clone.find(".curtirComentarioButton");
		const $icone = $botao.find("i");
		const $numLikes = $clone.find(".numLikesComentario");

		$.ajax({
			url: "/ProjetoTCC/api/LikeControl",
			type: "GET",
			dataType: "json",
			data: {
				id: idComentario,
				idNome: "idComentario",
				tabela: "LikeComent"
			},
			success: function(response) {
				if (response && response.curtido === true) {
					$icone.removeClass("bi-heart").addClass("bi-heart-fill text-danger");
				}
			},
			error: function(err) {
				console.warn("GET LikeControl falhou");
			}
		});

		buscarNumLikes($numLikes, idComentario, "ComentarioControl");

		$botao.on("click", function() {

			const acao = $icone.hasClass("bi-heart")
				? "adicionarLike"
				: "removerLike";

			$.post("/ProjetoTCC/api/LikeControl", {
				acao: acao,
				id: idComentario,
				idNome: "idComentario",
				tabelaLike: "LikeComent",
				tabelaParent: "comentario"
			}, function() {

				$icone.toggleClass("bi-heart bi-heart-fill text-danger");
				buscarNumLikes($numLikes, idComentario, "ComentarioControl");

			});
		});

		$comentario.find(".comentarioActions").append($clone);
	}

	function configurarExclusaoComentario($comentario, comentario, idUsuario) {

		if (comentario.usuario.idUsuario !== idUsuario) return;

		const template = document.querySelector("#excluirComentarioTemplate").content;
		const $clone = $(template.cloneNode(true));

		$clone.find(".excluirComentarioButton")
			.on("click", function() {

				if (confirm("Excluir comentário?")) {

					$.ajax({
						url: `/ProjetoTCC/api/ComentarioControl?idComentario=${comentario.idComentario}`,
						type: "DELETE",
						success: function() {
							$comentario.remove();
						}
					});
				}
			});

		$comentario.find(".comentarioActions").append($clone);
	}

	function buscarNumLikes(span, id, nomeControl) {

		$.get(`/ProjetoTCC/api/${nomeControl}`, {
			acao: "BuscarNumLikes",
			id: id
		}, function(res) {
			span.text(res.numLikes);
		});
	}

	return {
		carregar: carregar
	};

})();