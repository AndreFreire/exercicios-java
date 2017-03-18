package com.exercicioandrefreire.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exercicioandrefreire.domain.Address;

@Service
public class CepService {
	@Value("${urlZipcode}")
	private String urlZipcode;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Address getAddressFromWebService(String zipcode){

		RestTemplate restTemplate = new RestTemplate();
		JSONObject addressJson = null;
		while(addressJson == null){
			System.out.println(urlZipcode +" "+ zipcode);
			String fooResourceUrl = String.format(urlZipcode, zipcode);

			ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl , String.class);

			try {
				addressJson = new JSONObject(response.getBody());
				if(addressJson.has("erro")){
					addressJson = null;
					zipcode = adaptZipcode(zipcode);
				}
			} catch (JSONException e) {
				return null;
			}

			if(!validateZipcode(zipcode)){
				return null;
			}

		}
		Address address = parseAddress(addressJson);
		return address;
	}

	public String adaptZipcode(String zipcode) {
		StringBuilder zipcodeAux = new StringBuilder(zipcode);
		for(int i = zipcodeAux.length()-1; i >= 0; i--){
			if(zipcodeAux.charAt(i) != '0'){
				zipcodeAux.setCharAt(i, '0');
				break;
			}
		}		
		return zipcodeAux.toString();
	}

	public Address parseAddress(JSONObject addressJson){
		Address address = null;
		try {
			address = new Address(
					addressJson.getString("cep"),
					addressJson.getString("logradouro"),
					addressJson.getString("bairro"),
					addressJson.getString("localidade"), 
					addressJson.getString("uf"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;

	}

	public boolean validateZipcode(String zipcode){
		int zipcodeAux = 0;		
		if(zipcode.length() != 8) return false;				
		try{			
			zipcodeAux = Integer.parseInt(zipcode);			
		}catch (NumberFormatException e) {
			return false;
		}		
		if(zipcodeAux == 0) return false;
		return true;
	}

}
