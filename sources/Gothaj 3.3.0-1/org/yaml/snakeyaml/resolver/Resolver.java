package org.yaml.snakeyaml.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class Resolver {
   public static final Pattern BOOL = Pattern.compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$");
   public static final Pattern FLOAT = Pattern.compile(
      "^([-+]?(?:[0-9][0-9_]*)\\.[0-9_]*(?:[eE][-+]?[0-9]+)?|[-+]?(?:[0-9][0-9_]*)(?:[eE][-+]?[0-9]+)|[-+]?\\.[0-9_]+(?:[eE][-+]?[0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$"
   );
   public static final Pattern INT = Pattern.compile(
      "^(?:[-+]?0b_*[0-1][0-1_]*|[-+]?0_*[0-7][0-7_]*|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x_*[0-9a-fA-F][0-9a-fA-F_]*|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$"
   );
   public static final Pattern MERGE = Pattern.compile("^(?:<<)$");
   public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
   public static final Pattern EMPTY = Pattern.compile("^$");
   public static final Pattern TIMESTAMP = Pattern.compile(
      "^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$"
   );
   public static final Pattern VALUE = Pattern.compile("^(?:=)$");
   public static final Pattern YAML = Pattern.compile("^(?:!|&|\\*)$");
   protected Map<Character, List<ResolverTuple>> yamlImplicitResolvers = new HashMap<>();

   protected void addImplicitResolvers() {
      this.addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO", 10);
      this.addImplicitResolver(Tag.INT, INT, "-+0123456789");
      this.addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
      this.addImplicitResolver(Tag.MERGE, MERGE, "<", 10);
      this.addImplicitResolver(Tag.NULL, NULL, "~nN\u0000", 10);
      this.addImplicitResolver(Tag.NULL, EMPTY, null, 10);
      this.addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789", 50);
      this.addImplicitResolver(Tag.YAML, YAML, "!&*", 10);
   }

   public Resolver() {
      this.addImplicitResolvers();
   }

   public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
      this.addImplicitResolver(tag, regexp, first, 1024);
   }

   public void addImplicitResolver(Tag tag, Pattern regexp, String first, int limit) {
      if (first == null) {
         List<ResolverTuple> curr = this.yamlImplicitResolvers.get(null);
         if (curr == null) {
            curr = new ArrayList<>();
            this.yamlImplicitResolvers.put(null, curr);
         }

         curr.add(new ResolverTuple(tag, regexp, limit));
      } else {
         char[] chrs = first.toCharArray();
         int i = 0;

         for (int j = chrs.length; i < j; i++) {
            Character theC = chrs[i];
            if (theC == 0) {
               theC = null;
            }

            List<ResolverTuple> curr = this.yamlImplicitResolvers.get(theC);
            if (curr == null) {
               curr = new ArrayList<>();
               this.yamlImplicitResolvers.put(theC, curr);
            }

            curr.add(new ResolverTuple(tag, regexp, limit));
         }
      }
   }

   public Tag resolve(NodeId kind, String value, boolean implicit) {
      if (kind == NodeId.scalar && implicit) {
         List<ResolverTuple> resolvers;
         if (value.length() == 0) {
            resolvers = this.yamlImplicitResolvers.get('\u0000');
         } else {
            resolvers = this.yamlImplicitResolvers.get(value.charAt(0));
         }

         if (resolvers != null) {
            for (ResolverTuple v : resolvers) {
               Tag tag = v.getTag();
               Pattern regexp = v.getRegexp();
               if (value.length() <= v.getLimit() && regexp.matcher(value).matches()) {
                  return tag;
               }
            }
         }

         if (this.yamlImplicitResolvers.containsKey(null)) {
            for (ResolverTuple vx : this.yamlImplicitResolvers.get(null)) {
               Tag tag = vx.getTag();
               Pattern regexp = vx.getRegexp();
               if (value.length() <= vx.getLimit() && regexp.matcher(value).matches()) {
                  return tag;
               }
            }
         }
      }

      switch (kind) {
         case scalar:
            return Tag.STR;
         case sequence:
            return Tag.SEQ;
         default:
            return Tag.MAP;
      }
   }
}
