package cl.duoc.viviendas.dao;

import cl.duoc.viviendas.model.Casa;
import cl.duoc.viviendas.model.Departamento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ViviendaDAO {

    private static final Path ARCHIVO_DEPARTAMENTOS = Path.of("departamentos.json");
    private static final Path ARCHIVO_CASAS = Path.of("casas.json");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Departamento> cargarDepartamentos() throws IOException {
        Type tipo = new TypeToken<List<Departamento>>() {}.getType();
        return leer(ARCHIVO_DEPARTAMENTOS, tipo);
    }

    public List<Casa> cargarCasas() throws IOException {
        Type tipo = new TypeToken<List<Casa>>() {}.getType();
        return leer(ARCHIVO_CASAS, tipo);
    }

    public void guardarDepartamentos(List<Departamento> lista) throws IOException {
        escribir(ARCHIVO_DEPARTAMENTOS, lista);
    }

    public void guardarCasas(List<Casa> lista) throws IOException {
        escribir(ARCHIVO_CASAS, lista);
    }

    private <T> List<T> leer(Path archivo, Type tipoLista) throws IOException {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(archivo)) {
            List<T> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        }
    }

    private void escribir(Path archivo, List<?> lista) throws IOException {
        try (Writer writer = Files.newBufferedWriter(archivo)) {
            gson.toJson(lista, writer);
        }
    }
}