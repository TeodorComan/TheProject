package org.project.system.product.service;

import org.project.system.product.ProductException;
import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.repository.PredefinedStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredefinedStructureService {

    @Autowired
    private PredefinedStructureRepository predefinedStructureRepository;

    public PredefinedStructure find(Long id) throws ProductException{
        return predefinedStructureRepository.findById(id).orElseThrow(()->new ProductException("PredefinedStructure doesn't exist"));
    }
}
