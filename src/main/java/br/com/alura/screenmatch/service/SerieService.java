package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(this.repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(this.repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(this.repositorio.top5SeriesLancadas());
    }

    public SerieDTO obterSeriePorId(Long id) {
        Optional<Serie> serie = this.repositorio.findById(id);

        if(serie.isPresent()) {
            Serie s = serie.get();

            return new SerieDTO(s.getId(), s.getTitulo(),
                    s.getTotalTemporadas(), s.getAvaliacao(),
                    s.getGenero(), s.getAtores(), s.getPoster(),
                    s.getSinopse());
        }

        return null;
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(),
                        s.getTotalTemporadas(), s.getAvaliacao(),
                        s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<EpisodioDTO> obterTodasAsTemporadas(Long id) {
        Optional<Serie> serie = this.repositorio.findById(id);

        if(serie.isPresent()) {
            Serie s = serie.get();

            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo()
                            , e.getNumeroEpisodio()))
                    .sorted(Comparator
                            .comparing(EpisodioDTO::temporada)
                            .thenComparing(EpisodioDTO::numeroEpisodio))
                    .collect(Collectors.toList());
        }

        return null;
    }

    public Map<Integer, List<EpisodioDTO>> obterEpisodiosAgrupadosPorTemporada(Long id) {
        Optional<Serie> serie = this.repositorio.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();

            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .sorted(Comparator.comparing(EpisodioDTO::numeroEpisodio))
                    .collect(Collectors.groupingBy(EpisodioDTO::temporada));
        }

        return null;
    }

    public List<EpisodioDTO> obterEpisodiosPorTemporada(Long serieId,
                                                        Integer numeroTemporada) {
        return this.repositorio.episodiosPorTemporada(serieId,
                        numeroTemporada).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo()
                        , e.getNumeroEpisodio()))
                .sorted(Comparator.comparing(EpisodioDTO::numeroEpisodio))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);

        return converteDados(this.repositorio.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTop5EpisodiosPorSerie(Long id) {
        return this.repositorio.top5EpisodiosPorSerie(id).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(),
                        e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }
}
