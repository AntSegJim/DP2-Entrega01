/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package forms;

import java.util.HashSet;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import security.Authority;
import security.UserAccount;
import services.CustomizableSystemService;
import domain.Actor;

public class RegistrationFormBrotherhood extends Actor {

	@Autowired
	private CustomizableSystemService	customizableService;


	// Constructors -----------------------------------------------------------

	public RegistrationFormBrotherhood() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String	password;


	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	//	@NotBlank
	//	@NotNull
	//	public String getName() {
	//		return this.name;
	//	}
	//
	//	public void setName(final String name) {
	//		this.name = name;
	//	}
	//
	//	public String getMiddleName() {
	//		return this.middleName;
	//	}
	//
	//	public void setMiddleName(final String middleName) {
	//		this.middleName = middleName;
	//	}
	//	@NotBlank
	//	@NotNull
	//	public String getSurname() {
	//		return this.surname;
	//	}
	//
	//	public void setSurname(final String surname) {
	//		this.surname = surname;
	//	}
	//	@URL
	//	public String getPhoto() {
	//		return this.photo;
	//	}
	//
	//	public void setPhoto(final String photo) {
	//		this.photo = photo;
	//	}
	//	@Column(unique = true)
	//	@NotNull
	//	@NotBlank
	//	public String getEmail() {
	//		return this.email;
	//	}
	//
	//	public void setEmail(final String email) {
	//		this.email = email;
	//	}
	//	@Pattern(regexp = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$")
	//	public String getPhone() {
	//		return this.phone;
	//	}
	//
	//	public void setPhone(final String phone) {
	//		this.phone = phone;
	//	}
	//
	//	public String getAddress() {
	//		return this.address;
	//	}
	//
	//	public void setAddress(final String address) {
	//		this.address = address;
	//	}
	//	@NotNull
	//	public UserAccount getUserAccount() {
	//		return this.userAccount;
	//	}
	//
	//	public void setUserAccount(final UserAccount userAccount) {
	//		this.userAccount = userAccount;
	//	}

	// Business methods -------------------------------------------------------

	public RegistrationFormBrotherhood createToBrotherhood() {

		final RegistrationFormBrotherhood registrationForm = new RegistrationFormBrotherhood();

		registrationForm.setName("");
		registrationForm.setMiddleName("");
		registrationForm.setSurname("");
		registrationForm.setPhoto("");
		registrationForm.setEmail("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");
		registrationForm.setAddress("");
		registrationForm.setPassword("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.BROTHERHOOD);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		registrationForm.setUserAccount(user);

		return registrationForm;
	}

}
