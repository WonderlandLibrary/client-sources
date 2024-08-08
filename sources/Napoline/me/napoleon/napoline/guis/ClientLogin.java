package me.napoleon.napoline.guis;

import java.awt.Color;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import me.napoleon.napoline.GLSLSandboxShader;
import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.StringUtils;
import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.guis.utils.EmptyInputBox;
import me.napoleon.napoline.utils.client.HWIDUtil;
import me.napoleon.napoline.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ClientLogin extends GuiScreen {
    EmptyInputBox username;
    EmptyInputBox password;
    private static GLSLSandboxShader backgroundShader;
    private boolean loginsuccessfully = false;
    int anim = 140;
    String hwid = HWIDUtil.getHWID();
    private long initTime = System.currentTimeMillis();
    float runtime = 0f;
    public char[] server = {'h', 't', 't', 'p', 's', ':', '/', '/', 'n', 'a', 'p', 'o', 'l', 'i', 'n', 'e', '-', 'b', 'a', 'c', 'k', 'e', 'n', 'd', '.', 'h', 'e', 'r', 'o', 'k', Napoline.serverSecond[0], Napoline.serverSecond[1], Napoline.serverSecond[2], Napoline.serverSecond[3], Napoline.serverSecond[4], Napoline.serverSecond[5], Napoline.serverSecond[6], Napoline.serverSecond[7], Napoline.serverSecond[8], Napoline.serverSecond[9], Napoline.serverSecond[10], Napoline.serverSecond[11], Napoline.serverSecond[12]};

    @Override
    public void initGui() {
        super.initGui();
        try {
            this.backgroundShader = new GLSLSandboxShader("/shader.fsh");
        } catch (IOException e) {
            e.printStackTrace();
        }
        initTime = System.currentTimeMillis();
        username = new EmptyInputBox(4, mc.fontRendererObj, 20, 150, 100, 20);
        password = new EmptyInputBox(4, mc.fontRendererObj, 20, 180, 100, 20);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        try {
        	runtime += .01f;
        }catch(Exception e) {
        	runtime=0f;
        }


        if (loginsuccessfully) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
        //RenderUtils.drawImage(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new ResourceLocation("client/UI/bg.png"));
        //GL11.glTranslatef(100f, 2f, 1f);
        this.drawDefaultBackground();
        this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY, runtime);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1f,-1f);
        GL11.glVertex2f(-1f,1f);
        GL11.glVertex2f(1f,1f);
        GL11.glVertex2f(1f,-1f);
        GL11.glEnd();
        

        GL20.glUseProgram(0);

        username.yPosition = 100;
        password.yPosition = username.yPosition + 30;
        //RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(62, 66, 104).getRGB());
        //RenderUtils.drawRect(0, 0, 140, sr.getScaledHeight(), new Color(46,49,49).getRGB());
        
        GlStateManager.pushMatrix();
        FontLoaders.F10.drawString("Credit: glslsandbox.com/e#76072.0", sr.getScaledWidth()-79, sr.getScaledHeight()-7, new Color(40, 40, 40).getRGB());
        GlStateManager.popMatrix();
        
        RenderUtils.drawRoundedRect(2, 2, 138, sr.getScaledHeight()-2, new Color(46,49,49).getRGB(), new Color(28, 31, 31).getRGB());

        FontLoaders.F23.drawString("Welcome!", 37, 55, new Color(255, 255, 255).getRGB());
        //FontLoaders.F16.drawString("Enter your credentials to continue.", 10, 65, new Color(255, 255, 255).getRGB());


        RenderUtils.drawRoundRect(username.xPosition, username.yPosition, username.xPosition + username.getWidth(), username.yPosition + 20, username.isFocused() ? new Color(150, 150, 150).getRGB() : new Color(38,41,41).getRGB());
        RenderUtils.drawRoundRect(username.xPosition + 0.5f, username.yPosition + 0.5f, username.xPosition + username.getWidth() - 0.5f, username.yPosition + 20 - 0.5f, new Color(18,21,21).getRGB());

        if (!username.isFocused() && username.getText().isEmpty()) {
            FontLoaders.F16.drawString("USERNAME", username.xPosition + 4, username.yPosition + 7, new Color(180, 180, 180).getRGB());
        }

        RenderUtils.drawRoundRect(password.xPosition, password.yPosition, password.xPosition + password.getWidth(), password.yPosition + 20, password.isFocused() ? new Color(150, 150, 150).getRGB() : new Color(38,41,41).getRGB());
        RenderUtils.drawRoundRect(password.xPosition + 0.5f, password.yPosition + 0.5f, password.xPosition + password.getWidth() - 0.5f, password.yPosition + 20 - 0.5f, new Color(18,21,21).getRGB());
        if (!password.isFocused() && password.getText().isEmpty()) {
            FontLoaders.F16.drawString("PASSWORD", password.xPosition + 4, password.yPosition + 7, new Color(180, 180, 180).getRGB());
        } else {
            String xing = "";
            for (char c : password.getText().toCharArray()) {
                xing = xing + "*";

            }
            FontLoaders.F20.drawString(xing, password.xPosition + 4, password.yPosition + 6, new Color(255, 255, 255).getRGB());
        }

        username.drawTextBox();
        if (isHovered(password.xPosition, password.yPosition + 30, password.xPosition + password.getWidth(), password.yPosition + 50, mouseX, mouseY)) {
            if (Mouse.isButtonDown(0)) {
            	new Thread(()->{
    				try {
    					String userName = System.getProperty("os.name").toLowerCase().contains("windows") ? new com.sun.security.auth.module.NTSystem().getName() : System.getProperty("user.name");
    					HttpClient httpClient = HttpClientBuilder.create().build();
    					HttpGetWithEntity e = new HttpGetWithEntity();
    					e.setURI(new URI(StringUtils.stringify(server)));
    					e.setHeader("User-Agent", "Mozila/5.0 (Windows NT 10.0; Win64; x64; rv:93.0) Gecko/20100101 Firefox/93.0");
    					HttpEntity payload = new StringEntity("{"
    							+ "\n  \"hwid\": \""+hwid+"\","
    							+ "\n  \"name\": \""+userName+"\","
    							+ "\n  \"did\": \""+"development"+"\","
    							+ "\n  \"username\": \""+username.getText()+"\","
    							+ "\n  \"password\": \""+hashPassword(password.getText())+"\","
    							+ "\n  \"version\": \""+Napoline.CLIENT_Ver+"\""
    							+ "\n}", ContentType.APPLICATION_JSON);

    					e.setEntity(payload);
    					HttpResponse response = httpClient.execute(e);
    					if(contains_(response.getStatusLine().toString(),new char[] {'2','6','9'})) {
    						loginsuccessfully = !loginsuccessfully;
    	                    Napoline.username = username.getText();
    					}
    	            } catch (Exception e) {
    	            }
    			}).start();
            }
            RenderUtils.drawRoundRect(password.xPosition, password.yPosition + 30, password.xPosition + password.getWidth(), password.yPosition + 50, new Color(107, 141, 205).getRGB());
            FontLoaders.F16.drawCenteredString("Login", password.xPosition + password.getWidth() / 2, password.yPosition + 38, new Color(255, 255, 255).getRGB());
        } else {
            RenderUtils.drawRoundRect(password.xPosition, password.yPosition + 30, password.xPosition + password.getWidth(), password.yPosition + 50, new Color(77, 111, 175).getRGB());
            FontLoaders.F16.drawCenteredString("Login", password.xPosition + password.getWidth() / 2, password.yPosition + 38, new Color(255, 255, 255).getRGB());
        }
        if (isHovered(password.xPosition, password.yPosition + 60, password.xPosition + FontLoaders.F14.getStringWidth("Register"), password.yPosition + 70, mouseX, mouseY)) {
            if (Mouse.isButtonDown(0)) {
                //Register
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://discord.gg/CMsUPnVCTF");
                } catch (IOException e) {
                	e.printStackTrace();
                }
            }
            FontLoaders.F14.drawString("Register", password.xPosition, password.yPosition + 60, new Color(77, 111, 175).getRGB());
        } else {
            FontLoaders.F14.drawString("Register", password.xPosition, password.yPosition + 60, new Color(150, 150, 150).getRGB());
        }


        if (isHovered(password.xPosition + password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid"), password.yPosition + 60, password.xPosition + password.getWidth(), password.yPosition + 70, mouseX, mouseY)) {
            if (Mouse.isButtonDown(0)) {
                setClipboardString(hwid);
            }
            FontLoaders.F14.drawString("Copy HWID", password.xPosition + password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid"), password.yPosition + 60, new Color(77, 111, 175).getRGB());//77,111,175
        } else {
            FontLoaders.F14.drawString("Copy HWID", password.xPosition + password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid"), password.yPosition + 60, new Color(150, 150, 150).getRGB());//77,111,175
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean contains_(String s, char[] t) {
        char[] array1 = s.toCharArray();
        char[] array2 = t;
        boolean status = false;

        if (array2.length < array1.length) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] == array2[0] && i + array2.length - 1 < array1.length) {
                    int j = 0;
                    while (j < array2.length) {
                        if (array1[i + j] == array2[j]) {
                            j++;
                        } else
                            break;
                    }
                    if (j == array2.length) {
                        status = true;
                        break;
                    }
                }

            }
        }
        return status;
    }
    

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	switch (keyCode) {
		case Keyboard.KEY_TAB:
	        if (username.isFocused()) {
	        	if(keyCode == Keyboard.KEY_TAB) {
	        		password.setFocused(true);
	        		username.setFocused(false);
	        		return;
	        	}
	        }
			break;
		case Keyboard.KEY_RETURN:
			new Thread(()->{
				try {
					String userName = System.getProperty("os.name").toLowerCase().contains("windows") ? new com.sun.security.auth.module.NTSystem().getName() : System.getProperty("user.name");
					HttpClient httpClient = HttpClientBuilder.create().build();
					HttpGetWithEntity e = new HttpGetWithEntity();
					e.setURI(new URI(StringUtils.stringify(server)));
					e.setHeader("User-Agent", "Mozila/5.0 (Windows NT 10.0; Win64; x64; rv:93.0) Gecko/20100101 Firefox/93.0");
					HttpEntity payload = new StringEntity("{"
							+ "\n  \"hwid\": \""+hwid+"\","
							+ "\n  \"name\": \""+userName+"\","
							+ "\n  \"did\": \""+"development"+"\","
							+ "\n  \"username\": \""+username.getText()+"\","
							+ "\n  \"password\": \""+hashPassword(password.getText())+"\","
							+ "\n  \"version\": \""+Napoline.CLIENT_Ver+"\""
							+ "\n}", ContentType.APPLICATION_JSON);

					e.setEntity(payload);
					HttpResponse response = httpClient.execute(e);
					if(contains_(response.getStatusLine().toString(),new char[] {'2','6','9'})) {
						loginsuccessfully = !loginsuccessfully;
	                    Napoline.username = username.getText();
					}
	            } catch (Exception e) {
	            }
			}).start();
			
			break;
		default:
	        if (username.isFocused()) {
	            username.textboxKeyTyped(typedChar, keyCode);
	        }
	        if (password.isFocused()) {
	            password.textboxKeyTyped(typedChar, keyCode);
	        }
			break;
		}
    }
    
    public String hashPassword(String password) {
    	try { 
            MessageDigest md = MessageDigest.getInstance("SHA-512"); 
            byte[] messageDigest = md.digest(password.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        } 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
