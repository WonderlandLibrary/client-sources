package org.newdawn.slick.opengl;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;


































public class PNGDecoder
{
  public static Format ALPHA = new Format(1, true, null);
  public static Format LUMINANCE = new Format(1, false, null);
  public static Format LUMINANCE_ALPHA = new Format(2, true, null);
  public static Format RGB = new Format(3, false, null);
  public static Format RGBA = new Format(4, true, null);
  public static Format BGRA = new Format(4, true, null);
  public static Format ABGR = new Format(4, true, null);
  
  public static class Format
  {
    final int numComponents;
    final boolean hasAlpha;
    
    private Format(int numComponents, boolean hasAlpha) {
      this.numComponents = numComponents;
      this.hasAlpha = hasAlpha;
    }
    
    public int getNumComponents() {
      return numComponents;
    }
    
    public boolean isHasAlpha() {
      return hasAlpha;
    }
  }
  
  private static final byte[] SIGNATURE = { -119, 80, 78, 71, 13, 10, 26, 10 };
  
  private static final int IHDR = 1229472850;
  
  private static final int PLTE = 1347179589;
  
  private static final int tRNS = 1951551059;
  private static final int IDAT = 1229209940;
  private static final int IEND = 1229278788;
  private static final byte COLOR_GREYSCALE = 0;
  private static final byte COLOR_TRUECOLOR = 2;
  private static final byte COLOR_INDEXED = 3;
  private static final byte COLOR_GREYALPHA = 4;
  private static final byte COLOR_TRUEALPHA = 6;
  private final InputStream input;
  private final CRC32 crc;
  private final byte[] buffer;
  private int chunkLength;
  private int chunkType;
  private int chunkRemaining;
  private int width;
  private int height;
  private int bitdepth;
  private int colorType;
  private int bytesPerPixel;
  private byte[] palette;
  private byte[] paletteA;
  private byte[] transPixel;
  
  public PNGDecoder(InputStream input)
    throws IOException
  {
    this.input = input;
    crc = new CRC32();
    buffer = new byte['က'];
    
    readFully(buffer, 0, SIGNATURE.length);
    if (!checkSignature(buffer)) {
      throw new IOException("Not a valid PNG file");
    }
    
    openChunk(1229472850);
    readIHDR();
    closeChunk();
    for (;;)
    {
      openChunk();
      switch (chunkType) {
      case 1229209940: 
        break;
      case 1347179589: 
        readPLTE();
        break;
      case 1951551059: 
        readtRNS();
      }
      
      closeChunk();
    }
    
    if ((colorType == 3) && (palette == null)) {
      throw new IOException("Missing PLTE chunk");
    }
  }
  
  public int getHeight() {
    return height;
  }
  
  public int getWidth() {
    return width;
  }
  
  public boolean hasAlpha() {
    return (colorType == 6) || 
      (paletteA != null) || (transPixel != null);
  }
  
  public boolean isRGB() {
    return (colorType == 6) || 
      (colorType == 2) || 
      (colorType == 3);
  }
  






  public Format decideTextureFormat(Format fmt)
  {
    switch (colorType) {
    case 2: 
      if ((fmt == ABGR) || (fmt == RGBA) || (fmt == BGRA) || (fmt == RGB)) {
        return fmt;
      }
      
      return RGB;
    case 6: 
      if ((fmt == ABGR) || (fmt == RGBA) || (fmt == BGRA) || (fmt == RGB)) {
        return fmt;
      }
      
      return RGBA;
    case 0: 
      if ((fmt == LUMINANCE) || (fmt == ALPHA)) {
        return fmt;
      }
      
      return LUMINANCE;
    case 4: 
      return LUMINANCE_ALPHA;
    case 3: 
      if ((fmt == ABGR) || (fmt == RGBA) || (fmt == BGRA)) {
        return fmt;
      }
      
      return RGBA;
    }
    throw new UnsupportedOperationException("Not yet implemented");
  }
  
  public void decode(ByteBuffer buffer, int stride, Format fmt) throws IOException
  {
    int offset = buffer.position();
    int lineSize = (width * bitdepth + 7) / 8 * bytesPerPixel;
    byte[] curLine = new byte[lineSize + 1];
    byte[] prevLine = new byte[lineSize + 1];
    byte[] palLine = bitdepth < 8 ? new byte[width + 1] : null;
    
    Inflater inflater = new Inflater();
    try {
      for (int y = 0; y < height; y++) {
        readChunkUnzip(inflater, curLine, 0, curLine.length);
        unfilter(curLine, prevLine);
        
        buffer.position(offset + y * stride);
        
        switch (colorType) {
        case 2: 
          if (fmt == ABGR) {
            copyRGBtoABGR(buffer, curLine);
          }
          else if (fmt == RGBA) {
            copyRGBtoRGBA(buffer, curLine);
          }
          else if (fmt == BGRA) {
            copyRGBtoBGRA(buffer, curLine);
          }
          else if (fmt == RGB) {
            copy(buffer, curLine);
          } else {
            throw new UnsupportedOperationException("Unsupported format for this image");
          }
          break;
        case 6: 
          if (fmt == ABGR) {
            copyRGBAtoABGR(buffer, curLine);
          } else if (fmt == RGBA) {
            copy(buffer, curLine);
          } else if (fmt == BGRA) {
            copyRGBAtoBGRA(buffer, curLine);
          } else if (fmt == RGB) {
            copyRGBAtoRGB(buffer, curLine);
          } else {
            throw new UnsupportedOperationException("Unsupported format for this image");
          }
          break;
        case 0: 
          if ((fmt == LUMINANCE) || (fmt == ALPHA)) {
            copy(buffer, curLine);
          } else {
            throw new UnsupportedOperationException("Unsupported format for this image");
          }
          break;
        case 4: 
          if (fmt == LUMINANCE_ALPHA) {
            copy(buffer, curLine);
          } else {
            throw new UnsupportedOperationException("Unsupported format for this image");
          }
          break;
        case 3: 
          switch (bitdepth) {
          case 8:  palLine = curLine; break;
          case 4:  expand4(curLine, palLine); break;
          case 2:  expand2(curLine, palLine); break;
          case 1:  expand1(curLine, palLine); break;
          case 3: case 5: case 6: case 7: default:  throw new UnsupportedOperationException("Unsupported bitdepth for this image");
          }
          if (fmt == ABGR) {
            copyPALtoABGR(buffer, palLine);
          } else if (fmt == RGBA) {
            copyPALtoRGBA(buffer, palLine);
          } else if (fmt == BGRA) {
            copyPALtoBGRA(buffer, palLine);
          } else {
            throw new UnsupportedOperationException("Unsupported format for this image");
          }
          break;
        case 1: case 5: default: 
          throw new UnsupportedOperationException("Not yet implemented");
        }
        
        byte[] tmp = curLine;
        curLine = prevLine;
        prevLine = tmp;
      }
    } finally {
      inflater.end();
    }
  }
  
  private void copy(ByteBuffer buffer, byte[] curLine) {
    buffer.put(curLine, 1, curLine.length - 1);
  }
  
  private void copyRGBtoABGR(ByteBuffer buffer, byte[] curLine) {
    if (transPixel != null) {
      byte tr = transPixel[1];
      byte tg = transPixel[3];
      byte tb = transPixel[5];
      int i = 1; for (int n = curLine.length; i < n; i += 3) {
        byte r = curLine[i];
        byte g = curLine[(i + 1)];
        byte b = curLine[(i + 2)];
        byte a = -1;
        if ((r == tr) && (g == tg) && (b == tb)) {
          a = 0;
        }
        buffer.put(a).put(b).put(g).put(r);
      }
    } else {
      int i = 1; for (int n = curLine.length; i < n; i += 3) {
        buffer.put((byte)-1).put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[i]);
      }
    }
  }
  
  private void copyRGBtoRGBA(ByteBuffer buffer, byte[] curLine) {
    if (transPixel != null) {
      byte tr = transPixel[1];
      byte tg = transPixel[3];
      byte tb = transPixel[5];
      int i = 1; for (int n = curLine.length; i < n; i += 3) {
        byte r = curLine[i];
        byte g = curLine[(i + 1)];
        byte b = curLine[(i + 2)];
        byte a = -1;
        if ((r == tr) && (g == tg) && (b == tb)) {
          a = 0;
        }
        buffer.put(r).put(g).put(b).put(a);
      }
    } else {
      int i = 1; for (int n = curLine.length; i < n; i += 3) {
        buffer.put(curLine[i]).put(curLine[(i + 1)]).put(curLine[(i + 2)]).put((byte)-1);
      }
    }
  }
  
  private void copyRGBtoBGRA(ByteBuffer buffer, byte[] curLine) {
    if (transPixel != null) {
      byte tr = transPixel[1];
      byte tg = transPixel[3];
      byte tb = transPixel[5];
      int i = 1; for (int n = curLine.length; i < n; i += 3) {
        byte r = curLine[i];
        byte g = curLine[(i + 1)];
        byte b = curLine[(i + 2)];
        byte a = -1;
        if ((r == tr) && (g == tg) && (b == tb)) {
          a = 0;
        }
        buffer.put(b).put(g).put(r).put(a);
      }
    } else {
      int i = 1; for (int n = curLine.length; i < n; i += 3) {
        buffer.put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[i]).put((byte)-1);
      }
    }
  }
  
  private void copyRGBAtoABGR(ByteBuffer buffer, byte[] curLine) {
    int i = 1; for (int n = curLine.length; i < n; i += 4) {
      buffer.put(curLine[(i + 3)]).put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[i]);
    }
  }
  
  private void copyRGBAtoBGRA(ByteBuffer buffer, byte[] curLine) {
    int i = 1; for (int n = curLine.length; i < n; i += 4) {
      buffer.put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[(i + 0)]).put(curLine[(i + 3)]);
    }
  }
  
  private void copyRGBAtoRGB(ByteBuffer buffer, byte[] curLine) {
    int i = 1; for (int n = curLine.length; i < n; i += 4) {
      buffer.put(curLine[i]).put(curLine[(i + 1)]).put(curLine[(i + 2)]);
    }
  }
  
  private void copyPALtoABGR(ByteBuffer buffer, byte[] curLine) {
    if (paletteA != null) {
      int i = 1; for (int n = curLine.length; i < n; i++) {
        int idx = curLine[i] & 0xFF;
        byte r = palette[(idx * 3 + 0)];
        byte g = palette[(idx * 3 + 1)];
        byte b = palette[(idx * 3 + 2)];
        byte a = paletteA[idx];
        buffer.put(a).put(b).put(g).put(r);
      }
    } else {
      int i = 1; for (int n = curLine.length; i < n; i++) {
        int idx = curLine[i] & 0xFF;
        byte r = palette[(idx * 3 + 0)];
        byte g = palette[(idx * 3 + 1)];
        byte b = palette[(idx * 3 + 2)];
        byte a = -1;
        buffer.put(a).put(b).put(g).put(r);
      }
    }
  }
  
  private void copyPALtoRGBA(ByteBuffer buffer, byte[] curLine) {
    if (paletteA != null) {
      int i = 1; for (int n = curLine.length; i < n; i++) {
        int idx = curLine[i] & 0xFF;
        byte r = palette[(idx * 3 + 0)];
        byte g = palette[(idx * 3 + 1)];
        byte b = palette[(idx * 3 + 2)];
        byte a = paletteA[idx];
        buffer.put(r).put(g).put(b).put(a);
      }
    } else {
      int i = 1; for (int n = curLine.length; i < n; i++) {
        int idx = curLine[i] & 0xFF;
        byte r = palette[(idx * 3 + 0)];
        byte g = palette[(idx * 3 + 1)];
        byte b = palette[(idx * 3 + 2)];
        byte a = -1;
        buffer.put(r).put(g).put(b).put(a);
      }
    }
  }
  
  private void copyPALtoBGRA(ByteBuffer buffer, byte[] curLine) {
    if (paletteA != null) {
      int i = 1; for (int n = curLine.length; i < n; i++) {
        int idx = curLine[i] & 0xFF;
        byte r = palette[(idx * 3 + 0)];
        byte g = palette[(idx * 3 + 1)];
        byte b = palette[(idx * 3 + 2)];
        byte a = paletteA[idx];
        buffer.put(b).put(g).put(r).put(a);
      }
    } else {
      int i = 1; for (int n = curLine.length; i < n; i++) {
        int idx = curLine[i] & 0xFF;
        byte r = palette[(idx * 3 + 0)];
        byte g = palette[(idx * 3 + 1)];
        byte b = palette[(idx * 3 + 2)];
        byte a = -1;
        buffer.put(b).put(g).put(r).put(a);
      }
    }
  }
  
  private void expand4(byte[] src, byte[] dst) {
    int i = 1; for (int n = dst.length; i < n; i += 2) {
      int val = src[(1 + (i >> 1))] & 0xFF;
      switch (n - i) {
      default:  dst[(i + 1)] = ((byte)(val & 0xF)); }
      dst[i] = ((byte)(val >> 4));
    }
  }
  
  private void expand2(byte[] src, byte[] dst)
  {
    int i = 1; for (int n = dst.length; i < n; i += 4) {
      int val = src[(1 + (i >> 2))] & 0xFF;
      switch (n - i) {
      default:  dst[(i + 3)] = ((byte)(val & 0x3));
      case 3:  dst[(i + 2)] = ((byte)(val >> 2 & 0x3));
      case 2:  dst[(i + 1)] = ((byte)(val >> 4 & 0x3)); }
      dst[i] = ((byte)(val >> 6));
    }
  }
  
  private void expand1(byte[] src, byte[] dst)
  {
    int i = 1; for (int n = dst.length; i < n; i += 8) {
      int val = src[(1 + (i >> 3))] & 0xFF;
      switch (n - i) {
      default:  dst[(i + 7)] = ((byte)(val & 0x1));
      case 7:  dst[(i + 6)] = ((byte)(val >> 1 & 0x1));
      case 6:  dst[(i + 5)] = ((byte)(val >> 2 & 0x1));
      case 5:  dst[(i + 4)] = ((byte)(val >> 3 & 0x1));
      case 4:  dst[(i + 3)] = ((byte)(val >> 4 & 0x1));
      case 3:  dst[(i + 2)] = ((byte)(val >> 5 & 0x1));
      case 2:  dst[(i + 1)] = ((byte)(val >> 6 & 0x1)); }
      dst[i] = ((byte)(val >> 7));
    }
  }
  
  private void unfilter(byte[] curLine, byte[] prevLine) throws IOException
  {
    switch (curLine[0]) {
    case 0: 
      break;
    case 1: 
      unfilterSub(curLine);
      break;
    case 2: 
      unfilterUp(curLine, prevLine);
      break;
    case 3: 
      unfilterAverage(curLine, prevLine);
      break;
    case 4: 
      unfilterPaeth(curLine, prevLine);
      break;
    default: 
      throw new IOException("invalide filter type in scanline: " + curLine[0]);
    }
  }
  
  private void unfilterSub(byte[] curLine) {
    int bpp = bytesPerPixel;
    int i = bpp + 1; for (int n = curLine.length; i < n; i++) {
      int tmp18_17 = i; byte[] tmp18_16 = curLine;tmp18_16[tmp18_17] = ((byte)(tmp18_16[tmp18_17] + curLine[(i - bpp)]));
    }
  }
  
  private void unfilterUp(byte[] curLine, byte[] prevLine) {
    int bpp = bytesPerPixel;
    int i = 1; for (int n = curLine.length; i < n; i++) {
      int tmp18_16 = i; byte[] tmp18_15 = curLine;tmp18_15[tmp18_16] = ((byte)(tmp18_15[tmp18_16] + prevLine[i]));
    }
  }
  
  private void unfilterAverage(byte[] curLine, byte[] prevLine) {
    int bpp = bytesPerPixel;
    

    for (int i = 1; i <= bpp; i++) {
      int tmp14_12 = i; byte[] tmp14_11 = curLine;tmp14_11[tmp14_12] = ((byte)(tmp14_11[tmp14_12] + (byte)((prevLine[i] & 0xFF) >>> 1)));
    }
    for (int n = curLine.length; i < n; i++) {
      int tmp49_47 = i; byte[] tmp49_46 = curLine;tmp49_46[tmp49_47] = ((byte)(tmp49_46[tmp49_47] + (byte)((prevLine[i] & 0xFF) + (curLine[(i - bpp)] & 0xFF) >>> 1)));
    }
  }
  
  private void unfilterPaeth(byte[] curLine, byte[] prevLine) {
    int bpp = bytesPerPixel;
    

    for (int i = 1; i <= bpp; i++) {
      int tmp14_12 = i; byte[] tmp14_11 = curLine;tmp14_11[tmp14_12] = ((byte)(tmp14_11[tmp14_12] + prevLine[i]));
    }
    for (int n = curLine.length; i < n; i++) {
      int a = curLine[(i - bpp)] & 0xFF;
      int b = prevLine[i] & 0xFF;
      int c = prevLine[(i - bpp)] & 0xFF;
      int p = a + b - c;
      int pa = p - a; if (pa < 0) pa = -pa;
      int pb = p - b; if (pb < 0) pb = -pb;
      int pc = p - c; if (pc < 0) pc = -pc;
      if ((pa <= pb) && (pa <= pc)) {
        c = a;
      } else if (pb <= pc)
        c = b;
      int tmp169_167 = i; byte[] tmp169_166 = curLine;tmp169_166[tmp169_167] = ((byte)(tmp169_166[tmp169_167] + (byte)c));
    }
  }
  
  private void readIHDR() throws IOException {
    checkChunkLength(13);
    readChunk(buffer, 0, 13);
    width = readInt(buffer, 0);
    height = readInt(buffer, 4);
    bitdepth = (buffer[8] & 0xFF);
    colorType = (buffer[9] & 0xFF);
    
    switch (colorType) {
    case 0: 
      if (bitdepth != 8) {
        throw new IOException("Unsupported bit depth: " + bitdepth);
      }
      bytesPerPixel = 1;
      break;
    case 4: 
      if (bitdepth != 8) {
        throw new IOException("Unsupported bit depth: " + bitdepth);
      }
      bytesPerPixel = 2;
      break;
    case 2: 
      if (bitdepth != 8) {
        throw new IOException("Unsupported bit depth: " + bitdepth);
      }
      bytesPerPixel = 3;
      break;
    case 6: 
      if (bitdepth != 8) {
        throw new IOException("Unsupported bit depth: " + bitdepth);
      }
      bytesPerPixel = 4;
      break;
    case 3: 
      switch (bitdepth) {
      case 1: 
      case 2: 
      case 4: 
      case 8: 
        bytesPerPixel = 1;
        break;
      case 3: case 5: case 6: case 7: default: 
        throw new IOException("Unsupported bit depth: " + bitdepth);
      }
      break;
    case 1: case 5: default: 
      throw new IOException("unsupported color format: " + colorType);
    }
    
    if (buffer[10] != 0) {
      throw new IOException("unsupported compression method");
    }
    if (buffer[11] != 0) {
      throw new IOException("unsupported filtering method");
    }
    if (buffer[12] != 0) {
      throw new IOException("unsupported interlace method");
    }
  }
  
  private void readPLTE() throws IOException {
    int paletteEntries = chunkLength / 3;
    if ((paletteEntries < 1) || (paletteEntries > 256) || (chunkLength % 3 != 0)) {
      throw new IOException("PLTE chunk has wrong length");
    }
    palette = new byte[paletteEntries * 3];
    readChunk(palette, 0, palette.length);
  }
  
  private void readtRNS() throws IOException {
    switch (colorType) {
    case 0: 
      checkChunkLength(2);
      transPixel = new byte[2];
      readChunk(transPixel, 0, 2);
      break;
    case 2: 
      checkChunkLength(6);
      transPixel = new byte[6];
      readChunk(transPixel, 0, 6);
      break;
    case 3: 
      if (palette == null) {
        throw new IOException("tRNS chunk without PLTE chunk");
      }
      paletteA = new byte[palette.length / 3];
      Arrays.fill(paletteA, (byte)-1);
      readChunk(paletteA, 0, paletteA.length);
    }
    
  }
  
  private void closeChunk()
    throws IOException
  {
    if (chunkRemaining > 0)
    {
      skip(chunkRemaining + 4);
    } else {
      readFully(buffer, 0, 4);
      int expectedCrc = readInt(buffer, 0);
      int computedCrc = (int)crc.getValue();
      if (computedCrc != expectedCrc) {
        throw new IOException("Invalid CRC");
      }
    }
    chunkRemaining = 0;
    chunkLength = 0;
    chunkType = 0;
  }
  
  private void openChunk() throws IOException {
    readFully(buffer, 0, 8);
    chunkLength = readInt(buffer, 0);
    chunkType = readInt(buffer, 4);
    chunkRemaining = chunkLength;
    crc.reset();
    crc.update(buffer, 4, 4);
  }
  
  private void openChunk(int expected) throws IOException {
    openChunk();
    if (chunkType != expected) {
      throw new IOException("Expected chunk: " + Integer.toHexString(expected));
    }
  }
  
  private void checkChunkLength(int expected) throws IOException {
    if (chunkLength != expected) {
      throw new IOException("Chunk has wrong size");
    }
  }
  
  private int readChunk(byte[] buffer, int offset, int length) throws IOException {
    if (length > chunkRemaining) {
      length = chunkRemaining;
    }
    readFully(buffer, offset, length);
    crc.update(buffer, offset, length);
    chunkRemaining -= length;
    return length;
  }
  
  private void refillInflater(Inflater inflater) throws IOException {
    while (chunkRemaining == 0) {
      closeChunk();
      openChunk(1229209940);
    }
    int read = readChunk(buffer, 0, buffer.length);
    inflater.setInput(buffer, 0, read);
  }
  
  private void readChunkUnzip(Inflater inflater, byte[] buffer, int offset, int length) throws IOException {
    try {
      do {
        int read = inflater.inflate(buffer, offset, length);
        if (read <= 0) {
          if (inflater.finished()) {
            throw new EOFException();
          }
          if (inflater.needsInput()) {
            refillInflater(inflater);
          } else {
            throw new IOException("Can't inflate " + length + " bytes");
          }
        } else {
          offset += read;
          length -= read;
        }
      } while (
      













        length > 0);
    } catch (DataFormatException ex) {
      throw ((IOException)new IOException("inflate error").initCause(ex));
    }
  }
  
  private void readFully(byte[] buffer, int offset, int length) throws IOException {
    do {
      int read = input.read(buffer, offset, length);
      if (read < 0) {
        throw new EOFException();
      }
      offset += read;
      length -= read;
    } while (
    





      length > 0);
  }
  
  private int readInt(byte[] buffer, int offset) {
    return 
      buffer[offset] << 24 | 
      (buffer[(offset + 1)] & 0xFF) << 16 | 
      (buffer[(offset + 2)] & 0xFF) << 8 | 
      buffer[(offset + 3)] & 0xFF;
  }
  
  private void skip(long amount) throws IOException {
    while (amount > 0L) {
      long skipped = input.skip(amount);
      if (skipped < 0L) {
        throw new EOFException();
      }
      amount -= skipped;
    }
  }
  
  private static boolean checkSignature(byte[] buffer) {
    for (int i = 0; i < SIGNATURE.length; i++) {
      if (buffer[i] != SIGNATURE[i]) {
        return false;
      }
    }
    return true;
  }
}
