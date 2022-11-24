package com.mtcoding.demo.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtcoding.demo.dto.UserReqDto.JoinReqDto;

// @Transactional // 이거 걸면 롤백됨, 근데 트런케이트 사용할 거임 왜? auto increment 초기화를 위해
@ActiveProfiles("test")
@AutoConfigureMockMvc // mockmvc 사용하기 위함
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // 가짜환경에 스프링 컨텍스트를 복사 => 독립적인 환경
public class UserControllerTest {

    private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=utf-8";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void join_test() throws Exception {
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("123");
        joinReqDto.setEmail("email@com.com");
        String requestBody = om.writeValueAsString(joinReqDto);

        // MockMvcRequestBuilders 이거 사용하기 싫으면 스태틱하거나 c+. convert to static ...
        ResultActions resultActions = mockMvc.perform(
                post("/api/join")
                        .content(requestBody)
                        .contentType("application/json; charset=utf-8")
                        .accept("application/json; charset=utf-8"));

        String reponseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("디버그 : " + reponseBody); // 로그대신

        // then : 상태코드 + 핵심 값
        // convert to static import 나, 임포트 스태틱 해주면 됨(해당 임포트 변경)
        resultActions.andExpect(status().isCreated()); // 리절트매처스 안에 있는 거라서 resultactions.andreturn.get.. 이지랄 안해도 됨
        resultActions.andExpect(jsonPath("$.data.username").value("ssar"));
    }
}
