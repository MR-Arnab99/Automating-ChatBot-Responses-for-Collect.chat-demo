package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class CollectChatTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @Test
    public void runChatbot() throws InterruptedException {
        driver.get("https://collect.chat/demo");

        // chat icon launcher outside iframe
        WebElement launcherButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("chat-bot-launcher-button")));
        launcherButton.click();

        // Switch to the iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                By.id("chat-bot-iframe")));

        // First "Yes" clicking
        WebElement firstYes = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'Yes')]")));
        firstYes.click();

        // Second "Yes" using CSS selector
        WebElement secondYes = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.bubble.option.theme-border.theme-color")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", secondYes);
        wait.until(ExpectedConditions.elementToBeClickable(secondYes)).click();

        //  "Google" option clicking
        WebElement googleOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(), 'Google')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", googleOption);
        googleOption.click();
        Thread.sleep(5000);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
