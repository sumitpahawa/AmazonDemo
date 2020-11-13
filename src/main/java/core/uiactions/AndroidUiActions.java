package core.uiactions;
import com.google.common.collect.ImmutableMap;
import core.constants.Direction;
import core.managers.DeviceManager;
import core.managers.baseutils.ADB;
import core.managers.logmanager.MyLogger;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
//import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class AndroidUiActions {
    public DeviceManager deviceManager;
    public AndroidDriver<AndroidElement> driver;
    public ADB adb;
    public AndroidUiActions(AndroidDriver<AndroidElement> driver) {
        deviceManager = new DeviceManager((String) driver.getCapabilities().getCapability("udid"));
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(30)), this);
        MyLogger.log.info("AndroidUiActions Objects instance is created");
        adb = deviceManager.adb;
    }

    public void swipeDownward() {
        Dimension size = driver.manage().window().getSize();
        int width = (int) (size.getWidth() / 2);
        int startPoint = (int) (size.getHeight() * 0.8);

        int endPoint = (int) (size.getHeight() * 0.21);
        new TouchAction(driver).press(PointOption.point(width, startPoint)).waitAction()
                .moveTo(PointOption.point(width, endPoint)).release().perform();
    }
    

    public void swipeUpward() {

        Dimension size=driver.manage().window().getSize();
        int width=(int)(size.getWidth()/2);
        int startPoint=(int)(size.getHeight()*0.8);

        int endPoint=(int)(size.getHeight()*0.21);
        new TouchAction(driver).press(PointOption.point(width, endPoint)).waitAction().moveTo(PointOption.point(width, startPoint)).release().perform();
    }

    public WebElement waitToAppear(AndroidElement element) throws Exception {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException toe) {
            MyLogger.log.info("No element was found " + element.toString());
            throw new Exception();
        }
    }

    public WebElement waitToAppear(WebElement element) throws Exception {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(60))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException toe) {
            MyLogger.log.info("No element was found " + element.toString());
            throw new Exception();
        }
    }

    public WebElement waitToAppear(AndroidElement element, int waitSeconds) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(waitSeconds))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException toe) {
        }
        return element;
    }

    public boolean waitForActivity(String appActivity, int waitSeconds) {
        MyLogger.log.info("Checking App Activity");

        String activity="";
        boolean isActivityPresent= false;
        for (int i = 0; i < waitSeconds; i++) {
            activity= driver.currentActivity();
            MyLogger.log.info("Current AppActivity : " + activity);
            if (!activity.contains(appActivity)) {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
            } else {
                isActivityPresent = true;
                MyLogger.log.info(activity + " - Activity is Present");
            }
        }
        return isActivityPresent;
    }

    public  Boolean waitToDisappear(AndroidElement element, int waitSeconds) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(waitSeconds))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException toe) {
            return false;
        }
    }

    public void scrollToAWebElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public  Boolean waitToDisappear(AndroidElement element) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(60))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException toe) {
            return false;
        }
    }
    public  WebElement waitForElementToBetapable(AndroidElement element, int waitSeconds) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(waitSeconds))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException toe) {
        }
        return element;
    }

    public Boolean isElementPresent(AndroidElement element) {
        try {
            return waitToAppear(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isElementPresent(WebElement element) {
        try {
            return waitToAppear(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isChecked(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("checked").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isCheckable(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("checkable").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean istapable(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("tapable").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isEnabled(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("enabled").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isFocusable(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("focusable").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isFocused(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("focused").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isScrollable(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("scrollable").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isLongtapable(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("long-tapable").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isSelected(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("selected").contains("true");
        } catch (Exception e) {
            return null;
        }
    }

    public Point getLocation(AndroidElement element) {
        try {
            return waitToAppear(element).getLocation();
        } catch (Exception e) {
            return null;
        }
    }

    public Dimension getSize(AndroidElement element) {
        try {
            return waitToAppear(element).getSize();
        } catch (Exception e) {
            return null;
        }
    }

    public String getText(AndroidElement element) {
        try {
            return waitToAppear(element).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getResourceId(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("resourceId");
        } catch (Exception e) {
            return null;
        }
    }

    public String getClassName(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("className");
        } catch (Exception e) {
            return null;
        }
    }

    public String getContentDesc(AndroidElement element) {
        try {
            return waitToAppear(element).getAttribute("name");
        } catch (Exception e) {
            return null;
        }
    }

    public void clearText(AndroidElement element) {
        try {
            waitToAppear(element).clear();
        } catch (Exception e) {
        }
    }

    public void typeText(AndroidElement element, String text) {
        try {
            waitToAppear(element).sendKeys(text);
        } catch (Exception e) {
            Assert.fail("Unable to find the edit text to enter the text" +element);
        }
    }

    public void typeTextAndHitEnter(WebElement element, String text) {
        try {
            waitToAppear(element).sendKeys(text + "\n");
        } catch (Exception e) {
            Assert.fail("Unable to find the edit text to enter the text" +element);
        }
    }

    public void typeText(WebElement element, String text) {
        try {
            waitToAppear(element).sendKeys(text);
        } catch (Exception e) {
            Assert.fail("Unable to find the edit text to enter the text" +element);
        }
    }


    public AndroidUiActions tap(AndroidElement element) throws Exception {
        MyLogger.log.info("Inside tap");
        try {
            waitToAppear(element).click();
        } catch (Exception e) {
            MyLogger.log.info("No Element was found by this" +element + e.getMessage());
            Assert.fail("No Element was found");
        }
        return this;
    }

    public AndroidUiActions tap(WebElement element) throws Exception {
        MyLogger.log.info("Inside tap");
        try {
            waitToAppear(element).click();
        } catch (Exception e) {
            MyLogger.log.info("No Element was found by this" +element + e.getMessage());
            Assert.fail("No Element was found by this"+ element + e.getMessage());
        }
        return this;
    }

    public AndroidUiActions longPress(AndroidElement element) {
        MyLogger.log.info("I am in inside longPress");
        try {
            waitToAppear(element);
            ElementOption option = new ElementOption().withElement(element);
            new TouchAction<>(driver)
                    .longPress(LongPressOptions
                            .longPressOptions()
                            .withElement(option))
                    .waitAction()
                    .perform();
        } catch (Exception e) {
        }
        return this;
    }

    public AndroidUiActions longPress(AndroidElement element, int holdTimeSeconds) {
        MyLogger.log.info("I am in inside longPress");
        try {
            waitToAppear(element);
            ElementOption option = new ElementOption().withElement(element);
            new TouchAction<>(driver)
                    .longPress(LongPressOptions
                            .longPressOptions()
                            .withElement(option)
                            .withDuration(Duration
                                    .ofSeconds(holdTimeSeconds)))
                    .waitAction()
                    .perform();
        } catch (Exception e) {
        }
        return this;
    }

    public AndroidUiActions longPress(int positionX, int positionY, int holdTimeSeconds) {
        MyLogger.log.info("I am in inside longPress");
        try {
            TouchAction perform = new TouchAction(driver)
                    .longPress(point(positionX, positionY))
                    .waitAction(waitOptions(Duration
                            .ofSeconds(holdTimeSeconds)))
                    .perform();
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * Go back to previous screen
     *
     * @return
     */
    public AndroidUiActions goBack() {
        try{
            pressKey(AndroidKey.BACK);
            MyLogger.log.info("Sucessfully get back to previous page");
        }catch(Exception e){
            MyLogger.log.debug("Unable to get back to previous screen" + e.getMessage());
            Assert.fail("Unable to get back to previous screen"+ e.getMessage());

        }

        return this;
    }

    public AndroidUiActions pressEnterKey(){
        try{
            pressKey(AndroidKey.ENTER);
            MyLogger.log.info("Successfully pressed the ENTER key");
        }catch(Exception e){
            MyLogger.log.debug("Not able to pressed the ENTER key" + e.getMessage());
            Assert.fail("Not able to pressed the ENTER key" + e.getMessage());
        }

        return this;
    }


    public AndroidUiActions pasteKey(){
        try{
            pressKey(AndroidKey.PASTE);
            MyLogger.log.info("Successfully pressed the ENTER key");
        }catch(Exception e){
            MyLogger.log.debug("Not able to pressed the ENTER key" + e.getMessage());
            Assert.fail("Not able to pressed the ENTER key" + e.getMessage());
        }

        return this;
    }

    /**
     * Displays home screen
     *
     * @param
     * @return
     */
    public AndroidUiActions goHome() {
        try{
            pressKey(AndroidKey.HOME);
        }catch(Exception e){
            Assert.fail("Unable to go to home page");
        }
        return this;
    }

    /**
     * taps on backspace
     *
     * @param
     * @return
     */
    public AndroidUiActions backSpace() throws InterruptedException {
        pressKey(AndroidKey.DEL);
        return this;
    }


    /**
     * closes an app
     */
    public AndroidUiActions closeApp() {
        driver.closeApp();
        return this;
    }

    public void refreshApp() {
        try {
            pressKey(AndroidKey.APP_SWITCH);
            Thread.sleep(2000);
            MyLogger.log.info("Successfully backs the app in background (event: APP SWITCH)");
        }catch(Exception e){
            MyLogger.log.debug("Unable to backs the app in background (event:APP SWITCH)" + e.getMessage());
            Assert.fail("Unable to backs the app in background (event:APP SWITCH)"+ e.getMessage());
        }
        //pressKey(AndroidKey.BACK);
    }

    public void startActivity(String appPackage, String appActivity){
        driver.startActivity(new Activity(appPackage, appActivity));
        MyLogger.log.info("Started the activity");
    }


    public void appSwitch() {
        pressKey(AndroidKey.APP_SWITCH);
    }

    public AndroidUiActions volumeUp() {
        pressKey(AndroidKey.VOLUME_UP);
        return this;
    }


    /**
     * Call and EndCall Android Keys used
     * to accept and end the calls
     *
     */
    public void acceptCallButton() {
        try{
            pressKey(AndroidKey.CALL);
        }catch(Exception e){
            Assert.fail("Unable to receive the call");
        }
    }

    public void endCallButton() {
        try{
            pressKey(AndroidKey.ENDCALL);
            MyLogger.log.info("Successfully ends the call");
        }catch(Exception e){
            MyLogger.log.debug("Unable to end the call " +e.getMessage());
            Assert.fail("Unable to end the call " +e.getMessage());
        }

    }

    public AndroidUiActions powerKey() {
        pressKey(AndroidKey.POWER);
        return this;
    }
    /**
     * Displays home screen
     *
     * @param
     * @return
     */
    private AndroidUiActions pressKey(AndroidKey androidKey) {
        try {
            driver.pressKey(new KeyEvent(androidKey));
            MyLogger.log.info("Pressed key " + androidKey.name());
        } catch (Exception e) {
            Assert.fail("Failed in pressAndroidKeyAction(" + androidKey.name() + ")", e);
        }
        return this;
    }








    /**
     * Scrolls to element
     *
     * @return
     */
    public AndroidUiActions scrollToText(String visibleElementText) {
        try {
            driver.findElementByAndroidUIAutomator("new UiScrollable(" +
                    "new UiSelector().scrollable(true).instance(0))" + ".scrollIntoView(" +
                    "new UiSelector().textContains(\"" + visibleElementText + "\").instance(0))");

        } catch (Exception e) {
            //sLogger.error(e.getMessage(), e);
            Assert.fail("Failed in scrollTo " + e.getMessage(), e);
        }
        return this;

    }

    public AndroidUiActions scrollToContentDesc(String contentDescription) {
        try {
            driver.findElementByAndroidUIAutomator("new UiScrollable(" +
                    "new UiSelector().scrollable(true).instance(0))" + ".scrollIntoView(" +
                    "new UiSelector().descriptionContains(\"" + contentDescription + "\").instance(0))");

        } catch (Exception e) {
            //sLogger.error(e.getMessage(), e);
            Assert.fail("Failed in scrollTo " + e.getMessage(), e);
        }
        return this;
    }

    public void launchApp(){
        driver.launchApp();
        MyLogger.log.info("Application is launched");

    }


    /**
     * Selects by co-ordinates
     *
     * @param x,y dimensions
     * @return
     */
    public AndroidUiActions tap(int x, int y) {
        try {

            new TouchAction<>(driver).tap(point(x, y)).perform();
            MyLogger.log.info("Successfully pressed the given co-ordinate" + x +","+y);
            //   sLogger.debug("tapped on co-ordinates");
        } catch (Exception e) {
         /*   addCommentAndScreenshot("press By Coordinates failed");
            sLogger.error(e.getMessage(), e);
            */
            MyLogger.log.debug("Failed in pressByCoordinates"+ x+","+y + e.getMessage(), e);
            Assert.fail("Failed in pressByCoordinates "+x+","+y + e.getMessage(), e);
        }
        return this;
    }

    public boolean checkDriver() {
        try {
            new TouchAction<>(driver).perform();
        } catch (Exception e) {
            MyLogger.log.info("Driver is terminated");
            return false;
        }
        return true;
    }




    /**
     * Swipe horizontally on element
     *
     * @return
     */
    public AndroidUiActions swipe(int startPositionX, int startPositionY, int endPositionX, int endPositionY) {
        try {
            new TouchAction<>(driver).press(point(startPositionX, startPositionY)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(endPositionX, endPositionY)).release().perform();
            MyLogger.log.info("Successfully swiped the app in upward direction to close it");
        } catch (Exception e) {
            MyLogger.log.debug("Not able to swipe the app in upward direction to close it" + e.getMessage());
            Assert.fail("Not able to swipe the app in upward direction to close it" + e.getMessage());
        }
        return this;
    }

    public AndroidUiActions swipeHorizontally(int startPositionX, int startPositionY, int endPositionX, int endPositionY) {
        try {
            new TouchAction<>(driver).press(point(startPositionX, startPositionY)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(endPositionX, endPositionY)).release().perform();
        } catch (Exception e) {
            MyLogger.log.debug("Not able to swipe the app in upward direction to close it" + e.getMessage());
            Assert.fail("Not able to swipe the app in upward direction to close it" + e.getMessage());
        }
        return this;
    }


    /**
     * Swipes by Element
     *
     * @param startElement
     * @param direction
     * @throws InterruptedException
     */
    public void swipeByElement(AndroidElement startElement, Direction direction) throws InterruptedException {

        int startXCoord = startElement.getLocation().getX();
        int startYCoord = startElement.getLocation().getY();
        int endXCoord = startElement.getSize().getWidth();
        int endYCoord = startElement.getSize().getHeight();

        TouchAction<?> action = new TouchAction<>(driver);
        switch (direction) {
            case LEFTTORIGHT:
                action.press(point(startXCoord, startYCoord)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(endXCoord, startYCoord)).release().perform();
                break;
            case RIGHTTOLEFT:
                action.press(point(endXCoord, startYCoord)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(startXCoord, startYCoord)).release().perform();
                break;
            case TOPTOBOTTOM:
                action.press(point(startXCoord, startYCoord)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(startXCoord, endYCoord)).release().perform();
                break;
            case BOTTOMTOTOP:
                action.press(point(startXCoord, endYCoord)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(startXCoord, startYCoord)).release().perform();
                break;

            default:
                break;
        }
    }

    /**
     * Swipe by Element
     *
     * @param startElement
     * @param anchor
     * @param direction
     * @throws InterruptedException
     */
    public void swipeByElement(AndroidElement startElement, double anchor, Direction direction) throws InterruptedException {

        int startXCoord = startElement.getLocation().getX();
        int startYCoord = startElement.getLocation().getY();
        int endXCoord = startElement.getSize().getWidth();
        int endYCoord = startElement.getSize().getHeight();

        int y = driver.manage().window().getSize().getHeight();
//    	int x = getDriver().manage().window().getSize().getWidth();
        System.out.println("startXCoord:" + startXCoord + ",startYCoord:" + startYCoord + ",endXCoord:" + endXCoord + ", endYCoord: " + endYCoord);
        TouchAction<?> action = new TouchAction<>(driver);

        switch (direction) {
            case LEFTTORIGHT:
                action.press(point(startXCoord, startYCoord)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(endXCoord, startYCoord)).release().perform();
                break;
            case RIGHTTOLEFT:
                action.press(point(endXCoord * 2, startYCoord)).waitAction(waitOptions(ofMillis(1000))).moveTo(point((int) (startXCoord * anchor), startYCoord)).release().perform();
                break;
            case TOPTOBOTTOM:
                action.press(point(startXCoord, startYCoord))/*.waitAction(waitOptions(ofMillis(1000)))*/.moveTo(point(startXCoord, y - 200)).release().perform();
                break;
            case BOTTOMTOTOP:
                action.press(point(endXCoord / 2, y - 200)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(endXCoord / 2, (int) (y * anchor))).release().perform();
                break;

            default:
                break;
        }

    }

    //Horizontal Swipe by percentages
    public AndroidUiActions horizontalSwipeByPercentage(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.height * anchorPercentage);
        int startPoint = (int) (size.width * startPercentage);
        int endPoint = (int) (size.width * endPercentage);

        new TouchAction(driver)
                .press(point(startPoint, anchor))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(endPoint, anchor))
                .release().perform();
        return this;
    }

    //Vertical Swipe by percentages
    public AndroidUiActions verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);

        new TouchAction(driver)
                .press(point(anchor, startPoint))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(anchor, endPoint))
                .release().perform();
        return this;
    }

    //Swipe by elements
    public AndroidUiActions swipeByElements(AndroidElement startElement, AndroidElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);
        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);

        new TouchAction(driver)
                .press(point(startX, startY))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(endX, endY))
                .release().perform();
        return this;
    }

    //Multitouch action by using an android element
/*    public void multiTouchByElement (AndroidElement androidElement) {
        TouchAction press = new TouchAction(Anroid.driver)
                .press(element(androidElement))
                .waitAction(waitOptions(ofSeconds(1)))
                .release();

        new MultiTouchAction(driver)
                .add(press)
                .perform();
    }*/

    /**
     * Verifies current android activity
     *
     * @param activity to be verified
     * @return
     */
    public boolean isActivityLaunched(String activity) {
        boolean isLaunched = false;
        try {
            if (driver.currentActivity().contains(activity))
                isLaunched = true;
        } catch (Exception e) {
            //sLogger.error(e.getMessage(), e);
            Assert.fail(activity + "is not launched", e);
        }
        return isLaunched;
    }


    public Boolean isKeyboardShown() {
        try {
            return driver.isKeyboardShown();
        } catch (Exception e) {
            Assert.fail("Failed in pressNavigationAction() due to: " + e.getMessage(), e);
            return null;
        }
    }

    public AndroidUiActions hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            Assert.fail("Failed in pressNavigationAction() due to: " + e.getMessage(), e);
        }
        return this;
    }

    public String getDeviceTime() {
        String deviceTime = "";
        try {
            deviceTime = driver.getDeviceTime();
        } catch (Exception e) {
            Assert.fail("Failed in pressNavigationAction() due to: " + e.getMessage(), e);
        }
        return deviceTime;
    }

    public String getClipboardText() {
        String clipBoardText = "";
        try {
            clipBoardText = driver.getClipboardText();
        } catch (Exception e) {
            Assert.fail("Failed in pressNavigationAction() due to: " + e.getMessage(), e);
        }
        return clipBoardText;
    }

    public String getPageSource() {
        String pageSource = "";
        try {
            pageSource = driver.getPageSource();
        } catch (Exception e) {
            Assert.fail("Failed in pressNavigationAction() due to: " + e.getMessage(), e);
        }
        return pageSource;
    }

    public String executeADBShellScript(String command) {
        List<String> list = Arrays.asList(command.split(" "));
        Map<String, Object> map = ImmutableMap.of(
                "command", list.get(0), "args", list.subList(1, list.size()));
        String output = (String) driver.executeScript("mobile:shell", map);
        return output;
    }





    /**
     * Opens notificationsText
     *
     * @param
     * @return
     */
    public AndroidUiActions openNotifications() {
        try {
            driver.openNotifications();
        } catch (Exception e) {
            //sLogger.error(e.getMessage(), e);
            Assert.fail("Failed in openNotifications()" + e.getMessage());
        }
        return this;
    }




    public void navigateBack(){
        try {
            Thread.sleep(500);
            goBack();
            MyLogger.log.info("Navigated back successfully");
        } catch (Exception e) {
            MyLogger.log.debug("Not able to go back "+e.getMessage());
            Assert.fail("Not able to go back "  + e.getMessage());
        }
    }


    public AndroidUiActions findElementByXpath(By xpath){
        try{
            waitToAppear((AndroidElement) driver.findElement(xpath));
        }catch (Exception e){

        }
        return this;
    }

    public AndroidUiActions findElementById(By Id){
        try{
            waitToAppear((AndroidElement) driver.findElement(Id));
        }catch (Exception e){

        }
        return this;
    }

    public AndroidUiActions findElementByClassName(By className){
        try{
            waitToAppear((AndroidElement) driver.findElement(className));
        }catch (Exception e){

        }
        return this;
    }

    public AndroidUiActions findElementByContent(By content){
        try{
            waitToAppear((AndroidElement) driver.findElement(content));
        }catch (Exception e){

        }
        return this;
    }

    public AndroidUiActions findElementByText(By text){
        try{
            waitToAppear((AndroidElement) driver.findElement(text));
        }catch (Exception e){

        }
        return this;
    }

    public AndroidUiActions tapUiAutomatorText(String text) {
        try {
             WebElement element = driver.findElementByAndroidUIAutomator("new UiScrollable(" +
                     "new UiSelector().scrollable(false).instance(0))" + ".scrollIntoView(" +
                                "new UiSelector().textContains(\"" + text + "\").instance(0))");
            System.out.println(element);
            new TouchAction<>(driver).tap(tapOptions().withElement(element(element))).perform();
            MyLogger.log.info("Tapped to the element with text successfully");

        } catch (Exception e) {
            MyLogger.log.info("Failed in tapping with text " + e.getMessage(), e);
            Assert.fail("Failed in tapping with text " + e.getMessage(), e);
        }
        return this;
    }

    public AndroidUiActions tapUiAutomatorContent(String text) {
        try {
            WebElement element = driver.findElementByAndroidUIAutomator("new UiScrollable(" +
                    "new UiSelector().scrollable(true).instance(0))" + ".scrollIntoView(" +
                    "new UiSelector().descriptionContains(\"" + text + "\").instance(0))");
            System.out.println(element);
            new TouchAction<>(driver).tap(tapOptions().withElement(element(element))).perform();
            MyLogger.log.info("Tapped to the element with content description successfully");

        } catch (Exception e) {
            MyLogger.log.info("Failed in tapping with content description " + e.getMessage(), e);
            Assert.fail("Failed in tapping with content description " + e.getMessage(), e);
        }
        return this;
    }


    public AndroidUiActions tapUiAutomatorResourceId(String text) {
        try {
            WebElement element = driver.findElementByAndroidUIAutomator("new UiScrollable(" +
                    "new UiSelector().scrollable(true).instance(0))" + ".scrollIntoView(" +
                    "new UiSelector().ResourceId(\"" + text + "\").instance(0))");
            System.out.println(element);
            new TouchAction<>(driver).tap(tapOptions().withElement(element(element))).perform();
            MyLogger.log.info("Tapped to the element with ID successfully");

        } catch (Exception e) {
            MyLogger.log.info("Failed in tapping with ID " + e.getMessage(), e);
            Assert.fail("Failed in tapping with ID " + e.getMessage(), e);
        }
        return this;
    }

    public AndroidUiActions tapUiAutomatorClassName(String text) {
        try {
            WebElement element = driver.findElementByAndroidUIAutomator("new UiScrollable(" +
                    "new UiSelector().scrollable(true).instance(0))" + ".scrollIntoView(" +
                    "new UiSelector().className(\"" + text + "\").instance(0))");
            System.out.println(element);
            new TouchAction<>(driver).tap(tapOptions().withElement(element(element))).perform();
            MyLogger.log.info("Tapped to the element with className successfully");

        } catch (Exception e) {
            MyLogger.log.info("Failed in tapping with className " + e.getMessage(), e);
            Assert.fail("Failed in tapping with className " + e.getMessage(), e);
        }
        return this;
    }




}
