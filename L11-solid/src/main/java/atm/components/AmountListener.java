package atm.components;

public class AmountListener {

    private int totalAmount;

    public void add(Banknote banknoteType, int count) {
        totalAmount += banknoteType.getNominal() * count;
    }

    public void withdraw(Banknote banknoteType, int count) {
        totalAmount -= banknoteType.getNominal() * count;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
