package atm.components;

import atm.exceptions.CellOverFilledException;

class Cell {

    private static final int MAX_COUNT_OF_BANKNOTES_IN_SELL = 1000;

    private StackOfBanknotes stackOfBanknotes;
    private AmountListener amountListener;

    Cell(StackOfBanknotes stackOfBanknotes, AmountListener amountListener) {
        if (stackOfBanknotes.getCount() > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            throw new CellOverFilledException("Max amount of banknotes in the cell is " + MAX_COUNT_OF_BANKNOTES_IN_SELL);
        this.stackOfBanknotes = stackOfBanknotes;
        this.amountListener = amountListener;
        amountListener.add(stackOfBanknotes.getMoneyAmount());
    }

    boolean add(int count) {
        if (count + stackOfBanknotes.getCount() > MAX_COUNT_OF_BANKNOTES_IN_SELL)
            return false;
        stackOfBanknotes.add(count);
        amountListener.add(stackOfBanknotes.getMoneyAmount());
        return true;
    }

    boolean withdraw(int count) {
        if (stackOfBanknotes.getCount() < count)
            return false;
        stackOfBanknotes.remove(count);
        amountListener.withdraw(count * stackOfBanknotes.getType().getNominal());
        return true;
    }

    int withdrawAll() {
        int was = stackOfBanknotes.getCount();
        stackOfBanknotes.removeAll();
        amountListener.withdraw(was * stackOfBanknotes.getType().getNominal());
        return was;
    }

    int getBanknoteCount() {
        return stackOfBanknotes.getCount();
    }
}
