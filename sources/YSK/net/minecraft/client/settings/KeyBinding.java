package net.minecraft.client.settings;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;

public class KeyBinding implements Comparable<KeyBinding>
{
    private final int keyCodeDefault;
    private boolean pressed;
    private final String keyCategory;
    private static final Set<String> keybindSet;
    private static final List<KeyBinding> keybindArray;
    private int pressTime;
    private int keyCode;
    private final String keyDescription;
    private static final IntHashMap<KeyBinding> hash;
    
    public static void onTick(final int n) {
        if (n != 0) {
            final KeyBinding keyBinding = KeyBinding.hash.lookup(n);
            if (keyBinding != null) {
                final KeyBinding keyBinding2 = keyBinding;
                keyBinding2.pressTime += " ".length();
            }
        }
    }
    
    public String getKeyDescription() {
        return this.keyDescription;
    }
    
    public boolean isPressed() {
        if (this.pressTime == 0) {
            return "".length() != 0;
        }
        this.pressTime -= " ".length();
        return " ".length() != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void setKeyBindState(final int n, final boolean pressed) {
        if (n != 0) {
            final KeyBinding keyBinding = KeyBinding.hash.lookup(n);
            if (keyBinding != null) {
                keyBinding.pressed = pressed;
            }
        }
    }
    
    public static void resetKeyBindingArrayAndHash() {
        KeyBinding.hash.clearMap();
        final Iterator<KeyBinding> iterator = KeyBinding.keybindArray.iterator();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final KeyBinding keyBinding = iterator.next();
            KeyBinding.hash.addKey(keyBinding.keyCode, keyBinding);
        }
    }
    
    public void setKeyCode(final int keyCode) {
        this.keyCode = keyCode;
    }
    
    public KeyBinding(final String keyDescription, final int n, final String keyCategory) {
        this.keyDescription = keyDescription;
        this.keyCode = n;
        this.keyCodeDefault = n;
        this.keyCategory = keyCategory;
        KeyBinding.keybindArray.add(this);
        KeyBinding.hash.addKey(n, this);
        KeyBinding.keybindSet.add(keyCategory);
    }
    
    public static Set<String> getKeybinds() {
        return KeyBinding.keybindSet;
    }
    
    private void unpressKey() {
        this.pressTime = "".length();
        this.pressed = ("".length() != 0);
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    static {
        keybindArray = Lists.newArrayList();
        hash = new IntHashMap<KeyBinding>();
        keybindSet = Sets.newHashSet();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((KeyBinding)o);
    }
    
    public String getKeyCategory() {
        return this.keyCategory;
    }
    
    public static void unPressAllKeys() {
        final Iterator<KeyBinding> iterator = KeyBinding.keybindArray.iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().unpressKey();
        }
    }
    
    @Override
    public int compareTo(final KeyBinding keyBinding) {
        int n = I18n.format(this.keyCategory, new Object["".length()]).compareTo(I18n.format(keyBinding.keyCategory, new Object["".length()]));
        if (n == 0) {
            n = I18n.format(this.keyDescription, new Object["".length()]).compareTo(I18n.format(keyBinding.keyDescription, new Object["".length()]));
        }
        return n;
    }
    
    public boolean isKeyDown() {
        return this.pressed;
    }
    
    public int getKeyCodeDefault() {
        return this.keyCodeDefault;
    }
}
