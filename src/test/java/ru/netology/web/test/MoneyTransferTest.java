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
        verificationPage.validVerify(verificationCode);
        // проверяем баланс обеих карт до пополнения карты 0001 с карты 0002
        int balanceCardOneBefore = cardSelectPage.getCardBalance(0);
        int balanceCardTwoBefore = cardSelectPage.getCardBalance(1);
        cardSelectPage.clickButton(0);
        TransactionPage.chargeCard('2', "100");
        int balanceCardOneAfter = cardSelectPage.getCardBalance(0);
        int balanceCardTwoAfter = cardSelectPage.getCardBalance(1);
        // пополняем карту 0002 с карты 0001
        cardSelectPage.clickButton(1);
        TransactionPage.chargeCard('1', "200");
        int balanceCardOneAfterChargingCardTwo = cardSelectPage.getCardBalance(0);
        int balanceCardTwoAfterAfterChargingCardTwo = cardSelectPage.getCardBalance(1);

        // сравниваем ожидаемое и фактическое значения баланса, я не привязываюсь к захардкоженной цифре "10000" - это значение может измениться в будущем
       assertEquals(balanceCardOneBefore + (Integer.parseInt(DataGenerator.getCardInfo('2', "100").getTransferAmount())), (balanceCardOneAfter), "баланс карты 0001 должен был увеличиться на сумму транзакции");
       assertEquals(balanceCardTwoBefore - (Integer.parseInt(DataGenerator.getCardInfo('2', "100").getTransferAmount())), (balanceCardTwoAfter), "баланс карты 0002 должен был уменьшиться на сумму транзакции");
       assertEquals(balanceCardTwoAfter + (Integer.parseInt(DataGenerator.getCardInfo('1', "200").getTransferAmount())), (balanceCardTwoAfterAfterChargingCardTwo), "баланс карты 0002 должен был увеличиться на сумму транзакции");
       assertEquals(balanceCardOneAfter - (Integer.parseInt(DataGenerator.getCardInfo('1', "200").getTransferAmount())), (balanceCardOneAfterChargingCardTwo), "баланс карты 0001 должен был увеличиться на сумму транзакции");
        }
}