package com.servicio.persistencia.ImplRepositories;

import com.servicio.dominio.IRepositories.ObjectRepository;
import com.servicio.dominio.dto.ObjectRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ObjectRepositoryImpl implements ObjectRepository {

    @Override
    public List<ObjectRequestDTO> getAll() {
        List<ObjectRequestDTO> list = new ArrayList<>();
        list.add(new ObjectRequestDTO("Laura", "Nieves", "Success"));
        list.add(new ObjectRequestDTO("Diego", "Diaz", "Success"));
        return list;
    }

    @Override
    public ObjectRequestDTO getById(Long id) {
        return new ObjectRequestDTO("Diego", "Diaz", "Success");
    }

    @Override
    public ObjectRequestDTO save(ObjectRequestDTO objectRequestDTO) throws Exception {

        return objectRequestDTO;
    }

    @Override
    public ObjectRequestDTO update(ObjectRequestDTO objectResponseDTO) {
        return objectResponseDTO;
    }

    @Override
    public void delete(Long id) {
    }
}