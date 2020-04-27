package atm.components;

public interface Cell {

    boolean add(int count);
    boolean withdraw(int count);
    int getBanknoteCount();
}
