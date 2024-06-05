package me.superskidder.lune.modules.combat;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import me.superskidder.lune.Lune;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.utils.client.FileUtil;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.render.RenderUtils;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Num;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;

public class Music extends Mod {
    public File musicFile;
    AudioClip audioClip = null;
    private Bool<Boolean> pictureOn = new Bool<>("Picture", true);
    private Num<Double> pictureX = new Num<>("X", 0.0, -100.0, 1000.0);
    private Num<Double> pictureY = new Num<>("Y", 0.0, -100.0, 1000.0);
    private Num<Double> pictureWidth = new Num<>("Width", 300.0, 25.0, 700.0);
    private Num<Double> pictureHeight = new Num<>("Height", 300.0, 25.0, 700.0);
    
    public Music(){
        super("Music", ModCategory.Combat,"Music improve your power");;
        this.addValues(pictureOn, pictureX, pictureY, pictureWidth, pictureHeight);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onEnabled(){
    	musicFile = new File(Lune.luneDataFolder.getAbsolutePath() + File.separator + "Music.m4a");
    	
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String fileURL = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/Client/Music"));
					String oldURL = FileUtil.readFile("MusicURL");
					if(!oldURL.contains(fileURL)) {
						FileUtil.saveFile( "MusicURL", fileURL);
						musicFile.delete();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();

    	musicFile = new File(Lune.luneDataFolder.getAbsolutePath() + File.separator + "Music.m4a");
        
    	if(!musicFile.exists()) {
    		new Thread(new Runnable() {
    			@Override
    			public void run() {
    				PlayerUtil.sendMessage("Downloading Music...");
    				try {
						String fileURL = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/Client/Music"));
						String url = "https://qian-xia233.coding.net/api/share/download/" + fileURL;
						downloadFile(url, musicFile.getAbsolutePath(), url);
					} catch (Exception e) {
						e.printStackTrace();
						PlayerUtil.sendMessage("Failed to get music...");
						ModuleManager.getModByClass(Music.class).setStage(false);;
					}
    				PlayerUtil.sendMessage("Finished to download the Music.");

    				try {
    					audioClip = Applet.newAudioClip(musicFile.toURL());
    				} catch (MalformedURLException e1) {
    					e1.printStackTrace();
    				}
    		        audioClip.play();
    			}
    		}).start();;
    	} else {
			try {
				audioClip = Applet.newAudioClip(musicFile.toURL());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
	        audioClip.play();
    	}
    }

    @Override
    public void onDisable(){
    	try {
			audioClip.stop();
		}catch (NullPointerException e){
    		// 可能是音乐下载失败或音乐不存在才会导致audioClip为null
		}
    }
    
    TimerUtil gitTimerUtil = new TimerUtil();
    int num = 1;
    
    @EventTarget
    public void onRender(EventRender2D e){
    	if(!this.pictureOn.getValue()) {
    		return;
    	}
        //ScaledResolution sr = new ScaledResolution((mc));
        //RenderUtils.drawRect((float)sr.getScaledWidth_double()/2-35,90,(float)sr.getScaledWidth_double()/2+mc.fontRendererObj.getStringWidth("Now playing Lune Client V1.0 Theme Song")+10,120,new Color(33,33,33,180).getRGB());
        //mc.fontRendererObj.drawStringWithShadow("Now playing Lune Client V1.0 Theme Song",(float)sr.getScaledWidth_double()/2-30,100,-1);
    	
        if(gitTimerUtil.delay(90)) {
            if(num == 28) {
            	num = 1; 
            }else {
            	num++;
            }
            
            gitTimerUtil.reset();
        }

        
        RenderUtils.drawImage(this.pictureX.getValue().intValue(), this.pictureY.getValue().intValue(), this.pictureWidth.getValue().intValue(), this.pictureHeight.getValue().intValue(), new ResourceLocation("client/xD/" + num + ".png"), new Color(255, 255, 255));
    }
    
	public static File downloadFile(String urlPath, String fileSavePath, String referer) throws Exception {
		File file = null;
		BufferedInputStream bin = null;
		OutputStream out = null;
		try {
			URL url = new URL(urlPath);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 绕过Coding检测
			httpURLConnection.setRequestProperty("Referer", referer);
			httpURLConnection.connect();

			int fileLength = httpURLConnection.getContentLength();

			String filePathUrl = httpURLConnection.getURL().getFile();
			String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
			fileFullName = fileFullName.substring(fileFullName.lastIndexOf("/") + 1);

			url.openConnection();

			bin = new BufferedInputStream(httpURLConnection.getInputStream());

			String path = fileSavePath;
			file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			out = new FileOutputStream(file);
			int size = 0;
			int len = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				len += size;
				out.write(buf, 0, size);

			}
			return file;
		} catch (Exception e) {
			throw new Exception(e.toString());
		} finally {
			if (bin != null) {
				bin.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
