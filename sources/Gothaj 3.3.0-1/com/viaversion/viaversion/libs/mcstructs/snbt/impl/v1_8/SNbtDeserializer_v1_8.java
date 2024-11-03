package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_8;

import com.viaversion.viaversion.libs.mcstructs.snbt.ISNbtDeserializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Arrays;
import java.util.Stack;

public class SNbtDeserializer_v1_8 implements ISNbtDeserializer<CompoundTag> {
   private static final String ARRAY_PATTERN = "\\[[-+\\d|,\\s]+]";
   private static final String BYTE_PATTERN = "[-+]?[0-9]+[b|B]";
   private static final String SHORT_PATTERN = "[-+]?[0-9]+[s|S]";
   private static final String INT_PATTERN = "[-+]?[0-9]+";
   private static final String LONG_PATTERN = "[-+]?[0-9]+[l|L]";
   private static final String FLOAT_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[f|F]";
   private static final String DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[d|D]";
   private static final String SHORT_DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+";

   public CompoundTag deserialize(String s) throws SNbtDeserializeException {
      s = s.trim();
      if (!s.startsWith("{")) {
         throw new SNbtDeserializeException("Invalid tag encountered, expected '{' as first char.");
      } else if (this.getTagCount(s) != 1) {
         throw new SNbtDeserializeException("Encountered multiple top tags, only one expected");
      } else {
         return (CompoundTag)this.parseTag(s);
      }
   }

   @Override
   public Tag deserializeValue(String s) throws SNbtDeserializeException {
      return this.parseTag(s);
   }

   private Tag parseTag(String value) throws SNbtDeserializeException {
      value = value.trim();
      if (value.startsWith("{")) {
         value = value.substring(1, value.length() - 1);
         CompoundTag compound = new CompoundTag();

         while (value.length() > 0) {
            String pair = this.findPair(value, false);
            if (pair.length() > 0) {
               String subName = this.find(pair, true, false);
               String subValue = this.find(pair, false, false);
               compound.put(subName, this.parseTag(subValue));
            }

            if (value.length() < pair.length() + 1) {
               break;
            }

            char nextChar = value.charAt(pair.length());
            if (nextChar != ',' && nextChar != '{' && nextChar != '}' && nextChar != '[' && nextChar != ']') {
               throw new SNbtDeserializeException("Unexpected token '" + nextChar + "' at: " + value.substring(pair.length()));
            }

            value = value.substring(pair.length() + 1);
         }

         return compound;
      } else if (value.startsWith("[") && !value.matches("\\[[-+\\d|,\\s]+]")) {
         value = value.substring(1, value.length() - 1);
         ListTag list = new ListTag();

         while (value.length() > 0) {
            String pairx = this.findPair(value, true);
            if (pairx.length() > 0) {
               String subValue = this.find(pairx, false, true);

               try {
                  list.add(this.parseTag(subValue));
               } catch (IllegalArgumentException var6) {
               }
            }

            if (value.length() < pairx.length() + 1) {
               break;
            }

            char nextChar = value.charAt(pairx.length());
            if (nextChar != ',' && nextChar != '{' && nextChar != '}' && nextChar != '[' && nextChar != ']') {
               throw new SNbtDeserializeException("Unexpected token '" + nextChar + "' at: " + value.substring(pairx.length()));
            }

            value = value.substring(pairx.length() + 1);
         }

         return list;
      } else {
         return this.parsePrimitive(value);
      }
   }

   private Tag parsePrimitive(String value) {
      try {
         if (value.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]")) {
            return new DoubleTag(Double.parseDouble(value.substring(0, value.length() - 1)));
         }

         if (value.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]")) {
            return new FloatTag(Float.parseFloat(value.substring(0, value.length() - 1)));
         }

         if (value.matches("[-+]?[0-9]+[b|B]")) {
            return new ByteTag(Byte.parseByte(value.substring(0, value.length() - 1)));
         }

         if (value.matches("[-+]?[0-9]+[l|L]")) {
            return new LongTag(Long.parseLong(value.substring(0, value.length() - 1)));
         }

         if (value.matches("[-+]?[0-9]+[s|S]")) {
            return new ShortTag(Short.parseShort(value.substring(0, value.length() - 1)));
         }

         if (value.matches("[-+]?[0-9]+")) {
            return new IntTag(Integer.parseInt(value));
         }

         if (value.matches("[-+]?[0-9]*\\.?[0-9]+")) {
            return new DoubleTag(Double.parseDouble(value));
         }

         if (value.equalsIgnoreCase("false")) {
            return new ByteTag((byte)0);
         }

         if (value.equalsIgnoreCase("true")) {
            return new ByteTag((byte)1);
         }
      } catch (NumberFormatException var7) {
         return new StringTag(value.replace("\\\"", "\""));
      }

      if (value.startsWith("[") && value.endsWith("]")) {
         String arrayContent = value.substring(1, value.length() - 1);
         String[] parts = this.trimSplit(arrayContent);

         try {
            int[] ints = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
               ints[i] = Integer.parseInt(parts[i].trim());
            }

            return new IntArrayTag(ints);
         } catch (NumberFormatException var6) {
            return new StringTag(value);
         }
      } else {
         if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
         }

         value = value.replace("\\\"", "\"");
         StringBuilder out = new StringBuilder();
         char[] chars = value.toCharArray();

         for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (i < chars.length - 1 && c == '\\' && chars[i + 1] == '\\') {
               out.append("\\");
               i++;
            } else {
               out.append(c);
            }
         }

         return new StringTag(out.toString());
      }
   }

   private int getTagCount(String s) throws SNbtDeserializeException {
      Stack<Character> brackets = new Stack<>();
      boolean quoted = false;
      int count = 0;
      char[] chars = s.toCharArray();

      for (int i = 0; i < chars.length; i++) {
         char c = chars[i];
         if (c == '"') {
            if (this.isEscaped(s, i)) {
               if (!quoted) {
                  throw new SNbtDeserializeException("Illegal use of \\\": " + s);
               }
            } else {
               quoted = !quoted;
            }
         } else if (!quoted) {
            if (c != '{' && c != '[') {
               this.checkBrackets(s, c, brackets);
            } else {
               if (brackets.isEmpty()) {
                  count++;
               }

               brackets.push(c);
            }
         }
      }

      if (quoted) {
         throw new SNbtDeserializeException("Unbalanced quotation: " + s);
      } else if (!brackets.isEmpty()) {
         throw new SNbtDeserializeException("Unbalanced brackets " + this.quotesToString(brackets) + ": " + s);
      } else {
         return count == 0 && !s.isEmpty() ? 1 : count;
      }
   }

   private String findPair(String s, boolean isArray) throws SNbtDeserializeException {
      int separatorIndex = this.getCharIndex(s, ':');
      if (separatorIndex == -1 && !isArray) {
         throw new SNbtDeserializeException("Unable to locate name/value separator for string: " + s);
      } else {
         int pairSeparator = this.getCharIndex(s, ',');
         if (pairSeparator != -1 && pairSeparator < separatorIndex && !isArray) {
            throw new SNbtDeserializeException("Name error at: " + s);
         } else {
            if (isArray && (separatorIndex == -1 || separatorIndex > pairSeparator)) {
               separatorIndex = -1;
            }

            Stack<Character> brackets = new Stack<>();
            int i = separatorIndex + 1;
            int quoteEnd = 0;
            boolean quoted = false;
            boolean hasContent = false;
            boolean isString = false;

            for (char[] chars = s.toCharArray(); i < chars.length; i++) {
               char c = chars[i];
               if (c == '"') {
                  if (this.isEscaped(s, i)) {
                     if (!quoted) {
                        throw new SNbtDeserializeException("Illegal use of \\\": " + s);
                     }
                  } else {
                     quoted = !quoted;
                     if (quoted && !hasContent) {
                        isString = true;
                     }

                     if (!quoted) {
                        quoteEnd = i;
                     }
                  }
               } else if (!quoted) {
                  if (c != '{' && c != '[') {
                     this.checkBrackets(s, c, brackets);
                     if (c == ',' && brackets.isEmpty()) {
                        return s.substring(0, i);
                     }
                  } else {
                     brackets.push(c);
                  }
               }

               if (!Character.isWhitespace(c)) {
                  if (!quoted && isString && quoteEnd != i) {
                     return s.substring(0, quoteEnd + 1);
                  }

                  hasContent = true;
               }
            }

            return s.substring(0, i);
         }
      }
   }

   private String find(String s, boolean name, boolean isArray) throws SNbtDeserializeException {
      if (isArray) {
         s = s.trim();
         if (s.startsWith("{") || s.startsWith("[")) {
            return name ? "" : s;
         }
      }

      int separatorIndex = this.getCharIndex(s, ':');
      if (separatorIndex == -1) {
         if (!isArray) {
            throw new SNbtDeserializeException("Unable to locate name/value separator for string: " + s);
         } else {
            return name ? "" : s;
         }
      } else {
         return name ? s.substring(0, separatorIndex).trim() : s.substring(separatorIndex + 1).trim();
      }
   }

   private int getCharIndex(String s, char wanted) {
      boolean quoted = true;
      char[] chars = s.toCharArray();

      for (int i = 0; i < chars.length; i++) {
         char c = chars[i];
         if (c == '"') {
            if (!this.isEscaped(s, i)) {
               quoted = !quoted;
            }
         } else if (quoted) {
            if (c == wanted) {
               return i;
            }

            if (c == '{' || c == '[') {
               return -1;
            }
         }
      }

      return -1;
   }

   private String[] trimSplit(String s) {
      String[] split = s.split(",");
      String[] clean = new String[split.length];
      int index = 0;

      for (String value : split) {
         if (!value.isEmpty()) {
            clean[index++] = value;
         }
      }

      return Arrays.copyOfRange(clean, 0, index);
   }

   private boolean isEscaped(String s, int index) {
      return index > 0 && s.charAt(index - 1) == '\\' && !this.isEscaped(s, index - 1);
   }

   private void checkBrackets(String s, char close, Stack<Character> brackets) throws SNbtDeserializeException {
      if (close != '}' || !brackets.isEmpty() && brackets.pop() == '{') {
         if (close == ']' && (brackets.isEmpty() || brackets.pop() != '[')) {
            throw new SNbtDeserializeException("Unbalanced square brackets []: " + s);
         }
      } else {
         throw new SNbtDeserializeException("Unbalanced curly brackets {}: " + s);
      }
   }

   private String quotesToString(Stack<Character> quotes) {
      StringBuilder s = new StringBuilder();

      for (Character c : quotes) {
         s.append(c);
      }

      return s.toString();
   }
}
