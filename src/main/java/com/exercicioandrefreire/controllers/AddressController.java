package com.exercicioandrefreire.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exercicioandrefreire.domain.Address;
import com.exercicioandrefreire.services.AddressService;

@Controller
public class AddressController {
	private AddressService addressService;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String saveProduct(){
    	Address address = new Address("a","a","a","a","a");
    	addressService.saveAddress(address);
        return "redirect:/address/" + address.getId();
    }
    
    @RequestMapping(value = "address", method = RequestMethod.GET)
    public String newProduct(){
    	System.out.println("teste");
        //model.addAttribute("product", new Address());
        return "productform";
    }
    
    
    
}
