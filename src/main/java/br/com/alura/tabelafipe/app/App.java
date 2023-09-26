package br.com.alura.tabelafipe.app;

import br.com.alura.tabelafipe.model.Dados;
import br.com.alura.tabelafipe.model.Modelos;
import br.com.alura.tabelafipe.model.Veiculo;
import br.com.alura.tabelafipe.service.ConsumerApi;
import br.com.alura.tabelafipe.service.DataConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    private Scanner input = new Scanner(System.in);
    private ConsumerApi consumer = new ConsumerApi();
    private DataConverter converter = new DataConverter();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void showMenu() {
        String menu = """
                ***OPÇÕES***
                1 - Carro
                2 - Moto
                3 - Caminhão
                4 - Sair
                                
                Digite o número correspondente para consultar:
                """;
        System.out.println(menu);
        var option = input.nextInt();

        String address = null, json;
        switch (option) {
            case 1 -> address = URL_BASE + "carros/marcas";

            case 2 -> address = URL_BASE + "motos/marcas";

            case 3 -> address = URL_BASE + "caminhoes/marcas";
        }

        json = consumer.obterDados(address);
        //System.out.println(json);
        var marcas = converter.getList(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::code))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca do carro: ");
        var codigoMarca = input.nextInt();
        address = address.concat("/" + codigoMarca + "/modelos");
        json = consumer.obterDados(address);
        //System.out.println(json);
        var modeloLista = converter.dataConversion(json, Modelos.class);
        System.out.println("Modelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::code))
                .forEach(System.out::println);

        System.out.println("Digite o código do modelo do carro: ");
        option = input.nextInt();
        address = address.concat("/" + option + "/anos");
        json = consumer.obterDados(address);
        var anos = converter.getList(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var addressYear = address + "/" + anos.get(i).code();
            json = consumer.obterDados(addressYear);
            Veiculo veiculo = converter.dataConversion(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("Todos od veiculos com avaliacao por ano: ");
        veiculos.stream()
                .sorted(Comparator.comparing(Veiculo::ano))
                .forEach(System.out::println);
    }
}
