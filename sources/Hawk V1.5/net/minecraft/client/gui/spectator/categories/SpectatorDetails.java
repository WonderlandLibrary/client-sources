package net.minecraft.client.gui.spectator.categories;

import com.google.common.base.Objects;
import java.util.List;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;

public class SpectatorDetails {
   private final ISpectatorMenuView field_178684_a;
   private final List field_178682_b;
   private static final String __OBFID = "CL_00001923";
   private final int field_178683_c;

   public SpectatorDetails(ISpectatorMenuView var1, List var2, int var3) {
      this.field_178684_a = var1;
      this.field_178682_b = var2;
      this.field_178683_c = var3;
   }

   public int func_178681_b() {
      return this.field_178683_c;
   }

   public ISpectatorMenuObject func_178680_a(int var1) {
      return var1 >= 0 && var1 < this.field_178682_b.size() ? (ISpectatorMenuObject)Objects.firstNonNull(this.field_178682_b.get(var1), SpectatorMenu.field_178657_a) : SpectatorMenu.field_178657_a;
   }
}
