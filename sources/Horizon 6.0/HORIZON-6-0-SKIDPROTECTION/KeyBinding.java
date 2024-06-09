package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import java.util.Set;
import java.util.List;

public class KeyBinding implements Comparable
{
    private static final List Â;
    private static final IntHashMap Ý;
    private static final Set Ø­áŒŠá;
    private final String Âµá€;
    private final int Ó;
    private final String à;
    private int Ø;
    public boolean HorizonCode_Horizon_È;
    private int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000628";
    
    static {
        Â = Lists.newArrayList();
        Ý = new IntHashMap();
        Ø­áŒŠá = Sets.newHashSet();
    }
    
    public static void HorizonCode_Horizon_È(final int keyCode) {
        if (keyCode != 0) {
            final KeyBinding var1 = (KeyBinding)KeyBinding.Ý.HorizonCode_Horizon_È(keyCode);
            if (var1 != null) {
                final KeyBinding keyBinding = var1;
                ++keyBinding.áŒŠÆ;
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final int keyCode, final boolean pressed) {
        if (keyCode != 0) {
            final KeyBinding var2 = (KeyBinding)KeyBinding.Ý.HorizonCode_Horizon_È(keyCode);
            if (var2 != null) {
                var2.HorizonCode_Horizon_È = pressed;
            }
        }
    }
    
    public static void HorizonCode_Horizon_È() {
        for (final KeyBinding var2 : KeyBinding.Â) {
            var2.áˆºÑ¢Õ();
        }
    }
    
    public static void Â() {
        KeyBinding.Ý.HorizonCode_Horizon_È();
        for (final KeyBinding var2 : KeyBinding.Â) {
            KeyBinding.Ý.HorizonCode_Horizon_È(var2.Ø, var2);
        }
    }
    
    public static Set Ý() {
        return KeyBinding.Ø­áŒŠá;
    }
    
    public KeyBinding(final String description, final int keyCode, final String category) {
        this.Âµá€ = description;
        this.Ø = keyCode;
        this.Ó = keyCode;
        this.à = category;
        KeyBinding.Â.add(this);
        KeyBinding.Ý.HorizonCode_Horizon_È(keyCode, this);
        KeyBinding.Ø­áŒŠá.add(category);
    }
    
    public boolean Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Âµá€() {
        return this.à;
    }
    
    public boolean Ó() {
        if (this.áŒŠÆ == 0) {
            return false;
        }
        --this.áŒŠÆ;
        return true;
    }
    
    private void áˆºÑ¢Õ() {
        this.áŒŠÆ = 0;
        this.HorizonCode_Horizon_È = false;
    }
    
    public String à() {
        return this.Âµá€;
    }
    
    public int Ø() {
        return this.Ó;
    }
    
    public int áŒŠÆ() {
        return this.Ø;
    }
    
    public void Â(final int keyCode) {
        this.Ø = keyCode;
    }
    
    public int HorizonCode_Horizon_È(final KeyBinding p_compareTo_1_) {
        int var2 = I18n.HorizonCode_Horizon_È(this.à, new Object[0]).compareTo(I18n.HorizonCode_Horizon_È(p_compareTo_1_.à, new Object[0]));
        if (var2 == 0) {
            var2 = I18n.HorizonCode_Horizon_È(this.Âµá€, new Object[0]).compareTo(I18n.HorizonCode_Horizon_È(p_compareTo_1_.Âµá€, new Object[0]));
        }
        return var2;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.HorizonCode_Horizon_È((KeyBinding)p_compareTo_1_);
    }
}
