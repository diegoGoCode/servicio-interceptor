package com.servicio.dominio.IRepositories;

import com.servicio.dominio.dto.ObjectRequestDTO;

import java.util.List;


public interface ObjectRepository {
    List<ObjectRequestDTO> getAll();

    ObjectRequestDTO getById(Long id);

    ObjectRequestDTO save(ObjectRequestDTO objectRequestDTO) throws Exception;

    ObjectRequestDTO update(ObjectRequestDTO objectRequestDTO);

    void delete(Long id);
}