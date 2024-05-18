package net.labymod.spm;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class SPMBinding implements Comparable<SPMBinding>
{
    private static final List<SPMBinding> keybindArray = Lists.<SPMBinding>newArrayList();
    private static final IntHashMap<SPMBinding> hash = new IntHashMap();
    private static final Set<String> keybindSet = Sets.<String>newHashSet();
    private final String keyDescription;
    private final int keyCodeDefault;
    private final String keyCategory;
    private int keyCode;
    private boolean pressed;
    private int pressTime;

    public static void onTick(int keyCode)
    {
        if (keyCode != 0)
        {
            SPMBinding spmbinding = (SPMBinding)hash.lookup(keyCode);

            if (spmbinding != null)
            {
                ++spmbinding.pressTime;
            }
        }
    }

    public static void setKeyBindState(int keyCode, boolean pressed)
    {
        if (keyCode != 0)
        {
            SPMBinding spmbinding = (SPMBinding)hash.lookup(keyCode);

            if (spmbinding != null)
            {
                spmbinding.pressed = pressed;
            }
        }
    }

    public static void unPressAllKeys()
    {
        for (SPMBinding spmbinding : keybindArray)
        {
            spmbinding.unpressKey();
        }
    }

    public static void resetKeyBindingArrayAndHash()
    {
        hash.clearMap();

        for (SPMBinding spmbinding : keybindArray)
        {
            hash.addKey(spmbinding.keyCode, spmbinding);
        }
    }

    public static Set<String> getKeybinds()
    {
        return keybindSet;
    }

    public SPMBinding(String description, int keyCode, String category)
    {
        this.keyDescription = description;
        this.keyCode = keyCode;
        this.keyCodeDefault = keyCode;
        this.keyCategory = category;
        keybindArray.add(this);
        hash.addKey(keyCode, this);
        keybindSet.add(category);
    }

    public boolean isKeyDown()
    {
        return this.pressed;
    }

    public String getKeyCategory()
    {
        return this.keyCategory;
    }

    public boolean isPressed()
    {
        if (this.pressTime == 0)
        {
            return false;
        }
        else
        {
            --this.pressTime;
            return true;
        }
    }

    private void unpressKey()
    {
        this.pressTime = 0;
        this.pressed = false;
    }

    public String getKeyDescription()
    {
        return this.keyDescription;
    }

    public int getKeyCodeDefault()
    {
        return this.keyCodeDefault;
    }

    public int getKeyCode()
    {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode)
    {
        this.keyCode = keyCode;
    }

    public int compareTo(SPMBinding p_compareTo_1_)
    {
        int i = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));

        if (i == 0)
        {
            i = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
        }

        return i;
    }
}
