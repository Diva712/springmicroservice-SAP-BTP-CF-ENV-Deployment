package com.diva.springmicroservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diva.springmicroservice.entities.Address;
import com.diva.springmicroservice.entities.Vendor;
import com.diva.springmicroservice.services.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> getAddress() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{addressId}")
    public Address getAddressById(@PathVariable("addressId") String AddressId) {
        return addressService.getAddressById(AddressId);
    }

    @PostMapping
    public Address createAddress(@RequestBody Address payload) {
        return addressService.addAddress(payload);
    }

}
