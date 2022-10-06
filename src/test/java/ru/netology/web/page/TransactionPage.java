package ru.netology.web.page;

import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.web.data.DataGenerator.getCardInfo;

public class TransactionPage {

    public void chargeCard(char cardNumberEnding, String transferAmount) {
        $("[data-test-id=\"from\"] input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        $("[data-test-id=\"from\"] input").setValue(getCardInfo(cardNumberEnding, transferAmount).getCardNumber());
        $("[data-test-id=\"amount\"] input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        $("[data-test-id=\"amount\"] input").setValue(getCardInfo(cardNumberEnding, transferAmount).getTransferAmount());
        $("[data-test-id=\"action-transfer\"]").click();
    }
}
