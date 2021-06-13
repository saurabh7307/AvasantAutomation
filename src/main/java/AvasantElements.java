import org.openqa.selenium.By;

public class AvasantElements {
    public static final By ACCOUNT_MENU_ICON =  By.id("account-menu");
    public static final By LOG_IN_HEADING = By.xpath("//h2[contains(text(),'Login')]");
    public static final By USER_NAME_INPUT_TEXT_AREA = By.id("popup-username");
    public static final By PASSWORD_INPUT_TEXT_AREA = By.id("popup-password");
    public static final By LOG_IN_BUTTON = By.className("submit");
    public static final By LOGGED_IN_WELCOME_STRING = By.xpath("//div[@class = 'login-detail'][contains(text(),'Welcome')]");
    public static final String USER_NAME = "//div[contains(text(),'')]";
    public static final By RESEARCH_AND_DATA_NAVIGATION = By.xpath("//a[contains(text(),'Research & Data')]");
    public static final By ACCESS_REPORT_OPTION = By.xpath("//a[contains(text(),'Access Reports')]");
    public static final By STRING_ON_ACCESS_REPORT_PAGE = By.xpath("//h2[contains(text(),'Areas of Interest')]");
    public static final By SEARCH_BAR = By.className("aws-search-field");
    public static final By SEARCH_ICON = By.className("aws-search-btn_icon");
    public static final By HEADING_SEARCH_PAGE = By.className("heading_search");
    public static final By FIRST_REPORT  = By.xpath("(//div[@class='inner_product_header_cell']//a)[1]");
    public static final By DOWNLOAD_REPORT_HEADING = By.xpath("//h2[contains(text(),'From IT Service Delivery to Business Consulting: Understanding the Wipro-Capco Deal')]");
    public static final By DOWNLOAD_REPORT_BUTTON = By.className("free-download");

    /***************Download report pop_up element*************************/
    public static final By DOWNLOAD_POP_UP = By.xpath("//h1//span[contains(text(),'Download')]");
    public static final By FIRST_NAME = By.xpath("(//input[@name='firstname'])[1]");
    public static final By LAST_NAME = By.xpath("(//input[@name='lastname'])[1]");
    public static final By JOB_TITLE = By.xpath("(//input[@name='jobtitle'])[1]");
    public static final By COMPANY_NAME = By.xpath("(//input[@name='company'])[1]");
    public static final By PHONE_NUMBER = By.xpath("(//input[@name='phone'])[1]");
    public static final By EMAIL_ADDRESS = By.xpath("(//input[@name='email'])[1]");
    public static final By COUNTRY_DROP_DOWN = By.xpath("(//select[@name='country_global_equations'])[1]");
    public static final By TICK_CHECK_BOX = By.name("please_keep_me_updated_with_latest_news_research_and_events_from_avasant");
    public static final By THANK_YOU_POP_UP = By.xpath("//p[contains(text(),'Your Report will be downloaded shortly.')]");
    public static final By DOWNLOAD_BUTTON = By.xpath("//input[@value='Download']");
}
