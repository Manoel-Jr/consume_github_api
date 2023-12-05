package br.com.consumerapigithub.app.controller;

import br.com.consumerapigithub.app.dto.GitHubResponseDTO;
import br.com.consumerapigithub.app.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/organizations")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService service;

    @GetMapping(value = "/{organizationName}")
    public ResponseEntity<GitHubResponseDTO> repositories(@PathVariable(value = "organizationName") String organizationName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findRepositoriesByOrganization(organizationName));
    }
}
