<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register Page</title>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>
<h1>Register Page</h1>
<form action="/board/register" method="post" enctype="multipart/form-data">
title : <input type="text" name="title" placeholder="제목"><br>
writer : <input type="text" name="writer" value="${ses.id }" readonly="readonly"><br>
content : <br>
<textarea rows="10" cols="50" name="content"></textarea><br>
file : <input type="file" id="file" name="files" multiple style="display:none">
<button type="button" id="trigger">FileUpload</button><br>
<div id="fileZone">

</div>
<button type="submit" id="regBtn">등록</button> <br>
</form>
<a href="/"><button type="button">home</button></a>

<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
</body>
</html>