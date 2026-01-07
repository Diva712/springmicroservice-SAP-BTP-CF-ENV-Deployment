package com.diva.springmicroservice.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diva.springmicroservice.entities.Address;

public interface IAddressPersistance extends JpaRepository<Address, String> {

}
