package org.json;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

public class XML {
   public static final Character LT = '<';
   public static final Character BANG = '!';
   public static final Character QUEST = '?';
   public static final Character QUOT = '"';
   public static final Character AMP = '&';
   public static final Character APOS = '\'';
   public static final String NULL_ATTR = "xsi:nil";
   public static final Character SLASH = '/';
   public static final Character EQ = '=';
   public static final Character GT = '>';

   public static Object stringToValue(String var0) {
      if (var0.equals("")) {
         return var0;
      } else if (var0.equalsIgnoreCase("true")) {
         return Boolean.TRUE;
      } else if (var0.equalsIgnoreCase("false")) {
         return Boolean.FALSE;
      } else if (var0.equalsIgnoreCase("null")) {
         return JSONObject.NULL;
      } else {
         char var1 = var0.charAt(0);
         if (var1 >= '0' && var1 <= '9' || var1 == '-') {
            try {
               if (var0.indexOf(46) <= -1 && var0.indexOf(101) <= -1 && var0.indexOf(69) <= -1 && !"-0".equals(var0)) {
                  Long var4 = Long.valueOf(var0);
                  if (var0.equals(var4.toString())) {
                     if (var4 == (long)var4.intValue()) {
                        return var4.intValue();
                     }

                     return var4;
                  }
               } else {
                  Double var2 = Double.valueOf(var0);
                  if (!var2.isInfinite() && !var2.isNaN()) {
                     return var2;
                  }
               }
            } catch (Exception var3) {
            }
         }

         return var0;
      }
   }

   public static String toString(Object var0) throws JSONException {
      return toString(var0, (String)null, XMLParserConfiguration.ORIGINAL);
   }

   private static boolean parse(XMLTokener var0, JSONObject var1, String var2, XMLParserConfiguration var3) throws JSONException {
      JSONObject var6 = null;
      Object var9 = var0.nextToken();
      String var7;
      if (var9 == BANG) {
         char var4 = var0.next();
         if (var4 == '-') {
            if (var0.next() == '-') {
               var0.skipPast("-->");
               return false;
            }

            var0.back();
         } else if (var4 == '[') {
            var9 = var0.nextToken();
            if ("CDATA".equals(var9) && var0.next() == '[') {
               var7 = var0.nextCDATA();
               if (var7.length() > 0) {
                  var1.accumulate(var3.cDataTagName, var7);
               }

               return false;
            }

            throw var0.syntaxError("Expected 'CDATA['");
         }

         int var5 = 1;

         do {
            var9 = var0.nextMeta();
            if (var9 == null) {
               throw var0.syntaxError("Missing '>' after '<!'.");
            }

            if (var9 == LT) {
               ++var5;
            } else if (var9 == GT) {
               --var5;
            }
         } while(var5 > 0);

         return false;
      } else if (var9 == QUEST) {
         var0.skipPast("?>");
         return false;
      } else if (var9 == SLASH) {
         var9 = var0.nextToken();
         if (var2 == null) {
            throw var0.syntaxError(String.valueOf((new StringBuilder()).append("Mismatched close tag ").append(var9)));
         } else if (!var9.equals(var2)) {
            throw var0.syntaxError(String.valueOf((new StringBuilder()).append("Mismatched ").append(var2).append(" and ").append(var9)));
         } else if (var0.nextToken() != GT) {
            throw var0.syntaxError("Misshaped close tag");
         } else {
            return true;
         }
      } else if (var9 instanceof Character) {
         throw var0.syntaxError("Misshaped tag");
      } else {
         String var8 = (String)var9;
         var9 = null;
         var6 = new JSONObject();
         boolean var10 = false;

         while(true) {
            while(true) {
               if (var9 == null) {
                  var9 = var0.nextToken();
               }

               if (!(var9 instanceof String)) {
                  if (var9 == SLASH) {
                     if (var0.nextToken() != GT) {
                        throw var0.syntaxError("Misshaped tag");
                     }

                     if (var10) {
                        var1.accumulate(var8, JSONObject.NULL);
                     } else if (var6.length() > 0) {
                        var1.accumulate(var8, var6);
                     } else {
                        var1.accumulate(var8, "");
                     }

                     return false;
                  }

                  if (var9 != GT) {
                     throw var0.syntaxError("Misshaped tag");
                  }

                  while(true) {
                     var9 = var0.nextContent();
                     if (var9 == null) {
                        if (var8 != null) {
                           throw var0.syntaxError(String.valueOf((new StringBuilder()).append("Unclosed tag ").append(var8)));
                        }

                        return false;
                     }

                     if (var9 instanceof String) {
                        var7 = (String)var9;
                        if (var7.length() > 0) {
                           var6.accumulate(var3.cDataTagName, var3.keepStrings ? var7 : stringToValue(var7));
                        }
                     } else if (var9 == LT && parse(var0, var6, var8, var3)) {
                        if (var6.length() == 0) {
                           var1.accumulate(var8, "");
                        } else if (var6.length() == 1 && var6.opt(var3.cDataTagName) != null) {
                           var1.accumulate(var8, var6.opt(var3.cDataTagName));
                        } else {
                           var1.accumulate(var8, var6);
                        }

                        return false;
                     }
                  }
               }

               var7 = (String)var9;
               var9 = var0.nextToken();
               if (var9 == EQ) {
                  var9 = var0.nextToken();
                  if (!(var9 instanceof String)) {
                     throw var0.syntaxError("Missing value");
                  }

                  if (var3.convertNilAttributeToNull && "xsi:nil".equals(var7) && Boolean.parseBoolean((String)var9)) {
                     var10 = true;
                  } else if (!var10) {
                     var6.accumulate(var7, var3.keepStrings ? (String)var9 : stringToValue((String)var9));
                  }

                  var9 = null;
               } else {
                  var6.accumulate(var7, "");
               }
            }
         }
      }
   }

   public static String unescape(String var0) {
      StringBuilder var1 = new StringBuilder(var0.length());
      int var2 = 0;

      for(int var3 = var0.length(); var2 < var3; ++var2) {
         char var4 = var0.charAt(var2);
         if (var4 == '&') {
            int var5 = var0.indexOf(59, var2);
            if (var5 > var2) {
               String var6 = var0.substring(var2 + 1, var5);
               var1.append(XMLTokener.unescapeEntity(var6));
               var2 += var6.length() + 1;
            } else {
               var1.append(var4);
            }
         } else {
            var1.append(var4);
         }
      }

      return String.valueOf(var1);
   }

   public static JSONObject toJSONObject(String var0, boolean var1) throws JSONException {
      return toJSONObject((Reader)(new StringReader(var0)), var1);
   }

   public static JSONObject toJSONObject(String var0) throws JSONException {
      return toJSONObject(var0, XMLParserConfiguration.ORIGINAL);
   }

   public static JSONObject toJSONObject(String var0, XMLParserConfiguration var1) throws JSONException {
      return toJSONObject((Reader)(new StringReader(var0)), var1);
   }

   public static JSONObject toJSONObject(Reader var0, boolean var1) throws JSONException {
      return var1 ? toJSONObject(var0, XMLParserConfiguration.KEEP_STRINGS) : toJSONObject(var0, XMLParserConfiguration.ORIGINAL);
   }

   private static Iterable<Integer> codePointIterator(String var0) {
      return new Iterable<Integer>(var0) {
         final String val$string;

         public Iterator<Integer> iterator() {
            return new Iterator<Integer>(this) {
               private int length;
               private int nextIndex;
               final <undefinedtype> this$0;

               public Object next() {
                  return this.next();
               }

               public Integer next() {
                  int var1 = this.this$0.val$string.codePointAt(this.nextIndex);
                  this.nextIndex += Character.charCount(var1);
                  return var1;
               }

               public boolean hasNext() {
                  return this.nextIndex < this.length;
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }

               {
                  this.this$0 = var1;
                  this.nextIndex = 0;
                  this.length = this.this$0.val$string.length();
               }
            };
         }

         {
            this.val$string = var1;
         }
      };
   }

   public static JSONObject toJSONObject(Reader var0) throws JSONException {
      return toJSONObject(var0, XMLParserConfiguration.ORIGINAL);
   }

   public static JSONObject toJSONObject(Reader var0, XMLParserConfiguration var1) throws JSONException {
      JSONObject var2 = new JSONObject();
      XMLTokener var3 = new XMLTokener(var0);

      while(var3.more()) {
         var3.skipPast("<");
         if (var3.more()) {
            parse(var3, var2, (String)null, var1);
         }
      }

      return var2;
   }

   public static void noSpace(String var0) throws JSONException {
      int var2 = var0.length();
      if (var2 == 0) {
         throw new JSONException("Empty string.");
      } else {
         for(int var1 = 0; var1 < var2; ++var1) {
            if (Character.isWhitespace(var0.charAt(var1))) {
               throw new JSONException(String.valueOf((new StringBuilder()).append("'").append(var0).append("' contains a space character.")));
            }
         }

      }
   }

   private static boolean mustEscape(int var0) {
      return Character.isISOControl(var0) && var0 != 9 && var0 != 10 && var0 != 13 || (var0 < 32 || var0 > 55295) && (var0 < 57344 || var0 > 65533) && (var0 < 65536 || var0 > 1114111);
   }

   public static String toString(Object var0, String var1) {
      return toString(var0, var1, XMLParserConfiguration.ORIGINAL);
   }

   public static String escape(String var0) {
      StringBuilder var1 = new StringBuilder(var0.length());
      Iterator var2 = codePointIterator(var0).iterator();

      while(var2.hasNext()) {
         int var3 = (Integer)var2.next();
         switch(var3) {
         case 34:
            var1.append("&quot;");
            break;
         case 38:
            var1.append("&amp;");
            break;
         case 39:
            var1.append("&apos;");
            break;
         case 60:
            var1.append("&lt;");
            break;
         case 62:
            var1.append("&gt;");
            break;
         default:
            if (mustEscape(var3)) {
               var1.append("&#x");
               var1.append(Integer.toHexString(var3));
               var1.append(';');
            } else {
               var1.appendCodePoint(var3);
            }
         }
      }

      return String.valueOf(var1);
   }

   public static String toString(Object var0, String var1, XMLParserConfiguration var2) throws JSONException {
      StringBuilder var3 = new StringBuilder();
      JSONArray var4;
      Object var9;
      if (var0 instanceof JSONObject) {
         if (var1 != null) {
            var3.append('<');
            var3.append(var1);
            var3.append('>');
         }

         JSONObject var5 = (JSONObject)var0;
         Iterator var13 = var5.keySet().iterator();

         while(true) {
            while(true) {
               while(var13.hasNext()) {
                  String var14 = (String)var13.next();
                  var9 = var5.opt(var14);
                  if (var9 == null) {
                     var9 = "";
                  } else if (var9.getClass().isArray()) {
                     var9 = new JSONArray(var9);
                  }

                  int var10;
                  int var11;
                  Object var12;
                  if (var14.equals(var2.cDataTagName)) {
                     if (var9 instanceof JSONArray) {
                        var4 = (JSONArray)var9;
                        var10 = var4.length();

                        for(var11 = 0; var11 < var10; ++var11) {
                           if (var11 > 0) {
                              var3.append('\n');
                           }

                           var12 = var4.opt(var11);
                           var3.append(escape(var12.toString()));
                        }
                     } else {
                        var3.append(escape(var9.toString()));
                     }
                  } else if (var9 instanceof JSONArray) {
                     var4 = (JSONArray)var9;
                     var10 = var4.length();

                     for(var11 = 0; var11 < var10; ++var11) {
                        var12 = var4.opt(var11);
                        if (var12 instanceof JSONArray) {
                           var3.append('<');
                           var3.append(var14);
                           var3.append('>');
                           var3.append(toString(var12, (String)null, var2));
                           var3.append("</");
                           var3.append(var14);
                           var3.append('>');
                        } else {
                           var3.append(toString(var12, var14, var2));
                        }
                     }
                  } else if ("".equals(var9)) {
                     var3.append('<');
                     var3.append(var14);
                     var3.append("/>");
                  } else {
                     var3.append(toString(var9, var14, var2));
                  }
               }

               if (var1 != null) {
                  var3.append("</");
                  var3.append(var1);
                  var3.append('>');
               }

               return String.valueOf(var3);
            }
         }
      } else if (var0 == null || !(var0 instanceof JSONArray) && !var0.getClass().isArray()) {
         String var6 = var0 == null ? "null" : escape(var0.toString());
         return var1 == null ? String.valueOf((new StringBuilder()).append("\"").append(var6).append("\"")) : (var6.length() == 0 ? String.valueOf((new StringBuilder()).append("<").append(var1).append("/>")) : String.valueOf((new StringBuilder()).append("<").append(var1).append(">").append(var6).append("</").append(var1).append(">")));
      } else {
         if (var0.getClass().isArray()) {
            var4 = new JSONArray(var0);
         } else {
            var4 = (JSONArray)var0;
         }

         int var7 = var4.length();

         for(int var8 = 0; var8 < var7; ++var8) {
            var9 = var4.opt(var8);
            var3.append(toString(var9, var1 == null ? "array" : var1, var2));
         }

         return String.valueOf(var3);
      }
   }
}
