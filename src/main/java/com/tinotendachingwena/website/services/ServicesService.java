package com.tinotendachingwena.website.services;

import com.tinotendachingwena.website.models.ServicesRequest;
import com.tinotendachingwena.website.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    public ServicesRequest saveService (ServicesRequest servicesRequest) {
        return servicesRepository.save(servicesRequest);
    }
}
