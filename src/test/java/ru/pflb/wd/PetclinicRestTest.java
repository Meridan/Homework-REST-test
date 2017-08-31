package ru.pflb.wd;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.hamcrest.Matchers.*;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class PetclinicRestTest {

    private static final String BASE_URI = "http://localhost:9966/petclinic";

    @Test
    public void shouldFindOwnerAndAddPet() {

        // Генерируем рандомные данные (буквенные имя, фамилию, адрес, город и цифровой телефон)
        String newFirstName = capitalize(RandomStringUtils.randomAlphabetic(6));
        String newLastName = capitalize(RandomStringUtils.randomAlphabetic(10));
        String newFullName = newFirstName + ' ' + newLastName; // Склеиваем имя с фамилией для последующей проверки
        String newAddress = RandomStringUtils.randomAlphanumeric(14);
        String newCity = capitalize(RandomStringUtils.randomAlphabetic(6));
        String newTelephone = RandomStringUtils.randomNumeric(10);

        // Составление тела запроса на добавление нового владельца
        JSONObject petJsonObj = new JSONObject()
                .put("id", JSONObject.NULL)
                .put("firstName", newFirstName)
                .put("lastName", newLastName)
                .put("address", newAddress)
                .put("city", newCity)
                .put("telephone", newTelephone);

        Integer nameId = given()
                .contentType("application/json")
                .body(petJsonObj.toString())
                .baseUri(BASE_URI)
                .when()
                .post("/api/owners")
                .then()
                // expect 2xx response code
                .statusCode(equalTo(201))
                // Проверка наличия нового пользователя
                .body("firstName", equalTo(newFirstName))
                .extract().path("id");

        System.out.println("Добавлен владелец с id=\"" + nameId + "\"");

        // http://localhost:9966/petclinic/api/owners/*/lastname/Franklin

    }
}
