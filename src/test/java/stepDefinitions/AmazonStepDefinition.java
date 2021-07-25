package stepDefinitions;

import org.testng.annotations.AfterClass;

import helper.HelperMethods;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AmazonStepDefinition {

	@Given("^Initialize the driver $")
	public void initialize_the_driver() throws Throwable {
		System.out.println("hwllo");
	}

	@Given("^The count of dropdown options $")
	public void the_count_of_dropdown_options() throws Throwable {
	}

	@When("^The user visits \"([^\"]*)\" $")
	public void the_user_visits_something(String strArg1) throws Throwable {
	}

	@When("^User generates random number between \"([^\"]*)\" and count of dropdownOptions $")
	public void user_generates_random_number_between_something_and_count_of_dropdownoptions(String strArg1)
			throws Throwable {
	}

	@Then("^Check the logo $")
	public void check_the_logo() throws Throwable {
	}

	@Then("^Validate All Dropdown option is not selected in dropdown $")
	public void validate_all_dropdown_option_is_not_selected_in_dropdown() throws Throwable {
	}

	@And("^Check the page Title is \"([^\"]*)\" $")
	public void check_the_page_title_is_something(String strArg1) throws Throwable {
	}

	@And("^User selects dropdown randomly $")
	public void user_selects_dropdown_randomly() throws Throwable {
	}

	@AfterClass
	public void tearDown() {
		HelperMethods.tearDown();
	}
}
