package ru.netology.web.page;

import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.web.data.DataGenerator.getCardOneInfo;
import static ru.netology.web.data.DataGenerator.getCardTwoInfo;

public class TransactionPage {
    public static void chargeCardOneUsingCardTwo() {
        $("[data-test-id=\"from\"] input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        $("[data-test-id=\"from\"] input").setValue(getCardTwoInfo().getCardNumber());
        $("[data-test-id=\"amount\"] input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        $("[data-test-id=\"amount\"] input").setValue(getCardTwoInfo().getTransferAmount());
        $("[data-test-id=\"action-transfer\"]").click();
    }

    public static void chargeCardTwoUsingCardOne() {
        $("[data-test-id=\"from\"] input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        $("[data-test-id=\"from\"] input").setValue(getCardOneInfo().getCardNumber());
        $("[data-test-id=\"amount\"] input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        $("[data-test-id=\"amount\"] input").setValue(getCardOneInfo().getTransferAmount());
        $("[data-test-id=\"action-transfer\"]").click();
    }
}
