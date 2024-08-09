/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class SetupAPIDevice {
    private final String device_instance_id;
    private final String device_name;

    public SetupAPIDevice(String string, String string2) {
        this.device_instance_id = string;
        this.device_name = string2;
    }

    public final String getName() {
        return this.device_name;
    }

    public final String getInstanceId() {
        return this.device_instance_id;
    }
}

