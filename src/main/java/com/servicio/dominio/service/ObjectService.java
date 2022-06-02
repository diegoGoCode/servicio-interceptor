package com.servicio.dominio.service;

import com.servicio.dominio.IRepositories.ObjectRepository;
import com.servicio.dominio.dto.ObjectRequestDTO;
import com.servicio.dominio.dto.ObjectResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    public List<ObjectRequestDTO> getAll() {
        return objectRepository.getAll();
    }

    public ObjectRequestDTO getById(Long id) {
        return objectRepository.getById(id);
    }

    public ObjectRequestDTO save(ObjectRequestDTO objectRequestDTO) throws Exception {
        return objectRepository.save(objectRequestDTO);
    }

    public ObjectRequestDTO update(ObjectRequestDTO objectRequestDTO) {
        return objectRepository.update(objectRequestDTO);
    }

    public void delete(Long id) {
        objectRepository.delete(id);
    }
}
