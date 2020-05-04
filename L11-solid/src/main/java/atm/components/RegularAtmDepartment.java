package atm.components;

import atm.ATM;
import atm.AtmDepartment;

import java.util.ArrayList;
import java.util.List;

public class RegularAtmDepartment implements AtmDepartment {

    private final List<RegularATM> atms;
    private final ResetCommand resetCommand;

    public RegularAtmDepartment(int countOfAtms) {
        atms = new ArrayList<>(countOfAtms);
        resetCommand = new ResetCommand();
        for (int i = 0; i < countOfAtms; i++) {
            RegularATM atm = new RegularATM(i);
            atm.saveState();
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
        atms.forEach(resetCommand::execute);
    }
}
