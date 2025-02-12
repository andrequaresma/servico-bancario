package br.com.estudo.domain.service;

import br.com.estudo.domain.exceptions.ErrosSistema;
import br.com.estudo.domain.http.AgenciaHttp;
import br.com.estudo.domain.enums.SituacaoCadastral;
import br.com.estudo.domain.http.SituacaoCadastralHttpService;
import br.com.estudo.domain.models.Agencia;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgenciaService {

    @RestClient
    private SituacaoCadastralHttpService situacaoCadastralHttpService;

    private List<Agencia> agencias = new ArrayList<>();

    public void cadastrar(Agencia agencia){
        AgenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());

        if (agenciaHttp != null && agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)){
            agencias.add(agencia);
        } else {
            throw new ErrosSistema("Agência com CNPJ " + agencia.getCnpj() + " está com situação cadastral " + agenciaHttp.getSituacaoCadastral());
        }
    }

    public Agencia buscarPorId(Integer id) {
//        return agencias.stream().filter(agencia -> agencia.getId().equals(id)).findFirst().orElse(null); // sugestão copilot
        return agencias.stream().filter(agencia -> agencia.getId().equals(id)).toList().getFirst(); // sugestão Alura
    }

    public void deletar(Integer id) {
        agencias.removeIf(agencia -> agencia.getId().equals(id));
    }

    public void alterar(Agencia agencia) {
        deletar(agencia.getId());
        cadastrar(agencia);
    }
}
