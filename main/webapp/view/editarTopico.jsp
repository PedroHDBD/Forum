<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Novo tópico</title>
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
<body>

	<%
	request.setCharacterEncoding("UTF-8");

	if (request.getSession().getAttribute("idUsuario") == null) {
		response.sendRedirect("./login.jsp");
	}

	String idTopico = request.getParameter("idTopico");
	%>

	<div class="container-fluid my-3 px-3">
		<div
			class="bg-black bg-gradient text-white p-3 rounded-3 d-flex flex-column"
			id="header">

			<div class="d-flex align-items-center">

				<div class="d-flex gap-2">
					<button type="button"
						class="bg-dark rounded-2 text-white border border-secondary border-opacity-50 p-1 p-md-2"
						id="voltar">
						<i class="bi bi-arrow-left fs-6 fs-md-5"></i>
					</button>
				</div>

				<div class="d-flex ms-auto align-items-center gap-1" id="acoes">
					<button
						class="bg-dark rounded-2 text-white border border-secondary border-opacity-50 p-1 p-md-2"
						type="button" data-bs-toggle="offcanvas"
						data-bs-target="#offcanvasRight">
						<i class="bi bi-list fs-6 fs-md-5"></i>
					</button>
				</div>

			</div>

			<h5 class="mb-0 text-center fw-medium fs-5 text-break" id='h5'></h5>

		</div>
	</div>

	<div class="row justify-content-center m-0 p-1">
    
    <div class="col-12 col-md-10 col-lg-8 col-xl-6">

		<form class='bg-dark-subtle py-2 px-3 rounded-3 shadow w-100'>
			<label for="titulo" class='fs-5 mb-1'>Título: </label> <input
				type="text" name="titulo" autocomplete="off" maxlength="60" required
				class='w-100 rounded-3 py-1 px-2 border border-dark border-opacity-25'
				placeholder='Máximo: 60 caracteres' id='tituloAtual'><br>
			<input type="submit" name="submit" value="Editar"
				class='rounded-3 py-1 px-3 border border-dark border-opacity-25 mt-2 bg-secondary-subtle fs-5 editarTopicoButton'>
		</form>

	</div>
</div>

	<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight">
		<div class="offcanvas-body p-3">

			<div class="list-group list-group-flush">

				<a href="feed.jsp"
					class="list-group-item list-group-item-action d-flex align-items-center gap-3 py-3 rounded-3">
					<i class="bi bi-house fs-5"></i> <span>Feed</span>
				</a> <a href="perfil.jsp"
					class="list-group-item list-group-item-action d-flex align-items-center gap-3 py-3 rounded-3 mt-2">
					<i class="bi bi-person-circle fs-5"></i> <span>Perfil</span>
				</a> <a href="foruns.jsp"
					class="list-group-item list-group-item-action d-flex align-items-center gap-3 py-3 rounded-3 mt-2">
					<i class="bi bi-book fs-5"></i> <span>Fóruns</span>
				</a> <a href="login.jsp"
					class="list-group-item list-group-item-action d-flex align-items-center gap-3 py-3 rounded-3 mt-4 text-danger">
					<i class="bi bi-box-arrow-right fs-5"></i> <span>Sair</span>
				</a>

			</div>

		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/scripts/editarTopico.js"></script>
</body>
</html>