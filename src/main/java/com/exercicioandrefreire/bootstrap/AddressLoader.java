package com.exercicioandrefreire.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.exercicioandrefreire.domain.Address;
import com.exercicioandrefreire.repositories.AddressRepository;


@Component
public class AddressLoader implements ApplicationListener<ContextRefreshedEvent> {

    private AddressRepository AddressRepository;

    private Logger log = Logger.getLogger(AddressLoader.class);

    @Autowired
    public void setAddressRepository(AddressRepository AddressRepository) {
        this.AddressRepository = AddressRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Address address = new Address();
        address.setCep("cep");
		address.setStreet("logradouro");
		address.setDistrict("bairro");
		address.setCity("localidade"); 
		address.setState("uf");
		AddressRepository.save(address);

        log.info("Saved Address - id: " + address.getId());

        
    }
}