<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form onsubmit="update(event, ${sessionScope.principal})">	
	  <div class="form-group">
	    <input type="text"  value="${sessionScope.principal.username}" class="form-control" placeholder="Enter username"  maxlength="20" readonly="readonly">
	  </div>
	  <div class="form-group">
	    <input type="email"  id="email"  value="${sessionScope.principal.email}"  class="form-control" placeholder="Enter email"  >
	  </div>
	  <button type="submit" class="btn btn-primary">회원수정</button>
	</form>
</div>

<script>
	async function update(event, id){ 
		   //console.log(event);
		   event.preventDefault();
		   // 주소 : PUT board/3
		   // UPDATE board SET title = ?, content = ? WHERE id = ?
		   let userUpdateDto = {
				   email: document.querySelector("#email").value
		   };
	
			// JSON.stringify(자바스크립트 오브젝트) => 리턴 json 문자열
			// JSON.parse(제이슨 문자열) => 리턴 자바스크립트 함수
	
			
			// 3초
			let response = await fetch("http://localhost:8080/board/"+id, {
				method: "put",
				body: JSON.stringify(userUpdateDto),
				headers: {
					"Content-Type": "application/json; charset=utf-8"
				}
			});
			
			let parseResponse = await response.json(); // 나중에 스프링함수에서 리턴될때 머가 리턴되는지 확인해보자!!
			
			if(parseResponse.code == 1){
				alert("업데이트 성공");
				location.href = "/"
			}else{
				alert("업데이트 실패 : "+parseResponse.msg);
			}
	}
</script>

<%@ include file="../layout/footer.jsp" %>