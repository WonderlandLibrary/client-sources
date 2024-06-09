package cow.milkgod.cheese.module.modules;

import cow.milkgod.cheese.module.*;
import net.minecraft.tileentity.*;
import java.util.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import cow.milkgod.cheese.*;
import cow.milkgod.cheese.events.*;
import cow.milkgod.cheese.utils.*;
import cow.milkgod.cheese.commands.*;

public class StorageESP extends Module
{
    public static boolean chest;
    public static boolean dispenser;
    public static boolean enderChest;
    
    static {
        StorageESP.chest = true;
        StorageESP.dispenser = false;
        StorageESP.enderChest = true;
    }
    
    public StorageESP() {
        super("StorageESP", 0, Category.RENDER, 10053324, true, "Marks out storage shit", new String[] { "sesp" });
    }
    
    @EventTarget
    private void onRender(final Event3D event) {
        GlStateManager.pushMatrix();
        for (final Object ent1 : Wrapper.mc.theWorld.loadedTileEntityList) {
            final TileEntity ent2 = (TileEntity)ent1;
            if (!(ent2 instanceof TileEntityChest) && !(ent2 instanceof TileEntityDispenser) && !(ent2 instanceof TileEntityEnderChest)) {
                continue;
            }
            if (ent2 instanceof TileEntityChest && !StorageESP.chest) {
                continue;
            }
            if (ent2 instanceof TileEntityDispenser && !StorageESP.dispenser) {
                continue;
            }
            if (ent2 instanceof TileEntityEnderChest && !StorageESP.enderChest) {
                continue;
            }
            this.drawEsp(ent2, Event3D.getPartialTicks());
        }
        GlStateManager.popMatrix();
    }
    
    private void drawEsp(final TileEntity ent, final float pTicks) {
        final double x1 = ent.getPos().getX() - RenderManager.renderPosX;
        final double y1 = ent.getPos().getY() - RenderManager.renderPosY;
        final double z1 = ent.getPos().getZ() - RenderManager.renderPosZ;
        final float[] color = this.getColor(ent);
        AxisAlignedBB box = new AxisAlignedBB(x1, y1, z1, x1 + 1.0, y1 + 1.0, z1 + 1.0);
        if (ent instanceof TileEntityChest) {
            final TileEntityChest chest = TileEntityChest.class.cast(ent);
            if (chest.adjacentChestZPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 1.9375);
            }
            else if (chest.adjacentChestXPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 1.9375, y1 + 0.875, z1 + 0.9375);
            }
            else {
                if (chest.adjacentChestZPos != null || chest.adjacentChestXPos != null || chest.adjacentChestZNeg != null || chest.adjacentChestXNeg != null) {
                    return;
                }
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
            }
        }
        else if (ent instanceof TileEntityEnderChest) {
            box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
        }
        RenderUtils.filledBox(box, new Color(color[0], color[1], color[2]).getRGB() & 0x19FFFFFF, true);
        RenderGlobal.drawOutlinedBoundingBox(box, new Color(color[0], color[1], color[2]).getRGB());
    }
    
    private float[] getColor(final TileEntity ent) {
        if (ent instanceof TileEntityChest) {
            return new float[] { 0.0f, 0.9f, 0.9f };
        }
        if (ent instanceof TileEntityDispenser) {
            return new float[] { 0.29f, 0.29f, 0.29f };
        }
        if (ent instanceof TileEntityEnderChest) {
            return new float[] { 0.3f, 0.0f, 0.3f };
        }
        return new float[] { 1.0f, 1.0f, 1.0f };
    }
    
    @Override
    protected void addCommand() {
        Cheese.getInstance();
        final CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("StorageESP", "Unknown Option! ", null, "<Chest | Dispenser | Enderchest>", "Store that shit bby") {
            @EventTarget
            public void onTick(final EventTick ev) {
                Label_0547: {
                    try {
                        final String nigger = EventChatSend.getMessage().split(" ")[1];
                        Label_0483: {
                            Label_0440: {
                                Label_0402: {
                                    Label_0364: {
                                        Label_0326: {
                                            final String s;
                                            final String s2;
                                            switch (s2 = (s = nigger)) {
                                                case "Dispenser": {
                                                    break Label_0402;
                                                }
                                                case "Vphase": {
                                                    break Label_0547;
                                                }
                                                case "actual": {
                                                    break Label_0440;
                                                }
                                                case "Enderchest": {
                                                    break Label_0326;
                                                }
                                                case "vPhase": {
                                                    break Label_0547;
                                                }
                                                case "values": {
                                                    break Label_0440;
                                                }
                                                case "vphase": {
                                                    break Label_0547;
                                                }
                                                case "sp": {
                                                    break Label_0326;
                                                }
                                                case "HCF": {
                                                    break Label_0364;
                                                }
                                                case "norm": {
                                                    break Label_0402;
                                                }
                                                case "Chest": {
                                                    break Label_0364;
                                                }
                                                case "chest": {
                                                    break Label_0364;
                                                }
                                                case "dispenser": {
                                                    break Label_0402;
                                                }
                                                case "enderchest": {
                                                    break Label_0326;
                                                }
                                                default:
                                                    break;
                                            }
                                            break Label_0483;
                                        }
                                        StorageESP.enderChest = !StorageESP.enderChest;
                                        Logger.logChat("Set enderchest to §e" + StorageESP.enderChest);
                                        break Label_0547;
                                    }
                                    StorageESP.chest = !StorageESP.chest;
                                    Logger.logChat("Set chest mode to §e" + StorageESP.chest);
                                    break Label_0547;
                                }
                                StorageESP.dispenser = !StorageESP.dispenser;
                                Logger.logChat("Set dispenser mode to §e" + StorageESP.dispenser);
                                break Label_0547;
                            }
                            Logger.logChat("Current storage mode:  §eDispenser: " + StorageESP.dispenser + "Chest: " + StorageESP.chest + "EnderChest: " + StorageESP.enderChest);
                        }
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                    catch (Exception ex) {
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                }
                this.Toggle();
            }
        });
    }
}
