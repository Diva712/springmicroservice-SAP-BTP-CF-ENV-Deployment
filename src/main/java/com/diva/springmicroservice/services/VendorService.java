package com.diva.springmicroservice.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diva.springmicroservice.entities.Vendor;

@Service
public class VendorService {

    private final IVendorPersistance vendorDB;

    // Constructor Injection (BEST PRACTICE)
    public VendorService(IVendorPersistance vendorDB) {
        this.vendorDB = vendorDB;
    }

    // GET ALL
    public List<Vendor> getAllVendors() {
        return vendorDB.findAll();
    }

    // GET BY ID
    public Vendor getVendorById(String vendorId) {
        return vendorDB.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));
    }

    // CREATE
    public Vendor addVendor(Vendor newVendor) {
        return vendorDB.save(newVendor);
    }

    // UPDATE
    public Vendor updateVendorById(String vendorId, Vendor vendorData) {

        Vendor existingVendor = getVendorById(vendorId);

        existingVendor.setCompanyName(vendorData.getCompanyName());
        existingVendor.setContactPerson(vendorData.getContactPerson());
        existingVendor.setFirstName(vendorData.getFirstName());
        existingVendor.setLastName(vendorData.getLastName());
        existingVendor.setEmail(vendorData.getEmail());
        existingVendor.setWebsite(vendorData.getWebsite());
        existingVendor.setStatus(vendorData.getStatus());

        return vendorDB.save(existingVendor);
    }

    // DELETE
    public void deleteVendor(String vendorId) {
        if (!vendorDB.existsById(vendorId)) {
            throw new RuntimeException("Vendor not found with id: " + vendorId);
        }
        vendorDB.deleteById(vendorId);
    }
}
