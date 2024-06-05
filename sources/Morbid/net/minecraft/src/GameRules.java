package net.minecraft.src;

import java.util.*;

public class GameRules
{
    private TreeMap theGameRules;
    
    public GameRules() {
        this.theGameRules = new TreeMap();
        this.addGameRule("doFireTick", "true");
        this.addGameRule("mobGriefing", "true");
        this.addGameRule("keepInventory", "false");
        this.addGameRule("doMobSpawning", "true");
        this.addGameRule("doMobLoot", "true");
        this.addGameRule("doTileDrops", "true");
        this.addGameRule("commandBlockOutput", "true");
    }
    
    public void addGameRule(final String par1Str, final String par2Str) {
        this.theGameRules.put(par1Str, new GameRuleValue(par2Str));
    }
    
    public void setOrCreateGameRule(final String par1Str, final String par2Str) {
        final GameRuleValue var3 = this.theGameRules.get(par1Str);
        if (var3 != null) {
            var3.setValue(par2Str);
        }
        else {
            this.addGameRule(par1Str, par2Str);
        }
    }
    
    public String getGameRuleStringValue(final String par1Str) {
        final GameRuleValue var2 = this.theGameRules.get(par1Str);
        return (var2 != null) ? var2.getGameRuleStringValue() : "";
    }
    
    public boolean getGameRuleBooleanValue(final String par1Str) {
        final GameRuleValue var2 = this.theGameRules.get(par1Str);
        return var2 != null && var2.getGameRuleBooleanValue();
    }
    
    public NBTTagCompound writeGameRulesToNBT() {
        final NBTTagCompound var1 = new NBTTagCompound("GameRules");
        for (final String var3 : this.theGameRules.keySet()) {
            final GameRuleValue var4 = this.theGameRules.get(var3);
            var1.setString(var3, var4.getGameRuleStringValue());
        }
        return var1;
    }
    
    public void readGameRulesFromNBT(final NBTTagCompound par1NBTTagCompound) {
        final Collection var2 = par1NBTTagCompound.getTags();
        for (final NBTBase var4 : var2) {
            final String var5 = var4.getName();
            final String var6 = par1NBTTagCompound.getString(var4.getName());
            this.setOrCreateGameRule(var5, var6);
        }
    }
    
    public String[] getRules() {
        return (String[])this.theGameRules.keySet().toArray(new String[0]);
    }
    
    public boolean hasRule(final String par1Str) {
        return this.theGameRules.containsKey(par1Str);
    }
}
