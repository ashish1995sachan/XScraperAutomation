package demo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import com.fasterxml.jackson.databind.ObjectMapper;



public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @Test
    public void testCase01() throws InterruptedException, IOException {
        System.out.println("Start of Test Case 01");           //Test Case 01 Starts
        driver.get("https://www.scrapethissite.com/pages/");
        WebElement link1 = driver.findElement(By.xpath("//a[text()='Hockey Teams: Forms, Searching and Pagination']"));
        link1.click();
        Thread.sleep(2000);
        
        ArrayList <HashMap<String , Object>> dataSet = new ArrayList<>();
        
        
        Thread.sleep(2000);
        for(int pages = 1 ; pages <= 4 ; pages++){
            WebElement page = driver.findElement(By.xpath("//ul[@class='pagination']//li//a[contains(text(),'" + pages + "')]"));
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='pagination']//li//a[contains(text(),'" + pages + "')]")));
            page.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='pagination']//li//a[contains(text(),'" + pages + "')]")));
            System.out.println("Page " + pages);
            
            List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[@class='team']"));
               
        for (WebElement row : rows) {
            String teamName = row.findElement(By.xpath("./td[@class='name']")).getText();

            int year =Integer.parseInt(row.findElement(By.xpath("./td[@class='year']")).getText());

            double winPercentage = Double.parseDouble(row.findElement(By.xpath("./td[contains(@class,'pct')]")).getText());
        
            long epochTime = System.currentTimeMillis()/1000;

            String epoch = String.valueOf(epochTime);

            if(winPercentage <= 0.400){
                HashMap<String , Object> map = new HashMap<>();
                map.put("EpochTime", epoch);
                map.put("TeamName", teamName);
                map.put("Year", year);
                map.put("WinPercentage", winPercentage);

                dataSet.add(map);
            }
        }

        
            System.out.println("Printing data for page "+ pages);
            for(HashMap<String ,Object> data : dataSet){
            System.out.println("Epoch :"+data.get("EpochTime"));
            System.out.println("Team :"+data.get("TeamName"));
            System.out.println("Year :"+data.get("Year"));
            System.out.println("Win Percentage :"+data.get("WinPercentage"));
            System.out.println("");
        }
        if(pages != 4  ){
        System.out.println("Next Page");
        }
        }
        Thread.sleep(2000);
         //Printing Data in json File
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/test/hockey-team-data.json");
        mapper.writeValue(jsonFile,dataSet);
        System.out.println("JSON Data written to the file as" + jsonFile);
        System.out.println("Absolute Path of Json file is" + jsonFile.getAbsolutePath());

        

        System.out.println("End of Test Case 01");           //Test Case 01 Ends
    }



    @Test
    public void testCase02() throws InterruptedException, IOException{
        System.out.println("Start of Test Case 02");           //Test Case 02 Starts
        driver.get("https://www.scrapethissite.com/pages/");
        WebElement link2 = driver.findElement(By.xpath("//a[text()='Oscar Winning Films: AJAX and Javascript']"));
        link2.click();

        ArrayList <HashMap<String , Object>> movieSet = new ArrayList<>();
        Thread.sleep(2000);

        for(int pages = 2015 ; pages >= 2010 ; pages--){
            WebElement page = driver.findElement(By.xpath("//a[@id='" + pages + "']"));
            page.click();
            
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table']")));
            System.out.println("Year " + pages);
            
        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[@class='film']"));
        int count  = 0;
        for ( int row = 1 ; row <=5 ; row++) {
            String filmTitle = driver.findElement(By.xpath("(//table//tbody//tr[@class='film']//td[contains(@class,'title')])[" + row + "]")).getText();

            int nominations =Integer.parseInt(driver.findElement(By.xpath("(//table//tbody//tr[@class='film']//td[contains(@class,'nominations')])[" + row + "]")).getText());

            int awards =Integer.parseInt(driver.findElement(By.xpath("(//table//tbody//tr[@class='film']//td[contains(@class,'awards')])[" + row + "]")).getText());
            
            int year = pages;

            long epochTime = System.currentTimeMillis()/1000;
            String epoch = String.valueOf(epochTime);
            
            boolean isWinningFlag = count == 0 ;
            String isWinning = String.valueOf(isWinningFlag);

            
                HashMap<String , Object> map = new HashMap<>();
                map.put("EpochTime", epoch);
                map.put("FilmName", filmTitle);
                map.put("Nominations", nominations);
                map.put("Awards", awards);
                map.put("Year", year);
                map.put("IsWinner", isWinning);
                movieSet.add(map);

                count++;
            
        }
        for(HashMap<String ,Object> data : movieSet){
            System.out.println("Epoch :"+data.get("EpochTime"));
            System.out.println("Film Name :"+data.get("FilmName"));
            System.out.println("Year :"+data.get("Year"));
            System.out.println("Nominations :"+data.get("Nominations"));
            System.out.println("Award Number :"+data.get("Awards"));
            System.out.println("Winner :"+data.get("IsWinner"));

            System.out.println("");
        }
        System.out.println("Next Page");
        
    }
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/test/oscar-winner-data.json");
        mapper.writeValue(jsonFile,movieSet);
        System.out.println("JSON Data written to the file as" + jsonFile);
        System.out.println("Absolute Path of Json file is" + jsonFile.getAbsolutePath());

        System.out.println("End of Test Case 02");           //Test Case 02 Ends
    }




    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        //driver.close();
        //driver.quit();

    }
}