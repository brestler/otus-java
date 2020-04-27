package atm.components;

import atm.exceptions.CellOverFilledException;

class RegularCell implements Cell {

    private static final int MAX_COUNT_OF_BANKNOTES_IN_SELL = 1000;

    private AmountListener amountListener;
    private Banknote banknoteType;
    private int count;

    RegularCell(Banknote banknoteType, int count, AmountListener amountListener) {
        if (count > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            throw new CellOverFilledException("Max amount of banknotes in the cell is " + MAX_COUNT_OF_BANKNOTES_IN_SELL);
        this.banknoteType = banknoteType;
        this.amountListener = amountListener;
        this.count = count;
        this.amountListener.add(banknoteType, count);
    }

    @Override
    public boolean add(int count) {
        if (this.count + count > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            return false;
        this.count += count;
        amountListener.add(banknoteType, count);
        return true;
    }

    @Override
    public boolean withdraw(int count) {
        if (this.count < count)
            return false;
        this.count -= count;
        amountListener.withdraw(banknoteType, count);
        return true;
    }

    @Override
    public Banknote getBanknoteType() {
        return banknoteType;
    }

    @Override
    public int getBanknoteCount() {
        return this.count;
    }
}
