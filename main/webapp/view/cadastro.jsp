<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cadastro</title>
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
<body class="p-3 d-flex justify-content-center align-items-center" style="height: 100vh;">

	<%
	if (session != null) {
		session.invalidate();
	}
	%>

	<div class="card p-4 shadow px-5 w-25">
		<h2 class="text-center mb-4">Cadastro</h2>
		<form action="/ProjetoTCC/api/CadastroControl" method="post">
			<div class="mb-3">
				<label for="nome" class="form-label">Nome:</label>
                <input type="text" class="form-control" id="nome" name="nome" required autocomplete="off">
			</div>
			<div class="mb-3">
				<label for="username" class="form-label">Nome de usuário:</label>
                <input type="text" class="form-control" id="nome" name="username" required autocomplete="off">
                <%
				if ("1".equals(request.getParameter("erroUsername"))) {
				%>
				<span class="text-danger">Nome de usuário já existe</span>
				<%
				}
				%>
			</div>
			<div class="mb-3">
				<label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" name="email" required autocomplete="off">
				<%
				if ("1".equals(request.getParameter("erroEmail"))) {
				%>
				<span class="text-danger">Email já cadastrado</span>
				<%
				}
				%>
			</div>
			<div class="mb-3">
				<label for="senha" class="form-label">Senha:</label>
                <input type="password" class="form-control" id="senha" name="senha" required autocomplete="off">
			</div>
			<button type="submit" class="btn btn-primary w-100">Cadastrar</button>
		</form>

		<p class="mt-3 text-center">
			Já tem conta? <a href="login.jsp">Faça o login</a>
		</p>
	</div>
</body>
</html>