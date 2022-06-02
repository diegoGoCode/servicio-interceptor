package com.servicio.web;

import com.servicio.dominio.dto.ObjectRequestDTO;
import com.servicio.dominio.service.ObjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ObjectController {

    @Autowired
    private ObjectService objectService;

    @Operation(summary = "Obtiene todos los registros")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-CompanyId", schema = @Schema(type = "string"))
    })
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObjectRequestDTO>> getAll() {
        return ResponseEntity.ok(objectService.getAll());
    }

    @Operation(summary = "Obtiene un registro por du id")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-CompanyId", schema = @Schema(type = "string"))
    })
    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectRequestDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok(objectService.getById(id));
    }

    @Operation(summary = "Guarda un registro")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-CompanyId", schema = @Schema(type = "string"))
    })
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectRequestDTO> save(@RequestBody ObjectRequestDTO objectRequestDTO) throws Exception {
        objectRequestDTO.setStatusReponse("Success");
        return ResponseEntity.ok(objectService.save(objectRequestDTO));
    }

    @Operation(summary = "Actualiza un registro")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-CompanyId", schema = @Schema(type = "string"))
    })
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectRequestDTO> update(@RequestBody ObjectRequestDTO objectRequestDTO) {
        objectRequestDTO.setStatusReponse("Success");
        return ResponseEntity.ok(objectService.update(objectRequestDTO));
    }

    @Operation(summary = "Elimina un registro")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.HEADER, name = "X-CompanyId", schema = @Schema(type = "string"))
    })
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestParam Long id) {
        objectService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}