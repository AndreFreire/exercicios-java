package com.exercicioandrefreire.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.exercicioandrefreire.domain.Address;

public class Util {

	private final static Logger log = LoggerFactory.getLogger(Util.class);

	public static ResponseEntity<String> getResponseInvalid(String message){
		log.info(message);
		JSONObject response = null;
		try {
			response = new JSONObject();
			response.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<String> getResponseNotFound(String message){
		log.info(message);
		JSONObject response = null;
		try {
			response = new JSONObject();
			response.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<String> getResponseInternalError(){
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
	public static ResponseEntity<String> getAddressResponseOk(Address Address){
		log.info("Zipcode was found successly");
		return new ResponseEntity<String>(Address.toString(), HttpStatus.OK);
	}
	
	public static ResponseEntity<String> getResponseOk(String message){
		log.info(message);		
		JSONObject response = null;
		try {
			response = new JSONObject();
			response.put("message", message);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
