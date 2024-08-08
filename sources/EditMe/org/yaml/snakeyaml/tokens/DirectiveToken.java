package org.yaml.snakeyaml.tokens;

import java.util.List;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;

public final class DirectiveToken extends Token {
   private final String name;
   private final List value;

   public DirectiveToken(String var1, List var2, Mark var3, Mark var4) {
      super(var3, var4);
      this.name = var1;
      if (var2 != null && var2.size() != 2) {
         throw new YAMLException("Two strings must be provided instead of " + String.valueOf(var2.size()));
      } else {
         this.value = var2;
      }
   }

   public String getName() {
      return this.name;
   }

   public List getValue() {
      return this.value;
   }

   protected String getArguments() {
      return this.value != null ? "name=" + this.name + ", value=[" + this.value.get(0) + ", " + this.value.get(1) + "]" : "name=" + this.name;
   }

   public Token.ID getTokenId() {
      return Token.ID.Directive;
   }
}
