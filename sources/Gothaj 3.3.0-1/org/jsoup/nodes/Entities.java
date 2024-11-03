package org.jsoup.nodes;

import java.io.IOException;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.HashMap;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Parser;

public class Entities {
   private static final int empty = -1;
   private static final String emptyName = "";
   static final int codepointRadix = 36;
   private static final char[] codeDelims = new char[]{',', ';'};
   private static final HashMap<String, String> multipoints = new HashMap<>();
   private static final Document.OutputSettings DefaultOutput = new Document.OutputSettings();

   private Entities() {
   }

   public static boolean isNamedEntity(String name) {
      return Entities.EscapeMode.extended.codepointForName(name) != -1;
   }

   public static boolean isBaseNamedEntity(String name) {
      return Entities.EscapeMode.base.codepointForName(name) != -1;
   }

   public static String getByName(String name) {
      String val = multipoints.get(name);
      if (val != null) {
         return val;
      } else {
         int codepoint = Entities.EscapeMode.extended.codepointForName(name);
         return codepoint != -1 ? new String(new int[]{codepoint}, 0, 1) : "";
      }
   }

   public static int codepointsForName(String name, int[] codepoints) {
      String val = multipoints.get(name);
      if (val != null) {
         codepoints[0] = val.codePointAt(0);
         codepoints[1] = val.codePointAt(1);
         return 2;
      } else {
         int codepoint = Entities.EscapeMode.extended.codepointForName(name);
         if (codepoint != -1) {
            codepoints[0] = codepoint;
            return 1;
         } else {
            return 0;
         }
      }
   }

   public static String escape(String string, Document.OutputSettings out) {
      if (string == null) {
         return "";
      } else {
         StringBuilder accum = StringUtil.borrowBuilder();

         try {
            escape(accum, string, out, false, false, false, false);
         } catch (IOException var4) {
            throw new SerializationException(var4);
         }

         return StringUtil.releaseBuilder(accum);
      }
   }

   public static String escape(String string) {
      return escape(string, DefaultOutput);
   }

   static void escape(
      Appendable accum,
      String string,
      Document.OutputSettings out,
      boolean inAttribute,
      boolean normaliseWhite,
      boolean stripLeadingWhite,
      boolean trimTrailing
   ) throws IOException {
      boolean lastWasWhite = false;
      boolean reachedNonWhite = false;
      Entities.EscapeMode escapeMode = out.escapeMode();
      CharsetEncoder encoder = out.encoder();
      Entities.CoreCharset coreCharset = out.coreCharset;
      int length = string.length();
      boolean skipped = false;
      int offset = 0;

      while (offset < length) {
         int codePoint;
         label74: {
            codePoint = string.codePointAt(offset);
            if (normaliseWhite) {
               if (StringUtil.isWhitespace(codePoint)) {
                  if ((!stripLeadingWhite || reachedNonWhite) && !lastWasWhite) {
                     if (trimTrailing) {
                        skipped = true;
                     } else {
                        accum.append(' ');
                        lastWasWhite = true;
                     }
                  }
                  break label74;
               }

               lastWasWhite = false;
               reachedNonWhite = true;
               if (skipped) {
                  accum.append(' ');
                  skipped = false;
               }
            }

            if (codePoint < 65536) {
               char c = (char)codePoint;
               switch (c) {
                  case '\t':
                  case '\n':
                  case '\r':
                     accum.append(c);
                     break;
                  case '"':
                     if (inAttribute) {
                        accum.append("&quot;");
                     } else {
                        accum.append(c);
                     }
                     break;
                  case '&':
                     accum.append("&amp;");
                     break;
                  case '<':
                     if (inAttribute && escapeMode != Entities.EscapeMode.xhtml && out.syntax() != Document.OutputSettings.Syntax.xml) {
                        accum.append(c);
                     } else {
                        accum.append("&lt;");
                     }
                     break;
                  case '>':
                     if (!inAttribute) {
                        accum.append("&gt;");
                     } else {
                        accum.append(c);
                     }
                     break;
                  case ' ':
                     if (escapeMode != Entities.EscapeMode.xhtml) {
                        accum.append("&nbsp;");
                     } else {
                        accum.append("&#xa0;");
                     }
                     break;
                  default:
                     if (c >= ' ' && canEncode(coreCharset, c, encoder)) {
                        accum.append(c);
                     } else {
                        appendEncoded(accum, escapeMode, codePoint);
                     }
               }
            } else {
               String c = new String(Character.toChars(codePoint));
               if (encoder.canEncode(c)) {
                  accum.append(c);
               } else {
                  appendEncoded(accum, escapeMode, codePoint);
               }
            }
         }

         offset += Character.charCount(codePoint);
      }
   }

   private static void appendEncoded(Appendable accum, Entities.EscapeMode escapeMode, int codePoint) throws IOException {
      String name = escapeMode.nameForCodepoint(codePoint);
      if (!"".equals(name)) {
         accum.append('&').append(name).append(';');
      } else {
         accum.append("&#x").append(Integer.toHexString(codePoint)).append(';');
      }
   }

   public static String unescape(String string) {
      return unescape(string, false);
   }

   static String unescape(String string, boolean strict) {
      return Parser.unescapeEntities(string, strict);
   }

   private static boolean canEncode(Entities.CoreCharset charset, char c, CharsetEncoder fallback) {
      switch (charset) {
         case ascii:
            return c < 128;
         case utf:
            return true;
         default:
            return fallback.canEncode(c);
      }
   }

   private static void load(Entities.EscapeMode e, String pointsData, int size) {
      e.nameKeys = new String[size];
      e.codeVals = new int[size];
      e.codeKeys = new int[size];
      e.nameVals = new String[size];
      int i = 0;
      CharacterReader reader = new CharacterReader(pointsData);

      try {
         for (; !reader.isEmpty(); i++) {
            String name = reader.consumeTo('=');
            reader.advance();
            int cp1 = Integer.parseInt(reader.consumeToAny(codeDelims), 36);
            char codeDelim = reader.current();
            reader.advance();
            int cp2;
            if (codeDelim == ',') {
               cp2 = Integer.parseInt(reader.consumeTo(';'), 36);
               reader.advance();
            } else {
               cp2 = -1;
            }

            String indexS = reader.consumeTo('&');
            int index = Integer.parseInt(indexS, 36);
            reader.advance();
            e.nameKeys[i] = name;
            e.codeVals[i] = cp1;
            e.codeKeys[index] = cp1;
            e.nameVals[index] = name;
            if (cp2 != -1) {
               multipoints.put(name, new String(new int[]{cp1, cp2}, 0, 2));
            }
         }

         Validate.isTrue(i == size, "Unexpected count of entities loaded");
      } finally {
         reader.close();
      }
   }

   static enum CoreCharset {
      ascii,
      utf,
      fallback;

      static Entities.CoreCharset byName(String name) {
         if (name.equals("US-ASCII")) {
            return ascii;
         } else {
            return name.startsWith("UTF-") ? utf : fallback;
         }
      }
   }

   public static enum EscapeMode {
      xhtml(EntitiesData.xmlPoints, 4),
      base(EntitiesData.basePoints, 106),
      extended(EntitiesData.fullPoints, 2125);

      private String[] nameKeys;
      private int[] codeVals;
      private int[] codeKeys;
      private String[] nameVals;

      private EscapeMode(String file, int size) {
         Entities.load(this, file, size);
      }

      int codepointForName(String name) {
         int index = Arrays.binarySearch(this.nameKeys, name);
         return index >= 0 ? this.codeVals[index] : -1;
      }

      String nameForCodepoint(int codepoint) {
         int index = Arrays.binarySearch(this.codeKeys, codepoint);
         if (index < 0) {
            return "";
         } else {
            return index < this.nameVals.length - 1 && this.codeKeys[index + 1] == codepoint ? this.nameVals[index + 1] : this.nameVals[index];
         }
      }

      private int size() {
         return this.nameKeys.length;
      }
   }
}
