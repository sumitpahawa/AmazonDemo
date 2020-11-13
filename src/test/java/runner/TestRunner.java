package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;

@CucumberOptions(features = "src/test/resources/demo/", glue = "StepDefinitions", plugin = {
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "json:target/cucumber-reports/Cucumber.json"}, monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {


}
