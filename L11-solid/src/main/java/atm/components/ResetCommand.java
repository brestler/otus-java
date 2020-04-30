package atm.components;

import atm.ATM;
import atm.AtmCommand;

import java.util.Map;

public class ResetCommand implements AtmCommand {

    private final AtmMemento atmMemento;

    public ResetCommand(AtmMemento atmMemento) {
        this.atmMemento = atmMemento;
    }

    public void execute(ATM atm) {
        Map<Banknote, Integer> previousState = atmMemento.getPreviousState(atm.getId());
        atm.updateCells(previousState);
    }
}
