package testCase;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import helper.HelperMethods;

public class Testing {

	public static void main(String[] args) throws IOException, ParseException {
		String URL = HelperMethods.getFrontendURL("amazon");
		System.out.println(URL);

	}
}
