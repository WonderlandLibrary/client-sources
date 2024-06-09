/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SoundHandler
/*     */   implements IResourceManagerReloadListener, ITickable
/*     */ {
/*  31 */   private static final Logger logger = LogManager.getLogger();
/*  32 */   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
/*  33 */   private static final ParameterizedType TYPE = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  37 */         return new Type[] { String.class, SoundList.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  41 */         return Map.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  45 */         return null;
/*     */       }
/*     */     };
/*  48 */   public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0D, 0.0D, false);
/*  49 */   private final SoundRegistry sndRegistry = new SoundRegistry();
/*     */   
/*     */   private final SoundManager sndManager;
/*     */   private final IResourceManager mcResourceManager;
/*     */   
/*     */   public SoundHandler(IResourceManager manager, GameSettings gameSettingsIn) {
/*  55 */     this.mcResourceManager = manager;
/*  56 */     this.sndManager = new SoundManager(this, gameSettingsIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  61 */     this.sndManager.reloadSoundSystem();
/*  62 */     this.sndRegistry.clearMap();
/*     */     
/*  64 */     for (String s : resourceManager.getResourceDomains()) {
/*     */ 
/*     */       
/*     */       try {
/*  68 */         for (IResource iresource : resourceManager.getAllResources(new ResourceLocation(s, "sounds.json"))) {
/*     */           
/*     */           try
/*     */           {
/*  72 */             Map<String, SoundList> map = getSoundMap(iresource.getInputStream());
/*     */             
/*  74 */             for (Map.Entry<String, SoundList> entry : map.entrySet())
/*     */             {
/*  76 */               loadSoundResource(new ResourceLocation(s, entry.getKey()), entry.getValue());
/*     */             }
/*     */           }
/*  79 */           catch (RuntimeException runtimeexception)
/*     */           {
/*  81 */             logger.warn("Invalid sounds.json", runtimeexception);
/*     */           }
/*     */         
/*     */         } 
/*  85 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, SoundList> getSoundMap(InputStream stream) {
/*     */     Map<String, SoundList> map;
/*     */     try {
/*  98 */       map = (Map)GSON.fromJson(new InputStreamReader(stream), TYPE);
/*     */     }
/*     */     finally {
/*     */       
/* 102 */       IOUtils.closeQuietly(stream);
/*     */     } 
/*     */     
/* 105 */     return map;
/*     */   }
/*     */   
/*     */   private void loadSoundResource(ResourceLocation location, SoundList sounds) {
/*     */     SoundEventAccessorComposite soundeventaccessorcomposite;
/* 110 */     boolean flag = !this.sndRegistry.containsKey(location);
/*     */ 
/*     */     
/* 113 */     if (!flag && !sounds.canReplaceExisting()) {
/*     */       
/* 115 */       soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
/*     */     }
/*     */     else {
/*     */       
/* 119 */       if (!flag)
/*     */       {
/* 121 */         logger.debug("Replaced sound event location {}", new Object[] { location });
/*     */       }
/*     */       
/* 124 */       soundeventaccessorcomposite = new SoundEventAccessorComposite(location, 1.0D, 1.0D, sounds.getSoundCategory());
/* 125 */       this.sndRegistry.registerSound(soundeventaccessorcomposite);
/*     */     } 
/*     */     
/* 128 */     for (SoundList.SoundEntry soundlist$soundentry : sounds.getSoundList()) {
/*     */       Object<SoundPoolEntry> lvt_10_1_; ResourceLocation resourcelocation1; InputStream inputstream;
/* 130 */       String s = soundlist$soundentry.getSoundEntryName();
/* 131 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 132 */       String s1 = s.contains(":") ? resourcelocation.getResourceDomain() : location.getResourceDomain();
/*     */ 
/*     */       
/* 135 */       switch (soundlist$soundentry.getSoundEntryType()) {
/*     */         
/*     */         case null:
/* 138 */           resourcelocation1 = new ResourceLocation(s1, "sounds/" + resourcelocation.getResourcePath() + ".ogg");
/* 139 */           inputstream = null;
/*     */ 
/*     */           
/*     */           try {
/* 143 */             inputstream = this.mcResourceManager.getResource(resourcelocation1).getInputStream();
/*     */           }
/* 145 */           catch (FileNotFoundException var18) {
/*     */             
/* 147 */             logger.warn("File {} does not exist, cannot add it to event {}", new Object[] { resourcelocation1, location });
/*     */             
/*     */             continue;
/* 150 */           } catch (IOException ioexception) {
/*     */             
/* 152 */             logger.warn("Could not load sound file " + resourcelocation1 + ", cannot add it to event " + location, ioexception);
/*     */ 
/*     */             
/*     */             continue;
/*     */           } finally {
/* 157 */             IOUtils.closeQuietly(inputstream);
/*     */           } 
/*     */           
/* 160 */           lvt_10_1_ = (Object<SoundPoolEntry>)new SoundEventAccessor(new SoundPoolEntry(resourcelocation1, soundlist$soundentry.getSoundEntryPitch(), soundlist$soundentry.getSoundEntryVolume(), soundlist$soundentry.isStreaming()), soundlist$soundentry.getSoundEntryWeight());
/*     */           break;
/*     */         
/*     */         case SOUND_EVENT:
/* 164 */           lvt_10_1_ = (Object<SoundPoolEntry>)new ISoundEventAccessor<SoundPoolEntry>(s1, soundlist$soundentry)
/*     */             {
/*     */               final ResourceLocation field_148726_a;
/*     */               
/*     */               public int getWeight() {
/* 169 */                 SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
/* 170 */                 return (soundeventaccessorcomposite1 == null) ? 0 : soundeventaccessorcomposite1.getWeight();
/*     */               }
/*     */               
/*     */               public SoundPoolEntry cloneEntry() {
/* 174 */                 SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
/* 175 */                 return (soundeventaccessorcomposite1 == null) ? SoundHandler.missing_sound : soundeventaccessorcomposite1.cloneEntry();
/*     */               }
/*     */             };
/*     */           break;
/*     */         
/*     */         default:
/* 181 */           throw new IllegalStateException("IN YOU FACE");
/*     */       } 
/*     */       
/* 184 */       soundeventaccessorcomposite.addSoundToEventPool((ISoundEventAccessor<SoundPoolEntry>)lvt_10_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEventAccessorComposite getSound(ResourceLocation location) {
/* 190 */     return (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(ISound sound) {
/* 198 */     this.sndManager.playSound(sound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDelayedSound(ISound sound, int delay) {
/* 206 */     this.sndManager.playDelayedSound(sound, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(EntityPlayer player, float p_147691_2_) {
/* 211 */     this.sndManager.setListener(player, p_147691_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pauseSounds() {
/* 216 */     this.sndManager.pauseAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSounds() {
/* 221 */     this.sndManager.stopAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloadSounds() {
/* 226 */     this.sndManager.unloadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 234 */     this.sndManager.updateAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resumeSounds() {
/* 239 */     this.sndManager.resumeAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSoundLevel(SoundCategory category, float volume) {
/* 244 */     if (category == SoundCategory.MASTER && volume <= 0.0F)
/*     */     {
/* 246 */       stopSounds();
/*     */     }
/*     */     
/* 249 */     this.sndManager.setSoundCategoryVolume(category, volume);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSound(ISound p_147683_1_) {
/* 254 */     this.sndManager.stopSound(p_147683_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory... categories) {
/* 262 */     List<SoundEventAccessorComposite> list = Lists.newArrayList();
/*     */     
/* 264 */     for (ResourceLocation resourcelocation : this.sndRegistry.getKeys()) {
/*     */       
/* 266 */       SoundEventAccessorComposite soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(resourcelocation);
/*     */       
/* 268 */       if (ArrayUtils.contains((Object[])categories, soundeventaccessorcomposite.getSoundCategory()))
/*     */       {
/* 270 */         list.add(soundeventaccessorcomposite);
/*     */       }
/*     */     } 
/*     */     
/* 274 */     if (list.isEmpty())
/*     */     {
/* 276 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 280 */     return list.get((new Random()).nextInt(list.size()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSoundPlaying(ISound sound) {
/* 286 */     return this.sndManager.isSoundPlaying(sound);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\audio\SoundHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */