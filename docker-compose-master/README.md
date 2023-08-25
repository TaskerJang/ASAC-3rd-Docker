## Docker Compose Tutorial

### How to test, 구동

`docker-compose up` 명령어 실행 후 아래 API 동작 확인

- `GET /users` : 빈 리스트 조회 확인
- `POST /users` : `{ "name": "World", "age": 12 }`
- `GET /users` : 추가한 유저가 표기되는 리스트 조회 확인
- `GET /users/1` : 추가한 유저 조회 확인

### Version, 버전

- Gradle 8.2.1 (Kotlin 1.8.20, Groovy 3.0.17)
- Java 11

### Purpose, 목적
Spring Boot Web 을 기반으로, JPA 를 통한 PostgresQL 연결 및 Multi-Container 로 구동  

### Description, 설명

* PostgresQL 사용 + JPA (Hibernate)
  * `ddl-auto` 옵션은 `create` 로 설정
    * 매번 Spring Boot 켜질때마다 DROP + CREATE 테이블 실행 
  * 실제 운영 서버에서의 설정은 `ddl-auto` 값은 `validate` or `none` 으로 설정
  * [JPA + PostgresQL 사용 시 ddl-auto 사용 옵션 설명](https://smpark1020.tistory.com/140)
* Docker
  * Dockerfile 내 자체 Gradle 수행을 위해 설정
  * Gradle 명령어 중 `bootRun` 수행하면 아래 절차 수행
    * Dependancy 다운로드 → Java 컴파일 → Spring Boot 로드 (:8080)
* Docker Compose
  * 크게 설정할것이 없다
    * DB 에 해당하는 초기 설정값 (USERNAME, PASSWORD)
    * Spring Boot 구동의 기준이 되는 Dockerfile 지정 및 Container 디펜던시 명시
    * 각각 Container 의 내부 포트 및 외부 포트

### Troubleshooting, 문제 및 해결

#### 1. docker-compose 를 반복 수행해도 재컴파일이 되지않습니다.
예를 들어, Lombok 라이브러리를 Gradle 내 추가했는데, 그 전에 빌드한 도커 이미지를 계속 재사용하여 에러가 사라지지 않는 경우.
Lombok 을 통해 자동생성되어야할 Getter 가 자바 재컴파일로 인식되지 않아 cannot find symbol 오류 발생하는 경우 존재

재컴파일을 하기 위해서는 아래 강제 옵션을 사용해야한다.

`docker-compose up -d --force-recreate --no-deps --build docker_tutorial`

옵션 설명

- `-d` : Detached mode: Run containers in the background
- `--force-recreate` : Recreate containers
- `--build` : Build images before starting containers.
- `--no-deps` : Don't start linked services.

### 2. Error: Conflict. The container name "/postgres_host" 

기존 컨테이너가 아직 지워지지 않아 발생한 문제라 조금 기다렸다가 다시 `docker-compose up` 수행하면 정상 구동
