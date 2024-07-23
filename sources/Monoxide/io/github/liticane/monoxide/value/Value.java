package io.github.liticane.monoxide.value;

import java.util.function.Supplier;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public abstract class Value<T> {
    private final String name;
    private String idName;
    private final Object owner;
    protected T value;

    private final ArrayList<Supplier<Boolean>> suppliers;

    public Value(String name, Object owner, T value, ArrayList<Supplier<Boolean>> suppliers) {
        this.name = name;
        this.idName = name;
        this.owner = owner;
        this.value = value;

        this.suppliers = suppliers;

        ValueManager.getInstance().add(this);
    }

    public Value(String name, Object owner, T value, Supplier<Boolean>[] suppliers) {
        this.name = name;
        this.idName = name;
        this.owner = owner;
        this.value = value;

        ArrayList<Supplier<Boolean>> suppliersList = new ArrayList<>();

        if(suppliers != null) {
            suppliersList.addAll(Arrays.asList(suppliers));
        }

        this.suppliers = suppliersList;

        ValueManager.getInstance().add(this);
    }

    public Value(String name, Object owner, T value) {
        this.name = name;
        this.idName = name;
        this.owner = owner;
        this.value = value;

        this.suppliers = null;

        ValueManager.getInstance().add(this);
    }

    @SuppressWarnings("unchecked")
    public <V extends Value<?>> V setIdName(String name) {
        this.idName = name;
        return (V) this;
    }

    public abstract String getValueAsString();

    public void setValue(T value) {
        this.value = value;
    }

    public abstract void setValue(String string);

    public boolean isVisible(){
        if (this.suppliers == null)
            return true;

        for (Supplier<Boolean> booleanSupplier : this.suppliers)
            if (!booleanSupplier.get())
                return false;

        return true;
    }

}
