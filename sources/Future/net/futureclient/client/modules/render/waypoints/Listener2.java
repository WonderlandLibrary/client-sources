package net.futureclient.client.modules.render.waypoints;

import net.futureclient.client.Xa;
import net.minecraft.client.gui.GuiGameOver;
import net.futureclient.client.events.EventGuiScreen;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Waypoints;
import net.futureclient.client.IF;
import net.futureclient.client.n;

public class Listener2 extends n<IF>
{
    public final Waypoints k;
    
    public Listener2(final Waypoints k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventGuiScreen)event);
    }
    
    public void M(final EventGuiScreen eventGuiScreen) {
        if (eventGuiScreen.M() instanceof GuiGameOver && Waypoints.e(this.k).M()) {
            String replaceAll = "";
            Listener2 listener2;
            if (Waypoints.getMinecraft24().isSingleplayer()) {
                replaceAll = "singleplayer";
                listener2 = this;
            }
            else if (Waypoints.getMinecraft35().getCurrentServerData() != null) {
                replaceAll = Waypoints.getMinecraft39().getCurrentServerData().serverIP.replaceAll(":", "_");
                listener2 = this;
            }
            else {
                if (Waypoints.getMinecraft19().isConnectedToRealms()) {
                    replaceAll = "realms";
                }
                listener2 = this;
            }
            final double double1 = Double.parseDouble(Waypoints.M(listener2.k).format(Waypoints.getMinecraft14().player.posX).replaceAll(",", "."));
            final double double2 = Double.parseDouble(Waypoints.M(this.k).format(Waypoints.getMinecraft8().player.posY).replaceAll(",", "."));
            final double double3 = Double.parseDouble(Waypoints.M(this.k).format(Waypoints.getMinecraft1().player.posZ).replaceAll(",", "."));
            if (this.k.e("Death") != null && (Waypoints.getMinecraft32().player.getDistance(this.k.e("Death").b(), this.k.e("Death").e(), this.k.e("Death").M()) > 0.0 || !replaceAll.equalsIgnoreCase(this.k.e("Death").b()))) {
                final Xa e = this.k.e("Death");
                if (Waypoints.M(this.k, e)) {
                    this.k.k.remove(e);
                }
                this.k.k.add(new Xa("Death", replaceAll, double1, double2, double3, Waypoints.getMinecraft31().world.provider.getDimensionType().getName()));
                return;
            }
            if (this.k.e("Death") == null) {
                this.k.k.add(new Xa("Death", replaceAll, double1, double2, double3, Waypoints.getMinecraft34().world.provider.getDimensionType().getName()));
            }
        }
    }
}
