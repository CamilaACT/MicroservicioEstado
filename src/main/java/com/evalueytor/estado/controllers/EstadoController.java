package com.evalueytor.estado.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.evalueytor.estado.models.Estado;
import com.evalueytor.estado.repositories.EstadoRepository;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {

    @Autowired
    EstadoRepository estadoRepository;

    // Listar todo
    @GetMapping("/findall")
    public List<Estado> list() {
        return estadoRepository.findAll();
    }

    // Listar por Id
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Estado> obtenerEstadoPorId(@PathVariable Long id) {
        Optional<Estado> estadoOpcion = estadoRepository.findById(id);
        return estadoOpcion.map(premio -> new ResponseEntity<>(premio, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una nueva estado
    @PostMapping("/save")
    public ResponseEntity<Estado> crearPremio(@RequestBody Estado nuevoEstado) {
        Estado estadoGuardado = estadoRepository.save(nuevoEstado);
        return new ResponseEntity<>(estadoGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/updatebyid/{id}")
    public ResponseEntity<Estado> actualizarEmopresa(@PathVariable Long id, @RequestBody Estado estadoActual) {
        Optional<Estado> estadoOptional = estadoRepository.findById(id);
        return estadoOptional.map(estado -> {
            estado.setId(id);
            estado.setNombre(estadoActual.getNombre());
            Estado estadoActualGuardado = estadoRepository.save(estado);
            return new ResponseEntity<>(estadoActualGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        Optional<Estado> estadoOptional = estadoRepository.findById(id);
        if (estadoOptional.isPresent()) {
            estadoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
