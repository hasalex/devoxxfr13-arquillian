package fr.sewatech.arquillian.web;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class BasicTestCase {

    @Drone
    WebDriver driver;

    @Test
    public void testOpeningHomePage() {
        driver.get("http://www.google.com");

        String pageTitle = driver.getTitle();

        assertEquals(pageTitle, "Google");

    }}