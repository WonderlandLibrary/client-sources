/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class KeyBinding
implements Comparable<KeyBinding> {
    private static final Map<String, KeyBinding> KEYBIND_ARRAY = Maps.newHashMap();
    private static final Map<InputMappings.Input, KeyBinding> HASH = Maps.newHashMap();
    private static final Set<String> KEYBIND_SET = Sets.newHashSet();
    private static final Map<String, Integer> CATEGORY_ORDER = Util.make(Maps.newHashMap(), KeyBinding::lambda$static$0);
    private final String keyDescription;
    private final InputMappings.Input keyCodeDefault;
    private final String keyCategory;
    private InputMappings.Input keyCode;
    public boolean pressed;
    private int pressTime;

    public static void onTick(InputMappings.Input input) {
        KeyBinding keyBinding = HASH.get(input);
        if (keyBinding != null) {
            ++keyBinding.pressTime;
        }
    }

    public static void setKeyBindState(InputMappings.Input input, boolean bl) {
        KeyBinding keyBinding = HASH.get(input);
        if (keyBinding != null) {
            keyBinding.setPressed(bl);
        }
    }

    public static void updateKeyBindState() {
        for (KeyBinding keyBinding : KEYBIND_ARRAY.values()) {
            if (keyBinding.keyCode.getType() != InputMappings.Type.KEYSYM || keyBinding.keyCode.getKeyCode() == InputMappings.INPUT_INVALID.getKeyCode()) continue;
            keyBinding.setPressed(InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), keyBinding.keyCode.getKeyCode()));
        }
    }

    public static void unPressAllKeys() {
        for (KeyBinding keyBinding : KEYBIND_ARRAY.values()) {
            keyBinding.unpressKey();
        }
    }

    public static void resetKeyBindingArrayAndHash() {
        HASH.clear();
        for (KeyBinding keyBinding : KEYBIND_ARRAY.values()) {
            HASH.put(keyBinding.keyCode, keyBinding);
        }
    }

    public KeyBinding(String string, int n, String string2) {
        this(string, InputMappings.Type.KEYSYM, n, string2);
    }

    public KeyBinding(String string, InputMappings.Type type, int n, String string2) {
        this.keyDescription = string;
        this.keyCodeDefault = this.keyCode = type.getOrMakeInput(n);
        this.keyCategory = string2;
        KEYBIND_ARRAY.put(string, this);
        HASH.put(this.keyCode, this);
        KEYBIND_SET.add(string2);
    }

    public boolean isKeyDown() {
        return this.pressed;
    }

    public String getKeyCategory() {
        return this.keyCategory;
    }

    public boolean isPressed() {
        if (this.pressTime == 0) {
            return true;
        }
        --this.pressTime;
        return false;
    }

    private void unpressKey() {
        this.pressTime = 0;
        this.setPressed(true);
    }

    public String getKeyDescription() {
        return this.keyDescription;
    }

    public InputMappings.Input getDefault() {
        return this.keyCodeDefault;
    }

    public void bind(InputMappings.Input input) {
        this.keyCode = input;
    }

    @Override
    public int compareTo(KeyBinding keyBinding) {
        return this.keyCategory.equals(keyBinding.keyCategory) ? I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(keyBinding.keyDescription, new Object[0])) : CATEGORY_ORDER.get(this.keyCategory).compareTo(CATEGORY_ORDER.get(keyBinding.keyCategory));
    }

    public static Supplier<ITextComponent> getDisplayString(String string) {
        KeyBinding keyBinding = KEYBIND_ARRAY.get(string);
        return keyBinding == null ? () -> KeyBinding.lambda$getDisplayString$1(string) : keyBinding::func_238171_j_;
    }

    public boolean conflicts(KeyBinding keyBinding) {
        return this.keyCode.equals(keyBinding.keyCode);
    }

    public boolean isInvalid() {
        return this.keyCode.equals(InputMappings.INPUT_INVALID);
    }

    public boolean matchesKey(int n, int n2) {
        if (n == InputMappings.INPUT_INVALID.getKeyCode()) {
            return this.keyCode.getType() == InputMappings.Type.SCANCODE && this.keyCode.getKeyCode() == n2;
        }
        return this.keyCode.getType() == InputMappings.Type.KEYSYM && this.keyCode.getKeyCode() == n;
    }

    public boolean matchesMouseKey(int n) {
        return this.keyCode.getType() == InputMappings.Type.MOUSE && this.keyCode.getKeyCode() == n;
    }

    public ITextComponent func_238171_j_() {
        return this.keyCode.func_237520_d_();
    }

    public boolean isDefault() {
        return this.keyCode.equals(this.keyCodeDefault);
    }

    public String getTranslationKey() {
        return this.keyCode.getTranslationKey();
    }

    public void setPressed(boolean bl) {
        this.pressed = bl;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((KeyBinding)object);
    }

    private static ITextComponent lambda$getDisplayString$1(String string) {
        return new TranslationTextComponent(string);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("key.categories.movement", 1);
        hashMap.put("key.categories.gameplay", 2);
        hashMap.put("key.categories.inventory", 3);
        hashMap.put("key.categories.creative", 4);
        hashMap.put("key.categories.multiplayer", 5);
        hashMap.put("key.categories.ui", 6);
        hashMap.put("key.categories.misc", 7);
    }
}

