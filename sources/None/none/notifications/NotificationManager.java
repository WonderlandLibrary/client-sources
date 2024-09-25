/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package none.notifications;

import java.util.concurrent.LinkedBlockingQueue;

import net.minecraft.client.Minecraft;
import none.module.modules.render.NotificationHUD;

public class NotificationManager {
    
    private LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
    
    private Notification currentNotification = null;
    
    private LinkedBlockingQueue<Notification> SPpendingNotifications = new LinkedBlockingQueue<>();
    private Notification SPcurrentNotification = null;
    
    private Minecraft mc = Minecraft.getMinecraft();
    
    public void show(Notification notification) {
        pendingNotifications.add(notification);
    }
    
    public void showSP(Notification notification) {
        SPpendingNotifications.add(notification);
    }

    public void update() {
    	if (mc.thePlayer != null && mc.thePlayer.ticksExisted < 1) {
    		pendingNotifications.clear();
    	}
        if (currentNotification != null && !currentNotification.isShown()) {
            currentNotification = null;
        }

        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            if (currentNotification.getType() != NotificationType.SP) {
		        if (NotificationHUD.NotificationType.getSelected().equalsIgnoreCase("Slow")) {
		        	currentNotification.show();
		        }else if (NotificationHUD.NotificationType.getSelected().equalsIgnoreCase("Instant")) {
		        	currentNotification.showInstant();
		        }
            }
        }
    }
    
    public void SPupdate() {
        if (SPcurrentNotification != null && !SPcurrentNotification.isShown()) {
        	SPcurrentNotification = null;
        }

        if (SPcurrentNotification == null && !SPpendingNotifications.isEmpty()) {
        	SPcurrentNotification = SPpendingNotifications.poll();
        	if (SPcurrentNotification.getType() == NotificationType.SP) {
	            if (NotificationHUD.NotificationType.getSelected().equalsIgnoreCase("Slow")) {
	            	SPcurrentNotification.show();
	            }else if (NotificationHUD.NotificationType.getSelected().equalsIgnoreCase("Instant")) {
	            	SPcurrentNotification.showInstant();
	            }
        	}
        }

    }

    public void render() {
        update();
        if (currentNotification != null && currentNotification.getType() != NotificationType.SP)
            currentNotification.render();
    }
    
    public void renderSP() {
        SPupdate();
        if (SPcurrentNotification != null && SPcurrentNotification.getType() == NotificationType.SP)
            SPcurrentNotification.render();
    }
    
    public void Clear() {
    	pendingNotifications.clear();
    }
}
