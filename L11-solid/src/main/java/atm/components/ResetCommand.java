package atm.components;

import atm.ATM;

import java.util.Map;

public class ResetCommand {

    private final AtmMemento atmMemento;

    public ResetCommand(AtmMemento atmMemento) {
        this.atmMemento = atmMemento;
    }

    public void execute(ATM atm) {
        Map<Banknote, Integer> previousState = atmMemento.getHistoryOfAtm(atm.getId()).pop();
        atm.updateCells(previousState);
    }
}
