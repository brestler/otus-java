package atm;

import atm.components.Banknote;

public interface Cell {

    boolean add(int count);
    boolean withdraw(int count);
    int getBanknoteCount();
    Banknote getBanknoteType();
}
