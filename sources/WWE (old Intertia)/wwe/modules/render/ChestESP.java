package wwe.modules.render;

import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import java.awt.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.block.*;
import wwe.*;

public class ChestESP extends qMIe
{
    public BooleanValue chest;
    public BooleanValue enderchest;
    public BooleanValue furnace;
    public BooleanValue dispenser;
    public BooleanValue dropper;
    public BooleanValue hopper;
    public BooleanValue shulkerbox;
    public BooleanValue minecart;
    public BooleanValue itemframe;
    public ModeValue mode;
    
    public ChestESP() {
        super("Chest ESP", "Makes storage units glow", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Box");
        list.add("Outline");
        this.chest = new BooleanValue("Chest", this, "ESP around chests", true);
        this.enderchest = new BooleanValue("Ender chest", this, "ESP around ender chest", true);
        this.furnace = new BooleanValue("Furnace", this, "ESP around furnaces", true);
        this.dispenser = new BooleanValue("Dispenser", this, "ESP around dispensers", true);
        this.dropper = new BooleanValue("Dropper", this, "ESP around droppers", true);
        this.hopper = new BooleanValue("Hopper", this, "ESP around hoppers", true);
        this.shulkerbox = new BooleanValue("Shulker box", this, "ESP around shulker boxes", true);
        this.minecart = new BooleanValue("Mine cart", this, "ESP around mine carts", true);
        this.itemframe = new BooleanValue("Item frame", this, "ESP around item frames", true);
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Box", list);
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        if (this.mode.OnGi().equalsIgnoreCase("Box")) {
            this.owgW("Chest ESP " + TextFormatting.DARK_GRAY + "Box");
            for (final TileEntity tileEntity : Wrapper.mc.world.loadedTileEntityList) {
                if (tileEntity instanceof TileEntityChest && this.chest.OnGi()) {
                    final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
                    this.ZZRX(tileEntityChest, tileEntityChest.getPos().getX(), tileEntityChest.getPos().getY(), tileEntityChest.getPos().getZ());
                }
                if (tileEntity instanceof TileEntityEnderChest && this.enderchest.OnGi()) {
                    final TileEntityEnderChest tileEntityEnderChest = (TileEntityEnderChest)tileEntity;
                    this.lqNE(tileEntityEnderChest, tileEntityEnderChest.getPos().getX(), tileEntityEnderChest.getPos().getY(), tileEntityEnderChest.getPos().getZ());
                }
                if (tileEntity instanceof TileEntityFurnace && this.furnace.OnGi()) {
                    BIIV.zIBn(((TileEntityFurnace)tileEntity).getPos(), Color.gray, 1.0, 1.0);
                }
                if (tileEntity instanceof TileEntityDropper && this.dropper.OnGi()) {
                    BIIV.zIBn(((TileEntityDropper)tileEntity).getPos(), Color.gray, 1.0, 1.0);
                }
                if (tileEntity instanceof TileEntityDispenser && this.dispenser.OnGi()) {
                    BIIV.zIBn(((TileEntityDispenser)tileEntity).getPos(), Color.gray, 1.0, 1.0);
                }
                if (tileEntity instanceof TileEntityHopper && this.hopper.OnGi()) {
                    BIIV.zIBn(((TileEntityHopper)tileEntity).getPos(), Color.gray, 1.0, 1.0);
                }
                if (tileEntity instanceof TileEntityShulkerBox && this.shulkerbox.OnGi()) {
                    BIIV.zIBn(((TileEntityShulkerBox)tileEntity).getPos(), Color.pink, 1.0, 1.0);
                }
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
            }
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (entity instanceof EntityMinecartChest && this.minecart.OnGi()) {
                    BIIV.zIBn(entity.getPosition(), Color.orange, 1.0, 1.0);
                }
                if ((entity instanceof EntityMinecartFurnace && this.minecart.OnGi()) || (entity instanceof EntityMinecartHopper && this.minecart.OnGi())) {
                    BIIV.zIBn(entity.getPosition(), Color.gray, 1.0, 1.0);
                }
                if (entity instanceof EntityItemFrame && this.itemframe.OnGi()) {
                    final EntityItemFrame entityItemFrame = (EntityItemFrame)entity;
                    final Entity entity2 = entity;
                    Color color;
                    if (entityItemFrame.getDisplayedItem().isEmpty()) {
                        color = Color.RED;
                        if (ChestESP.\u200b\u2009\u2001) {
                            throw null;
                        }
                    }
                    else {
                        color = Color.GREEN;
                    }
                    BIIV.KZGQ(entity2, color);
                }
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Outline")) {
            this.owgW("Chest ESP " + TextFormatting.DARK_GRAY + "Outline");
            for (final TileEntityFurnace next : Wrapper.mc.world.loadedTileEntityList) {
                if (next instanceof TileEntityFurnace && this.furnace.OnGi()) {
                    BIIV.zIBn(next.getPos(), Color.gray, 1.0, 1.0);
                }
                if (next instanceof TileEntityDropper && this.dropper.OnGi()) {
                    BIIV.zIBn(((TileEntityDropper)next).getPos(), Color.gray, 1.0, 1.0);
                }
                if (next instanceof TileEntityDispenser && this.dispenser.OnGi()) {
                    BIIV.zIBn(((TileEntityDispenser)next).getPos(), Color.gray, 1.0, 1.0);
                }
                if (next instanceof TileEntityHopper && this.hopper.OnGi()) {
                    BIIV.zIBn(((TileEntityHopper)next).getPos(), Color.gray, 1.0, 1.0);
                }
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
            }
            for (final Entity entity3 : Wrapper.mc.world.loadedEntityList) {
                if (entity3 instanceof EntityMinecartChest && this.minecart.OnGi()) {
                    BIIV.zIBn(entity3.getPosition(), Color.orange, 1.0, 1.0);
                }
                if ((entity3 instanceof EntityMinecartFurnace && this.minecart.OnGi()) || (entity3 instanceof EntityMinecartHopper && this.minecart.OnGi())) {
                    BIIV.zIBn(entity3.getPosition(), Color.gray, 1.0, 1.0);
                }
                if (entity3 instanceof EntityItemFrame && this.itemframe.OnGi()) {
                    final EntityItemFrame entityItemFrame2 = (EntityItemFrame)entity3;
                    final Entity entity4 = entity3;
                    Color color2;
                    if (entityItemFrame2.getDisplayedItem().isEmpty()) {
                        color2 = Color.RED;
                        if (ChestESP.\u200b\u2009\u2001) {
                            throw null;
                        }
                    }
                    else {
                        color2 = Color.GREEN;
                    }
                    BIIV.KZGQ(entity4, color2);
                }
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
            }
        }
    }
    
    public static void AUkB(final float n) {
        RenderHelper.enableStandardItemLighting();
        for (final TileEntity tileEntity : Wrapper.mc.world.loadedTileEntityList) {
            if (!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof TileEntityEnderChest) && !(tileEntity instanceof TileEntityShulkerBox)) {
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
                continue;
            }
            else {
                GL11.glPushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                TileEntityRendererDispatcher.instance.render(tileEntity, tileEntity.getPos().getX() - yYGD.onRX(), tileEntity.getPos().getY() - yYGD.UuJK(), tileEntity.getPos().getZ() - yYGD.LkVR(), n);
                GL11.glPopMatrix();
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    public static void tCsD(final float n) {
        RenderHelper.enableStandardItemLighting();
        for (final TileEntity tileEntity : Wrapper.mc.world.loadedTileEntityList) {
            if (!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof TileEntityEnderChest) && !(tileEntity instanceof TileEntityShulkerBox)) {
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
                continue;
            }
            else {
                if (tileEntity instanceof TileEntityChest) {
                    GL11.glColor3f(1.0f, 0.7f, 0.0f);
                    if (ChestESP.\u200b\u2009\u2001) {
                        throw null;
                    }
                }
                else if (tileEntity instanceof TileEntityEnderChest) {
                    GL11.glColor3f(145.0f, 0.0f, 255.0f);
                    if (ChestESP.\u200b\u2009\u2001) {
                        throw null;
                    }
                }
                else if (tileEntity instanceof TileEntityShulkerBox) {
                    GL11.glColor3f(255.0f, 0.0f, 0.0f);
                    if (ChestESP.\u200b\u2009\u2001) {
                        throw null;
                    }
                }
                else {
                    GL11.glColor3f(1.0f, 1.0f, 1.0f);
                }
                TileEntityRendererDispatcher.instance.render(tileEntity, tileEntity.getPos().getX() - yYGD.onRX(), tileEntity.getPos().getY() - yYGD.UuJK(), tileEntity.getPos().getZ() - yYGD.LkVR(), n);
                if (ChestESP.\u200b\u2009\u2001) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    public void ZZRX(final TileEntityChest tileEntityChest, final double n, final double n2, final double n3) {
        if (this.IuiN()) {
            final boolean adjacentChestChecked = tileEntityChest.adjacentChestChecked;
            if (tileEntityChest.adjacentChestXPos != null) {
                if (tileEntityChest.getChestType() == BlockChest.Type.TRAP) {
                    BIIV.zIBn(tileEntityChest.getPos(), Color.red, 1.0, 2.0);
                    if (ChestESP.\u200b\u2009\u2001) {
                        throw null;
                    }
                }
                else {
                    BIIV.zIBn(tileEntityChest.getPos(), Color.orange, 1.0, 2.0);
                }
            }
            if (tileEntityChest.adjacentChestZPos != null) {
                if (tileEntityChest.getChestType() == BlockChest.Type.TRAP) {
                    BIIV.zIBn(tileEntityChest.getPos(), Color.red, 2.0, 1.0);
                    if (ChestESP.\u200b\u2009\u2001) {
                        throw null;
                    }
                }
                else {
                    BIIV.zIBn(tileEntityChest.getPos(), Color.orange, 2.0, 1.0);
                }
            }
            if (tileEntityChest.adjacentChestZNeg == null && tileEntityChest.adjacentChestXNeg == null && tileEntityChest.adjacentChestXPos == null && tileEntityChest.adjacentChestZPos == null) {
                if (tileEntityChest.getChestType() == BlockChest.Type.TRAP) {
                    BIIV.zIBn(tileEntityChest.getPos(), Color.red, 1.0, 1.0);
                    if (ChestESP.\u200b\u2009\u2001) {
                        throw null;
                    }
                }
                else {
                    BIIV.zIBn(tileEntityChest.getPos(), Color.orange, 1.0, 1.0);
                }
            }
        }
    }
    
    public void lqNE(final TileEntityEnderChest tileEntityEnderChest, final double n, final double n2, final double n3) {
        if (this.IuiN()) {
            BIIV.zIBn(tileEntityEnderChest.getPos(), Color.magenta, 1.0, 1.0);
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}
