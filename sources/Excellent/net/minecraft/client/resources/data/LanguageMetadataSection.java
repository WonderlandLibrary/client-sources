package net.minecraft.client.resources.data;

import net.minecraft.client.resources.Language;

import java.util.Collection;

public class LanguageMetadataSection
{
    public static final LanguageMetadataSectionSerializer field_195818_a = new LanguageMetadataSectionSerializer();
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> languagesIn)
    {
        this.languages = languagesIn;
    }

    public Collection<Language> getLanguages()
    {
        return this.languages;
    }
}
