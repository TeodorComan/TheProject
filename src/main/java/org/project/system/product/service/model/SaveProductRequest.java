package org.project.system.product.service.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SaveProductRequest {

    private Long predefinedStructureId;
    private String name;
    private String description;
    private List<Attribute> attributes;
    private Long userId;

}
