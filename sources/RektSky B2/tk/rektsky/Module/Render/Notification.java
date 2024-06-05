package tk.rektsky.Module.Render;

import tk.rektsky.Module.Settings.*;
import tk.rektsky.Module.*;
import tk.rektsky.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.client.*;
import tk.rektsky.Utils.Display.*;

public class Notification extends Module
{
    public static PopupMessage currentNotification;
    public static PopupMessage waitingNotification;
    public ListSetting animationSetting;
    
    public Notification() {
        super("Notification", "Shows Notification instead of Chat Message", 0, Category.RENDER);
        this.animationSetting = new ListSetting("Animation", new String[] { "Sine", "Cubic", "Quint", "Circ", "Elastic", "Quad", "Quart", "Expo", "Back", "Bounce" }, "Elastic");
        this.toggle();
    }
    
    public static void displayNotification(final PopupMessage message) {
        if (!ModulesManager.getModuleByClass(Notification.class).isToggled()) {
            Client.addClientChat("[" + message.getTitle() + "] " + message.getMessage());
            return;
        }
        if (Notification.currentNotification != null) {
            Notification.currentNotification = message;
            return;
        }
        Notification.currentNotification = message;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof WorldTickEvent && Notification.currentNotification != null) {
            if (Notification.currentNotification.getShowTicks() > 0) {
                Notification.currentNotification.setShowTicks(Notification.currentNotification.getShowTicks() - 1);
            }
            else {
                Notification.currentNotification = null;
            }
        }
    }
    
    static {
        Notification.currentNotification = null;
        Notification.waitingNotification = null;
    }
    
    public static class PopupMessage
    {
        private String title;
        private String message;
        private int color;
        private int titleColor;
        private long firstRenderTime;
        private int showTicks;
        
        public PopupMessage(final String title, final String message, final int color, final int titleColor, final int showTicks) {
            this.title = title;
            this.message = message;
            this.color = color;
            this.showTicks = showTicks;
            this.titleColor = titleColor;
            this.firstRenderTime = Minecraft.getSystemTime();
        }
        
        public PopupMessage(final String title, final String message, final ColorUtil.NotificationColors color, final int showTicks) {
            this.title = title;
            this.message = message;
            this.color = color.getColor();
            this.showTicks = showTicks;
            this.titleColor = color.getTitleColor();
            this.firstRenderTime = Minecraft.getSystemTime();
        }
        
        public String getTitle() {
            return this.title;
        }
        
        public long getFirstRenderTime() {
            return this.firstRenderTime;
        }
        
        public void setTitle(final String title) {
            this.title = title;
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public void setMessage(final String message) {
            this.message = message;
        }
        
        public int getColor() {
            return this.color;
        }
        
        public void setColor(final int color) {
            this.color = color;
        }
        
        public int getShowTicks() {
            return this.showTicks;
        }
        
        public void setShowTicks(final int showTicks) {
            this.showTicks = showTicks;
        }
        
        public int getTitleColor() {
            return this.titleColor;
        }
        
        public void setTitleColor(final int titleColor) {
            this.titleColor = titleColor;
        }
    }
}
