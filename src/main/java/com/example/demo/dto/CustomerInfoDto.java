package com.example.demo.dto;

import java.text.ParseException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import com.example.demo.dto.CustomerRegisterRequest;
import org.apache.commons.validator.routines.EmailValidator;

import lombok.Data;

@Data
public class CustomerInfoDto {

	private String firstName;
	private String lastName;
	private String fullName;
	private String userName;
	private String password;
	private String customerId;
	private String address;
	private String eMailAddress;
	private String phoneNumber;
	private String errorMessage = "";

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final String phoneMask = "###-###-####";
	final String regexPattern = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
		      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
		      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

	public CustomerInfoDto(CustomerRegisterRequest customerRegisterRequest) {
		// TODO Auto-generated constructor stub
		
		this.firstName = customerRegisterRequest.getFirstName();
		this.password = customerRegisterRequest.getPassword();
		this.lastName = customerRegisterRequest.getLastName();
		this.fullName = this.firstName + this.lastName;
		this.userName = customerRegisterRequest.getUserName();
		this.customerId = UUID.randomUUID().toString();
		this.address = customerRegisterRequest.getAddress();
		this.eMailAddress = customerRegisterRequest.getMailAddress();
		this.phoneNumber = customerRegisterRequest.getPhoneNumber();
		if (validatePhoneNumber(this.phoneNumber)) {
			this.errorMessage = validateEmailAdress(this.eMailAddress) == true ? "" : "invalid email address!";
		} else {
			this.errorMessage = "invalid phone number!";
		}
	}

	private boolean validatePhoneNumber(String phoneNumber) {
		boolean isValid = phoneNumber.matches(regexPattern);
		return isValid;
		/*try {
			MaskFormatter maskFormatter = new MaskFormatter(phoneMask);
			maskFormatter.setValueContainsLiteralCharacters(false);
			maskFormatter.valueToString(phoneNumber);
			return true;
		} catch (Exception e) {
			return false;
		}*/
	}

	private boolean validateEmailAdress(String eMailAddress) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(eMailAddress);
		return matcher.find();
	}

}
