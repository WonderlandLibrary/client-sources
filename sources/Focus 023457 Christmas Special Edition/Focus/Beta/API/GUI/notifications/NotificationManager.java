package Focus.Beta.API.GUI.notifications;

import Focus.Beta.IMPL.font.FontLoaders;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NotificationManager {

    private final int DEFAULT_DELAY = 2000;
    private final List NOTIFICATIONS = new CopyOnWriteArrayList();

    public List getNOTIFICATIONS(){
        return this.NOTIFICATIONS;
    }

    public void launch() {
        Iterator iterator = this.NOTIFICATIONS.iterator();
        if(iterator.hasNext()){
            Notification noti = (Notification) iterator.next();
            int index = this.NOTIFICATIONS.indexOf(noti) * 37;
            if(noti.getY() < 50 + index){
                noti.setY(MathHelper.clamp_double(noti.getY() + 0.5D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()), 0.0D, 50 + index));
            }

            if(noti.getY() < 50 + index){
                noti.setY(MathHelper.clamp_double(noti.getY() - 0.25D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()), 50 + index, 99999.0D));
            }

            String delayString = noti.getDelay() / 1000 + "";

            String delayString2 = " (" + delayString.substring(0, delayString.indexOf(".") + 2) + "s) ";
            if(noti.isExtending() && noti.getX() < Math.max(FontLoaders.arial18.getStringWidth(noti.getMessage() + delayString2), FontLoaders.arial22.getStringWidth(noti.getCallReason()) + 36)){
                noti.setX(MathHelper.clamp_double(noti.getX() + 0.5D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()), 0.0D, Math.max(
                        FontLoaders.arial18.getStringWidth(noti.getMessage() + delayString2), FontLoaders.arial22.getStringWidth(noti.getCallReason()) + 36)
                ));
                noti.getTimer().reset();
            }

            noti.setExtending(false);
            if(!noti.isExtending() && noti.getTimer().hasElapsed(noti.getDelay() + 150, false) && noti.getX() > 0.0D){
                noti.setX(noti.getX() - 0.5D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()));
            }

            if(noti.getX() <= 0.0D){
                this.remove(noti);
            }
        }
    }

    public void pop(@NotNull String message, @NotNull String CallReason, int delay, Type type){
        Notification notification = new Notification(message,CallReason,delay,type);
        Iterator iterator = this.NOTIFICATIONS.iterator();
        if(iterator.hasNext()){
            Notification notification1 = (Notification) iterator.next();
            if(notification.getMessage().equalsIgnoreCase(notification1.getMessage())){
                if(notification1.getX() >= (double)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(CallReason) + 63)){
                    notification1.getTimer().reset();
                }

                return;
            }
        }

        notification.setExtending(true);
        notification.getTimer().reset();
        this.add(notification);
    }

    public void add(@NotNull Notification notification){
        this.NOTIFICATIONS.add(notification);
    }
    private void remove(@Nullable Notification noti) {
        this.NOTIFICATIONS.remove(noti);
    }
}
