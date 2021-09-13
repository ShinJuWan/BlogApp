<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<div class="container">
	<form action="/board"  method="post">
	  <div class="form-group">
	    <input type="text" name="title" class="form-control" placeholder="Enter title"  >
	  </div>
	  <div class="form-group">
	  	<textarea id="summernote" class="form-control" rows="5"  name="content"></textarea>
	  </div>
	  <button type="submit" class="btn btn-primary">글쓰기</button>
	</form>
</div>

  <script>
        $('#summernote').summernote({
             height: 350
        });
  </script>
<%@ include file="../layout/footer.jsp" %>