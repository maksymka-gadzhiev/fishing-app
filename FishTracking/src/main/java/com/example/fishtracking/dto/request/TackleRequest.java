package com.example.fishtracking.dto.request;

import com.example.fishtracking.enums.TackleType;
import lombok.Data;

@Data
public class TackleRequest {
    public String name;
    public TackleType type;
    public Double length;
    public Double test;
    public Double lineWeight;
    public Double hookSize;
}
