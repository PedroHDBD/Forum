<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Lista de Publicações</title>
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

	String idTopico = request.getParameter("idTopico");
	%>

	<script>
		const idUsuario =
	<%=idUsuario%></script>

	<div
		class="bg-black bg-gradient text-white p-3 rounded-3 d-flex justify-content-between align-items-center"
		id='header'>
		<div class="d-flex align-items-center">

			<button type="submit"
				class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
				id="voltar">
				<i class="bi bi-arrow-left px-2 fs-4"></i>
			</button>

			<button type="submit"
				class="bg-dark bg-gradient rounded-2 text-white ms-2 border border-secondary border-opacity-50"
				onclick="window.location.href = './adicionarPublicacao.jsp?idTopico=<%=idTopico%>'">
				<i class="bi bi-plus-lg fs-4"></i>
			</button>

		</div>

		<div class="ms-auto d-flex flex-column text-end me-3">
			<span id='spanNome'>­</span> <span id='spanData'>­</span>
		</div>

		<template id='canvas-template'>
			<div class="d-flex align-items-center ps-2">
				<button
					class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
					type="button" data-bs-toggle="offcanvas"
					data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
					<i class="bi bi-list px-2 fs-4"></i>
				</button>
			</div>
		</template>

		<h5
			class='m-0 position-absolute start-50 translate-middle-x w-75 fw-medium h5Header'></h5>

	</div>

	<template id='publicacao-template'>
		<div class='my-5 w-75 shadow rounded-3 publicacao'>

			<div
				class=' p-3 m-0 bg-dark bg-gradient text-white rounded-3 rounded-bottom-0 fs-3 d-flex justify-content-between align-items-center headerPublicacao'>
				<span class='span-publicacao-autor'></span> <span
					class='fw-normal m-0 flex-grow-1 ms-5 float-end span-publicacao-data fs-6'></span>
			</div>

			<h5
				class='p-3 m-0 bg-dark-subtle fw-normal py-4 text-break publicacao-texto'></h5>

			<div class='bg-body-secondary p-3'>Comentários:</div>
			
			<div class='comentariosDiv p-1 bg-body-secondary'></div>

			<div class='adicionarComentario d-flex align-items-stretch w-100'>
				<form class='d-flex w-100 adicionarComentarioForm'>
					<input type='text' name='texto' maxlength='1000' required
						placeholder='Digite seu comentário...' autocomplete='off'
						class='p-2 border-dark border-opacity-50 flex-grow-1'
						style='border-radius: 0 0 0 0.375rem'>
					<button type='submit'
						class='btn-submit px-3 bg-info-subtle bg-gradient border border-black border-opacity-10 fs-6 custom-rounded adicionarComentarioButton'>
						<i class='bi bi-send fs-5'></i>
					</button>
				</form>
			</div>
		</div>
	</template>

	<template id='comentario-template'>
		<div class='comentario d-flex'>
			<h6
				class='comentarioConteudo text-break fw-normal w-100 p-3 m-0 bg-body-tertiary border-top border-start border-bottom border-black border-opacity-10 d-flex justify-content-between align-items-center'></h6>
			<h6
				class='comentarioData fw-light m-0 p-2 bg-body-tertiary border-top border-end border-bottom border-black border-opacity-10 text-nowrap fs-6 text-secondary'></h6>
		</div>
	</template>

	<template id='curtirComentarioTemplate'>
		<div
			class='d-flex border-top border-bottom border-black border-opacity-10 curtirComentario'>
			<button type="submit"
				class="d-flex px-3 forumBtn fs-6 h-100 align-items-center curtirComentarioButton w-100">
				<span class="numLikes fs-5 mb-1 pe-1">0</span> <i
					class="bi bi-heart fs-5"></i>
			</button>
		</div>
	</template>

	<template id='excluirComentarioTemplate'>
		<div
			class='d-flex border-top border-bottom border-black border-opacity-10 excluirComentario'>
			<button type='submit'
				class='px-3 forumBtn fs-6 h-100 excluirComentarioButton'>
				<i class='bi bi-trash fs-5'></i>
			</button>
		</div>
	</template>

	<template id='acoesPublicacaoTemplate'>
		<div class='excluirPublicacao'>
			<button type='submit'
				class='p-2 border border-dark border-opacity-25 rounded-start-pill fs-6 bg-dark-subtle excluirPublicacaoButton ms-2'>
				<i class='bi bi-trash fs-5 px-1'></i>
			</button>
		</div>

		<div class='editarPublicacao'>
			<button type='submit'
				class='p-2 border border-dark border-opacity-25 rounded-end-pill fs-6 bg-dark-subtle editarPublicacaoButton'>
				<i class='bi bi-pencil fs-5 px-1'></i>
			</button>
		</div>
	</template>

	<template id='curtirPublicacaoTemplate'>
		<div class='curtirPublicacao'>
			<button type='submit'
				class='p-2 border border-dark border-opacity-25 rounded-pill fs-6 bg-dark-subtle curtirPublicacaoButton'>
				<span class='numLikes fs-5 px-1'></span> <i
					class='bi bi-heart fs-5 pe-1'></i>
			</button>
		</div>
	</template>

	<template id='acoesTopicoTemplate'>
		<div class='excluirTopico d-flex align-items-stretch'>
			<button type='submit'
				class='p-1 border border-dark border-opacity-25 rounded-start-pill fs-6 bg-dark-subtle excluirTopicoButton'>
				<i class='bi bi-trash fs-6 px-1'></i>
			</button>
		</div>

		<div class='editarTopico d-flex align-items-stretch'>
			<button type='submit'
				class='p-1 border border-dark border-opacity-25 rounded-end-pill fs-6 bg-dark-subtle editarTopicoButton'>
				<i class='bi bi-pencil fs-6 px-1'></i>
			</button>
		</div>
	</template>

	<div id="publicacoes-list"
		class='d-flex flex-column justify-content-center align-items-center m-0 p-3 w-100'>Carregando
		publicações...</div>


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
	<script src="./scripts/publicacoes.js"></script>
</body>
</html>