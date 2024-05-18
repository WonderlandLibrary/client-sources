package net.minecraft.src;

class GuiFlatPresetsItem
{
    public int iconId;
    public String presetName;
    public String presetData;
    
    public GuiFlatPresetsItem(final int par1, final String par2Str, final String par3Str) {
        this.iconId = par1;
        this.presetName = par2Str;
        this.presetData = par3Str;
    }
}
