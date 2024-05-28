package arsenic.module.impl.player;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventPacket;
import arsenic.event.impl.EventRender2D;
import arsenic.event.impl.EventUpdate;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.EnumProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.minecraft.PlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.awt.*;
import java.util.TimerTask;

@ModuleInfo(name = "BedAura", category = ModuleCategory.Player)
public class BedAura extends Module {
    public final DoubleProperty range = new DoubleProperty("Range", new DoubleValue(0, 5, 3, 0.1));
    public BooleanProperty debug = new BooleanProperty("Debug", false);
    public BooleanProperty autotool = new BooleanProperty("Bypass AutoTool", false);
    private boolean I1I1I1I11111II = false;
    public final EnumProperty<mode> mo = new EnumProperty<>("Mode: ", mode.Legit);
    private java.util.Timer t;
    private BlockPos m = null;
    public int dell, I1I1I1I1111I;
    public boolean I1I1I1I11111II1;

    public void onEnable() {
        I1I1I1I11111II = true;
        I1I1I1I11111II1 = false;
        (this.t = new java.util.Timer()).scheduleAtFixedRate(this.t(), 0L, 600L);
    }

    public void onDisable() {
        I1I1I1I11111II1 = false;
        if (this.t != null) {
            this.t.cancel();
            this.t.purge();
            this.t = null;
        }

        this.m = null;
    }

    public TimerTask t() {
        return new TimerTask() {
            public void run() {
                int ra = (int) range.getValue().getInput();
                if (mo.getValue() == mode.Legit) {
                    for (int y = ra; y >= -ra; --y) {
                        for (int x = -ra; x <= ra; ++x) {
                            for (int z = -ra; z <= ra; ++z) {
                                for (EnumFacing direction : EnumFacing.VALUES) { // For each direction
                                    BlockPos bd = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y, Module.mc.thePlayer.posZ + (double) z);
                                    BlockPos neighbourPos = bd.offset(direction); // Offset the block's position by 1 block in the current direction
                                    IBlockState neighbourState = mc.theWorld.getBlockState(neighbourPos); // Get the IBlockState at the neighboring position
                                    Block neighbourBlock = neighbourState.getBlock(); // Get the IBlockState's Block
                                    if (neighbourBlock == Blocks.air) {
                                        if (PlayerUtils.isPlayerInGame()) {
                                            BlockPos p = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y, Module.mc.thePlayer.posZ + (double) z);
                                            boolean bed = Module.mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                                            if (BedAura.this.m == p) {
                                                if (!bed) {
                                                    BedAura.this.m = null;
                                                }
                                            } else if (bed) {
                                                BedAura.this.mi(p);
                                                BedAura.this.m = p;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (mo.getValue() == mode.Bypass) {
                    for (int y = ra; y >= -ra; --y) {
                        for (int x = -ra; x <= ra; ++x) {
                            for (int z = -ra; z <= ra; ++z) {
                                for (EnumFacing direction : EnumFacing.VALUES) { // For each direction
                                    BlockPos bd = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y, Module.mc.thePlayer.posZ + (double) z);
                                    BlockPos neighbourPos = bd.offset(direction); // Offset the block's position by 1 block in the current direction

                                    IBlockState neighbourState = mc.theWorld.getBlockState(neighbourPos); // Get the IBlockState at the neighboring position

                                    Block neighbourBlock = neighbourState.getBlock(); // Get the IBlockState's Block
                                    if (neighbourBlock == Blocks.air) {
                                        if (PlayerUtils.isPlayerInGame()) {
                                            BlockPos p = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y, Module.mc.thePlayer.posZ + (double) z);
                                            boolean bed = Module.mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                                            if (BedAura.this.m == p) {
                                                if (!bed) {
                                                    BedAura.this.m = null;
                                                }
                                            } else if (bed) {
                                                BedAura.this.mi(p);
                                                BedAura.this.m = p;
                                                I1I1I1I11111II1 = false;
                                                if (autotool.getValue()) {
                                                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                                                }
                                                if (debug.getValue()) {
                                                    PlayerUtils.addWaterMarkedMessageToChat("§2 Bypassed Successfully");
                                                    PlayerUtils.addWaterMarkedMessageToChat("§4 Bed Nuked");
                                                }
                                            }
                                        }
                                    } else if (neighbourBlock != Blocks.air) {
                                        if (PlayerUtils.isPlayerInGame()) {
                                            BlockPos p = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y, Module.mc.thePlayer.posZ + (double) z);
                                            BlockPos off = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y + 1, Module.mc.thePlayer.posZ + (double) z);
                                            boolean bed = Module.mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                                            if (bed && !I1I1I1I11111II1 && mc.theWorld.getBlockState(off).getBlock() != Blocks.air) {
                                                String oblc = String.valueOf(mc.theWorld.getBlockState(off).getBlock().getLocalizedName());
                                                if (debug.getValue()) {
                                                    dell++;
                                                    if (dell > 10) {
                                                        dell = 0;
                                                        PlayerUtils.addWaterMarkedMessageToChat("§6 Trying to Bypass");
                                                    }
                                                }
                                                BedAura.this.mi(off);
                                                BedAura.this.m = null;
                                                I1I1I1I11111II1 = true;
                                                if (debug.getValue()) {
                                                    dell++;
                                                    if (dell > 10) {
                                                        dell = 0;
                                                        PlayerUtils.addWaterMarkedMessageToChat("§6 Trying to Bypass");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }


    private void mi(BlockPos p) {
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p, EnumFacing.NORTH));
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p, EnumFacing.NORTH));
    }

    private int index = -1;

    private void hotkeyToFastestAura() {
        double speed = 1;
        int ra = (int) range.getValue().getInput();
        for (int y = ra; y >= -ra; --y) {
            for (int x = -ra; x <= ra; ++x) {
                for (int z = -ra; z <= ra; ++z) {
                    BlockPos p = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y, Module.mc.thePlayer.posZ + (double) z);
                    boolean bed = Module.mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                    if (bed) {
                        BlockPos off2 = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y + 1, Module.mc.thePlayer.posZ + (double) z);
                        for (int slot = 0; slot <= 8; slot++) {
                            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                            if (itemInSlot != null) {
                                if (itemInSlot.getItem() instanceof ItemTool || itemInSlot.getItem() instanceof ItemShears) {
                                    Block bl = mc.theWorld.getBlockState(off2).getBlock();

                                    if (itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
                                        speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                                        index = slot;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (index == -1 || speed <= 1.1 || speed == 0) {
        } else {
            if (I1I1I1I11111II) {
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                I1I1I1I11111II = false;
            }
        }
    }

    //blockesp //uhh what?
    public enum mode {
        Legit,
        Bypass
    }

    @EventLink
    public Listener<EventPacket.Incoming.Pre> onPacket = e -> {
        if (e.getPacket() instanceof C09PacketHeldItemChange) {
            if (!I1I1I1I11111II && ((C09PacketHeldItemChange) e.getPacket()).getSlotId() != index) {
                I1I1I1I11111II = true;
                if (debug.getValue()) {
                    PlayerUtils.addWaterMarkedMessageToChat("Corrected C09");
                }
            }
        }
    };

    @EventLink
    public Listener<EventRender2D> onRender2D = e -> {
        if (mc.currentScreen != null) {
            return;
        }

        ScaledResolution res = new ScaledResolution(mc);
        if (mc.currentScreen == null) {
            int ra = (int) range.getValue().getInput();
            for (int y = ra; y >= -ra; --y) {
                for (int x = -ra; x <= ra; ++x) {
                    for (int z = -ra; z <= ra; ++z) {
                        BlockPos p = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y, Module.mc.thePlayer.posZ + (double) z);
                        boolean bed = Module.mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                        BlockPos off = new BlockPos(Module.mc.thePlayer.posX + (double) x, Module.mc.thePlayer.posY + (double) y + 1, Module.mc.thePlayer.posZ + (double) z);
                        if (bed && mc.theWorld.getBlockState(off).getBlock() != Blocks.air) {
                            String oblc = String.valueOf(mc.theWorld.getBlockState(off).getBlock().getLocalizedName());


                            String t = "Currently Mining ";
                            int xx = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(t) / 2;
                            int yy = res.getScaledHeight() / 2 - 15;
                            mc.fontRendererObj.drawString("Currently Mining " + oblc, (float) xx - 13, (float) yy, Color.orange.getRGB(), true);
                            mc.fontRendererObj.drawString(t, (float) xx - 13, (float) yy, Color.white.getRGB(), true);
                        }
                    }
                }
            }
        }

        if (autotool.getValue() && mo.getValue() == mode.Bypass) {
            hotkeyToFastestAura();
        }
    };

    @EventLink
    public Listener<EventUpdate> up = ev -> {
        if (I1I1I1I11111II1 && mo.getValue() == mode.Bypass) {
            I1I1I1I1111I++;
            if (I1I1I1I1111I > 80) {
                //I1I1I1I11111II1 = false;
                I1I1I1I1111I = 0;
            }
        }
    };
}
