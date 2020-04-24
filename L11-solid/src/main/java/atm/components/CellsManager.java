package atm.components;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CellsManager {

    private Map<Banknote, Cell> cells;
    private AmountListener amountListener;

    public CellsManager() {
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

    public boolean put(StackOfBanknotes unit) {
        Cell cell = cells.get(unit.getType());
        return cell.add(unit.getCount());
    }

    public boolean withdraw(StackOfBanknotes unit) {
        Cell cell = cells.get(unit.getType());
        return cell.withdraw(unit.getCount());
    }

    public int withdrawAll(Banknote banknoteType) {
        Cell cell = cells.get(banknoteType);
        return cell.withdrawAll();
    }

    public int getBanknoteCountAvailable(Banknote banknoteType) {
        return cells.get(banknoteType).getBanknoteCount();
    }

    public int getTotalAmount() {
        return amountListener.getTotalAmount();
    }

}
