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
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Lato:wght@300;400;700&family=Rubik:wght@300;400;700&display=swap"
	rel="stylesheet">
</head>
<body>

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

	<div class="container-fluid my-3 px-3">
		<div class="bg-black bg-gradient text-white p-3 rounded-3 d-flex align-items-center position-relative w-100">

			<button type="button" class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
				onclick="window.location.href = 'foruns.jsp'">
				<i class="bi bi-arrow-left px-2 fs-4"></i>
			</button>

			<button type="button" class="bg-dark bg-gradient rounded-2 text-white ms-2 border border-secondary border-opacity-50"
				onclick="window.location.href = './adicionarTopico.jsp?idForum=<%=idForum%>'">
				<i class="bi bi-plus-lg fs-4"></i>
			</button>

			<h5 class="m-0 position-absolute start-50 translate-middle-x w-auto" id='h5'> - Tópicos</h5>

			<button class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50 ms-auto" style="height: 3rem;" type="button" data-bs-toggle="offcanvas"
				data-bs-target="#offcanvasRight">
				<i class="bi bi-list px-2 fs-4"></i>
			</button>
			
		</div>
	</div>

	<div class="container-fluid mt-4">
		<div id="topico-list" class="row" data-idforum="<%=idForum%>" data-idusuario="<%=idUsuario%>">Carregando tópicos...</div>
	</div>

	<template id="topico-template">
		<div class="col-12 col-md-10 col-lg-8 col-xl-6 mb-3 px-3 d-flex divTopico me-1">

				<button type="submit"
					class="d-flex justify-content-center align-items-center flex-grow-1 text-start px-4 py-0 botaoSubmit fs-5 rounded-start-pill bg-secondary-subtle shadow-sm border border-dark border-opacity-25">
					
					<span class="flex-grow-1 fs-5 py-2 px-3 titulo-topico"></span>
					<span class="fs-6 autor-topico"></span>
					<span class="fs-6 px-3">-</span>
					<span class="fs-6 data-topico text-nowrap"></span>
				</button>
				
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

	<div class="offcanvas offcanvas-end h-100" style="width: 15rem"
		tabindex="-1" id="offcanvasRight">
		<div
			class="offcanvas-body d-flex align-items-center justify-content-center">
			<ul class="list-group w-75">
				<li class="list-group-item text-center py-2 rounded-3 "><a
					href="feed.jsp"> <i class="bi bi-house px-2 fs-4"></i>Feed
				</a></li>
				<br>
				<li class="list-group-item text-center py-2 rounded-3"><a
					href="perfil.jsp"> <i class="bi bi-person-circle px-2 fs-4"></i>Perfil
				</a></li>
				<br>
				<li class="list-group-item text-center py-2 rounded-3"><a
					href="foruns.jsp"> <i class="bi bi-book px-2 fs-4"></i>Fóruns
				</a></li>
				<br>
				<li class="list-group-item text-center py-2 rounded-3"><a
					href="login.jsp"> <i class="bi bi-escape px-2 fs-4"></i>Sair
				</a></li>
			</ul>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/topicos.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/canvas.js"></script>

</body>
</html>







