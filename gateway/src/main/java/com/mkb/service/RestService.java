package com.mkb.service;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import com.mkb.entity.User;
import com.mkb.dto.AuthResponseDTO;
import com.mkb.response.ApiResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;

@Service
@RequiredArgsConstructor
public class RestService {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private LibraryService.AuthRequestDTO getAuthRequestDTO(String username, String password) {
        return new LibraryService.AuthRequestDTO(username, password);
    }

    public ResponseEntity<AuthResponseDTO> register(User user) {

        RestTemplate restTemplate = new RestTemplate();
        final var response = restTemplate.exchange(
                "http://localhost:8090/auth/register",
                HttpMethod.POST,
                registerEntity(user),
                AuthResponseDTO.class
        );

        final var token = Objects.requireNonNull(response.getBody()).getToken();
        System.out.println("TOKEN -> " + token);
        return response;
    }

//    public ResponseEntity<AuthResponseDTO> getToken(String username, String password) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        final var response = restTemplate.exchange(
//                "http://localhost:8090/auth/authenticate",
//                HttpMethod.POST,
//                getHttpEntity(username, password),
//                AuthResponseDTO.class
//        );
//
//        final var token = Objects.requireNonNull(response.getBody()).getToken();
//        System.out.println("TOKEN -> " + token);
//
//        return response;
//    }


    public static HttpEntity<?> getHttpEntity(String username, String password) {
        record AuthDTO(String username, String password) {
        }
        final var authDTO = new AuthDTO(username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(authDTO, headers);
    }

    public static HttpEntity<?> registerEntity(User user) {
        record UserDTO(String fullName, String email, String password) {
        }
        final var registerDTO = new UserDTO(user.getFullName(), user.getEmail(), user.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(registerDTO, headers);
    }

    public ApiResponse getData(String token) {

        RestTemplate libServiceTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
        headers.set("Authorization", "Bearer " + token); //var-2
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ApiResponse> response = libServiceTemplate.exchange(
                "http://localhost:8070/api/v1/schools/",
                HttpMethod.GET,
                requestEntity,
                ApiResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error occurred during the request.");
        }
    }

    public ResponseEntity<AuthResponseDTO> getToken(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LibraryService.AuthRequestDTO> requestEntity =
                new HttpEntity<>(
                        getAuthRequestDTO(username, password),
                        headers
                );

        try {
            ResponseEntity<AuthResponseDTO> response = restTemplate.exchange(
                    "http://localhost:8090/auth/authenticate",
                    HttpMethod.POST,
                    requestEntity,
                    AuthResponseDTO.class
            );
            return response;

        } catch (HttpClientErrorException.Forbidden ex) {
            // Handle 403 Forbidden error
            System.err.println("Access denied. Check your credentials or permissions.");
            throw ex;
        } catch (HttpClientErrorException ex) {
            // Handle other client errors
            System.err.println("Client error: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
            throw ex;
        } catch (HttpServerErrorException ex) {
            // Handle server errors
            System.err.println("Server error: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
            throw ex;
        } catch (RestClientException ex) {
            // Handle other exceptions
            System.err.println("Rest client exception: " + ex.getMessage());
            throw ex;
        }
    }

    public ApiResponse getUsersData(String token) {

        RestTemplate libServiceTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
        headers.set("Authorization", "Bearer " + token); //var-2
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiResponse> response = libServiceTemplate.exchange(
                    "http://localhost:8070/api/v1/schools/users",
                    HttpMethod.GET,
                    requestEntity,
                    ApiResponse.class
            );

            return response.getBody();

        } catch (HttpClientErrorException.Forbidden ex) {
            // Handle 403 Forbidden error
            System.err.println("Access denied. Check your credentials or permissions.");
            throw ex;
        } catch (HttpClientErrorException ex) {
            // Handle other client errors
            System.err.println("Client error: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
            throw ex;
        } catch (HttpServerErrorException ex) {
            // Handle server errors
            System.err.println("Server error: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
            throw ex;
        } catch (RestClientException ex) {
            // Handle other exceptions
            System.err.println("Rest client exception: " + ex.getMessage());
            throw ex;
        }
    }
}