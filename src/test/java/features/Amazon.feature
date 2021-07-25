Feature: Select random department from the dropdown each time the test is run 

Scenario: Visit the frontend site 
	Given Initialize the driver 
	When The user visits "https://www.amazon.com" 
	Then Check the logo 
	And Check the page Title is "Amazon.com. Spend less. Smile more." 
	
Scenario: Select Random dropdown 
	Given The count of dropdown options 
	When User generates random number between "1" and count of dropdownOptions 
	And User selects dropdown randomly 
	Then Validate All Dropdown option is not selected in dropdown 
	
	
