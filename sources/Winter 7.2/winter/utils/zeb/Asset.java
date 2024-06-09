/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.zeb;

import java.io.InputStream;

public class Asset {
    private String label;

    public Asset(String label) {
        this.label = label;
    }

    public InputStream asInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream(this.label);
    }
}

