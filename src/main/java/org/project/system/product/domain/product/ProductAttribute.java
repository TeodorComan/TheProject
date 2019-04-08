package org.project.system.product.domain.product;

import lombok.Data;
import org.project.system.product.ProductException;
import org.project.system.product.domain.attribute.Attribute;
import org.project.system.product.domain.attribute.ValueType;

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

    public ProductAttribute(Attribute attribute, String value) throws ProductException {

        //@TODO validate value matches attribute value type
        this.attribute = attribute;
        this.value = value;
    }

    public ProductAttribute() {
    }

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
