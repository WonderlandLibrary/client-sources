package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.RunCommandEvent;
import net.silentclient.client.utils.AsyncScreenshots.ImageSelection;
import org.lwjgl.Sys;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ScreenshotManager {
	private File screenshot;
	private BufferedImage image;
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	public ScreenshotManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void setScreenshot(File screenshot) {
		this.screenshot = screenshot;
	}
	
	public File getScreenshot() {
		return screenshot;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

    @EventTarget()
    public void command(RunCommandEvent event) {
        if(event.getCommand().startsWith("/slc$copy ")) {
            String message = event.getCommand().replace("/slc$copy ", "");
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(message), null);
            event.setCancelled(true);
            return;
        }
    	switch(event.getCommand()) {
    	case "/$openfolder":
    		File file1 = new File(mc.mcDataDir, "screenshots");
            String s = file1.getAbsolutePath();

            if (Util.getOSType() == Util.EnumOS.OSX)
            {
                try
                {
                	Client.logger.info(s);
                    Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
                    return;
                }
                catch (IOException ioexception1)
                {
                	Client.logger.error((String)"Couldn\'t open file", (Throwable)ioexception1);
                }
            }
            else if (Util.getOSType() == Util.EnumOS.WINDOWS)
            {
                String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {s});

                try
                {
                    Runtime.getRuntime().exec(s1);
                    return;
                }
                catch (IOException ioexception)
                {
                    Client.logger.error((String)"Couldn\'t open file", (Throwable)ioexception);
                }
            }

            boolean flag = false;

            try
            {
                Class<?> oclass = Class.forName("java.awt.Desktop");
                Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {file1.toURI()});
            }
            catch (Throwable throwable)
            {
            	Client.logger.error("Couldn\'t open link", throwable);
                flag = true;
            }

            if (flag)
            {
            	Client.logger.info("Opening via system class!");
                Sys.openURL("file://" + s);
            }
            event.setCancelled(true);
            break;
    	case "/$delete":
    		try {
                if (screenshot.exists() && screenshot.delete()) {
                    NotificationUtils.showNotification("success", "Image has been deleted.");
                    screenshot = null;
                } else {
                    NotificationUtils.showNotification("success", "Couldn't find image.");
                }
            } catch (NullPointerException e) {
            	
            }
            event.setCancelled(true);
    		break;
    	case "/$copyss":
    		try {
    			copyScreenshot(true);
    		} catch(Exception e) {
                NotificationUtils.showNotification("success", e.getMessage());
    		}
            event.setCancelled(true);
    		break;
    		
        default:
        	break;
    	}
    	
    }
    
    public void copyScreenshot(boolean message) throws HeadlessException {
        final ImageSelection sel = new ImageSelection(image);
        Multithreading.runAsync(() -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null));

        if (message) {
            NotificationUtils.showNotification("success", "Image copied!");
        }
    }
}
