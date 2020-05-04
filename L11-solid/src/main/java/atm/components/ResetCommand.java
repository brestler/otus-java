package atm.components;

import atm.ATM;
import atm.AtmCommand;

import java.util.Map;

public class ResetCommand implements AtmCommand {

    public void execute(ATM atm) {
        Map<Banknote, Integer> previousState = atm.getPreviousState();
        atm.updateCells(previousState);
    }
}
