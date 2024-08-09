package dev.darkmoon.client.module.setting;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

public class Setting {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Object value;

    @Getter
    @Setter
    protected Supplier<Boolean> visible;

    public Setting(String name, Object value){
        this.name = name;
        this.value = value;
    }
}

