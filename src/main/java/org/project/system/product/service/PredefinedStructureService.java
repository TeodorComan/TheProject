package org.project.system.product.service;

import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.repository.PredefinedStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PredefinedStructureService {

    @Autowired
    private PredefinedStructureRepository predefinedStructureRepository;

    public Optional<PredefinedStructure> find(Long id){
        return predefinedStructureRepository.findById(id);
    }
}
