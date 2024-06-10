/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class CompactArrayList
/*   6:    */ {
/*   7:    */   private ArrayList list;
/*   8:    */   private int initialCapacity;
/*   9:    */   private float loadFactor;
/*  10:    */   private int countValid;
/*  11:    */   
/*  12:    */   public CompactArrayList()
/*  13:    */   {
/*  14: 14 */     this(10, 0.75F);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public CompactArrayList(int initialCapacity)
/*  18:    */   {
/*  19: 19 */     this(initialCapacity, 0.75F);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public CompactArrayList(int initialCapacity, float loadFactor)
/*  23:    */   {
/*  24: 24 */     this.list = null;
/*  25: 25 */     this.initialCapacity = 0;
/*  26: 26 */     this.loadFactor = 1.0F;
/*  27: 27 */     this.countValid = 0;
/*  28: 28 */     this.list = new ArrayList(initialCapacity);
/*  29: 29 */     this.initialCapacity = initialCapacity;
/*  30: 30 */     this.loadFactor = loadFactor;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void add(int index, Object element)
/*  34:    */   {
/*  35: 35 */     if (element != null) {
/*  36: 37 */       this.countValid += 1;
/*  37:    */     }
/*  38: 40 */     this.list.add(index, element);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean add(Object element)
/*  42:    */   {
/*  43: 45 */     if (element != null) {
/*  44: 47 */       this.countValid += 1;
/*  45:    */     }
/*  46: 50 */     return this.list.add(element);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object set(int index, Object element)
/*  50:    */   {
/*  51: 55 */     Object oldElement = this.list.set(index, element);
/*  52: 57 */     if (element != oldElement)
/*  53:    */     {
/*  54: 59 */       if (oldElement == null) {
/*  55: 61 */         this.countValid += 1;
/*  56:    */       }
/*  57: 64 */       if (element == null) {
/*  58: 66 */         this.countValid -= 1;
/*  59:    */       }
/*  60:    */     }
/*  61: 70 */     return oldElement;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object remove(int index)
/*  65:    */   {
/*  66: 75 */     Object oldElement = this.list.remove(index);
/*  67: 77 */     if (oldElement != null) {
/*  68: 79 */       this.countValid -= 1;
/*  69:    */     }
/*  70: 82 */     return oldElement;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void clear()
/*  74:    */   {
/*  75: 87 */     this.list.clear();
/*  76: 88 */     this.countValid = 0;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void compact()
/*  80:    */   {
/*  81: 93 */     if ((this.countValid <= 0) && (this.list.size() <= 0))
/*  82:    */     {
/*  83: 95 */       clear();
/*  84:    */     }
/*  85: 97 */     else if (this.list.size() > this.initialCapacity)
/*  86:    */     {
/*  87: 99 */       float currentLoadFactor = this.countValid * 1.0F / this.list.size();
/*  88:101 */       if (currentLoadFactor <= this.loadFactor)
/*  89:    */       {
/*  90:103 */         int dstIndex = 0;
/*  91:106 */         for (int i = 0; i < this.list.size(); i++)
/*  92:    */         {
/*  93:108 */           Object wr = this.list.get(i);
/*  94:110 */           if (wr != null)
/*  95:    */           {
/*  96:112 */             if (i != dstIndex) {
/*  97:114 */               this.list.set(dstIndex, wr);
/*  98:    */             }
/*  99:117 */             dstIndex++;
/* 100:    */           }
/* 101:    */         }
/* 102:121 */         for (i = this.list.size() - 1; i >= dstIndex; i--) {
/* 103:123 */           this.list.remove(i);
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean contains(Object elem)
/* 110:    */   {
/* 111:131 */     return this.list.contains(elem);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Object get(int index)
/* 115:    */   {
/* 116:136 */     return this.list.get(index);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isEmpty()
/* 120:    */   {
/* 121:141 */     return this.list.isEmpty();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int size()
/* 125:    */   {
/* 126:146 */     return this.list.size();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getCountValid()
/* 130:    */   {
/* 131:151 */     return this.countValid;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.CompactArrayList
 * JD-Core Version:    0.7.0.1
 */