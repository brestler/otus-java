package atm.components;

import atm.exceptions.CellOverFilledException;

class RegularCell implements Cell {

    private static final int MAX_COUNT_OF_BANKNOTES_IN_SELL = 1000;

    private int count;

    RegularCell(int count) {
        if (count > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            throw new CellOverFilledException("Max amount of banknotes in the cell is " + MAX_COUNT_OF_BANKNOTES_IN_SELL);
        this.count = count;
    }

    @Override
    public boolean add(int count) {
        if (this.count + count > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            return false;
        this.count += count;
        return true;
    }

    @Override
    public boolean withdraw(int count) {
        if (this.count < count)
            return false;
        this.count -= count;
        return true;
    }

    @Override
    public int getBanknoteCount() {
        return this.count;
    }
}
