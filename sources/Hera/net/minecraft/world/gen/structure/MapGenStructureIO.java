/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class MapGenStructureIO
/*     */ {
/*  12 */   private static final Logger logger = LogManager.getLogger();
/*  13 */   private static Map<String, Class<? extends StructureStart>> startNameToClassMap = Maps.newHashMap();
/*  14 */   private static Map<Class<? extends StructureStart>, String> startClassToNameMap = Maps.newHashMap();
/*  15 */   private static Map<String, Class<? extends StructureComponent>> componentNameToClassMap = Maps.newHashMap();
/*  16 */   private static Map<Class<? extends StructureComponent>, String> componentClassToNameMap = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private static void registerStructure(Class<? extends StructureStart> startClass, String structureName) {
/*  20 */     startNameToClassMap.put(structureName, startClass);
/*  21 */     startClassToNameMap.put(startClass, structureName);
/*     */   }
/*     */ 
/*     */   
/*     */   static void registerStructureComponent(Class<? extends StructureComponent> componentClass, String componentName) {
/*  26 */     componentNameToClassMap.put(componentName, componentClass);
/*  27 */     componentClassToNameMap.put(componentClass, componentName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getStructureStartName(StructureStart start) {
/*  32 */     return startClassToNameMap.get(start.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getStructureComponentName(StructureComponent component) {
/*  37 */     return componentClassToNameMap.get(component.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureStart getStructureStart(NBTTagCompound tagCompound, World worldIn) {
/*  42 */     StructureStart structurestart = null;
/*     */ 
/*     */     
/*     */     try {
/*  46 */       Class<? extends StructureStart> oclass = startNameToClassMap.get(tagCompound.getString("id"));
/*     */       
/*  48 */       if (oclass != null)
/*     */       {
/*  50 */         structurestart = oclass.newInstance();
/*     */       }
/*     */     }
/*  53 */     catch (Exception exception) {
/*     */       
/*  55 */       logger.warn("Failed Start with id " + tagCompound.getString("id"));
/*  56 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  59 */     if (structurestart != null) {
/*     */       
/*  61 */       structurestart.readStructureComponentsFromNBT(worldIn, tagCompound);
/*     */     }
/*     */     else {
/*     */       
/*  65 */       logger.warn("Skipping Structure with id " + tagCompound.getString("id"));
/*     */     } 
/*     */     
/*  68 */     return structurestart;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureComponent getStructureComponent(NBTTagCompound tagCompound, World worldIn) {
/*  73 */     StructureComponent structurecomponent = null;
/*     */ 
/*     */     
/*     */     try {
/*  77 */       Class<? extends StructureComponent> oclass = componentNameToClassMap.get(tagCompound.getString("id"));
/*     */       
/*  79 */       if (oclass != null)
/*     */       {
/*  81 */         structurecomponent = oclass.newInstance();
/*     */       }
/*     */     }
/*  84 */     catch (Exception exception) {
/*     */       
/*  86 */       logger.warn("Failed Piece with id " + tagCompound.getString("id"));
/*  87 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  90 */     if (structurecomponent != null) {
/*     */       
/*  92 */       structurecomponent.readStructureBaseNBT(worldIn, tagCompound);
/*     */     }
/*     */     else {
/*     */       
/*  96 */       logger.warn("Skipping Piece with id " + tagCompound.getString("id"));
/*     */     } 
/*     */     
/*  99 */     return structurecomponent;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 104 */     registerStructure((Class)StructureMineshaftStart.class, "Mineshaft");
/* 105 */     registerStructure((Class)MapGenVillage.Start.class, "Village");
/* 106 */     registerStructure((Class)MapGenNetherBridge.Start.class, "Fortress");
/* 107 */     registerStructure((Class)MapGenStronghold.Start.class, "Stronghold");
/* 108 */     registerStructure((Class)MapGenScatteredFeature.Start.class, "Temple");
/* 109 */     registerStructure((Class)StructureOceanMonument.StartMonument.class, "Monument");
/* 110 */     StructureMineshaftPieces.registerStructurePieces();
/* 111 */     StructureVillagePieces.registerVillagePieces();
/* 112 */     StructureNetherBridgePieces.registerNetherFortressPieces();
/* 113 */     StructureStrongholdPieces.registerStrongholdPieces();
/* 114 */     ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
/* 115 */     StructureOceanMonumentPieces.registerOceanMonumentPieces();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\gen\structure\MapGenStructureIO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */