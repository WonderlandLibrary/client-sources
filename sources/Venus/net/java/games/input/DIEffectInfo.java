/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.IDirectInputDevice;

final class DIEffectInfo {
    private final IDirectInputDevice device;
    private final byte[] guid;
    private final int guid_id;
    private final int effect_type;
    private final int static_params;
    private final int dynamic_params;
    private final String name;

    public DIEffectInfo(IDirectInputDevice iDirectInputDevice, byte[] byArray, int n, int n2, int n3, int n4, String string) {
        this.device = iDirectInputDevice;
        this.guid = byArray;
        this.guid_id = n;
        this.effect_type = n2;
        this.static_params = n3;
        this.dynamic_params = n4;
        this.name = string;
    }

    public final byte[] getGUID() {
        return this.guid;
    }

    public final int getGUIDId() {
        return this.guid_id;
    }

    public final int getDynamicParams() {
        return this.dynamic_params;
    }

    public final int getEffectType() {
        return this.effect_type;
    }

    public final String getName() {
        return this.name;
    }
}

