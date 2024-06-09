/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class StateMap
/*    */   extends StateMapperBase
/*    */ {
/*    */   private final IProperty<?> name;
/*    */   private final String suffix;
/*    */   private final List<IProperty<?>> ignored;
/*    */   
/*    */   private StateMap(IProperty<?> name, String suffix, List<IProperty<?>> ignored) {
/* 22 */     this.name = name;
/* 23 */     this.suffix = suffix;
/* 24 */     this.ignored = ignored;
/*    */   }
/*    */   
/*    */   protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
/*    */     String s;
/* 29 */     Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*    */ 
/*    */     
/* 32 */     if (this.name == null) {
/*    */       
/* 34 */       s = ((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock())).toString();
/*    */     }
/*    */     else {
/*    */       
/* 38 */       s = this.name.getName(map.remove(this.name));
/*    */     } 
/*    */     
/* 41 */     if (this.suffix != null)
/*    */     {
/* 43 */       s = String.valueOf(s) + this.suffix;
/*    */     }
/*    */     
/* 46 */     for (IProperty<?> iproperty : this.ignored)
/*    */     {
/* 48 */       map.remove(iproperty);
/*    */     }
/*    */     
/* 51 */     return new ModelResourceLocation(s, getPropertyString(map));
/*    */   }
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     private IProperty<?> name;
/*    */     private String suffix;
/* 58 */     private final List<IProperty<?>> ignored = Lists.newArrayList();
/*    */ 
/*    */     
/*    */     public Builder withName(IProperty<?> builderPropertyIn) {
/* 62 */       this.name = builderPropertyIn;
/* 63 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder withSuffix(String builderSuffixIn) {
/* 68 */       this.suffix = builderSuffixIn;
/* 69 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder ignore(IProperty... p_178442_1_) {
/* 74 */       Collections.addAll(this.ignored, (IProperty<?>[])p_178442_1_);
/* 75 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public StateMap build() {
/* 80 */       return new StateMap(this.name, this.suffix, this.ignored, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\block\statemap\StateMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */