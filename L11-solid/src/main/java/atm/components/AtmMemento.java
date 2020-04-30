package atm.components;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class AtmMemento {

    private Map<Integer, Deque<Map<Banknote, Integer>>> statesHistory = new HashMap<>();

    public void saveState(RegularATM atm) {
        Deque<Map<Banknote, Integer>> history = statesHistory.computeIfAbsent(atm.getId(), (id) -> new ArrayDeque<>());
        history.push(atm.getSnapshot());
    }

    public Deque<Map<Banknote, Integer>> getHistoryOfAtm(int id) {
        return statesHistory.get(id);
    }
}
