/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.IMetadataSection;

public class LanguageMetadataSection
implements IMetadataSection {
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> languagesIn) {
        this.languages = languagesIn;
    }

    public Collection<Language> getLanguages() {
        return this.languages;
    }
}

