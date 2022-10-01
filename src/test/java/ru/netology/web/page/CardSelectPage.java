package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardSelectPage {

    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public CardSelectPage() {}

    public int getCardBalance(int index) {
        val text = cards.get(index).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

        // there is a Charge button for each card on this page, so I use index to pass a parameter, for example, '0' for card one
        // I believe my approach gives us an easily scalable solution
        public CardSelectPage clickButton(int index) {
            $$("[data-test-id=\"action-deposit\"]").get(index).shouldBe(Condition.visible).click();
            return new CardSelectPage();
        }
    }
