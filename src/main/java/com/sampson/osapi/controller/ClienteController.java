package com.sampson.osapi.controller;

import com.sampson.osapi.domain.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClienteController {

    @GetMapping("/clientes")
    public List<Cliente> listar() {
        var cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Fernanda");
        cliente1.setTelefone("21 999999999");
        cliente1.setEmail("a@a.com");

        var cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Natalia");
        cliente2.setTelefone("21 888888888");
        cliente2.setEmail("b@b.com");

        return Arrays.asList(cliente1, cliente2);
    }
}
