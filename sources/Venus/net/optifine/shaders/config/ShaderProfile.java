/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import net.optifine.shaders.config.ShaderOption;

public class ShaderProfile {
    private String name = null;
    private Map<String, String> mapOptionValues = new LinkedHashMap<String, String>();
    private Set<String> disabledPrograms = new LinkedHashSet<String>();

    public ShaderProfile(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public void addOptionValue(String string, String string2) {
        this.mapOptionValues.put(string, string2);
    }

    public void addOptionValues(ShaderProfile shaderProfile) {
        if (shaderProfile != null) {
            this.mapOptionValues.putAll(shaderProfile.mapOptionValues);
        }
    }

    public void applyOptionValues(ShaderOption[] shaderOptionArray) {
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            ShaderOption shaderOption = shaderOptionArray[i];
            String string = shaderOption.getName();
            String string2 = this.mapOptionValues.get(string);
            if (string2 == null) continue;
            shaderOption.setValue(string2);
        }
    }

    public String[] getOptions() {
        Set<String> set = this.mapOptionValues.keySet();
        return set.toArray(new String[set.size()]);
    }

    public String getValue(String string) {
        return this.mapOptionValues.get(string);
    }

    public void addDisabledProgram(String string) {
        this.disabledPrograms.add(string);
    }

    public void removeDisabledProgram(String string) {
        this.disabledPrograms.remove(string);
    }

    public Collection<String> getDisabledPrograms() {
        return new LinkedHashSet<String>(this.disabledPrograms);
    }

    public void addDisabledPrograms(Collection<String> collection) {
        this.disabledPrograms.addAll(collection);
    }

    public boolean isProgramDisabled(String string) {
        return this.disabledPrograms.contains(string);
    }
}

