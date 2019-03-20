package br.com.andersonmatte.buscacep.projeto.api;

import java.util.List;

import br.com.andersonmatte.buscacep.projeto.entidade.Estado;
import br.com.andersonmatte.buscacep.projeto.entidade.Municipio;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IbgeAPI {

    public static final Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("http://servicodados.ibge.gov.br")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/api/v1/localidades/estados/")
    Call<List<Estado>> getEstados();

    @GET("/api/v1/localidades/estados/{UF}/municipios")
    Call<List<Municipio>> getMunicipios(@Path("UF") String uf);

}
