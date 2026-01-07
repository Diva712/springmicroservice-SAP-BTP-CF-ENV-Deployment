package com.diva.springmicroservice.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.diva.springmicroservice.entities.Vendor;
import com.diva.springmicroservice.services.VendorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;

    // Constructor Injection (BEST PRACTICE)
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // ✅ GET ALL VENDORS
    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    // ✅ GET VENDOR BY ID
    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable("id") String vendorId) {
        return vendorService.getVendorById(vendorId);
    }

    // ✅ CREATE VENDOR
    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.addVendor(vendor);
    }

    // ✅ UPDATE VENDOR
    @PutMapping("/{id}")
    public Vendor updateVendor(
            @PathVariable("id") String vendorId,
            @RequestBody Vendor vendor) {

        return vendorService.updateVendorById(vendorId, vendor);
    }

    // ✅ DELETE VENDOR
    @DeleteMapping("/{id}")
    public void deleteVendor(@PathVariable("id") String vendorId) {
        vendorService.deleteVendor(vendorId);
    }
}
