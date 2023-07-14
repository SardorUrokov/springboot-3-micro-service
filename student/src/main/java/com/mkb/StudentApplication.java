package com.mkb;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);

//        RestTemplate restTemplate = new RestTemplate();
//
//        final var response = restTemplate.exchange(
//                "http://localhost:8090/auth/",
//                HttpMethod.POST,
//                getHttpEntity("", ""),
//                AuthResponseDTO.class
//        );
//
//        final var token = Objects.requireNonNull(response.getBody()).getToken();
//        RestTemplate libServiceTemplate = new RestTemplate();
//    }
//
//    public static HttpEntity<?> getHttpEntity(String username, String password) {
//        record UserDTO(String username, String password) {
//        }
//        final var userDTO = new UserDTO(username, password);
//        HttpHeaders headers = new HttpHeaders();
////		headers.setBearerAuth();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        return new HttpEntity<>(userDTO, headers);
//    }
    }
}