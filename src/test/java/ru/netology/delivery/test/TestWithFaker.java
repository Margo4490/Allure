package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class TestWithFaker {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() { SelenideLogger.removeListener("allure"); }


    @Test
    void happyPathAfterChangeDate() {

        Configuration.holdBrowserOpen = true;
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").val(DataGenerator.generateCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        String scheduledDate = generateDate(3);

        $("[data-test-id='date'] input").val(scheduledDate);
        $("[data-test-id='name'] input").val(DataGenerator.getName());
        $("[data-test-id='phone'] input").val(DataGenerator.getNumberPhone());
        $("[data-test-id='agreement']").click();
        $("div .button").click();
        $("[data-test-id='success-notification'] .notification__title").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + scheduledDate), Duration.ofSeconds(15));

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        String rescheduledDate = generateDate(6);

        $("[data-test-id='date'] input").val(rescheduledDate);
        $("div .button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + rescheduledDate), Duration.ofSeconds(15));

    }
}
