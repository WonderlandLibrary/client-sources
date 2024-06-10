/*  1:   */ package net.minecraft.server.management;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.LinkedHashMap;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Map.Entry;
/*  8:   */ import java.util.Set;
/*  9:   */ 
/* 10:   */ public class LowerStringMap
/* 11:   */   implements Map
/* 12:   */ {
/* 13:12 */   private final Map internalMap = new LinkedHashMap();
/* 14:   */   private static final String __OBFID = "CL_00001488";
/* 15:   */   
/* 16:   */   public int size()
/* 17:   */   {
/* 18:17 */     return this.internalMap.size();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isEmpty()
/* 22:   */   {
/* 23:22 */     return this.internalMap.isEmpty();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean containsKey(Object par1Obj)
/* 27:   */   {
/* 28:27 */     return this.internalMap.containsKey(par1Obj.toString().toLowerCase());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean containsValue(Object par1Obj)
/* 32:   */   {
/* 33:32 */     return this.internalMap.containsKey(par1Obj);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Object get(Object par1Obj)
/* 37:   */   {
/* 38:37 */     return this.internalMap.get(par1Obj.toString().toLowerCase());
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Object put(String par1Str, Object par2Obj)
/* 42:   */   {
/* 43:42 */     return this.internalMap.put(par1Str.toLowerCase(), par2Obj);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Object remove(Object par1Obj)
/* 47:   */   {
/* 48:47 */     return this.internalMap.remove(par1Obj.toString().toLowerCase());
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void putAll(Map par1Map)
/* 52:   */   {
/* 53:52 */     Iterator var2 = par1Map.entrySet().iterator();
/* 54:54 */     while (var2.hasNext())
/* 55:   */     {
/* 56:56 */       Map.Entry var3 = (Map.Entry)var2.next();
/* 57:57 */       put((String)var3.getKey(), var3.getValue());
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void clear()
/* 62:   */   {
/* 63:63 */     this.internalMap.clear();
/* 64:   */   }
/* 65:   */   
/* 66:   */   public Set keySet()
/* 67:   */   {
/* 68:68 */     return this.internalMap.keySet();
/* 69:   */   }
/* 70:   */   
/* 71:   */   public Collection values()
/* 72:   */   {
/* 73:73 */     return this.internalMap.values();
/* 74:   */   }
/* 75:   */   
/* 76:   */   public Set entrySet()
/* 77:   */   {
/* 78:78 */     return this.internalMap.entrySet();
/* 79:   */   }
/* 80:   */   
/* 81:   */   public Object put(Object par1Obj, Object par2Obj)
/* 82:   */   {
/* 83:83 */     return put((String)par1Obj, par2Obj);
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.management.LowerStringMap
 * JD-Core Version:    0.7.0.1
 */