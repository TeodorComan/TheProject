package org.project.system.product.service.model;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class CreatePredefinedStructureRequest {
    private PredefinedStructure predefinedStructure;
    private Long userId;

}
