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
	href="https://fonts.googleapis.com/css2?family=Lato:wght@300;400;700;900&family=Rubik:wght@300..900&display=swap"
	rel="stylesheet">
</head>

<body>

	<%
	if (request.getSession().getAttribute("idUsuario") == null) {
		response.sendRedirect("./login.jsp");
	}
	%>

	<!-- ================= HEADER ================= -->
	<div class="container-fluid my-3 px-3">
		<div class="bg-black bg-gradient text-white p-3 rounded-3 d-flex align-items-center position-relative w-100">

			<button type="button"
				class="bg-dark bg-gradient rounded-2 text-white  border border-secondary border-opacity-50"
				onclick="window.location.href = 'login.jsp'">
				<i class="bi bi-arrow-left px-2 fs-4"></i>
			</button>

			<h5 class="m-0 position-absolute start-50 translate-middle-x">
				Fóruns</h5>

			<button
				class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50 ms-auto"
				style="height: 3rem;" type="button" data-bs-toggle="offcanvas"
				data-bs-target="#offcanvasRight">
				<i class="bi bi-list px-2 fs-4"></i>
			</button>

		</div>
	</div>

	<!-- ================= LISTA DE FÓRUNS (GRID) ================= -->
	<div class="container-fluid mt-4">
		<div id="forum-list" class="row">Carregando fóruns...</div>
	</div>

	<!-- ================= TEMPLATE ================= -->
	<template id="forum-template">
		<div class="col-12 col-md-10 col-lg-8 col-xl-6 mb-3 px-3 d-flex">

			<button
				class="flex-grow-1 text-start px-4 py-2 forumBtn fs-5 rounded-start-pill bg-secondary-subtle shadow-sm border border-dark border-opacity-25">
				<span class="forum-nome fw-medium"></span>
			</button>

			<button type="button"
				class="btn bg-dark-subtle border-start seguirBtn border-dark border-opacity-25 rounded-end-pill px-3">
				<i class="bi bi-award fs-4"></i>
			</button>

		</div>
	</template>

	<!-- ================= OFFCANVAS ================= -->
	<div class="offcanvas offcanvas-end h-100" style="width: 15rem"
		tabindex="-1" id="offcanvasRight">

		<div
			class="offcanvas-body d-flex align-items-center justify-content-center">

			<ul class="list-group w-75">

				<li class="list-group-item text-center py-2 rounded-3"><a
					href="feed.jsp"> <i class="bi bi-house px-2 fs-4"></i> Feed
				</a></li>

				<br>

				<li class="list-group-item text-center py-2 rounded-3"><a
					href="perfil.jsp"> <i class="bi bi-person-circle px-2 fs-4"></i>
						Perfil
				</a></li>

				<br>

				<li class="list-group-item text-center py-2 rounded-3"><a
					href="foruns.jsp"> <i class="bi bi-book px-2 fs-4"></i> Fóruns
				</a></li>

				<br>

				<li class="list-group-item text-center py-2 rounded-3"><a
					href="login.jsp"> <i class="bi bi-escape px-2 fs-4"></i> Sair
				</a></li>

			</ul>

		</div>

	</div>

	<!-- ================= SCRIPTS ================= -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/foruns.js"></script>

</body>
</html>
