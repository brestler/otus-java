package atm.components;

public class AmountListener {

    private int totalAmount = 0;

    public void add(int amount) {
        totalAmount += amount;
    }

    public void withdraw(int amount) {
        totalAmount -= amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
