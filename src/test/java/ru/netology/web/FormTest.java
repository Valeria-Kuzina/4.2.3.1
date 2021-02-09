package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.cssSelector;

import static ru.netology.web.DataGenerator.*;

public class FormTest {
    final String city = getCity();
    final String date = getRelevantDate(6);
    final String otherdate = getRelevantDate(10);
    final String name = getName();
    final String phone = getPhone();

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(city);
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        form.$(cssSelector("[name=name]")).sendKeys(name);
        form.$(cssSelector("[name=phone]")).sendKeys(phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Запланировать")).click();
        $(byText("Успешно!")).waitUntil(Condition.visible, 15000);
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно Запланирована на " + date));
    }

    @Test
    void shouldRescheduleDateUponRequest() {
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(city);
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        form.$(cssSelector("[name=name]")).sendKeys(name);
        form.$(cssSelector("[name=phone]")).sendKeys(phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Запланировать")).click();
        $(byText("Успешно!")).waitUntil(Condition.visible, 15000);
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно Запланирована на " + date));

        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(otherdate);
        form.$(byText("Запланировать")).click();
        $(cssSelector(".notification_status_error .button")).click();
        $(withText("Успешно!")).waitUntil(Condition.visible, 15000);
    }

}
