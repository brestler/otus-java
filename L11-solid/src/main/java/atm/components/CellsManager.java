package atm.components;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import static atm.components.Banknote.*;

public class CellsManager {

    private Map<Banknote, Cell> cells;
    private AmountListener amountListener;

    public CellsManager() {
        amountListener = new AmountListener();
        CellFactory factory = new CellFactory(amountListener);
        EnumMap<Banknote, Cell> initialCells = new EnumMap<>(Banknote.class);
        // default initial amount in ATM
        initialCells.put(TEN, factory.createCell(TEN, 500));
        initialCells.put(Banknote.FIFTY, factory.createCell(FIFTY, 500));
        initialCells.put(Banknote.HUNDRED, factory.createCell(HUNDRED, 500));
        initialCells.put(Banknote.FIVE_HUNDRED, factory.createCell(FIVE_HUNDRED, 500));
        initialCells.put(Banknote.THOUSAND, factory.createCell(THOUSAND, 500));
        initialCells.put(Banknote.FIVE_THOUSAND, factory.createCell(FIVE_THOUSAND, 500));
        cells = Collections.unmodifiableMap(initialCells);
    }

    public CellsManager(Map<Banknote, Integer> initialMoney) {
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

    public boolean put(StackOfBanknotes banknotes) {
        Cell cell = cells.get(banknotes.getType());
        if (cell.add(banknotes.getCount())) {
            amountListener.add(banknotes);
            return true;
        }
        return false;
    }

    public boolean withdraw(StackOfBanknotes banknotes) {
        Cell cell = cells.get(banknotes.getType());
        if (cell.withdraw(banknotes.getCount())) {
            amountListener.withdraw(banknotes);
            return true;
        }
        return false;
    }

    public int getBanknoteCountAvailable(Banknote banknoteType) {
        return cells.get(banknoteType).getBanknoteCount();
    }

    public int getTotalAmount() {
        return amountListener.getTotalAmount();
    }

}
