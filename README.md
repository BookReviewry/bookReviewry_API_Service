#### 프로젝트 시작
       * `cd back && ./mvnw spring-boot:run`

#### 프로젝트 종료
    * 직접 종료하기
        * `netstat -nlp | grep :8080`을 입력하여 포트 확인
        * `kill -9 ${port}`₩