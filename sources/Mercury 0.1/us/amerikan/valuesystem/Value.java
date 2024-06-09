/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.valuesystem;

public class Value<T> {
    private String valueName;
    private T valueObject;

    public Value(String valueName, T valueObject) {
        this.valueName = valueName;
        this.valueObject = valueObject;
    }

    public String getValueName() {
        return this.valueName;
    }

    public T getObject() {
        return this.valueObject;
    }

    public void setObject(T valueObject) {
        this.valueObject = valueObject;
    }
}

