package org.project.system.product.model.attribute;

import org.project.system.user.model.Account;
import org.project.system.user.model.User;

import java.util.Optional;

/**
 * Attributes are information pieces that describe a product. The same attribute can be used for different types of product.
 */
public class Attribute {
    private String name;
    private ValueType type;
    private UnitOfMeasure unitOfMeasure;
    private User createdBy;

    /**
     * A {@link UnitOfMeasure} makes sense only if the {@link ValueType} is {@link ValueType#NUMERIC}, {@link ValueType#NUMERIC_NEGATIV} or {@link ValueType#NUMERIC_POSITIVE}
     *
     * @return Optional<UnitOfMeasure>
     */
    public Optional<UnitOfMeasure> getUnitOfMeasure() {
        return Optional.ofNullable(unitOfMeasure);
    }

    /**
     * A {@link UnitOfMeasure} makes sense only if the {@link ValueType} is {@link ValueType#NUMERIC}, {@link ValueType#NUMERIC_NEGATIV} or {@link ValueType#NUMERIC_POSITIVE}
     *
     * @throws IllegalArgumentException if an incompatible {@link ValueType}
     */
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {

        if (type != null && (!type.equals(ValueType.NUMERIC) && !type.equals(ValueType.NUMERIC_NEGATIV) && !type.equals(ValueType.NUMERIC_POSITIVE))) {
            throw new IllegalArgumentException("A UnitOfMeasure can be set only for a numeric ValueType");
        }

        this.unitOfMeasure = unitOfMeasure;
    }

    public ValueType getType() {
        return type;
    }

    /**
     * If a {@link UnitOfMeasure} has been set, only the following values make sense {@link ValueType#NUMERIC}, {@link ValueType#NUMERIC_NEGATIV} and {@link ValueType#NUMERIC_POSITIVE}
     *
     * @throws IllegalArgumentException if a {@link UnitOfMeasure} has been set and the {@link ValueType} is incompatible
     */
    public void setType(ValueType type) {

        if (unitOfMeasure != null && (!type.equals(ValueType.NUMERIC) && !type.equals(ValueType.NUMERIC_NEGATIV) && !type.equals(ValueType.NUMERIC_POSITIVE))) {
            throw new IllegalArgumentException("Only a numeric ValueType is allowed due to the existence of a specified UnitOfMeasure");
        }
        this.type = type;
    }
}
