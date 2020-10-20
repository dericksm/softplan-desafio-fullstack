package com.derick.server.models.dto;

import com.derick.server.models.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Nome obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 40 caracteres")
    private String name;

    @NotEmpty(message = "E-mail obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private Integer role;

    public ClientDTO(Client client){
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.role = client.getRole().getValue();
    }
}
