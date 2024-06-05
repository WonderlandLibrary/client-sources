package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.AntiBot;
import Ascii4UwUWareClient.Util.Math.RotationUtil;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Radar extends Module {
    double[] vector;
    float dist;
    private final Option<Boolean> players = new Option<Boolean>("Players", "Players", true);
    private final Option<Boolean> animals = new Option<Boolean>("Animals", "Animals", false);
    private final Option<Boolean> friendlies = new Option<Boolean>("Passives", "Passives", false);
    private final Option<Boolean> monsters = new Option<Boolean>("Mobs", "Mobs", false);
    private final Option<Boolean> items = new Option<Boolean>("Items", "Items", false);
    private final Option<Boolean> invisibles = new Option<Boolean>("Invisibles", "Invisibles", false);

    public Radar() {
        super("Radar", new String[]{"rar"}, ModuleType.Render);
        this.addValues(players, animals, friendlies, monsters, items, invisibles);
    }

    @EventHandler
    public void onRender2D(EventRender2D event) {
        if (!mc.gameSettings.showDebugInfo) {
            GL11.glPushMatrix();

            int x = 2;
            int y = 17;
            int width = 66;
            int height = 66;
            float cx = x + (width / 2f);
            float cy = y + (height / 2f);

            RenderUtil.drawBorderedRect(x, y, x + width, y + height, 1, new Color(0, 0, 0, 130).getRGB(), new Color(0, 0, 0, 130).getRGB());
            RenderUtil.drawRectSized(x + (width / 2f) - 0.5f, y, 1, height, new Color(0, 0, 0, 130).getRGB());
            RenderUtil.drawRectSized(x, y + (height / 2f) - 0.5f, width, 1, new Color(0, 0, 0, 130).getRGB());
            RenderUtil.drawRectSized(cx - 1, cy - 1, 2, 2, new Color( 229, 18, 143 ).getRGB());

            int maxDist = 66 / 2;
            for (Entity entity : Minecraft.theWorld.loadedEntityList) {
                if (isValid(entity)) {
                    // X difference
                    double dx = RenderUtil.lerp(entity.prevPosX, entity.posX, event.getPartialTicks())
                            - RenderUtil.lerp(Minecraft.thePlayer.prevPosX, Minecraft.thePlayer.posX,
                            event.getPartialTicks());
                    // Z difference
                    double dz = RenderUtil.lerp(entity.prevPosZ, entity.posZ, event.getPartialTicks())
                            - RenderUtil.lerp(Minecraft.thePlayer.prevPosZ, Minecraft.thePlayer.posZ,
                            event.getPartialTicks());

                    // Make sure they're within the available rendering range
                    if ((dx * dx + dz * dz) <= (maxDist * maxDist)) {
                        dist = MathHelper.sqrt_double(dx * dx + dz * dz);
                        vector = getLookVector(
                                RotationUtil.getRotations(entity)[0] - (float) RenderUtil.lerp(Minecraft.thePlayer.prevRotationYawHead, Minecraft.thePlayer.rotationYawHead, event.getPartialTicks()));
                        if (entity instanceof EntityMob) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(252, 101, 0).getRGB());
                        } else if (entity instanceof EntityPlayer) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(248, 0, 0).getRGB());
                        } else if (entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityVillager || entity instanceof EntityGolem) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(42, 226, 29).getRGB());
                        } else if (entity instanceof EntityItem) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(255, 255, 255).getRGB());
                        }
                    }
                }
            }
            GL11.glPopMatrix();
        }
    }

    public double[] getLookVector(float yaw) {
        yaw *= MathHelper.deg2Rad;
        return new double[]{
                -MathHelper.sin(yaw),
                MathHelper.cos(yaw)
        };
    }

    private boolean isValid(Entity entity) {
        AntiBot ab = (AntiBot) Client.instance.getModuleManager().getModuleByClass(AntiBot.class);
        return ((entity instanceof EntityPlayer && this.players.getValue() && !ab.isServerBot((EntityPlayer) entity))
                || (entity instanceof EntityMob && this.monsters.getValue())
                || ((entity instanceof EntityAnimal || entity instanceof EntitySquid) && this.animals.getValue())
                || ((entity instanceof EntityVillager || entity instanceof EntityGolem) && this.friendlies.getValue())
                || (entity instanceof EntityItem && items.getValue()))
                && (!entity.isInvisible() || this.invisibles.getValue()) && entity != Minecraft.thePlayer;
    }
}