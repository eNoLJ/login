package com.login.web;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@DisplayName("유저 관련 API 테스트")
@ExtendWith(SoftAssertionsExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @InjectSoftAssertions
    SoftAssertions softly;

    @LocalServerPort
    int port;

    Map<String, String> headers;
    Map<String, String> body;
    ExtractableResponse<Response> response;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        headers = new HashMap<>();
        body = new HashMap<>();
    }

    @Test
    @DisplayName("회원가입 기능")
    void signUp() {
        body.put("email", "derosatam76@naver.com");
        body.put("password", "123456");
        body.put("name", "enolj");

        // 회원가입 성공
        response = request("POST", "/api/signUp", headers, body);

        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // 회원가입 실패(중복 된 이메일)
        response = request("POST", "/api/signUp", headers,  body);

        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("로그인 기능")
    void login() {
        // 로그인 성공
        body.put("email", "derosatam76@gmail.com");
        body.put("password", "asdzxc");

        response = request("POST", "/api/login", headers, body);

        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softly.assertThat(response.jsonPath().getString("token")).isNotNull();

        // 로그인 실패
        body.put("email", "derosatam76@mumu");
        body.put("password", "asdzxc");

        response = request("POST", "/api/login", headers,  body);

        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("로그아웃 기능")
    void logout() {
        // 로그아웃 성공
        headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsb2dpblNlcnZpY2UiLCJpZCI6MX0.Zy1LYiSo5R_ILob8UuuFJeNxZJCGsVBcBu4WR37GzwQ");

        response = request("POST", "/api/logout", headers, body);

        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        // 로그아웃 실패(유효하지 않은 토큰)
        headers.put("Authorization", "Bearer invalid token");

        response = request("POST", "/api/logout", headers, body);

        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("내 정보 조회 기능")
    void viewMyInfo() {
        // 내 정보 조회 성공
        headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsb2dpblNlcnZpY2UiLCJpZCI6MX0.Zy1LYiSo5R_ILob8UuuFJeNxZJCGsVBcBu4WR37GzwQ");

        response = request("GET", "/api/myInfo", headers, body);

        softly.assertThat(response.jsonPath().getString("id")).isEqualTo("1");
        softly.assertThat(response.jsonPath().getString("email")).isEqualTo("derosatam76@gmail.com");
        softly.assertThat(response.jsonPath().getString("name")).isEqualTo("eNoLj");

        // 내 정보 조회 실패
        headers.put("Authorization", "Bearer invalid token");

        response = request("GET", "/api/myInfo", headers, body);

        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> request(String method, String url, Map<String, String> headers, Map<String, String> body) {
        return RestAssured
                .given().log().all()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .headers(headers)
                .body(body)
                .when().request(method, url)
                .then().log().all()
                .extract();
    }
}
