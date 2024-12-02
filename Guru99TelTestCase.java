package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Guru99TelTestClass {

    private static final WebDriver driver = new ChromeDriver();

    public static String addCustomer() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/telecom/addcustomer.php");

        // Wait for the page to load
        Thread.sleep(5000);

        // Fill out the customer form
        driver.findElement(By.id("fname")).sendKeys("RB");
        driver.findElement(By.id("lname")).sendKeys("Chowdhury");
        driver.findElement(By.id("email")).sendKeys("contact@rbctcsworld.com");
        driver.findElement(By.name("addr")).sendKeys("506 Jefferson St PA 19462");
        driver.findElement(By.id("telephoneno")).sendKeys("2673425565");

        // Select "Done" radio button
        driver.findElement(By.xpath("//*[@id=\"main\"]/div/form/div/div[1]/label")).click();

        // Submit the form
        driver.findElement(By.name("submit")).click();

        // Retrieve the Customer ID
        Thread.sleep(5000);
        WebElement customerIdElement = driver.findElement(By.xpath("//table//tr[1]//td[2]"));
        String customerId = customerIdElement.getText();
        System.out.println("Customer ID: " + customerId);

        return customerId;
    }

    public static void addTariff() throws InterruptedException {
        driver.get("https://demo.guru99.com/telecom/addtariffplans.php");

        // Wait for the page to load
        Thread.sleep(5000);

        // Fill out the tariff plan form
        driver.findElement(By.id("rental1")).sendKeys("110");
        driver.findElement(By.id("local_minutes")).sendKeys("510");
        driver.findElement(By.id("inter_minutes")).sendKeys("310");
        driver.findElement(By.id("sms_pack")).sendKeys("160");
        driver.findElement(By.id("minutes_charges")).sendKeys("260");
        driver.findElement(By.id("inter_charges")).sendKeys("370");
        driver.findElement(By.id("sms_charges")).sendKeys("50");

        // Submit the form
        driver.findElement(By.name("submit")).click();

        // Validate success
        Thread.sleep(5000);
        WebElement successMessage = driver.findElement(By.xpath("//h2[contains(text(),'Congratulation')]"));
        if (successMessage.isDisplayed()) {
            System.out.println("Tariff plan added successfully.");
        } else {
            System.out.println("Failed to add tariff plan.");
        }
    }

    public static void addCustomerToTariff(String customerId) throws InterruptedException {
        driver.get("https://demo.guru99.com/telecom/assigntariffplantocustomer.php");

        // Wait for the page to load
        Thread.sleep(5000);

        // Assign tariff to customer
        driver.findElement(By.id("customer_id")).sendKeys(customerId);
        driver.findElement(By.name("submit")).click();

        // Validate assignment success
        Thread.sleep(5000);

        try {
            WebElement successMessage = driver.findElement(By.xpath("//h2[contains(text(),'Congratulation')]"));
            if (successMessage.isDisplayed()) {
                System.out.println("Tariff plan assigned to customer successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to assign tariff plan. Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            String customerId = addCustomer();
            addTariff();
            addCustomerToTariff(customerId);
        } catch (InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
