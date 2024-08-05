package fr.dog.anticheat;

import fr.dog.util.player.PlayerUtil;
import fr.dog.util.player.ChatUtil;
import fr.dog.module.impl.player.Anticheat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

public class Check {
    protected Anticheat anticheat;
   public String checkName;
    public HashMap<UUID, Integer> flags = new HashMap<>();
    public Minecraft mc = Minecraft.getMinecraft();
   ;

    public Check(String checkName, Anticheat anticheat) {
        this.checkName = checkName;
        this.anticheat = anticheat;
    }

    public void flagPlayer(EntityPlayer player, int vl) {
        if (!anticheat.isEnabled()) {
            return;
        }

        boolean checkEnabled = false;
        switch (checkName) {
            case "Autoblock":
                checkEnabled = anticheat.autoblock.getValue();
                break;
            case "NoslowA", "NoslowB":
                checkEnabled = anticheat.noslow.getValue();
                break;
            case "Scaffold":
                checkEnabled = anticheat.scaffold.getValue();
                break;

        }

        if (!checkEnabled) {
            return;
        }

        if (player == mc.thePlayer) {
            return;
        }

        boolean whitelistTeam = anticheat.team.getValue();
        if (whitelistTeam && PlayerUtil.isEntityTeamSameAsPlayer(player)) {
            return;
        }

        UUID playerId = player.getGameProfile().getId();
        int newFlags = flags.getOrDefault(playerId, 0) + vl;
        flags.put(playerId, newFlags);

        int flagThreshold = checkName.equals("Autoblock") ? 3 : 15;

        if (newFlags % flagThreshold == 0) {
            String formattedFlags = new DecimalFormat("#.##").format(newFlags);
            ChatUtil.display(player.getGameProfile().getName() + " §cFlagged §ffor§c " + checkName + " §f[x" + formattedFlags + "]");
        }
    }

    public void onUpdate() {

    }

    public void onPacket(Packet packet) {

    }

    public void onBlockMod(BlockPos pos, IBlockState state) {

    }
}
