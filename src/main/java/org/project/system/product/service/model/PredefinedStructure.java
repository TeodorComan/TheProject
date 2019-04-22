package org.project.system.product.service.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PredefinedStructure {
    private Long id;
    private String name;
    private String description;
    private List<Attribute> attributes;
}
