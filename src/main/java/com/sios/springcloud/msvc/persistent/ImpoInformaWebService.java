package com.sios.springcloud.msvc.persistent;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hetah.sios.bootfaces.commons.entities.SaldosCarteraClientes;

/**
 * Implementación de InformaWebService
 * 
 * @author [Tu nombre]
 */
@Service
public class ImpoInformaWebService implements InformaWebService {
    
    private final ImpoInformaWebDAO dao;
    
    @Autowired
    public ImpoInformaWebService(ImpoInformaWebDAO dao) {
        this.dao = dao;
    }
    
    @Override
    public Optional<List<SaldosCarteraClientes>> traerCarteraByCliente(String nit) {
    	return dao.traerCarteraByCliente(nit);
    }
}