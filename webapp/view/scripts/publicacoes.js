document.addEventListener("DOMContentLoaded", (event) => {

	//////// VARIÁVEIS GLOBAIS ////////
	const urlParams = new URLSearchParams(window.location.search);
	const idTopico = parseInt(urlParams.get('idTopico'));
	let forumNome = "";
	let idForum = null;
	let forum = null;

	//////// BUSCAR NOME DO FÓRUM (PELO TÓPICO) ////////
	$.ajax({
		url: "/ProjetoTCC/api/ForumControl?acao=BuscarNomeForumComTopico&idTopico=" + idTopico,
		method: "GET",
		dataType: "json",
		success: function(forumData) {
			forum = forumData;
			forumNome = forum.nome;
			idForum = forum.idForum;
			carregarPublicacoes();
		},
		error: function(xhr, status, error) {
			console.error("Erro na requisição BuscarNomeForumComTopico:", status, error);
			console.log("Resposta do servidor:", xhr.responseText);
		}
	});

	//////// BOTÃO VOLTAR ////////
	$(document).on("click", "#voltar", () => {
		if (forum) {
			const params = new URLSearchParams({ idForum: forum.idForum });
			window.location.href = `topicos.jsp?${params.toString()}`;
		} else {
			console.error("Variável 'forum' ainda não foi carregada.");
		}
	});

	//////// FUNÇÃO PRINCIPAL: CARREGAR PUBLICAÇÕES ////////
	function carregarPublicacoes() {

		//////// BOTÃO ADICIONAR PUBLICAÇÃO ////////
		$(".adicionarPublicacaoButton").on("click", () => {
			const params = new URLSearchParams({ idTopico: idTopico });
			window.location.href = `adicionarPublicacao.jsp?${params.toString()}`;
		});

		//////// REQUISIÇÃO: RESGATAR TÍTULO DO TÓPICO ////////
		$.ajax({
			url: "/ProjetoTCC/api/control?acao=ResgatarTitulo&idTopico=" + idTopico,
			method: "POST",
			dataType: "json",
			success: function(topico) {

				//////// CABEÇALHO DO TÓPICO ////////
				const $header = $("#header");
				tituloTopico = topico.titulo;
				$header.find(".h5Header").text(forumNome + " - " + tituloTopico);

				//////// FORMATAR DATA ////////
				const dataUTCSpan = topico.data;
				const dataLocal = new Date(dataUTCSpan);
				const diaSpan = dataLocal.getUTCDate().toString().padStart(2, '0');
				const mesSpan = (dataLocal.getUTCMonth() + 1).toString().padStart(2, '0');
				const anoSpan = dataLocal.getUTCFullYear().toString().slice(-2);
				const horaSpan = dataLocal.getUTCHours().toString().padStart(2, '0');
				const minutosSpan = dataLocal.getUTCMinutes().toString().padStart(2, '0');
				const dataFormatadaSpan = `${diaSpan}/${mesSpan}/${anoSpan} ${horaSpan}:${minutosSpan}`;

				$("#spanforumNome").text("Por: " + topico.username);
				$("#spanData").text(dataFormatadaSpan);

				//////// BOTÕES DE AÇÃO DO TÓPICO (EDITAR/EXCLUIR) ////////
				if (topico.idUsuario == idUsuario) {
					const acoesTopicoTemplate = document.querySelector("#acoesTopicoTemplate").content;
					const $acoesTopicoClone = acoesTopicoTemplate.cloneNode(true);
					const $excluirTopico = $($acoesTopicoClone).find(".excluirTopico");
					const $editarTopico = $($acoesTopicoClone).find(".editarTopico");

					// Excluir tópico
					$(document).on("click", ".excluirTopicoButton", function() {
						if (confirm("Tem certeza que deseja excluir este tópico?")) {
							$.ajax({
								url: '/ProjetoTCC/api/TopicoControl?idTopico=' + idTopico,
								type: 'DELETE',
								success: function() {
									window.location.href = './topicos.jsp?idForum=' + idForum;
								},
								error: function(xhr, status, error) {
									alert("Erro ao excluir o tópico: " + xhr.status);
								}
							});
						}
					});

					// Editar tópico
					$(document).on("click", ".editarTopicoButton", function() {
						const params = new URLSearchParams({ idTopico: idTopico });
						window.location.href = `editarTopico.jsp?${params.toString()}`;
					});

					$header.append($excluirTopico);
					$header.append($editarTopico);
				}

				const offcanvasTemplate = document.querySelector("#canvas-template").content.cloneNode(true);
				$header.append(offcanvasTemplate);

			}
		});

		//////// REQUISIÇÃO: LISTAR PUBLICAÇÕES ////////
		$.ajax({
			url: "/ProjetoTCC/api/PublicacaoControl?acao=listarPublicacoes&idTopico=" + idTopico,
			method: "GET",
			dataType: "json",
			success: function(publicacoes) {

				const publicacaoTemplate = document.querySelector("#publicacao-template").content;
				const $publicacoesList = $("#publicacoes-list");
				$publicacoesList.empty();

				//////// LOOP DE PUBLICAÇÕES ////////
				publicacoes.forEach(publicacao => {
					const $publicacaoClone = publicacaoTemplate.cloneNode(true);
					const $publicacao = $($publicacaoClone).find('.publicacao');

					//////// FORMATAR DATA DA PUBLICAÇÃO ////////
					const dataUTC = new Date(publicacao.data);
					const dia = dataUTC.getUTCDate().toString().padStart(2, '0');
					const mes = (dataUTC.getUTCMonth() + 1).toString().padStart(2, '0');
					const ano = dataUTC.getUTCFullYear().toString().slice(-2);
					const hora = dataUTC.getUTCHours().toString().padStart(2, '0');
					const minutos = dataUTC.getUTCMinutes().toString().padStart(2, '0');
					const dataFormatada = `${dia}/${mes}/${ano} ${hora}:${minutos}`;

					//////// CAMPOS DA PUBLICAÇÃO ////////
					$publicacao.find(".span-publicacao-autor").text("Por: " + publicacao.username);
					$publicacao.find(".span-publicacao-data").text(dataFormatada);
					$publicacao.find(".publicacao-texto").text(publicacao.texto);
					$publicacao.find(".adicionarComentarioForm").data("id-publicacao", publicacao.idPublicacao);

					//////// BOTÃO DE CURTIR PUBLICAÇÃO ////////
					const curtirPublicacaoTemplate = document.querySelector("#curtirPublicacaoTemplate").content;
					const $curtirPublicacaoClone = $(curtirPublicacaoTemplate.cloneNode(true));
					const $curtirPublicacao = $curtirPublicacaoClone.find(".curtirPublicacaoButton");
					const numLikesSpan = $curtirPublicacaoClone.find(".numLikes");
					const $icone = $curtirPublicacao.find("i");

					numLikesSpan.text(publicacao.numLikes);

					$.ajax({
						url: "/ProjetoTCC/api/LikeControl",
						type: "GET",
						dataType: "json",
						data: { 
							id: publicacao.idPublicacao,
							idNome: "idPublicacao",
							tabela: "LikePubli"
						},
						success: function(response) {
							if (response && response.curtido === true) {
								$icone.removeClass("bi-heart").addClass("bi-heart-fill text-danger");
							}
						},
						error: function(err) {
							console.warn("GET LikeControl falhou para", idPublicacaoFechada, err);
						}
					});

					$curtirPublicacao.on("click", function() {

						if ($icone.hasClass("bi-heart")) {
							$.ajax({
								url: "/ProjetoTCC/api/LikeControl",
								type: "POST",
								data: {
									acao: "adicionarLike",
									idPublicacao: publicacao.idPublicacao
								},
								success: function() {
									$icone.removeClass("bi-heart").addClass("bi-heart-fill text-danger");
									publicacao.numLikes += 1;
									numLikesSpan.text(publicacao.numLikes);
								}
							});
						} else {
							$.ajax({
								url: "/ProjetoTCC/api/LikeControl",
								type: "POST",
								data: {
									acao: "removerLike",
									idPublicacao: publicacao.idPublicacao
								},
								success: function() {
									$icone.removeClass("bi-heart-fill text-danger").addClass("bi-heart");
									publicacao.numLikes -= 1;
									numLikesSpan.text(publicacao.numLikes);
								}
							})
						}
					});

					$publicacao.find(".headerPublicacao").append($curtirPublicacaoClone);

					//////// BOTÕES DE AÇÃO DA PUBLICAÇÃO (EDITAR/EXCLUIR) ////////
					if (publicacao.idUsuario == idUsuario) {
						const acoesPublicacaoTemplate = document.querySelector("#acoesPublicacaoTemplate").content;
						const $acoesPublicacaoClone = acoesPublicacaoTemplate.cloneNode(true);
						const $excluirPublicacao = $($acoesPublicacaoClone).find(".excluirPublicacao");
						const $editarPublicacao = $($acoesPublicacaoClone).find(".editarPublicacao");

						// Excluir publicação
						$(document).off("click", ".excluirPublicacaoButton").on("click", ".excluirPublicacaoButton", function() {
							const $publicacao = $(this).closest(".publicacao");
							const idPublicacao = $publicacao.find(".adicionarComentarioForm").data("id-publicacao");

							if (confirm("Tem certeza que deseja excluir essa publicação?")) {
								$.ajax({
									url: '/ProjetoTCC/api/PublicacaoControl?idPublicacao=' + idPublicacao,
									type: 'DELETE',
									success: function() {
										$publicacao.remove();
									},
									error: function(xhr) {
										alert("Erro ao excluir a publicação: " + xhr.status);
									}
								});
							}
						});

						// Editar publicação
						$(document).on("click", ".editarPublicacaoButton", function() {
							const params = new URLSearchParams({ idPublicacao: publicacao.idPublicacao });
							window.location.href = `editarPublicacao.jsp?${params.toString()}`;
						});

						$publicacao.find(".headerPublicacao").append($excluirPublicacao);
						$publicacao.find(".headerPublicacao").append($editarPublicacao);
					}

					//////// CARREGAR COMENTÁRIOS ////////
					if (publicacao.numComentarios > 0) {
						$.ajax({
							url: "/ProjetoTCC/api/ComentarioControl?idPublicacao=" + publicacao.idPublicacao,
							method: "GET",
							dataType: "json",
							success: function(comentarios) {
								const comentarioTemplate = document.querySelector("#comentario-template").content;

								comentarios.forEach(comentario => {
									const $comentarioClone = comentarioTemplate.cloneNode(true);
									const $comentario = $($comentarioClone).find('.comentario');

									// Formatar data do comentário
									const dataComentarioUTC = comentario.data;
									const dataComentarioLocal = new Date(dataComentarioUTC);
									const diaComentario = dataComentarioLocal.getUTCDate().toString().padStart(2, '0');
									const mesComentario = (dataComentarioLocal.getUTCMonth() + 1).toString().padStart(2, '0');
									const anoComentario = dataComentarioLocal.getUTCFullYear().toString().slice(-2);
									const horaComentario = dataComentarioLocal.getUTCHours().toString().padStart(2, '0');
									const minutosComentario = dataComentarioLocal.getUTCMinutes().toString().padStart(2, '0');
									const dataComentarioFormatada = `${diaComentario}/${mesComentario}/${anoComentario} ${horaComentario}:${minutosComentario}`;

									$comentario.find('.comentarioConteudo').html("<div><span class='fw-bolder pe-1'>" + comentario.username + ": </span> " + comentario.texto + "</div>");
									$comentario.find('.comentarioData').text(dataComentarioFormatada);

									// Botão excluir comentário
									if (comentario.idUsuario == idUsuario) {
										const excluirComentarioTemplate = document.querySelector("#excluirComentarioTemplate").content;
										const $excluirComentarioClone = excluirComentarioTemplate.cloneNode(true);
										const $excluirComentario = $($excluirComentarioClone).find(".excluirComentario");

										$excluirComentario.find(".excluirComentarioButton").data("id-comentario", comentario.idComentario);
										$comentario.append($excluirComentario);
									}

									// Botão curtir comentário
									const curtirComentarioTemplate = document.querySelector("#curtirComentarioTemplate").content;
									const $curtirComentarioClone = curtirComentarioTemplate.cloneNode(true);
									const $curtirComentario = $($curtirComentarioClone).find(".curtirComentario");
									$curtirComentario.find(".curtirComentarioButton").data("id-comentario", comentario.idComentario);
									$comentario.append($curtirComentario);

									$publicacao.find(".comentariosDiv").append($comentario);
								});
							}
						});
					}
					$publicacoesList.append($publicacao);
				});
			}
		});
	}

	//////// EXCLUIR COMENTÁRIO ////////
	$(document).on("click", ".excluirComentarioButton", function(e) {
		e.preventDefault();

		const $botao = $(this);
		const idComentario = $botao.data("id-comentario");

		const idPublicacao = $botao.closest(".publicacao").find(".adicionarComentarioForm").data("id-publicacao");

		if (confirm("Tem certeza que deseja excluir este comentário?")) {
			$.ajax({
				url: '/ProjetoTCC/api/ComentarioControl?idComentario=' + idComentario + '&idPublicacao=' + idPublicacao,
				type: 'DELETE',
				success: function() {
					$botao.closest(".comentario").remove();
				},
				error: function(xhr, status, error) {
					alert("Erro ao excluir o comentário: " + xhr.status);
				}
			});
		}
	});

	//////// ADICIONAR COMENTÁRIO ////////
	$(document).on("submit", ".adicionarComentarioForm", function(e) {
		e.preventDefault();
		const form = $(this);
		const formData = form.serialize();
		const idPublicacao = form.data("id-publicacao");

		$.ajax({
			url: "/ProjetoTCC/api/ComentarioControl?idPublicacao=" + idPublicacao,
			type: "POST",
			data: formData,
			success: function() { location.reload() },
			error: function(xhr) {
				alert("Erro ao adicionar comentário: " + xhr.responseText);
			}
		});
	});
});