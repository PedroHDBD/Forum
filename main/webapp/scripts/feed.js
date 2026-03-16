$(document).ready(function () {

	PublicacaoService.carregar({
		url: "/ProjetoTCC/api/FeedControl?acao=listarFeed",
		containerSelector: "#feed-publicacoes",
		idUsuario: window.idUsuario
	});

});