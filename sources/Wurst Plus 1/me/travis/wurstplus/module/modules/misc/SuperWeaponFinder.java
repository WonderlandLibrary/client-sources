package me.travis.wurstplus.module.modules.misc;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.util.Friends;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.event.events.RenderEvent;
import me.travis.wurstplus.util.wurstplusTessellator;
import java.awt.Color;

@Module.Info(name = "32k Warner", category = Module.Category.MISC)

public class SuperWeaponFinder extends Module {

    private Setting<Boolean> renderOwn = this.register(Settings.b("Show Own", false));
    private Setting<Boolean> rainbow = this.register(Settings.b("RainbowMode", false));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
    private Setting<Float> satuation = this.register(Settings.floatBuilder("Saturation").withRange(0.0f, 1.0f).withValue(0.6f).withVisibility(o -> rainbow.getValue()).build());
    private Setting<Float> brightness = this.register(Settings.floatBuilder("Brightness").withRange(0.0f, 1.0f).withValue(0.6f).withVisibility(o -> rainbow.getValue()).build());
    private Setting<Integer> speed = this.register(Settings.integerBuilder("Speed").withRange(0, 10).withValue(2).withVisibility(o -> rainbow.getValue()).build());
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Transparency").withRange(0, 255).withValue(70).build());
    private BlockPos renderBlock;
    private Color rgbc;
    private float hue;

    @Override
    public void onUpdate() {
        if (mc.player.isDead || mc.player == null || this.isDisabled()) {
            return;
        }
        EntityPlayer target = null;
        List<EntityPlayer> entities = new ArrayList<EntityPlayer>();
        entities.addAll(mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
        for (EntityPlayer e : entities) {
            if (e.isDead || e.getHealth() <= 0.0f) continue;
            if (e.getName() == mc.player.getName() && !this.renderOwn.getValue()) continue;
            if (checkSharpness(e.getHeldItemMainhand())) {
                this.renderBlock = e.getPosition();
                target = e;
            }
        }
        if (target == null) {
            this.renderBlock = null;
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (this.renderBlock != null) {
            if (this.rainbow.getValue()) {
                this.rgbc = Color.getHSBColor(this.hue, this.satuation.getValue(), this.brightness.getValue());
                wurstplusTessellator.drawRange(this.renderBlock, rgbc.getRed(), rgbc.getGreen(), rgbc.getBlue(), this.alpha.getValue());
                if (this.hue + ( (float) speed.getValue()/200) > 1) {
                    this.hue = 0;   
                } else {
                    this.hue += ( (float) speed.getValue()/200);
                }
            } else {
                wurstplusTessellator.drawRange(this.renderBlock, this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue());
            }
        }
    }
    
    private boolean checkSharpness(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        }
        NBTTagList enchants = (NBTTagList)stack.getTagCompound().getTag("ench");
        if (enchants == null) {
            return false;
        }
        for (int i = 0; i < enchants.tagCount(); ++i) {
            NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") != 16) continue;
            int lvl = enchant.getInteger("lvl");
            if (lvl < 42) break;
            return true;
        }
        return false;
    }

    @Override
    protected void onEnable() {
        this.hue = 0f;
    }

    @Override
    protected void onDisable() {
        this.renderBlock = null;
    }

}