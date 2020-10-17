package com.derick.server.domain.dto;

import com.derick.server.services.validation.UserInsert;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@UserInsert
public class NewClientDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Nome obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 40 caracteres")
    private String name;

    @NotEmpty(message = "E-mail obrigatório")
    @Email(message = "Invalid e-mail format")
    private String email;

    @NotEmpty(message = "Senha obrigatória")
    private String password;

    private Integer userType;
}
