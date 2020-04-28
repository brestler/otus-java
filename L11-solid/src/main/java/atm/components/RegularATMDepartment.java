package atm.components;

import atm.ATM;
import atm.ATMDepartment;

import java.util.ArrayList;
import java.util.List;

public class RegularATMDepartment implements ATMDepartment {

    private List<RegularATM> atms;
    private RegularATMStateSaver stateSaver;

    public RegularATMDepartment(int countOfAtms) {
        atms = new ArrayList<>(countOfAtms);
        stateSaver = new RegularATMStateSaver();
        for (int i = 0; i < countOfAtms; i++) {
            RegularATM atm = new RegularATM();
            stateSaver.saveState(atm);
            atms.add(atm);
        }
    }

    @Override
    public List<ATM> getATMs() {
        return new ArrayList<>(atms);
    }

    @Override
    public long getAmountLeftFromAllATMs() {
        return atms.stream().mapToInt(ATM::amountLeft).sum();
    }

    @Override
    public void reset() {
        atms = stateSaver.getInitialStates();
    }
}
