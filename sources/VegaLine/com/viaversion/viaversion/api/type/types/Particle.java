/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.List;

public class Particle {
    private List<ParticleData> arguments = new ArrayList<ParticleData>(4);
    private int id;

    public Particle(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ParticleData> getArguments() {
        return this.arguments;
    }

    @Deprecated
    public void setArguments(List<ParticleData> arguments) {
        this.arguments = arguments;
    }

    public <T> void add(Type<T> type2, T value) {
        this.arguments.add(new ParticleData(type2, value));
    }

    public static class ParticleData {
        private Type type;
        private Object value;

        public ParticleData(Type type2, Object value) {
            this.type = type2;
            this.value = value;
        }

        public Type getType() {
            return this.type;
        }

        public void setType(Type type2) {
            this.type = type2;
        }

        public Object getValue() {
            return this.value;
        }

        public <T> T get() {
            return (T)this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String toString() {
            return "ParticleData{type=" + this.type + ", value=" + this.value + '}';
        }
    }
}

