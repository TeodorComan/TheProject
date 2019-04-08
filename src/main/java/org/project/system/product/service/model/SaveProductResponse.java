package org.project.system.product.service.model;

import lombok.Data;
import lombok.ToString;
import org.project.system.product.domain.product.Product;

@Data
@ToString
public class SaveProductResponse {

    private Product product;
}
