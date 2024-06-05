package net.minecraft.src;

public class DerivedWorldInfo extends WorldInfo
{
    private final WorldInfo theWorldInfo;
    
    public DerivedWorldInfo(final WorldInfo par1WorldInfo) {
        this.theWorldInfo = par1WorldInfo;
    }
    
    @Override
    public NBTTagCompound getNBTTagCompound() {
        return this.theWorldInfo.getNBTTagCompound();
    }
    
    @Override
    public NBTTagCompound cloneNBTCompound(final NBTTagCompound par1NBTTagCompound) {
        return this.theWorldInfo.cloneNBTCompound(par1NBTTagCompound);
    }
    
    @Override
    public long getSeed() {
        return this.theWorldInfo.getSeed();
    }
    
    @Override
    public int getSpawnX() {
        return this.theWorldInfo.getSpawnX();
    }
    
    @Override
    public int getSpawnY() {
        return this.theWorldInfo.getSpawnY();
    }
    
    @Override
    public int getSpawnZ() {
        return this.theWorldInfo.getSpawnZ();
    }
    
    @Override
    public long getWorldTotalTime() {
        return this.theWorldInfo.getWorldTotalTime();
    }
    
    @Override
    public long getWorldTime() {
        return this.theWorldInfo.getWorldTime();
    }
    
    @Override
    public long getSizeOnDisk() {
        return this.theWorldInfo.getSizeOnDisk();
    }
    
    @Override
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.theWorldInfo.getPlayerNBTTagCompound();
    }
    
    @Override
    public int getDimension() {
        return this.theWorldInfo.getDimension();
    }
    
    @Override
    public String getWorldName() {
        return this.theWorldInfo.getWorldName();
    }
    
    @Override
    public int getSaveVersion() {
        return this.theWorldInfo.getSaveVersion();
    }
    
    @Override
    public long getLastTimePlayed() {
        return this.theWorldInfo.getLastTimePlayed();
    }
    
    @Override
    public boolean isThundering() {
        return this.theWorldInfo.isThundering();
    }
    
    @Override
    public int getThunderTime() {
        return this.theWorldInfo.getThunderTime();
    }
    
    @Override
    public boolean isRaining() {
        return this.theWorldInfo.isRaining();
    }
    
    @Override
    public int getRainTime() {
        return this.theWorldInfo.getRainTime();
    }
    
    @Override
    public EnumGameType getGameType() {
        return this.theWorldInfo.getGameType();
    }
    
    @Override
    public void setSpawnX(final int par1) {
    }
    
    @Override
    public void setSpawnY(final int par1) {
    }
    
    @Override
    public void setSpawnZ(final int par1) {
    }
    
    @Override
    public void incrementTotalWorldTime(final long par1) {
    }
    
    @Override
    public void setWorldTime(final long par1) {
    }
    
    @Override
    public void setSpawnPosition(final int par1, final int par2, final int par3) {
    }
    
    @Override
    public void setWorldName(final String par1Str) {
    }
    
    @Override
    public void setSaveVersion(final int par1) {
    }
    
    @Override
    public void setThundering(final boolean par1) {
    }
    
    @Override
    public void setThunderTime(final int par1) {
    }
    
    @Override
    public void setRaining(final boolean par1) {
    }
    
    @Override
    public void setRainTime(final int par1) {
    }
    
    @Override
    public boolean isMapFeaturesEnabled() {
        return this.theWorldInfo.isMapFeaturesEnabled();
    }
    
    @Override
    public boolean isHardcoreModeEnabled() {
        return this.theWorldInfo.isHardcoreModeEnabled();
    }
    
    @Override
    public WorldType getTerrainType() {
        return this.theWorldInfo.getTerrainType();
    }
    
    @Override
    public void setTerrainType(final WorldType par1WorldType) {
    }
    
    @Override
    public boolean areCommandsAllowed() {
        return this.theWorldInfo.areCommandsAllowed();
    }
    
    @Override
    public boolean isInitialized() {
        return this.theWorldInfo.isInitialized();
    }
    
    @Override
    public void setServerInitialized(final boolean par1) {
    }
    
    @Override
    public GameRules getGameRulesInstance() {
        return this.theWorldInfo.getGameRulesInstance();
    }
}
