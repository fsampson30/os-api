package com.sampson.osapi.controller;

import com.sampson.osapi.domain.model.Comentario;
import com.sampson.osapi.domain.service.GestaoOrdemServicoService;
import com.sampson.osapi.model.ClienteResumoModel;
import com.sampson.osapi.model.ComentarioInputModel;
import com.sampson.osapi.model.ComentarioModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioModel adicionar(@Valid @PathVariable Long ordemServicoId, @RequestBody ComentarioInputModel comentarioInputModel) {
        Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId, comentarioInputModel.getDescricao());

        return toModel(comentario);


    }

    private ComentarioModel toModel(Comentario comentario) {
        return modelMapper.map(comentario, ComentarioModel.class);
    }
}
