package com.sios.springcloud.msvc.persistent;

import java.util.List;
import java.util.Optional;

import net.hetah.sios.bootfaces.commons.entities.SaldosCarteraClientes;

public interface InformaWebService {
    Optional<List<SaldosCarteraClientes>> traerCarteraByCliente(String nit);
}
