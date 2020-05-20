package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldWithValidInfo() {
        open("http://localhost:9999");
        val person = UserGenerator.Registration.generateValidActiveUser();
        $("input[name ='login']").setValue(person.getLogin());
        $("input[name='password']").setValue(person.getPassword());
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Личный кабинет")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldWithValidInfoButStatusBlocked() {
        open("http://localhost:9999");
        val person = UserGenerator.Registration.generateValidButBlockedUser();
        $("input[name ='login']").setValue(person.getLogin());
        $("input[name='password']").setValue(person.getPassword());
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldValidInfoButWithoutRegistration() {
        open("http://localhost:9999");
        val person = UserGenerator.Registration.generateUserWithoutRegistration();
        $("input[name ='login']").setValue(person.getLogin());
        $("input[name='password']").setValue(person.getPassword());
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldWithoutPassword() {
        open("http://localhost:9999");
        val person = UserGenerator.Registration.generateValidActiveUser();
        $("input[name ='login']").setValue(person.getLogin());
        $("input[name='password']").setValue(" ");
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldWithoutLogin() {
        open("http://localhost:9999");
        val person = UserGenerator.Registration.generateValidActiveUser();
        $("input[name ='login']").setValue(" ");
        $("input[name='password']").setValue(person.getPassword());
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldValidPasswordButNotValidLogin() {
        open("http://localhost:9999");
        val person = UserGenerator.Registration.generateUserWithInvalidLogin();
        $("input[name ='login']").setValue(person.getLogin());
        $("input[name='password']").setValue(person.getPassword());
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }

    @Test
    void shouldValidLoginButNotValidPassword() {
        open("http://localhost:9999");
        val person = UserGenerator.Registration.generateUserWithInvalidPassword();
        $("input[name ='login']").setValue(person.getLogin());
        $("input[name='password']").setValue(person.getPassword());
        $("button[type='button'][data-test-id='action-login']").click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 5000);
    }
}