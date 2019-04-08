package org.project.system.product.service;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.system.product.ProductException;
import org.project.system.product.domain.attribute.Attribute;
import org.project.system.product.domain.product.PredefinedStructure;
import org.project.system.product.domain.product.PredefinedStructureAttribute;
import org.project.system.product.repository.ProductRepository;
import org.project.system.product.service.model.SaveProductRequest;
import org.project.system.productsearch.service.ProductSearchService;
import org.project.system.user.domain.Account;
import org.project.system.user.domain.User;
import org.project.system.user.service.UserService;

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

    @Rule
    public ExpectedException exception = ExpectedException.none();



    @Before
    public void setUp(){
        productService.setProductSearchService(productSearchService);
        productService.setProductRepository(productRepository);
        productService.setPredefinedStructureService(predefinedStructureService);
        productService.setUserService(userService);
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

        PredefinedStructure predefinedStructure = createPredefinedStructureWithUser();
        List<PredefinedStructureAttribute> structureAttributes = new ArrayList();
        PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
        Attribute structureAttribute = new Attribute();
        structureAttribute.setId(1L);

        predefinedStructureAttribute.setAttribute(structureAttribute);
        predefinedStructure.setStructureAttributes(structureAttributes);
        structureAttributes.add(predefinedStructureAttribute);

        Mockito.when(predefinedStructureService.find(1l)).thenReturn(Optional.of(predefinedStructure));

        User requestUser = new User();
        requestUser.setId(2L);
        Account owner = new Account();
        owner.setId(3L);
        requestUser.setOwner(predefinedStructure.getCreatedBy().getOwner());
        Mockito.when(userService.find(2l)).thenReturn(Optional.of(requestUser));

        exception.expect(ProductException.class);
        exception.expectMessage("Product attributes are different than specified in the predefined structure");

        productService.saveProduct(saveProductRequest);
    }

    @Test
    public void test_CreateProductMissingMandatoryAttribute() throws ProductException{
        SaveProductRequest saveProductRequest = new SaveProductRequest();
        saveProductRequest.setPredefinedStructureId(1l);
        saveProductRequest.setUserId(2L);

        PredefinedStructure predefinedStructure = createPredefinedStructureWithUser();
        List<PredefinedStructureAttribute> structureAttributes = new ArrayList();
        PredefinedStructureAttribute predefinedStructureAttribute = new PredefinedStructureAttribute();
        predefinedStructureAttribute.setMandatory(true);
        Attribute structureAttribute = new Attribute();
        structureAttribute.setId(1L);

        predefinedStructureAttribute.setAttribute(structureAttribute);
        predefinedStructure.setStructureAttributes(structureAttributes);
        structureAttributes.add(predefinedStructureAttribute);

        Mockito.when(predefinedStructureService.find(1l)).thenReturn(Optional.of(predefinedStructure));

        User requestUser = new User();
        requestUser.setId(2L);
        Account owner = new Account();
        owner.setId(3L);
        requestUser.setOwner(predefinedStructure.getCreatedBy().getOwner());
        Mockito.when(userService.find(2l)).thenReturn(Optional.of(requestUser));


        exception.expect(ProductException.class);
        exception.expectMessage("Missing mandatory Attribute");

        productService.saveProduct(saveProductRequest);

    }

    @Test
    public void test_CreateProductWithTemplateOwnedBySomebodyElse() throws ProductException{
        SaveProductRequest saveProductRequest = new SaveProductRequest();
        saveProductRequest.setPredefinedStructureId(1l);
        saveProductRequest.setUserId(2L);

        PredefinedStructure predefinedStructure = createPredefinedStructureWithUser();
        Mockito.when(predefinedStructureService.find(1l)).thenReturn(Optional.of(predefinedStructure));

        User requestUser = new User();
        requestUser.setId(2L);
        Account owner = new Account();
        owner.setId(3L);
        requestUser.setOwner(owner);
        Mockito.when(userService.find(2l)).thenReturn(Optional.of(requestUser));


        exception.expect(ProductException.class);
        exception.expectMessage("PredefinedStructure not owned by User");

        productService.saveProduct(saveProductRequest);
    }

    @Test
    public void test_CreateProductWithUnknownPredefinedStructure() throws ProductException{
        SaveProductRequest saveProductRequest = new SaveProductRequest();
        saveProductRequest.setPredefinedStructureId(1l);
        saveProductRequest.setUserId(2L);

        Mockito.when(predefinedStructureService.find(1l)).thenReturn(Optional.empty());

        User requestUser = new User();
        requestUser.setId(2L);
        Account owner = new Account();
        owner.setId(3L);
        requestUser.setOwner(owner);
        Mockito.when(userService.find(2l)).thenReturn(Optional.of(requestUser));


        exception.expect(ProductException.class);
        exception.expectMessage("PredefinedStructure doesn't exist");

        productService.saveProduct(saveProductRequest);
    }

    private PredefinedStructure createPredefinedStructureWithUser(){
        PredefinedStructure predefinedStructure = new PredefinedStructure();
        User user = new User();
        Account account = new Account();
        account.setId(1L);
        user.setOwner(account);
        user.setId(1L);
        predefinedStructure.setCreatedBy(user);

        return predefinedStructure;
    }


}
