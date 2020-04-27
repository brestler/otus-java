package atm.components;

public class StackOfBanknotes {

    private Banknote banknoteType;
    private int banknoteCount;

    public static StackOfBanknotes singleBanknoteStack(Banknote banknote) {
        return new StackOfBanknotes(banknote, 1);
    }

    public static StackOfBanknotes stackOfBanknotes(Banknote banknote, int count) {
        return new StackOfBanknotes(banknote, count);
    }

    private StackOfBanknotes(Banknote banknoteType, int banknoteCount) {
        this.banknoteType = banknoteType;
        this.banknoteCount = banknoteCount;
    }

    public Banknote getType() {
        return banknoteType;
    }

    public int getCount() {
        return banknoteCount;
    }
}
