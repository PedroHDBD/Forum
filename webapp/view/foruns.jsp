<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Fóruns</title>
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
	href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900&family=Rubik:ital,wght@0,300..900;1,300..900&display=swap"
	rel="stylesheet">
</head>
<body class="p-3">

	<%
	if (request.getSession().getAttribute("idUsuario") == null) {
		response.sendRedirect("./login.jsp");
	}
	%>

	<div
		class="bg-black bg-gradient text-white p-3 rounded-3 d-flex justify-content-between align-items-center position-relative">

		<div class="d-flex align-items-center">
			<button type="submit"
				class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
				onclick="window.location.href = 'login.jsp'">
				<i class="bi bi-arrow-left px-2 fs-4"></i>
			</button>
		</div>

		<h5 class="m-0 position-absolute start-50 translate-middle-x">Fóruns</h5>

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


	<div id="forum-list">Carregando fóruns...</div>

	<template id="forum-template">
		<div
			class="bg-secondary-subtle my-4 rounded-5 w-50 d-flex align-items-center shadow">
			<button
				class="w-100 text-start px-3 py-2 bg-transparent border border-dark border-opacity-25 forumBtn fs-5 rounded-5">
				<span class="px-3 forum-nome"></span>
			</button>
		</div>
	</template>

	<div class="offcanvas offcanvas-end " tabindex="-1" id="offcanvasRight"
		aria-labelledby="offcanvasRightLabel">
		<div class="offcanvas-header">
			<h5 id="offcanvasRightLabel">Utilitários</h5>
			<button type="button" class="btn-close" data-bs-dismiss="offcanvas"
				aria-label="Fechar"></button>
		</div>
		<div class="offcanvas-body">
			<p>Selecione uma opção:</p>
			<ul class="list-group">
				<li class="list-group-item"><a href="#">Perfil</a></li>
				<li class="list-group-item"><a href="#">Configurações</a></li>
				<li class="list-group-item"><a href="#">Ajuda</a></li>
				<li class="list-group-item"><a href="login.jsp">Sair</a></li>
			</ul>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="./scripts/foruns.js"></script>

</body>
</html>