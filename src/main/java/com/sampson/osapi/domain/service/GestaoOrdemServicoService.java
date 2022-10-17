package com.sampson.osapi.domain.service;

import com.sampson.osapi.domain.exception.NegocioException;
import com.sampson.osapi.domain.model.Cliente;
import com.sampson.osapi.domain.model.OrdemServico;
import com.sampson.osapi.domain.model.StatusOrdemServico;
import com.sampson.osapi.domain.repository.ClienteRepository;
import com.sampson.osapi.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public OrdemServico criar(OrdemServico ordemServico){
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow( () -> new NegocioException("Cliente não encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(LocalDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }
}
