<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
   <form action="/user/1" method="post">
     <div class="form-group">
       <input type="text"   name="username"  value="${sessionScope.principal.username}" class="form-control" placeholder="Enter username"  maxlength="20" readonly="readonly">
     </div>
     <div class="form-group">
       <input type="password"  name="password"  value=""  class="form-control" placeholder="Enter password"  maxlength="20">
     </div>
     <div class="form-group">
       <input type="email"  name="email"  value="${sessionScope.principal.email}"  class="form-control" placeholder="Enter email"  >
     </div>
     <button type="submit" class="btn btn-primary">회원수정</button>
   </form>
</div>

<%@ include file="../layout/footer.jsp" %>