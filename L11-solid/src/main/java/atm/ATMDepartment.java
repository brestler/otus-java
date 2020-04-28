package atm;

import java.util.List;

public interface ATMDepartment {

    List<ATM> getATMs();
    long getAmountLeftFromAllATMs();
    void reset();
}
