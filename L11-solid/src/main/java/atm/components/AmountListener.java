package atm.components;

public class AmountListener {

    private int totalAmount = 0;

    public void add(Banknote banknoteType, int count) {
        totalAmount += banknoteType.getNominal() * count;
    }

    public void add(StackOfBanknotes stackOfBanknotes) {
        this.add(stackOfBanknotes.getType(), stackOfBanknotes.getCount());
    }

    public void withdraw(Banknote banknoteType, int count) {
        totalAmount -= banknoteType.getNominal() * count;
    }

    public void withdraw(StackOfBanknotes stackOfBanknotes) {
        this.withdraw(stackOfBanknotes.getType(), stackOfBanknotes.getCount());
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
