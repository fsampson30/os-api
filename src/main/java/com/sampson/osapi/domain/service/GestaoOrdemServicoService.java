package com.sampson.osapi.domain.service;

import com.sampson.osapi.domain.exception.EntidadeNaoEncontradaException;
import com.sampson.osapi.domain.exception.NegocioException;
import com.sampson.osapi.domain.model.Cliente;
import com.sampson.osapi.domain.model.Comentario;
import com.sampson.osapi.domain.model.OrdemServico;
import com.sampson.osapi.domain.model.StatusOrdemServico;
import com.sampson.osapi.domain.repository.ClienteRepository;
import com.sampson.osapi.domain.repository.ComentarioRepository;
import com.sampson.osapi.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public OrdemServico criar(OrdemServico ordemServico){
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow( () -> new NegocioException("Cliente não encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }

    public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
        OrdemServico ordemServico = buscar(ordemServicoId);

        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);

        return comentarioRepository.save(comentario);
    }

    public void finalizar(Long ordemServicoId) {
        OrdemServico ordemServico = buscar(ordemServicoId);

        ordemServico.finalizar();
        ordemServicoRepository.save(ordemServico);
    }

    public void cancelar(Long ordemServicoId) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        ordemServico.cancelar();
        ordemServicoRepository.save(ordemServico);
    }

    private OrdemServico buscar(Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
    }
}
