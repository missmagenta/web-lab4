package com.lab4.backend.backend.dto;

import com.lab4.backend.backend.model.Attempt;
import lombok.Data;

@Data
public class AttemptDTO {
    private Attempt attempt;
    private String jwtToken;
}
