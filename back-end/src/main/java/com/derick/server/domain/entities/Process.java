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

    private String name;

    @ManyToMany
    @JoinTable(
            name = "process_clients",
            joinColumns = @JoinColumn(name = "process_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> responsibleClients = new ArrayList<>();

    private Boolean finalized = false;

    public Process(String name, String feedback){
        this.name = name;
        this.feedback = feedback;
    }
}
