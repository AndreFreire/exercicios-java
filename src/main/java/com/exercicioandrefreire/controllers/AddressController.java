package com.exercicioandrefreire.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercicioandrefreire.domain.Address;
import com.exercicioandrefreire.services.AddressService;
import com.exercicioandrefreire.services.CepService;
import com.exercicioandrefreire.util.Util;

@RestController
@RequestMapping(value="address")
public class AddressController {
	private AddressService addressService;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private CepService cepService;

	@Autowired
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	@Autowired
	public void setCepService(CepService cepService) {
		this.cepService = cepService;
	}

	@RequestMapping(value = "/save/", method = RequestMethod.POST)
	public ResponseEntity<String> saveAddress(@RequestParam String zipcode, @RequestParam String number){
		Address address = cepService.getAddressFromWebService(zipcode);
		address.setNumber(number);
		addressService.saveAddress(address);
		return Util.getResponseOk(String.format("Endereco cadastrado com sucesso, id %", address.getId()));
	}

	@RequestMapping(value = "/delete/{id}/", method = RequestMethod.GET)
	public ResponseEntity<String> deleteAddress(@PathVariable("id") int id ){
		try{
			addressService.deleteAddress(id);
		} catch (EmptyResultDataAccessException e) {
			return Util.getResponseNotFound("Endereco nao encontrado");
		}
		return Util.getResponseOk("Endereco cadastrado com sucesso");
	}

	@RequestMapping(value = "/get/{id}/", method = RequestMethod.GET)
	public ResponseEntity<String> getAddressById(@PathVariable("id") int id ){
		Address address = addressService.getAddressById(id);
		if(address == null){
			return Util.getResponseNotFound("Endereco nao encontrado");
		}
		return Util.getAddressResponseOk(address);
	}    
}
