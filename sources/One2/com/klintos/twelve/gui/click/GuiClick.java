package com.klintos.twelve.gui.click;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.klintos.twelve.Twelve;
import com.klintos.twelve.gui.click.panel.PanelBase;
import com.klintos.twelve.gui.click.theme.themes.one.elements.ElementRadar;
import com.klintos.twelve.gui.click.theme.themes.one.gui.GuiClickClient;
import com.klintos.twelve.mod.ModCategory;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.utils.MP3Utils;
import com.klintos.twelve.utils.NahrFont;

import jaco.mp3.player.MP3Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;

public class GuiClick extends GuiScreen
{
    private static final List<PanelBase> GUI_PANELS;
    private int par1;
    private int par2;
    private MP3Utils mp3Utils;
    private static final File clientDir;
    private static final File guiFile;
    private static boolean THEME_FIRSTLOADED;
    private int xx;
    private int k;
    private int drag;
    private float scroll;
    private ArrayList<ListItem> items;
    
    static {
        GUI_PANELS = new ArrayList<PanelBase>();
        clientDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "ONE2");
        guiFile = new File(GuiClick.clientDir + File.separator + "gui.txt");
    }
    
    public List<PanelBase> getPanels() {
        return GuiClick.GUI_PANELS;
    }
    
    public GuiClick() {
        this.mp3Utils = new MP3Utils();
        this.items = new ArrayList<ListItem>();
        this.loadPanels();
        final File folder = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\music\\");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File[] mp3;
        for (int length = (mp3 = MP3Utils.findMP3(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\music\\")).length, i = 0; i < length; ++i) {
            final File file = mp3[i];
            Twelve.getInstance().getMP3Player();
            if (!MP3Player.playlist.contains(file)) {
                Twelve.getInstance().getMP3Player();
                MP3Player.playlist.add(file);
            }
            System.out.println("FOUND IN LOCAL: " + file.getName());
        }
    }
    
    public void loadPanels() {
        if (!GuiClick.clientDir.exists()) {
            GuiClick.clientDir.mkdir();
        }
        if (!this.getAlreadyLoaded()) {
            this.loadGuiSettings();
            this.setAlreadyLoaded(true);
        }
        GuiClick.GUI_PANELS.clear();
        int i = 2;
        for (final ModCategory type : ModCategory.values()) {
            final PanelBase panel = new GuiClickClient(this, type.name(), 2, i, type.toString());
            GuiClick.GUI_PANELS.add(panel);
            i += 22;
        }
        final GuiClickClient values = new GuiClickClient(this, "Radar", 2, i, "Radar");
        GuiClick.GUI_PANELS.add(values);
        this.loadGuiSettings();
    }
    
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.par1 = par1;
        this.par2 = par2;
        for (final PanelBase panel : this.getPanels()) {
            panel.drawScreen(par1, par2);
        }
//        this.drawMusic(par1, par2);
        drawRect(0, 0, 0, 0, 0);
        super.drawScreen(par1, par2, par3);
    }
    
    public void drawPinned() {
        try {
            for (final PanelBase panel : this.getPanels()) {
                if (panel.getPin()) {
                    panel.drawScreen(this.par1, this.par2);
                }
            }
            drawRect(0, 0, 0, 0, 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final PanelBase panel : this.getPanels()) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        this.saveGuiSettings();
        boolean hoverNext;
        boolean listHover;
        boolean hoverPlay = mouseX > this.width - this.xx && mouseY > this.height - 72 && mouseX < this.width + 70 - this.xx && mouseY < this.height - 56;
        boolean hoverShuffle = mouseX > this.width - this.xx + 74 && mouseY > this.height - 72 && mouseX < this.width + 144 - this.xx && mouseY < this.height - 56;
        boolean hoverPrev = mouseX > this.width - this.xx && mouseY > this.height - 52 && mouseX < this.width + 70 - this.xx && mouseY < this.height - 36;
        boolean bl = hoverNext = mouseX > this.width - this.xx + 74 && mouseY > this.height - 52 && mouseX < this.width + 144 - this.xx && mouseY < this.height - 36;
        if (Twelve.getInstance().player != null) {
	        if (hoverPlay) {
	            if (Twelve.getInstance().player.isPlaying()) {
	                Twelve.getInstance().player.pause();
	            } else {
	                Twelve.getInstance().player.play();
	            }
	        }
	        if (hoverShuffle) {
	            Twelve.getInstance().player.setShuffle(!Twelve.getInstance().player.shuffle);
	        }
	        if (hoverPrev) {
	            Twelve.getInstance().player.skipBackward();
	        }
	        if (hoverNext) {
	            Twelve.getInstance().player.skipForward();
	        }
        }
        if (Twelve.getInstance().player != null) {
	        boolean bl2 = listHover = mouseX > this.width - this.xx && mouseY > 19 && mouseX < this.width + 144 && mouseY < 200;
	        if (listHover && mouseButton == 0) {
	            for (ListItem item : this.items) {
	                if (!item.highlighted) continue;
	                this.scroll = 0.0f;
	                Twelve.getInstance().player.playingIndex = item.index;
	                Twelve.getInstance().player.stop();
	                Twelve.getInstance().player.play();
	            }
	        }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void onGuiClosed() {
        for (final PanelBase panel : this.getPanels()) {
            panel.onGuiClosed();
        }
        this.saveGuiSettings();
        super.onGuiClosed();
    }
    
    public boolean getAlreadyLoaded() {
        return GuiClick.THEME_FIRSTLOADED;
    }
    
    public void setAlreadyLoaded(final boolean LOADED) {
        GuiClick.THEME_FIRSTLOADED = LOADED;
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void saveGuiSettings() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "ONE2", "gui.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final PanelBase window : this.getPanels()) {
                out.write(String.valueOf(window.getName().replace(" ", "")) + ":" + window.PANEL_POSX + ":" + window.PANEL_POSY + ":" + window.getOpen());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public void loadGuiSettings() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\gui.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            @SuppressWarnings("resource")
			final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.toLowerCase().trim();
                final String[] args = curLine.split(":");
                final String title = args[0];
                final int dragX = Integer.parseInt(args[1]);
                final int dragY = Integer.parseInt(args[2]);
                final boolean open = Boolean.parseBoolean(args[3]);
                for (final PanelBase window : this.getPanels()) {
                    if (window.getName().replace(" ", "").equalsIgnoreCase(title)) {
                        window.PANEL_POSX = dragX;
                        window.PANEL_POSY = dragY;
                        window.setOpen(open);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.saveGuiSettings();
        }
    }
    
    private void drawMusic(final int mouseX, final int mouseY) {
        boolean hover;
        boolean listHover;
        boolean bl = hover = mouseX > this.width - 5 - this.xx && mouseY > 0 && mouseX < this.width && mouseY < this.height;
        if (hover && this.k < 16) {
            ++this.k;
        } else if (!hover && this.k > 0) {
            --this.k;
        }
        this.xx = (int)(MathHelper.cos(0.1f * (float)(110 + this.k)) * 150.0f);
        GuiUtils.drawBorderedRect(this.width - 5 - this.xx, -1, this.width, this.height, -11184811, -13421773);
        Twelve.getInstance().guiFont.drawString("MUSIC", this.width - this.xx + 60, 7.0f, NahrFont.FontType.PLAIN, -36752, 0);
        boolean bl2 = listHover = mouseX > this.width - this.xx && mouseY > 19 && mouseX < this.width + 144 && mouseY < this.height - 160;
        if (listHover) {
            this.drag += - Mouse.getDWheel() / 120;
        }
        if (this.drag < 0) {
            this.drag = 0;
        }
        if (this.drag > this.height - 160 - 29) {
            this.drag = this.height - 160 - 29;
        }
        if (MP3Player.playlist.size() > 0) {
	        GL11.glPushMatrix();
	        GL11.glEnable((int)3089);
	        GL11.glScissor((int)((this.width - this.xx) * 2), (int)((this.height - 200) * 2), (int)273, (int)((this.height - 160 - 19) * 2));
	        float scrollPercent = (float)this.drag / ((float)this.height - 160.0f - 28.5f);
	        Twelve.getInstance().getMP3Player();
	        float scrollSpeed = 12 * MP3Player.playlist.size() - 181;
	        scrollSpeed = scrollSpeed < 0.0f ? 0.0f : scrollSpeed;
	        float scrollPosition = - scrollPercent * scrollSpeed;
	        int count = 0;
	        Twelve.getInstance().getMP3Player();
	        for (Object file : MP3Player.playlist) {
	        	if (file instanceof File) {
		            boolean itemHover = mouseX > this.width + 2 - this.xx && (float)mouseY > (float)(18 + count * 12) + scrollPosition && mouseX < this.width + 136 - this.xx && (float)mouseY < (float)(30 + count * 12) + scrollPosition;
	        		ListItem thing = new ListItem(count, itemHover);
	        		if (!this.items.contains(thing)) {
	        			this.items.add(new ListItem(count, itemHover));
	        		}
		            Twelve.getInstance().guiFont.drawString(MP3Utils.getTitle((File) file), this.width + 2 - this.xx, (float)(21 + count * 12) + scrollPosition + 0.2f, NahrFont.FontType.PLAIN, itemHover ? -43691 : -1, 0);
		            GuiUtils.drawFineBorderedRect(this.width - this.xx, 30 + count * 12 + (int)scrollPosition, this.width + 144 - this.xx, 31 + count * 12 + (int)scrollPosition, -11184811, -13421773);
		            ++count;
	        	}
	        }
	        GL11.glDisable((int)3089);
	        GL11.glPopMatrix();
        }
        GuiUtils.drawFineBorderedRect(this.width - this.xx, 19, this.width + 144 - this.xx, this.height - 160, -11184811, -13882324);
        GuiUtils.drawFineBorderedRect(this.width + 136 - this.xx, 19, this.width + 144 - this.xx, this.height - 160, -11184811, -13421773);
        GuiUtils.drawFineBorderedRect(this.width + 136 - this.xx, 19 + this.drag, this.width + 144 - this.xx, 29 + this.drag, -11184811, -43691);
        GuiUtils.drawFineBorderedRect(this.width + 33 - this.xx, this.height - 155, this.width + 111 - this.xx, this.height - 77, -11184811, -13882324);
        boolean hoverNext = mouseX > this.width - this.xx + 74 && mouseY > this.height - 52 && mouseX < this.width + 144 - this.xx && mouseY < this.height - 36;
        boolean hoverPlay = mouseX > this.width - this.xx && mouseY > this.height - 72 && mouseX < this.width + 70 - this.xx && mouseY < this.height - 56;
        boolean hoverShuffle = mouseX > this.width - this.xx + 74 && mouseY > this.height - 72 && mouseX < this.width + 144 - this.xx && mouseY < this.height - 56;
        boolean hoverPrev = mouseX > this.width - this.xx && mouseY > this.height - 52 && mouseX < this.width + 70 - this.xx && mouseY < this.height - 36;
        	this.drawSmallButton(Twelve.getInstance().player.isPlaying() ? "Pause" : "Play", this.width - this.xx, this.height - 72, this.width + 70 - this.xx, this.height - 56, Twelve.getInstance().player.isPlaying(), hoverPlay);
        this.drawSmallButton("Shuffle", this.width - this.xx + 74, this.height - 72, this.width + 144 - this.xx, this.height - 56, Twelve.getInstance().player.isShuffle(), hoverShuffle);
        this.drawSmallButton("Previous", this.width - this.xx, this.height - 52, this.width + 70 - this.xx, this.height - 36, false, hoverPrev);
        this.drawSmallButton("Next", this.width - this.xx + 74, this.height - 52, this.width + 144 - this.xx, this.height - 36, false, hoverNext);
	        GL11.glPushMatrix();
	        GL11.glEnable((int)3089);
	        GL11.glScissor((int)((this.width - this.xx) * 2), (int)0, (int)288, (int)1920);
	        String titleAndArtist = MP3Utils.getTitleAndArtist((File)MP3Player.playlist.get(Twelve.getInstance().player.playingIndex));
	        if (Twelve.getInstance().player.isPlaying()) {
	            this.scroll = (float)((double)this.scroll + 0.4);
	        }
	        if (this.scroll > 149.0f) {
	            this.scroll = - (int)Twelve.getInstance().guiFont.getStringWidth(titleAndArtist) - 5;
	        }
	        Twelve.getInstance().guiFont.drawString(titleAndArtist, (float)(this.width - this.xx) + this.scroll, this.height - 32, NahrFont.FontType.PLAIN, -1, 0);
	        GL11.glDisable((int)3089);
	        GL11.glPopMatrix();
	        int minutes = Twelve.getInstance().player.getPosition() / 60000;
	        int seconds = Twelve.getInstance().player.getPosition() / 1000 % 60;
	        int progress = (int)((float)Twelve.getInstance().player.getPosition() / (float)MP3Utils.getLength((File)MP3Player.playlist.get(Twelve.getInstance().player.playingIndex)) * 144.0f);
	        GuiUtils.drawFineBorderedRect(this.width - this.xx, this.height - 21, this.width + 144 - this.xx, this.height - 5, -12303292, -13882324);
	        GuiUtils.drawFineBorderedRect(this.width - this.xx + 1, this.height - 21, this.width + progress - this.xx, this.height - 5, -36752, -44976);
	        String time = String.format("%d:%02d", minutes, seconds);
	        Twelve.getInstance().guiFont.drawCenteredString(time, this.width - this.xx + 72, this.height - 17, NahrFont.FontType.PLAIN, -1, 0);
	        drawRect(0, 0, 0, 0, 0);
    }
    
    public void drawSmallButton(final String text, final int startX, final int startY, final int endX, final int endY, final boolean active, final boolean hover) {
        final int centreX = (endX - startX) / 2;
        final int centreY = (endY - startY) / 2;
        GuiUtils.drawFineBorderedRect(startX, startY, endX, endY, hover ? -36752 : -12303292, active ? -44976 : -13882324);
        Twelve.getInstance().guiFont.drawCenteredString(text, startX + centreX, startY + centreY - Twelve.getInstance().guiFont.getStringHeight(text) / 2.0f + 1.0f, NahrFont.FontType.PLAIN, -1, 0);
    }
    
    class ListItem
    {
        public int index;
        public boolean highlighted;
        
        public ListItem(final int index, final boolean highlighted) {
            this.index = index;
            this.highlighted = highlighted;
        }
    }
}
