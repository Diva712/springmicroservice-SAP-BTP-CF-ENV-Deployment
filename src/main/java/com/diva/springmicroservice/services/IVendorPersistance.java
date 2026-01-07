package com.diva.springmicroservice.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diva.springmicroservice.entities.Vendor;

public interface IVendorPersistance extends JpaRepository<Vendor, String> {

    List<Vendor> findByCompanyName(String companyName);

}
