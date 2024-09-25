/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.type.types;

import java.util.LinkedList;
import java.util.List;
import us.myles.ViaVersion.api.type.Type;

public class Particle {
    private int id;
    private List<ParticleData> arguments = new LinkedList<ParticleData>();

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

    public void setArguments(List<ParticleData> arguments) {
        this.arguments = arguments;
    }

    public static class ParticleData {
        private Type type;
        private Object value;

        public ParticleData(Type type, Object value) {
            this.type = type;
            this.value = value;
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

        public void setValue(Object value) {
            this.value = value;
        }
    }
}

