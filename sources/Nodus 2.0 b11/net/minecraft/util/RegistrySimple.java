/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Maps;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.Map;
/*  6:   */ import java.util.Set;
/*  7:   */ import org.apache.logging.log4j.LogManager;
/*  8:   */ import org.apache.logging.log4j.Logger;
/*  9:   */ 
/* 10:   */ public class RegistrySimple
/* 11:   */   implements IRegistry
/* 12:   */ {
/* 13:12 */   private static final Logger logger = ;
/* 14:15 */   protected final Map registryObjects = createUnderlyingMap();
/* 15:   */   private static final String __OBFID = "CL_00001210";
/* 16:   */   
/* 17:   */   protected Map createUnderlyingMap()
/* 18:   */   {
/* 19:23 */     return Maps.newHashMap();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object getObject(Object par1Obj)
/* 23:   */   {
/* 24:28 */     return this.registryObjects.get(par1Obj);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void putObject(Object par1Obj, Object par2Obj)
/* 28:   */   {
/* 29:36 */     if (this.registryObjects.containsKey(par1Obj)) {
/* 30:38 */       logger.warn("Adding duplicate key '" + par1Obj + "' to registry");
/* 31:   */     }
/* 32:41 */     this.registryObjects.put(par1Obj, par2Obj);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Set getKeys()
/* 36:   */   {
/* 37:49 */     return Collections.unmodifiableSet(this.registryObjects.keySet());
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean containsKey(Object p_148741_1_)
/* 41:   */   {
/* 42:57 */     return this.registryObjects.containsKey(p_148741_1_);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.RegistrySimple
 * JD-Core Version:    0.7.0.1
 */