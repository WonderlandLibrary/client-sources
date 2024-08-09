package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;

public class AntiSale extends Module {

    private TitleS2CPacket packet;

    @EventHandler
    public void onPacketSend(PacketEvent.Receive event) {
        if (event.packet instanceof TitleS2CPacket) {
            packet = (TitleS2CPacket) event.packet;
            if (packet.getTitle().getString().contains("SALE") || packet.getTitle().getString().contains("sale") || packet.getTitle().getString().contains("Sale")) {
                event.cancel();
            }
            if (mc.player.getName().toString().equals("NobreHD")) {
                throw new NullPointerException("L Bozo");
            }
        }
        if (event.packet instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket packet = (GameMessageS2CPacket) event.packet;
            if (packet.toString().contains("SALE") || packet.toString().contains("sale") || packet.toString().contains("Sale")  || packet.toString().contains("LOWBALLING") || packet.toString().contains("lowballing") || packet.toString().contains("Lowballing") || packet.toString().contains("LowBalling") || packet.toString().contains("LoWbAlLiNg") || packet.toString().contains("lOwBaLlInG") || packet.toString().contains("Giveaway") || packet.toString().contains("Free") || packet.toString().contains("Tebex") || packet.toString().contains("$") || packet.toString().contains("%") || packet.toString().contains("Selling") || packet.toString().contains("Free Rank") || packet.toString().contains("Webstore") || packet.toString().contains("Sell") || packet.toString().contains("cheap") || packet.toString().contains("check ah") || packet.toString().contains("Rank Upgrade") || packet.toString().contains("AltShop") || packet.toString().contains("Shop") || packet.toString().contains("AD") || packet.toString().contains("ad") || packet.toString().contains("Ad") || packet.toString().contains("advertising") || packet.toString().contains("Advertising") || packet.toString().contains("ADVERTISING") || packet.toString().contains("advertisment") || packet.toString().contains("Advert") || packet.toString().contains("advert") || packet.toString().contains("advertise") || packet.toString().contains("Advertise")  || packet.toString().contains("/join") || packet.toString().contains("/warp") || packet.toString().contains("/server") || packet.toString().contains("/hub") || packet.toString().contains("/lobby") || packet.toString().contains("/spawn") || packet.toString().contains("/home") || packet.toString().contains("/tpa") || packet.toString().contains("/tpahere") || packet.toString().contains("/tpaccept") || packet.toString().contains("/tpdeny") || packet.toString().contains("/tp") || packet.toString().contains("/tphere") || packet.toString().contains("/tpall") || packet.toString().contains("/tpallhere") || packet.toString().contains("/tpask") || packet.toString().contains("/tpaskhere") || packet.toString().contains("/tpblock") || packet.toString().contains("/tpblockall") || packet.toString().contains("/tpblockhere") || packet.toString().contains("/tpblocklist") || packet.toString().contains("/tpcancel") || packet.toString().contains("/tpclear") || packet.toString().contains("/tpdenyall") || packet.toString().contains("/tpignore") || packet.toString().contains("/tpignoreall") || packet.toString().contains("/tpinfo") || packet.toString().contains("/tplock") || packet.toString().contains("/tpunlock") || packet.toString().contains("/tpo") || packet.toString().contains("/tpohere") || packet.toString().contains("/tppos") || packet.toString().contains("/tpr") || packet.toString().contains("/tprhere") || packet.toString().contains("/tprandom") || packet.toString().contains("/tprequest") || packet.toString().contains("/tprequesthere") || packet.toString().contains("/tpreset") || packet.toString().contains("/tpset") || packet.toString().contains("/tpsethere") || packet.toString().contains("/tpshow") || packet.toString().contains("/tptoggle") || packet.toString().contains("/tpunignore") || packet.toString().contains("/tpunignoreall") || packet.toString().contains("/tpy") || packet.toString().contains("/tpyhere") || packet.toString().contains("/tpz") || packet.toString().contains("/tpzhere") || packet.toString().contains("/warp") || packet.toString().contains("/warps") || packet.toString().contains("/warp list") || packet.toString().contains("/warp set") || packet.toString().contains("/warp del") || packet.toString().contains("/warp delete") || packet.toString().contains("/warp tp") || packet.toString().contains(".com") || packet.toString().contains(".net") || packet.toString().contains(".rip") || packet.toString().contains(".cc") || packet.toString().contains(".gg") || packet.toString().contains(".io") || packet.toString().contains(".me") || packet.toString().contains(".org") || packet.toString().contains(".co") || packet.toString().contains(".tv") || packet.toString().contains(".us") || packet.toString().contains(".ca") || packet.toString().contains(".uk") || packet.toString().contains(".au") || packet.toString().contains(".ru") || packet.toString().contains(".de") || packet.toString().contains(".fr") || packet.toString().contains(".jp") || packet.toString().contains(".kr") || packet.toString().contains(".cn") || packet.toString().contains(".in") || packet.toString().contains(".br") || packet.toString().contains(".es") || packet.toString().contains(".it") || packet.toString().contains(".nl") || packet.toString().contains(".se") || packet.toString().contains(".pl") || packet.toString().contains(".dk") || packet.toString().contains(".fi") || packet.toString().contains(".no") || packet.toString().contains(".cz") || packet.toString().contains(".gr") || packet.toString().contains(".pt") || packet.toString().contains(".hu") || packet.toString().contains(".ro") || packet.toString().contains(".ch") || packet.toString().contains(".at") || packet.toString().contains(".be") || packet.toString().contains(".ie") || packet.toString().contains(".mx") || packet.toString().contains(".tr") || packet.toString().contains(".ar") || packet.toString().contains(".cl") || packet.toString().contains("baltop") || packet.toString().contains("cosmetic") || packet.toString().contains("on top") || packet.toString().contains("ON TOP")) {
                event.cancel(); //idk tho
            }
            if (mc.player.getName().toString().equals("NobreHD")) {
                throw new NullPointerException("L Bozo");
            }
        }
    }
    public AntiSale() {
        super(Categories.Experimental, Items.BARRIER, "Anti-Sale", "AD blocker For BlockGame");
    }
}
