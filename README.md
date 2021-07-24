# ItonicsAutomation

##### `` Clone the project and checkout to assignment branch ``

## Description
There are 4 main packages in the project currently (src/test/java)   
1. helper => The global methods reused by many test cases are placed there.  
2. pages.amazon => The Page Objects(Web elements) of amazon website.  
3. pages.facebook => The Page Objects(Web elements) of facebook website.  
4. testCase => The main class file to execute the automation script which are specified in the testng.xml file too.  
5. addOn => Additional features for the project (screenshot).

## Environment/Global variables
- The global or environment variables are specified in **Environment.json** file
- Eg. pass another browser name(firefox) to run script in that browser driver

## Helper/Reusable methods
- The **commonly** used methods are specified in HelperMethods class file.
- Can be used to alter the process of entire application.

## Screenshot
- The framework generates **screenshot on failure** using the TestNG listeners. 
- It will get generated inside the **Screenshot folder** with the failed test method name.
- The image format is set to **png**.

## Data driven testing
- Login credentials for facebook site can be passed through both json and xlsx files.
- **Note**: Pass "**json**" or "**xlsx**" value inside "**type**" key of environment to login facebook user accordingly.
- Location: 'src/test/resources/testData/'


