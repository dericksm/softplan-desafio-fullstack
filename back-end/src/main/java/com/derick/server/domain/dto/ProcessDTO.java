package com.derick.server.domain.dto;

import com.derick.server.domain.entities.Process;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessDTO implements Serializable {

    private Integer id;

    private String feedback;

    private List<ClientDTO> responsibleUsers;

    public ProcessDTO(Process process){
        this.id = process.getId();
        this.feedback = process.getFeedback();
    }
}
