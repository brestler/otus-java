package atm;

import atm.components.Banknote;

public interface Cell {

    boolean add(int count);
    boolean withdraw(int count);
    void withdrawAll();
    int getBanknoteCount();
    Banknote getBanknoteType();
}
