<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

</head>
<body>
<h1>Board Modify Page</h1>
<c:set var="board" value="${boardDTO.bvo }" />
<form action="/board/modify" method="post" enctype="multipart/form-data">
bno : <input type="text" name="bno" value="${board.bno }" readonly="readonly"><br>
title : <input type="text" name="title" value="${board.title }"><br>
writer : <input type="text" name="writer" value="${board.writer }" readonly="readonly"><br>
reg_date : <input type="text" name="reg_date" value="${board.reg_date }"><br>
content : <br>
<textarea rows="10" cols="30" name="content">${board.content }</textarea><br>

<!-- 파일 표시라인 -->
<c:set var="flist" value="${boardDTO.flist }"></c:set>

<div>
	<ul>
		<c:forEach items="${flist }" var="fvo">
			<li>
				<c:choose>
					<c:when test="${fvo.file_type >0 }">
						<div>
							<img alt="없음" src="/upload/${fn: replace(fvo.save_dir, '\\','/')}/${fvo.uuid}_th_${fvo.file_name}">
						</div>
					</c:when>
					<c:otherwise>
						<div>
							<!-- 클립모양 같은 파일 아이콘 모양 값을 넣을 수 있음. -->
						</div>
					</c:otherwise>
				</c:choose>
					<div>${fvo.file_name }</div>
					<button type="button" class="file-x" data-uuid="${fvo.uuid }">X</button>
			</li>
		</c:forEach>
	</ul>
</div>
<!-- 파일 등록라인 -->
file : <input type="file" id="file" name="files" multiple style="display:none">
<button type="button" id="trigger">FileUpload</button><br>
<div id="fileZone">

</div>
<button type="submit" id="regBtn">수정</button> <br>
</form>
<a href="/board/list"><button type="button">목록</button> </a>
<a href="/"><button type="button">home</button> </a>
<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
<script type="text/javascript" src="/resources/js/boardModify.js"></script>
</body>
</html>