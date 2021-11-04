package com.example.demo.single.dto;

import com.example.demo.single.util.UsuarioEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumMap;

@Data
@NoArgsConstructor
public class UsuarioRequest {

    private EnumMap<UsuarioEnum, Object> enumMap;
}
