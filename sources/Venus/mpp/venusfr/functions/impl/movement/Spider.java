/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.MouseUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;

@FunctionRegister(name="Spider", type=Category.Movement)
public class Spider
extends Function {
    public ModeSetting mode = new ModeSetting("Mode", "Grim", "Grim", "Matrix");
    private final SliderSetting spiderSpeed = new SliderSetting("Speed", 2.0f, 1.0f, 10.0f, 0.05f).setVisible(this::lambda$new$0);
    StopWatch stopWatch = new StopWatch();

    public Spider() {
        this.addSettings(this.spiderSpeed, this.mode);
    }

    @Subscribe
    private void onMotion(EventMotion eventMotion) {
        switch ((String)this.mode.get()) {
            case "Matrix": {
                if (!Spider.mc.player.collidedHorizontally) {
                    return;
                }
                long l = MathHelper.clamp(500L - ((Float)this.spiderSpeed.get()).longValue() / 2L * 100L, 0L, 500L);
                if (!this.stopWatch.isReached(l)) break;
                eventMotion.setOnGround(false);
                Spider.mc.player.setOnGround(false);
                Spider.mc.player.collidedVertically = true;
                Spider.mc.player.collidedHorizontally = true;
                Spider.mc.player.isAirBorne = true;
                Spider.mc.player.jump();
                this.stopWatch.reset();
                break;
            }
            case "Grim": {
                int n = this.getSlotInInventoryOrHotbar(false);
                if (n == -1) {
                    this.print("\u0411\u043b\u043e\u043a\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u044b!");
                    this.toggle();
                    return;
                }
                if (!Spider.mc.player.collidedHorizontally) {
                    return;
                }
                if (Spider.mc.player.isOnGround()) {
                    eventMotion.setOnGround(false);
                    Spider.mc.player.setOnGround(false);
                    Spider.mc.player.jump();
                }
                if (!(Spider.mc.player.fallDistance > 0.0f) || !(Spider.mc.player.fallDistance < 2.0f)) break;
                this.placeBlocks(eventMotion, n);
            }
        }
    }

    private void placeBlocks(EventMotion eventMotion, int n) {
        int n2 = Spider.mc.player.inventory.currentItem;
        Spider.mc.player.inventory.currentItem = n;
        eventMotion.setPitch(80.0f);
        eventMotion.setYaw(Spider.mc.player.getHorizontalFacing().getHorizontalAngle());
        BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)MouseUtil.rayTrace(4.0, eventMotion.getYaw(), eventMotion.getPitch(), Spider.mc.player);
        Spider.mc.player.swingArm(Hand.MAIN_HAND);
        Spider.mc.playerController.processRightClickBlock(Spider.mc.player, Spider.mc.world, Hand.MAIN_HAND, blockRayTraceResult);
        Spider.mc.player.inventory.currentItem = n2;
        Spider.mc.player.fallDistance = 0.0f;
    }

    public int getSlotInInventoryOrHotbar(boolean bl) {
        int n = bl ? 0 : 9;
        int n2 = bl ? 9 : 36;
        int n3 = -1;
        for (int i = n; i < n2; ++i) {
            if (Spider.mc.player.inventory.getStackInSlot(i).getItem() == Items.TORCH || !(Spider.mc.player.inventory.getStackInSlot(i).getItem() instanceof BlockItem) && Spider.mc.player.inventory.getStackInSlot(i).getItem() != Items.WATER_BUCKET) continue;
            n3 = i;
        }
        return n3;
    }

    private Boolean lambda$new$0() {
        return !this.mode.is("Grim");
    }
}

