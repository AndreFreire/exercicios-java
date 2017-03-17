package com.exercicioandrefreire;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping(value="/")
class CepController{

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${urlZipcode}")
	private String urlZipcode;

	@RequestMapping(value="/cep/{zipcode}/", method = RequestMethod.GET)
	public ResponseEntity<String> getAdress(@PathVariable String zipcode){
		try{
			if(!validateZipcode(zipcode)){
				return getResponseInvalid();
			}	
			AddressModel address = getAddressFromWebService(zipcode);			
			if(address == null){
				return getResponseNotFound();
			}	
			return getResponseOk(address.getStringJson());
			
		}catch (Exception e) {
			return getResponseInternalError();
		}
		
	}

	public AddressModel getAddressFromWebService(String zipcode){

		RestTemplate restTemplate = new RestTemplate();
		JSONObject addressJson = null;
		while(addressJson == null){
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
		AddressModel address = parseAddress(addressJson);
		return address;
	}

	private String adaptZipcode(String zipcode) {
		StringBuilder zipcodeAux = new StringBuilder(zipcode);
		for(int i = zipcodeAux.length()-1; i >= 0; i--){
			if(zipcodeAux.charAt(i) != '0'){
				zipcodeAux.setCharAt(i, '0');
				break;
			}
		}		
		return zipcodeAux.toString();
	}

	public AddressModel parseAddress(JSONObject addressJson){
		AddressModel address = null;
		try {
			address = new AddressModel(
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

	public ResponseEntity<String> getResponseInvalid(){
		log.info("zipcode invalid");
		JSONObject response = null;
		try {
			response = new JSONObject();
			response.put("message", "invalid zipcode");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> getResponseNotFound(){
		log.info("zipcode not found");
		JSONObject response = null;
		try {
			response = new JSONObject();
			response.put("message", "not found");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<String> getResponseInternalError(){
		log.error("Internal unknow error");		
		JSONObject response = null;
		try {
			response = new JSONObject();
			response.put("message", "internal error");
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public ResponseEntity<String> getResponseOk(String response){
		log.info("zipcode found successfully");
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}