package atm;

import atm.components.Banknote;

import java.util.List;
import java.util.Map;

public interface ATM {

    void accept(List<Banknote> banknotes);

    Map<Banknote, Integer> withdraw(int amount);

    int amountLeft();

    int getId();

    void updateCells(Map<Banknote, Integer> previousState);

    void saveState();

    Map<Banknote, Integer> getPreviousState();
}
