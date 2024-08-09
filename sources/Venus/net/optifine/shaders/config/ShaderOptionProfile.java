/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.ArrayList;
import net.optifine.Lang;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderProfile;

public class ShaderOptionProfile
extends ShaderOption {
    private ShaderProfile[] profiles = null;
    private ShaderOption[] options = null;
    private static final String NAME_PROFILE = "<profile>";
    private static final String VALUE_CUSTOM = "<custom>";

    public ShaderOptionProfile(ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray) {
        super(NAME_PROFILE, "", ShaderOptionProfile.detectProfileName(shaderProfileArray, shaderOptionArray), ShaderOptionProfile.getProfileNames(shaderProfileArray), ShaderOptionProfile.detectProfileName(shaderProfileArray, shaderOptionArray, true), null);
        this.profiles = shaderProfileArray;
        this.options = shaderOptionArray;
    }

    @Override
    public void nextValue() {
        super.nextValue();
        if (this.getValue().equals(VALUE_CUSTOM)) {
            super.nextValue();
        }
        this.applyProfileOptions();
    }

    public void updateProfile() {
        ShaderProfile shaderProfile = this.getProfile(this.getValue());
        if (shaderProfile == null || !ShaderUtils.matchProfile(shaderProfile, this.options, false)) {
            String string = ShaderOptionProfile.detectProfileName(this.profiles, this.options);
            this.setValue(string);
        }
    }

    private void applyProfileOptions() {
        ShaderProfile shaderProfile = this.getProfile(this.getValue());
        if (shaderProfile != null) {
            String[] stringArray = shaderProfile.getOptions();
            for (int i = 0; i < stringArray.length; ++i) {
                String string = stringArray[i];
                ShaderOption shaderOption = this.getOption(string);
                if (shaderOption == null) continue;
                String string2 = shaderProfile.getValue(string);
                shaderOption.setValue(string2);
            }
        }
    }

    private ShaderOption getOption(String string) {
        for (int i = 0; i < this.options.length; ++i) {
            ShaderOption shaderOption = this.options[i];
            if (!shaderOption.getName().equals(string)) continue;
            return shaderOption;
        }
        return null;
    }

    private ShaderProfile getProfile(String string) {
        for (int i = 0; i < this.profiles.length; ++i) {
            ShaderProfile shaderProfile = this.profiles[i];
            if (!shaderProfile.getName().equals(string)) continue;
            return shaderProfile;
        }
        return null;
    }

    @Override
    public String getNameText() {
        return Lang.get("of.shaders.profile");
    }

    @Override
    public String getValueText(String string) {
        return string.equals(VALUE_CUSTOM) ? Lang.get("of.general.custom", VALUE_CUSTOM) : Shaders.translate("profile." + string, string);
    }

    @Override
    public String getValueColor(String string) {
        return string.equals(VALUE_CUSTOM) ? "\u00a7c" : "\u00a7a";
    }

    @Override
    public String getDescriptionText() {
        String string = Shaders.translate("profile.comment", null);
        if (string != null) {
            return string;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.profiles.length; ++i) {
            String string2;
            String string3 = this.profiles[i].getName();
            if (string3 == null || (string2 = Shaders.translate("profile." + string3 + ".comment", null)) == null) continue;
            stringBuffer.append(string2);
            if (string2.endsWith(". ")) continue;
            stringBuffer.append(". ");
        }
        return stringBuffer.toString();
    }

    private static String detectProfileName(ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray) {
        return ShaderOptionProfile.detectProfileName(shaderProfileArray, shaderOptionArray, false);
    }

    private static String detectProfileName(ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray, boolean bl) {
        ShaderProfile shaderProfile = ShaderUtils.detectProfile(shaderProfileArray, shaderOptionArray, bl);
        return shaderProfile == null ? VALUE_CUSTOM : shaderProfile.getName();
    }

    private static String[] getProfileNames(ShaderProfile[] shaderProfileArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < shaderProfileArray.length; ++i) {
            ShaderProfile shaderProfile = shaderProfileArray[i];
            arrayList.add(shaderProfile.getName());
        }
        arrayList.add(VALUE_CUSTOM);
        return arrayList.toArray(new String[arrayList.size()]);
    }
}

