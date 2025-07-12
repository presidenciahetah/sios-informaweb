package com.sios.springcloud.msvc.persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import net.hetah.sios.bootfaces.commons.entities.SaldosCarteraClientes;

@Repository
public class ImpoInformaWebDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImpoInformaWebDAO(@Qualifier("jdbcInformaweb") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<SaldosCarteraClientes>> traerCarteraByCliente(String nit) {
        String sql = """
            SELECT nits.nombre, movi.nit, 
                   SUM(CASE movi.deb_cre 
                       WHEN 'D' THEN movi.valor 
                       WHEN 'C' THEN -movi.valor 
                       ELSE 0 
                   END) AS saldo 
            FROM movi 
            JOIN nits ON nits.nit::text = movi.nit::text 
            JOIN movih ON movih.numero = movi.numero_doc AND movih.transaccion = movi.tipo_doc 
            WHERE movi.cuenta LIKE '1305%' 
                AND movi.comprob NOT IN ('NCN') 
                AND movi.nit::text = ? 
            GROUP BY nits.nombre, movi.nit 
            ORDER BY saldo DESC
            """;

        try {
            List<SaldosCarteraClientes> resultados = jdbcTemplate.query(sql, 
                new Object[]{nit.trim()}, // Usamos el parámetro preparado
                new RowMapper<SaldosCarteraClientes>() {
                    @Override
                    public SaldosCarteraClientes mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SaldosCarteraClientes item = new SaldosCarteraClientes();
                        item.setNombre(rs.getString("nombre"));
                        item.setNit(rs.getString("nit"));
                        item.setSaldo(rs.getBigDecimal("saldo"));
                        return item;
                    }
                });

            return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados);
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            throw e;
        }
    }
}
