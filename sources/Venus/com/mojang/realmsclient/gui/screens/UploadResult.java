/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

public class UploadResult {
    public final int field_225179_a;
    public final String field_225180_b;

    private UploadResult(int n, String string) {
        this.field_225179_a = n;
        this.field_225180_b = string;
    }

    public static class Builder {
        private int field_225177_a = -1;
        private String field_225178_b;

        public Builder func_225175_a(int n) {
            this.field_225177_a = n;
            return this;
        }

        public Builder func_225176_a(String string) {
            this.field_225178_b = string;
            return this;
        }

        public UploadResult func_225174_a() {
            return new UploadResult(this.field_225177_a, this.field_225178_b);
        }
    }
}

