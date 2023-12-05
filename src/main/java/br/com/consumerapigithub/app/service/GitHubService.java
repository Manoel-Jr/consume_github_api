package br.com.consumerapigithub.app.service;

import br.com.consumerapigithub.app.dto.GitHubResponseDTO;

public interface GitHubService {

    GitHubResponseDTO findRepositoriesByOrganization(String org);
}
