package org.project.system.product.service;

import org.project.system.product.ProductException;
import org.project.system.product.domain.attribute.Attribute;
import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.domain.product.PredefinedStructureAttribute;
import org.project.system.product.domain.product.Product;
import org.project.system.product.domain.product.ProductAttribute;
import org.project.system.product.repository.ProductRepository;
import org.project.system.productsearch.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSearchService productSearchService;

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setProductSearchService(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }


    @Transactional
    public Product saveProduct(Product product) throws ProductException{

        validate(product);

        Product savedProduct = productRepository.saveAndFlush(product);


        return savedProduct;

    }

    private void validate(Product product)throws ProductException {
        if(product.getPredefinedStructure().isPresent()) {
            validateAttributes(product.getProductAttributes(), product.getPredefinedStructure().get());
        }
    }

    private void validateAttributes(List<ProductAttribute> productAttributeList, PredefinedStructure predefinedStructure) throws ProductException {
        List<Attribute> predefinedStructureAttributes = predefinedStructure.getStructureAttributes().stream().map(predefinedStructureAttribute -> predefinedStructureAttribute.getAttribute()).collect(Collectors.toList());

        List<Attribute> auxiliaryProductAttributeList = new ArrayList();
        if(productAttributeList!=null){
            auxiliaryProductAttributeList = productAttributeList.stream().map(productAttribute -> productAttribute.getAttribute()).collect(Collectors.toList());
        }

        for(PredefinedStructureAttribute predefinedStructureAttribute : predefinedStructure.getStructureAttributes()){
            int index = auxiliaryProductAttributeList.indexOf(predefinedStructureAttribute.getAttribute());

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
}
