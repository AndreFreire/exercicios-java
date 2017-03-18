package com.exercicioandrefreire.services;

import com.exercicioandrefreire.domain.Address;

public interface AddressService {

	Iterable<Address> listAllAddress();

	Address getAddressById(Integer id);

	Address saveAddress(Address address);
}