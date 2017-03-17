package com.exercicioandrefreire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/address")
public class AddressController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="/insert/", method = RequestMethod.POST)
	public ResponseEntity<String> getAdress(@PathVariable String zipcode){
		return null;
	}
}
