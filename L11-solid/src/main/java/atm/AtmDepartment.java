package atm;

import java.util.List;

public interface AtmDepartment {

    List<ATM> getATMs();
    long getAmountLeftFromAllATMs();
    void reset();
}
