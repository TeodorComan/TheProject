package org.project.system.product.model.product;

import org.project.system.product.model.attribute.Attribute;

/**
 * Describes the behaviour of an {@link Attribute} in relationship to a certain {@link PredefinedStructure}.
 * Ex. the same attribute can be mandatory for a {@link PredefinedStructure} but optional for another.
 */
public class PredefinedStructureAttribute {
    private Attribute attribute;
    private boolean isMandatory;
}