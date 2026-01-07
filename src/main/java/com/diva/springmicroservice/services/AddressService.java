package com.diva.springmicroservice.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diva.springmicroservice.entities.Address;

@Service
public class AddressService {

    IAddressPersistance addressDB;

    public AddressService(IAddressPersistance addressDB) {
        this.addressDB = addressDB;
    }

    // GET ALL
    public List<Address> getAllAddresses() {
        return addressDB.findAll();
    }

    // GET BY ID
    public Address getAddressById(String addressId) {
        return addressDB.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
    }

    public Address addAddress(Address newAddress) {
        return addressDB.save(newAddress);
    }

}
