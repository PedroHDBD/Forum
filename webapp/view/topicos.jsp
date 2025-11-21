<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Lista de Tópicos</title>
<link rel="stylesheet" href="./css/css.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Rubik:ital,wght@0,300..900;1,300..900&display=swap"
	rel="stylesheet">
</head>
<body class="p-3">

	<%
	request.setCharacterEncoding("UTF-8");
	int idUsuario = -1;

	if (request.getSession().getAttribute("idUsuario") == null) {
		response.sendRedirect("./login.jsp");
	} else {
		idUsuario = (int) request.getSession().getAttribute("idUsuario");
	}

	String idForum = request.getParameter("idForum");
	%>

	<div
		class="bg-black bg-gradient text-white p-3 rounded-3 d-flex justify-content-between align-items-center">
		<div class="d-flex align-items-center">
			<button type="submit"
				class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
				onclick="window.location.href = 'foruns.jsp'">
				<i class="bi bi-arrow-left px-2 fs-4"></i>
			</button>

			<button type="submit"
				class="bg-dark bg-gradient rounded-2 text-white ms-2 border border-secondary border-opacity-50"
				onclick="window.location.href = './adicionarTopico.jsp?idForum=<%=idForum%>'">
				<i class="bi bi-plus-lg fs-4"></i>
			</button>

		</div>
		<h5 class="m-0 position-absolute start-50 translate-middle-x" id='h5'></h5>

		<div class="ms-auto d-flex flex-column text-end text-black">
			<span>­</span> <span>­</span>
		</div>

		<div class="d-flex align-items-center">
			<button
				class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
				type="button" data-bs-toggle="offcanvas"
				data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
				<i class="bi bi-list px-2 fs-4"></i>
			</button>
		</div>

	</div>

	<div id="topico-list" data-idforum="<%=idForum%>"
		data-idusuario="<%=idUsuario%>">Carregando tópicos...</div>

	<template id="topico-template">
		<div
			class="bg-secondary-subtle my-4 rounded-5 w-75 d-flex align-items-center shadow divTopico">

			<div class="flex-grow-1 d-flex">
				<button type="submit"
					class="w-100 text-start px-3 bg-transparent border border-dark border-opacity-25 forumBtn fs-5 d-flex align-items-stretch botaoSubmit"
					style="padding: 0;">
					<span class="flex-grow-1 fs-5 py-2 px-3 titulo-topico"></span>
					<div
						class="d-flex flex-column justify-content-center text-end h-100 fs-1 m-0 p-0">
						<span class="fs-6 autor-topico"></span> <span
							class="fs-6 data-topico text-nowrap"></span>
					</div>
				</button>
			</div>
		</div>
	</template>

	<template id="autor-botoes-template">
		<div class="form-excluir d-flex align-items-stretch excluirTopico">

			<button type="submit"
				class="btn bg-dark-subtle border border-dark border-opacity-25 rounded-0 h-100 excluirTopicoButton">
				<i class="bi bi-trash fs-4"></i>
			</button>
		</div>

		<div class="form-editar d-flex align-items-stretch">
			<button type="submit"
				class="btn bg-dark-subtle border border-dark border-opacity-25 rounded-end-pill h-100 editarTopicoButton">
				<i class="bi bi-pencil fs-4"></i>
			</button>
		</div>
	</template>

	<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight"
		aria-labelledby="offcanvasRightLabel">
		<div class="offcanvas-header">
			<button type="button" class="btn-close" data-bs-dismiss="offcanvas"
				aria-label="Fechar"></button>
		</div>
		<div class="offcanvas-body ">
			<ul class="list-group d-flex align-items-center">
				<li class="list-group-item bg-secondary-subtle my-2 p-0 rounded-5 w-75 mx-auto d-flex align-items-center shadow border border-dark border-opacity-25 justify-content-center">
					<a href="perfil.jsp" class="text-reset text-decoration-none w-100 text-center p-2">Perfil</a>
				</li>
				<li class="list-group-item bg-secondary-subtle my-2 p-0 rounded-5 w-75 mx-auto d-flex align-items-center shadow border border-dark border-opacity-25 justify-content-center">
					<a href="login.jsp" class="text-reset text-decoration-none w-100 text-center p-2">Sair</a>
				</li>
			</ul>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="./scripts/topicos.js"></script>

</body>
</html>