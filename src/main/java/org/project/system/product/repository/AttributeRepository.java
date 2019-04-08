package org.project.system.product.repository;

import org.project.system.product.domain.attribute.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute,Long> {
}
