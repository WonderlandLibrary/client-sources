package ninja.leaping.configurate.attributed;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.attributed.AttributedConfigurationNode;

public class SimpleAttributedConfigurationNode extends SimpleConfigurationNode implements AttributedConfigurationNode {
   private String tagName;
   private final Map attributes = new LinkedHashMap();
   private static acE[] g;

   public static SimpleAttributedConfigurationNode root() {
      return root("root", ConfigurationOptions.defaults());
   }

   public static SimpleAttributedConfigurationNode root(String var0) {
      return root(var0, ConfigurationOptions.defaults());
   }

   public static SimpleAttributedConfigurationNode root(String var0, ConfigurationOptions var1) {
      return new SimpleAttributedConfigurationNode(var0, (Object)null, (SimpleConfigurationNode)null, var1);
   }

   protected SimpleAttributedConfigurationNode(String var1, Object var2, SimpleConfigurationNode var3, ConfigurationOptions var4) {
      super(var2, var3, var4);
      this.setTagName(var1);
   }

   protected SimpleAttributedConfigurationNode(String var1, SimpleConfigurationNode var2, SimpleConfigurationNode var3) {
      super(var2, var3);
      this.setTagName(var1);
   }

   public String getTagName() {
      return this.tagName;
   }

   public SimpleAttributedConfigurationNode setTagName(String var1) {
      acE[] var2 = c();
      if(Strings.isNullOrEmpty(var1)) {
         throw new IllegalArgumentException("Tag name cannot be null/empty");
      } else {
         this.tagName = var1;
         return this;
      }
   }

   public SimpleAttributedConfigurationNode addAttribute(String var1, String var2) {
      acE[] var3 = c();
      if(Strings.isNullOrEmpty(var1)) {
         throw new IllegalArgumentException("Attribute name cannot be null/empty");
      } else {
         this.attributes.put(var1, var2);
         return this;
      }
   }

   public SimpleAttributedConfigurationNode removeAttribute(String var1) {
      this.attributes.remove(var1);
      return this;
   }

   public boolean hasAttributes() {
      acE[] var1 = c();
      return !this.attributes.isEmpty();
   }

   public String getAttribute(String var1) {
      return (String)this.attributes.get(var1);
   }

   public Map getAttributes() {
      return ImmutableMap.copyOf(this.attributes);
   }

   public SimpleAttributedConfigurationNode setAttributes(Map var1) {
      c();
      Iterator var3 = var1.keySet().iterator();
      if(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(Strings.isNullOrEmpty(var4)) {
            throw new IllegalArgumentException("Attribute name cannot be null/empty");
         }
      }

      this.attributes.clear();
      this.attributes.putAll(var1);
      return this;
   }

   public SimpleAttributedConfigurationNode getParent() {
      return (SimpleAttributedConfigurationNode)super.getParent();
   }

   protected SimpleAttributedConfigurationNode createNode(Object var1) {
      return new SimpleAttributedConfigurationNode("element", var1, this, this.getOptions());
   }

   public SimpleAttributedConfigurationNode setValue(Object var1) {
      acE[] var2 = c();
      if(var1 instanceof AttributedConfigurationNode) {
         AttributedConfigurationNode var3 = (AttributedConfigurationNode)var1;
         this.setTagName(var3.getTagName());
         this.setAttributes(var3.getAttributes());
      }

      return (SimpleAttributedConfigurationNode)super.setValue(var1);
   }

   public SimpleAttributedConfigurationNode mergeValuesFrom(ConfigurationNode var1) {
      acE[] var2 = c();
      if(var1 instanceof AttributedConfigurationNode) {
         AttributedConfigurationNode var3 = (AttributedConfigurationNode)var1;
         this.setTagName(var3.getTagName());
         Iterator var4 = var3.getAttributes().entrySet().iterator();
         if(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            this.addAttribute((String)var5.getKey(), (String)var5.getValue());
         }
      }

      return (SimpleAttributedConfigurationNode)super.mergeValuesFrom(var1);
   }

   public SimpleAttributedConfigurationNode getNode(Object... var1) {
      return (SimpleAttributedConfigurationNode)super.getNode(var1);
   }

   public List getChildrenList() {
      return super.getChildrenList();
   }

   public Map getChildrenMap() {
      return super.getChildrenMap();
   }

   public SimpleAttributedConfigurationNode getAppendedNode() {
      return (SimpleAttributedConfigurationNode)super.getAppendedNode();
   }

   public SimpleAttributedConfigurationNode copy() {
      return this.copy((SimpleConfigurationNode)null);
   }

   protected SimpleAttributedConfigurationNode copy(SimpleConfigurationNode var1) {
      SimpleAttributedConfigurationNode var2 = new SimpleAttributedConfigurationNode(this.tagName, var1, this);
      var2.attributes.putAll(this.attributes);
      return var2;
   }

   public boolean equals(Object var1) {
      acE[] var2 = c();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof SimpleAttributedConfigurationNode)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         SimpleAttributedConfigurationNode var3 = (SimpleAttributedConfigurationNode)var1;
         return this.tagName.equals(var3.tagName) && this.attributes.equals(var3.attributes);
      }
   }

   public int hashCode() {
      c();
      int var2 = super.hashCode();
      var2 = 31 * var2 + this.tagName.hashCode();
      var2 = 31 * var2 + this.attributes.hashCode();
      return var2;
   }

   public String toString() {
      acE[] var1 = c();
      String var10000 = "SimpleAttributedConfigurationNode{super=" + super.toString() + ", tagName=" + this.tagName + ", attributes=" + this.attributes + '}';
      if(acE.b() == null) {
         b(new acE[2]);
      }

      return var10000;
   }

   public static void b(acE[] var0) {
      g = var0;
   }

   public static acE[] c() {
      return g;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }

   static {
      b(new acE[1]);
   }
}
