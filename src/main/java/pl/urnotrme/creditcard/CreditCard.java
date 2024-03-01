package pl.urnotrme.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal balance;
    private BigDecimal credit;
    private BigDecimal creditToRepay;
    private int withdrawCounter;
    private boolean isAssigned;



    public CreditCard(String cardNumber) {
        this.withdrawCounter = 0;
        this.isAssigned = false;
    }

    public void assignCredit(BigDecimal creditAmount) {

        if (isAlreadyAssigned()) {
            throw new ReassignCreditExceptions();
        }

        if (isBelowThreshold(creditAmount)) {
            throw new CreditBelowThresholdException();
        }

        this.balance = creditAmount;
        this.credit = creditAmount;
        this.creditToRepay = creditAmount;
        this.isAssigned = true;
    }

    private boolean isAlreadyAssigned() {
        return isAssigned;
    }

    private static boolean isBelowThreshold(BigDecimal creditAmount) {
        return creditAmount.compareTo(BigDecimal.valueOf(100)) < 0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCredit() {
        return this.credit;
    }

    public BigDecimal getCreditToRepay() {
        return this.creditToRepay;
    }

    public int getWithdrawCounter() {
        return withdrawCounter;
    }

    private boolean withdrawOver10Times(){
        return this.withdrawCounter > 10;
    }

    public void withdraw(BigDecimal withdrawAmount) {
        this.withdrawCounter ++;
        if (withdrawOver10Times()) {
            throw new WithdrawDoneOver10TimesException();
        }

        if (withdrawAmount.compareTo(this.credit) > 0) {
            throw new AmountOverLimitException();
        }

        if (withdrawAmount.compareTo(this.balance) > 0) {
            throw new NotEnoughMoneyException();
        }
        this.balance = balance.subtract((withdrawAmount));
    }

    public void repayCredit(BigDecimal repayAmount) {
        this.creditToRepay = creditToRepay.subtract((repayAmount));
    }
}
