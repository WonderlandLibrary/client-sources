package net.minecraft.client.gui.spectator;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class SpectatorMenu {
   private static final ISpectatorMenuObject field_178655_b = new SpectatorMenu.EndSpectatorObject((Object)null);
   private static final String __OBFID = "CL_00001927";
   private static final ISpectatorMenuObject field_178653_d = new SpectatorMenu.MoveMenuObject(1, true);
   private int field_178658_j;
   public static final ISpectatorMenuObject field_178657_a = new ISpectatorMenuObject() {
      private static final String __OBFID = "CL_00001926";

      public void func_178663_a(float var1, int var2) {
      }

      public IChatComponent func_178664_z_() {
         return new ChatComponentText("");
      }

      public boolean func_178662_A_() {
         return false;
      }

      public void func_178661_a(SpectatorMenu var1) {
      }
   };
   private static final ISpectatorMenuObject field_178654_e = new SpectatorMenu.MoveMenuObject(1, false);
   private static final ISpectatorMenuObject field_178656_c = new SpectatorMenu.MoveMenuObject(-1, true);
   private int field_178660_i = -1;
   private final List field_178652_g = Lists.newArrayList();
   private ISpectatorMenuView field_178659_h = new BaseSpectatorGroup();
   private final ISpectatorMenuReciepient field_178651_f;

   public void func_178647_a(ISpectatorMenuView var1) {
      this.field_178652_g.add(this.func_178646_f());
      this.field_178659_h = var1;
      this.field_178660_i = -1;
      this.field_178658_j = 0;
   }

   public int func_178648_e() {
      return this.field_178660_i;
   }

   public void func_178644_b(int var1) {
      ISpectatorMenuObject var2 = this.func_178643_a(var1);
      if (var2 != field_178657_a) {
         if (this.field_178660_i == var1 && var2.func_178662_A_()) {
            var2.func_178661_a(this);
         } else {
            this.field_178660_i = var1;
         }
      }

   }

   public ISpectatorMenuObject func_178645_b() {
      return this.func_178643_a(this.field_178660_i);
   }

   public void func_178641_d() {
      this.field_178651_f.func_175257_a(this);
   }

   public List func_178642_a() {
      ArrayList var1 = Lists.newArrayList();

      for(int var2 = 0; var2 <= 8; ++var2) {
         var1.add(this.func_178643_a(var2));
      }

      return var1;
   }

   public SpectatorDetails func_178646_f() {
      return new SpectatorDetails(this.field_178659_h, this.func_178642_a(), this.field_178660_i);
   }

   public SpectatorMenu(ISpectatorMenuReciepient var1) {
      this.field_178651_f = var1;
   }

   static void access$0(SpectatorMenu var0, int var1) {
      var0.field_178658_j = var1;
   }

   public ISpectatorMenuObject func_178643_a(int var1) {
      int var2 = var1 + this.field_178658_j * 6;
      return this.field_178658_j > 0 && var1 == 0 ? field_178656_c : (var1 == 7 ? (var2 < this.field_178659_h.func_178669_a().size() ? field_178653_d : field_178654_e) : (var1 == 8 ? field_178655_b : (var2 >= 0 && var2 < this.field_178659_h.func_178669_a().size() ? (ISpectatorMenuObject)Objects.firstNonNull(this.field_178659_h.func_178669_a().get(var2), field_178657_a) : field_178657_a)));
   }

   public ISpectatorMenuView func_178650_c() {
      return this.field_178659_h;
   }

   static class EndSpectatorObject implements ISpectatorMenuObject {
      private static final String __OBFID = "CL_00001925";

      public void func_178663_a(float var1, int var2) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
         Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
      }

      EndSpectatorObject(Object var1) {
         this();
      }

      public void func_178661_a(SpectatorMenu var1) {
         var1.func_178641_d();
      }

      private EndSpectatorObject() {
      }

      public boolean func_178662_A_() {
         return true;
      }

      public IChatComponent func_178664_z_() {
         return new ChatComponentText("Close menu");
      }
   }

   static class MoveMenuObject implements ISpectatorMenuObject {
      private static final String __OBFID = "CL_00001924";
      private final int field_178666_a;
      private final boolean field_178665_b;

      public MoveMenuObject(int var1, boolean var2) {
         this.field_178666_a = var1;
         this.field_178665_b = var2;
      }

      public void func_178661_a(SpectatorMenu var1) {
         SpectatorMenu.access$0(var1, this.field_178666_a);
      }

      public IChatComponent func_178664_z_() {
         return this.field_178666_a < 0 ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
      }

      public boolean func_178662_A_() {
         return this.field_178665_b;
      }

      public void func_178663_a(float var1, int var2) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
         if (this.field_178666_a < 0) {
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
         } else {
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
         }

      }
   }
}
