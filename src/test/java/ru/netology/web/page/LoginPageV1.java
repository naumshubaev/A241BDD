package ru.netology.web.page;

import ru.netology.web.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

public class LoginPageV1 {
    public VerificationPage validLogin(DataGenerator.CredsInfo credsInfo) {
        $("[data-test-id=login] input").setValue(credsInfo.getLogin());
        $("[data-test-id=password] input").setValue(credsInfo.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }
    public VerificationPage invalidLogin() {
        return null;
    }
}
