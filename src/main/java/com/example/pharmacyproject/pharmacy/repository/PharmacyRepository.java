package com.example.pharmacyproject.pharmacy.repository;

import com.example.pharmacyproject.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    } //JPA를 상속 받아서 <Entity 이름, PK값>
