package com.example.demo.single.service;

import com.example.demo.single.entity.Usuario;

import java.util.List;
import java.util.Map;

public interface UsuarioRepoCustom {

    List<Usuario> filterData(Map<String, Object> mapit);
}
