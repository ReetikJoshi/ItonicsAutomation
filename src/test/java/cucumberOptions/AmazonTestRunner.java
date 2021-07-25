package cucumberOptions;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/java/features", glue = { "stepDefinitions" })
public class AmazonTestRunner extends AbstractTestNGCucumberTests {
}
