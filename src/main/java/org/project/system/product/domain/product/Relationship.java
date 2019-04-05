package org.project.system.product.domain.product;

import javax.persistence.Entity;

@Entity
public class Relationship {
    private String name;
    private RelationshipType type;
}
