/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultAttributeMap
/*     */   implements AttributeMap
/*     */ {
/*     */   private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater;
/*     */   private static final int BUCKET_SIZE = 4;
/*     */   private static final int MASK = 3;
/*     */   private volatile AtomicReferenceArray<DefaultAttribute<?>> attributes;
/*     */   
/*     */   static {
/*  35 */     AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> referenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(DefaultAttributeMap.class, "attributes");
/*     */     
/*  37 */     if (referenceFieldUpdater == null) {
/*  38 */       referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
/*     */     }
/*     */     
/*  41 */     updater = referenceFieldUpdater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Attribute<T> attr(AttributeKey<T> key) {
/*  54 */     if (key == null) {
/*  55 */       throw new NullPointerException("key");
/*     */     }
/*  57 */     AtomicReferenceArray<DefaultAttribute<?>> attributes = this.attributes;
/*  58 */     if (attributes == null) {
/*     */       
/*  60 */       attributes = new AtomicReferenceArray<DefaultAttribute<?>>(4);
/*     */       
/*  62 */       if (!updater.compareAndSet(this, null, attributes)) {
/*  63 */         attributes = this.attributes;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     int i = index(key);
/*  68 */     DefaultAttribute<?> head = attributes.get(i);
/*  69 */     if (head == null) {
/*     */ 
/*     */       
/*  72 */       head = new DefaultAttribute(key);
/*  73 */       if (attributes.compareAndSet(i, null, head))
/*     */       {
/*  75 */         return (Attribute)head;
/*     */       }
/*  77 */       head = attributes.get(i);
/*     */     } 
/*     */ 
/*     */     
/*  81 */     synchronized (head) {
/*  82 */       DefaultAttribute<?> curr = head;
/*     */       while (true) {
/*  84 */         if (!curr.removed && curr.key == key) {
/*  85 */           return (Attribute)curr;
/*     */         }
/*     */         
/*  88 */         DefaultAttribute<?> next = curr.next;
/*  89 */         if (next == null) {
/*  90 */           DefaultAttribute<T> attr = new DefaultAttribute<T>(head, key);
/*  91 */           curr.next = attr;
/*  92 */           attr.prev = curr;
/*  93 */           return attr;
/*     */         } 
/*  95 */         curr = next;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int index(AttributeKey<?> key) {
/* 102 */     return key.id() & 0x3;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class DefaultAttribute<T>
/*     */     extends AtomicReference<T>
/*     */     implements Attribute<T>
/*     */   {
/*     */     private static final long serialVersionUID = -2661411462200283011L;
/*     */     
/*     */     private final DefaultAttribute<?> head;
/*     */     
/*     */     private final AttributeKey<T> key;
/*     */     
/*     */     private DefaultAttribute<?> prev;
/*     */     
/*     */     private DefaultAttribute<?> next;
/*     */     private volatile boolean removed;
/*     */     
/*     */     DefaultAttribute(DefaultAttribute<?> head, AttributeKey<T> key) {
/* 122 */       this.head = head;
/* 123 */       this.key = key;
/*     */     }
/*     */     
/*     */     DefaultAttribute(AttributeKey<T> key) {
/* 127 */       this.head = this;
/* 128 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public AttributeKey<T> key() {
/* 133 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T setIfAbsent(T value) {
/* 138 */       while (!compareAndSet(null, value)) {
/* 139 */         T old = get();
/* 140 */         if (old != null) {
/* 141 */           return old;
/*     */         }
/*     */       } 
/* 144 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public T getAndRemove() {
/* 149 */       this.removed = true;
/* 150 */       T oldValue = getAndSet(null);
/* 151 */       remove0();
/* 152 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 157 */       this.removed = true;
/* 158 */       set(null);
/* 159 */       remove0();
/*     */     }
/*     */     
/*     */     private void remove0() {
/* 163 */       synchronized (this.head) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 169 */         if (this.prev != null) {
/* 170 */           this.prev.next = this.next;
/*     */           
/* 172 */           if (this.next != null)
/* 173 */             this.next.prev = this.prev; 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\DefaultAttributeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */