package org.project.system.product.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.system.product.ProductException;
import org.project.system.product.domain.attribute.Attribute;
import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.domain.product.PredefinedStructureAttribute;
import org.project.system.product.domain.product.Product;
import org.project.system.product.repository.ProductRepository;
import org.project.system.product.service.model.SaveProductRequest;
import org.project.system.productsearch.service.ProductSearchService;
import org.project.system.user.domain.Account;
import org.project.system.user.domain.User;
import org.project.system.user.service.UserService;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService productService = new ProductService();

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductSearchService productSearchService;

    @Mock
    private PredefinedStructureService predefinedStructureService;

    @Mock
    private UserService userService;

    @Mock
    private AttributeService attributeService;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp(){
        ReflectionTestUtils.setField(productService,"productSearchService",productSearchService);
        ReflectionTestUtils.setField(productService,"productRepository",productRepository);
        ReflectionTestUtils.setField(productService,"predefinedStructureService",predefinedStructureService);
        ReflectionTestUtils.setField(productService,"userService",userService);
        ReflectionTestUtils.setField(productService,"attributeService",attributeService);
    }


    @Test
    public void test_CreateProductWithDifferentAttributesThanPredefinedStructure() throws ProductException{

        SaveProductRequest saveProductRequest = new SaveProductRequest();
        saveProductRequest.setPredefinedStructureId(1l);
        saveProductRequest.setUserId(2L);

        List<org.project.system.product.service.model.Attribute> attributes = new ArrayList<>();
        org.project.system.product.service.model.Attribute attribute = new org.project.system.product.service.model.Attribute();
        attribute.setId(2L);
        attributes.add(attribute);
        saveProductRequest.setAttributes(attributes);

        PredefinedStructure predefinedStructure = generatePredefinedStructureWithUser();
        List<PredefinedStructureAttribute> structureAttributes = new ArrayList();
        PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
        Attribute structureAttribute = new Attribute();
        structureAttribute.setId(1L);

        predefinedStructureAttribute.setAttribute(structureAttribute);
        predefinedStructure.setStructureAttributes(structureAttributes);
        structureAttributes.add(predefinedStructureAttribute);

        Mockito.when(predefinedStructureService.find(1l)).thenReturn(predefinedStructure);

        User requestUser = generateUserWithOwner(2L,1L);
        Mockito.when(userService.find(2L)).thenReturn(Optional.of(requestUser));

        exception.expect(ProductException.class);
        exception.expectMessage("Product attributes are different than specified in the predefined structure");

        productService.saveProduct(saveProductRequest);
    }

    @Test
    public void test_CreateProductMissingMandatoryAttribute() throws ProductException{
        SaveProductRequest saveProductRequest = new SaveProductRequest();
        saveProductRequest.setPredefinedStructureId(1l);
        saveProductRequest.setUserId(2L);

        PredefinedStructure predefinedStructure = generatePredefinedStructureWithUser();
        List<PredefinedStructureAttribute> structureAttributes = new ArrayList();
        PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
        predefinedStructureAttribute.setMandatory(true);
        Attribute structureAttribute = new Attribute();
        structureAttribute.setId(1L);

        predefinedStructureAttribute.setAttribute(structureAttribute);
        predefinedStructure.setStructureAttributes(structureAttributes);
        structureAttributes.add(predefinedStructureAttribute);

        Mockito.when(predefinedStructureService.find(1l)).thenReturn(predefinedStructure);

        User requestUser = generateUserWithOwner(2L,3L);
        requestUser.setOwner(predefinedStructure.getCreatedBy().getOwner());
        Mockito.when(userService.find(2l)).thenReturn(Optional.of(requestUser));


        exception.expect(ProductException.class);
        exception.expectMessage("Missing mandatory Attribute");

        productService.saveProduct(saveProductRequest);

    }

    @Test
    public void test_CreateProductWithTemplateOwnedBySomebodyElse() throws ProductException{
        SaveProductRequest saveProductRequest = new SaveProductRequest();
        saveProductRequest.setPredefinedStructureId(1L);
        saveProductRequest.setUserId(2L);

        PredefinedStructure predefinedStructure = generatePredefinedStructureWithUser();
        Mockito.when(predefinedStructureService.find(1L)).thenReturn(predefinedStructure);

        User requestUser = generateUserWithOwner(2L,3L);
        Mockito.when(userService.find(2l)).thenReturn(Optional.of(requestUser));


        exception.expect(ProductException.class);
        exception.expectMessage("PredefinedStructure not owned by User");

        productService.saveProduct(saveProductRequest);
    }

    @Test
    public void test_CreateProductWithPredefinedStructure() throws ProductException{
        SaveProductRequest saveProductRequest = new SaveProductRequest();
        saveProductRequest.setPredefinedStructureId(1l);
        saveProductRequest.setUserId(1L);

        List<org.project.system.product.service.model.Attribute> attributes = new ArrayList<>();
        org.project.system.product.service.model.Attribute attribute = new org.project.system.product.service.model.Attribute();
        attribute.setId(1L);
        attribute.setValue("red");

        attributes.add(attribute);
        saveProductRequest.setAttributes(attributes);

        User requestUser = generateUserWithOwner(1L,1L);
        Mockito.when(userService.find(1l)).thenReturn(Optional.of(requestUser));

        PredefinedStructure predefinedStructure = generatePredefinedStructureWithUser();
        List<PredefinedStructureAttribute> structureAttributes = new ArrayList();
        PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
        predefinedStructureAttribute.setMandatory(true);
        Attribute structureAttribute = new Attribute();
        structureAttribute.setId(1L);

        predefinedStructureAttribute.setAttribute(structureAttribute);
        predefinedStructure.setStructureAttributes(structureAttributes);
        structureAttributes.add(predefinedStructureAttribute);

        Mockito.when(predefinedStructureService.find(1l)).thenReturn(predefinedStructure);
        Mockito.when(attributeService.find(1L)).thenReturn(structureAttribute);

        productService.saveProduct(saveProductRequest);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        Mockito.verify(productRepository).saveAndFlush(captor.capture());

        Assert.assertEquals(attribute.getId(),captor.getValue().getProductAttributes().get(0).getAttribute().getId());
        Assert.assertEquals("red",captor.getValue().getProductAttributes().get(0).getValue());


        Mockito.verify(productSearchService).register(Mockito.any());


    }

    private PredefinedStructure generatePredefinedStructureWithUser(){
        PredefinedStructure predefinedStructure = new PredefinedStructure();
        predefinedStructure.setId(1L);
        predefinedStructure.setCreatedBy(generateUserWithOwner(1L,1L));

        return predefinedStructure;
    }

    private User generateUserWithOwner(Long userId, Long ownerId){
        User user = new User();
        user.setId(userId);
        Account owner = new Account();
        owner.setId(ownerId);
        user.setOwner(owner);

        return user;
    }


}
