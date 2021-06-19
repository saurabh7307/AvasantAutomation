import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class AvasantAutomation {

    public static WebDriver webDriver;
    public static Properties dataFileProperties;
    ExtentHtmlReporter htmlReporter;
    ExtentReports report;
    public static ExtentTest testinfo;
    boolean flag;
    int WAIT = 15;
    String url, path = System.getProperty("user.dir") + "/src/main/resources/download";

    /************************ Few Utils Function **********************************************/
    // wait for the visibility of element till given time
    public static void waitForVisibilityOfElement(By path, int timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecond);
        wait.until(ExpectedConditions.visibilityOfElementLocated(path));
    }

    public static void openUrlNewTab(String url) {
        ((JavascriptExecutor) webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
        webDriver.get(url);
    }

    /************************ Automation **********************************************/

    @BeforeSuite
    public void setUp() throws Exception {
        htmlReporter = new ExtentHtmlReporter("AvasantAutomationReport.html");
        // create ExtentReports and attach reporter(s)
        report = new ExtentReports();
        report.attachReporter(htmlReporter);
        // Set up chrome driver
        System.setProperty("webDriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver"); // Set Property
        System.setProperty("webDriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe"); // for windows
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("prefs", chromePrefs);
        webDriver = new ChromeDriver(options);
        //Read Data from data properties file
        dataFileProperties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/Properties/datafile.properties");
        dataFileProperties.load(fileInputStream);
        webDriver.manage().window().maximize();
        MyScreenRecorder.startRecording("AutomationTest");
    }

    @BeforeMethod
    public void register(Method method) {
        String testName = method.getName();
        testinfo = report.createTest(testName);
    }

    @Test(priority = 1)
    public void verifyOpenHomePage() {
        webDriver.get(dataFileProperties.getProperty("WEBSITE_URL"));
    }

    @Test(priority = 2)
    public void verifyOpenLogInPage() {
        webDriver.findElement(AvasantElements.ACCOUNT_MENU_ICON).click();
        Assert.assertTrue(webDriver.findElement(AvasantElements.LOG_IN_HEADING).isDisplayed());  // Verify Log_in screen is Open
    }

    @Test(priority = 3)
    public void verifyLogIn() {
        webDriver.findElement(AvasantElements.USER_NAME_INPUT_TEXT_AREA).sendKeys(dataFileProperties.getProperty("EMAIL"));
        webDriver.findElement(AvasantElements.PASSWORD_INPUT_TEXT_AREA).sendKeys(dataFileProperties.getProperty("PASSWORD"));
        webDriver.findElement(AvasantElements.LOG_IN_BUTTON).click();
        waitForVisibilityOfElement(AvasantElements.LOGGED_IN_WELCOME_STRING, WAIT);
        String userName = webDriver.findElement(AvasantElements.LOGGED_IN_WELCOME_STRING).getText();
        testinfo.info("Logged in user welcome string : " + userName); // add welcome string of logged in user to the report
        WebElement userNameElement = webDriver.findElement(By.xpath(String.format(AvasantElements.USER_NAME, userName)));
        Assert.assertTrue(userNameElement.isDisplayed()); // verify User detail
    }

    @Test(priority = 4)
    public void verifyOpenAccessReportPage() {
        waitForVisibilityOfElement(AvasantElements.RESEARCH_AND_DATA_NAVIGATION, WAIT);
        WebElement accessReportElement = webDriver.findElement(AvasantElements.RESEARCH_AND_DATA_NAVIGATION);
        Actions actions = new Actions(webDriver);
        actions.moveToElement(accessReportElement).perform();
        webDriver.findElement(AvasantElements.ACCESS_REPORT_OPTION).click();
        waitForVisibilityOfElement(AvasantElements.STRING_ON_ACCESS_REPORT_PAGE, WAIT);
        Assert.assertTrue(webDriver.findElement(AvasantElements.STRING_ON_ACCESS_REPORT_PAGE).isDisplayed());  // verify string on access report page
    }

    @Test(priority = 5)
    public void verifySearchReportAndClickFirst() {
        waitForVisibilityOfElement(AvasantElements.SEARCH_BAR, WAIT);
        webDriver.findElement(AvasantElements.SEARCH_BAR).sendKeys(dataFileProperties.getProperty("SEARCH_REPORT_NAME"));
        webDriver.findElement(AvasantElements.SEARCH_ICON).click();
        waitForVisibilityOfElement(AvasantElements.HEADING_SEARCH_PAGE, WAIT);
        url = webDriver.findElement(AvasantElements.FIRST_REPORT).getAttribute("href");
        testinfo.info("First report URL is : " + url);
    }

    @Test(priority = 6)
    public void verifyOpenAndDownloadReport() {
        openUrlNewTab(url);
        waitForVisibilityOfElement(AvasantElements.DOWNLOAD_REPORT_HEADING, WAIT);
        String currentUrl = webDriver.getCurrentUrl();
        Assert.assertEquals(url, currentUrl); // verify URL
        testinfo.info("Actual URL : " + url);
        testinfo.info("Current URL : " + currentUrl);
    }

    @Test(priority = 7)
    public void verifyDownloadReport() throws Exception {
        flag = webDriver.findElement(AvasantElements.DOWNLOAD_REPORT_HEADING).isDisplayed();
        if (flag) { // if report is correct
            webDriver.findElement(AvasantElements.DOWNLOAD_REPORT_BUTTON).click();
            waitForVisibilityOfElement(AvasantElements.DOWNLOAD_POP_UP, WAIT);
            webDriver.findElement(AvasantElements.FIRST_NAME).sendKeys(dataFileProperties.getProperty("FIRST_NAME"));
            webDriver.findElement(AvasantElements.LAST_NAME).sendKeys(dataFileProperties.getProperty("LAST_NAME"));
            webDriver.findElement(AvasantElements.JOB_TITLE).sendKeys(dataFileProperties.getProperty("JOB_TITLE"));
            webDriver.findElement(AvasantElements.COMPANY_NAME).sendKeys(dataFileProperties.getProperty("ORGANIZATION"));
            webDriver.findElement(AvasantElements.PHONE_NUMBER).sendKeys(dataFileProperties.getProperty("PHONE_NUMBER"));
            webDriver.findElement(AvasantElements.EMAIL_ADDRESS).sendKeys(dataFileProperties.getProperty("EMAIL_ADDRESS"));
            webDriver.findElement(AvasantElements.COUNTRY_DROP_DOWN).click();
            Select select = new Select(webDriver.findElement(AvasantElements.COUNTRY_DROP_DOWN));
            select.selectByValue(dataFileProperties.getProperty("COUNTRY_NAME"));
            webDriver.findElement(AvasantElements.TICK_CHECK_BOX).click();
            webDriver.findElement(AvasantElements.DOWNLOAD_BUTTON).click();
            Thread.sleep(3000);  // wait 8 second to download the report
            waitForVisibilityOfElement(AvasantElements.THANK_YOU_POP_UP, WAIT);
            Assert.assertTrue(webDriver.findElement(AvasantElements.THANK_YOU_POP_UP).isDisplayed());  // verify thank you pop-up after downloading report
            File directoryPath = new File(path);
            String[] fileName = directoryPath.list();
            for (String file : fileName) {
                testinfo.info("Downloaded File Name : " + file);
                Assert.assertTrue(file.contains(dataFileProperties.getProperty("FILE_NAME"))); // verify the downloaded file name
            }
        }
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
    public void tearDown() throws Exception {
        webDriver.quit();
        report.flush();
        MyScreenRecorder.stopRecording();
    }
}
