package net.minecraft.world;

import java.util.Set;
import java.util.TreeMap;
import net.a6X;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameRules$ValueType;

public class GameRules {
   private TreeMap theGameRules = new TreeMap();
   private static final String a = "CL_00000136";

   public GameRules() {
      this.addGameRule("doFireTick", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("mobGriefing", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("keepInventory", "false", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("doMobSpawning", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("doMobLoot", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("doTileDrops", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("doEntityDrops", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("commandBlockOutput", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("naturalRegeneration", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("doDaylightCycle", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("logAdminCommands", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("showDeathMessages", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("randomTickSpeed", "3", GameRules$ValueType.NUMERICAL_VALUE);
      this.addGameRule("sendCommandFeedback", "true", GameRules$ValueType.BOOLEAN_VALUE);
      this.addGameRule("reducedDebugInfo", "false", GameRules$ValueType.BOOLEAN_VALUE);
   }

   public void addGameRule(String var1, String var2, GameRules$ValueType var3) {
      this.theGameRules.put(var1, new a6X(var2, var3));
   }

   public void setOrCreateGameRule(String var1, String var2) {
      a6X var3 = (a6X)this.theGameRules.get(var1);
      var3.a(var2);
   }

   public String a(String var1) {
      a6X var2 = (a6X)this.theGameRules.get(var1);
      return var2.b();
   }

   public boolean getBoolean(String var1) {
      a6X var2 = (a6X)this.theGameRules.get(var1);
      return var2.a();
   }

   public int getInt(String var1) {
      a6X var2 = (a6X)this.theGameRules.get(var1);
      return var2.c();
   }

   public NBTTagCompound writeToNBT() {
      NBTTagCompound var1 = new NBTTagCompound();

      for(Object var3 : this.theGameRules.keySet()) {
         a6X var4 = (a6X)this.theGameRules.get(var3);
         var1.setString((String)var3, var4.b());
      }

      return var1;
   }

   public void readFromNBT(NBTTagCompound var1) {
      for(String var3 : var1.getKeySet()) {
         String var4 = var1.getString(var3);
         this.setOrCreateGameRule(var3, var4);
      }

   }

   public String[] getRules() {
      Set var1 = this.theGameRules.keySet();
      return (String[])((String[])((String[])var1.toArray(new String[var1.size()])));
   }

   public boolean hasRule(String var1) {
      return this.theGameRules.containsKey(var1);
   }

   public boolean areSameType(String var1, GameRules$ValueType var2) {
      a6X var3 = (a6X)this.theGameRules.get(var1);
      return var3.d() == var2 || var2 == GameRules$ValueType.ANY_VALUE;
   }
}
