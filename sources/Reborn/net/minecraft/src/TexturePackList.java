package net.minecraft.src;

import net.minecraft.client.*;
import java.io.*;
import java.util.*;

public class TexturePackList
{
    private static final ITexturePack defaultTexturePack;
    private final Minecraft mc;
    private final File texturePackDir;
    private final File mpTexturePackFolder;
    private List availableTexturePacks;
    private Map texturePackCache;
    private ITexturePack selectedTexturePack;
    private boolean isDownloading;
    
    static {
        defaultTexturePack = new TexturePackDefault();
    }
    
    public TexturePackList(final File par1File, final Minecraft par2Minecraft) {
        this.availableTexturePacks = new ArrayList();
        this.texturePackCache = new HashMap();
        this.mc = par2Minecraft;
        this.texturePackDir = new File(par1File, "texturepacks");
        this.mpTexturePackFolder = new File(par1File, "texturepacks-mp-cache");
        this.createTexturePackDirs();
        this.updateAvaliableTexturePacks();
    }
    
    private void createTexturePackDirs() {
        if (!this.texturePackDir.isDirectory()) {
            this.texturePackDir.delete();
            this.texturePackDir.mkdirs();
        }
        if (!this.mpTexturePackFolder.isDirectory()) {
            this.mpTexturePackFolder.delete();
            this.mpTexturePackFolder.mkdirs();
        }
    }
    
    public boolean setTexturePack(final ITexturePack par1ITexturePack) {
        if (par1ITexturePack == this.selectedTexturePack) {
            return false;
        }
        this.isDownloading = false;
        this.selectedTexturePack = par1ITexturePack;
        this.mc.gameSettings.skin = par1ITexturePack.getTexturePackFileName();
        this.mc.gameSettings.saveOptions();
        return true;
    }
    
    public void requestDownloadOfTexture(final String par1Str) {
        String var2 = par1Str.substring(par1Str.lastIndexOf("/") + 1);
        if (var2.contains("?")) {
            var2 = var2.substring(0, var2.indexOf("?"));
        }
        if (var2.endsWith(".zip")) {
            final File var3 = new File(this.mpTexturePackFolder, var2);
            this.downloadTexture(par1Str, var3);
        }
    }
    
    private void downloadTexture(final String par1Str, final File par2File) {
        final HashMap var3 = new HashMap();
        final GuiProgress var4 = new GuiProgress();
        var3.put("X-Minecraft-Username", this.mc.session.username);
        var3.put("X-Minecraft-Version", "1.5.2");
        var3.put("X-Minecraft-Supported-Resolutions", "16");
        this.isDownloading = true;
        this.mc.displayGuiScreen(var4);
        HttpUtil.downloadTexturePack(par2File, par1Str, new TexturePackDownloadSuccess(this), var3, 10000000, var4);
    }
    
    public boolean getIsDownloading() {
        return this.isDownloading;
    }
    
    public void onDownloadFinished() {
        this.isDownloading = false;
        this.updateAvaliableTexturePacks();
        this.mc.scheduleTexturePackRefresh();
    }
    
    public void updateAvaliableTexturePacks() {
        final ArrayList var1 = new ArrayList();
        this.selectedTexturePack = TexturePackList.defaultTexturePack;
        var1.add(TexturePackList.defaultTexturePack);
        for (final File var3 : this.getTexturePackDirContents()) {
            final String var4 = this.generateTexturePackID(var3);
            if (var4 != null) {
                Object var5 = this.texturePackCache.get(var4);
                if (var5 == null) {
                    var5 = (var3.isDirectory() ? new TexturePackFolder(var4, var3, TexturePackList.defaultTexturePack) : new TexturePackCustom(var4, var3, TexturePackList.defaultTexturePack));
                    this.texturePackCache.put(var4, var5);
                }
                if (((ITexturePack)var5).getTexturePackFileName().equals(this.mc.gameSettings.skin)) {
                    this.selectedTexturePack = (ITexturePack)var5;
                }
                var1.add(var5);
            }
        }
        this.availableTexturePacks.removeAll(var1);
        for (final ITexturePack var6 : this.availableTexturePacks) {
            var6.deleteTexturePack(this.mc.renderEngine);
            this.texturePackCache.remove(var6.getTexturePackID());
        }
        this.availableTexturePacks = var1;
    }
    
    private String generateTexturePackID(final File par1File) {
        return (par1File.isFile() && par1File.getName().toLowerCase().endsWith(".zip")) ? (String.valueOf(par1File.getName()) + ":" + par1File.length() + ":" + par1File.lastModified()) : ((par1File.isDirectory() && new File(par1File, "pack.txt").exists()) ? (String.valueOf(par1File.getName()) + ":folder:" + par1File.lastModified()) : null);
    }
    
    private List getTexturePackDirContents() {
        return (this.texturePackDir.exists() && this.texturePackDir.isDirectory()) ? Arrays.asList(this.texturePackDir.listFiles()) : Collections.emptyList();
    }
    
    public List availableTexturePacks() {
        return Collections.unmodifiableList((List<?>)this.availableTexturePacks);
    }
    
    public ITexturePack getSelectedTexturePack() {
        return this.selectedTexturePack;
    }
    
    public boolean func_77300_f() {
        if (!this.mc.gameSettings.serverTextures) {
            return false;
        }
        final ServerData var1 = this.mc.getServerData();
        return var1 == null || var1.func_78840_c();
    }
    
    public boolean getAcceptsTextures() {
        if (!this.mc.gameSettings.serverTextures) {
            return false;
        }
        final ServerData var1 = this.mc.getServerData();
        return var1 != null && var1.getAcceptsTextures();
    }
    
    static boolean isDownloading(final TexturePackList par0TexturePackList) {
        return par0TexturePackList.isDownloading;
    }
    
    static ITexturePack setSelectedTexturePack(final TexturePackList par0TexturePackList, final ITexturePack par1ITexturePack) {
        return par0TexturePackList.selectedTexturePack = par1ITexturePack;
    }
    
    static String generateTexturePackID(final TexturePackList par0TexturePackList, final File par1File) {
        return par0TexturePackList.generateTexturePackID(par1File);
    }
    
    static ITexturePack func_98143_h() {
        return TexturePackList.defaultTexturePack;
    }
    
    static Minecraft getMinecraft(final TexturePackList par0TexturePackList) {
        return par0TexturePackList.mc;
    }
}
