/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Unpooled
/*     */ {
/*  79 */   private static final ByteBufAllocator ALLOC = UnpooledByteBufAllocator.DEFAULT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final ByteBuf EMPTY_BUFFER = ALLOC.buffer(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer() {
/* 101 */     return ALLOC.heapBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer() {
/* 109 */     return ALLOC.directBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer(int initialCapacity) {
/* 118 */     return ALLOC.heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer(int initialCapacity) {
/* 127 */     return ALLOC.directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer(int initialCapacity, int maxCapacity) {
/* 137 */     return ALLOC.heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
/* 147 */     return ALLOC.directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[] array) {
/* 156 */     if (array.length == 0) {
/* 157 */       return EMPTY_BUFFER;
/*     */     }
/* 159 */     return new UnpooledHeapByteBuf(ALLOC, array, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[] array, int offset, int length) {
/* 168 */     if (length == 0) {
/* 169 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 172 */     if (offset == 0 && length == array.length) {
/* 173 */       return wrappedBuffer(array);
/*     */     }
/*     */     
/* 176 */     return wrappedBuffer(array).slice(offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer buffer) {
/* 185 */     if (!buffer.hasRemaining()) {
/* 186 */       return EMPTY_BUFFER;
/*     */     }
/* 188 */     if (buffer.hasArray()) {
/* 189 */       return wrappedBuffer(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining()).order(buffer.order());
/*     */     }
/*     */ 
/*     */     
/* 193 */     if (PlatformDependent.hasUnsafe()) {
/* 194 */       if (buffer.isReadOnly()) {
/* 195 */         if (buffer.isDirect()) {
/* 196 */           return new ReadOnlyUnsafeDirectByteBuf(ALLOC, buffer);
/*     */         }
/* 198 */         return new ReadOnlyByteBufferBuf(ALLOC, buffer);
/*     */       } 
/*     */       
/* 201 */       return new UnpooledUnsafeDirectByteBuf(ALLOC, buffer, buffer.remaining());
/*     */     } 
/*     */     
/* 204 */     if (buffer.isReadOnly()) {
/* 205 */       return new ReadOnlyByteBufferBuf(ALLOC, buffer);
/*     */     }
/* 207 */     return new UnpooledDirectByteBuf(ALLOC, buffer, buffer.remaining());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuf buffer) {
/* 218 */     if (buffer.isReadable()) {
/* 219 */       return buffer.slice();
/*     */     }
/* 221 */     return EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[]... arrays) {
/* 231 */     return wrappedBuffer(16, arrays);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuf... buffers) {
/* 240 */     return wrappedBuffer(16, buffers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer... buffers) {
/* 249 */     return wrappedBuffer(16, buffers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(int maxNumComponents, byte[]... arrays) {
/* 258 */     switch (arrays.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 283 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         if ((arrays[0]).length != 0)
/*     */           return wrappedBuffer(arrays[0]); 
/*     */     }  List<ByteBuf> components = new ArrayList<ByteBuf>(arrays.length); for (byte[] a : arrays) {
/*     */       if (a == null)
/*     */         break;  if (a.length > 0)
/*     */         components.add(wrappedBuffer(a)); 
/*     */     }  if (!components.isEmpty())
/* 292 */       return new CompositeByteBuf(ALLOC, false, maxNumComponents, components);  } public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuf... buffers) { switch (buffers.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 307 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         if (buffers[0].isReadable())
/*     */           return wrappedBuffer(buffers[0].order(BIG_ENDIAN)); 
/*     */     } 
/*     */     for (ByteBuf b : buffers) {
/*     */       if (b.isReadable())
/*     */         return new CompositeByteBuf(ALLOC, false, maxNumComponents, buffers); 
/*     */     }  }
/* 316 */   public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuffer... buffers) { switch (buffers.length)
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 341 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         if (buffers[0].hasRemaining())
/*     */           return wrappedBuffer(buffers[0].order(BIG_ENDIAN));  }  List<ByteBuf> components = new ArrayList<ByteBuf>(buffers.length); for (ByteBuffer b : buffers) { if (b == null)
/*     */         break;  if (b.remaining() > 0)
/*     */         components.add(wrappedBuffer(b.order(BIG_ENDIAN)));  }
/*     */      if (!components.isEmpty())
/* 348 */       return new CompositeByteBuf(ALLOC, false, maxNumComponents, components);  } public static CompositeByteBuf compositeBuffer() { return compositeBuffer(16); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompositeByteBuf compositeBuffer(int maxNumComponents) {
/* 355 */     return new CompositeByteBuf(ALLOC, false, maxNumComponents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[] array) {
/* 364 */     if (array.length == 0) {
/* 365 */       return EMPTY_BUFFER;
/*     */     }
/* 367 */     return wrappedBuffer((byte[])array.clone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[] array, int offset, int length) {
/* 377 */     if (length == 0) {
/* 378 */       return EMPTY_BUFFER;
/*     */     }
/* 380 */     byte[] copy = new byte[length];
/* 381 */     System.arraycopy(array, offset, copy, 0, length);
/* 382 */     return wrappedBuffer(copy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(ByteBuffer buffer) {
/* 392 */     int length = buffer.remaining();
/* 393 */     if (length == 0) {
/* 394 */       return EMPTY_BUFFER;
/*     */     }
/* 396 */     byte[] copy = new byte[length];
/* 397 */     int position = buffer.position();
/*     */     try {
/* 399 */       buffer.get(copy);
/*     */     } finally {
/* 401 */       buffer.position(position);
/*     */     } 
/* 403 */     return wrappedBuffer(copy).order(buffer.order());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(ByteBuf buffer) {
/* 413 */     int readable = buffer.readableBytes();
/* 414 */     if (readable > 0) {
/* 415 */       ByteBuf copy = buffer(readable);
/* 416 */       copy.writeBytes(buffer, buffer.readerIndex(), readable);
/* 417 */       return copy;
/*     */     } 
/* 419 */     return EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[]... arrays) {
/* 430 */     switch (arrays.length) {
/*     */       case 0:
/* 432 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 434 */         if ((arrays[0]).length == 0) {
/* 435 */           return EMPTY_BUFFER;
/*     */         }
/* 437 */         return copiedBuffer(arrays[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 442 */     int length = 0;
/* 443 */     for (byte[] a : arrays) {
/* 444 */       if (Integer.MAX_VALUE - length < a.length) {
/* 445 */         throw new IllegalArgumentException("The total length of the specified arrays is too big.");
/*     */       }
/*     */       
/* 448 */       length += a.length;
/*     */     } 
/*     */     
/* 451 */     if (length == 0) {
/* 452 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 455 */     byte[] mergedArray = new byte[length];
/* 456 */     for (int i = 0, j = 0; i < arrays.length; i++) {
/* 457 */       byte[] a = arrays[i];
/* 458 */       System.arraycopy(a, 0, mergedArray, j, a.length);
/* 459 */       j += a.length;
/*     */     } 
/*     */     
/* 462 */     return wrappedBuffer(mergedArray);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuf... buffers) {
/* 476 */     switch (buffers.length) {
/*     */       case 0:
/* 478 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 480 */         return copiedBuffer(buffers[0]);
/*     */     } 
/*     */ 
/*     */     
/* 484 */     ByteOrder order = null;
/* 485 */     int length = 0;
/* 486 */     for (ByteBuf b : buffers) {
/* 487 */       int bLen = b.readableBytes();
/* 488 */       if (bLen > 0) {
/*     */ 
/*     */         
/* 491 */         if (Integer.MAX_VALUE - length < bLen) {
/* 492 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 495 */         length += bLen;
/* 496 */         if (order != null) {
/* 497 */           if (!order.equals(b.order())) {
/* 498 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 501 */           order = b.order();
/*     */         } 
/*     */       } 
/*     */     } 
/* 505 */     if (length == 0) {
/* 506 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 509 */     byte[] mergedArray = new byte[length];
/* 510 */     for (int i = 0, j = 0; i < buffers.length; i++) {
/* 511 */       ByteBuf b = buffers[i];
/* 512 */       int bLen = b.readableBytes();
/* 513 */       b.getBytes(b.readerIndex(), mergedArray, j, bLen);
/* 514 */       j += bLen;
/*     */     } 
/*     */     
/* 517 */     return wrappedBuffer(mergedArray).order(order);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuffer... buffers) {
/* 531 */     switch (buffers.length) {
/*     */       case 0:
/* 533 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 535 */         return copiedBuffer(buffers[0]);
/*     */     } 
/*     */ 
/*     */     
/* 539 */     ByteOrder order = null;
/* 540 */     int length = 0;
/* 541 */     for (ByteBuffer b : buffers) {
/* 542 */       int bLen = b.remaining();
/* 543 */       if (bLen > 0) {
/*     */ 
/*     */         
/* 546 */         if (Integer.MAX_VALUE - length < bLen) {
/* 547 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 550 */         length += bLen;
/* 551 */         if (order != null) {
/* 552 */           if (!order.equals(b.order())) {
/* 553 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 556 */           order = b.order();
/*     */         } 
/*     */       } 
/*     */     } 
/* 560 */     if (length == 0) {
/* 561 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 564 */     byte[] mergedArray = new byte[length];
/* 565 */     for (int i = 0, j = 0; i < buffers.length; i++) {
/* 566 */       ByteBuffer b = buffers[i];
/* 567 */       int bLen = b.remaining();
/* 568 */       int oldPos = b.position();
/* 569 */       b.get(mergedArray, j, bLen);
/* 570 */       b.position(oldPos);
/* 571 */       j += bLen;
/*     */     } 
/*     */     
/* 574 */     return wrappedBuffer(mergedArray).order(order);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(CharSequence string, Charset charset) {
/* 584 */     if (string == null) {
/* 585 */       throw new NullPointerException("string");
/*     */     }
/*     */     
/* 588 */     if (string instanceof CharBuffer) {
/* 589 */       return copiedBuffer((CharBuffer)string, charset);
/*     */     }
/*     */     
/* 592 */     return copiedBuffer(CharBuffer.wrap(string), charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(CharSequence string, int offset, int length, Charset charset) {
/* 603 */     if (string == null) {
/* 604 */       throw new NullPointerException("string");
/*     */     }
/* 606 */     if (length == 0) {
/* 607 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 610 */     if (string instanceof CharBuffer) {
/* 611 */       CharBuffer buf = (CharBuffer)string;
/* 612 */       if (buf.hasArray()) {
/* 613 */         return copiedBuffer(buf.array(), buf.arrayOffset() + buf.position() + offset, length, charset);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 619 */       buf = buf.slice();
/* 620 */       buf.limit(length);
/* 621 */       buf.position(offset);
/* 622 */       return copiedBuffer(buf, charset);
/*     */     } 
/*     */     
/* 625 */     return copiedBuffer(CharBuffer.wrap(string, offset, offset + length), charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(char[] array, Charset charset) {
/* 635 */     if (array == null) {
/* 636 */       throw new NullPointerException("array");
/*     */     }
/* 638 */     return copiedBuffer(array, 0, array.length, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(char[] array, int offset, int length, Charset charset) {
/* 648 */     if (array == null) {
/* 649 */       throw new NullPointerException("array");
/*     */     }
/* 651 */     if (length == 0) {
/* 652 */       return EMPTY_BUFFER;
/*     */     }
/* 654 */     return copiedBuffer(CharBuffer.wrap(array, offset, length), charset);
/*     */   }
/*     */   
/*     */   private static ByteBuf copiedBuffer(CharBuffer buffer, Charset charset) {
/* 658 */     return ByteBufUtil.encodeString0(ALLOC, true, buffer, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf unmodifiableBuffer(ByteBuf buffer) {
/* 668 */     ByteOrder endianness = buffer.order();
/* 669 */     if (endianness == BIG_ENDIAN) {
/* 670 */       return new ReadOnlyByteBuf(buffer);
/*     */     }
/*     */     
/* 673 */     return (new ReadOnlyByteBuf(buffer.order(BIG_ENDIAN))).order(LITTLE_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyInt(int value) {
/* 680 */     ByteBuf buf = buffer(4);
/* 681 */     buf.writeInt(value);
/* 682 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyInt(int... values) {
/* 689 */     if (values == null || values.length == 0) {
/* 690 */       return EMPTY_BUFFER;
/*     */     }
/* 692 */     ByteBuf buffer = buffer(values.length * 4);
/* 693 */     for (int v : values) {
/* 694 */       buffer.writeInt(v);
/*     */     }
/* 696 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(int value) {
/* 703 */     ByteBuf buf = buffer(2);
/* 704 */     buf.writeShort(value);
/* 705 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(short... values) {
/* 712 */     if (values == null || values.length == 0) {
/* 713 */       return EMPTY_BUFFER;
/*     */     }
/* 715 */     ByteBuf buffer = buffer(values.length * 2);
/* 716 */     for (int v : values) {
/* 717 */       buffer.writeShort(v);
/*     */     }
/* 719 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(int... values) {
/* 726 */     if (values == null || values.length == 0) {
/* 727 */       return EMPTY_BUFFER;
/*     */     }
/* 729 */     ByteBuf buffer = buffer(values.length * 2);
/* 730 */     for (int v : values) {
/* 731 */       buffer.writeShort(v);
/*     */     }
/* 733 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyMedium(int value) {
/* 740 */     ByteBuf buf = buffer(3);
/* 741 */     buf.writeMedium(value);
/* 742 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyMedium(int... values) {
/* 749 */     if (values == null || values.length == 0) {
/* 750 */       return EMPTY_BUFFER;
/*     */     }
/* 752 */     ByteBuf buffer = buffer(values.length * 3);
/* 753 */     for (int v : values) {
/* 754 */       buffer.writeMedium(v);
/*     */     }
/* 756 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyLong(long value) {
/* 763 */     ByteBuf buf = buffer(8);
/* 764 */     buf.writeLong(value);
/* 765 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyLong(long... values) {
/* 772 */     if (values == null || values.length == 0) {
/* 773 */       return EMPTY_BUFFER;
/*     */     }
/* 775 */     ByteBuf buffer = buffer(values.length * 8);
/* 776 */     for (long v : values) {
/* 777 */       buffer.writeLong(v);
/*     */     }
/* 779 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyBoolean(boolean value) {
/* 786 */     ByteBuf buf = buffer(1);
/* 787 */     buf.writeBoolean(value);
/* 788 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyBoolean(boolean... values) {
/* 795 */     if (values == null || values.length == 0) {
/* 796 */       return EMPTY_BUFFER;
/*     */     }
/* 798 */     ByteBuf buffer = buffer(values.length);
/* 799 */     for (boolean v : values) {
/* 800 */       buffer.writeBoolean(v);
/*     */     }
/* 802 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyFloat(float value) {
/* 809 */     ByteBuf buf = buffer(4);
/* 810 */     buf.writeFloat(value);
/* 811 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyFloat(float... values) {
/* 818 */     if (values == null || values.length == 0) {
/* 819 */       return EMPTY_BUFFER;
/*     */     }
/* 821 */     ByteBuf buffer = buffer(values.length * 4);
/* 822 */     for (float v : values) {
/* 823 */       buffer.writeFloat(v);
/*     */     }
/* 825 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyDouble(double value) {
/* 832 */     ByteBuf buf = buffer(8);
/* 833 */     buf.writeDouble(value);
/* 834 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyDouble(double... values) {
/* 841 */     if (values == null || values.length == 0) {
/* 842 */       return EMPTY_BUFFER;
/*     */     }
/* 844 */     ByteBuf buffer = buffer(values.length * 8);
/* 845 */     for (double v : values) {
/* 846 */       buffer.writeDouble(v);
/*     */     }
/* 848 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf unreleasableBuffer(ByteBuf buf) {
/* 855 */     return new UnreleasableByteBuf(buf);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\Unpooled.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */