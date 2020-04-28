package atm.components;

import atm.Cell;

public class CellFactory {

    private AmountListener amountListener;

    public CellFactory(AmountListener amountListener) {
        this.amountListener = amountListener;
    }

    public Cell createCell(Banknote banknoteType, int count) {
        return new RegularCell(banknoteType, count, amountListener);
    }
}
