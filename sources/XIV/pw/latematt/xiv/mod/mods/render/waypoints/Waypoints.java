package pw.latematt.xiv.mod.mods.render.waypoints;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.AddWeatherEvent;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.PlayerDeathEvent;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.mod.mods.render.waypoints.base.Waypoint;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.EntityUtils;
import pw.latematt.xiv.utils.RenderUtils;
import pw.latematt.xiv.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author Matthew
 */
public class Waypoints extends Mod implements CommandHandler {
    private final float[] color = new float[]{0.89F, 0.15F, 0.21F};
    private final List<Waypoint> points;
    private final Value<Boolean> lightningPoints = new Value<>("waypoints_lightning_points", false);
    private final Value<Boolean> deathPoints = new Value<>("waypoints_death_points", false);
    private final Value<Boolean> boxes = new Value<>("waypoints_boxes", true);
    private final Value<Boolean> tracerLines = new Value<>("waypoints_tracer_lines", true);
    private final Value<Boolean> nametags = new Value<>("waypoints_nametags", true);
    private final Listener render3DListener, motionUpdateListener, addWeatherListener, playerDeathListener;

    public Waypoints() {
        super("Waypoints", ModType.RENDER);
        Command.newCommand().cmd("waypoints").description("Base command for Waypoints mod.").arguments("<action>").aliases("points", "wp").handler(this).build();

        points = new CopyOnWriteArrayList<>();
        render3DListener = new Listener<Render3DEvent>() {
            @Override
            public void onEventCalled(Render3DEvent event) {
                if (!Minecraft.isGuiEnabled())
                    return;
                RenderUtils.beginGl();
                for (Waypoint waypoint : points) {
                    String server;
                    if (mc.getCurrentServerData() == null) {
                        server = "singleplayer";
                    } else {
                        server = mc.getCurrentServerData().serverIP;
                    }
                    if (!waypoint.getServer().equals(server))
                        continue;

                    if (waypoint.getDimension() != mc.thePlayer.dimension)
                        continue;

                    if (boxes.getValue()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.func_179090_x();
                        drawBoxes(waypoint);
                        GlStateManager.popMatrix();
                    }

                    if (tracerLines.getValue()) {
                        double x = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosX + (EntityUtils.getReference().posX - EntityUtils.getReference().lastTickPosX) * event.getPartialTicks() - mc.getRenderManager().renderPosX : 0;
                        double y = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosY + (EntityUtils.getReference().posY - EntityUtils.getReference().lastTickPosY) * event.getPartialTicks() - mc.getRenderManager().renderPosY : 0;
                        double z = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosZ + (EntityUtils.getReference().posZ - EntityUtils.getReference().lastTickPosZ) * event.getPartialTicks() - mc.getRenderManager().renderPosZ : 0;

                        GlStateManager.pushMatrix();
                        GlStateManager.loadIdentity();
                        mc.entityRenderer.orientCamera(event.getPartialTicks());
                        GlStateManager.func_179090_x();
                        drawTracerLines(waypoint, x, y, z);
                        GlStateManager.popMatrix();
                    }

                    if (nametags.getValue()) {
                        drawNametags(waypoint);
                    }
                }
                RenderUtils.endGl();
            }
        };

        motionUpdateListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                if (event.getCurrentState() == MotionUpdateEvent.State.PRE) {
                    for (Waypoint waypoint : points) {
                        if (!waypoint.isTemporary())
                            continue;
                        if (!waypoint.getServer().equals(getCurrentServerIP()))
                            continue;
                        double distance = mc.thePlayer.getDistance(waypoint.getX(), waypoint.getY(), waypoint.getZ());
                        if (distance <= 3) {
                            points.remove(waypoint);
                            XIV.getInstance().getFileManager().saveFile("waypoints");
                            ChatLogger.print(String.format("Waypoint \"%s\" reached!", waypoint.getName()));
                        }
                    }
                }
            }
        };

        addWeatherListener = new Listener<AddWeatherEvent>() {
            @Override
            public void onEventCalled(AddWeatherEvent event) {
                if (!lightningPoints.getValue())
                    return;
                if (event.getEntity() instanceof EntityLightningBolt) {
                    EntityLightningBolt lightningBolt = (EntityLightningBolt) event.getEntity();
                    Waypoint point = new Waypoint("Lightning", getCurrentServerIP(), lightningBolt.posX, lightningBolt.posY, lightningBolt.posZ, mc.thePlayer.dimension, true);
                    points.add(point);
                    XIV.getInstance().getFileManager().saveFile("waypoints");
                    ChatLogger.print(String.format("Lightning Waypoint added at %s, %s, %s", point.getX(), point.getY(), point.getZ()));
                }
            }
        };

        playerDeathListener = new Listener<PlayerDeathEvent>() {
            @Override
            public void onEventCalled(PlayerDeathEvent event) {
                if (!deathPoints.getValue())
                    return;
                Waypoint point = new Waypoint("Death", getCurrentServerIP(), mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.dimension, false);
                points.add(point);
                XIV.getInstance().getFileManager().saveFile("waypoints");
                ChatLogger.print(String.format("Death Waypoint added at %s, %s, %s", point.getX(), point.getY(), point.getZ()));
            }
        };

        setEnabled(true);

        new XIVFile("waypoints", "json") {
            @Override
            public void load() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<Waypoint> pointsFromFile = gson.fromJson(reader, new TypeToken<List<Waypoint>>() {
                }.getType());
                points.addAll(pointsFromFile.stream().collect(Collectors.toList()));
            }

            @Override
            public void save() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Files.write(gson.toJson(points).getBytes("UTF-8"), file);
            }
        };
    }

    private void drawBoxes(Waypoint waypoint) {
        double x = waypoint.getX() + 0.5F - mc.getRenderManager().renderPosX;
        double y = waypoint.getY() - mc.getRenderManager().renderPosY;
        double z = waypoint.getZ() + 0.5F - mc.getRenderManager().renderPosZ;
        AxisAlignedBB box = AxisAlignedBB.fromBounds(x - 0.5D, y, z - 0.5D, x + 0.5D, y + 1.0D, z + 0.5D);
        GlStateManager.color(color[0], color[1], color[2], 0.6F);
        RenderUtils.drawLines(box);
        RenderGlobal.drawOutlinedBoundingBox(box, -1);
        GlStateManager.color(color[0], color[1], color[2], 0.11F);
        RenderUtils.drawFilledBox(box);
    }

    private void drawTracerLines(Waypoint waypoint, double x2, double y2, double z2) {
        double x = waypoint.getX() + 0.5F - mc.getRenderManager().renderPosX;
        double y = waypoint.getY() - mc.getRenderManager().renderPosY;
        double z = waypoint.getZ() + 0.5F - mc.getRenderManager().renderPosZ;
        GlStateManager.color(color[0], color[1], color[2], 1.0F);
        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
        var3.startDrawing(2);
        var3.addVertex(x2, y2 + mc.thePlayer.getEyeHeight(), z2);
        var3.addVertex(x, y, z);
        var2.draw();
    }

    private void drawNametags(Waypoint waypoint) {
        double x = waypoint.getX() + 0.5F - mc.getRenderManager().renderPosX;
        double y = waypoint.getY() - mc.getRenderManager().renderPosY;
        double z = waypoint.getZ() + 0.5F - mc.getRenderManager().renderPosZ;
        double dist = EntityUtils.getReference().getDistance(waypoint.getX(), waypoint.getY(), waypoint.getZ());
        final String text = waypoint.getName() + " \2477" + Math.round(dist) + "m\247r";
        double far = this.mc.gameSettings.renderDistanceChunks * 12.8D;
        double dl = Math.sqrt(x * x + z * z + y * y);
        double d;

        if (dl > far) {
            d = far / dl;
            dist *= d;
            x *= d;
            y *= d;
            z *= d;
        }

        float var13 = ((float) dist / 5 <= 2 ? 2.0F : (float) dist / 5) * RenderUtils.getNametagSize().getValue();
        float var14 = 0.016666668F * var13;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 1.5F, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        if (mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, -1.0F, 0.0F, 0.0F);
        } else {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.scale(-var14, -var14, var14);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.func_179090_x();
        worldRenderer.startDrawingQuads();
        int var18 = mc.fontRendererObj.getStringWidth(text) / 2;
        worldRenderer.func_178960_a(0.0F, 0.0F, 0.0F, RenderUtils.getNametagOpacity().getValue());
        worldRenderer.addVertex(-var18 - 2, -2, 0.0D);
        worldRenderer.addVertex(-var18 - 2, 9, 0.0D);
        worldRenderer.addVertex(var18 + 2, 9, 0.0D);
        worldRenderer.addVertex(var18 + 2, -2, 0.0D);
        tessellator.draw();
        GlStateManager.func_179098_w();
        mc.fontRendererObj.drawStringWithShadow(text, -var18, 0, 0xFFFFFFFF);
        GlStateManager.popMatrix();
    }

    private String getCurrentServerIP() {
        return mc.getCurrentServerData() == null ? "singleplayer" : mc.getCurrentServerData().serverIP;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "add":
                case "a":
                    if (arguments.length >= 7) {
                        try {
                            int x = Integer.parseInt(arguments[2]);
                            int y = Integer.parseInt(arguments[3]);
                            int z = Integer.parseInt(arguments[4]);
                            boolean temporary = Boolean.parseBoolean(arguments[5]);

                            String name = message.substring((String.format("%s %s %s %s %s %s ", arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5])).length());
                            Waypoint waypoint = new Waypoint(name, getCurrentServerIP(), x, y, z, mc.thePlayer.dimension, temporary);
                            points.add(waypoint);
                            XIV.getInstance().getFileManager().saveFile("waypoints");
                            ChatLogger.print(String.format("Waypoint \"%s\" added at %s, %s, %s", waypoint.getName(), waypoint.getX(), waypoint.getY(), waypoint.getZ()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print("Invalid integer, valid arguments: waypoints add <x> <y> <z> <temp> <name>");
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: waypoints add <x> <y> <z> <temp> <name>");
                    }
                    break;
                case "here":
                case "h":
                    if (arguments.length >= 3) {
                        try {
                            int x = mc.thePlayer.getPosition().getX();
                            int y = mc.thePlayer.getPosition().getY();
                            int z = mc.thePlayer.getPosition().getZ();

                            String name = message.substring((String.format("%s %s ", arguments[0], arguments[1])).length());
                            Waypoint waypoint = new Waypoint(name, getCurrentServerIP(), x, y, z, mc.thePlayer.dimension, false);
                            points.add(waypoint);
                            XIV.getInstance().getFileManager().saveFile("waypoints");
                            ChatLogger.print(String.format("Waypoint \"%s\" added at %s, %s, %s", waypoint.getName(), waypoint.getX(), waypoint.getY(), waypoint.getZ()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print("Invalid integer, valid arguments: waypoints here <name>");
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: waypoints here <name>");
                    }
                    break;
                case "del":
                case "d":
                    if (arguments.length >= 3) {
                        final String name = message.substring((String.format("%s %s ", arguments[0], arguments[1])).length());
                        boolean found = false;
                        for (final Waypoint waypoint : points) {
                            if (waypoint.getName().toLowerCase().startsWith(name.toLowerCase())) {
                                points.remove(waypoint);
                                XIV.getInstance().getFileManager().saveFile("waypoints");
                                ChatLogger.print(String.format("Waypoint \"%s\" deleted.", waypoint.getName()));
                                found = true;
                            }
                        }

                        if (!found) {
                            ChatLogger.print("Waypoint \"" + name + "\" not found.");
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: waypoints del <name>");
                    }
                    break;
                case "tracerlines":
                case "tracers":
                    if (arguments.length >= 3) {
                        tracerLines.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        tracerLines.setValue(!tracerLines.getValue());
                    }
                    ChatLogger.print(String.format("Waypoints will %s draw tracer lines.", tracerLines.getValue() ? "now" : "no longer"));
                    break;
                case "boxes":
                    if (arguments.length >= 3) {
                        boxes.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        boxes.setValue(!boxes.getValue());
                    }
                    ChatLogger.print(String.format("Waypoints will %s draw boxes.", boxes.getValue() ? "now" : "no longer"));
                    break;
                case "nametags":
                case "tags":
                    if (arguments.length >= 3) {
                        nametags.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        nametags.setValue(!nametags.getValue());
                    }
                    ChatLogger.print(String.format("Waypoints will %s draw nametags.", nametags.getValue() ? "now" : "no longer"));
                    break;
                case "lightning":
                    if (arguments.length >= 3) {
                        lightningPoints.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        lightningPoints.setValue(!lightningPoints.getValue());
                    }
                    ChatLogger.print(String.format("Waypoints will %s set waypoints at lightning strikes.", lightningPoints.getValue() ? "now" : "no longer"));
                    break;
                case "death":
                    if (arguments.length >= 3) {
                        deathPoints.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        deathPoints.setValue(!deathPoints.getValue());
                    }
                    ChatLogger.print(String.format("Waypoints will %s set waypoints at death.", deathPoints.getValue() ? "now" : "no longer"));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: add, here, del, tracerlines, boxes, nametags, lightning, death");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: waypoints <action>");
        }
    }

    public List<Waypoint> getPoints() {
        return points;
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(render3DListener, motionUpdateListener, addWeatherListener, playerDeathListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(render3DListener, motionUpdateListener, addWeatherListener, playerDeathListener);
    }
}
