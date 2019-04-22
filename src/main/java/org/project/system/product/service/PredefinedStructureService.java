package org.project.system.product.service;

import org.project.system.product.ProductException;
import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.domain.product.PredefinedStructureAttribute;
import org.project.system.product.repository.PredefinedStructureRepository;
import org.project.system.product.service.model.Attribute;
import org.project.system.product.service.model.CreatePredefinedStructureRequest;
import org.project.system.product.service.model.CreatePredefinedStructureResponse;
import org.project.system.user.domain.User;
import org.project.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredefinedStructureService {

    @Autowired
    private PredefinedStructureRepository predefinedStructureRepository;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private UserService userService;

    public PredefinedStructure find(Long id) throws ProductException{
        return predefinedStructureRepository.findById(id).orElseThrow(()->new ProductException("PredefinedStructure doesn't exist"));
    }

    public CreatePredefinedStructureResponse create(CreatePredefinedStructureRequest request) throws ProductException {
        PredefinedStructure predefinedStructure = new PredefinedStructure();

        User user = userService.find(request.getUserId());

        if(predefinedStructureRepository.findByNameAndAccount(predefinedStructure.getName(), user.getAccount()).isPresent()){
            throw new ProductException("Predefined Structure with the given name already exists");
        }

        predefinedStructure.setName(request.getPredefinedStructure().getName());
        predefinedStructure.setDescription(request.getPredefinedStructure().getDescription());

        List<PredefinedStructureAttribute> predefinedStructureAttributes = new ArrayList<>();

        for(Attribute attribute : request.getPredefinedStructure().getAttributes()) {
            org.project.system.product.domain.attribute.Attribute domainAttribute = attributeService.find(attribute.getId());
            PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
            predefinedStructureAttribute.setAttribute(domainAttribute);
            predefinedStructureAttribute.setMandatory(attribute.getIsMandatory());

            predefinedStructureAttributes.add(predefinedStructureAttribute);
        }


        predefinedStructure.setStructureAttributes(predefinedStructureAttributes);
        predefinedStructure.setUser(user);

        CreatePredefinedStructureResponse response = new CreatePredefinedStructureResponse();
        response.setPredefinedStructure(request.getPredefinedStructure());

        response.getPredefinedStructure().setId(predefinedStructureRepository.saveAndFlush(predefinedStructure).getId());


        return response;
    }
}
