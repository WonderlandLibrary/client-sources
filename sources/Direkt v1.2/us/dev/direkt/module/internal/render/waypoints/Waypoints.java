package us.dev.direkt.module.internal.render.waypoints;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import us.dev.api.property.Property;
import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.file.internal.files.WaypointsFile;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.internal.core.listeners.PlayerDeathListener;
import us.dev.direkt.module.internal.render.waypoints.handler.WaypointManager;
import us.dev.direkt.module.internal.render.waypoints.handler.WaypointManager.WaypointData;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.Iterator;
import java.util.Optional;

@ModData(label = "Waypoints", aliases = {"waypoint", "way"}, category = ModCategory.RENDER)
public class Waypoints extends ToggleableModule {

    @Exposed(description = "Should your death locations be shown as waypoints")
    private Property<Boolean> deathPoints = new Property<>("Deaths", true);

	private Timer rotationTimer = new Timer(), bobbingTimer = new Timer();
    private float rotateDiamond, diamondYOffset;
    private boolean moveDiamondDown;

    public Waypoints() {
        super.addCommand(new ModuleCommand(Direkt.getInstance().getCommandManager(), getLabel(), getAliases()) {
            @Executes("add|a")
            public String addWaypoint(String label, Optional<Integer> color) {
                final String currentServer = getServer(), currentWorld = getWorld();
                if (!Direkt.getInstance().getWaypointManager().isWaypoint(label.toLowerCase(), currentWorld, currentServer)) {
                    if (!color.isPresent()) {
                        Direkt.getInstance().getWaypointManager().addWaypoint(label, currentWorld, currentServer, new BlockPos(MathHelper.floor_double(Wrapper.getPlayer().posX), MathHelper.floor_double(Wrapper.getPlayer().posY), MathHelper.floor_double(Wrapper.getPlayer().posZ)), Wrapper.getPlayer().dimension, 0x5500FF00, WaypointManager.Type.REGULAR);
                        return "Added the waypoint \247f" + label + "\2477 at X: " + MathHelper.floor_double(Wrapper.getPlayer().posX) + " Y: " + MathHelper.floor_double(Wrapper.getPlayer().posY) + " Z: " + MathHelper.floor_double(Wrapper.getPlayer().posZ);
                    } else {
                        Direkt.getInstance().getWaypointManager().addWaypoint(label, currentWorld, currentServer, new BlockPos(MathHelper.floor_double(Wrapper.getPlayer().posX), MathHelper.floor_double(Wrapper.getPlayer().posY), MathHelper.floor_double(Wrapper.getPlayer().posZ)), Wrapper.getPlayer().dimension, color.get(), WaypointManager.Type.REGULAR);
                        return "Added the waypoint \247f" + label + "\2477 at X: " + MathHelper.floor_double(Wrapper.getPlayer().posX) + " Y: " + MathHelper.floor_double(Wrapper.getPlayer().posY) + " Z: " + MathHelper.floor_double(Wrapper.getPlayer().posZ) + " with the color " + color.get();
                    }
                }
                else
                    return "\247f" + label + "\2477 is already a waypoint!";
            }

            @Executes("add|a")
            public String addWaypoint(String label, int posX, int posY, int posZ, Optional<Integer> color) {
                final String currentServer = getServer(), currentWorld = getWorld();
                if (!Direkt.getInstance().getWaypointManager().isWaypoint(label.toLowerCase(), currentWorld, currentServer)) {
                    if (!color.isPresent()) {
                        Direkt.getInstance().getWaypointManager().addWaypoint(label, currentWorld, currentServer, new BlockPos(posX, posY, posZ), Wrapper.getPlayer().dimension, 0x5500FF00, WaypointManager.Type.REGULAR);
                        return "Added the waypoint \247f" + label + "\2477 at X: " + posX + " Y: " + posY + " Z: " + posZ;
                    } else {
                        Direkt.getInstance().getWaypointManager().addWaypoint(label, currentWorld, currentServer, new BlockPos(posX, posY, posZ), Wrapper.getPlayer().dimension, color.get(), WaypointManager.Type.REGULAR);
                        return "Added the waypoint 247f" + label + "\2477 at X: " + posX + " Y: " + posY + " Z: " + posZ + " with the color " + color.get();
                    }
                }
                else
                    return "\247f" + label + "\2477 is already a waypoint!";
            }

            @Executes("del|d|remove|r")
            public String removeWaypoint(String label) {
                final String currentServer = getServer(), currentWorld = getWorld();
                boolean success = Direkt.getInstance().getWaypointManager().removeWaypoint(label.toLowerCase(), currentWorld, currentServer);
                return success ? "Removed the waypoint \247f" + label + "\2477." : "\247f " + label + " \2477is not a waypoint.";
            }

            @Executes("list|l")
            public String listWaypoints() {
                final String currentServer = getServer(), currentWorld = getWorld();
                final int[] count = new int[1];
                final StringBuilder sb = new StringBuilder("" + System.lineSeparator() + "\u00A7lWaypoints\u00A7r" + System.lineSeparator() + "\u00A78[\u00A7r");
                Direkt.getInstance().getWaypointManager().getWaypoints().stream()
                        .filter(data -> data.getServer().equals(currentServer) && data.getWorld().equals(currentWorld))
                        .peek(x -> count[0]++)
                        .forEach(data -> sb.append(data.getName()).append(" \u00A77(")
                                .append(data.getLocation().getX()).append(", ")
                                .append(data.getLocation().getY()).append(", ")
                                .append(data.getLocation().getZ())
                                .append(")\u00A78, \u00A7f"));
                if (count[0] > 0) {
                    sb.setLength(sb.length() - 4);
                    return sb.append("\u00A78]\u00A7r").toString();
                } else {
                    return "No waypoints exist for this world.";
                }
            }

            @Executes("clear|c")
            public String clearWaypoints() {
                final String currentServer = getServer(), currentWorld = getWorld();
                Direkt.getInstance().getWaypointManager().getWaypoints().removeIf(waypoint -> waypoint.getServer().equals(currentServer) && waypoint.getWorld().equals(currentWorld));
                Direkt.getInstance().getFileManager().getFile(WaypointsFile.class).save();
                return "Cleared waypoints for current world.";
            }
        });
    }

    @Listener
    protected Link<EventRender3D> onRender3D = new Link<>(event -> {
        if (PlayerDeathListener.findWorldDeathPoints().isPresent() && this.deathPoints.getValue()) {
            PlayerDeathListener.findWorldDeathPoints().get().forEach(Direkt.getInstance().getWaypointManager().getWaypoints()::add);
            PlayerDeathListener.findWorldDeathPoints().get().clear();
        }

        for (Iterator<WaypointManager.WaypointData> it = Direkt.getInstance().getWaypointManager().getWaypoints().iterator(); it.hasNext();) {
            final WaypointManager.WaypointData waypoint = it.next();
            if (waypoint.getServer().equals(!Wrapper.getMinecraft().isSingleplayer() ? Wrapper.getMinecraft().getCurrentServerData().serverIP : "SINGLEPLAYER") && waypoint.getWorld().contains(!Wrapper.getMinecraft().isSingleplayer() ? Wrapper.getWorld().getSpawnPoint().toString() : Wrapper.getMinecraft().getIntegratedServer().getWorldName()) && waypoint.getDimension() == Wrapper.getPlayer().dimension) {

                switch (waypoint.getType()) {
                    case REGULAR:
                        this.drawDiamond(waypoint.getLocation().getX() - RenderManager.renderPosX(), waypoint.getLocation().getY() - RenderManager.renderPosY(), waypoint.getLocation().getZ() - RenderManager.renderPosZ(), waypoint.getColor());
                        this.drawTag(waypoint, waypoint.getLocation().getX() - RenderManager.renderPosX(), waypoint.getLocation().getY() - RenderManager.renderPosY(), waypoint.getLocation().getZ() - RenderManager.renderPosZ());
                        break;
                    case DEATH:
                        if (this.deathPoints.getValue()) {
                            if (Wrapper.getPlayer().isEntityAlive() && waypoint.getLocation().distanceSq(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ) <= 6) {
                                it.remove();
                            }
                            this.drawDiamond(waypoint.getLocation().getX() - RenderManager.renderPosX(), waypoint.getLocation().getY() - RenderManager.renderPosY(), waypoint.getLocation().getZ() - RenderManager.renderPosZ(), waypoint.getColor());
                            this.drawTag(waypoint, waypoint.getLocation().getX() - RenderManager.renderPosX(), waypoint.getLocation().getY() - RenderManager.renderPosY(), waypoint.getLocation().getZ() - RenderManager.renderPosZ());
                        }
                        break;
                }
            }
        }

        if (this.rotationTimer.hasReach(10)){
            ++this.rotateDiamond;
            this.rotationTimer.reset();
        }
        if (this.bobbingTimer.hasReach(25 + (int)(Math.random() * 10))){
            if (this.diamondYOffset < 0.1 && !moveDiamondDown)
                this.diamondYOffset += 0.01;
            else
                moveDiamondDown = true;

            if (this.diamondYOffset > -0.1 && moveDiamondDown)
                this.diamondYOffset -= 0.01;
            else
                moveDiamondDown = false;
            this.bobbingTimer.reset();
        }
    });
    
    private void drawDiamond(double x, double y, double z, int color) {
        GL11.glPushMatrix();
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2F);

        OGLRender.glColor(0x4000FF00);
        
        GL11.glTranslated(x + 0.5, y + this.diamondYOffset, z + 0.5);
        GL11.glRotatef(rotateDiamond, 0.0f, 1.0f, 0.0f);
        GL11.glTranslated(-x, -y, -z);
        drawDiamondFill(new AxisAlignedBB(x - 0.5, y, z - 0.5, x + 0.5, y + 1.0, z + 0.5));

        OGLRender.glColor(0xFF00FF00);
        
        drawDiamondOutline(new AxisAlignedBB(x - 0.5, y, z - 0.5, x + 0.5, y + 1.0, z + 0.5));
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    private static void drawDiamondOutline(AxisAlignedBB par1AxisAlignedBB) {
        GL11.glBegin(GL11.GL_LINES);
        //Top Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 1.0, par1AxisAlignedBB.minZ + 0.5);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);

        //Top Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 1.0, par1AxisAlignedBB.minZ + 0.5);

        //Top Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 1.0, par1AxisAlignedBB.minZ + 0.5);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);

        //Top Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 1.0, par1AxisAlignedBB.minZ + 0.5);

        //Center
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);

        //Center
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);

        //Center
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);

        //Center
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);

        //Bottom Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 0.0, par1AxisAlignedBB.minZ + 0.5);

        //Bottom Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 0.0, par1AxisAlignedBB.minZ + 0.5);

        //Bottom Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.7, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.7);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 0.0, par1AxisAlignedBB.minZ + 0.5);

        //Bottom Pyramid
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.3, par1AxisAlignedBB.minY + 0.5, par1AxisAlignedBB.minZ + 0.3);
        GL11.glVertex3d(par1AxisAlignedBB.minX + 0.5, par1AxisAlignedBB.minY + 0.0, par1AxisAlignedBB.minZ + 0.5);
        GL11.glEnd();
    }

    private static void drawDiamondFill(AxisAlignedBB axisalignedbb) {
    	GL11.glPushMatrix();
    	GL11.glBegin(GL11.GL_TRIANGLE_FAN);
    	
        // outside top 1
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY + 1.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);

        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
        
        // outside top 2
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY + 1.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);

        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);

        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        
        // outside bottom 1
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY - 0.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);

        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);

        // outside bottom 2
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY - 0.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);

        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
 
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        
        // inside top 1
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY + 1.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);

        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);
  
        // inside top 2
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY + 1.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);

        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
     
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        
        // inside bottom 1
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY + 0.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);

        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
    
        // inside bottom 2
        GL11.glVertex3d(axisalignedbb.minX + 0.5, axisalignedbb.minY - 0.0, axisalignedbb.minZ + 0.5);
        GL11.glVertex3d(axisalignedbb.minX + 0.7, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);

        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.3);
        GL11.glVertex3d(axisalignedbb.minX + 0.3, axisalignedbb.minY + 0.5, axisalignedbb.minZ + 0.7);
        
        GL11.glEnd();
        GL11.glPopMatrix();
    	
    }

    private void drawTag(WaypointData wpt, double d, double d1, double d2) {
        float distance = (float) Wrapper.getDistance(Wrapper.getPlayer().posX + 0.5, Wrapper.getPlayer().posY + 0.5, Wrapper.getPlayer().posZ + 0.5, wpt.getLocation().getX() + 0.5, wpt.getLocation().getY() + 0.5, wpt.getLocation().getZ() + 0.5);
        int distanceInt = MathHelper.floor_double(Wrapper.getDistance(MathHelper.floor_double(Wrapper.getPlayer().posX) + 0.5, MathHelper.floor_double(Wrapper.getPlayer().posY) + 0.5, MathHelper.floor_double(Wrapper.getPlayer().posZ)  + 0.5, wpt.getLocation().getX() + 0.5, wpt.getLocation().getY() + 0.5, wpt.getLocation().getZ() + 0.5));
        float scaleFactor = distance < 10 ? 0.02F : distance / 360;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        double div = distance / 180;
        if(distance < 270)
        	GL11.glTranslated(d + 0.5D, d1 + 1.5D + distance * 0.03, d2 + 0.5D);
        else
        	GL11.glTranslated((d + 0.5D) / div, d1 / div + 7, (d2 + 0.5D) / div);
        GL11.glRotated(-Wrapper.getPlayer().rotationYaw, 0, 1, 0);
        GL11.glRotated(Wrapper.getPlayer().rotationPitch, 1, 0, 0);
        GL11.glRotated(180, 0, 0, 1);
        if(distance > 270)
        	GL11.glScaled(0.5, 0.5, 0.5);
        else
            GL11.glScaled(scaleFactor, scaleFactor, 0.0);
        String text = wpt.getName() + " \247r(" + (distanceInt) + "m)";
        OGLRender.drawBorderedRect(-Wrapper.getFontRenderer().getStringWidth(text) / 2 - 1, -2, Wrapper.getFontRenderer().getStringWidth(text) / 2, 9, 0x22cccccc, 0xafFFFFFF);
        Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(text, -Wrapper.getFontRenderer().getStringWidth(text) / 2, 0, 0xFFFFFFFF);
        GL11.glTranslatef(-(float) d, -(float) d1 + 1.5F, -(float) d2 + 0.1f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    public static String getServer() {
        return !Wrapper.getMinecraft().isSingleplayer() ? Wrapper.getMinecraft().getCurrentServerData().serverIP : "SINGLEPLAYER";
    }

    public static String getWorld() {
        return !Wrapper.getMinecraft().isSingleplayer() ? Wrapper.getWorld().getSpawnPoint().toString() : Wrapper.getMinecraft().getIntegratedServer().getWorldName();
    }
}
