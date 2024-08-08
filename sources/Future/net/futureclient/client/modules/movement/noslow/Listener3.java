package net.futureclient.client.modules.movement.noslow;

import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.client.db;
import net.futureclient.client.pg;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.ZG;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiOptions;
import net.futureclient.client.KC;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.xe;
import net.futureclient.client.n;

public class Listener3 extends n<xe>
{
    public final NoSlow k;
    
    public Listener3(final NoSlow k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xe)event);
    }
    
    @Override
    public void M(final xe xe) {
        if (this.k.inventoryMove.M() && (NoSlow.getMinecraft29().currentScreen instanceof GuiInventory || NoSlow.getMinecraft5().currentScreen instanceof KC || NoSlow.getMinecraft4().currentScreen instanceof GuiOptions || NoSlow.getMinecraft23().currentScreen instanceof GuiVideoSettings || NoSlow.getMinecraft7().currentScreen instanceof GuiScreenOptionsSounds || NoSlow.getMinecraft28().currentScreen instanceof GuiContainer || NoSlow.getMinecraft30().currentScreen instanceof GuiIngameMenu)) {
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP player = NoSlow.getMinecraft26().player;
                player.rotationPitch -= 2.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP player2 = NoSlow.getMinecraft1().player;
                player2.rotationPitch += 2.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP player3 = NoSlow.getMinecraft2().player;
                player3.rotationYaw -= 2.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP player4 = NoSlow.getMinecraft14().player;
                player4.rotationYaw += 2.0f;
            }
            if (Keyboard.isKeyDown(29) && ZG.M()) {
                NoSlow.getMinecraft10().player.setSprinting(true);
            }
            if (NoSlow.getMinecraft15().player.rotationPitch > 90.0f) {
                NoSlow.getMinecraft18().player.rotationPitch = 90.0f;
            }
            if (NoSlow.getMinecraft6().player.rotationPitch < -90.0f) {
                NoSlow.getMinecraft17().player.rotationPitch = -90.0f;
            }
        }
        NoSlow.M(this.k);
        if (NoSlow.getMinecraft3().player.isActiveItemStackBlocking()) {
            final Speed speed = (Speed)pg.M().M().M((Class)db.class);
            if (this.k.items.M() && ZG.b() && NoSlow.getMinecraft20().player.onGround && speed != null && !speed.M()) {
                NoSlow.getMinecraft12().player.motionY = 1.273197475E-314;
            }
        }
    }
}
