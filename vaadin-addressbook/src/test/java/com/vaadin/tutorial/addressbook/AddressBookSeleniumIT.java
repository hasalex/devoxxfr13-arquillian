package com.vaadin.tutorial.addressbook;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AddressBookSeleniumIT{

    private static WebDriver browser;

    @BeforeClass
    public static void setup() {
        browser = new FirefoxDriver();
        browser.get("http://localhost:8080");
    }
    @AfterClass
    public static void quit() {
        browser.quit();
    }

    @Test
    public void should_home_page_have_ten_lines() {
        WebDriverWait wait = new WebDriverWait(browser, 100);
        WebElement contactListDiv = wait.until(visibilityOfElementLocated(By.id("contactList")));
        WebElement contactListSubDiv = contactListDiv.findElement(By.className("v-table-body"));
        WebElement contactListTable = contactListSubDiv.findElement(By.tagName("table"));
        List<WebElement> contactItems = contactListTable.findElements(By.tagName("tr"));
        assertEquals("Liste des contacts", 10, contactItems.size());
    }

    @Test
    public void should_home_page_have_ten_lines__xpath_version() {
        WebDriverWait wait = new WebDriverWait(browser, 100);
        wait.until(invisibilityOfElementLocated(By.className("v-app-loading")));
        List<WebElement> contactItems = browser.findElements(By.xpath("//div[@id='contactList']/div[contains(@class,'v-table-body')]//table//tr"));
        assertEquals("Liste des contacts", 10, contactItems.size());
    }

    @Test
    public void should_find_modified_contact() {
        WebDriverWait wait = new WebDriverWait(browser, 100);
        WebElement firstContact = wait.until(visibilityOfElementLocated(By.xpath("//div[@id='contactList']/div[contains(@class,'v-table-body')]//table//tr")));
        firstContact.click();

        WebElement lastNameField = wait.until(visibilityOfElementLocated(By.id("LastNameField")));
        lastNameField.sendKeys("ZZZ");
        firstContact.click();

        WebElement searchField = browser.findElement(By.id("SearchField"));
        ((JavascriptExecutor) browser).executeScript("document.getElementById('SearchField').focus();");
        searchField.sendKeys("ZZZ");

        wait.until(invisibilityOfElementLocated(By.xpath("//div[@id='contactList']/div[contains(@class,'v-table-body')]//table//tr[10]")));
        List<WebElement> contactItems = browser.findElements(By.xpath("//div[@id='contactList']/div[contains(@class,'v-table-body')]//table//tr"));
        assertEquals("Liste filtrée des contacts", 1, contactItems.size());
    }

}
