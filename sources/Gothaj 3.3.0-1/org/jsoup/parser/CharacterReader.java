package org.jsoup.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import javax.annotation.Nullable;
import org.jsoup.UncheckedIOException;
import org.jsoup.helper.Validate;

public final class CharacterReader {
   static final char EOF = '\uffff';
   private static final int maxStringCacheLen = 12;
   static final int maxBufferLen = 32768;
   static final int readAheadLimit = 24576;
   private static final int minReadAheadLen = 1024;
   private char[] charBuf;
   private Reader reader;
   private int bufLength;
   private int bufSplitPoint;
   private int bufPos;
   private int readerPos;
   private int bufMark = -1;
   private static final int stringCacheSize = 512;
   private String[] stringCache = new String[512];
   @Nullable
   private ArrayList<Integer> newlinePositions = null;
   private int lineNumberOffset = 1;
   private boolean readFully;
   @Nullable
   private String lastIcSeq;
   private int lastIcIndex;

   public CharacterReader(Reader input, int sz) {
      Validate.notNull(input);
      Validate.isTrue(input.markSupported());
      this.reader = input;
      this.charBuf = new char[Math.min(sz, 32768)];
      this.bufferUp();
   }

   public CharacterReader(Reader input) {
      this(input, 32768);
   }

   public CharacterReader(String input) {
      this(new StringReader(input), input.length());
   }

   public void close() {
      if (this.reader != null) {
         try {
            this.reader.close();
         } catch (IOException var5) {
         } finally {
            this.reader = null;
            this.charBuf = null;
            this.stringCache = null;
         }
      }
   }

   private void bufferUp() {
      if (!this.readFully && this.bufPos >= this.bufSplitPoint) {
         int pos;
         int offset;
         if (this.bufMark != -1) {
            pos = this.bufMark;
            offset = this.bufPos - this.bufMark;
         } else {
            pos = this.bufPos;
            offset = 0;
         }

         try {
            long skipped = this.reader.skip((long)pos);
            this.reader.mark(32768);
            int read = 0;

            while (read <= 1024) {
               int thisRead = this.reader.read(this.charBuf, read, this.charBuf.length - read);
               if (thisRead == -1) {
                  this.readFully = true;
               }

               if (thisRead <= 0) {
                  break;
               }

               read += thisRead;
            }

            this.reader.reset();
            if (read > 0) {
               Validate.isTrue(skipped == (long)pos);
               this.bufLength = read;
               this.readerPos += pos;
               this.bufPos = offset;
               if (this.bufMark != -1) {
                  this.bufMark = 0;
               }

               this.bufSplitPoint = Math.min(this.bufLength, 24576);
            }
         } catch (IOException var7) {
            throw new UncheckedIOException(var7);
         }

         this.scanBufferForNewlines();
         this.lastIcSeq = null;
      }
   }

   public int pos() {
      return this.readerPos + this.bufPos;
   }

   public void trackNewlines(boolean track) {
      if (track && this.newlinePositions == null) {
         this.newlinePositions = new ArrayList<>(409);
         this.scanBufferForNewlines();
      } else if (!track) {
         this.newlinePositions = null;
      }
   }

   public boolean isTrackNewlines() {
      return this.newlinePositions != null;
   }

   public int lineNumber() {
      return this.lineNumber(this.pos());
   }

   int lineNumber(int pos) {
      if (!this.isTrackNewlines()) {
         return 1;
      } else {
         int i = this.lineNumIndex(pos);
         return i == -1 ? this.lineNumberOffset : i + this.lineNumberOffset + 1;
      }
   }

   public int columnNumber() {
      return this.columnNumber(this.pos());
   }

   int columnNumber(int pos) {
      if (!this.isTrackNewlines()) {
         return pos + 1;
      } else {
         int i = this.lineNumIndex(pos);
         return i == -1 ? pos + 1 : pos - this.newlinePositions.get(i) + 1;
      }
   }

   String cursorPos() {
      return this.lineNumber() + ":" + this.columnNumber();
   }

   private int lineNumIndex(int pos) {
      if (!this.isTrackNewlines()) {
         return 0;
      } else {
         int i = Collections.binarySearch(this.newlinePositions, pos);
         if (i < -1) {
            i = Math.abs(i) - 2;
         }

         return i;
      }
   }

   private void scanBufferForNewlines() {
      if (this.isTrackNewlines()) {
         if (this.newlinePositions.size() > 0) {
            int index = this.lineNumIndex(this.readerPos);
            if (index == -1) {
               index = 0;
            }

            int linePos = this.newlinePositions.get(index);
            this.lineNumberOffset += index;
            this.newlinePositions.clear();
            this.newlinePositions.add(linePos);
         }

         for (int i = this.bufPos; i < this.bufLength; i++) {
            if (this.charBuf[i] == '\n') {
               this.newlinePositions.add(1 + this.readerPos + i);
            }
         }
      }
   }

   public boolean isEmpty() {
      this.bufferUp();
      return this.bufPos >= this.bufLength;
   }

   private boolean isEmptyNoBufferUp() {
      return this.bufPos >= this.bufLength;
   }

   public char current() {
      this.bufferUp();
      return this.isEmptyNoBufferUp() ? '\uffff' : this.charBuf[this.bufPos];
   }

   char consume() {
      this.bufferUp();
      char val = this.isEmptyNoBufferUp() ? '\uffff' : this.charBuf[this.bufPos];
      this.bufPos++;
      return val;
   }

   void unconsume() {
      if (this.bufPos < 1) {
         throw new UncheckedIOException(new IOException("WTF: No buffer left to unconsume."));
      } else {
         this.bufPos--;
      }
   }

   public void advance() {
      this.bufPos++;
   }

   void mark() {
      if (this.bufLength - this.bufPos < 1024) {
         this.bufSplitPoint = 0;
      }

      this.bufferUp();
      this.bufMark = this.bufPos;
   }

   void unmark() {
      this.bufMark = -1;
   }

   void rewindToMark() {
      if (this.bufMark == -1) {
         throw new UncheckedIOException(new IOException("Mark invalid"));
      } else {
         this.bufPos = this.bufMark;
         this.unmark();
      }
   }

   int nextIndexOf(char c) {
      this.bufferUp();

      for (int i = this.bufPos; i < this.bufLength; i++) {
         if (c == this.charBuf[i]) {
            return i - this.bufPos;
         }
      }

      return -1;
   }

   int nextIndexOf(CharSequence seq) {
      this.bufferUp();
      char startChar = seq.charAt(0);

      for (int offset = this.bufPos; offset < this.bufLength; offset++) {
         if (startChar != this.charBuf[offset]) {
            do {
               offset++;
            } while (offset < this.bufLength && startChar != this.charBuf[offset]);
         }

         int i = offset + 1;
         int last = i + seq.length() - 1;
         if (offset < this.bufLength && last <= this.bufLength) {
            for (int j = 1; i < last && seq.charAt(j) == this.charBuf[i]; j++) {
               i++;
            }

            if (i == last) {
               return offset - this.bufPos;
            }
         }
      }

      return -1;
   }

   public String consumeTo(char c) {
      int offset = this.nextIndexOf(c);
      if (offset != -1) {
         String consumed = cacheString(this.charBuf, this.stringCache, this.bufPos, offset);
         this.bufPos += offset;
         return consumed;
      } else {
         return this.consumeToEnd();
      }
   }

   String consumeTo(String seq) {
      int offset = this.nextIndexOf(seq);
      if (offset != -1) {
         String consumed = cacheString(this.charBuf, this.stringCache, this.bufPos, offset);
         this.bufPos += offset;
         return consumed;
      } else if (this.bufLength - this.bufPos < seq.length()) {
         return this.consumeToEnd();
      } else {
         int endPos = this.bufLength - seq.length() + 1;
         String consumed = cacheString(this.charBuf, this.stringCache, this.bufPos, endPos - this.bufPos);
         this.bufPos = endPos;
         return consumed;
      }
   }

   public String consumeToAny(char... chars) {
      this.bufferUp();
      int pos = this.bufPos;
      int start = pos;
      int remaining = this.bufLength;
      char[] val = this.charBuf;

      label27:
      for (int charLen = chars.length; pos < remaining; pos++) {
         for (int i = 0; i < charLen; i++) {
            if (val[pos] == chars[i]) {
               break label27;
            }
         }
      }

      this.bufPos = pos;
      return pos > start ? cacheString(this.charBuf, this.stringCache, start, pos - start) : "";
   }

   String consumeToAnySorted(char... chars) {
      this.bufferUp();
      int pos = this.bufPos;
      int start = pos;
      int remaining = this.bufLength;
      char[] val = this.charBuf;

      while (pos < remaining && Arrays.binarySearch(chars, val[pos]) < 0) {
         pos++;
      }

      this.bufPos = pos;
      return this.bufPos > start ? cacheString(this.charBuf, this.stringCache, start, pos - start) : "";
   }

   String consumeData() {
      int pos = this.bufPos;
      int start = pos;
      int remaining = this.bufLength;

      label20:
      for (char[] val = this.charBuf; pos < remaining; pos++) {
         switch (val[pos]) {
            case '\u0000':
            case '&':
            case '<':
               break label20;
         }
      }

      this.bufPos = pos;
      return pos > start ? cacheString(this.charBuf, this.stringCache, start, pos - start) : "";
   }

   String consumeAttributeQuoted(boolean single) {
      int pos = this.bufPos;
      int start = pos;
      int remaining = this.bufLength;
      char[] val = this.charBuf;

      label26:
      while (pos < remaining) {
         switch (val[pos]) {
            case '\u0000':
            case '&':
               break label26;
            case '\'':
               if (single) {
                  break label26;
               }
            case '"':
               if (!single) {
                  break label26;
               }
            default:
               pos++;
         }
      }

      this.bufPos = pos;
      return pos > start ? cacheString(this.charBuf, this.stringCache, start, pos - start) : "";
   }

   String consumeRawData() {
      int pos = this.bufPos;
      int start = pos;
      int remaining = this.bufLength;

      label20:
      for (char[] val = this.charBuf; pos < remaining; pos++) {
         switch (val[pos]) {
            case '\u0000':
            case '<':
               break label20;
         }
      }

      this.bufPos = pos;
      return pos > start ? cacheString(this.charBuf, this.stringCache, start, pos - start) : "";
   }

   String consumeTagName() {
      this.bufferUp();
      int pos = this.bufPos;
      int start = pos;
      int remaining = this.bufLength;

      label20:
      for (char[] val = this.charBuf; pos < remaining; pos++) {
         switch (val[pos]) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case '/':
            case '<':
            case '>':
               break label20;
         }
      }

      this.bufPos = pos;
      return pos > start ? cacheString(this.charBuf, this.stringCache, start, pos - start) : "";
   }

   String consumeToEnd() {
      this.bufferUp();
      String data = cacheString(this.charBuf, this.stringCache, this.bufPos, this.bufLength - this.bufPos);
      this.bufPos = this.bufLength;
      return data;
   }

   String consumeLetterSequence() {
      this.bufferUp();

      int start;
      for (start = this.bufPos; this.bufPos < this.bufLength; this.bufPos++) {
         char c = this.charBuf[this.bufPos];
         if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && !Character.isLetter(c)) {
            break;
         }
      }

      return cacheString(this.charBuf, this.stringCache, start, this.bufPos - start);
   }

   String consumeLetterThenDigitSequence() {
      this.bufferUp();

      int start;
      for (start = this.bufPos; this.bufPos < this.bufLength; this.bufPos++) {
         char c = this.charBuf[this.bufPos];
         if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && !Character.isLetter(c)) {
            break;
         }
      }

      while (!this.isEmptyNoBufferUp()) {
         char c = this.charBuf[this.bufPos];
         if (c < '0' || c > '9') {
            break;
         }

         this.bufPos++;
      }

      return cacheString(this.charBuf, this.stringCache, start, this.bufPos - start);
   }

   String consumeHexSequence() {
      this.bufferUp();

      int start;
      for (start = this.bufPos; this.bufPos < this.bufLength; this.bufPos++) {
         char c = this.charBuf[this.bufPos];
         if ((c < '0' || c > '9') && (c < 'A' || c > 'F') && (c < 'a' || c > 'f')) {
            break;
         }
      }

      return cacheString(this.charBuf, this.stringCache, start, this.bufPos - start);
   }

   String consumeDigitSequence() {
      this.bufferUp();

      int start;
      for (start = this.bufPos; this.bufPos < this.bufLength; this.bufPos++) {
         char c = this.charBuf[this.bufPos];
         if (c < '0' || c > '9') {
            break;
         }
      }

      return cacheString(this.charBuf, this.stringCache, start, this.bufPos - start);
   }

   boolean matches(char c) {
      return !this.isEmpty() && this.charBuf[this.bufPos] == c;
   }

   boolean matches(String seq) {
      this.bufferUp();
      int scanLength = seq.length();
      if (scanLength > this.bufLength - this.bufPos) {
         return false;
      } else {
         for (int offset = 0; offset < scanLength; offset++) {
            if (seq.charAt(offset) != this.charBuf[this.bufPos + offset]) {
               return false;
            }
         }

         return true;
      }
   }

   boolean matchesIgnoreCase(String seq) {
      this.bufferUp();
      int scanLength = seq.length();
      if (scanLength > this.bufLength - this.bufPos) {
         return false;
      } else {
         for (int offset = 0; offset < scanLength; offset++) {
            char upScan = Character.toUpperCase(seq.charAt(offset));
            char upTarget = Character.toUpperCase(this.charBuf[this.bufPos + offset]);
            if (upScan != upTarget) {
               return false;
            }
         }

         return true;
      }
   }

   boolean matchesAny(char... seq) {
      if (this.isEmpty()) {
         return false;
      } else {
         this.bufferUp();
         char c = this.charBuf[this.bufPos];

         for (char seek : seq) {
            if (seek == c) {
               return true;
            }
         }

         return false;
      }
   }

   boolean matchesAnySorted(char[] seq) {
      this.bufferUp();
      return !this.isEmpty() && Arrays.binarySearch(seq, this.charBuf[this.bufPos]) >= 0;
   }

   boolean matchesLetter() {
      if (this.isEmpty()) {
         return false;
      } else {
         char c = this.charBuf[this.bufPos];
         return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || Character.isLetter(c);
      }
   }

   boolean matchesAsciiAlpha() {
      if (this.isEmpty()) {
         return false;
      } else {
         char c = this.charBuf[this.bufPos];
         return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
      }
   }

   boolean matchesDigit() {
      if (this.isEmpty()) {
         return false;
      } else {
         char c = this.charBuf[this.bufPos];
         return c >= '0' && c <= '9';
      }
   }

   boolean matchConsume(String seq) {
      this.bufferUp();
      if (this.matches(seq)) {
         this.bufPos = this.bufPos + seq.length();
         return true;
      } else {
         return false;
      }
   }

   boolean matchConsumeIgnoreCase(String seq) {
      if (this.matchesIgnoreCase(seq)) {
         this.bufPos = this.bufPos + seq.length();
         return true;
      } else {
         return false;
      }
   }

   boolean containsIgnoreCase(String seq) {
      if (seq.equals(this.lastIcSeq)) {
         if (this.lastIcIndex == -1) {
            return false;
         }

         if (this.lastIcIndex >= this.bufPos) {
            return true;
         }
      }

      this.lastIcSeq = seq;
      String loScan = seq.toLowerCase(Locale.ENGLISH);
      int lo = this.nextIndexOf(loScan);
      if (lo > -1) {
         this.lastIcIndex = this.bufPos + lo;
         return true;
      } else {
         String hiScan = seq.toUpperCase(Locale.ENGLISH);
         int hi = this.nextIndexOf(hiScan);
         boolean found = hi > -1;
         this.lastIcIndex = found ? this.bufPos + hi : -1;
         return found;
      }
   }

   @Override
   public String toString() {
      return this.bufLength - this.bufPos < 0 ? "" : new String(this.charBuf, this.bufPos, this.bufLength - this.bufPos);
   }

   private static String cacheString(char[] charBuf, String[] stringCache, int start, int count) {
      if (count > 12) {
         return new String(charBuf, start, count);
      } else if (count < 1) {
         return "";
      } else {
         int hash = 0;

         for (int i = 0; i < count; i++) {
            hash = 31 * hash + charBuf[start + i];
         }

         int index = hash & 511;
         String cached = stringCache[index];
         if (cached != null && rangeEquals(charBuf, start, count, cached)) {
            return cached;
         } else {
            cached = new String(charBuf, start, count);
            stringCache[index] = cached;
            return cached;
         }
      }
   }

   static boolean rangeEquals(char[] charBuf, int start, int count, String cached) {
      if (count == cached.length()) {
         int i = start;
         int j = 0;

         while (count-- != 0) {
            if (charBuf[i++] != cached.charAt(j++)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean rangeEquals(int start, int count, String cached) {
      return rangeEquals(this.charBuf, start, count, cached);
   }
}
