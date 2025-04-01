package com.example.api_gateway;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class Core {
    private final WebClient core;

    public Core(Environment env) {
        var baseUrl = "http://core-service:8081";
        core = WebClient.create(baseUrl);
    }

    public Mono<Student> createStudent(Student student) {
        return core.post()
                .uri("/api/students")
                .bodyValue(student)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<List<Student>> getAllStudents() {
        return core.get()
                .uri("/api/students")
                .retrieve()
                .bodyToFlux(Student.class)
                .collectList();
    }

    public Mono<Student> updateStudent(Long id, Student student) {
        return core.put()
                .uri("/api/students/" + id)
                .bodyValue(student)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<HttpStatus> deleteStudent(Long id) {
        return core.delete()
                .uri("/api/students/" + id)
                .retrieve()
                .toBodilessEntity()
                .map(response -> (HttpStatus) response.getStatusCode());
    }
}
