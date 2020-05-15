package ru.netology.domain;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @Test
    void shouldWithValidInfo() {
        open("http://localhost:9999");
        UserGenerator.Registration.validInfo();
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Личный кабинет")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldWithValidInfoButStatusBlocked() {
        open("http://localhost:9999");
        UserGenerator.Registration.validInfoButStatusBlocked();
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldWithoutPassword() {
        open("http://localhost:9999");
        UserGenerator.Registration.withoutPassword();
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldWithoutLogin() {
        open("http://localhost:9999");
        UserGenerator.Registration.withoutLogin();
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldValidPasswordButNotValidLogin() {
        open("http://localhost:9999");
        UserGenerator.Registration.validPasswordButNotValidLogin();
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldValidLoginButNotValidPassword() {
        open("http://localhost:9999");
        UserGenerator.Registration.validLoginButNotValidPassword();
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldValidInfoButWithoutRegistration() {
        open("http://localhost:9999");
        UserGenerator.Registration.validInfoButWithoutRegistration();
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }
}