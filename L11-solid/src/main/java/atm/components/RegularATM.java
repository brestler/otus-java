package atm.components;

import atm.ATM;
import atm.exceptions.ATMException;
import org.apache.log4j.Logger;

import java.util.*;

import static atm.components.Banknote.TEN;
import static atm.components.StackOfBanknotes.singleBanknoteStack;
import static atm.components.StackOfBanknotes.stackOfBanknotes;

public class RegularATM implements ATM {

    private static final Logger logger = Logger.getLogger(RegularATM.class);

    private CellsManager cellsManager;

    public RegularATM() {
        cellsManager = new CellsManager();
    }

    public RegularATM(Map<Banknote, Integer> initialMoney) {
        cellsManager = new CellsManager(initialMoney);
    }

    @Override
    public void accept(List<Banknote> banknotes) {
        Collection<StackOfBanknotes> stacks = transformToStacksOfBanknotes(banknotes);
        List<StackOfBanknotes> processed = new ArrayList<>();
        for (StackOfBanknotes stackOfBanknotes : stacks) {
            if (!cellsManager.put(stackOfBanknotes)) {
                // if some cell is overfilled, rollback everything (return the money) and show an error
                rollBack(processed);
                throw new ATMException("Cell with " + stackOfBanknotes.getType() + "'s is overfilled. " +
                        "Please use other nominals or come back later :)");
            }
            processed.add(stackOfBanknotes);
        }
    }

    private Collection<StackOfBanknotes> transformToStacksOfBanknotes(List<Banknote> banknotes) {
        Map<Banknote, StackOfBanknotes> stacks = new HashMap<>();
        for (Banknote banknoteType : banknotes) {
            stacks.merge(banknoteType, singleBanknoteStack(banknoteType),
                    (oldVal, newVal) -> stackOfBanknotes(banknoteType, oldVal.getCount() + newVal.getCount()));
        }
        return stacks.values();
    }

    private void rollBack(List<StackOfBanknotes> stacks) {
        for (StackOfBanknotes stackOfBanknotes : stacks) {
            cellsManager.withdraw(stackOfBanknotes);
        }
    }

    @Override
    public Map<Banknote, Integer> withdraw(int amount) {
        if (amount > amountLeft())
            throw new ATMException("Not enough money in ATM");
        int backupAmount = amount;

        // not pretty :(
        Map<Banknote, Integer> result = new HashMap<>();
        for (Banknote banknoteType : Banknote.values()) {
            int nominal = banknoteType.getNominal();
            int availableCount = cellsManager.getBanknoteCountAvailable(banknoteType);
            if (amount == 0)
                break;
            int countNeeded = amount / nominal;
            if (countNeeded == 0 || availableCount == 0)
                continue;
            if (availableCount < countNeeded) {
                int diff = (countNeeded - availableCount) * nominal;
                amount = (amount % nominal) + diff;
                result.put(banknoteType, availableCount);
            } else {
                amount %= nominal;
                result.put(banknoteType, countNeeded);
            }
        }

        if (amount > TEN.getNominal()) {
            throw new ATMException("Available nominals do not allow to return the requested amount. " +
                    "Please try other nominals");
        }

        if (amount < TEN.getNominal()) {
            logger.warn("Minimal banknote nominal is 10 but you've requested " + backupAmount +
                    ". Additional " + amount + " won't be withdrawn");
        }

        // withdraw only if able to return the amount
        for (Map.Entry<Banknote, Integer> entry : result.entrySet()) {
            cellsManager.withdraw(stackOfBanknotes(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @Override
    public int amountLeft() {
        return cellsManager.getTotalAmount();
    }
}
