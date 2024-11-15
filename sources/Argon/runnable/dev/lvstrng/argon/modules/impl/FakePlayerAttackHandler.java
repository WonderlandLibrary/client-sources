// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.impl;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

class FakePlayerAttackHandler implements PlayerInteractEntityC2SPacket.Handler {
    final FakePlayer fakePlayer;

    FakePlayerAttackHandler(final FakePlayer field509) {
        this.fakePlayer = field509;
    }

    @Override
    public void interact(Hand hand) {

    }

    @Override
    public void interactAt(Hand hand, Vec3d pos) {

    }

    @Override
    public void attack() {
        /*final int n = 0;
        final HitResult crosshairTarget = FakePlayer.method158(this.fakePlayer).crosshairTarget;
        final int n2 = n;
        final HitResult hitResult = crosshairTarget;
        if (hitResult instanceof EntityHitResult entityHitResult) {
            final Entity entity = entityHitResult.getEntity();
            if (entity == this.fakePlayer.field223) {
                int n3;
                final boolean b = (this.fakePlayer.field223.method_5805() ? 1 : 0) != 0;
                Label_0416:
                {
                    FakePlayer field509;
                    {
                        if (!b) {
                            return;
                        }
                        field509 = this.fakePlayer;
                        n3 = field509.field223.field_6235;
                        if (n3 == 0) {
                            FakePlayer.method159(this.fakePlayer).world.method_8486(this.fakePlayer.field223.method_23317(), this.fakePlayer.field223.method_23318(), this.fakePlayer.field223.method_23321(), SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                            final boolean method459 = TargetUtil.canAttack((PlayerEntity) FakePlayer.method160(this.fakePlayer).player, entity);
                            final boolean method_5624 = FakePlayer.method161(this.fakePlayer).player.method_5624();
                            final Item item = FakePlayer.method162(this.fakePlayer).player.method_6047().getItem();
                            boolean b3;
                            final boolean b2 = b3 = method459;
                            Label_0360:
                            {
                                if (n2 == 0) {
                                    if (b2) {
                                        FakePlayer.method163(this.fakePlayer).world.method_8486(this.fakePlayer.field223.method_23317(), this.fakePlayer.field223.method_23318(), this.fakePlayer.field223.method_23321(), method_5624 ? SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK : SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                                        if (n2 == 0) {
                                            break Label_0360;
                                        }
                                    }
                                }
                                SoundEvent entity_PLAYER_ATTACK_KNOCKBACK;
                                {
                                    b3 = (item instanceof SwordItem);
                                    entity_PLAYER_ATTACK_KNOCKBACK = (b3 ? SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP : SoundEvents.ENTITY_PLAYER_ATTACK_STRONG);
                                }
                                FakePlayer.method164(this.fakePlayer).world.method_8486(this.fakePlayer.field223.method_23317(), this.fakePlayer.field223.method_23318(), this.fakePlayer.field223.method_23321(), entity_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                            }
                            if (n2 == 0) {
                                break Label_0416;
                            }
                        }
                        final FakePlayer field510 = this.fakePlayer;
                    }
                    FakePlayer.method165(field509).world.method_8486(this.fakePlayer.field223.method_23317(), this.fakePlayer.field223.method_23318(), this.fakePlayer.field223.method_23321(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                }
                final double n4 = EnchantmentHelper.getKnockback((LivingEntity) FakePlayer.method166(this.fakePlayer).player) * 0.5f;
                this.fakePlayer.field223.method_18800(MathHelper.sin(this.fakePlayer.field223.method_36454() * 0.017453292f) * n4, 0.5, -MathHelper.cos(this.fakePlayer.field223.method_36454() * 0.017453292f) * n4);
            }
        }*/
    }
}
