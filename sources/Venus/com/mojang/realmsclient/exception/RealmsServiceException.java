/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.client.RealmsError;
import net.minecraft.client.resources.I18n;

public class RealmsServiceException
extends Exception {
    public final int field_224981_a;
    public final String field_224982_b;
    public final int field_224983_c;
    public final String field_224984_d;

    public RealmsServiceException(int n, String string, RealmsError realmsError) {
        super(string);
        this.field_224981_a = n;
        this.field_224982_b = string;
        this.field_224983_c = realmsError.func_224974_b();
        this.field_224984_d = realmsError.func_224973_a();
    }

    public RealmsServiceException(int n, String string, int n2, String string2) {
        super(string);
        this.field_224981_a = n;
        this.field_224982_b = string;
        this.field_224983_c = n2;
        this.field_224984_d = string2;
    }

    @Override
    public String toString() {
        if (this.field_224983_c == -1) {
            return "Realms (" + this.field_224981_a + ") " + this.field_224982_b;
        }
        String string = "mco.errorMessage." + this.field_224983_c;
        String string2 = I18n.format(string, new Object[0]);
        return (string2.equals(string) ? this.field_224984_d : string2) + " - " + this.field_224983_c;
    }
}

