package com.delkabo.tests;

import com.delkabo.helpers.Attach;
import com.delkabo.drivers.web.BrowserWebDriver;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;


public class TestBase {


    public static String deviceHost = System.getProperty("deviceHost");


    @BeforeAll
    static void setUp() {
        BrowserWebDriver.configure();
    }

    @BeforeEach
    public void startDriver() {
        open();
    }

    @AfterEach
    public void addAttachments() {

        String sessionId;

        switch (deviceHost) {
            case "web":
                sessionId = Attach.sessionId();
                Attach.addBrowserConsoleLogs();
                addAttached();
                if (BrowserWebDriver.isVideoOn()) {
                    Attach.addVideo(sessionId);
                }
                break;
            case "browserstack":
                sessionId = Attach.sessionId();
                addAttached();
                Attach.video(sessionId);
                break;
            default:
                sessionId = "";
                addAttached();
        }
    }

    private void addAttached() {
        Attach.addScreenshotAs("Last screenshot");
        Attach.addPageSource();
        Selenide.closeWebDriver();
    }
}
