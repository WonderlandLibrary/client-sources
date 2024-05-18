package me.Emir.Karaguc.module.render;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.Event3D;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import me.Emir.Karaguc.utils.OutlineUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;

public class ESP extends Module {
    public ESP() {
        super("ESP", 0, Category.RENDER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Box");
        //options.add("ClickBit");
        //options.add("Shader");
        options.add("Outline");
        options.add("Glow");
        Karaguc.instance.settingsManager.rSetting(new Setting("ESP Mode", this, "Box", options));
        Karaguc.instance.settingsManager.rSetting(new Setting("PlayerESP", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("ChestESP", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("SpawnerESP", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("ItemESP", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("BedESP", this, false));
    }

    @EventTarget
    public void onRender(Event3D event) {
        String mode = Karaguc.instance.settingsManager.getSettingByName("ESP Mode").getValString();
        if(mode.equalsIgnoreCase("Box")) {
        	
        	this.setDisplayName("ESP \u00a77Box");
        	
            for(Object o : mc.theWorld.loadedEntityList) {
                if(Karaguc.instance.settingsManager.getSettingByName("PlayerESP").getValBoolean()) {
                    if(!(o instanceof EntityPlayerSP)) {
                        if(o instanceof EntityPlayer) {
                            EntityPlayer en = (EntityPlayer)o;
                            if(Karaguc.instance.friendManager.hasFriend(en.getName())) {
                                drawESP(0, 1F, 1F, 1F, en);
                                continue;
                            }
                            if(mc.thePlayer.isOnSameTeam(en))
                                drawESP(.5F, 1F, .5F, 1F, en);
                            else
                                drawESP(1F, 1F, 1F, 1F, en);
                        }
                    }
                }
            }

            if(Karaguc.instance.settingsManager.getSettingByName("ChestESP").getValBoolean()) {
                for(Object o : mc.theWorld.loadedTileEntityList) {
                    if(!(o instanceof TileEntity))
                        return;
                    BlockPos pos = new BlockPos(((TileEntity)o).getPos());
                    double x = pos.getX() - mc.getRenderManager().renderPosX;
                    double y = pos.getY() - mc.getRenderManager().renderPosY;
                    double z = pos.getZ() - mc.getRenderManager().renderPosZ;
                    if (o instanceof TileEntityChest) {
                        OutlineUtils.drawOutlinedBlockESP(x, y, z, 0.4f, 1f, 0.7f, 1f, 1f);
                        OutlineUtils.drawSolidBlockESP(x, y, z, 0.4f, 1f, 0.7f, 0.2f);
                    }
                    if (o instanceof TileEntityEnderChest) {
                        OutlineUtils.drawOutlinedBlockESP(x, y, z, 1f, 0.3f, 1f, 1f, 1f);
                        OutlineUtils.drawSolidBlockESP(x, y, z, 1f, 0.3f, 1f, 0.2f);
                    }
                }
            }
        }
    }

    public void drawESP(float red, float green, float blue, float alpha, Entity entity) {
        //TODO: Add Aura colour change
        /*if(AuraUtils.isAttacking(entity)) {
            red = 1;
            green = 0;
            blue = 0;
        }*/
        double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
        double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
        double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
        red -= (double) entity.hurtResistantTime / 30D;
        green -= (double) entity.hurtResistantTime / 30D;
        blue -= (double) entity.hurtResistantTime / 30D;
        OutlineUtils.drawSolidEntityESP(xPos, yPos, zPos, entity.width / 2, entity.height, 1F, .5F, .5F, .2F);
        OutlineUtils.drawOutlinedEntityESP(xPos, yPos, zPos, entity.width / 2, entity.height, red, green, blue, alpha);
    }
}
