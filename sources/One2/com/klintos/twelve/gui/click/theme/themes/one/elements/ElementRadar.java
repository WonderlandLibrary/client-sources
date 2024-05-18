// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.theme.themes.one.elements;

import com.klintos.twelve.utils.FileUtils;
import com.klintos.twelve.handlers.notifications.Notification;
import java.util.Iterator;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.utils.NahrFont;
import com.klintos.twelve.Twelve;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import com.klintos.twelve.gui.click.elements.ElementBase;

public class ElementRadar extends ElementBase
{
    private ArrayList<Player> players;
    
    public ElementRadar() {
        super(14);
        this.players = new ArrayList<Player>();
    }
    
    @Override
    public void drawElement(final int POSX, final int POSY, final int MOUSEX, final int MOUSEY) {
        this.setPosX(POSX);
        this.setPosY(POSY);
        int y = 0;
        for (final Object ent : Minecraft.getMinecraft().theWorld.playerEntities) {
            if (ent instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)ent;
                if (player.equals((Object)Minecraft.getMinecraft().thePlayer)) {
                    continue;
                }
                final boolean isHover = MOUSEX >= this.getPosX() && MOUSEY >= this.getPosY() + 12 + y && MOUSEX <= this.getPosX() + this.getWidth() / 2 / 2 + 78 && MOUSEY <= this.getPosY() + 21 + y;
                this.players.add(new Player(player.getName(), isHover));
                final String d = "§";
                String dc = "a";
                final int dist = (int)Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)player);
                if (dist <= 16) {
                    dc = "c";
                }
                else if (dist <= 24) {
                    dc = "6";
                }
                y += 10;
                Twelve.getInstance().guiFont.drawString("§f[" + d + dc + dist + "§f]", this.getPosX() + this.getWidth() - Twelve.getInstance().guiFont.getStringWidth("[" + d + dc + dist + "]") + 8.0f, y + this.getPosY() + 3, NahrFont.FontType.PLAIN, -1, 0);
                Twelve.getInstance().guiFont.drawString(player.getGameProfile().getName(), this.getPosX() + 2, y + this.getPosY() + 3, NahrFont.FontType.PLAIN, Twelve.getInstance().getFriendHandler().isFriend(player.getGameProfile().getName()) ? -43691 : (isHover ? -43691 : -1), 0);
            }
        }
        if (y == 0) {
            y = 10;
            Twelve.getInstance().guiFont.drawString("§fNo people in range.", this.getPosX() + 2, y + this.getPosY() + 3, NahrFont.FontType.PLAIN, -1, 0);
        }
        GuiUtils.drawFineBorderedRect(this.getPosX(), this.getPosY() + 10, this.getPosX() + this.getWidth() / 2 / 2 + 79, this.getPosY() + y + 13, -8947849, 452984831);
        this.setHeight(y);
    }
    
    @Override
    public void mouseClicked(final int par1, final int par2, final int par3) {
        if (par1 >= this.getPosX() && par2 >= this.getPosY() + 4 && par1 <= this.getPosX() + this.getWidth() / 2 / 2 + 78 && par2 <= this.getPosY() + this.getHeight() + 5) {
            if (par3 == 0) {
                for (final Player p : this.players) {
                    if (p.hover && !Twelve.getInstance().getFriendHandler().isFriend(p.name)) {
                        Twelve.getInstance().getFriendHandler().addFriend(p.name);
                        Twelve.getInstance().getNotificationHandler().addNotification(new Notification(String.valueOf(p.name) + " is now your friend.", -43691));
                        FileUtils.saveFriends();
                    }
                }
            }
            else if (par3 == 1) {
                for (final Player p : this.players) {
                    if (p.hover && Twelve.getInstance().getFriendHandler().isFriend(p.name)) {
                        Twelve.getInstance().getFriendHandler().delFriend(p.name);
                        Twelve.getInstance().getNotificationHandler().addNotification(new Notification(String.valueOf(p.name) + " is no longer your friend.", -43691));
                        FileUtils.saveFriends();
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
