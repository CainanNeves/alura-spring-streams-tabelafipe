package br.com.alura.tabelafipe.service;

import java.util.List;

public interface IDataConverter {
    <T> T dataConversion(String json, Class<T> ofClass);

    <T> List<T> getList(String json, Class<T> ofClass);
}
