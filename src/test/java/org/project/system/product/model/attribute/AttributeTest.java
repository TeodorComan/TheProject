package org.project.system.product.model.attribute;


import org.junit.Test;

public class AttributeTest {

    @Test(expected = Test.None.class)
    public void setCorrectUnitOfMeasureTest() {
        Attribute attribute = new Attribute();
        attribute.setType(ValueType.NUMERIC);
        attribute.setUnitOfMeasure(new UnitOfMeasure());
    }

    @Test(expected = Test.None.class)
    public void setUnitOfMeasureNoValueTypeTest() {
        Attribute attribute = new Attribute();
        attribute.setUnitOfMeasure(new UnitOfMeasure());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIncorrectUnitOfMeasureTest(){
        Attribute attribute = new Attribute();
        attribute.setType(ValueType.BOOLEAN);
        attribute.setUnitOfMeasure(new UnitOfMeasure());
    }

    @Test(expected = Test.None.class)
    public void setCorrectValueTypeTest() {
        Attribute attribute = new Attribute();
        attribute.setUnitOfMeasure(new UnitOfMeasure());
        attribute.setType(ValueType.NUMERIC);
    }

    @Test(expected = Test.None.class)
    public void setValueTypeNoUnitOfMeasureTest() {
        Attribute attribute = new Attribute();
        attribute.setType(ValueType.DATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIncorrectValueTypeTest(){
        Attribute attribute = new Attribute();
        attribute.setUnitOfMeasure(new UnitOfMeasure());
        attribute.setType(ValueType.BOOLEAN);
    }
}
