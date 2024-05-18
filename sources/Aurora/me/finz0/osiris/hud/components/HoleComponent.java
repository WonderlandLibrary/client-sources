package me.finz0.osiris.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.CurrentHole;
import me.finz0.osiris.util.BlockUtils;
import me.finz0.osiris.util.FontUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class HoleComponent extends Panel {
    public HoleComponent(double ix, double iy, ClickGUI parent) {
        super("Hole", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;
    }



    CurrentHole mod = ((CurrentHole) ModuleManager.getModuleByName("Hole"));

    Color c;
    boolean font;
    Color color;

    boolean bedrock;
    boolean obby;
    boolean safe;


    public void drawHud(){
        doStuff();
        if(mod.mode.getValString().equalsIgnoreCase("Texture")) renderHole(x, y);
        else FontUtils.drawStringWithShadow(font, text(), (int)x, (int)y, color.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        double w = FontUtils.getStringWidth(font, text()) + 2;
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        if(mod.mode.getValString().equalsIgnoreCase("Texture")) width = 48;
        else width = w;
        if(mod.mode.getValString().equalsIgnoreCase("Texture")) height = 48;
        else height = FontUtils.getFontHeight(font) + 2;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        if(!extended) FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int)y + (int) height, c.getRGB());
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int)y + (int) height, c.getRGB());
            if(mod.mode.getValString().equalsIgnoreCase("Texture")) renderHole(x, y);
            else FontUtils.drawStringWithShadow(font, text(), (int)x, (int)y, color.getRGB());
        }
    }

    private void doStuff() {
        color = safe ? Color.GREEN : Color.RED;
        font = mod.customFont.getValBoolean();
        bedrock = northBrock() && eastBrock() && southBrock() && westBrock();
        obby = !bedrock && ((northObby() || northBrock()) && (eastObby() || eastBrock()) && (southObby() || southBrock()) && (westObby() || westBrock()));
        safe = obby || bedrock;
    }

    private String text(){
        String text;

        if(mod.mode.getValString().equalsIgnoreCase("Safe/Unsafe")) text = safe ? "Safe" : "Unsafe";
        else text = bedrock ? "Bedrock" : obby ? "Obsidian" : "None";

        return ChatFormatting.GRAY + "Hole " + ChatFormatting.RESET + text;
    }

    private void renderHole(double x, double y){
        double leftX = x;
        double leftY = y + 16;
        double upX = x + 16;
        double upY = y;
        double rightX = x + 32;
        double rightY = y + 16;
        double bottomX = x + 16;
        double bottomY = y + 32;
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        switch (mc.getRenderViewEntity().getHorizontalFacing()) {
            case NORTH:
                if(northObby() || northBrock()) renderItem(upX, upY, new ItemStack(mc.world.getBlockState(playerPos.north()).getBlock()));
                if(westObby() || westBrock()) renderItem(leftX, leftY, new ItemStack(mc.world.getBlockState(playerPos.west()).getBlock()));
                if(eastObby() || eastBrock()) renderItem(rightX, rightY, new ItemStack(mc.world.getBlockState(playerPos.east()).getBlock()));
                if(southObby() || southBrock()) renderItem(bottomX, bottomY, new ItemStack(mc.world.getBlockState(playerPos.south()).getBlock()));
            break;

            case SOUTH:
                if(southObby() || southBrock()) renderItem(upX, upY, new ItemStack(mc.world.getBlockState(playerPos.south()).getBlock()));
                if(eastObby() || eastBrock()) renderItem(leftX, leftY, new ItemStack(mc.world.getBlockState(playerPos.east()).getBlock()));
                if(westObby() || westBrock()) renderItem(rightX, rightY, new ItemStack(mc.world.getBlockState(playerPos.west()).getBlock()));
                if(northObby() || northBrock()) renderItem(bottomX, bottomY, new ItemStack(mc.world.getBlockState(playerPos.north()).getBlock()));
            break;

            case WEST:
                if(westObby() || westBrock()) renderItem(upX, upY, new ItemStack(mc.world.getBlockState(playerPos.west()).getBlock()));
                if(southObby() || southBrock()) renderItem(leftX, leftY, new ItemStack(mc.world.getBlockState(playerPos.south()).getBlock()));
                if(northObby() || northBrock()) renderItem(rightX, rightY, new ItemStack(mc.world.getBlockState(playerPos.north()).getBlock()));
                if(eastObby() || eastBrock()) renderItem(bottomX, bottomY, new ItemStack(mc.world.getBlockState(playerPos.east()).getBlock()));
            break;

            case EAST:
                if(eastObby() || eastBrock()) renderItem(upX, upY, new ItemStack(mc.world.getBlockState(playerPos.east()).getBlock()));
                if(northObby() || northBrock()) renderItem(leftX, leftY, new ItemStack(mc.world.getBlockState(playerPos.north()).getBlock()));
                if(southObby() || southBrock()) renderItem(rightX, rightY, new ItemStack(mc.world.getBlockState(playerPos.south()).getBlock()));
                if(westObby() || westBrock()) renderItem(bottomX, bottomY, new ItemStack(mc.world.getBlockState(playerPos.west()).getBlock()));
            break;
        }
    }

    private void renderItem(double x, double y, ItemStack is){
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(is, (int)x, (int)y);
        RenderHelper.disableStandardItemLighting();
    }

    private boolean northObby(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.OBSIDIAN;
    }
    private boolean eastObby(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.OBSIDIAN;
    }
    private boolean southObby(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.OBSIDIAN;
    }
    private boolean westObby(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.OBSIDIAN;
    }

    private boolean northBrock(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.BEDROCK;
    }
    private boolean eastBrock(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.BEDROCK;
    }
    private boolean southBrock(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.BEDROCK;
    }
    private boolean westBrock(){
        Vec3d vec3d = BlockUtils.getInterpolatedPos(mc.player, 0);
        BlockPos playerPos = new BlockPos(vec3d);
        return mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.BEDROCK;
    }

}