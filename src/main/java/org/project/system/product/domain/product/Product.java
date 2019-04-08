package org.project.system.product.domain.product;


import lombok.Data;
import org.project.system.user.domain.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Data
public class Product {

    @Id
    private Long id;
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
