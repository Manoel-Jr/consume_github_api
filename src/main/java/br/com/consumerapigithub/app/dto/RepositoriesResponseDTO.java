package br.com.consumerapigithub.app.dto;

import br.com.consumerapigithub.entity.Owner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoriesResponseDTO implements Serializable {

    private String id;
    private String node_id;
    private String name;
    private String full_name;
    private String commits_url;
    private Owner owner;
    private List<String> topics;

}
