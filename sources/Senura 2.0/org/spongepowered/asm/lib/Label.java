/*     */ package org.spongepowered.asm.lib;
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
/*     */ 
/*     */ public class Label
/*     */ {
/*     */   static final int DEBUG = 1;
/*     */   static final int RESOLVED = 2;
/*     */   static final int RESIZED = 4;
/*     */   static final int PUSHED = 8;
/*     */   static final int TARGET = 16;
/*     */   static final int STORE = 32;
/*     */   static final int REACHABLE = 64;
/*     */   static final int JSR = 128;
/*     */   static final int RET = 256;
/*     */   static final int SUBROUTINE = 512;
/*     */   static final int VISITED = 1024;
/*     */   static final int VISITED2 = 2048;
/*     */   public Object info;
/*     */   int status;
/*     */   int line;
/*     */   int position;
/*     */   private int referenceCount;
/*     */   private int[] srcAndRefPositions;
/*     */   int inputStackTop;
/*     */   int outputStackMax;
/*     */   Frame frame;
/*     */   Label successor;
/*     */   Edge successors;
/*     */   Label next;
/*     */   
/*     */   public int getOffset() {
/* 278 */     if ((this.status & 0x2) == 0) {
/* 279 */       throw new IllegalStateException("Label offset position has not been resolved yet");
/*     */     }
/*     */     
/* 282 */     return this.position;
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
/*     */   void put(MethodWriter owner, ByteVector out, int source, boolean wideOffset) {
/* 306 */     if ((this.status & 0x2) == 0) {
/* 307 */       if (wideOffset) {
/* 308 */         addReference(-1 - source, out.length);
/* 309 */         out.putInt(-1);
/*     */       } else {
/* 311 */         addReference(source, out.length);
/* 312 */         out.putShort(-1);
/*     */       }
/*     */     
/* 315 */     } else if (wideOffset) {
/* 316 */       out.putInt(this.position - source);
/*     */     } else {
/* 318 */       out.putShort(this.position - source);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addReference(int sourcePosition, int referencePosition) {
/* 338 */     if (this.srcAndRefPositions == null) {
/* 339 */       this.srcAndRefPositions = new int[6];
/*     */     }
/* 341 */     if (this.referenceCount >= this.srcAndRefPositions.length) {
/* 342 */       int[] a = new int[this.srcAndRefPositions.length + 6];
/* 343 */       System.arraycopy(this.srcAndRefPositions, 0, a, 0, this.srcAndRefPositions.length);
/*     */       
/* 345 */       this.srcAndRefPositions = a;
/*     */     } 
/* 347 */     this.srcAndRefPositions[this.referenceCount++] = sourcePosition;
/* 348 */     this.srcAndRefPositions[this.referenceCount++] = referencePosition;
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
/*     */   boolean resolve(MethodWriter owner, int position, byte[] data) {
/* 375 */     boolean needUpdate = false;
/* 376 */     this.status |= 0x2;
/* 377 */     this.position = position;
/* 378 */     int i = 0;
/* 379 */     while (i < this.referenceCount) {
/* 380 */       int source = this.srcAndRefPositions[i++];
/* 381 */       int reference = this.srcAndRefPositions[i++];
/*     */       
/* 383 */       if (source >= 0) {
/* 384 */         int j = position - source;
/* 385 */         if (j < -32768 || j > 32767) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 395 */           int opcode = data[reference - 1] & 0xFF;
/* 396 */           if (opcode <= 168) {
/*     */             
/* 398 */             data[reference - 1] = (byte)(opcode + 49);
/*     */           } else {
/*     */             
/* 401 */             data[reference - 1] = (byte)(opcode + 20);
/*     */           } 
/* 403 */           needUpdate = true;
/*     */         } 
/* 405 */         data[reference++] = (byte)(j >>> 8);
/* 406 */         data[reference] = (byte)j; continue;
/*     */       } 
/* 408 */       int offset = position + source + 1;
/* 409 */       data[reference++] = (byte)(offset >>> 24);
/* 410 */       data[reference++] = (byte)(offset >>> 16);
/* 411 */       data[reference++] = (byte)(offset >>> 8);
/* 412 */       data[reference] = (byte)offset;
/*     */     } 
/*     */     
/* 415 */     return needUpdate;
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
/*     */   Label getFirst() {
/* 427 */     return (this.frame == null) ? this : this.frame.owner;
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
/*     */ 
/*     */   
/*     */   boolean inSubroutine(long id) {
/* 442 */     if ((this.status & 0x400) != 0) {
/* 443 */       return ((this.srcAndRefPositions[(int)(id >>> 32L)] & (int)id) != 0);
/*     */     }
/* 445 */     return false;
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
/*     */   boolean inSameSubroutine(Label block) {
/* 458 */     if ((this.status & 0x400) == 0 || (block.status & 0x400) == 0) {
/* 459 */       return false;
/*     */     }
/* 461 */     for (int i = 0; i < this.srcAndRefPositions.length; i++) {
/* 462 */       if ((this.srcAndRefPositions[i] & block.srcAndRefPositions[i]) != 0) {
/* 463 */         return true;
/*     */       }
/*     */     } 
/* 466 */     return false;
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
/*     */   void addToSubroutine(long id, int nbSubroutines) {
/* 478 */     if ((this.status & 0x400) == 0) {
/* 479 */       this.status |= 0x400;
/* 480 */       this.srcAndRefPositions = new int[nbSubroutines / 32 + 1];
/*     */     } 
/* 482 */     this.srcAndRefPositions[(int)(id >>> 32L)] = this.srcAndRefPositions[(int)(id >>> 32L)] | (int)id;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void visitSubroutine(Label JSR, long id, int nbSubroutines) {
/* 503 */     Label stack = this;
/* 504 */     while (stack != null) {
/*     */       
/* 506 */       Label l = stack;
/* 507 */       stack = l.next;
/* 508 */       l.next = null;
/*     */       
/* 510 */       if (JSR != null) {
/* 511 */         if ((l.status & 0x800) != 0) {
/*     */           continue;
/*     */         }
/* 514 */         l.status |= 0x800;
/*     */         
/* 516 */         if ((l.status & 0x100) != 0 && 
/* 517 */           !l.inSameSubroutine(JSR)) {
/* 518 */           Edge edge = new Edge();
/* 519 */           edge.info = l.inputStackTop;
/* 520 */           edge.successor = JSR.successors.successor;
/* 521 */           edge.next = l.successors;
/* 522 */           l.successors = edge;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 527 */         if (l.inSubroutine(id)) {
/*     */           continue;
/*     */         }
/*     */         
/* 531 */         l.addToSubroutine(id, nbSubroutines);
/*     */       } 
/*     */       
/* 534 */       Edge e = l.successors;
/* 535 */       while (e != null) {
/*     */ 
/*     */ 
/*     */         
/* 539 */         if ((l.status & 0x80) == 0 || e != l.successors.next)
/*     */         {
/* 541 */           if (e.successor.next == null) {
/* 542 */             e.successor.next = stack;
/* 543 */             stack = e.successor;
/*     */           } 
/*     */         }
/* 546 */         e = e.next;
/*     */       } 
/*     */     } 
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
/*     */   
/*     */   public String toString() {
/* 562 */     return "L" + System.identityHashCode(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\Label.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */