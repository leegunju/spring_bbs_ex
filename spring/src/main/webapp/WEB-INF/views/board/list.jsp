<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board List Page</title>
</head>
<body>
<h1>Board List Page</h1>

<!-- search line -->
<div>
<form action="/board/list" method="get">
	<div>
	<c:set value="${ph.pgvo.type }" var="typed"/>
		<select name="type">
 			<option ${typed == null ? 'selected':'' }>Choose...</option>
 			<option value="t" ${typed eq 't' ? 'selected':'' }>Title</option>
 			<option value="c" ${typed eq 'c' ? 'selected':'' }>Content</option>
 			<option value="w" ${typed eq 'w' ? 'selected':'' }>Writer</option>
 			<option value="tc" ${typed eq 'tc' ? 'selected':'' }>Title&Content</option>
 			<option value="cw" ${typed eq 'cw' ? 'selected':'' }>Content&Writer</option>
 			<option value="tw" ${typed eq 'tw' ? 'selected':'' }>Title&Writer</option>
 			<option value="tcw" ${typed eq 'tcw' ? 'selected':'' }>all</option>
		</select>
		<input type="text" name="keyword" placeholder="Search" value="${ph.pgvo.keyword }">
		<input type="hidden" name="pageNo" value="1">
		<input type="hidden" name="qty" value="${ph.pgvo.qty }">
		<button type="submit">Search</button>
		<span>${ph.totalCount }</span>
	</div>
</form>
</div>
<table border="1">
 	<thead>
      <tr>
        <th width="10%">번호</th>
        <th width="30%">제목</th>
        <th width="20%">작성자</th>
        <th width="30%">작성일</th>
        <th width="10%">조회수</th>
       </tr>
    </thead>
    <tbody>
      <c:forEach items="${list}" var="board">
	      <tr>
	        <td>${board.bno}</td>
	        <td><a href="/board/detail?bno=${board.bno }">${board.title}</a></td>
	        <td>${board.writer}</td>
	        <td>${board.reg_date}</td>
	        <td>${board.read_count}</td>
	      </tr>
      </c:forEach>
    </tbody>
</table>
<!-- paging Line -->
<div>
<c:if test="${ph.prev }">
<a href="/board/list?pageNo=${ph.startPage -1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"> ◀ </a>
</c:if>

<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
	 <a href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"> ${i } | </a>
</c:forEach>

<c:if test="${ph.next }">
<a href="/board/list?pageNo=${ph.endPage +1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"> ▶ </a>
</c:if>
</div>
</body>
</html>