package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.utils.misc.FakeClientPlayer;
import xyz.mathax.mathaxclient.utils.network.capes.Capes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static xyz.mathax.mathaxclient.MatHax.mc;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    private void onGetCapeTexture(CallbackInfoReturnable<Identifier> infoReturnable) {
        Identifier id = Capes.get((PlayerEntity) (Object) this);
        if (id != null) {
            infoReturnable.setReturnValue(id);
        }
    }

    @Inject(method = "getPlayerListEntry", at = @At("HEAD"), cancellable = true)
    private void onGetPlayerListEntry(CallbackInfoReturnable<PlayerListEntry> infoReturnable) {
        if (mc.getNetworkHandler() == null) {
            infoReturnable.setReturnValue(FakeClientPlayer.getPlayerListEntry());
        }
    }

    @Inject(method = "isSpectator", at = @At("HEAD"), cancellable = true)
    private void onIsSpectator(CallbackInfoReturnable<Boolean> infoReturnable) {
        if (mc.getNetworkHandler() == null) {
            infoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = "isCreative", at = @At("HEAD"), cancellable = true)
    private void onIsCreative(CallbackInfoReturnable<Boolean> infoReturnable) {
        if (mc.getNetworkHandler() == null) {
            infoReturnable.setReturnValue(false);
        }
    }
}
