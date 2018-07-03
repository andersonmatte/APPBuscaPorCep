package br.com.andersonmatte.buscacep.activity.api;

import br.com.andersonmatte.buscacep.activity.entidade.Endereco;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepAPI {

    public static final Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("https://viacep.com.br")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/ws/{cep}/json/")
    Call<Endereco> getEnderecoPorCep(@Path("cep") String cep);

}
