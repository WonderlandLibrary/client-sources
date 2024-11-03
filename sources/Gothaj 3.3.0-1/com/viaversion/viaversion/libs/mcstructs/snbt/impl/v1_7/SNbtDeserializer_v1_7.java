package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_7;

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
import java.util.Stack;

public class SNbtDeserializer_v1_7 implements ISNbtDeserializer<Tag> {
   private static final String ARRAY_PATTERN = "\\[[-\\d|,\\s]+]";
   private static final String BYTE_PATTERN = "[-+]?[0-9]+[b|B]";
   private static final String SHORT_PATTERN = "[-+]?[0-9]+[s|S]";
   private static final String INT_PATTERN = "[-+]?[0-9]+";
   private static final String LONG_PATTERN = "[-+]?[0-9]+[l|L]";
   private static final String FLOAT_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[f|F]";
   private static final String DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[d|D]";
   private static final String SHORT_DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+";

   @Override
   public Tag deserialize(String s) throws SNbtDeserializeException {
      s = s.trim();
      int tagCount = this.getTagCount(s);
      if (tagCount != 1) {
         throw new SNbtDeserializeException("Encountered multiple top tags, only one expected");
      } else {
         Tag tag;
         if (s.startsWith("{")) {
            tag = this.parse("tag", s);
         } else {
            tag = this.parse(this.find(s, true, false), this.find(s, false, false));
         }

         return tag;
      }
   }

   @Override
   public Tag deserializeValue(String s) throws SNbtDeserializeException {
      return this.parse("tag", s);
   }

   private Tag parse(String name, String value) throws SNbtDeserializeException {
      value = value.trim();
      this.getTagCount(value);
      if (value.startsWith("{")) {
         if (!value.endsWith("}")) {
            throw new SNbtDeserializeException("Unable to locate ending bracket } for: " + value);
         } else {
            value = value.substring(1, value.length() - 1);
            CompoundTag compound = new CompoundTag();

            while (!value.isEmpty()) {
               String pair = this.findPair(value, false);
               if (!pair.isEmpty()) {
                  String subName = this.find(pair, true, false);
                  String subValue = this.find(pair, false, false);
                  compound.put(subName, this.parse(subName, subValue));
                  if (value.length() < pair.length() + 1) {
                     break;
                  }

                  char next = value.charAt(pair.length());
                  if (next != ',' && next != '{' && next != '}' && next != '[' && next != ']') {
                     throw new SNbtDeserializeException("Unexpected token '" + name + "' at: " + value.substring(pair.length()));
                  }

                  value = value.substring(pair.length() + 1);
               }
            }

            return compound;
         }
      } else if (!value.startsWith("[") || value.matches("\\[[-\\d|,\\s]+]")) {
         return this.parsePrimitive(value);
      } else if (!value.endsWith("]")) {
         throw new SNbtDeserializeException("Unable to locate ending bracket ] for: " + value);
      } else {
         value = value.substring(1, value.length() - 1);
         ListTag list = new ListTag();

         while (!value.isEmpty()) {
            String pair = this.findPair(value, true);
            if (!pair.isEmpty()) {
               String subNamex = this.find(pair, true, true);
               String subValuex = this.find(pair, false, true);

               try {
                  list.add(this.parse(subNamex, subValuex));
               } catch (IllegalArgumentException var8) {
               }

               if (value.length() < pair.length() + 1) {
                  break;
               }

               char next = value.charAt(pair.length());
               if (next != ',' && next != '{' && next != '}' && next != '[' && next != ']') {
                  throw new SNbtDeserializeException("Unexpected token '" + name + "' at: " + value.substring(pair.length()));
               }

               value = value.substring(pair.length() + 1);
            }
         }

         return list;
      }
   }

   private Tag parsePrimitive(String value) {
      try {
         if (value.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]")) {
            return new DoubleTag(Double.parseDouble(value.substring(0, value.length() - 1)));
         } else if (value.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]")) {
            return new FloatTag(Float.parseFloat(value.substring(0, value.length() - 1)));
         } else if (value.matches("[-+]?[0-9]+[b|B]")) {
            return new ByteTag(Byte.parseByte(value.substring(0, value.length() - 1)));
         } else if (value.matches("[-+]?[0-9]+[l|L]")) {
            return new LongTag(Long.parseLong(value.substring(0, value.length() - 1)));
         } else if (value.matches("[-+]?[0-9]+[s|S]")) {
            return new ShortTag(Short.parseShort(value.substring(0, value.length() - 1)));
         } else if (value.matches("[-+]?[0-9]+")) {
            return new IntTag(Integer.parseInt(value));
         } else if (value.matches("[-+]?[0-9]*\\.?[0-9]+")) {
            return new DoubleTag(Double.parseDouble(value));
         } else if (value.equalsIgnoreCase("false")) {
            return new ByteTag((byte)0);
         } else if (value.equalsIgnoreCase("true")) {
            return new ByteTag((byte)1);
         } else if (value.startsWith("[") && value.endsWith("]")) {
            if (value.length() > 2) {
               String arrayContent = value.substring(1, value.length() - 1);
               String[] parts = arrayContent.split(",");

               try {
                  if (parts.length <= 1) {
                     return new IntArrayTag(new int[]{Integer.parseInt(arrayContent.trim())});
                  } else {
                     int[] ints = new int[parts.length];

                     for (int i = 0; i < parts.length; i++) {
                        ints[i] = Integer.parseInt(parts[i].trim());
                     }

                     return new IntArrayTag(ints);
                  }
               } catch (NumberFormatException var6) {
                  return new StringTag(value);
               }
            } else {
               return new IntArrayTag(new int[0]);
            }
         } else {
            if (value.startsWith("\"") && value.endsWith("\"") && value.length() > 2) {
               value = value.substring(1, value.length() - 1);
            }

            return new StringTag(value.replace("\\\"", "\""));
         }
      } catch (NumberFormatException var7) {
         return new StringTag(value.replace("\\\"", "\""));
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
            if (i > 0 && chars[i - 1] == '\\') {
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
      if (separatorIndex < 0 && !isArray) {
         throw new SNbtDeserializeException("Unable to locate name/value for string: " + s);
      } else {
         int pairSeparator = this.getCharIndex(s, ',');
         if (pairSeparator >= 0 && pairSeparator < separatorIndex && !isArray) {
            throw new SNbtDeserializeException("Name error at: " + s);
         } else {
            if (isArray && (separatorIndex < 0 || separatorIndex > pairSeparator)) {
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
                  if (i > 0 && chars[i - 1] == '\\') {
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

      int separatorIndex = s.indexOf(":");
      if (separatorIndex < 0) {
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
      boolean quoted = false;
      char[] chars = s.toCharArray();

      for (int i = 0; i < chars.length; i++) {
         char c = chars[i];
         if (c == '"') {
            if (i <= 0 || chars[i - 1] != '\\') {
               quoted = !quoted;
            }
         } else if (!quoted) {
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
