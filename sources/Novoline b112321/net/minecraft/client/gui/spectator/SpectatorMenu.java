package net.minecraft.client.gui.spectator;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.spectator.BaseSpectatorGroup;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu$1;
import net.minecraft.client.gui.spectator.SpectatorMenu$EndSpectatorObject;
import net.minecraft.client.gui.spectator.SpectatorMenu$MoveMenuObject;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;

public class SpectatorMenu {
   private static final ISpectatorMenuObject field_178655_b = new SpectatorMenu$EndSpectatorObject((SpectatorMenu$1)null);
   private static final ISpectatorMenuObject field_178656_c = new SpectatorMenu$MoveMenuObject(-1, true);
   private static final ISpectatorMenuObject field_178653_d = new SpectatorMenu$MoveMenuObject(1, true);
   private static final ISpectatorMenuObject field_178654_e = new SpectatorMenu$MoveMenuObject(1, false);
   public static final ISpectatorMenuObject field_178657_a = new SpectatorMenu$1();
   private final ISpectatorMenuRecipient field_178651_f;
   private final List field_178652_g = Lists.newArrayList();
   private ISpectatorMenuView field_178659_h = new BaseSpectatorGroup();
   private int field_178660_i = -1;
   private int field_178658_j;

   public SpectatorMenu(ISpectatorMenuRecipient var1) {
      this.field_178651_f = var1;
   }

   public ISpectatorMenuObject func_178643_a(int var1) {
      int var2 = var1 + this.field_178658_j * 6;
      return this.field_178658_j > 0?field_178656_c:(var1 == 7?(var2 < this.field_178659_h.func_178669_a().size()?field_178653_d:field_178654_e):(var1 == 8?field_178655_b:(var2 < this.field_178659_h.func_178669_a().size()?(ISpectatorMenuObject)Objects.firstNonNull(this.field_178659_h.func_178669_a().get(var2), field_178657_a):field_178657_a)));
   }

   public List func_178642_a() {
      ArrayList var1 = Lists.newArrayList();

      for(int var2 = 0; var2 <= 8; ++var2) {
         var1.add(this.func_178643_a(var2));
      }

      return var1;
   }

   public ISpectatorMenuObject func_178645_b() {
      return this.func_178643_a(this.field_178660_i);
   }

   public ISpectatorMenuView func_178650_c() {
      return this.field_178659_h;
   }

   public void func_178644_b(int var1) {
      ISpectatorMenuObject var2 = this.func_178643_a(var1);
      if(var2 != field_178657_a) {
         if(this.field_178660_i == var1 && var2.func_178662_A_()) {
            var2.func_178661_a(this);
         } else {
            this.field_178660_i = var1;
         }
      }

   }

   public void func_178641_d() {
      this.field_178651_f.func_175257_a(this);
   }

   public int func_178648_e() {
      return this.field_178660_i;
   }

   public void func_178647_a(ISpectatorMenuView var1) {
      this.field_178652_g.add(this.func_178646_f());
      this.field_178659_h = var1;
      this.field_178660_i = -1;
      this.field_178658_j = 0;
   }

   public SpectatorDetails func_178646_f() {
      return new SpectatorDetails(this.field_178659_h, this.func_178642_a(), this.field_178660_i);
   }

   static int access$102(SpectatorMenu var0, int var1) {
      return var0.field_178658_j = var1;
   }
}
