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

    public void add(int count) {
        banknoteCount += count;
    }

    public void remove(int count) {
        banknoteCount -= count;
    }

    public void removeAll() {
        banknoteCount = 0;
    }

    public int getMoneyAmount() {
        return banknoteType.getNominal() * banknoteCount;
    }

    public Banknote getType() {
        return banknoteType;
    }

    public int getCount() {
        return banknoteCount;
    }
}
