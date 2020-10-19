package com.derick.server.domain.dto;

import com.derick.server.domain.entities.Process;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessDTO implements Serializable {

    private Integer id;

    private String feedback;

    @NotEmpty
    private String name;

    private Boolean finalized = false;

    @NotEmpty
    private List<ClientDTO> responsibleClients = new ArrayList<>();

    public ProcessDTO(Process process){
        this.id = process.getId();
        this.feedback = process.getFeedback();
        this.name = process.getName();
        this.finalized = process.getFinalized();
    }
}
