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

    public Particle(int n) {
        this.id = n;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int n) {
        this.id = n;
    }

    public List<ParticleData> getArguments() {
        return this.arguments;
    }

    @Deprecated
    public void setArguments(List<ParticleData> list) {
        this.arguments = list;
    }

    public <T> void add(Type<T> type, T t) {
        this.arguments.add(new ParticleData(type, t));
    }

    public static class ParticleData {
        private Type type;
        private Object value;

        public ParticleData(Type type, Object object) {
            this.type = type;
            this.value = object;
        }

        public Type getType() {
            return this.type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Object getValue() {
            return this.value;
        }

        public <T> T get() {
            return (T)this.value;
        }

        public void setValue(Object object) {
            this.value = object;
        }

        public String toString() {
            return "ParticleData{type=" + this.type + ", value=" + this.value + '}';
        }
    }
}

