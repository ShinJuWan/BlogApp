<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form>
		<div class="form-group">
			<input type="text"  vlaue="${sessionScope.principal.username}"  class="form-control" placeholder="Enter username"  readonly="readonly">
		</div>
		<div class="form-group">
			<input type="password"  vlaue="${sessionScope.principal.password}"  class="form-control"  placeholder="Enter password" >
		</div>
		<div class="form-group">
			<input type="email"  vlaue="${sessionScope.principal.eamil}"  class="form-control"  placeholder="Enter email"  >
		</div>
		<button type="submit" class="btn btn-primary">회원수정</button>
	</form>
</div>

<%@ include file="../layout/footer.jsp" %>


    