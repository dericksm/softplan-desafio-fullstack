package com.derick.server.domain.entities;

import com.derick.server.domain.enums.ClientRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private ClientRole role;

    public Client(Integer id, String name, String email, ClientRole clientRole, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = clientRole;
    }

    public Client(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Client(Integer id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
