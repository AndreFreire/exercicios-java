package com.exercicioandrefreire.domain;

import org.json.JSONException;
import org.json.JSONObject;
import javax.persistence.*;

@Entity
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String cep;
	String street;
	String district;
	String city;
	String state;
	
	public Address() {}
	
	public Address(String cep, String street, String district, String city, String state) {
		this.cep = cep;
		this.street = street;
		this.district = district;
		this.city = city;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getStringJson(){
		JSONObject response = new JSONObject();
		try {
			response.put("cep", this.getCep());
			response.put("rua", this.getStreet());
			response.put("bairro", this.getDistrict());
			response.put("cidade", this.getCity());
			response.put("estado", this.getState());

			return response.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
