package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class UserGenerator {
    private UserGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        @BeforeAll
        static void setUpAll(RegistrationData registrationData) {
            given()
                    .spec(requestSpec)
                    .body(registrationData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static RegistrationData generateValidActiveUser() {
            Faker faker = new Faker(new Locale("ru"));
            RegistrationData registrationData = new RegistrationData(
                    faker.name().firstName(),
                    faker.internet().password(),
                    Status.active);
            setUpAll(registrationData);
            return registrationData;
        }

        public static RegistrationData generateValidButBlockedUser() {
            Faker faker = new Faker(new Locale("ru"));
            RegistrationData registrationData = new RegistrationData(
                    faker.name().firstName(),
                    faker.internet().password(),
                    Status.blocked);
            setUpAll(registrationData);
            return registrationData;
        }

        public static RegistrationData generateUserWithoutRegistration() {
            Faker faker = new Faker(new Locale("ru"));
            RegistrationData registrationData = new RegistrationData(
                    faker.name().firstName(),
                    faker.internet().password(),
                    Status.active);
            return registrationData;
        }

        public static void validInfo() {
            val person = UserGenerator.Registration.generateValidActiveUser();
            $("input[name ='login']").setValue(person.getLogin());
            $("input[name='password']").setValue(person.getPassword());
        }

        public static void validInfoButStatusBlocked() {
            val person = UserGenerator.Registration.generateValidButBlockedUser();
            $("input[name ='login']").setValue(person.getLogin());
            $("input[name='password']").setValue(person.getPassword());
        }

        public static void withoutPassword() {
            val person = UserGenerator.Registration.generateValidActiveUser();
            $("input[name ='login']").setValue(person.getLogin());
            $("input[name='password']").setValue(" ");
        }

        public static void withoutLogin() {
            val person = UserGenerator.Registration.generateValidActiveUser();
            $("input[name ='login']").setValue(" ");
            $("input[name='password']").setValue(person.getPassword());
        }

        public static void validPasswordButNotValidLogin() {
            val person = UserGenerator.Registration.generateValidActiveUser();
            $("input[name ='login']").setValue("Anna");
            $("input[name='password']").setValue(person.getPassword());
        }

        public static void validLoginButNotValidPassword() {
            val person = UserGenerator.Registration.generateValidActiveUser();
            $("input[name ='login']").setValue(person.getLogin());
            $("input[name='password']").setValue("person");
        }

        public static void validInfoButWithoutRegistration() {
            val person = UserGenerator.Registration.generateUserWithoutRegistration();
            $("input[name ='login']").setValue(person.getLogin());
            $("input[name='password']").setValue(person.getPassword());
        }
    }
}