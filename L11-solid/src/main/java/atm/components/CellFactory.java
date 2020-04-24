package atm.components;

import static atm.components.StackOfBanknotes.stackOfBanknotes;

public class CellFactory {

    private AmountListener amountListener;

    public CellFactory(AmountListener amountListener) {
        this.amountListener = amountListener;
    }

    public Cell createCell(Banknote banknoteType, int count) {
        return new Cell(stackOfBanknotes(banknoteType, count), amountListener);
    }
}
