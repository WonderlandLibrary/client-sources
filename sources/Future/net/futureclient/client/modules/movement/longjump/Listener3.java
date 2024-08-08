package net.futureclient.client.modules.movement.longjump;

import net.futureclient.client.events.Event;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.ZG;
import org.lwjgl.input.Keyboard;
import net.futureclient.client.hb;
import net.futureclient.client.EB;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.movement.LongJump;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener3 extends n<lF>
{
    public final LongJump k;
    
    public Listener3(final LongJump k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        switch (EB.D[((hb.wc)LongJump.M(this.k).M()).ordinal()]) {
            case 3: {
                if (LongJump.getMinecraft116().world == null) {
                    break;
                }
                if (Keyboard.isKeyDown(50)) {
                    final LongJump k = this.k;
                    final double n = 2.1199235295E-314;
                    final double n2 = 0.0;
                    LongJump.M(k, n2, n, n2);
                }
                if (!ZG.M()) {
                    return;
                }
                final float n4;
                final float n3 = (float)Math.cos(((n4 = LongJump.getMinecraft9().player.rotationYaw + ((LongJump.getMinecraft144().player.moveForward < 0.0f) ? 180 : 0) + ((LongJump.getMinecraft107().player.moveStrafing > 0.0f) ? (-90.0f * ((LongJump.getMinecraft119().player.moveForward < 0.0f) ? -0.5f : ((LongJump.getMinecraft100().player.moveForward > 0.0f) ? 0.5f : 1.0f))) : 0.0f) - ((LongJump.getMinecraft127().player.moveStrafing < 0.0f) ? (-90.0f * ((LongJump.getMinecraft47().player.moveForward < 0.0f) ? -0.5f : ((LongJump.getMinecraft17().player.moveForward > 0.0f) ? 0.5f : 1.0f))) : 0.0f)) + 90.0f) * 6.984873503E-315 / 0.0);
                final float n5 = (float)Math.sin((n4 + 90.0f) * 6.984873503E-315 / 0.0);
                if (!LongJump.getMinecraft19().player.collidedVertically) {
                    LongJump.M(this.k, LongJump.e(this.k) + 1);
                    if (LongJump.getMinecraft32().gameSettings.keyBindSneak.isKeyDown()) {
                        final NetHandlerPlayClient connection = LongJump.getMinecraft99().player.connection;
                        final double n6 = 2.1199235295E-314;
                        final double n7 = 0.0;
                        connection.sendPacket((Packet)new CPacketPlayer.Position(n7, n6, n7, false));
                    }
                    LongJump.b(this.k, 0);
                    if (!LongJump.getMinecraft96().player.collidedVertically) {
                        if (LongJump.getMinecraft150().player.motionY == 7.943109154E-315) {
                            final EntityPlayerSP player = LongJump.getMinecraft40().player;
                            player.motionY *= 7.957484216E-315;
                        }
                        if (LongJump.getMinecraft118().player.motionY == 8.24181575E-315) {
                            final EntityPlayerSP player2 = LongJump.getMinecraft15().player;
                            player2.motionY *= 1.3262473694E-314;
                        }
                        if (LongJump.getMinecraft117().player.motionY == 6.32021844E-315) {
                            final EntityPlayerSP player3 = LongJump.getMinecraft51().player;
                            player3.motionY *= 1.856746317E-314;
                        }
                        if (LongJump.getMinecraft80().player.motionY == 6.24930449E-315) {
                            final EntityPlayerSP player4 = LongJump.getMinecraft60().player;
                            player4.motionY *= 1.856746317E-314;
                        }
                        if (LongJump.getMinecraft29().player.motionY == 3.10334463E-315) {
                            final EntityPlayerSP player5 = LongJump.getMinecraft104().player;
                            player5.motionY *= 1.856746317E-314;
                        }
                        if (LongJump.getMinecraft92().player.motionY == 1.0745162934E-314) {
                            final EntityPlayerSP player6 = LongJump.getMinecraft34().player;
                            player6.motionY *= 1.3262473694E-314;
                        }
                        if (LongJump.getMinecraft132().player.motionY == 6.4161737E-315) {
                            final EntityPlayerSP player7 = LongJump.getMinecraft24().player;
                            player7.motionY *= 7.957484216E-315;
                        }
                        if (LongJump.getMinecraft53().player.motionY == 6.00011333E-315) {
                            final EntityPlayerSP player8 = LongJump.getMinecraft7().player;
                            player8.motionY *= 1.0609978955E-314;
                        }
                        if (LongJump.getMinecraft57().player.motionY == 1.411644485E-314) {
                            final EntityPlayerSP player9 = LongJump.getMinecraft14().player;
                            player9.motionY *= 1.0609978955E-314;
                        }
                        if (LongJump.M(this.k, (EntityPlayer)LongJump.getMinecraft49().player, 0.0) < 0.0) {
                            if (LongJump.getMinecraft146().player.motionY == 1.34402627E-314) {
                                final EntityPlayerSP player10 = LongJump.getMinecraft101().player;
                                player10.motionY *= 1.856746317E-314;
                            }
                            if (LongJump.getMinecraft111().player.motionY == 2.0925154156E-314) {
                                final EntityPlayerSP player11 = LongJump.getMinecraft74().player;
                                player11.motionY *= 0.0;
                            }
                            if (LongJump.getMinecraft102().player.motionY == 3.1047452E-315) {
                                final EntityPlayerSP player12 = LongJump.getMinecraft93().player;
                                player12.motionY *= 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft160().player.motionY == 8.801257036E-315) {
                                final EntityPlayerSP player13 = LongJump.getMinecraft145().player;
                                player13.motionY *= 2.652494739E-315;
                            }
                            if (LongJump.getMinecraft115().player.motionY == 4.343827836E-315) {
                                final EntityPlayerSP player14 = LongJump.getMinecraft97().player;
                                player14.motionY *= 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft139().player.motionY == 1.118078837E-314) {
                                final EntityPlayerSP player15 = LongJump.getMinecraft59().player;
                                player15.motionY *= 5.304989477E-315;
                            }
                            if (LongJump.getMinecraft89().player.motionY == 1.612716801E-314) {
                                final EntityPlayerSP player16 = LongJump.getMinecraft41().player;
                                player16.motionY *= 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft94().player.motionY == 1.0035121807E-314) {
                                final EntityPlayerSP player17 = LongJump.getMinecraft54().player;
                                player17.motionY *= 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft95().player.motionY == 1.9929972093E-314) {
                                final EntityPlayerSP player18 = LongJump.getMinecraft8().player;
                                player18.motionY *= 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft48().player.motionY == 7.22485148E-315) {
                                final EntityPlayerSP player19 = LongJump.getMinecraft65().player;
                                player19.motionY *= 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft108().player.motionY == 1.252364704E-315) {
                                final EntityPlayerSP player20 = LongJump.getMinecraft50().player;
                                player20.motionY *= 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft158().player.motionY == 1.341570533E-314) {
                                LongJump.getMinecraft2().player.motionY = 8.04889483E-315;
                            }
                            if (LongJump.getMinecraft83().player.motionY == 8.04889483E-315) {
                                LongJump.getMinecraft142().player.motionY = 1.3262473694E-314;
                            }
                            if (LongJump.getMinecraft147().player.motionY > 1.273197475E-314 && LongJump.getMinecraft16().player.motionY < 5.941588215E-315 && !LongJump.getMinecraft126().player.onGround && LongJump.getMinecraft135().gameSettings.keyBindForward.isKeyDown()) {
                                LongJump.getMinecraft72().player.motionY = 1.856746317E-314;
                            }
                        }
                        else {
                            if (LongJump.getMinecraft134().player.motionY < 1.273197475E-314 && LongJump.getMinecraft20().player.motionY > 1.9522361275E-314) {
                                final EntityPlayerSP player21 = LongJump.getMinecraft66().player;
                                player21.motionY *= 8.48798316E-315;
                            }
                            if (LongJump.getMinecraft159().player.motionY < 0.0 && LongJump.getMinecraft68().player.motionY > 5.941588215E-315) {
                                final EntityPlayerSP player22 = LongJump.getMinecraft81().player;
                                player22.motionY *= 1.273197475E-314;
                            }
                            if (LongJump.getMinecraft79().player.motionY < 8.48798316E-315 && LongJump.getMinecraft121().player.motionY > 1.273197475E-314) {
                                final EntityPlayerSP player23 = LongJump.getMinecraft137().player;
                                player23.motionY *= 2.0371159592E-314;
                            }
                            if (LongJump.getMinecraft112().player.motionY < 1.273197475E-314 && LongJump.getMinecraft61().player.motionY > 1.273197475E-314) {
                                final EntityPlayerSP player24 = LongJump.getMinecraft124().player;
                                player24.motionY *= 1.0185579796E-314;
                            }
                        }
                    }
                    ((ITimer)((IMinecraft)LongJump.getMinecraft71()).getTimer()).setTimerSpeed(0.85f);
                    final double[] array = { 9.086895254E-315, 6.46988029E-315, 6.162954817E-315, 1.2289241544E-314, 3.62878256E-315, 1.8498370986E-314, 1.4458091E-314, 1.6851023214E-314, 3.34087017E-316, 7.34719823E-315, 1.6670398935E-314, 3.56495293E-315, 2.0744630855E-314, 1.777723194E-314, 1.487773689E-314, 4.794012893E-315, 2.030325573E-315, 1.867356296E-314, 1.2888153637E-314, 4.685366707E-315, 3.73471259E-315, 1.7960572373E-314, 1.096647425E-314, 3.97237612E-315, 1.215479189E-314, 1.1204137775E-314, 1.307149407E-314, 1.4938850366E-314, 1.680620666E-314, 1.867356296E-314, 2.054091926E-314, 1.188317645E-315, 3.05567394E-315, 4.923030237E-315, 6.790386532E-315, 8.65774283E-315, 1.0525099124E-314, 1.239245542E-314, 1.4259811716E-314, 1.612716801E-314, 1.7994524307E-314, 1.9861880603E-314, 6.960146194E-315, 1.5278369694E-314, 2.376635285E-315, 4.24399158E-315, 6.111347877E-315, 1.4429571377E-314, 1.52783697E-315, 9.84606047E-315, 1.816428397E-314, 5.26254956E-315, 1.358077306E-314, 6.7903865E-316, 8.997262156E-315, 1.7315485657E-314, 4.413751247E-315, 1.273197475E-314, 2.105019825E-314, 8.14846384E-315, 1.646668734E-314, 3.56495293E-315, 1.188317643E-314, 2.020139993E-314, 7.29966552E-315, 1.561788902E-314, 2.716154613E-315, 1.1034378113E-314, 1.9352601614E-314, 6.450867205E-315, 8.3182235E-315, 3.73471259E-315, 2.0371159592E-314, 1.5787648684E-314, 1.1204137775E-314, 6.620626866E-315, 2.037115957E-315 };
                    if (LongJump.getMinecraft55().gameSettings.keyBindForward.isKeyDown()) {
                        try {
                            LongJump.getMinecraft45().player.motionX = n3 * array[LongJump.e(this.k) - 1] * 0.0;
                            LongJump.getMinecraft22().player.motionZ = n5 * array[LongJump.e(this.k) - 1] * 0.0;
                            return;
                        }
                        catch (Exception ex) {
                            return;
                        }
                    }
                    LongJump.getMinecraft31().player.motionX = 0.0;
                    LongJump.getMinecraft103().player.motionZ = 0.0;
                    return;
                }
                ((ITimer)((IMinecraft)LongJump.getMinecraft154()).getTimer()).setTimerSpeed(1.0f);
                LongJump.M(this.k, 0);
                LongJump.b(this.k, LongJump.h(this.k) + 1);
                final EntityPlayerSP player25 = LongJump.getMinecraft69().player;
                player25.motionX /= 0.0;
                final EntityPlayerSP player26 = LongJump.getMinecraft11().player;
                player26.motionZ /= 0.0;
                if (LongJump.h(this.k) == 1) {
                    LongJump.M(this.k, LongJump.getMinecraft58().player.posX, LongJump.getMinecraft3().player.posY, LongJump.getMinecraft67().player.posZ);
                    LongJump.M(this.k, LongJump.getMinecraft28().player.posX + 1.181527256E-314, LongJump.getMinecraft122().player.posY, LongJump.getMinecraft133().player.posZ);
                    LongJump.M(this.k, LongJump.getMinecraft88().player.posX, LongJump.getMinecraft44().player.posY + 7.978704173E-315, LongJump.getMinecraft155().player.posZ);
                    LongJump.M(this.k, LongJump.getMinecraft18().player.posX + 1.181527256E-314, LongJump.getMinecraft27().player.posY, LongJump.getMinecraft78().player.posZ);
                    LongJump.M(this.k, LongJump.getMinecraft138().player.posX, LongJump.getMinecraft43().player.posY + 7.978704173E-315, LongJump.getMinecraft82().player.posZ);
                }
                if (LongJump.h(this.k) > 2) {
                    LongJump.b(this.k, 0);
                    LongJump.getMinecraft70().player.motionX = n3 * 4.24399158E-315;
                    LongJump.getMinecraft125().player.motionZ = n5 * 4.24399158E-315;
                    LongJump.getMinecraft90().player.motionY = 1.856746317E-314;
                    break;
                }
                break;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
