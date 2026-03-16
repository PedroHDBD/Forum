<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
		<div class="bg-black bg-gradient text-white p-3 rounded-3 d-flex align-items-center position-relative w-100">
		
			<button type="submit"
				class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
				id='voltar'>
				<i class="bi bi-arrow-left px-2 fs-4"></i>
			</button>
			
		<h5 class="m-0 position-absolute start-50 translate-middle-x text-nowrap" id='h5'></h5>

			<button class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50 ms-auto" style="height: 3rem;" type="button" data-bs-toggle="offcanvas"
				data-bs-target="#offcanvasRight">
				<i class="bi bi-list px-2 fs-4"></i>
			</button>
		
		</div>
	</div>

	<div
		class='d-flex flex-column justify-content-center align-items-center m-0 p-3 w-100'>

		<form class='w-75 bg-dark-subtle py-3 px-4 rounded-3 shadow'>
			<label for="titulo" class='fs-5 mb-1'>Título: </label> <input
				type="text" name="titulo" autocomplete="off" maxlength="60" required
				class='w-100 rounded-3 py-1 px-2 border border-dark border-opacity-25'
				placeholder='Máximo: 60 caracteres' id='tituloAtual'><br>
			<input type="submit" name="submit" value="Editar"
				class='rounded-3 py-1 px-3 border border-dark border-opacity-25 mt-2 bg-secondary-subtle fs-5 editarTopicoButton'>
		</form>

	</div>

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
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/editarTopico.js"></script>
</body>
</html>