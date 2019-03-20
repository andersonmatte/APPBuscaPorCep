package br.com.andersonmatte.buscacep.projeto.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.projeto.api.IbgeAPI;
import br.com.andersonmatte.buscacep.projeto.entidade.Estado;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppCompatActivityBase extends AppCompatActivity {

    protected Realm realm;
    protected RealmResults<Estado> estadoRealmResults;
    protected List<Estado> listaEstado = new LinkedList<>();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.criaBancoRealm();
    }

    public void criaBancoRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    protected void recuperarEstados() {
        this.estadoRealmResults = realm.where(Estado.class).findAllAsync();
        this.estadoRealmResults.load();
        if (this.estadoRealmResults != null && this.estadoRealmResults.size() > 1) {
            this.setListaEstado(estadoRealmResults);
        } else {
            this.buscaEstados();
        }
    }

    protected void buscaEstados() {
        IbgeAPI ibgeAPI = IbgeAPI.retrofit.create(IbgeAPI.class);
        final Call<List<Estado>> callEstados = ibgeAPI.getEstados();
        callEstados.enqueue(new Callback<List<Estado>>() {
            @Override
            public void onResponse(Call<List<Estado>> call, Response<List<Estado>> response) {
                listaEstado = response.body();
                salvaEstados();
            }

            @Override
            public void onFailure(Call<List<Estado>> call, Throwable t) {
                Log.i("ERRO", getResources().getString(R.string.erro_buscaServico) + t.getMessage());
            }
        });
    }

    protected void salvaEstados() {
        if (!this.listaEstado.isEmpty()) {
            this.realm.beginTransaction();
            this.realm.insert(this.listaEstado);
            this.realm.commitTransaction();
            realm.where(Estado.class).findAllAsync();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

}