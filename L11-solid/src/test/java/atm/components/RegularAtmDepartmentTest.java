package atm.components;

import atm.ATM;
import atm.AtmDepartment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegularAtmDepartmentTest {

    @Test
    void shouldRestoreState() {
        AtmDepartment department = new RegularAtmDepartment(1);
        assertEquals(3330000, department.getAmountLeftFromAllATMs());
        department.getATMs().forEach(atm -> atm.withdraw(10000));
        assertEquals(3320000, department.getAmountLeftFromAllATMs());
        department.reset();
        assertEquals(3330000, department.getAmountLeftFromAllATMs());
    }

    @Test
    void shouldRestoreStateInAllAtms() {
        AtmDepartment department = new RegularAtmDepartment(2);
        assertEquals(6660000, department.getAmountLeftFromAllATMs());
        List<ATM> atMs = department.getATMs();
        ATM first = atMs.get(0);
        first.withdraw(3330000);
        ATM second = atMs.get(1);
        second.withdraw(30000);
        assertEquals(0, first.amountLeft());
        assertEquals(3300000, second.amountLeft());
        department.reset();
        assertEquals(6660000, department.getAmountLeftFromAllATMs());
    }

}