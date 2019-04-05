package org.project.system.product.service;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.system.product.ProductException;
import org.project.system.product.domain.attribute.Attribute;
import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.domain.product.PredefinedStructureAttribute;
import org.project.system.product.domain.product.Product;
import org.project.system.product.domain.product.ProductAttribute;
import org.project.system.product.repository.ProductRepository;
import org.project.system.productsearch.service.ProductSearchService;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService productService = new ProductService();

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductSearchService productSearchService;

    @Rule
    public ExpectedException exception = ExpectedException.none();



    @Before
    public void setUp(){
        productService.setProductSearchService(productSearchService);
        productService.setProductRepository(productRepository);
    }


    @Test
    public void test_CreateProductWithDifferentAttributesThanPredefinedStructure() throws ProductException{
        Product product = new Product();

        PredefinedStructure predefinedStructure = new PredefinedStructure();
        List<PredefinedStructureAttribute> structureAttributes = new ArrayList();
        PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
        Attribute structureAttribute = new Attribute();
        structureAttribute.setId(1L);
        predefinedStructureAttribute.setAttribute(structureAttribute);
        predefinedStructure.setStructureAttributes(structureAttributes);
        structureAttributes.add(predefinedStructureAttribute);
        product.setPredefinedStructure(predefinedStructure);

        List<ProductAttribute> productAttributes = new ArrayList<>();
        ProductAttribute productAttribute = new ProductAttribute();
        Attribute attribute = new Attribute();
        attribute.setId(2L);
        productAttribute.setAttribute(attribute);
        productAttributes.add(productAttribute);
        product.setProductAttributes(productAttributes);

        exception.expect(ProductException.class);
        exception.expectMessage("Product attributes are different than specified in the predefined structure");

        productService.saveProduct(product);
    }

    @Test
    public void test_CreateProductMissingMandatoryAttribute() throws ProductException{
        Product product = new Product();

        PredefinedStructure predefinedStructure = new PredefinedStructure();
        List<PredefinedStructureAttribute> structureAttributes = new ArrayList();
        PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
        Attribute structureAttribute = new Attribute();
        structureAttribute.setId(1L);
        predefinedStructureAttribute.setAttribute(structureAttribute);
        predefinedStructureAttribute.setMandatory(true);
        structureAttributes.add(predefinedStructureAttribute);

        predefinedStructure.setStructureAttributes(structureAttributes);
        product.setPredefinedStructure(predefinedStructure);


        exception.expect(ProductException.class);
        exception.expectMessage("Missing mandatory Attribute");

        productService.saveProduct(product);

    }

    @Test
    public void test_CreateProductWithTemplateOwnedBySomebodyElse() throws ProductException{

    }


}
