package atm.components;

import atm.Cell;

public class CellFactory {

    private Amount amount;

    public CellFactory(Amount amount) {
        this.amount = amount;
    }

    public Cell createCell(Banknote banknoteType, int count) {
        return new RegularCell(banknoteType, count, amount);
    }
}
