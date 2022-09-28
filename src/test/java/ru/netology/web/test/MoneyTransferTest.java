package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.page.CardSelectPage;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.TransactionPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999/");
        var loginPage = new LoginPageV1();
        var credsInfo = DataGenerator.getCredsInfo();
        var verificationPage = loginPage.validLogin(credsInfo);
        var verificationCode = DataGenerator.getVerificationCode(credsInfo);
        var cardSelectPage = new CardSelectPage();
        var transactionPage = new TransactionPage();
        DataGenerator.getCredsInfo();
        verificationPage.validVerify(verificationCode);
        // проверяем баланс обеих карт до пополнения карты 0001 с карты 0002
        int balanceCardOneBefore = cardSelectPage.getCardBalance(0);
        int balanceCardTwoBefore = cardSelectPage.getCardBalance(1);
        cardSelectPage.clickButton(0);
        transactionPage.chargeCardOneUsingCardTwo();
        int balanceCardOneAfter = cardSelectPage.getCardBalance(0);
        int balanceCardTwoAfter = cardSelectPage.getCardBalance(1);
        // пополняем карту 0002 с карты 0001
        cardSelectPage.clickButton(1);
        transactionPage.chargeCardTwoUsingCardOne();
        int balanceCardOneAfterChargingCardTwo = cardSelectPage.getCardBalance(0);
        int balanceCardTwoAfterAfterChargingCardTwo = cardSelectPage.getCardBalance(1);

        // проверяем разницу баланса, я не привязываюсь к захардкоженной цифре "10000" - это значение может измениться в будущем
       assert(Integer.parseInt(DataGenerator.getCardTwoInfo().getTransferAmount()) == balanceCardOneAfter - balanceCardOneBefore);
       assert(Integer.parseInt(DataGenerator.getCardTwoInfo().getTransferAmount()) == balanceCardTwoBefore - balanceCardTwoAfter);
       assert(Integer.parseInt(DataGenerator.getCardOneInfo().getTransferAmount()) == balanceCardOneAfter - balanceCardOneAfterChargingCardTwo);
       assert(Integer.parseInt(DataGenerator.getCardOneInfo().getTransferAmount()) == balanceCardTwoAfterAfterChargingCardTwo - balanceCardTwoAfter);

    }
}