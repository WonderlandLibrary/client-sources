package com.client.glowclient.modules.server;

import net.minecraft.client.gui.inventory.*;
import java.util.concurrent.atomic.*;
import net.minecraftforge.fml.common.network.*;
import java.util.*;
import java.lang.reflect.*;
import net.minecraftforge.client.event.*;
import com.client.glowclient.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class XCarry extends ModuleContainer
{
    private boolean A;
    private GuiInventory B;
    private AtomicBoolean b;
    
    private void m() {
        this.d();
        this.a();
    }
    
    public static AtomicBoolean M(final XCarry xCarry) {
        return xCarry.b;
    }
    
    private void a() {
        final boolean a = false;
        this.B = null;
        this.b.set(false);
        this.A = a;
    }
    
    @SubscribeEvent
    public void M(final FMLNetworkEvent$ClientDisconnectionFromServerEvent fmlNetworkEvent$ClientDisconnectionFromServerEvent) {
        this.E();
    }
    
    public static <F, T extends F> void M(final F n, final T t) throws NoSuchFieldException, IllegalAccessException {
        Objects.requireNonNull(n);
        Objects.requireNonNull(t);
        final Field[] declaredFields = n.getClass().getDeclaredFields();
        final int length = declaredFields.length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final Field field;
            Objects.requireNonNull(field = declaredFields[n2]);
            final Field field2 = field;
            field2.setAccessible(true);
            if ((field2.getModifiers() & 0x8) == 0x0) {
                Objects.requireNonNull(field);
                final Field declaredField;
                Objects.requireNonNull(declaredField = Field.class.getDeclaredField("modifiers"));
                final Field field3 = declaredField;
                final Field field4 = field;
                final Field field5 = field;
                declaredField.setAccessible(true);
                field3.setInt(field4, field5.getModifiers() & 0xFFFFFFEF);
                field4.set(t, field.get(n));
            }
            i = ++n2;
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final GuiOpenEvent guiOpenEvent) {
        if (this.A) {
            guiOpenEvent.setCanceled(true);
            return;
        }
        if (guiOpenEvent.getGui() instanceof GuiInventory) {
            try {
                final xE gui;
                M(guiOpenEvent.getGui(), gui = new xE(this));
                guiOpenEvent.setGui((GuiScreen)gui);
                this.b.set(false);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public XCarry() {
        final boolean a = false;
        final GuiInventory b = null;
        super(Category.SERVER, "XCarry", false, -1, "Use your crafting grid as inventory slots");
        this.B = b;
        this.b = new AtomicBoolean(false);
        this.A = a;
    }
    
    public static boolean M(final XCarry xCarry) {
        return xCarry.A;
    }
    
    private void d() {
        if (this.b.compareAndSet(true, false) && Wrapper.mc.player != null) {
            this.A = true;
            Wrapper.mc.player.closeScreen();
            if (this.B != null) {
                final GuiInventory b = null;
                this.B.onGuiClosed();
                this.B = b;
            }
            this.A = false;
        }
    }
    
    @Override
    public void E() {
        Wrapper.mc.addScheduledTask(this::m);
    }
}
