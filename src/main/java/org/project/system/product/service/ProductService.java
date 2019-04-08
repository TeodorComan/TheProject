package org.project.system.product.service;

import org.project.system.product.ProductException;
import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.domain.product.PredefinedStructureAttribute;
import org.project.system.product.domain.product.Product;
import org.project.system.product.domain.product.ProductAttribute;
import org.project.system.product.repository.ProductRepository;
import org.project.system.product.service.model.Attribute;
import org.project.system.product.service.model.SaveProductRequest;
import org.project.system.product.service.model.SaveProductResponse;
import org.project.system.productsearch.service.ProductSearchService;
import org.project.system.user.domain.User;
import org.project.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSearchService productSearchService;

    @Autowired
    UserService userService;

    @Autowired
    AttributeService attributeService;

    @Autowired
    PredefinedStructureService predefinedStructureService;

    @Transactional
    public SaveProductResponse saveProduct(SaveProductRequest request) throws ProductException{

        PredefinedStructure predefinedStructure = null;
        User user = userService.find(request.getUserId());

        if(request.getPredefinedStructureId()!=null){
            predefinedStructure = predefinedStructureService.find(request.getPredefinedStructureId());

            if(!predefinedStructure.getCreatedBy().getOwner().equals(user.getOwner())){
                throw new ProductException("PredefinedStructure not owned by User");
            }

            validateAttributes(request.getAttributes(),predefinedStructure);
        }

        Product product = new Product();
        product.setCreatedBy(user);
        product.setDescription(request.getDescription());
        product.setName(request.getName());
        product.setPredefinedStructure(predefinedStructure);
        product.setProductAttributes(constructProductAttributes(request.getAttributes()));

        Product savedProduct = productRepository.saveAndFlush(product);

        productSearchService.register(new org.project.system.productsearch.domain.Product(product));

        SaveProductResponse saveProductResponse = new SaveProductResponse();
        saveProductResponse.setProduct(savedProduct);

        return saveProductResponse;

    }

    private List<ProductAttribute> constructProductAttributes(List<Attribute> attributes) throws ProductException {

        List<ProductAttribute> productAttributes = new ArrayList<>();

        for(Attribute attribute : attributes) {

            org.project.system.product.domain.attribute.Attribute domainAttribute = attributeService.find(attribute.getId());

            productAttributes.add(new ProductAttribute(domainAttribute,attribute.getValue()));
        }

        return productAttributes;


    }

    private void validateAttributes(List<org.project.system.product.service.model.Attribute> productAttributeList, PredefinedStructure predefinedStructure) throws ProductException {

        List<org.project.system.product.service.model.Attribute> auxiliaryProductAttributeList = productAttributeList !=null ? new ArrayList(productAttributeList) : new ArrayList<>();

        for(PredefinedStructureAttribute predefinedStructureAttribute : predefinedStructure.getStructureAttributes()){
            int index =getIndexOfAttribute(predefinedStructureAttribute,auxiliaryProductAttributeList);

            // if the product has the attribute defined by the predefinedStructure, remove it from the auxiliary list
            // later check the auxiliary list length
            if(index>-1){
                auxiliaryProductAttributeList.remove(index);
            }
            else if( predefinedStructureAttribute.isMandatory()){
                throw new ProductException("Missing mandatory Attribute");
            }
        }

        if(auxiliaryProductAttributeList.size()>0){
            throw new ProductException("Product attributes are different than specified in the predefined structure");
        }
    }

    private int getIndexOfAttribute(PredefinedStructureAttribute predefinedStructureAttribute, List<org.project.system.product.service.model.Attribute> productAttributeList) {

        for (int i = 0; i < productAttributeList.size(); i ++) {
            if(productAttributeList.get(i).getId().equals(predefinedStructureAttribute.getAttribute().getId())){
                return i;
            }
        }

        return -1;

    }
}
