<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SignUp Page</title>
</head>
<body>
<h1>SignUp Page</h1>

<form action="/member/signup" method="post">
	id : <input type="text" name="id" placeholder="아이디"><br>
	password : <input type="password" name="pw" placeholder="비밀번호"><br>
	name : <input type="text" name="name" placeholder="이름"><br>
	email : <input type="text" name="email" placeholder="이메일"><br>
	home : <input type="text" name="home" placeholder="주소"><br>
	age : <input type="text" name="age" placeholder="나이"><br>
	<button>회원가입</button><br>
</form>
<script type="text/javascript">
const msg_signup = '<c:out value="${msg_signup}"/>';
console.log(msg_signup);
if(msg_signup === '0'){
	alert("중복된 아이디입니다.")
}
</script>
</body>
</html>