package ninja.leaping.configurate.commented;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class SimpleCommentedConfigurationNode extends SimpleConfigurationNode implements CommentedConfigurationNode {
   private String comment = null;
   private static boolean g;

   public static SimpleCommentedConfigurationNode root() {
      return root(ConfigurationOptions.defaults());
   }

   public static SimpleCommentedConfigurationNode root(ConfigurationOptions var0) {
      return new SimpleCommentedConfigurationNode((Object)null, (SimpleConfigurationNode)null, var0);
   }

   protected SimpleCommentedConfigurationNode(Object var1, SimpleConfigurationNode var2, ConfigurationOptions var3) {
      super(var1, var2, var3);
   }

   protected SimpleCommentedConfigurationNode(SimpleConfigurationNode var1, SimpleConfigurationNode var2) {
      super(var1, var2);
   }

   public Optional getComment() {
      return Optional.ofNullable(this.comment);
   }

   public SimpleCommentedConfigurationNode setComment(String var1) {
      this.attachIfNecessary();
      this.comment = var1;
      return this;
   }

   public SimpleCommentedConfigurationNode getParent() {
      return (SimpleCommentedConfigurationNode)super.getParent();
   }

   protected SimpleCommentedConfigurationNode createNode(Object var1) {
      return new SimpleCommentedConfigurationNode(var1, this, this.getOptions());
   }

   public SimpleCommentedConfigurationNode setValue(Object var1) {
      boolean var2 = d();
      if(var1 instanceof CommentedConfigurationNode && ((CommentedConfigurationNode)var1).getComment().isPresent()) {
         this.setComment((String)((CommentedConfigurationNode)var1).getComment().get());
      }

      return (SimpleCommentedConfigurationNode)super.setValue(var1);
   }

   public SimpleCommentedConfigurationNode mergeValuesFrom(ConfigurationNode var1) {
      boolean var2 = d();
      if(var1 instanceof CommentedConfigurationNode) {
         Optional var3 = ((CommentedConfigurationNode)var1).getComment();
         if(this.comment == null && var3.isPresent()) {
            this.comment = (String)var3.get();
         }
      }

      return (SimpleCommentedConfigurationNode)super.mergeValuesFrom(var1);
   }

   public SimpleCommentedConfigurationNode getNode(Object... var1) {
      return (SimpleCommentedConfigurationNode)super.getNode(var1);
   }

   public List getChildrenList() {
      return super.getChildrenList();
   }

   public Map getChildrenMap() {
      return super.getChildrenMap();
   }

   public SimpleCommentedConfigurationNode getAppendedNode() {
      return (SimpleCommentedConfigurationNode)super.getAppendedNode();
   }

   public SimpleCommentedConfigurationNode copy() {
      return this.copy((SimpleConfigurationNode)null);
   }

   protected SimpleCommentedConfigurationNode copy(SimpleConfigurationNode var1) {
      SimpleCommentedConfigurationNode var2 = new SimpleCommentedConfigurationNode(var1, this);
      var2.comment = this.comment;
      return var2;
   }

   public boolean equals(Object var1) {
      boolean var2 = d();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof SimpleCommentedConfigurationNode)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         SimpleCommentedConfigurationNode var3 = (SimpleCommentedConfigurationNode)var1;
         return Objects.equals(this.comment, var3.comment);
      }
   }

   public int hashCode() {
      d();
      int var2 = super.hashCode();
      var2 = 31 * var2 + Objects.hashCode(this.comment);
      if(acE.b() == null) {
         b(false);
      }

      return var2;
   }

   public String toString() {
      boolean var1 = d();
      return "SimpleCommentedConfigurationNode{super=" + super.toString() + ", comment=" + this.comment + '}';
   }

   public static void b(boolean var0) {
      g = var0;
   }

   public static boolean d() {
      return g;
   }

   public static boolean a() {
      boolean var0 = d();
      return true;
   }

   static {
      b(true);
   }
}
