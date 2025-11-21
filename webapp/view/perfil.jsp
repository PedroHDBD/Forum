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
<body class="p-3">

	<%
	request.setCharacterEncoding("UTF-8");
	int idUsuario = -1;

	if (request.getSession().getAttribute("idUsuario") == null) {
		response.sendRedirect("./login.jsp");
	} else {
		idUsuario = (int) request.getSession().getAttribute("idUsuario");
	}
	%>

	<div
		class="bg-black bg-gradient text-white p-3 rounded-3 d-flex justify-content-between align-items-center">
		<div class="d-flex align-items-center">
			<button type="submit"
				class="bg-dark bg-gradient rounded-2 text-white border border-secondary border-opacity-50"
				onclick="window.location.href = 'foruns.jsp'">
				<i class="bi bi-house px-2 fs-4"></i>
			</button>

		</div>

		<h5 class="m-0 position-absolute start-50 translate-middle-x" id='h5'>Perfil</h5>

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

	<div class="d-flex mt-3">
		<div class="card rounded-circle overflow-hidden" style="width: 15vw; aspect-ratio: 1/1; cursor: pointer;" id="cardImagemUsuario">
			<form id="atualizarFoto" enctype="multipart/form-data">
				<input type="file" id="inputImagem" name="imagem" accept="image/*"
					hidden> <img id="imagem" class="foto-perfil">
			</form>
		</div>


		<div class="modal fade" id="modalCrop" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered modal-lg">
				<div class="modal-content">

					<div class="modal-header">
						<h5 class="modal-title">Editar foto de perfil</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<div class="modal-body">
						<div class="crop-container">
							<img id="cropperImg" class="img-fluid">
						</div>
					</div>

					<div class="modal-footer">
						<button id="cropCancel" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancelar</button>
						<button id="cropConfirm" class="btn btn-primary">Confirmar</button>
					</div>

				</div>
			</div>
		</div>

		<div class="d-flex flex-column justify-content-between p-3 w-100">

			<div class='d-flex justify-content-between w-50'>
				<div
					class='bg-secondary-subtle w-100 rounded-start-pill p-2 d-flex justify-content-between align-items-center m-0'>
					<span class='mx-2 fw-bold'>Nome: <span id='nome'
						class='fw-bolder'></span></span>
				</div>
				<span class='form-editar editarUsername'>
					<button type="submit"
						class="btn bg-dark-subtle border-opacity-25 rounded-end-pill editarUsernameButton">
						<i class="bi bi-pencil fs-5 px-1"></i>
					</button>
				</span>
			</div>

			<div class='d-flex justify-content-between w-50'>
				<div
					class='bg-secondary-subtle w-100 rounded-start-pill p-2 d-flex justify-content-between align-items-center m-0'>
					<span class='mx-2 fw-bold'>Nome de usuário: <span
						id='username' class='fw-bolder'></span></span>
				</div>
				<span class='form-editar editarUsername'>
					<button type="submit"
						class="btn bg-dark-subtle border-opacity-25 rounded-end-pill editarUsernameButton">
						<i class="bi bi-pencil fs-5 px-1"></i>
					</button>
				</span>
			</div>

			<div
				class='bg-secondary-subtle w-50 rounded-pill p-2 d-flex justify-content-between'>
				<span class='mx-2 fw-bold'>Entrou em: <span id='data'
					class='fw-bolder'></span></span>
			</div>

		</div>
	</div>

	<div class="offcanvas offcanvas-end " tabindex="-1" id="offcanvasRight"
		aria-labelledby="offcanvasRightLabel">
		<div class="offcanvas-header">
			<button type="button" class="btn-close" data-bs-dismiss="offcanvas"
				aria-label="Fechar"></button>
		</div>
		<div class="offcanvas-body">
			<ul class="list-group">
				<li class="list-group-item"><a href="perfil.jsp">Perfil</a></li>
				<li class="list-group-item"><a href="login.jsp">Sair</a></li>
			</ul>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="./scripts/perfil.js"></script>

</body>
</html>