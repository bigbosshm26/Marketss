package com.rateplus.user;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rateplus.model.UserDTO;

@Entity(name="user")
@Table
public class User implements UserDetails{

	private static final long serialVersionUID = 2332345658193404586L;

	@Id
	private Long id;
	@NotNull
	private String firstName;	
	@NotNull
	private String lastName;	
	@NotNull
	private int age;	
	@NotNull
	private String email;	
	@NotNull
	private String phoneNumber;
	@NotNull
	private String password;
	private String link;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", email="
				+ email + ", phoneNumber=" + phoneNumber + ", password=" + password + ", link=" + link + "]";
	}
	
	public User(UserDTO userDTO) {
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.age = userDTO.getAge();
		this.email = userDTO.getEmail();
		this.phoneNumber = userDTO.getPhoneNumber();
		this.password = new BCryptPasswordEncoder().encode(userDTO.getPassword());
		this.link = userDTO.getLink();
	}
	
	public User() {}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public String getUsername() {
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	};
}
