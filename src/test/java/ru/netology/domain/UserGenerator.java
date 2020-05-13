package ru.netology.domain;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

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
    }
}
