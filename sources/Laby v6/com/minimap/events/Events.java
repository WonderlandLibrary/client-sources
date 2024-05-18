package com.minimap.events;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import com.minimap.minimap.*;
import java.io.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import com.minimap.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.input.*;
import com.minimap.interfaces.*;
import com.minimap.*;
import com.minimap.animation.*;
import de.labystudio.modapi.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import de.labystudio.modapi.events.*;
import com.minimap.settings.*;

public class Events implements Listener
{
    public Object lastGuiOpen;
    int deathCounter;
    private static boolean askedToUpdate;
    private static Minecraft mc;
    private GuiScreen lastScreen;
    
    public Events() {
        this.deathCounter = 0;
    }
    
    public void createDeathpoint(final EntityPlayer p) {
        boolean disabled = false;
        if (Minimap.waypoints == null) {
            return;
        }
        for (final Waypoint w : Minimap.waypoints.list) {
            if (w.type == 1) {
                if (!XaeroMinimap.getSettings().getDeathpoints() || !XaeroMinimap.getSettings().getOldDeathpoints()) {
                    Minimap.waypoints.list.remove(w);
                    break;
                }
                disabled = w.disabled;
                w.type = 0;
                w.name = "gui.xaero_deathpoint_old";
                break;
            }
        }
        if (XaeroMinimap.getSettings().getDeathpoints()) {
            final Waypoint deathpoint = new Waypoint(Minimap.myFloor(p.posX), Minimap.myFloor(p.posY), Minimap.myFloor(p.posZ), "gui.xaero_deathpoint", "D", 0, 1);
            deathpoint.disabled = disabled;
            Minimap.waypoints.list.add(deathpoint);
        }
        try {
            XaeroMinimap.settings.saveWaypoints();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void renderLast(final float partialTicks) {
        if (XaeroMinimap.settings.getShowIngameWaypoints() && Minimap.waypoints != null) {
            final Entity entity = XaeroMinimap.mc.getRenderViewEntity();
            final double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
            final double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
            final double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            for (final Waypoint w : Minimap.waypoints.list) {
                if (!w.disabled && (w.type != 1 || XaeroMinimap.getSettings().getDeathpoints())) {
                    this.renderIngameWaypoint(w, 12.0, d3, d4, d5, entity, worldrenderer, tessellator);
                }
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
        }
    }
    
    protected void renderIngameWaypoint(final Waypoint w, final double radius, final double d3, final double d4, final double d5, final Entity entity, final WorldRenderer worldrenderer, final Tessellator tessellator) {
        final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        final FontRenderer fontrenderer = renderManager.getFontRenderer();
        if (fontrenderer == null) {
            return;
        }
        final float f = 1.6f;
        final float f2 = 0.01666667f * f;
        GlStateManager.pushMatrix();
        float offX = w.x - (float)d3 + 0.5f;
        float offY = w.y - (float)d4 + 1.0f;
        float offZ = w.z - (float)d5 + 0.5f;
        final double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
        float textSize = 1.0f;
        boolean background = false;
        String name = w.getName();
        boolean showDistance = false;
        if (distance > radius) {
            final float zoomer = (float)(radius / distance);
            offX *= zoomer;
            offY *= zoomer;
            offY += entity.getEyeHeight() * (1.0f - zoomer);
            offZ *= zoomer;
            if (distance > 20.0) {
                textSize = 1.6f;
                if (XaeroMinimap.getSettings().distance == 1) {
                    final float Z = (float)((offZ != 0.0f) ? offZ : 0.001);
                    float angle = (float)Math.toDegrees(Math.atan(-offX / Z));
                    if (offZ < 0.0f) {
                        if (offX < 0.0f) {
                            angle += 180.0f;
                        }
                        else {
                            angle -= 180.0f;
                        }
                    }
                    final float cameraAngle = MathHelper.wrapAngleTo180_float(entity.rotationYaw);
                    showDistance = (angle - cameraAngle > -20.0f && angle - cameraAngle < 20.0f);
                }
                else if (XaeroMinimap.getSettings().distance == 2) {
                    showDistance = true;
                }
                if (showDistance) {
                    name = GuiMisc.simpleFormat.format(distance) + "m";
                    background = true;
                }
                else {
                    name = "";
                }
            }
        }
        GlStateManager.translate(offX, offY, offZ);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-f2, -f2, f2);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        w.drawIconInWorld(worldrenderer, tessellator, fontrenderer, name, textSize, background, showDistance);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    @EventHandler
    public void onRender(final RenderOverlayEvent e) {
        if (Keyboard.isKeyDown(1)) {
            InterfaceHandler.cancel();
        }
        if (ServerFilter.getCurrentFilterType() != ServerFilter.FilterType.DISALLOWED && XaeroMinimap.allowMiniMap) {
            Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            InterfaceHandler.drawInterfaces(e.getPartialTicks());
            Animation.tick();
        }
    }
    
    @EventHandler
    public void onTick(final GameTickEvent e) {
        if (this.lastScreen != Events.mc.currentScreen) {
            this.lastScreen = Events.mc.currentScreen;
            if (this.lastScreen instanceof GuiMainMenu || this.lastScreen instanceof GuiMultiplayer) {
                XaeroMinimap.getSettings().resetServerSettings();
            }
            if (this.lastScreen instanceof GuiGameOver) {
                ++this.deathCounter;
                if ((this.deathCounter & 0x1) == 0x0) {
                    this.createDeathpoint(XaeroMinimap.mc.thePlayer);
                }
            }
        }
    }
    
    @EventHandler
    public void onJoinedServer(final JoinedServerEvent e) {
        if (!XaeroMinimap.previousServer.equals(e.getIp())) {
            XaeroMinimap.previousServer = e.getIp();
            XaeroMinimap.allowMiniMap = true;
            XaeroMinimap.allowPlayerRadar = true;
            XaeroMinimap.allowRadar = true;
        }
        final ServerFilter.FilterType filter = ServerFilter.getFilterTypeForServer(e.getIp());
        if (filter != ServerFilter.FilterType.ALLOWED) {
            Events.mc.thePlayer.addChatComponentMessage(new ChatComponentText("[Minimap] This server has a filter for the minimap: " + filter.name()));
        }
    }
    
    @EventHandler
    public void onChatReceived(final ChatReceivedEvent e) {
        final String text = e.getCleanMsg();
        if (text.contains("§c §r§5 §r§1 §r§f")) {
            String code = text.substring(text.indexOf("f") + 1);
            code = code.replaceAll("§", "").replaceAll("r", "").replaceAll(" ", "");
            XaeroMinimap.getSettings().resetServerSettings();
            XaeroMinimap.getSettings();
            ModSettings.serverSettings &= Integer.parseInt(code);
            System.out.println("Code: " + code);
        }
    }
    
    static {
        Events.mc = Minecraft.getMinecraft();
    }
}
