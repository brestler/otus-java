package atm.components;

import java.util.ArrayList;
import java.util.List;

public class RegularATMStateSaver {

    private List<RegularATM> initialStates = new ArrayList<>();

    public void saveState(RegularATM atm) {
        initialStates.add(new RegularATM(atm));
    }

    public List<RegularATM> getInitialStates() {
        return initialStates;
    }
}
