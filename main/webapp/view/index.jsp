<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Login</title>
</head>
<body>

	<h2>Selecione o Usuário para Logar</h2>

	<form action="login.jsp" method="post">
		<input type="submit" name="usuario1" value="Usuário 1" /> <input
			type="submit" name="usuario2" value="Usuário 2" />
	</form>

	<%
	if (request.getParameter("usuario1") != null) {
		request.getSession().setAttribute("usuarioId", 1);
		response.sendRedirect("foruns.jsp");
	} else if (request.getParameter("usuario2") != null) {
		request.getSession().setAttribute("usuarioId", 2);
		response.sendRedirect("foruns.jsp");
	}
	%>

</body>
</html>