package br.com.alura.tabelafipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class DataConverter implements IDataConverter {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T dataConversion(String json, Class<T> ofClass) {
        try {
            return mapper.readValue(json, ofClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> getList(String json, Class<T> ofClass) {
        CollectionType list = mapper.getTypeFactory()
                .constructCollectionType(List.class, ofClass);

        try {
            return mapper.readValue(json, list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
