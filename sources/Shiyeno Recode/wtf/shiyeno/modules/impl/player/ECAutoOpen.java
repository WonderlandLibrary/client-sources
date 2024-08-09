package wtf.shiyeno.modules.impl.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventKey;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.font.styled.StyledFont;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.render.ProjectionUtils;
import wtf.shiyeno.util.render.RenderUtil.Render2D;

@FunctionAnnotation(
        name = "ECAutoOpen",
        type = Type.Player
)
public class ECAutoOpen extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    private BindSetting active = new BindSetting(" нопка открыти€", 0);
    public BooleanOption notif = new BooleanOption("—ообщение о эндер честе", true);
    public BooleanOption gps = new BooleanOption("GPS на ближайший эндер чест", true);
    public BooleanOption render = new BooleanOption("–исовать метки", true);

    public ECAutoOpen() {
        this.addSettings(new Setting[]{this.active, this.notif, this.gps, this.render});
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender eventRender) {
            if (this.render.get()) {
                onRender(eventRender.matrixStack);
            }
        }

        if (event instanceof EventKey e) {
            if (e.key == this.active.getKey()) {
                boolean test = true;
                Iterator var4 = mc.world.loadedTileEntityList.iterator();

                while(var4.hasNext()) {
                    TileEntity t = (TileEntity)var4.next();
                    if (t instanceof EnderChestTileEntity) {
                        int x = t.getPos().getX();
                        int y = t.getPos().getY();
                        int z = t.getPos().getZ();
                        if (mc.player.getDistanceSq((double)x, (double)y, (double)z) < 35.0) {
                            if (this.notif.get()) {
                                ClientUtil.sendMesage(TextFormatting.GREEN + "Ёндер сундук был найден и была попытка использовать его!");
                            }

                            BlockRayTraceResult rayTraceResult = new BlockRayTraceResult(new Vector3d(mc.player.getPosX(), mc.player.getPosY() - 1.0, mc.player.getPosZ()), Direction.UP, new BlockPos(x, y, z), false);
                            mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult);
                        }

                        if (this.gps.get() && mc.player.getDistanceSq((double)x, (double)y, (double)z) < 20000.0 && test) {
                            mc.player.sendChatMessage(".gps " + x + " " + z);
                            test = false;
                        }
                    }
                }
            }
        }
    }

    public static void onRender(MatrixStack stack) {
        float height = 7.5F;
        StyledFont small = Fonts.msSemiBold[11];
        Iterator var3 = mc.world.loadedTileEntityList.iterator();

        while(var3.hasNext()) {
            TileEntity entity = (TileEntity)var3.next();
            if (entity instanceof EnderChestTileEntity) {
                double x = (double)entity.getPos().getX();
                double y = (double)entity.getPos().getY() + 1.2;
                double z = (double)entity.getPos().getZ();
                Vector2d vector2d = ProjectionUtils.project(x, y, z);
                int dst = (int)Math.sqrt(Math.pow(x - mc.player.getPosX(), 2.0) + Math.pow(z - mc.player.getPosZ(), 2.0));
                String text = "Ёндер сундук (" + dst + "m)";
                float textWidth = small.getWidth(text) + 2.0F;
                if (vector2d != null) {
                    GL11.glPushMatrix();
                    Render2D.drawRoundedCorner((float)(vector2d.x - 1.0), (float)(vector2d.y - 3.0), textWidth, height, 3.0F, (new Color(0, 0, 0, 255)).getRGB());
                    small.drawString(stack, String.valueOf(text), (double)((float)vector2d.x), (double)((float)vector2d.y), (new Color(255, 255, 255, 255)).getRGB());
                    GL11.glPopMatrix();
                }
            }
        }
    }

    public void onDisable() {
    }
}