/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.util.math.BlockRayTraceResult;

@FunctionRegister(name="AutoTool", type=Category.Player)
public class AutoTool
extends Function {
    public final BooleanSetting silent = new BooleanSetting("\u041d\u0435\u0437\u0430\u043c\u0435\u0442\u043d\u044b\u0439", true);
    public int itemIndex = -1;
    public int oldSlot = -1;
    boolean status;
    boolean clicked;

    public AutoTool() {
        this.addSettings(this.silent);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (AutoTool.mc.player == null || AutoTool.mc.player.isCreative()) {
            this.itemIndex = -1;
            return;
        }
        if (this.isMousePressed()) {
            this.itemIndex = this.findBestToolSlotInHotBar();
            if (this.itemIndex != -1) {
                this.status = true;
                if (this.oldSlot == -1) {
                    this.oldSlot = AutoTool.mc.player.inventory.currentItem;
                }
                if (((Boolean)this.silent.get()).booleanValue()) {
                    AutoTool.mc.player.connection.sendPacket(new CHeldItemChangePacket(this.itemIndex));
                } else {
                    AutoTool.mc.player.inventory.currentItem = this.itemIndex;
                }
            }
        } else if (this.status && this.oldSlot != -1) {
            if (((Boolean)this.silent.get()).booleanValue()) {
                AutoTool.mc.player.connection.sendPacket(new CHeldItemChangePacket(this.oldSlot));
            } else {
                AutoTool.mc.player.inventory.currentItem = this.oldSlot;
            }
            this.itemIndex = this.oldSlot;
            this.status = false;
            this.oldSlot = -1;
        }
    }

    @Override
    public void onDisable() {
        this.status = false;
        this.itemIndex = -1;
        this.oldSlot = -1;
        super.onDisable();
    }

    private int findBestToolSlotInHotBar() {
        Object object = AutoTool.mc.objectMouseOver;
        if (object instanceof BlockRayTraceResult) {
            BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)object;
            object = AutoTool.mc.world.getBlockState(blockRayTraceResult.getPos()).getBlock();
            int n = -1;
            float f = 1.0f;
            for (int i = 0; i < 9; ++i) {
                float f2 = AutoTool.mc.player.inventory.getStackInSlot(i).getDestroySpeed(((Block)object).getDefaultState());
                if (!(f2 > f)) continue;
                f = f2;
                n = i;
            }
            return n;
        }
        return 1;
    }

    private boolean isMousePressed() {
        return AutoTool.mc.objectMouseOver != null && AutoTool.mc.gameSettings.keyBindAttack.isKeyDown();
    }
}

