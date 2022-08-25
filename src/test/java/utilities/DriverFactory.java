package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

//Do not allow to initialize this class from the outside
public class DriverFactory {
    // It will not allow to create an object outside from the class
    private DriverFactory(){
        // Do nothing---Empty Constructor

    }
    //Create an instance object from this class
    private static final DriverFactory instance= new DriverFactory();

    //Create a get methode getInstance().
    public static DriverFactory getInstance(){
        return instance;
    }
    // Create Thread local object// this DriverFactory class will give us driver object every time we call

    ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(()->{
        String environment = System.getProperty("environment") == null ? "local" : System.getProperty("environment");
        String browser = System.getProperty("browser") == null ? "chrome" : System.getProperty("browser");
        URL gridUrl = null;

        try {
            gridUrl = new URL(ReadConfigFiles.getPropertyValues("GridURL"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        if(environment.equals("remote") && browser.equals("chrome")){
            ChromeOptions chromeOptions = new ChromeOptions();
            return new RemoteWebDriver(gridUrl,chromeOptions);
        } else if(environment.equals("remote") && browser.equals("firefox")){
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            return new RemoteWebDriver(gridUrl,firefoxOptions);
        } else if(environment.equals("remote") && browser.equals("edge")){
            EdgeOptions edgeOptions = new EdgeOptions();
            return new RemoteWebDriver(gridUrl,edgeOptions);
        } else {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }

    });

    // Call this method to get the driver object and launch the browser
    public WebDriver getDriver(){
        return driver.get();

    }

    //Quits the driver and Closing the driver method
    public void removeDriver()
    {
        driver.get().quit();
        driver.remove();
    }

}
