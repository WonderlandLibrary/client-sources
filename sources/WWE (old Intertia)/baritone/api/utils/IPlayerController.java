package baritone.api.utils;

public interface IPlayerController
{
    boolean onPlayerDamageBlock(final et p0, final fa p1);
    
    void resetBlockRemoving();
    
    aip windowClick(final int p0, final int p1, final int p2, final afw p3, final aed p4);
    
    void setGameType(final ams p0);
    
    ams getGameType();
    
    default double getBlockReachDistance() {
        if (this.getGameType().d()) {
            return 5.0;
        }
        return 4.5;
    }
    
    ud processRightClickBlock(final bud p0, final amu p1, final et p2, final fa p3, final bhe p4, final ub p5);
    
    ud processRightClick(final bud p0, final amu p1, final ub p2);
}
