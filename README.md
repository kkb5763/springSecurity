# springSecurity
스프링 시큐리티

사용 기술 : jpa, mybatis 병행

swagger 접속 경로
http://localhost:8080/swagger-ui/index.html


(서블릿)localhost:8080/join >> 회원가입 -> 입력 정보 [id, 권한]

(서블릿)localhost:8080/login >> 로그인 api 호출 /api/login

(서블릿)localhost:8080/index >> token 정보 기반 페이지 구성
                       - if token.exists => login 기능 활성화
                       - if token.not    => api 노출 ( page_profile / page_meeting / page_logout / page_admin )