package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService servico;

    @GetMapping()
    public List<SerieDTO> obterSeries() {
        return this.servico.obterTodasAsSeries();
    }

    @GetMapping("/top-5")
    public List<SerieDTO> obterTop5Series() {
        return this.servico.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return this.servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterSeriePorId(@PathVariable Long id) {
        return this.servico.obterSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasAsTemporadas(@PathVariable Long id) {
        return this.servico.obterTodasAsTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/agrupadas")
    public Map<Integer, List<EpisodioDTO>> obterEpisodiosAgrupadosPorTemporada(@PathVariable Long id) {
        return this.servico.obterEpisodiosAgrupadosPorTemporada(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obterEpisodiosPorTemporada(@PathVariable Long id
            ,@PathVariable Integer numeroTemporada) {
        return this.servico.obterEpisodiosPorTemporada(id, numeroTemporada);
    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nomeGenero) {
        return this.servico.obterSeriesPorCategoria(nomeGenero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTop5EpisodiosPorSerie(@PathVariable Long id) {
        return this.servico.obterTop5EpisodiosPorSerie(id);
    }
}
