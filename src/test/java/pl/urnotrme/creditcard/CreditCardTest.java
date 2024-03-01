package pl.urnotrme.creditcard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class CreditCardTest {

    @Test
    void itAllowsToAssignCreditLimit() {
        //Arrange
        CreditCard card = new CreditCard("1234-4567");
        //Act
        card.assignCredit(BigDecimal.valueOf(1000));
        //Assert
        assertEquals(BigDecimal.valueOf(1000), card.getBalance());
    }

    @Test
    void itAllowsToAssignDifferentCreditLimits() {
        //Arrange
        CreditCard card1 = new CreditCard("1234-4567");
        CreditCard card2 = new CreditCard("1234-49067");

        //Act
        card1.assignCredit(BigDecimal.valueOf(1000));
        card2.assignCredit(BigDecimal.valueOf(1100));
        //Assert
        assertEquals(BigDecimal.valueOf(1000), card1.getBalance());
        assertEquals(BigDecimal.valueOf(1100), card2.getBalance());

    }

    @Test
    void itDenyCreditLimitBelow100v1() {
        CreditCard card1 = new CreditCard("1234-4567");

        try {
            card1.assignCredit(BigDecimal.valueOf(10));
            fail("Should throw exception");
        } catch (CreditBelowThresholdException e) {
            assertTrue(true);
        }
    }

    @Test
    void itDenyCreditLimitBelow100v2() {
        CreditCard card1 = new CreditCard("1234-5678");
        CreditCard card2 = new CreditCard("1234-5678");
        CreditCard card3 = new CreditCard("1234-5678");

        assertThrows(
                CreditBelowThresholdException.class,
                () -> card1.assignCredit(BigDecimal.valueOf(10)));

        assertThrows(
                CreditBelowThresholdException.class,
                () -> card2.assignCredit(BigDecimal.valueOf(99)));

        assertDoesNotThrow(() -> card3.assignCredit(BigDecimal.valueOf(100)));
    }



    @Test
    void itCantAssignLimitTwice(){
        CreditCard card = new CreditCard("1234-4567");
        card.assignCredit(BigDecimal.valueOf(1000));

        assertThrows(
                ReassignCreditExceptions.class,
                () -> card.assignCredit(BigDecimal.valueOf(1100))
        );

    }

    @Test
    void itAllowWithdraw() {
        //Arrange
        CreditCard card1 = new CreditCard("1234-4567");
        card1.assignCredit(BigDecimal.valueOf(1000));

        //Act
        card1.withdraw(BigDecimal.valueOf(100));

        //Assert
        assertEquals(BigDecimal.valueOf(900), card1.getBalance());
    }

    @Test
    void itDenyWithdrawOverBalance() {
        //Arrange
        CreditCard card1 = new CreditCard("1234-4567");
        card1.assignCredit(BigDecimal.valueOf(1000));

        assertDoesNotThrow(() -> card1.withdraw(BigDecimal.valueOf(500)));

        assertThrows(
                NotEnoughMoneyException.class,
                () -> card1.withdraw(BigDecimal.valueOf(600))
        );
    }

    @Test
    void itDenyWithdrawOverLimit() {
        //Arrange
        CreditCard card1 = new CreditCard("1234-4567");
        card1.assignCredit(BigDecimal.valueOf(1000));

        assertThrows(
                AmountOverLimitException.class,
                () -> card1.withdraw(BigDecimal.valueOf(1100))
        );
    }

    @Test
    void itAllowsRepay(){
        CreditCard card1 = new CreditCard("1234-4567");
        card1.assignCredit(BigDecimal.valueOf(1000));

        card1.repayCredit(BigDecimal.valueOf(400));

        assertEquals(BigDecimal.valueOf(600), card1.getCreditToRepay());
    }

    @Test
    void itCantWithdrawOver10Times() {
        CreditCard card1 = new CreditCard("1234-4567");
        card1.assignCredit(BigDecimal.valueOf(2000));

        for (int i = 0; i < 9; i++) {
            card1.withdraw(BigDecimal.valueOf(100));
        }
        assertDoesNotThrow(
                () -> card1.withdraw(BigDecimal.valueOf(100)));
        assertThrows(WithdrawDoneOver10TimesException.class,
                () -> card1.withdraw(BigDecimal.valueOf(100)));
  }

}
