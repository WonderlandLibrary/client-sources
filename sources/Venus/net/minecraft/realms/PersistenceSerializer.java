/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import com.google.gson.Gson;
import net.minecraft.realms.IPersistentSerializable;

public class PersistenceSerializer {
    private final Gson field_237693_a_ = new Gson();

    public String func_237694_a_(IPersistentSerializable iPersistentSerializable) {
        return this.field_237693_a_.toJson(iPersistentSerializable);
    }

    public <T extends IPersistentSerializable> T func_237695_a_(String string, Class<T> clazz) {
        return (T)((IPersistentSerializable)this.field_237693_a_.fromJson(string, clazz));
    }
}

