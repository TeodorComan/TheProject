package org.project.system.product.model.product;

import org.project.system.user.model.Account;
import org.project.system.user.model.User;

import java.util.List;

/**
 * Defines a structure of {@link Product} with a certain list of {@link PredefinedStructureAttribute} so that all products of that type can respect the structure defined for its type.
 * Ex. A book has attributes like: author, year in which it was written, etc. All books would have such attributes.
 * It is entirely up to the user how a product type is defined and not mandatory for a Product to actually have a predefined structure.
 */
public class PredefinedStructure {
    private String name;
    private String description;
    private List<PredefinedStructureAttribute> structureAttributes;
    private User createdBy;

}
