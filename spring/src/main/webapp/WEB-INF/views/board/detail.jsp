<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board Detail Page</title>
</head>
<body>
<h1>Board Detail Page</h1>
<c:set var="board" value="${boardDTO.bvo }"></c:set>

<table border="1">
	<tr>
		<th>bno</th>
		<td>${board.bno }</td>
	</tr>
	<tr>
		<th>title</th>
		<td>${board.title }</td>
	</tr>
	<tr>
		<th>writer</th>
		<td>${board.writer }</td>
	</tr>
	<tr>
		<th>read_Count</th>
		<td>${board.read_count }</td>
	</tr>
	<tr>
		<th>rag_date</th>
		<td>${board.reg_date }</td>
	</tr>
	<tr>
		<th>content</th>
		<td>${board.content }</td>
	</tr>
</table>
<!-- file 표현 영역 -->
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
				<div>
					<div>${fvo.file_name }</div>
					${fvo.reg_date }
				</div>
				<span>${fvo.file_size }Byte</span>
			</li>
		</c:forEach>
	</ul>
</div>
<a href="/board/list"><button>목록</button></a>
<!-- 로그인 id와 게시글의 작성자가 같지않으면 수정, 삭제버튼 안보이게 -->
<c:if test="${ses != null && ses.id == board.writer }">
<a href="/board/modify?bno=${board.bno }"><button>수정</button></a>
<a href="/board/delete?bno=${board.bno }"><button>삭제</button></a>
</c:if>

<!-- comment line -->
<div>
<!-- 댓글 작성 라인 -->
<div>
	<span id="cmtWriter"> ${ses.id }</span>
	<input type="text" id="cmtText" placeholder="Test Add Comment">
	<button type="button" id="cmtPostBtn">Post</button>
 </div>

<!-- 댓글 표시 라인 -->
<div>
	<!-- li 하나가 하나의 댓글 객체 -->
	<ul id="cmtListArea">
		<li>
			<div>
			<div>Writer</div>
				Content for Comment
			</div>
			<span>mod_date</span>
		</li>
	</ul>
</div>
</div>
<script type="text/javascript">
const bnoVal = '<c:out value="${boardDTO.bvo.bno}" />';
console.log("bno : "+bnoVal);
</script>
<script type="text/javascript" src="/resources/js/boardComment.js"></script>
<script type="text/javascript">
getCommentList(bnoVal);
</script>







</body>
</html>