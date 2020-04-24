package atm.components;

public class CellFactory {

    private AmountListener amountListener;

    public CellFactory(AmountListener amountListener) {
        this.amountListener = amountListener;
    }

    public Cell createCell(Banknote banknoteType, int count) {
        return new Cell(banknoteType, count, amountListener);
    }
}
