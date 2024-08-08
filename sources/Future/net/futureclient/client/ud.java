package net.futureclient.client;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemAir;

public class ud extends XB
{
    public ud() {
        super(new String[] { "Dupe", "DupeExploit", "DupeKick", "DropKick", "DupeAll", "InventoryDupe" });
    }
    
    @Override
    public String M() {
        return "&e[hand|all]";
    }
    
    @Override
    public String M(final String[] array) {
        if (this.k.isSingleplayer()) {
            return "You are in singleplayer.";
        }
        if (array.length != 1) {
            return null;
        }
        final String lowerCase = array[0].toLowerCase();
        int n = -1;
        int n2 = 0;
        Label_0142: {
            switch (lowerCase.hashCode()) {
                case 3194991:
                    if (lowerCase.equals("hand")) {
                        n2 = 0;
                        break Label_0142;
                    }
                    break;
                case 3198835:
                    if (lowerCase.equals("hend")) {
                        n2 = 1;
                        break Label_0142;
                    }
                    break;
                case 96673:
                    if (lowerCase.equals("all")) {
                        n2 = 2;
                        break Label_0142;
                    }
                    break;
                case -2020599460:
                    if (lowerCase.equals("inventory")) {
                        n = 3;
                        break;
                    }
                    break;
            }
            n2 = n;
        }
        switch (n2) {
            case 0:
            case 1:
                if (!(this.k.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemAir)) {
                    final String s = "W\u001f|\u001dc\u0004}\n3\f}\t3\u0006z\u000ex\u0004}\n=";
                    this.k.player.dropItem(true);
                    this.k.world.sendQuittingDisconnectingPacket();
                    return RG.M(s);
                }
                return "Put an item in your hand.";
            case 2:
            case 3: {
                int i = 9;
                int n3 = 9;
                while (i < 45) {
                    final PlayerControllerMP playerController = this.k.playerController;
                    final int n4 = 0;
                    final int n5 = n3;
                    final int n6 = 1;
                    final ClickType throw1 = ClickType.THROW;
                    ++n3;
                    playerController.windowClick(n4, n5, n6, throw1, (EntityPlayer)this.k.player);
                    i = n3;
                }
                this.k.world.sendQuittingDisconnectingPacket();
                return "Dropping and kicking.";
            }
            default:
                return null;
        }
    }
}
