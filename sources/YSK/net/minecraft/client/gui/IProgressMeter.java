package net.minecraft.client.gui;

public interface IProgressMeter
{
    public static final String[] lanSearchStates = lanSearchStates2;
    
    default static {
        final String[] lanSearchStates2 = new String[0x8F ^ 0x88];
        lanSearchStates2["".length()] = "oooooo";
        lanSearchStates2[" ".length()] = "Oooooo";
        lanSearchStates2["  ".length()] = "oOoooo";
        lanSearchStates2["   ".length()] = "ooOooo";
        lanSearchStates2[0x35 ^ 0x31] = "oooOoo";
        lanSearchStates2[0x35 ^ 0x30] = "ooooOo";
        lanSearchStates2[0x1E ^ 0x18] = "oooooO";
    }
    
    void doneLoading();
}
