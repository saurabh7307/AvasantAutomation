import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

public class AvasantAutomation {

    public static WebDriver webDriver;
    public static Properties dataFileProperties;
    String URL = "https://avasant.com/";
    String userEmail = "sau7307@gmail.com";
    String userPassword = "Avasant@12345678";
    ExtentHtmlReporter htmlReporter;
    ExtentReports report;
    public static ExtentTest testinfo;

    /************************ Few Utils Function **********************************************/
    public static void switchToNewTab(int tabIndex) {
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(tabIndex));
    }

    public static void closeCurrentTab() {
        webDriver.close();
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(0)); // switches to previous tab
    }

    // wait for the visibility of element till given time
    public static void waitForVisibilityOfElement(By path, int timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecond);
        wait.until(ExpectedConditions.visibilityOfElementLocated(path));
    }

    // wait for the element to be clickable till given time
    public static void waitForElementToBeClickable(By path, int timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecond);
        wait.until(ExpectedConditions.elementToBeClickable(path));
    }

    /************************ Automation **********************************************/

    @BeforeSuite
    public void setUp() throws IOException {
        htmlReporter = new ExtentHtmlReporter("AvasantAutomationReport.html");
        // create ExtentReports and attach reporter(s)
        report = new ExtentReports();
        report.attachReporter(htmlReporter);
        // Set up chrome driver
        System.setProperty(
                "webDriver.chrome.driver",
                System.getProperty("user.dir") + "/chromedriver"); // Set Property
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        webDriver = new ChromeDriver(options);
        //Read Data from data properties file
        dataFileProperties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/Properties/datafile.properties");
        dataFileProperties.load(fileInputStream);
        webDriver.manage().window().maximize();
    }

    @BeforeMethod
    public void register(Method method) {
        String testName = method.getName();
        testinfo = report.createTest(testName);
    }

    @Test(priority = 1)
    public void openHomePage() {
//        webDriver.get(dataFileProperties.getProperty("WEBSITE_URL"));
        System.out.println(dataFileProperties.getProperty("WEBSITE_URL"));
        testinfo.info("String" + "hello");
    }
    @AfterMethod
    public void getResult(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            testinfo.log(Status.FAIL, "Test Method name as : " + result.getName() + " is failed");
            testinfo.log(Status.FAIL, "Test failure :" + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            testinfo.log(Status.PASS, "Test Method name as : " + result.getName() + " is passed");
        }
    }

    @AfterSuite
    public void tearDown() {
        webDriver.quit();
        report.flush();
    }
}
