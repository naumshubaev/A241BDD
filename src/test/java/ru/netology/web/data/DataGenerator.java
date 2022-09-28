package ru.netology.web.data;

import lombok.Value;

public class DataGenerator {
    private DataGenerator () {}

    @Value
    public static class CredsInfo {
        private String login;
        private String password;
    }

    public static CredsInfo getCredsInfo() {return new CredsInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode{
        private String code;
    }
    public static VerificationCode getVerificationCode(CredsInfo getCredsInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String transferAmount;
    }
    public static CardInfo getCardOneInfo() { return new CardInfo("5559_0000_0000_0001", "100"); }

    public static CardInfo getCardTwoInfo() { return new CardInfo("5559_0000_0000_0002", "200"); }

}
