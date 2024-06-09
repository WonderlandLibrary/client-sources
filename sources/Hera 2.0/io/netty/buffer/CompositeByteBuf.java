/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.ResourceLeak;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompositeByteBuf
/*      */   extends AbstractReferenceCountedByteBuf
/*      */ {
/*      */   private final ResourceLeak leak;
/*      */   private final ByteBufAllocator alloc;
/*      */   private final boolean direct;
/*   45 */   private final List<Component> components = new ArrayList<Component>();
/*      */   private final int maxNumComponents;
/*   47 */   private static final ByteBuffer FULL_BYTEBUFFER = (ByteBuffer)ByteBuffer.allocate(1).position(1);
/*      */   
/*      */   private boolean freed;
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents) {
/*   52 */     super(2147483647);
/*   53 */     if (alloc == null) {
/*   54 */       throw new NullPointerException("alloc");
/*      */     }
/*   56 */     this.alloc = alloc;
/*   57 */     this.direct = direct;
/*   58 */     this.maxNumComponents = maxNumComponents;
/*   59 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, ByteBuf... buffers) {
/*   63 */     super(2147483647);
/*   64 */     if (alloc == null) {
/*   65 */       throw new NullPointerException("alloc");
/*      */     }
/*   67 */     if (maxNumComponents < 2) {
/*   68 */       throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
/*      */     }
/*      */ 
/*      */     
/*   72 */     this.alloc = alloc;
/*   73 */     this.direct = direct;
/*   74 */     this.maxNumComponents = maxNumComponents;
/*      */     
/*   76 */     addComponents0(0, buffers);
/*   77 */     consolidateIfNeeded();
/*   78 */     setIndex(0, capacity());
/*   79 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, Iterable<ByteBuf> buffers) {
/*   84 */     super(2147483647);
/*   85 */     if (alloc == null) {
/*   86 */       throw new NullPointerException("alloc");
/*      */     }
/*   88 */     if (maxNumComponents < 2) {
/*   89 */       throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
/*      */     }
/*      */ 
/*      */     
/*   93 */     this.alloc = alloc;
/*   94 */     this.direct = direct;
/*   95 */     this.maxNumComponents = maxNumComponents;
/*   96 */     addComponents0(0, buffers);
/*   97 */     consolidateIfNeeded();
/*   98 */     setIndex(0, capacity());
/*   99 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponent(ByteBuf buffer) {
/*  111 */     addComponent0(this.components.size(), buffer);
/*  112 */     consolidateIfNeeded();
/*  113 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(ByteBuf... buffers) {
/*  125 */     addComponents0(this.components.size(), buffers);
/*  126 */     consolidateIfNeeded();
/*  127 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(Iterable<ByteBuf> buffers) {
/*  139 */     addComponents0(this.components.size(), buffers);
/*  140 */     consolidateIfNeeded();
/*  141 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponent(int cIndex, ByteBuf buffer) {
/*  154 */     addComponent0(cIndex, buffer);
/*  155 */     consolidateIfNeeded();
/*  156 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponent0(int cIndex, ByteBuf buffer) {
/*  160 */     checkComponentIndex(cIndex);
/*      */     
/*  162 */     if (buffer == null) {
/*  163 */       throw new NullPointerException("buffer");
/*      */     }
/*      */     
/*  166 */     int readableBytes = buffer.readableBytes();
/*  167 */     if (readableBytes == 0) {
/*  168 */       return cIndex;
/*      */     }
/*      */ 
/*      */     
/*  172 */     Component c = new Component(buffer.order(ByteOrder.BIG_ENDIAN).slice());
/*  173 */     if (cIndex == this.components.size()) {
/*  174 */       this.components.add(c);
/*  175 */       if (cIndex == 0) {
/*  176 */         c.endOffset = readableBytes;
/*      */       } else {
/*  178 */         Component prev = this.components.get(cIndex - 1);
/*  179 */         c.offset = prev.endOffset;
/*  180 */         c.endOffset = c.offset + readableBytes;
/*      */       } 
/*      */     } else {
/*  183 */       this.components.add(cIndex, c);
/*  184 */       updateComponentOffsets(cIndex);
/*      */     } 
/*  186 */     return cIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(int cIndex, ByteBuf... buffers) {
/*  199 */     addComponents0(cIndex, buffers);
/*  200 */     consolidateIfNeeded();
/*  201 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponents0(int cIndex, ByteBuf... buffers) {
/*  205 */     checkComponentIndex(cIndex);
/*      */     
/*  207 */     if (buffers == null) {
/*  208 */       throw new NullPointerException("buffers");
/*      */     }
/*      */     
/*  211 */     int readableBytes = 0;
/*  212 */     for (ByteBuf b : buffers) {
/*  213 */       if (b == null) {
/*      */         break;
/*      */       }
/*  216 */       readableBytes += b.readableBytes();
/*      */     } 
/*      */     
/*  219 */     if (readableBytes == 0) {
/*  220 */       return cIndex;
/*      */     }
/*      */ 
/*      */     
/*  224 */     for (ByteBuf b : buffers) {
/*  225 */       if (b == null) {
/*      */         break;
/*      */       }
/*  228 */       if (b.isReadable()) {
/*  229 */         cIndex = addComponent0(cIndex, b) + 1;
/*  230 */         int size = this.components.size();
/*  231 */         if (cIndex > size) {
/*  232 */           cIndex = size;
/*      */         }
/*      */       } else {
/*  235 */         b.release();
/*      */       } 
/*      */     } 
/*  238 */     return cIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(int cIndex, Iterable<ByteBuf> buffers) {
/*  251 */     addComponents0(cIndex, buffers);
/*  252 */     consolidateIfNeeded();
/*  253 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponents0(int cIndex, Iterable<ByteBuf> buffers) {
/*  257 */     if (buffers == null) {
/*  258 */       throw new NullPointerException("buffers");
/*      */     }
/*      */     
/*  261 */     if (buffers instanceof ByteBuf)
/*      */     {
/*  263 */       return addComponent0(cIndex, (ByteBuf)buffers);
/*      */     }
/*      */     
/*  266 */     if (!(buffers instanceof Collection)) {
/*  267 */       List<ByteBuf> list = new ArrayList<ByteBuf>();
/*  268 */       for (ByteBuf b : buffers) {
/*  269 */         list.add(b);
/*      */       }
/*  271 */       buffers = list;
/*      */     } 
/*      */     
/*  274 */     Collection<ByteBuf> col = (Collection<ByteBuf>)buffers;
/*  275 */     return addComponents0(cIndex, col.<ByteBuf>toArray(new ByteBuf[col.size()]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consolidateIfNeeded() {
/*  285 */     int numComponents = this.components.size();
/*  286 */     if (numComponents > this.maxNumComponents) {
/*  287 */       int capacity = ((Component)this.components.get(numComponents - 1)).endOffset;
/*      */       
/*  289 */       ByteBuf consolidated = allocBuffer(capacity);
/*      */ 
/*      */       
/*  292 */       for (int i = 0; i < numComponents; i++) {
/*  293 */         Component component = this.components.get(i);
/*  294 */         ByteBuf b = component.buf;
/*  295 */         consolidated.writeBytes(b);
/*  296 */         component.freeIfNecessary();
/*      */       } 
/*  298 */       Component c = new Component(consolidated);
/*  299 */       c.endOffset = c.length;
/*  300 */       this.components.clear();
/*  301 */       this.components.add(c);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkComponentIndex(int cIndex) {
/*  306 */     ensureAccessible();
/*  307 */     if (cIndex < 0 || cIndex > this.components.size()) {
/*  308 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", new Object[] { Integer.valueOf(cIndex), Integer.valueOf(this.components.size()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkComponentIndex(int cIndex, int numComponents) {
/*  315 */     ensureAccessible();
/*  316 */     if (cIndex < 0 || cIndex + numComponents > this.components.size()) {
/*  317 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", new Object[] { Integer.valueOf(cIndex), Integer.valueOf(numComponents), Integer.valueOf(this.components.size()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateComponentOffsets(int cIndex) {
/*  325 */     int size = this.components.size();
/*  326 */     if (size <= cIndex) {
/*      */       return;
/*      */     }
/*      */     
/*  330 */     Component c = this.components.get(cIndex);
/*  331 */     if (cIndex == 0) {
/*  332 */       c.offset = 0;
/*  333 */       c.endOffset = c.length;
/*  334 */       cIndex++;
/*      */     } 
/*      */     
/*  337 */     for (int i = cIndex; i < size; i++) {
/*  338 */       Component prev = this.components.get(i - 1);
/*  339 */       Component cur = this.components.get(i);
/*  340 */       cur.offset = prev.endOffset;
/*  341 */       cur.endOffset = cur.offset + cur.length;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf removeComponent(int cIndex) {
/*  351 */     checkComponentIndex(cIndex);
/*  352 */     ((Component)this.components.remove(cIndex)).freeIfNecessary();
/*  353 */     updateComponentOffsets(cIndex);
/*  354 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf removeComponents(int cIndex, int numComponents) {
/*  364 */     checkComponentIndex(cIndex, numComponents);
/*      */     
/*  366 */     List<Component> toRemove = this.components.subList(cIndex, cIndex + numComponents);
/*  367 */     for (Component c : toRemove) {
/*  368 */       c.freeIfNecessary();
/*      */     }
/*  370 */     toRemove.clear();
/*      */     
/*  372 */     updateComponentOffsets(cIndex);
/*  373 */     return this;
/*      */   }
/*      */   
/*      */   public Iterator<ByteBuf> iterator() {
/*  377 */     ensureAccessible();
/*  378 */     List<ByteBuf> list = new ArrayList<ByteBuf>(this.components.size());
/*  379 */     for (Component c : this.components) {
/*  380 */       list.add(c.buf);
/*      */     }
/*  382 */     return list.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ByteBuf> decompose(int offset, int length) {
/*  389 */     checkIndex(offset, length);
/*  390 */     if (length == 0) {
/*  391 */       return Collections.emptyList();
/*      */     }
/*      */     
/*  394 */     int componentId = toComponentIndex(offset);
/*  395 */     List<ByteBuf> slice = new ArrayList<ByteBuf>(this.components.size());
/*      */ 
/*      */     
/*  398 */     Component firstC = this.components.get(componentId);
/*  399 */     ByteBuf first = firstC.buf.duplicate();
/*  400 */     first.readerIndex(offset - firstC.offset);
/*      */     
/*  402 */     ByteBuf buf = first;
/*  403 */     int bytesToSlice = length;
/*      */     do {
/*  405 */       int readableBytes = buf.readableBytes();
/*  406 */       if (bytesToSlice <= readableBytes) {
/*      */         
/*  408 */         buf.writerIndex(buf.readerIndex() + bytesToSlice);
/*  409 */         slice.add(buf);
/*      */         
/*      */         break;
/*      */       } 
/*  413 */       slice.add(buf);
/*  414 */       bytesToSlice -= readableBytes;
/*  415 */       componentId++;
/*      */ 
/*      */       
/*  418 */       buf = ((Component)this.components.get(componentId)).buf.duplicate();
/*      */     }
/*  420 */     while (bytesToSlice > 0);
/*      */ 
/*      */     
/*  423 */     for (int i = 0; i < slice.size(); i++) {
/*  424 */       slice.set(i, ((ByteBuf)slice.get(i)).slice());
/*      */     }
/*      */     
/*  427 */     return slice;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  432 */     int size = this.components.size();
/*  433 */     if (size == 0) {
/*  434 */       return false;
/*      */     }
/*  436 */     for (int i = 0; i < size; i++) {
/*  437 */       if (!((Component)this.components.get(i)).buf.isDirect()) {
/*  438 */         return false;
/*      */       }
/*      */     } 
/*  441 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*  446 */     if (this.components.size() == 1) {
/*  447 */       return ((Component)this.components.get(0)).buf.hasArray();
/*      */     }
/*  449 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  454 */     if (this.components.size() == 1) {
/*  455 */       return ((Component)this.components.get(0)).buf.array();
/*      */     }
/*  457 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  462 */     if (this.components.size() == 1) {
/*  463 */       return ((Component)this.components.get(0)).buf.arrayOffset();
/*      */     }
/*  465 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/*  470 */     if (this.components.size() == 1) {
/*  471 */       return ((Component)this.components.get(0)).buf.hasMemoryAddress();
/*      */     }
/*  473 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/*  478 */     if (this.components.size() == 1) {
/*  479 */       return ((Component)this.components.get(0)).buf.memoryAddress();
/*      */     }
/*  481 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  486 */     if (this.components.isEmpty()) {
/*  487 */       return 0;
/*      */     }
/*  489 */     return ((Component)this.components.get(this.components.size() - 1)).endOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf capacity(int newCapacity) {
/*  494 */     ensureAccessible();
/*  495 */     if (newCapacity < 0 || newCapacity > maxCapacity()) {
/*  496 */       throw new IllegalArgumentException("newCapacity: " + newCapacity);
/*      */     }
/*      */     
/*  499 */     int oldCapacity = capacity();
/*  500 */     if (newCapacity > oldCapacity) {
/*  501 */       int paddingLength = newCapacity - oldCapacity;
/*      */       
/*  503 */       int nComponents = this.components.size();
/*  504 */       if (nComponents < this.maxNumComponents) {
/*  505 */         ByteBuf padding = allocBuffer(paddingLength);
/*  506 */         padding.setIndex(0, paddingLength);
/*  507 */         addComponent0(this.components.size(), padding);
/*      */       } else {
/*  509 */         ByteBuf padding = allocBuffer(paddingLength);
/*  510 */         padding.setIndex(0, paddingLength);
/*      */ 
/*      */         
/*  513 */         addComponent0(this.components.size(), padding);
/*  514 */         consolidateIfNeeded();
/*      */       } 
/*  516 */     } else if (newCapacity < oldCapacity) {
/*  517 */       int bytesToTrim = oldCapacity - newCapacity;
/*  518 */       for (ListIterator<Component> i = this.components.listIterator(this.components.size()); i.hasPrevious(); ) {
/*  519 */         Component c = i.previous();
/*  520 */         if (bytesToTrim >= c.length) {
/*  521 */           bytesToTrim -= c.length;
/*  522 */           i.remove();
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  527 */         Component newC = new Component(c.buf.slice(0, c.length - bytesToTrim));
/*  528 */         newC.offset = c.offset;
/*  529 */         newC.endOffset = newC.offset + newC.length;
/*  530 */         i.set(newC);
/*      */       } 
/*      */ 
/*      */       
/*  534 */       if (readerIndex() > newCapacity) {
/*  535 */         setIndex(newCapacity, newCapacity);
/*  536 */       } else if (writerIndex() > newCapacity) {
/*  537 */         writerIndex(newCapacity);
/*      */       } 
/*      */     } 
/*  540 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  545 */     return this.alloc;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  550 */     return ByteOrder.BIG_ENDIAN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int numComponents() {
/*  557 */     return this.components.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int maxNumComponents() {
/*  564 */     return this.maxNumComponents;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int toComponentIndex(int offset) {
/*  571 */     checkIndex(offset);
/*      */     
/*  573 */     for (int low = 0, high = this.components.size(); low <= high; ) {
/*  574 */       int mid = low + high >>> 1;
/*  575 */       Component c = this.components.get(mid);
/*  576 */       if (offset >= c.endOffset) {
/*  577 */         low = mid + 1; continue;
/*  578 */       }  if (offset < c.offset) {
/*  579 */         high = mid - 1; continue;
/*      */       } 
/*  581 */       return mid;
/*      */     } 
/*      */ 
/*      */     
/*  585 */     throw new Error("should not reach here");
/*      */   }
/*      */   
/*      */   public int toByteIndex(int cIndex) {
/*  589 */     checkComponentIndex(cIndex);
/*  590 */     return ((Component)this.components.get(cIndex)).offset;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  595 */     return _getByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   protected byte _getByte(int index) {
/*  600 */     Component c = findComponent(index);
/*  601 */     return c.buf.getByte(index - c.offset);
/*      */   }
/*      */ 
/*      */   
/*      */   protected short _getShort(int index) {
/*  606 */     Component c = findComponent(index);
/*  607 */     if (index + 2 <= c.endOffset)
/*  608 */       return c.buf.getShort(index - c.offset); 
/*  609 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  610 */       return (short)((_getByte(index) & 0xFF) << 8 | _getByte(index + 1) & 0xFF);
/*      */     }
/*  612 */     return (short)(_getByte(index) & 0xFF | (_getByte(index + 1) & 0xFF) << 8);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getUnsignedMedium(int index) {
/*  618 */     Component c = findComponent(index);
/*  619 */     if (index + 3 <= c.endOffset)
/*  620 */       return c.buf.getUnsignedMedium(index - c.offset); 
/*  621 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  622 */       return (_getShort(index) & 0xFFFF) << 8 | _getByte(index + 2) & 0xFF;
/*      */     }
/*  624 */     return _getShort(index) & 0xFFFF | (_getByte(index + 2) & 0xFF) << 16;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getInt(int index) {
/*  630 */     Component c = findComponent(index);
/*  631 */     if (index + 4 <= c.endOffset)
/*  632 */       return c.buf.getInt(index - c.offset); 
/*  633 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  634 */       return (_getShort(index) & 0xFFFF) << 16 | _getShort(index + 2) & 0xFFFF;
/*      */     }
/*  636 */     return _getShort(index) & 0xFFFF | (_getShort(index + 2) & 0xFFFF) << 16;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected long _getLong(int index) {
/*  642 */     Component c = findComponent(index);
/*  643 */     if (index + 8 <= c.endOffset)
/*  644 */       return c.buf.getLong(index - c.offset); 
/*  645 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  646 */       return (_getInt(index) & 0xFFFFFFFFL) << 32L | _getInt(index + 4) & 0xFFFFFFFFL;
/*      */     }
/*  648 */     return _getInt(index) & 0xFFFFFFFFL | (_getInt(index + 4) & 0xFFFFFFFFL) << 32L;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/*  654 */     checkDstIndex(index, length, dstIndex, dst.length);
/*  655 */     if (length == 0) {
/*  656 */       return this;
/*      */     }
/*      */     
/*  659 */     int i = toComponentIndex(index);
/*  660 */     while (length > 0) {
/*  661 */       Component c = this.components.get(i);
/*  662 */       ByteBuf s = c.buf;
/*  663 */       int adjustment = c.offset;
/*  664 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  665 */       s.getBytes(index - adjustment, dst, dstIndex, localLength);
/*  666 */       index += localLength;
/*  667 */       dstIndex += localLength;
/*  668 */       length -= localLength;
/*  669 */       i++;
/*      */     } 
/*  671 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuffer dst) {
/*  676 */     int limit = dst.limit();
/*  677 */     int length = dst.remaining();
/*      */     
/*  679 */     checkIndex(index, length);
/*  680 */     if (length == 0) {
/*  681 */       return this;
/*      */     }
/*      */     
/*  684 */     int i = toComponentIndex(index);
/*      */     try {
/*  686 */       while (length > 0) {
/*  687 */         Component c = this.components.get(i);
/*  688 */         ByteBuf s = c.buf;
/*  689 */         int adjustment = c.offset;
/*  690 */         int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  691 */         dst.limit(dst.position() + localLength);
/*  692 */         s.getBytes(index - adjustment, dst);
/*  693 */         index += localLength;
/*  694 */         length -= localLength;
/*  695 */         i++;
/*      */       } 
/*      */     } finally {
/*  698 */       dst.limit(limit);
/*      */     } 
/*  700 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  705 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/*  706 */     if (length == 0) {
/*  707 */       return this;
/*      */     }
/*      */     
/*  710 */     int i = toComponentIndex(index);
/*  711 */     while (length > 0) {
/*  712 */       Component c = this.components.get(i);
/*  713 */       ByteBuf s = c.buf;
/*  714 */       int adjustment = c.offset;
/*  715 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  716 */       s.getBytes(index - adjustment, dst, dstIndex, localLength);
/*  717 */       index += localLength;
/*  718 */       dstIndex += localLength;
/*  719 */       length -= localLength;
/*  720 */       i++;
/*      */     } 
/*  722 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/*  728 */     int count = nioBufferCount();
/*  729 */     if (count == 1) {
/*  730 */       return out.write(internalNioBuffer(index, length));
/*      */     }
/*  732 */     long writtenBytes = out.write(nioBuffers(index, length));
/*  733 */     if (writtenBytes > 2147483647L) {
/*  734 */       return Integer.MAX_VALUE;
/*      */     }
/*  736 */     return (int)writtenBytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/*  743 */     checkIndex(index, length);
/*  744 */     if (length == 0) {
/*  745 */       return this;
/*      */     }
/*      */     
/*  748 */     int i = toComponentIndex(index);
/*  749 */     while (length > 0) {
/*  750 */       Component c = this.components.get(i);
/*  751 */       ByteBuf s = c.buf;
/*  752 */       int adjustment = c.offset;
/*  753 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  754 */       s.getBytes(index - adjustment, out, localLength);
/*  755 */       index += localLength;
/*  756 */       length -= localLength;
/*  757 */       i++;
/*      */     } 
/*  759 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setByte(int index, int value) {
/*  764 */     Component c = findComponent(index);
/*  765 */     c.buf.setByte(index - c.offset, value);
/*  766 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setByte(int index, int value) {
/*  771 */     setByte(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setShort(int index, int value) {
/*  776 */     return (CompositeByteBuf)super.setShort(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setShort(int index, int value) {
/*  781 */     Component c = findComponent(index);
/*  782 */     if (index + 2 <= c.endOffset) {
/*  783 */       c.buf.setShort(index - c.offset, value);
/*  784 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  785 */       _setByte(index, (byte)(value >>> 8));
/*  786 */       _setByte(index + 1, (byte)value);
/*      */     } else {
/*  788 */       _setByte(index, (byte)value);
/*  789 */       _setByte(index + 1, (byte)(value >>> 8));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setMedium(int index, int value) {
/*  795 */     return (CompositeByteBuf)super.setMedium(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setMedium(int index, int value) {
/*  800 */     Component c = findComponent(index);
/*  801 */     if (index + 3 <= c.endOffset) {
/*  802 */       c.buf.setMedium(index - c.offset, value);
/*  803 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  804 */       _setShort(index, (short)(value >> 8));
/*  805 */       _setByte(index + 2, (byte)value);
/*      */     } else {
/*  807 */       _setShort(index, (short)value);
/*  808 */       _setByte(index + 2, (byte)(value >>> 16));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setInt(int index, int value) {
/*  814 */     return (CompositeByteBuf)super.setInt(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setInt(int index, int value) {
/*  819 */     Component c = findComponent(index);
/*  820 */     if (index + 4 <= c.endOffset) {
/*  821 */       c.buf.setInt(index - c.offset, value);
/*  822 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  823 */       _setShort(index, (short)(value >>> 16));
/*  824 */       _setShort(index + 2, (short)value);
/*      */     } else {
/*  826 */       _setShort(index, (short)value);
/*  827 */       _setShort(index + 2, (short)(value >>> 16));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setLong(int index, long value) {
/*  833 */     return (CompositeByteBuf)super.setLong(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setLong(int index, long value) {
/*  838 */     Component c = findComponent(index);
/*  839 */     if (index + 8 <= c.endOffset) {
/*  840 */       c.buf.setLong(index - c.offset, value);
/*  841 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  842 */       _setInt(index, (int)(value >>> 32L));
/*  843 */       _setInt(index + 4, (int)value);
/*      */     } else {
/*  845 */       _setInt(index, (int)value);
/*  846 */       _setInt(index + 4, (int)(value >>> 32L));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/*  852 */     checkSrcIndex(index, length, srcIndex, src.length);
/*  853 */     if (length == 0) {
/*  854 */       return this;
/*      */     }
/*      */     
/*  857 */     int i = toComponentIndex(index);
/*  858 */     while (length > 0) {
/*  859 */       Component c = this.components.get(i);
/*  860 */       ByteBuf s = c.buf;
/*  861 */       int adjustment = c.offset;
/*  862 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  863 */       s.setBytes(index - adjustment, src, srcIndex, localLength);
/*  864 */       index += localLength;
/*  865 */       srcIndex += localLength;
/*  866 */       length -= localLength;
/*  867 */       i++;
/*      */     } 
/*  869 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuffer src) {
/*  874 */     int limit = src.limit();
/*  875 */     int length = src.remaining();
/*      */     
/*  877 */     checkIndex(index, length);
/*  878 */     if (length == 0) {
/*  879 */       return this;
/*      */     }
/*      */     
/*  882 */     int i = toComponentIndex(index);
/*      */     try {
/*  884 */       while (length > 0) {
/*  885 */         Component c = this.components.get(i);
/*  886 */         ByteBuf s = c.buf;
/*  887 */         int adjustment = c.offset;
/*  888 */         int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  889 */         src.limit(src.position() + localLength);
/*  890 */         s.setBytes(index - adjustment, src);
/*  891 */         index += localLength;
/*  892 */         length -= localLength;
/*  893 */         i++;
/*      */       } 
/*      */     } finally {
/*  896 */       src.limit(limit);
/*      */     } 
/*  898 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/*  903 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/*  904 */     if (length == 0) {
/*  905 */       return this;
/*      */     }
/*      */     
/*  908 */     int i = toComponentIndex(index);
/*  909 */     while (length > 0) {
/*  910 */       Component c = this.components.get(i);
/*  911 */       ByteBuf s = c.buf;
/*  912 */       int adjustment = c.offset;
/*  913 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  914 */       s.setBytes(index - adjustment, src, srcIndex, localLength);
/*  915 */       index += localLength;
/*  916 */       srcIndex += localLength;
/*  917 */       length -= localLength;
/*  918 */       i++;
/*      */     } 
/*  920 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, InputStream in, int length) throws IOException {
/*  925 */     checkIndex(index, length);
/*  926 */     if (length == 0) {
/*  927 */       return in.read(EmptyArrays.EMPTY_BYTES);
/*      */     }
/*      */     
/*  930 */     int i = toComponentIndex(index);
/*  931 */     int readBytes = 0;
/*      */     
/*      */     do {
/*  934 */       Component c = this.components.get(i);
/*  935 */       ByteBuf s = c.buf;
/*  936 */       int adjustment = c.offset;
/*  937 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  938 */       int localReadBytes = s.setBytes(index - adjustment, in, localLength);
/*  939 */       if (localReadBytes < 0) {
/*  940 */         if (readBytes == 0) {
/*  941 */           return -1;
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/*  947 */       if (localReadBytes == localLength) {
/*  948 */         index += localLength;
/*  949 */         length -= localLength;
/*  950 */         readBytes += localLength;
/*  951 */         i++;
/*      */       } else {
/*  953 */         index += localReadBytes;
/*  954 */         length -= localReadBytes;
/*  955 */         readBytes += localReadBytes;
/*      */       } 
/*  957 */     } while (length > 0);
/*      */     
/*  959 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/*  964 */     checkIndex(index, length);
/*  965 */     if (length == 0) {
/*  966 */       return in.read(FULL_BYTEBUFFER);
/*      */     }
/*      */     
/*  969 */     int i = toComponentIndex(index);
/*  970 */     int readBytes = 0;
/*      */     do {
/*  972 */       Component c = this.components.get(i);
/*  973 */       ByteBuf s = c.buf;
/*  974 */       int adjustment = c.offset;
/*  975 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  976 */       int localReadBytes = s.setBytes(index - adjustment, in, localLength);
/*      */       
/*  978 */       if (localReadBytes == 0) {
/*      */         break;
/*      */       }
/*      */       
/*  982 */       if (localReadBytes < 0) {
/*  983 */         if (readBytes == 0) {
/*  984 */           return -1;
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/*  990 */       if (localReadBytes == localLength) {
/*  991 */         index += localLength;
/*  992 */         length -= localLength;
/*  993 */         readBytes += localLength;
/*  994 */         i++;
/*      */       } else {
/*  996 */         index += localReadBytes;
/*  997 */         length -= localReadBytes;
/*  998 */         readBytes += localReadBytes;
/*      */       } 
/* 1000 */     } while (length > 0);
/*      */     
/* 1002 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int index, int length) {
/* 1007 */     checkIndex(index, length);
/* 1008 */     ByteBuf dst = Unpooled.buffer(length);
/* 1009 */     if (length != 0) {
/* 1010 */       copyTo(index, length, toComponentIndex(index), dst);
/*      */     }
/* 1012 */     return dst;
/*      */   }
/*      */   
/*      */   private void copyTo(int index, int length, int componentId, ByteBuf dst) {
/* 1016 */     int dstIndex = 0;
/* 1017 */     int i = componentId;
/*      */     
/* 1019 */     while (length > 0) {
/* 1020 */       Component c = this.components.get(i);
/* 1021 */       ByteBuf s = c.buf;
/* 1022 */       int adjustment = c.offset;
/* 1023 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/* 1024 */       s.getBytes(index - adjustment, dst, dstIndex, localLength);
/* 1025 */       index += localLength;
/* 1026 */       dstIndex += localLength;
/* 1027 */       length -= localLength;
/* 1028 */       i++;
/*      */     } 
/*      */     
/* 1031 */     dst.writerIndex(dst.capacity());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf component(int cIndex) {
/* 1041 */     return internalComponent(cIndex).duplicate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf componentAtOffset(int offset) {
/* 1051 */     return internalComponentAtOffset(offset).duplicate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf internalComponent(int cIndex) {
/* 1061 */     checkComponentIndex(cIndex);
/* 1062 */     return ((Component)this.components.get(cIndex)).buf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf internalComponentAtOffset(int offset) {
/* 1072 */     return (findComponent(offset)).buf;
/*      */   }
/*      */   
/*      */   private Component findComponent(int offset) {
/* 1076 */     checkIndex(offset);
/*      */     
/* 1078 */     for (int low = 0, high = this.components.size(); low <= high; ) {
/* 1079 */       int mid = low + high >>> 1;
/* 1080 */       Component c = this.components.get(mid);
/* 1081 */       if (offset >= c.endOffset) {
/* 1082 */         low = mid + 1; continue;
/* 1083 */       }  if (offset < c.offset) {
/* 1084 */         high = mid - 1; continue;
/*      */       } 
/* 1086 */       return c;
/*      */     } 
/*      */ 
/*      */     
/* 1090 */     throw new Error("should not reach here");
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/* 1095 */     if (this.components.size() == 1) {
/* 1096 */       return ((Component)this.components.get(0)).buf.nioBufferCount();
/*      */     }
/* 1098 */     int count = 0;
/* 1099 */     int componentsCount = this.components.size();
/* 1100 */     for (int i = 0; i < componentsCount; i++) {
/* 1101 */       Component c = this.components.get(i);
/* 1102 */       count += c.buf.nioBufferCount();
/*      */     } 
/* 1104 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 1110 */     if (this.components.size() == 1) {
/* 1111 */       return ((Component)this.components.get(0)).buf.internalNioBuffer(index, length);
/*      */     }
/* 1113 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int index, int length) {
/* 1118 */     if (this.components.size() == 1) {
/* 1119 */       ByteBuf buf = ((Component)this.components.get(0)).buf;
/* 1120 */       if (buf.nioBufferCount() == 1) {
/* 1121 */         return ((Component)this.components.get(0)).buf.nioBuffer(index, length);
/*      */       }
/*      */     } 
/* 1124 */     ByteBuffer merged = ByteBuffer.allocate(length).order(order());
/* 1125 */     ByteBuffer[] buffers = nioBuffers(index, length);
/*      */ 
/*      */     
/* 1128 */     for (int i = 0; i < buffers.length; i++) {
/* 1129 */       merged.put(buffers[i]);
/*      */     }
/*      */     
/* 1132 */     merged.flip();
/* 1133 */     return merged;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 1138 */     checkIndex(index, length);
/* 1139 */     if (length == 0) {
/* 1140 */       return EmptyArrays.EMPTY_BYTE_BUFFERS;
/*      */     }
/*      */     
/* 1143 */     List<ByteBuffer> buffers = new ArrayList<ByteBuffer>(this.components.size());
/* 1144 */     int i = toComponentIndex(index);
/* 1145 */     while (length > 0) {
/* 1146 */       Component c = this.components.get(i);
/* 1147 */       ByteBuf s = c.buf;
/* 1148 */       int adjustment = c.offset;
/* 1149 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/* 1150 */       switch (s.nioBufferCount()) {
/*      */         case 0:
/* 1152 */           throw new UnsupportedOperationException();
/*      */         case 1:
/* 1154 */           buffers.add(s.nioBuffer(index - adjustment, localLength));
/*      */           break;
/*      */         default:
/* 1157 */           Collections.addAll(buffers, s.nioBuffers(index - adjustment, localLength));
/*      */           break;
/*      */       } 
/* 1160 */       index += localLength;
/* 1161 */       length -= localLength;
/* 1162 */       i++;
/*      */     } 
/*      */     
/* 1165 */     return buffers.<ByteBuffer>toArray(new ByteBuffer[buffers.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf consolidate() {
/* 1172 */     ensureAccessible();
/* 1173 */     int numComponents = numComponents();
/* 1174 */     if (numComponents <= 1) {
/* 1175 */       return this;
/*      */     }
/*      */     
/* 1178 */     Component last = this.components.get(numComponents - 1);
/* 1179 */     int capacity = last.endOffset;
/* 1180 */     ByteBuf consolidated = allocBuffer(capacity);
/*      */     
/* 1182 */     for (int i = 0; i < numComponents; i++) {
/* 1183 */       Component c = this.components.get(i);
/* 1184 */       ByteBuf b = c.buf;
/* 1185 */       consolidated.writeBytes(b);
/* 1186 */       c.freeIfNecessary();
/*      */     } 
/*      */     
/* 1189 */     this.components.clear();
/* 1190 */     this.components.add(new Component(consolidated));
/* 1191 */     updateComponentOffsets(0);
/* 1192 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf consolidate(int cIndex, int numComponents) {
/* 1202 */     checkComponentIndex(cIndex, numComponents);
/* 1203 */     if (numComponents <= 1) {
/* 1204 */       return this;
/*      */     }
/*      */     
/* 1207 */     int endCIndex = cIndex + numComponents;
/* 1208 */     Component last = this.components.get(endCIndex - 1);
/* 1209 */     int capacity = last.endOffset - ((Component)this.components.get(cIndex)).offset;
/* 1210 */     ByteBuf consolidated = allocBuffer(capacity);
/*      */     
/* 1212 */     for (int i = cIndex; i < endCIndex; i++) {
/* 1213 */       Component c = this.components.get(i);
/* 1214 */       ByteBuf b = c.buf;
/* 1215 */       consolidated.writeBytes(b);
/* 1216 */       c.freeIfNecessary();
/*      */     } 
/*      */     
/* 1219 */     this.components.subList(cIndex + 1, endCIndex).clear();
/* 1220 */     this.components.set(cIndex, new Component(consolidated));
/* 1221 */     updateComponentOffsets(cIndex);
/* 1222 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardReadComponents() {
/* 1229 */     ensureAccessible();
/* 1230 */     int readerIndex = readerIndex();
/* 1231 */     if (readerIndex == 0) {
/* 1232 */       return this;
/*      */     }
/*      */ 
/*      */     
/* 1236 */     int writerIndex = writerIndex();
/* 1237 */     if (readerIndex == writerIndex && writerIndex == capacity()) {
/* 1238 */       for (Component c : this.components) {
/* 1239 */         c.freeIfNecessary();
/*      */       }
/* 1241 */       this.components.clear();
/* 1242 */       setIndex(0, 0);
/* 1243 */       adjustMarkers(readerIndex);
/* 1244 */       return this;
/*      */     } 
/*      */ 
/*      */     
/* 1248 */     int firstComponentId = toComponentIndex(readerIndex);
/* 1249 */     for (int i = 0; i < firstComponentId; i++) {
/* 1250 */       ((Component)this.components.get(i)).freeIfNecessary();
/*      */     }
/* 1252 */     this.components.subList(0, firstComponentId).clear();
/*      */ 
/*      */     
/* 1255 */     Component first = this.components.get(0);
/* 1256 */     int offset = first.offset;
/* 1257 */     updateComponentOffsets(0);
/* 1258 */     setIndex(readerIndex - offset, writerIndex - offset);
/* 1259 */     adjustMarkers(offset);
/* 1260 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardReadBytes() {
/* 1265 */     ensureAccessible();
/* 1266 */     int readerIndex = readerIndex();
/* 1267 */     if (readerIndex == 0) {
/* 1268 */       return this;
/*      */     }
/*      */ 
/*      */     
/* 1272 */     int writerIndex = writerIndex();
/* 1273 */     if (readerIndex == writerIndex && writerIndex == capacity()) {
/* 1274 */       for (Component component : this.components) {
/* 1275 */         component.freeIfNecessary();
/*      */       }
/* 1277 */       this.components.clear();
/* 1278 */       setIndex(0, 0);
/* 1279 */       adjustMarkers(readerIndex);
/* 1280 */       return this;
/*      */     } 
/*      */ 
/*      */     
/* 1284 */     int firstComponentId = toComponentIndex(readerIndex);
/* 1285 */     for (int i = 0; i < firstComponentId; i++) {
/* 1286 */       ((Component)this.components.get(i)).freeIfNecessary();
/*      */     }
/* 1288 */     this.components.subList(0, firstComponentId).clear();
/*      */ 
/*      */     
/* 1291 */     Component c = this.components.get(0);
/* 1292 */     int adjustment = readerIndex - c.offset;
/* 1293 */     if (adjustment == c.length) {
/*      */       
/* 1295 */       this.components.remove(0);
/*      */     } else {
/* 1297 */       Component newC = new Component(c.buf.slice(adjustment, c.length - adjustment));
/* 1298 */       this.components.set(0, newC);
/*      */     } 
/*      */ 
/*      */     
/* 1302 */     updateComponentOffsets(0);
/* 1303 */     setIndex(0, writerIndex - readerIndex);
/* 1304 */     adjustMarkers(readerIndex);
/* 1305 */     return this;
/*      */   }
/*      */   
/*      */   private ByteBuf allocBuffer(int capacity) {
/* 1309 */     if (this.direct) {
/* 1310 */       return alloc().directBuffer(capacity);
/*      */     }
/* 1312 */     return alloc().heapBuffer(capacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1317 */     String result = super.toString();
/* 1318 */     result = result.substring(0, result.length() - 1);
/* 1319 */     return result + ", components=" + this.components.size() + ')';
/*      */   }
/*      */   
/*      */   private static final class Component {
/*      */     final ByteBuf buf;
/*      */     final int length;
/*      */     int offset;
/*      */     int endOffset;
/*      */     
/*      */     Component(ByteBuf buf) {
/* 1329 */       this.buf = buf;
/* 1330 */       this.length = buf.readableBytes();
/*      */     }
/*      */ 
/*      */     
/*      */     void freeIfNecessary() {
/* 1335 */       this.buf.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readerIndex(int readerIndex) {
/* 1341 */     return (CompositeByteBuf)super.readerIndex(readerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writerIndex(int writerIndex) {
/* 1346 */     return (CompositeByteBuf)super.writerIndex(writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setIndex(int readerIndex, int writerIndex) {
/* 1351 */     return (CompositeByteBuf)super.setIndex(readerIndex, writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf clear() {
/* 1356 */     return (CompositeByteBuf)super.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf markReaderIndex() {
/* 1361 */     return (CompositeByteBuf)super.markReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf resetReaderIndex() {
/* 1366 */     return (CompositeByteBuf)super.resetReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf markWriterIndex() {
/* 1371 */     return (CompositeByteBuf)super.markWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf resetWriterIndex() {
/* 1376 */     return (CompositeByteBuf)super.resetWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf ensureWritable(int minWritableBytes) {
/* 1381 */     return (CompositeByteBuf)super.ensureWritable(minWritableBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst) {
/* 1386 */     return (CompositeByteBuf)super.getBytes(index, dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 1391 */     return (CompositeByteBuf)super.getBytes(index, dst, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, byte[] dst) {
/* 1396 */     return (CompositeByteBuf)super.getBytes(index, dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBoolean(int index, boolean value) {
/* 1401 */     return (CompositeByteBuf)super.setBoolean(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setChar(int index, int value) {
/* 1406 */     return (CompositeByteBuf)super.setChar(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setFloat(int index, float value) {
/* 1411 */     return (CompositeByteBuf)super.setFloat(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setDouble(int index, double value) {
/* 1416 */     return (CompositeByteBuf)super.setDouble(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src) {
/* 1421 */     return (CompositeByteBuf)super.setBytes(index, src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src, int length) {
/* 1426 */     return (CompositeByteBuf)super.setBytes(index, src, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, byte[] src) {
/* 1431 */     return (CompositeByteBuf)super.setBytes(index, src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setZero(int index, int length) {
/* 1436 */     return (CompositeByteBuf)super.setZero(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst) {
/* 1441 */     return (CompositeByteBuf)super.readBytes(dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst, int length) {
/* 1446 */     return (CompositeByteBuf)super.readBytes(dst, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 1451 */     return (CompositeByteBuf)super.readBytes(dst, dstIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] dst) {
/* 1456 */     return (CompositeByteBuf)super.readBytes(dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 1461 */     return (CompositeByteBuf)super.readBytes(dst, dstIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuffer dst) {
/* 1466 */     return (CompositeByteBuf)super.readBytes(dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 1471 */     return (CompositeByteBuf)super.readBytes(out, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf skipBytes(int length) {
/* 1476 */     return (CompositeByteBuf)super.skipBytes(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBoolean(boolean value) {
/* 1481 */     return (CompositeByteBuf)super.writeBoolean(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeByte(int value) {
/* 1486 */     return (CompositeByteBuf)super.writeByte(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeShort(int value) {
/* 1491 */     return (CompositeByteBuf)super.writeShort(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeMedium(int value) {
/* 1496 */     return (CompositeByteBuf)super.writeMedium(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeInt(int value) {
/* 1501 */     return (CompositeByteBuf)super.writeInt(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeLong(long value) {
/* 1506 */     return (CompositeByteBuf)super.writeLong(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeChar(int value) {
/* 1511 */     return (CompositeByteBuf)super.writeChar(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeFloat(float value) {
/* 1516 */     return (CompositeByteBuf)super.writeFloat(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeDouble(double value) {
/* 1521 */     return (CompositeByteBuf)super.writeDouble(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src) {
/* 1526 */     return (CompositeByteBuf)super.writeBytes(src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src, int length) {
/* 1531 */     return (CompositeByteBuf)super.writeBytes(src, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 1536 */     return (CompositeByteBuf)super.writeBytes(src, srcIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] src) {
/* 1541 */     return (CompositeByteBuf)super.writeBytes(src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 1546 */     return (CompositeByteBuf)super.writeBytes(src, srcIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuffer src) {
/* 1551 */     return (CompositeByteBuf)super.writeBytes(src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeZero(int length) {
/* 1556 */     return (CompositeByteBuf)super.writeZero(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf retain(int increment) {
/* 1561 */     return (CompositeByteBuf)super.retain(increment);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf retain() {
/* 1566 */     return (CompositeByteBuf)super.retain();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/* 1571 */     return nioBuffers(readerIndex(), readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardSomeReadBytes() {
/* 1576 */     return discardReadComponents();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void deallocate() {
/* 1581 */     if (this.freed) {
/*      */       return;
/*      */     }
/*      */     
/* 1585 */     this.freed = true;
/* 1586 */     int size = this.components.size();
/*      */ 
/*      */     
/* 1589 */     for (int i = 0; i < size; i++) {
/* 1590 */       ((Component)this.components.get(i)).freeIfNecessary();
/*      */     }
/*      */     
/* 1593 */     if (this.leak != null) {
/* 1594 */       this.leak.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/* 1600 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\CompositeByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */