package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO( Long id,
                        String titulo,
                        Integer totalTemporadas,
                        Double avaliacao,
                        @Enumerated(EnumType.STRING)
                        Categoria genero,
                        String atores,
                        String poster,
                        String sinopse) {

}
