/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.donate;

public class CustomItem {
    private final String textureUrl;
    private final String name;
    private final String id;
    private final String description;

    public CustomItem(String textureUrl, String name, String id, String description) {
        this.textureUrl = textureUrl;
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public String getTextureUrl() {
        return this.textureUrl;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }
}

