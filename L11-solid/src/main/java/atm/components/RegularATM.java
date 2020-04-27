package atm.components;

import atm.ATM;
import atm.exceptions.ATMException;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static atm.components.Banknote.TEN;
import static atm.components.StackOfBanknotes.singleBanknoteStack;
import static atm.components.StackOfBanknotes.stackOfBanknotes;

public class RegularATM implements ATM {

    private static final Logger logger = Logger.getLogger(RegularATM.class);

    private Map<Banknote, Cell> cells;
    private AmountListener amountListener;

    public RegularATM() {
        amountListener = new AmountListener();
        CellFactory factory = new CellFactory(amountListener);
        EnumMap<Banknote, Cell> initialCells = new EnumMap<>(Banknote.class);
        // default initial amount in ATM
        initialCells.put(Banknote.TEN, factory.createCell(Banknote.TEN, 500));
        initialCells.put(Banknote.FIFTY, factory.createCell(Banknote.FIFTY, 500));
        initialCells.put(Banknote.HUNDRED, factory.createCell(Banknote.HUNDRED, 500));
        initialCells.put(Banknote.FIVE_HUNDRED, factory.createCell(Banknote.FIVE_HUNDRED, 500));
        initialCells.put(Banknote.THOUSAND, factory.createCell(Banknote.THOUSAND, 500));
        initialCells.put(Banknote.FIVE_THOUSAND, factory.createCell(Banknote.FIVE_THOUSAND, 500));
        cells = Collections.unmodifiableMap(initialCells);
    }

    public RegularATM(Map<Banknote, Integer> initialMoney) {
        amountListener = new AmountListener();
        CellFactory factory = new CellFactory(amountListener);
        Map<Banknote, Cell> cellMap = initialMoney.entrySet().stream().
                collect(Collectors.toMap(Map.Entry::getKey, e -> factory.createCell(e.getKey(), e.getValue())));

        for (Banknote banknoteType : Banknote.values()) {
            //initialize cells with each banknoteType type, regardless of what was the initial input
            cellMap.computeIfAbsent(banknoteType, key -> factory.createCell(banknoteType, 0));
        }
        cells = Collections.unmodifiableMap(cellMap);
    }

    @Override
    public void accept(List<Banknote> banknotes) {
        Collection<StackOfBanknotes> stacks = transformToStacksOfBanknotes(banknotes);
        List<StackOfBanknotes> processed = new ArrayList<>();
        for (StackOfBanknotes stackOfBanknotes : stacks) {
            Cell cell = cells.get(stackOfBanknotes.getType());
            if (!cell.add(stackOfBanknotes.getCount())) {
                // if some cell is overfilled, rollback everything (return the money) and show an error
                rollBack(processed);
                throw new ATMException("RegularCell with " + stackOfBanknotes.getType() + "'s is overfilled. " +
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
            Cell cell = cells.get(stackOfBanknotes.getType());
            cell.withdraw(stackOfBanknotes.getCount());
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
            int availableCount = cells.get(banknoteType).getBanknoteCount();
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
            Cell cell = cells.get(entry.getKey());
            cell.withdraw(entry.getValue());
        }
        return result;
    }

    @Override
    public int amountLeft() {
        return amountListener.getTotalAmount();
    }
}
