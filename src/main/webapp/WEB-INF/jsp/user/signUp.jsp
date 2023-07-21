<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="sign-up-box">
		<h1 class="m-4 font-weight-bold">회원가입</h1>
		<form id="signUpForm" method="post" action="/user/sign_up">
			<span class="sign-up-subject">ID</span>
			<%-- 인풋 옆에 중복확인 버튼을 옆에 붙이기 위해 div 만들고 d-flex --%>
			<div class="d-flex ml-3 mt-3">
				<input type="text" id="loginId" name="loginId" class="form-control col-6" placeholder="ID를 입력해주세요">
				<button type="button" id="loginIdCheckBtn" class="btn btn-success">중복확인</button>
			</div>

			<%-- 아이디 체크 결과 --%>
			<div class="ml-3 mb-3">
				<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
				<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
				<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
			</div>

			<span class="sign-up-subject">Password</span>
			<div class="m-3">
				<input type="password" name="password" class="form-control col-6" placeholder="비밀번호를 입력하세요">
			</div>

			<span class="sign-up-subject">Confirm password</span>
			<div class="m-3">
				<input type="password" name="confirmPassword" class="form-control col-6" placeholder="비밀번호를 입력하세요">
			</div>

			<span class="sign-up-subject">Name</span>
			<div class="m-3">
				<input type="text" name="name" class="form-control col-6" placeholder="이름을 입력하세요">
			</div>

			<span class="sign-up-subject">이메일</span>
			<div class="m-3">
				<input type="text" name="email" class="form-control col-6" placeholder="이메일을 입력하세요">
			</div>

			<br>
			<div class="d-flex justify-content-center m-3">
				<button type="submit" id="signUpBtn" class="btn btn-info">가입하기</button>
			</div>
		</form>
	</div>
</div>

<script>
$(document).ready(function() {
	// 중복확인 버튼 클릭
	$("#loginIdCheckBtn").on('click', function() {
		//alert("클릭");
		
		// 경고 문구 초기화
		$('#idCheckLength').addClass('d-none');
		$('#idCheckDuplicated').addClass('d-none');
		$('#idCheckOk').addClass('d-none');
		
		let loginId = $('#loginId').val().trim();
		if (loginId.length < 4) {
			$('#idCheckLength').removeClass('d-none');
			return;
		}
		
		// AJAX 통신 - 중복확인
		$.ajax({
			// request
			url:"/user/is_duplicated_id"
			, data: {"loginId":loginId}
			
			// response
			, success: function(data) {
				if (data.isDuplicatedId) {
					// 중복
					$('#idCheckDuplicated').removeClass('d-none');
				} else {
					// 중복 아님 => 사용 가능
					$('#idCheckOk').removeClass('d-none');
				}
			}
			, error: function(request, status, error) {
				alert("중복확인에 실패했습니다.");
			}
		});
	});
	
	// 회원가입
	$('#signUpForm').on('submit', function(e) {
		e.preventDefault(); // 서브밋 기능 중단
		
		// validation
		let loginId = $('input[name=loginId]').val().trim();
		let password = $("input[name=password]").val();
		let confirmPassword = $('input[name=confirmPassword]').val();
		let name = $('input[name=name]').val().trim();
		let email = $('input[name=email]').val().trim();
		
		if (!loginId) {
			alert("아이디를 입력하세요");
			return false;
		}
		if (!password || !confirmPassword) {
			alert("비밀번호를 입력하세요");
			return false;
		}
		if (password != confirmPassword) {
			alert("비밀번호가 일치하지 않습니다");
			return false;
		}
		if (!name) {
			alert("이름을 입력하세요");
			return false;
		}
		if (!email) {
			alert("이메일을 입력하세요");
			return false;
		}
		// 아이디 중복확인 완료 됐는지 확인 -> idCheckOk d-none이 있으면 얼럿을 띄워야함
		if ($('#idCheckOk').hasClass('d-none')) {
			alert("아이디 중복확인을 다시 해주세요");
			return false;
		}
		
		let url = $(this).attr('action');
		console.log(url);
		let params = $(this).serialize(); // 폼태그에 있는 name 속성-값들로 파라미터 구성
		console.log(params);
		
		$.post(url, params)   // request
		.done(function(data) {
			// response
			if (data.code == 1) {
				alert("가입을 환영합니다! 로그인을 해주세요.");
				location.href = "/user/sign_in_view"; // 로그인 화면으로 이동
			} else {
				// 로직 실패
				alert(data.errorMessage);
			}
		});
	});
});
</script>