package org.project.system.product.model.product;


import org.project.system.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Product {
    private String name;
    private PredefinedStructure predefinedStructure;
    private String description;
    private Map<Relationship,List<Product>> relatedTo;
    private List<ProductAttribute> productAttributes;
    private User createdBy;

    public Optional<PredefinedStructure> getPredefinedStructure() {
        return Optional.of(predefinedStructure);
    }

    public void setPredefinedStructure(PredefinedStructure predefinedStructure) {
        this.predefinedStructure = predefinedStructure;
    }

}
