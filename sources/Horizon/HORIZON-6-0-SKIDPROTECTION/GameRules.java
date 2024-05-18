package HORIZON-6-0-SKIDPROTECTION;

import java.util.Set;
import java.util.Iterator;
import java.util.TreeMap;

public class GameRules
{
    private TreeMap HorizonCode_Horizon_È;
    private static final String Â = "CL_00000136";
    
    public GameRules() {
        this.HorizonCode_Horizon_È = new TreeMap();
        this.HorizonCode_Horizon_È("doFireTick", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("mobGriefing", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("keepInventory", "false", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("doMobSpawning", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("doMobLoot", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("doTileDrops", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("commandBlockOutput", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("naturalRegeneration", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("doDaylightCycle", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("logAdminCommands", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("showDeathMessages", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("randomTickSpeed", "3", GameRules.Â.Ý);
        this.HorizonCode_Horizon_È("sendCommandFeedback", "true", GameRules.Â.Â);
        this.HorizonCode_Horizon_È("reducedDebugInfo", "false", GameRules.Â.Â);
    }
    
    public void HorizonCode_Horizon_È(final String key, final String value, final Â type) {
        this.HorizonCode_Horizon_È.put(key, new HorizonCode_Horizon_È(value, type));
    }
    
    public void HorizonCode_Horizon_È(final String key, final String ruleValue) {
        final HorizonCode_Horizon_È var3 = this.HorizonCode_Horizon_È.get(key);
        if (var3 != null) {
            var3.HorizonCode_Horizon_È(ruleValue);
        }
        else {
            this.HorizonCode_Horizon_È(key, ruleValue, GameRules.Â.HorizonCode_Horizon_È);
        }
    }
    
    public String HorizonCode_Horizon_È(final String name) {
        final HorizonCode_Horizon_È var2 = this.HorizonCode_Horizon_È.get(name);
        return (var2 != null) ? var2.HorizonCode_Horizon_È() : "";
    }
    
    public boolean Â(final String name) {
        final HorizonCode_Horizon_È var2 = this.HorizonCode_Horizon_È.get(name);
        return var2 != null && var2.Â();
    }
    
    public int Ý(final String name) {
        final HorizonCode_Horizon_È var2 = this.HorizonCode_Horizon_È.get(name);
        return (var2 != null) ? var2.Ý() : 0;
    }
    
    public NBTTagCompound HorizonCode_Horizon_È() {
        final NBTTagCompound var1 = new NBTTagCompound();
        for (final String var3 : this.HorizonCode_Horizon_È.keySet()) {
            final HorizonCode_Horizon_È var4 = this.HorizonCode_Horizon_È.get(var3);
            var1.HorizonCode_Horizon_È(var3, var4.HorizonCode_Horizon_È());
        }
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        final Set var2 = nbt.Âµá€();
        for (final String var4 : var2) {
            final String var5 = nbt.áˆºÑ¢Õ(var4);
            this.HorizonCode_Horizon_È(var4, var5);
        }
    }
    
    public String[] Â() {
        return (String[])this.HorizonCode_Horizon_È.keySet().toArray(new String[0]);
    }
    
    public boolean Ø­áŒŠá(final String name) {
        return this.HorizonCode_Horizon_È.containsKey(name);
    }
    
    public boolean HorizonCode_Horizon_È(final String key, final Â otherValue) {
        final HorizonCode_Horizon_È var3 = this.HorizonCode_Horizon_È.get(key);
        return var3 != null && (var3.Ø­áŒŠá() == otherValue || otherValue == GameRules.Â.HorizonCode_Horizon_È);
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("ANY_VALUE", 0, "ANY_VALUE", 0, "ANY_VALUE", 0), 
        Â("BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1), 
        Ý("NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2);
        
        private static final Â[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002151";
        private static final Â[] Ó;
        
        static {
            à = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý };
            Ø­áŒŠá = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý };
            Ó = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý };
        }
        
        private Â(final String s, final int n, final String p_i46375_1_, final int p_i46375_2_, final String p_i45750_1_, final int p_i45750_2_) {
        }
    }
    
    static class HorizonCode_Horizon_È
    {
        private String HorizonCode_Horizon_È;
        private boolean Â;
        private int Ý;
        private double Ø­áŒŠá;
        private final Â Âµá€;
        private static final String Ó = "CL_00000137";
        
        public HorizonCode_Horizon_È(final String value, final Â type) {
            this.Âµá€ = type;
            this.HorizonCode_Horizon_È(value);
        }
        
        public void HorizonCode_Horizon_È(final String value) {
            this.HorizonCode_Horizon_È = value;
            if (value != null) {
                if (value.equals("false")) {
                    this.Â = false;
                    return;
                }
                if (value.equals("true")) {
                    this.Â = true;
                    return;
                }
            }
            this.Â = Boolean.parseBoolean(value);
            this.Ý = (this.Â ? 1 : 0);
            try {
                this.Ý = Integer.parseInt(value);
            }
            catch (NumberFormatException ex) {}
            try {
                this.Ø­áŒŠá = Double.parseDouble(value);
            }
            catch (NumberFormatException ex2) {}
        }
        
        public String HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public boolean Â() {
            return this.Â;
        }
        
        public int Ý() {
            return this.Ý;
        }
        
        public Â Ø­áŒŠá() {
            return this.Âµá€;
        }
    }
}
