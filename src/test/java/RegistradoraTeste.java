import banco_de_dados.BancoDeDados;
import compra.Compra;
import compra.Item;
import modelos.RegistradoraModelo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class RegistradoraTeste {

    @BeforeEach
    void reiniciarBancoDeDados() {
        BancoDeDados.reset();
    }

    @Test
    public void deveRegistrarAVendaNoBancoDeDados() {
        // Arrange -> tradução livre -> Entrada
        var pastelDeCarne = Item.builder().nome("Pastel de Carne").valor(new BigDecimal("7.00")).build();
        var pastelDeFlangoComCatupily = Item.builder().nome("Pastel de Flango com Catupily").valor(new BigDecimal("6.50")).build();

        List<Item> listaDeItens = Arrays.asList(pastelDeCarne, pastelDeFlangoComCatupily);

        var compra = Compra.builder().itens(listaDeItens).build();

        var registradora = RegistradoraModelo.construirCenario()
                .compra(compra)
                .valorRecebido(new BigDecimal("13.50"))
                .build();
        // Act -> tradução livre -> Ação

        registradora.efetivarVenda();

        // Assert -> tradução livre -> Resultado

        Assertions.assertEquals(1, BancoDeDados.vendas.size());
        Assertions.assertEquals(new BigDecimal("13.50"), BancoDeDados.vendas.get(0).getTotalCompra());
        Assertions.assertEquals(new BigDecimal("00.00"), BancoDeDados.vendas.get(0).getTroco());
        Assertions.assertEquals("Hikari Lee", BancoDeDados.vendas.get(0).getVendedor().getNome());
        Assertions.assertEquals("Bruce Lee", BancoDeDados.vendas.get(0).getCliente().getNome());
        Assertions.assertEquals(LocalDate.now(), BancoDeDados.vendas.get(0).getDataDaVenda());
    }

    @Test
    public void naoDeveRegistrarAVendaQuandoOValorRecebidoForMenorQueOTotal() {
        // Arrange -> tradução livre -> Entrada
        var pastelDeCarne = Item.builder().nome("Pastel de Carne").valor(new BigDecimal("7.00")).build();
        var pastelDeFlangoComCatupily = Item.builder().nome("Pastel de Flango com Catupily").valor(new BigDecimal("6.50")).build();

        List<Item> listaDeItens = Arrays.asList(pastelDeCarne, pastelDeFlangoComCatupily);

        var compra = Compra.builder().itens(listaDeItens).build();

        var registradora = RegistradoraModelo.construirCenario()
                .compra(compra)
                .valorRecebido(new BigDecimal("13.00"))
                .build();


        // Act -> tradução livre -> Ação

        registradora.efetivarVenda();

        // Assert -> tradução livre -> Resultado

        Assertions.assertEquals(0, BancoDeDados.vendas.size());

    }
}
