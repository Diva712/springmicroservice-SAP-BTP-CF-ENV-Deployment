// package com.diva.springmicroservice.entities;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import org.hibernate.annotations.CreationTimestamp;

// import com.fasterxml.jackson.annotation.JsonIgnore;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "VENDOR")
// public class Vendor {

//     @Id
//     @GeneratedValue(strategy = GenerationType.UUID)
//     @Column(name = "ID", nullable = false, updatable = false)
//     private String vendorCode;

//     @Column(name = "COMPANY_NAME", nullable = false)
//     private String companyName;

//     @Column(name = "CONTACT_PERSON", nullable = false)
//     private String contactPerson;

//     @Column(name = "FIRST_NAME", nullable = false)
//     private String firstName;

//     @Column(name = "LAST_NAME", nullable = false)
//     private String lastName;

//     @Column(name = "WEBSITE", nullable = false)
//     private String website;

//     @Column(name = "EMAIL", nullable = false)
//     private String email;

//     @Column(name = "STATUS", nullable = false)
//     private Integer status;

//     @CreationTimestamp
//     @Column(name = "REG_DATE", nullable = false, updatable = false)
//     private LocalDateTime regDate;

//     // 🔥 IMPORTANT FIX
//     @OneToMany(
//         cascade = CascadeType.ALL,
//         orphanRemoval = true,
//         fetch = FetchType.LAZY
//     )
//     @JoinColumn(name = "VENDOR_ID", referencedColumnName = "ID")
//     @JsonIgnore   // ✅ Lazy issue solved
//     private List<Address> addresses = new ArrayList<>();

//     // getters & setters

//     public String getVendorCode() {
//         return vendorCode;
//     }

//     public void setVendorCode(String vendorCode) {
//         this.vendorCode = vendorCode;
//     }

//     public String getCompanyName() {
//         return companyName;
//     }

//     public void setCompanyName(String companyName) {
//         this.companyName = companyName;
//     }

//     public String getContactPerson() {
//         return contactPerson;
//     }

//     public void setContactPerson(String contactPerson) {
//         this.contactPerson = contactPerson;
//     }

//     public String getFirstName() {
//         return firstName;
//     }

//     public void setFirstName(String firstName) {
//         this.firstName = firstName;
//     }

//     public String getLastName() {
//         return lastName;
//     }

//     public void setLastName(String lastName) {
//         this.lastName = lastName;
//     }

//     public String getWebsite() {
//         return website;
//     }

//     public void setWebsite(String website) {
//         this.website = website;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public Integer getStatus() {
//         return status;
//     }

//     public void setStatus(Integer status) {
//         this.status = status;
//     }

//     public LocalDateTime getRegDate() {
//         return regDate;
//     }

//     public List<Address> getAddresses() {
//         return addresses;
//     }

//     public void setAddresses(List<Address> addresses) {
//         this.addresses = addresses;
//     }
// }

//this is for PostgreSQL database.
package com.diva.springmicroservice.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "VENDOR")
public class Vendor {

    @Id
    @Column(name = "ID", nullable = false, updatable = false, length = 36)
    private String vendorCode;

    @Column(name = "COMPANY_NAME", nullable = false)
    private String companyName;

    @Column(name = "CONTACT_PERSON", nullable = false)
    private String contactPerson;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "WEBSITE", nullable = false, length = 255)
    private String website;

    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @CreationTimestamp
    @Column(name = "REG_DATE", nullable = false, updatable = false)
    private LocalDateTime regDate;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "VENDOR_ID", referencedColumnName = "ID")
    @JsonIgnore
    private List<Address> addresses = new ArrayList<>();

    /**
     * UUID generation handled in Java (HANA-safe)
     */
    @PrePersist
    public void prePersist() {
        if (this.vendorCode == null) {
            this.vendorCode = UUID.randomUUID().toString();
        }
    }

    // ======================
    // Getters & Setters
    // ======================

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}


