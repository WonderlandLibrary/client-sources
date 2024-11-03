package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TranslationComponent extends ATextComponent {
   private static final Function<String, String> NULL_TRANSLATOR = s -> null;
   private static final Pattern ARG_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
   private final String key;
   private final Object[] args;
   @Nullable
   private String fallback;
   private Function<String, String> translator = NULL_TRANSLATOR;

   public TranslationComponent(String key, List<?> args) {
      this.key = key;
      this.args = args.toArray();
   }

   public TranslationComponent(String key, Object... args) {
      this.key = key;
      this.args = args;
   }

   public String getKey() {
      return this.key;
   }

   public Object[] getArgs() {
      return this.args;
   }

   @Nullable
   public String getFallback() {
      return this.fallback;
   }

   public TranslationComponent setFallback(@Nullable String fallback) {
      this.fallback = fallback;
      return this;
   }

   public TranslationComponent setTranslator(@Nonnull Function<String, String> translator) {
      this.translator = translator;
      return this;
   }

   @Override
   public String asSingleString() {
      StringBuilder out = new StringBuilder();
      String translated = this.translator.apply(this.key);
      if (translated == null) {
         translated = this.fallback;
      }

      if (translated == null) {
         translated = this.key;
      }

      Matcher matcher = ARG_PATTERN.matcher(translated);
      int argIndex = 0;
      int start = 0;

      while (matcher.find(start)) {
         int matchStart = matcher.start();
         int matchEnd = matcher.end();
         if (matchStart > start) {
            out.append(String.format(translated.substring(start, matchStart)));
         }

         start = matchEnd;
         String argType = matcher.group(2);
         String match = translated.substring(matchStart, matchEnd);
         if (argType.equals("%") && match.equals("%%")) {
            out.append("%");
         } else {
            if (!argType.equals("s")) {
               throw new IllegalStateException("Unsupported format: '" + match + "'");
            }

            String rawIndex = matcher.group(1);
            int index;
            if (rawIndex == null) {
               index = argIndex++;
            } else {
               index = Integer.parseInt(rawIndex);
            }

            if (index < this.args.length) {
               Object arg = this.args[index];
               if (arg instanceof ATextComponent) {
                  out.append(((ATextComponent)arg).asUnformattedString());
               } else if (arg == null) {
                  out.append("null");
               } else {
                  out.append(arg);
               }
            }
         }
      }

      if (start < translated.length()) {
         out.append(String.format(translated.substring(start)));
      }

      return out.toString();
   }

   @Override
   public ATextComponent copy() {
      Object[] copyArgs = new Object[this.args.length];

      for (int i = 0; i < this.args.length; i++) {
         Object arg = this.args[i];
         if (arg instanceof ATextComponent) {
            copyArgs[i] = ((ATextComponent)arg).copy();
         } else {
            copyArgs[i] = arg;
         }
      }

      TranslationComponent copy = new TranslationComponent(this.key, copyArgs);
      copy.translator = this.translator;
      return this.putMetaCopy(copy);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TranslationComponent that = (TranslationComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings())
            && Objects.equals(this.getStyle(), that.getStyle())
            && Objects.equals(this.key, that.key)
            && Arrays.equals(this.args, that.args)
            && Objects.equals(this.translator, that.translator);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = Objects.hash(this.getSiblings(), this.getStyle(), this.key, this.translator);
      return 31 * result + Arrays.hashCode(this.args);
   }

   @Override
   public String toString() {
      return "TranslationComponent{siblings="
         + this.getSiblings()
         + ", style="
         + this.getStyle()
         + ", key='"
         + this.key
         + '\''
         + ", args="
         + Arrays.toString(this.args)
         + ", translator="
         + this.translator
         + '}';
   }
}
