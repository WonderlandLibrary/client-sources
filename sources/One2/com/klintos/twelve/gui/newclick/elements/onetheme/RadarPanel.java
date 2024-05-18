// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick.elements.onetheme;

import com.klintos.twelve.Twelve;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.gui.newclick.elements.base.Element;
import com.klintos.twelve.gui.newclick.ClickGui;
import com.klintos.twelve.gui.newclick.elements.base.Panel;

public class RadarPanel extends Panel
{
    public RadarPanel(final String title, final int posX, final int posY, final boolean expanded, final boolean pinned, final ClickGui parent) {
        super(title, posX, posY, expanded, pinned, parent);
        final Element radar = new ElementRadar(posX + 2, posY + this.getHeight(), this);
        this.addElement(radar);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        this.updatePanel(mouseX, mouseY);
        GuiUtils.drawBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + this.getWidth() + this.dragX, this.getPosY() + (this.isExpanded() ? this.getOpenHeight() : this.getHeight()) + this.dragY, this.isPinned() ? -36752 : -11184811, -13421773);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getTitle(), this.getPosX() + 5 + this.dragX, this.getPosY() + 7 + this.dragY, -36752);
        if (this.isExpanded()) {
            for (final Element element : this.getElements()) {
                element.draw(mouseX, mouseY);
            }
        }
    }
    
    public class ElementRadar extends Element
    {
        private ArrayList<Player> players;
        
        public ElementRadar(final int posX, final int posY, final Panel parent) {
            super(posX, posY, 96, 20, parent);
            this.players = new ArrayList<Player>();
        }
        
        @Override
        public void draw(final int mouseX, final int mouseY) {
            final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
            GuiUtils.drawFineBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + this.getWidth() + this.dragX, this.getPosY() + this.getHeight() + this.dragY, isHover ? -36752 : -12303292, -13882324);
            int y = 2;
            for (final Object ent : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (ent instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)ent;
                    if (player.equals((Object)Minecraft.getMinecraft().thePlayer)) {
                        continue;
                    }
                    final boolean isPlayerHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + y + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + 9 + y + this.dragY;
                    String col = "a";
                    final int dist = (int)Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)player);
                    if (dist <= 16) {
                        col = "c";
                    }
                    else if (dist <= 24) {
                        col = "6";
                    }
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(player.getName(), this.getPosX() + 2 + this.dragX, this.getPosY() + y + this.dragY, Twelve.getInstance().getFriendHandler().isFriend(player.getGameProfile().getName()) ? -43691 : (isPlayerHover ? -43691 : -1));
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("§f[§" + col + dist + "§f]", this.getPosX() + this.getWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth("[§" + col + dist + "]") - 2 + this.dragX, y + this.getPosY() + this.dragY, -1);
                    y += 10;
                }
            }
            if (y == 2) {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("No one in range.", this.getPosX() + 2 + this.dragX, y + this.getPosY() + this.dragY, -1);
                this.setHeight(12);
                this.getParent().setOpenHeight(34);
            }
            else {
                this.setHeight(y);
                this.getParent().setOpenHeight(y + 22);
            }
        }
        
        @Override
        public void mouseClicked(final int mouseX, final int mouseY, final int button) {
            final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
            if (isHover) {
                if (button == 0) {
                    for (final Player p : this.players) {
                        if (p.hover) {
                            Twelve.getInstance().getFriendHandler().isFriend(p.name);
                        }
                    }
                }
                else if (button == 1) {
                    for (final Player p : this.players) {
                        if (p.hover) {
                            Twelve.getInstance().getFriendHandler().isFriend(p.name);
                        }
                    }
                }
            }
        }
        
        public class Player
        {
            public String name;
            public boolean hover;
            
            public Player(final String name, final boolean hover) {
                this.name = name;
                this.hover = hover;
            }
        }
    }
}
