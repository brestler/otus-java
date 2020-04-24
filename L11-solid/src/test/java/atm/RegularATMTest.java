package atm;

import atm.components.Banknote;
import atm.components.RegularATM;
import atm.exceptions.ATMException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static atm.components.Banknote.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegularATMTest {

    @ParameterizedTest
    @MethodSource("provideBanknotesList")
    void shouldAcceptBanknotes(List<Banknote> banknotes, int totalAmountExpected) {
        ATM atm = new RegularATM(emptyMap());
        assertEquals(0, atm.amountLeft());
        atm.accept(banknotes);
        assertEquals(totalAmountExpected, atm.amountLeft());
    }

    private static Stream<Arguments> provideBanknotesList() {
        return Stream.of(
                Arguments.of(emptyList(), 0),
                Arguments.of(List.of(TEN), 10),
                Arguments.of(List.of(HUNDRED, TEN), 110)
        );
    }

    @Test
    void shouldThrowExceptionAndRollBackAmountWhenCellIsOverFilled() {
        ATM atm = new RegularATM(Map.of(TEN, 999));
        assertEquals(9990, atm.amountLeft());
        assertThrows(ATMException.class, () -> atm.accept(List.of(HUNDRED, TEN, TEN)));
        assertEquals(9990, atm.amountLeft());
    }

    @Test
    void shouldThrowExceptionIfRequestedAmountIsMoreThenLeft() {
        ATM atm = new RegularATM(Map.of(TEN, 1));
        assertEquals(10, atm.amountLeft());
        assertThrows(ATMException.class, () -> atm.withdraw(100));
    }

    @Test
    void shouldReduceAmount() {
        ATM atm = new RegularATM(Map.of(TEN, 10));
        assertEquals(100, atm.amountLeft());
        atm.withdraw(10);
        assertEquals(90, atm.amountLeft());
    }

    @Test
    void shouldReduceAmountUsingDifferentBanknotes() {
        ATM atm = new RegularATM(Map.of(FIFTY, 1, TEN, 10));
        assertEquals(150, atm.amountLeft());
        Map<Banknote, Integer> result = atm.withdraw(85);
        assertEquals(1, result.get(FIFTY));
        assertEquals(3, result.get(TEN));
    }

    @Test
    void shouldThrowExceptionIfCantReturnAmountWithAvailableNominals() {
        ATM atm = new RegularATM(Map.of(FIFTY, 1, TEN, 2));
        assertThrows(ATMException.class, () -> atm.withdraw(40));
    }

    @Test
    void shouldIgnoreAmountLessThenTen() {
        ATM atm = new RegularATM(Map.of(TEN, 2));
        Map<Banknote, Integer> result = atm.withdraw(15);
        assertEquals(1, result.get(TEN));
        assertEquals(10, atm.amountLeft());
    }

}