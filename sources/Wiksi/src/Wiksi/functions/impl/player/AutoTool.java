//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

@FunctionRegister(
        name = "AutoTool",
        type = Category.Player
)
public class AutoTool extends Function {
    public final BooleanSetting silent = new BooleanSetting("Незаметный", true);
    public final BooleanSetting haste = new BooleanSetting("Спешка", false);
    public int itemIndex = -1;
    public int oldSlot = -1;
    boolean status;
    boolean clicked;

    public AutoTool() {
        this.addSettings(new Setting[]{this.silent, this.haste});
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player != null && !mc.player.isCreative()) {
            if (this.isMousePressed()) {
                this.itemIndex = this.findBestToolSlotInHotBar();
                if (this.itemIndex != -1) {
                    this.status = true;
                    if (this.oldSlot == -1) {
                        this.oldSlot = mc.player.inventory.currentItem;
                    }

                    if ((Boolean)this.silent.get()) {
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(this.itemIndex));
                    } else {
                        mc.player.inventory.currentItem = this.itemIndex;
                    }
                }
            } else if (this.status && this.oldSlot != -1) {
                if ((Boolean)this.silent.get()) {
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(this.oldSlot));
                } else {
                    mc.player.inventory.currentItem = this.oldSlot;
                }

                this.itemIndex = this.oldSlot;
                this.status = false;
                this.oldSlot = -1;
            }

            if ((Boolean)this.haste.get()) {
                mc.player.addPotionEffect(new EffectInstance(Effects.HASTE, 100, 1, false, false));
            }

        } else {
            this.itemIndex = -1;
        }
    }

    public void onDisable() {
        this.status = false;
        this.itemIndex = -1;
        this.oldSlot = -1;
        mc.player.removePotionEffect(Effects.HASTE);
        super.onDisable();
    }

    private int findBestToolSlotInHotBar() {
        RayTraceResult var2 = mc.objectMouseOver;
        if (var2 instanceof BlockRayTraceResult blockRayTraceResult) {
            Block block = mc.world.getBlockState(blockRayTraceResult.getPos()).getBlock();
            int bestSlot = -1;
            float bestSpeed = 1.0F;

            for(int slot = 0; slot < 9; ++slot) {
                float speed = mc.player.inventory.getStackInSlot(slot).getDestroySpeed(block.getDefaultState());
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = slot;
                }
            }

            return bestSlot;
        } else {
            return -1;
        }
    }

    private boolean isMousePressed() {
        return mc.objectMouseOver != null && mc.gameSettings.keyBindAttack.isKeyDown();
    }
}
