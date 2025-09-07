package com.example.fishtracking.dto.response;

import com.example.fishtracking.enums.TackleType;
import lombok.Data;

@Data
public class TackleResponse {
    public Long id;
    public String name;
    public TackleType type;
    public Double length;
    public Double test;
    public Double lineWeight;
    public Double hookSize;
}
