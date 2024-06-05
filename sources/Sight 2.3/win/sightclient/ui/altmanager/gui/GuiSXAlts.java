package win.sightclient.ui.altmanager.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.lwjgl.input.Keyboard;

import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import win.sightclient.Sight;

public class GuiSXAlts extends GuiScreen {

    private GuiPasswordField passsword;
    private final GuiScreen previousScreen;
    private static AltLoginThread thread;
    private GuiTextField username;
    
    private static String status = "";
    
    protected Minecraft mc = Minecraft.getMinecraft();
    
    public GuiSXAlts(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
        thread = null;
        status = EnumChatFormatting.YELLOW + "Waiting...";
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 1: {
                if (Sight.instance.sxAlts.equals("")) {
                	this.status = EnumChatFormatting.RED + "Please log in.";
                } else {
                	Thread generate = new Thread(new Runnable() {

						@Override
						public void run() {
		                	status = generate();
						}
                		
                	});
                	generate.start();
                }
                break;
            }
            case 0: {
            	if (!this.username.getText().isEmpty() && !this.passsword.getText().isEmpty()) {
            		this.status = EnumChatFormatting.YELLOW + "Logging in...";
            		
            		JsonObject json = new JsonObject();
            		json.addProperty("username", this.username.getText());
            		json.addProperty("password", this.passsword.getText());

            		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            		try {
            		    HttpPost request = new HttpPost("https://sx-alts-server.herokuapp.com/api/login");
            		    StringEntity params = new StringEntity(json.toString());
            		    request.addHeader("content-type", "application/json");
            		    request.setEntity(params);
            		    CloseableHttpResponse c = httpClient.execute(request);
            		    BufferedReader br = new BufferedReader(new InputStreamReader(c.getEntity().getContent()));
            		    
            		    String line = br.readLine();
            		    
            		    if (line != null) {
            		    	boolean success = Boolean.parseBoolean(line.split(":")[1].split(",")[0]);
            		    	if (success) {
            		    		this.status = EnumChatFormatting.GREEN + "Logged in successfully";
            		    		Sight.instance.sxAlts = line.split(":")[2].substring(1, line.split(":")[2].length() - 2);
            		    		Sight.instance.fileManager.saveDataFile();
            		    	} else {
            		    		this.status = EnumChatFormatting.RED + line.split(":")[2].substring(1, line.split(":")[2].length() - 2);
            		    	}
            		    } else {
            		    	this.status = EnumChatFormatting.RED + "There was a error logging in.";
            		    }
            		} catch (Exception ex) {
            		    ex.printStackTrace();
            		} finally {
            		    httpClient.close();
            		}
            	} else {
            		this.status = EnumChatFormatting.RED + "Please enter a correct a username & a password";
            	}
                break;
            }
            case 2: {
            	Minecraft.getMinecraft().displayGuiScreen(this.previousScreen);
                break;
            }
        }
    }
    
    public static String generate() {
    	if (Sight.instance.sxAlts.equals("")) {
    		return EnumChatFormatting.RED + "Please log in with a S-X account";
    	}
    	
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://sx-alts-server.herokuapp.com/api/alt");
        
        request.addHeader("token", Sight.instance.sxAlts);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            if (entity != null) {
            	String status = EnumChatFormatting.RED + "There was a error generating";
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                boolean success = Boolean.parseBoolean(result.split(":")[1].split(",")[0]);
		    	if (success) {
		    		status = EnumChatFormatting.YELLOW + "Logging in...";
		    		String alt = result.substring(23, result.length() - 2);
		    		if (alt.contains(":")) {
		    			System.out.println("Contains");
		    			thread = new AltLoginThread(alt.split(":")[0], alt.split(":")[1], true);
		    			thread.start();
		    		}
		    	} else {
		    		status = EnumChatFormatting.RED + result.split(":")[2].substring(1, result.split(":")[2].length() - 2);
		    	}
                return status;
            }
        } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return EnumChatFormatting.RED + "There was a error generating";
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float z) {
    	this.drawDefaultBackground();
        this.passsword.drawTextBox();
        this.username.drawTextBox();
        if (this.thread != null) {
        	this.status = thread.getStatus();
        }
        mc.fontRendererObj.drawCenteredString("Alt Login", this.width / 2, 20, -1);
        mc.fontRendererObj.drawStringWithShadow((Sight.sxAlts.equals("") ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Logging in: " + !Sight.sxAlts.equals(""), 20, 20, -1);
        mc.fontRendererObj.drawCenteredString(status, this.width / 2, 29, -1);
        if (this.passsword.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow("Password", (float)(this.width / 2 - 94), this.passsword.isFocused() ? 129.0f : 131.0f, -7829368);
        }
        if (this.username.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow("Username", (float)(this.width / 2 - 94), this.username.isFocused() ? 104.0f : 106.0f, -7829368);
        }
        super.drawScreen(x, y, z);
    }
    
    @Override
    public void initGui() {
        final int var3 = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, 175, "Generate"));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 150, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, 198, "Back"));
        this.passsword = new GuiPasswordField(this.fontRendererObj, this.width / 2 - 98, 125, 195, 20);
        this.username = new GuiTextField(var3, this.fontRendererObj, this.width / 2 - 98, 100, 195, 20);
        this.passsword.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void keyTyped(final char character, final int key) throws IOException {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t' && (this.passsword.isFocused() || this.username.isFocused())) {
            this.passsword.setFocused(!this.passsword.isFocused());
            this.username.setFocused(!this.username.isFocused());
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.passsword.textboxKeyTyped(character, key);
        this.username.textboxKeyTyped(character, key);
    }
    
    @Override
    protected void mouseClicked(final int x, final int y, final int button) {
        try {
            super.mouseClicked(x, y, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.passsword.mouseClicked(x, y, button);
        this.passsword.mouseClicked(x, y, button);
        this.username.mouseClicked(x, y, button);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.passsword.updateCursorCounter();
        this.passsword.updateCursorCounter();
        this.username.updateCursorCounter();
    }
    
    
}
