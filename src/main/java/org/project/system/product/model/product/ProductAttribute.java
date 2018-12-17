package org.project.system.product.model.product;

import org.project.system.product.model.attribute.Attribute;

/**
 * Specify the value of an attribute for a certain product.
 */
public class ProductAttribute {

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
