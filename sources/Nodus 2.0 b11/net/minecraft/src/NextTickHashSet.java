/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.AbstractSet;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import net.minecraft.util.LongHashMap;
/*   8:    */ import net.minecraft.world.ChunkCoordIntPair;
/*   9:    */ import net.minecraft.world.NextTickListEntry;
/*  10:    */ 
/*  11:    */ public class NextTickHashSet
/*  12:    */   extends AbstractSet
/*  13:    */ {
/*  14: 14 */   private LongHashMap longHashMap = new LongHashMap();
/*  15: 15 */   private int size = 0;
/*  16: 16 */   private HashSet emptySet = new HashSet();
/*  17:    */   
/*  18:    */   public NextTickHashSet(Set oldSet)
/*  19:    */   {
/*  20: 20 */     addAll(oldSet);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int size()
/*  24:    */   {
/*  25: 25 */     return this.size;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean contains(Object obj)
/*  29:    */   {
/*  30: 31 */     if (!(obj instanceof NextTickListEntry)) {
/*  31: 33 */       return false;
/*  32:    */     }
/*  33: 37 */     NextTickListEntry entry = (NextTickListEntry)obj;
/*  34: 39 */     if (entry == null) {
/*  35: 41 */       return false;
/*  36:    */     }
/*  37: 45 */     long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
/*  38: 46 */     HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
/*  39: 47 */     return set == null ? false : set.contains(entry);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean add(Object obj)
/*  43:    */   {
/*  44: 55 */     if (!(obj instanceof NextTickListEntry)) {
/*  45: 57 */       return false;
/*  46:    */     }
/*  47: 61 */     NextTickListEntry entry = (NextTickListEntry)obj;
/*  48: 63 */     if (entry == null) {
/*  49: 65 */       return false;
/*  50:    */     }
/*  51: 69 */     long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
/*  52: 70 */     HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
/*  53: 72 */     if (set == null)
/*  54:    */     {
/*  55: 74 */       set = new HashSet();
/*  56: 75 */       this.longHashMap.add(key, set);
/*  57:    */     }
/*  58: 78 */     boolean added = set.add(entry);
/*  59: 80 */     if (added) {
/*  60: 82 */       this.size += 1;
/*  61:    */     }
/*  62: 85 */     return added;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean remove(Object obj)
/*  66:    */   {
/*  67: 93 */     if (!(obj instanceof NextTickListEntry)) {
/*  68: 95 */       return false;
/*  69:    */     }
/*  70: 99 */     NextTickListEntry entry = (NextTickListEntry)obj;
/*  71:101 */     if (entry == null) {
/*  72:103 */       return false;
/*  73:    */     }
/*  74:107 */     long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
/*  75:108 */     HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
/*  76:110 */     if (set == null) {
/*  77:112 */       return false;
/*  78:    */     }
/*  79:116 */     boolean removed = set.remove(entry);
/*  80:118 */     if (removed) {
/*  81:120 */       this.size -= 1;
/*  82:    */     }
/*  83:123 */     return removed;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Iterator getNextTickEntries(int chunkX, int chunkZ)
/*  87:    */   {
/*  88:131 */     HashSet set = getNextTickEntriesSet(chunkX, chunkZ);
/*  89:132 */     return set.iterator();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public HashSet getNextTickEntriesSet(int chunkX, int chunkZ)
/*  93:    */   {
/*  94:137 */     long key = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
/*  95:138 */     HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
/*  96:140 */     if (set == null) {
/*  97:142 */       set = this.emptySet;
/*  98:    */     }
/*  99:145 */     return set;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Iterator iterator()
/* 103:    */   {
/* 104:150 */     throw new UnsupportedOperationException("Not implemented");
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.NextTickHashSet
 * JD-Core Version:    0.7.0.1
 */