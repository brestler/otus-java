package atm.components;

import atm.Cell;
import atm.exceptions.CellOverFilledException;

class RegularCell implements Cell {

    private static final int MAX_COUNT_OF_BANKNOTES_IN_SELL = 1000;

    private Amount amount;
    private Banknote banknoteType;
    private int count;

    RegularCell(Banknote banknoteType, int count, Amount amount) {
        if (count > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            throw new CellOverFilledException("Max amount of banknotes in the cell is " + MAX_COUNT_OF_BANKNOTES_IN_SELL);
        this.banknoteType = banknoteType;
        this.amount = amount;
        this.count = count;
        this.amount.add(banknoteType, count);
    }

    @Override
    public boolean add(int count) {
        if (this.count + count > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            return false;
        this.count += count;
        amount.add(banknoteType, count);
        return true;
    }

    @Override
    public boolean withdraw(int count) {
        if (this.count < count)
            return false;
        this.count -= count;
        amount.withdraw(banknoteType, count);
        return true;
    }

    @Override
    public void withdrawAll() {
        int temp = count;
        count = 0;
        amount.withdraw(banknoteType, temp);
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
