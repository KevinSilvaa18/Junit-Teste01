package compra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Compra {
    private List<Item> itens;

    public BigDecimal getTotal() {
        return itens.stream()
                .map(Item::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
