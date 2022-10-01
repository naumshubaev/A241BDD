package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.page.CardSelectPage;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.TransactionPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999/");
        var loginPage = new LoginPageV1();
        var credsInfo = DataGenerator.getCredsInfo();
        var verificationPage = loginPage.validLogin(credsInfo);
        var verificationCode = DataGenerator.getVerificationCode(credsInfo);
        var cardSelectPage = new CardSelectPage();
        DataGenerator.getCredsInfo();
        verificationPage.validVerify(verificationCode);
        // проверяем баланс обеих карт до пополнения карты 0001 с карты 0002
        int balanceCardOneBefore = cardSelectPage.getCardBalance(0);
        int balanceCardTwoBefore = cardSelectPage.getCardBalance(1);
        cardSelectPage.clickButton(0);
        TransactionPage.chargeCardOneUsingCardTwo();
        int balanceCardOneAfter = cardSelectPage.getCardBalance(0);
        int balanceCardTwoAfter = cardSelectPage.getCardBalance(1);
        // пополняем карту 0002 с карты 0001
        cardSelectPage.clickButton(1);
        TransactionPage.chargeCardTwoUsingCardOne();
        int balanceCardOneAfterChargingCardTwo = cardSelectPage.getCardBalance(0);
        int balanceCardTwoAfterAfterChargingCardTwo = cardSelectPage.getCardBalance(1);

        // проверяем разницу баланса, я не привязываюсь к захардкоженной цифре "10000" - это значение может измениться в будущем
       assertEquals(balanceCardOneBefore + (Integer.parseInt(DataGenerator.getCardTwoInfo().getTransferAmount())), (balanceCardOneAfter), "баланс карты 0001 должен был увеличиться на сумму транзакции");
       assertEquals(balanceCardTwoBefore - (Integer.parseInt(DataGenerator.getCardTwoInfo().getTransferAmount())), (balanceCardTwoAfter), "баланс карты 0002 должен был уменьшиться на сумму транзакции");
       assertEquals(balanceCardTwoAfter + (Integer.parseInt(DataGenerator.getCardOneInfo().getTransferAmount())), (balanceCardTwoAfterAfterChargingCardTwo), "баланс карты 0002 должен был увеличиться на сумму транзакции");
       assertEquals(balanceCardOneAfter - (Integer.parseInt(DataGenerator.getCardOneInfo().getTransferAmount())), (balanceCardOneAfterChargingCardTwo), "баланс карты 0001 должен был увеличиться на сумму транзакции");
        System.out.println(balanceCardOneAfter);
        System.out.println(balanceCardOneBefore);
       assert(Integer.parseInt(DataGenerator.getCardOneInfo().getTransferAmount()) == balanceCardOneAfter - balanceCardOneAfterChargingCardTwo);
       assert(Integer.parseInt(DataGenerator.getCardOneInfo().getTransferAmount()) == balanceCardTwoAfterAfterChargingCardTwo - balanceCardTwoAfter);

    }
}