package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Dados(@JsonAlias("codigo")
                    String code,
                    @JsonAlias("nome")
                    String name) {
}
