<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Perfil</title>
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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.13/cropper.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.13/cropper.min.js"></script>
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
	%>

	<div class="container-fluid my-3 px-3">
		<div
			class="bg-black bg-gradient text-white p-3 rounded-3 d-flex flex-column"
			id="header">


			<div class="d-flex align-items-center">

				<div class="d-flex gap-2">
					<button type="button"
						class="bg-dark rounded-2 text-white border border-secondary border-opacity-50 p-1 p-md-2"
						id="voltar" onclick="window.location.href = 'feed.jsp'">
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

			<h5 class="mb-0 text-center fw-medium fs-5 h5Header">Perfil</h5>

		</div>
	</div>

	<div class="container mt-3">
		<div class="row align-items-center d-flex justify-content-center">

			<!-- FOTO -->
			<div
				class="col-12 col-md-3 d-flex justify-content-center mb-3 mb-md-0">
				<div class="col-6 col-md-12">

					<div class="card rounded-circle overflow-hidden w-100"
						style="aspect-ratio: 1/1; cursor: pointer;" id="cardImagemUsuario">

						<form id="atualizarFoto" enctype="multipart/form-data">
							<input type="file" id="inputImagem" name="imagem"
								accept="image/*" hidden> <img id="imagem"
								class="w-100 h-100 object-fit-cover">
						</form>

					</div>

				</div>
			</div>

			<!-- MODAL CROP -->
			<div class="modal fade" id="modalCrop" tabindex="-1"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered modal-lg">
					<div class="modal-content">

						<div class="modal-header">
							<h5 class="modal-title">Editar foto de perfil</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
						</div>

						<div class="modal-body">
							<div class="crop-container text-center">
								<img id="cropperImg" class="img-fluid">
							</div>
						</div>

						<div class="modal-footer">
							<button id="cropCancel" class="btn btn-secondary"
								data-bs-dismiss="modal">Cancelar</button>
							<button id="cropConfirm" class="btn btn-primary">
								Confirmar</button>
						</div>

					</div>
				</div>
			</div>
		</div>

		<!-- DADOS -->
		<div class="row align-items-center d-flex justify-content-center mt-3">

			<div class="col-12 col-md-9">

				<!-- NOME -->
				<div
					class="row mb-3 g-0 align-items-stretch d-flex justify-content-center">
					<div class="col-9 col-md-7">

						<form
							class="bg-secondary-subtle rounded-pill p-3 d-flex align-items-center h-100">
							<span class="fw-bold me-1 mb-0 text-nowrap"> Nome:</span> <input
								type="text" id="nome"
								class="form-control border-0 bg-transparent fw-bolder p-0 flex-grow-1">
						</form>

					</div>

				</div>

				<div
					class="row mb-3 g-0 align-items-stretch d-flex justify-content-center">
					<div class="col-9 col-md-7">

						<form
							class="bg-secondary-subtle rounded-pill p-3 d-flex align-items-center h-100">
							<span class="fw-bold me-1 mb-0 text-nowrap"> Nome de
								usuário:</span> <input type="text" id="username"
								class="form-control border-0 bg-transparent fw-bolder p-0 flex-grow-1">
						</form>

					</div>
				</div>

				<div class="row mb-3 justify-content-center">
					<div class="col-4 col-md-2 d-flex align-items-center">
						<button type="button" class="btn bg-dark-subtle w-100 rounded-pill editarUsernameButton">Atualizar
							<i class="bi bi-pencil"></i>
						</button>
					</div>
				</div>

				<!-- DATA -->
				<div class="row d-flex justify-content-center">
					<div class="col-7 col-md-5">
						<div class="bg-secondary-subtle rounded-pill p-2">
							<span class="fw-bold ms-2"> Entrou em: <span id="data"
								class="fw-bolder"></span>
							</span>
						</div>
					</div>
				</div>
			</div>
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
	<script src="${pageContext.request.contextPath}/scripts/perfil.js"></script>
	<script
		src="${pageContext.request.contextPath}/scripts/editarPerfil.js"></script>
</body>
</html>