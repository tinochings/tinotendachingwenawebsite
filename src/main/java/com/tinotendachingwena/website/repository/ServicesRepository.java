package com.tinotendachingwena.website.repository;

import com.tinotendachingwena.website.models.ServicesRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<ServicesRequest, Integer> {
}
