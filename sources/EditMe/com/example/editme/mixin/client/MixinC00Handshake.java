package com.example.editme.mixin.client;

import com.example.editme.util.module.ModuleManager;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {C00Handshake.class},
   priority = Integer.MAX_VALUE
)
public class MixinC00Handshake {
   @Shadow
   int field_149600_a;
   @Shadow
   String field_149598_b;
   @Shadow
   int field_149599_c;
   @Shadow
   EnumConnectionState field_149597_d;

   @Inject(
      method = {"writePacketData"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void writePacketData(PacketBuffer var1, CallbackInfo var2) {
      if (ModuleManager.isModuleEnabled("FakeVanilla")) {
         var2.cancel();
         var1.func_150787_b(this.field_149600_a);
         var1.func_180714_a(this.field_149598_b);
         var1.writeShort(this.field_149599_c);
         var1.func_150787_b(this.field_149597_d.func_150759_c());
      }

   }
}
