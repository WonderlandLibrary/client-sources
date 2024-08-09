/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;

public class LanguageMetadataSection {
    public static final LanguageMetadataSectionSerializer field_195818_a = new LanguageMetadataSectionSerializer();
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> collection) {
        this.languages = collection;
    }

    public Collection<Language> getLanguages() {
        return this.languages;
    }
}

