package org.project.system.product.model.product;


import lombok.Data;
import org.project.system.user.model.User;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Data
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

}
