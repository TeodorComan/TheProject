package org.project.system.product.domain.product;

import lombok.Data;
import org.project.system.product.domain.attribute.Attribute;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Specify the value of an attribute for a certain product.
 */
@Data
@Entity
public class ProductAttribute {

    @Id
    private Long id;
    private Attribute attribute;
    private String value;

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
