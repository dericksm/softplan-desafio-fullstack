package com.derick.server.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Process implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String feedback;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Client> responsibleClients = new ArrayList<>();

    private Boolean finalized;

    public Process(String feedback){
        this.feedback = feedback;
    }
}
