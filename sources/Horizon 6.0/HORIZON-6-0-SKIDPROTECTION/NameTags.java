package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = -1671646, Â = "Some infos about your \"Friends\".", HorizonCode_Horizon_È = "NameTags")
public class NameTags extends Mod
{
    public boolean Ý;
    public boolean Ø­áŒŠá;
    public boolean Âµá€;
    private RenderItem Ó;
    
    public NameTags() {
        this.Ý = true;
        this.Ø­áŒŠá = true;
        this.Âµá€ = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventNametagRender event) {
        this.Ó = new RenderItem(this.Â.Ø­áŒŠá, this.Â.É);
        final Character colorFormatCharacter = new Character('§');
        final EntityOtherPlayerMP entityIn = event.HorizonCode_Horizon_È;
        final float distance = Minecraft.áŒŠà().á.Ø­áŒŠá(entityIn);
        final float scaleFactor = (distance < 4.0f) ? 0.9f : (distance / 4.5f);
        int color = 16777215;
        float height = 0.0f;
        String str = event.Â;
        if (entityIn instanceof EntityPlayer) {
            final EntityPlayer player = entityIn;
            if (FriendManager.HorizonCode_Horizon_È.containsKey(player.v_())) {
                color = -9183489;
                str = colorFormatCharacter + "a" + FriendManager.HorizonCode_Horizon_È.get(player.v_());
            }
            if (player.Ï­Ä() > 16.0f) {
                str = String.valueOf(String.valueOf(str)) + " §8| " + colorFormatCharacter + "2" + MathUtils.HorizonCode_Horizon_È(player.Ï­Ä(), 1);
            }
            else if (player.Ï­Ä() > 8.0f) {
                str = String.valueOf(String.valueOf(str)) + " §8| " + colorFormatCharacter + "6" + MathUtils.HorizonCode_Horizon_È(player.Ï­Ä(), 1);
            }
            else {
                str = String.valueOf(String.valueOf(str)) + " §8| " + colorFormatCharacter + "4" + MathUtils.HorizonCode_Horizon_È(player.Ï­Ä(), 1);
            }
            if (!this.Ý) {
                str = (FriendManager.HorizonCode_Horizon_È.containsKey(player.v_()) ? entityIn.v_() : entityIn.v_());
            }
            if (player.Çªà¢()) {
                str = "§c" + String.valueOf(str);
            }
            if (player.áŒŠÏ()) {
                str = String.valueOf(String.valueOf(str)) + " " + colorFormatCharacter + "6[I]";
            }
            if (this.Âµá€) {
                final int Ping = Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(entityIn.£áŒŠá()).Ý();
                str = String.valueOf(String.valueOf(str)) + " §8| " + colorFormatCharacter + "7 Ping: " + Ping;
            }
            if (distance >= 10.0f) {
                height += (float)(distance / 40.0f - 0.25);
            }
        }
        final FontRenderer var12 = this.Â.µà;
        final float var13 = 1.6f;
        final float var14 = 0.026666671f;
        GL11.glPushMatrix();
        GL11.glEnable(32823);
        GL11.glPolygonOffset(1.0f, -1000000.0f);
        RenderHelper.Â();
        GL11.glTranslatef((float)event.Ý + 0.0f, (float)event.Ø­áŒŠá + entityIn.£ÂµÄ + 0.5f + height, (float)event.Âµá€);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        final RenderManager âµÈ = this.Â.ÂµÈ;
        GL11.glRotatef(-RenderManager.Ø, 0.0f, 1.0f, 0.0f);
        final RenderManager âµÈ2 = this.Â.ÂµÈ;
        GL11.glRotatef(RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-0.026666671f * scaleFactor * 1.4f, -0.026666671f * scaleFactor * 1.4f, 0.026666671f * scaleFactor * 1.4f);
        GL11.glDisable(2912);
        if (this.Ø­áŒŠá) {
            this.HorizonCode_Horizon_È(entityIn);
        }
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        OpenGlHelper.Ý(770, 771, 1, 0);
        final WorldRenderer var15 = Tessellator.HorizonCode_Horizon_È().Ý();
        GL11.glDisable(3553);
        var15.Â();
        final int var16 = var12.HorizonCode_Horizon_È(str) / 2;
        var15.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.25f);
        GL11.glLineWidth(0.3f);
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glBegin(3);
        GL11.glVertex2d((double)(-var16 - 2), -2.0);
        GL11.glVertex2d((double)(-var16 - 2), 10.0);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2d((double)(-var16 - 2), 10.0);
        GL11.glVertex2d((double)(var16 + 2), 10.0);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2d((double)(var16 + 2), 10.0);
        GL11.glVertex2d((double)(var16 + 2), -2.0);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2d((double)(var16 + 2), -2.0);
        GL11.glVertex2d((double)(-var16 - 2), -2.0);
        GL11.glEnd();
        var15.Â(-var16 - 2, -2.0, 0.0);
        var15.Â(-var16 - 2, 10.0, 0.0);
        var15.Â(var16 + 2, 10.0, 0.0);
        var15.Â(var16 + 2, -2.0, 0.0);
        Tessellator.HorizonCode_Horizon_È().Â();
        GL11.glEnable(3553);
        var12.HorizonCode_Horizon_È(str, -var16, 0.0f, color);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        var12.HorizonCode_Horizon_È(str, -var16, 0, color);
        GL11.glEnable(2896);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.Â();
        GL11.glDisable(32823);
        GL11.glPolygonOffset(1.0f, 1000000.0f);
        GL11.glPopMatrix();
        event.HorizonCode_Horizon_È(true);
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer player) {
        byte var16 = 0;
        if (player.Çªà¢()) {
            var16 = 4;
        }
        final FlexibleArray<Byte> armor = new FlexibleArray<Byte>();
        final ItemStack heldStack = player.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (heldStack != null) {
            armor.HorizonCode_Horizon_È(Byte.valueOf((byte)36));
        }
        for (byte b = 8; b >= 5; --b) {
            final ItemStack armorPiece = player.ŒÂ.HorizonCode_Horizon_È(b).HorizonCode_Horizon_È();
            if (armorPiece != null) {
                armor.HorizonCode_Horizon_È(Byte.valueOf(b));
            }
        }
        int x = -(armor.Â() * 8);
        for (final byte b2 : armor) {
            final ItemStack stack = player.ŒÂ.HorizonCode_Horizon_È(b2).HorizonCode_Horizon_È();
            String text = "";
            if (stack != null) {
                int y = 0;
                final int sLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.á.ŒÏ, stack);
                final int fLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.£à.ŒÏ, stack);
                final int kLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Å.ŒÏ, stack);
                if (sLevel > 0) {
                    GL11.glDisable(2896);
                    HorizonCode_Horizon_È("Sh" + sLevel, x, y);
                    y -= 9;
                }
                if (fLevel > 0) {
                    GL11.glDisable(2896);
                    HorizonCode_Horizon_È("Fir" + fLevel, x, y);
                    y -= 9;
                }
                if (kLevel > 0) {
                    GL11.glDisable(2896);
                    HorizonCode_Horizon_È("Kb" + kLevel, x, y);
                }
                else if (stack.HorizonCode_Horizon_È() instanceof ItemArmor) {
                    final int pLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.à.ŒÏ, stack);
                    final int tLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.áˆºÑ¢Õ.ŒÏ, stack);
                    final int uLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ø­à.ŒÏ, stack);
                    if (pLevel > 0) {
                        GL11.glDisable(2896);
                        HorizonCode_Horizon_È("P" + pLevel, x, y);
                        y -= 9;
                    }
                    if (tLevel > 0) {
                        GL11.glDisable(2896);
                        HorizonCode_Horizon_È("Th" + tLevel, x, y);
                        y -= 9;
                    }
                    if (uLevel > 0) {
                        GL11.glDisable(2896);
                        HorizonCode_Horizon_È("Unb" + uLevel, x, y);
                    }
                }
                else if (stack.HorizonCode_Horizon_È() instanceof ItemBow) {
                    final int powLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Æ.ŒÏ, stack);
                    final int punLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Šáƒ.ŒÏ, stack);
                    final int fireLevel = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ï­Ðƒà.ŒÏ, stack);
                    if (powLevel > 0) {
                        GL11.glDisable(2896);
                        HorizonCode_Horizon_È("Pow" + powLevel, x, y);
                        y -= 9;
                    }
                    if (punLevel > 0) {
                        GL11.glDisable(2896);
                        HorizonCode_Horizon_È("Pun" + punLevel, x, y);
                        y -= 9;
                    }
                    if (fireLevel > 0) {
                        GL11.glDisable(2896);
                        HorizonCode_Horizon_È("Fir" + fireLevel, x, y);
                    }
                }
                else if (stack.HorizonCode_Horizon_È() instanceof ItemAppleGold && stack.HorizonCode_Horizon_È().áŒŠÆ(stack) == EnumRarity.Ø­áŒŠá) {
                    text = String.valueOf(String.valueOf(text)) + "God";
                }
                this.Ó.HorizonCode_Horizon_È = -100.0f;
                this.Ó.HorizonCode_Horizon_È(player.ŒÂ.HorizonCode_Horizon_È(b2).HorizonCode_Horizon_È(), x, -18);
                Minecraft.áŒŠà().áˆºÏ().HorizonCode_Horizon_È(Minecraft.áŒŠà().µà, player.ŒÂ.HorizonCode_Horizon_È(b2).HorizonCode_Horizon_È(), x, -18);
                this.Ó.HorizonCode_Horizon_È = 0.0f;
                x += 16;
            }
        }
    }
    
    private static void HorizonCode_Horizon_È(final String text, int x, final int y) {
        x *= 2;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(text, x, (float)(-36 - y), -1286);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
