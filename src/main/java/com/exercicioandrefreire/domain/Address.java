package com.exercicioandrefreire.domain;

import javax.persistence.*;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String zipcode;
	String number;
	String street;
	String district;
	String city;
	String state;

	public Address(String zipcode, String street, String district, String city, String state) {
		this.zipcode = zipcode;
		this.street = street;
		this.district = district;
		this.city = city;
		this.state = state;
	}

	public Address() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString(){
		JSONObject response = new JSONObject();
		try {
			response.put("cep", this.getZipcode());
			response.put("rua", this.getStreet());
			response.put("bairro", this.getDistrict());
			response.put("cidade", this.getCity());
			response.put("estado", this.getState());
			if(this.getId() != 0){
				response.put("id", this.getId());
			}
			if(this.getNumber() != null){
				response.put("numero", this.getNumber());
			}

			return response.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	 
	
}
