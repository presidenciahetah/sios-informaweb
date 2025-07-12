package com.sios.springcloud.msvc.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sios.springcloud.msvc.persistent.InformaWebService;

import net.hetah.sios.bootfaces.commons.entities.SaldosCarteraClientes;

@RestController
@RequestMapping("/api/informaweb")
public class InformawebController {

    private static final Logger log = LoggerFactory.getLogger(InformawebController.class);
    
    private final InformaWebService service;

    public InformawebController(InformaWebService service) {
        this.service = service;
    }

    @GetMapping("/{nit}")
    public ResponseEntity<?> getClienteInfo(@PathVariable String nit) {
        log.info("Solicitud recibida para NIT: {}", nit);
        try {
            Optional<List<SaldosCarteraClientes>> saldoOptional = service.traerCarteraByCliente(nit.trim());
            if(saldoOptional.isPresent() && !saldoOptional.get().isEmpty()) {
                log.info("Datos encontrados para NIT: {}", nit);
                return ResponseEntity.ok(saldoOptional.get());
            } else {
                log.warn("No se encontraron datos para el NIT: {}", nit);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error al procesar la solicitud para el NIT: " + nit, e);
            return ResponseEntity.internalServerError().body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
