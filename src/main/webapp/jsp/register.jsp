<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Code Learner</title>
</head>
<link media="all" type="text/css" href="css/home.css" rel="stylesheet">
<jsp:include page="./includes/header.jsp" flush="true" />
<body>
	<div class="container">
		<h2 id="main-title" style="text-align: center">Sign Up</h2>
		<div style="padding: 70px 0; text-align:center;margin:0 auto;">
			<table width="50%" style="padding: 70px 0; margin:0 auto;">
				<tr>
					<td><input id="firstName" type="text" placeholder="First Name"></td>
				</tr>

				<tr>
					<td><input id="lastName" type="text" placeholder="Last Name"></td>
				</tr>

				<tr>
					<td><input id="email" type="text" placeholder="Email"></td>
				</tr>

				<tr>
					<td>
						<select id="selectRole" name="role">
							<option value="Student" selected>Student</option>
							<option value="Instructor">Instructor</option>
						</select>
						<%--<input id="userRole" type="text" placeholder="Role">--%>
					</td>
				</tr>

				<tr>
					<td><input id="userId" type="text" placeholder="User Id"></td>
				</tr>

				<tr>
					<td><input id="password" type="password" placeholder="Password"></td>
				</tr>
			</table>
			<br><button type="button" id="registerUser" class="btn btn-primary">Sign Up</button>
		</div>
	</div>
</body>
<jsp:include page="./includes/footer.jsp" flush="true" />
<script type="text/javascript" src="js/home.js"></script>
<script type="text/javascript" src="js/urls.js"></script>
</html>