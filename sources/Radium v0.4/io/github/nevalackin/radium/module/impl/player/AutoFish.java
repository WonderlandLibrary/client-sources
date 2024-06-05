package io.github.nevalackin.radium.module.impl.player;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.event.impl.render.Render3DEvent;
import io.github.nevalackin.radium.event.impl.world.PlaySoundEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;

import java.awt.*;

@ModuleInfo(label = "Auto Fish", category = ModuleCategory.SELF)
public final class AutoFish extends Module {

    private final Property<Boolean> drawRangeCircleProperty = new Property<>("Draw Range Circle", true);
    private final DoubleProperty validRangeProperty = new DoubleProperty("Valid Range", 8.0, 1.0, 20.0, 0.5);

    private boolean fishingRodIsCast;

    private boolean castNextTick;

    @Override
    public void onEnable() {
        fishingRodIsCast = false;
        castNextTick = false;
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            if (!isHoldingFishingRod()) {
                fishingRodIsCast = false;
                castNextTick = false;
                return;
            }

            if (castNextTick) {
                useHeldItem();
                castNextTick = false;
            }
        }
    }

    @Listener
    public void onRender3DEvent(Render3DEvent event) {
        if (drawRangeCircleProperty.getValue())
            RenderingUtils.drawLinesAroundPlayer(Wrapper.getPlayer(), validRangeProperty.getValue(), event.getPartialTicks(), 90, 1.0F, Color.WHITE.getRGB());
    }

    @Listener
    public void onPlaySoundEvent(PlaySoundEvent event) {
        if (event.getSoundName().equals("game.player.swim.splash") && isHoldingFishingRod()) {
            if (fishingRodIsCast) {
                fishingRodIsCast = false;
                castNextTick = true;
                useHeldItem();
            } else
                fishingRodIsCast = true;
        }
    }

    private void useHeldItem() {
        Wrapper.getPlayerController().sendUseItem(Wrapper.getPlayer(), Wrapper.getWorld(), Wrapper.getPlayer().getCurrentEquippedItem());
    }

    private boolean isHoldingFishingRod() {
        ItemStack heldItem = Wrapper.getPlayer().getCurrentEquippedItem();
        return heldItem != null && heldItem.getItem() instanceof ItemFishingRod;
    }
}
