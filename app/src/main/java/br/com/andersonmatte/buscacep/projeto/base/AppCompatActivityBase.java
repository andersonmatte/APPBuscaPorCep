package br.com.andersonmatte.buscacep.projeto.base;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.projeto.entity.Estado;
import io.realm.Realm;
import io.realm.RealmResults;

public class AppCompatActivityBase extends AppCompatActivity {

    protected Realm realm;
    protected RealmResults<Estado> estadoRealmResults;
    protected List<Estado> listaEstado = new ArrayList<Estado>();

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
        String jsonString = null;
        Gson gson = new Gson();
        try {
            jsonString = buscaEstadosJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonString != null) {
            Type collectionType = new TypeToken<List<Estado>>() {
            }.getType();
            this.listaEstado = gson.fromJson(jsonString, collectionType);
            salvaEstados();
        }
    }

    private String buscaEstadosJson() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.estadoscidades);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return writer.toString();
    }

    protected void salvaEstados() {
        if (!this.listaEstado.isEmpty()) {
            this.realm.beginTransaction();
            this.realm.insert(this.listaEstado);
            this.realm.commitTransaction();
            realm.where(Estado.class).findAllAsync();
        }
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}