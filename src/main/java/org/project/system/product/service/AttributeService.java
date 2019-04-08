package org.project.system.product.service;

import org.project.system.product.ProductException;
import org.project.system.product.domain.attribute.Attribute;
import org.project.system.product.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    public Attribute find(Long id) throws ProductException {
        return attributeRepository.findById(id).orElseThrow(()-> new ProductException("Attribute doesn't exist"));
    }
}
