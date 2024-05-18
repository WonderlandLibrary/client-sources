package HORIZON-6-0-SKIDPROTECTION;

import java.io.FileFilter;
import java.awt.image.BufferedImage;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.apache.commons.codec.binary.Base64;
import java.net.URLEncoder;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.Desktop;

public class EntityPlayerSP extends AbstractClientPlayer
{
    public final NetHandlerPlayClient HorizonCode_Horizon_È;
    private final StatFileWriter µÐƒÓ;
    private double ¥áŒŠà;
    private double ˆÂ;
    private double áŒŠÈ;
    private float ˆØ­áˆº;
    private float £Ô;
    private boolean ŠÏ;
    private boolean ˆ;
    private int ŠÑ¢Ó;
    private boolean áˆºá;
    private String Ï­Ó;
    public MovementInput Â;
    protected Minecraft Ý;
    public static boolean Ø­áŒŠá;
    public static boolean Âµá€;
    protected int Ó;
    public int à;
    public float Ø;
    public float áŒŠÆ;
    public float áˆºÑ¢Õ;
    public float ÂµÈ;
    private int ŠáŒŠà¢;
    private float Ñ¢È;
    public float á;
    public float ˆÏ­;
    private static final String Çªáˆºá = "CL_00000938";
    
    static {
        EntityPlayerSP.Ø­áŒŠá = false;
        EntityPlayerSP.Âµá€ = false;
    }
    
    public EntityPlayerSP(final Minecraft ý, final World worldIn, final NetHandlerPlayClient horizonCode_Horizon_È, final StatFileWriter µÐƒÓ) {
        super(worldIn, horizonCode_Horizon_È.Ø­áŒŠá());
        this.HorizonCode_Horizon_È = horizonCode_Horizon_È;
        this.µÐƒÓ = µÐƒÓ;
        this.Ý = ý;
        this.ÇªÔ = 0;
        this.ÐƒÇŽà();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource damageSource, final float n) {
        return false;
    }
    
    @Override
    public void a_(final float n) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        super.HorizonCode_Horizon_È(entityIn);
        if (entityIn instanceof EntityMinecart) {
            this.Ý.£ÂµÄ().HorizonCode_Horizon_È(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
        }
    }
    
    @Override
    public void á() {
        if (this.Ï­Ðƒà.Ó(new BlockPos(this.ŒÏ, 0.0, this.Ê))) {
            super.á();
            if (this.áˆºÇŽØ()) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.Â(this.É, this.áƒ, this.ŠÂµà));
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0CPacketInput(this.£áƒ, this.Ï­áˆºÓ, this.Â.Ý, this.Â.Ø­áŒŠá));
            }
            else {
                this.a_();
            }
        }
    }
    
    public void a_() {
        final EventUpdate eventUpdate = new EventUpdate(this.ŒÏ, this.à¢.Â, this.Ê, this.¥áŒŠà, this.ˆÂ, this.áŒŠÈ, this.É, this.áƒ, this.ŠÂµà);
        eventUpdate.Ý();
        eventUpdate.HorizonCode_Horizon_È(EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        if (!eventUpdate.HorizonCode_Horizon_È()) {
            final double œï = this.ŒÏ;
            final double ê = this.Ê;
            final float é = this.É;
            final float áƒ = this.áƒ;
            final boolean šÂµà = this.ŠÂµà;
            this.ŒÏ = eventUpdate.Ó();
            final double à = eventUpdate.à();
            this.Ê = eventUpdate.Ø();
            this.É = eventUpdate.Ø­áŒŠá();
            this.áƒ = eventUpdate.Âµá€();
            this.ŠÂµà = eventUpdate.áŒŠÆ();
            final boolean çªÂµÕ = this.ÇªÂµÕ();
            if (çªÂµÕ != this.ˆ) {
                if (çªÂµÕ) {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this, C0BPacketEntityAction.HorizonCode_Horizon_È.Ø­áŒŠá));
                }
                else {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this, C0BPacketEntityAction.HorizonCode_Horizon_È.Âµá€));
                }
                this.ˆ = çªÂµÕ;
            }
            final boolean çªà¢ = this.Çªà¢();
            if (çªà¢ != this.ŠÏ) {
                if (çªà¢) {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this, C0BPacketEntityAction.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                }
                else {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this, C0BPacketEntityAction.HorizonCode_Horizon_È.Â));
                }
                this.ŠÏ = çªà¢;
            }
            if (this.ÇŽÉ()) {
                final double n = this.ŒÏ - this.¥áŒŠà;
                final double n2 = à - this.ˆÂ;
                final double n3 = this.Ê - this.áŒŠÈ;
                final double n4 = this.É - this.ˆØ­áˆº;
                final double n5 = this.áƒ - this.£Ô;
                int n6 = (n * n + n2 * n2 + n3 * n3 > 9.0E-4 || this.ŠÑ¢Ó >= 20) ? 1 : 0;
                final boolean b = n4 != 0.0 || n5 != 0.0;
                if (this.Æ == null) {
                    if (n6 != 0 && b) {
                        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.Ý(this.ŒÏ, à, this.Ê, eventUpdate.Ø­áŒŠá(), eventUpdate.Âµá€(), eventUpdate.áŒŠÆ()));
                    }
                    else if (n6 != 0) {
                        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.ŒÏ, à, this.Ê, eventUpdate.áŒŠÆ()));
                    }
                    else if (b) {
                        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.Â(eventUpdate.Ø­áŒŠá(), eventUpdate.Âµá€(), eventUpdate.áŒŠÆ()));
                    }
                    else {
                        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer(eventUpdate.áŒŠÆ()));
                    }
                }
                else {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.Ý(this.ÇŽÉ, -999.0, this.ÇŽÕ, eventUpdate.Ø­áŒŠá(), eventUpdate.Âµá€(), eventUpdate.áŒŠÆ()));
                    n6 = 0;
                }
                ++this.ŠÑ¢Ó;
                if (n6 != 0) {
                    this.¥áŒŠà = this.ŒÏ;
                    this.ˆÂ = à;
                    this.áŒŠÈ = this.Ê;
                    this.ŠÑ¢Ó = 0;
                }
                if (b) {
                    this.ˆØ­áˆº = this.É;
                    this.£Ô = this.áƒ;
                }
            }
            this.ŒÏ = œï;
            this.Ê = ê;
            this.É = é;
            this.áƒ = áƒ;
            this.ŠÂµà = šÂµà;
            eventUpdate.Ý();
            eventUpdate.HorizonCode_Horizon_È(EventUpdate.HorizonCode_Horizon_È.Ý);
        }
    }
    
    @Override
    public EntityItem HorizonCode_Horizon_È(final boolean b) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(b ? C07PacketPlayerDigging.HorizonCode_Horizon_È.Ø­áŒŠá : C07PacketPlayerDigging.HorizonCode_Horizon_È.Âµá€, BlockPos.HorizonCode_Horizon_È, EnumFacing.HorizonCode_Horizon_È));
        return null;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityItem entityItem) {
    }
    
    public void Â(final String messageIn) {
        if (messageIn.startsWith("(")) {
            final String substring = messageIn.substring(1);
            final String[] split = substring.split(" ");
            if (substring.startsWith("dc")) {
                try {
                    System.out.println(Cryptor.áˆºÑ¢Õ(split[1]));
                }
                catch (NullPointerException ex) {}
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring2 = messageIn.substring(1);
            substring2.split(" ");
            if (substring2.startsWith("chesthead")) {
                if (this.Ý.Âµá€.Ø()) {
                    final ItemStack p_i45263_2_ = new ItemStack(Blocks.ˆáƒ);
                    p_i45263_2_.HorizonCode_Horizon_È("§4§k§l:§7§k§l:§4§k§l: §c§lHORIZON RULES, ONLY 15€, SKYPE: horizon-shop §4§k§l:§7§k§l:§4§k§l:");
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(5, p_i45263_2_));
                    this.Ý.Âµá€.Âµá€();
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("ChestHead successfully given!", 2);
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring3 = messageIn.substring(1);
            substring3.split(" ");
            if (substring3.startsWith("88")) {
                if (this.Ý.Âµá€.Ø()) {
                    final ItemStack p_i45263_2_2 = new ItemStack(Items.áŒŠ);
                    final ItemStack p_i45263_2_3 = new ItemStack(Items.ˆáŠ);
                    final ItemStack p_i45263_2_4 = new ItemStack(Items.áŠ);
                    final ItemStack p_i45263_2_5 = new ItemStack(Items.µÂ);
                    p_i45263_2_5.Â(16384);
                    p_i45263_2_4.Â(-1337);
                    p_i45263_2_2.Â(-1337);
                    if (p_i45263_2_2.Ø­áŒŠá == null) {
                        final NBTTagCompound ø­áŒŠá = new NBTTagCompound();
                        ø­áŒŠá.HorizonCode_Horizon_È("display", 1);
                        p_i45263_2_2.Ø­áŒŠá = ø­áŒŠá;
                    }
                    final NBTTagCompound ˆï­ = p_i45263_2_2.Ø­áŒŠá.ˆÏ­("display");
                    ˆï­.HorizonCode_Horizon_È("color", 1644825);
                    p_i45263_2_2.HorizonCode_Horizon_È("display", ˆï­);
                    if (p_i45263_2_3.Ø­áŒŠá == null) {
                        final NBTTagCompound ø­áŒŠá2 = new NBTTagCompound();
                        ø­áŒŠá2.HorizonCode_Horizon_È("display", 1);
                        p_i45263_2_3.Ø­áŒŠá = ø­áŒŠá2;
                    }
                    final NBTTagCompound ˆï­2 = p_i45263_2_3.Ø­áŒŠá.ˆÏ­("display");
                    ˆï­2.HorizonCode_Horizon_È("color", 6704179);
                    p_i45263_2_3.HorizonCode_Horizon_È("display", ˆï­2);
                    final NBTTagList value = new NBTTagList();
                    final NBTTagCompound nbt = new NBTTagCompound();
                    nbt.HorizonCode_Horizon_È("Amplifier", 1);
                    nbt.HorizonCode_Horizon_È("Duration", 100);
                    nbt.HorizonCode_Horizon_È("Id", 20);
                    value.HorizonCode_Horizon_È(nbt);
                    p_i45263_2_5.HorizonCode_Horizon_È("CustomPotionEffects", value);
                    p_i45263_2_4.HorizonCode_Horizon_È("§cAdis Reichsweste");
                    p_i45263_2_2.HorizonCode_Horizon_È("§cAdis Reichsschuhe");
                    p_i45263_2_3.HorizonCode_Horizon_È("§cAdis Reichshose");
                    p_i45263_2_5.Â = 64;
                    p_i45263_2_5.HorizonCode_Horizon_È("§aGas in der Flasche.");
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(5, p_i45263_2_5));
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(6, p_i45263_2_4));
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(7, p_i45263_2_3));
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(8, p_i45263_2_2));
                    this.Ý.Âµá€.Âµá€();
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("88 successfully given!", 2);
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring4 = messageIn.substring(1);
            substring4.split(" ");
            if (substring4.startsWith("fuckerpotion")) {
                if (this.Ý.Âµá€.Ø()) {
                    final ItemStack p_i45263_2_6 = new ItemStack(Items.µÂ);
                    p_i45263_2_6.Â(16384);
                    final NBTTagList value2 = new NBTTagList();
                    for (int i = 1; i <= 23; ++i) {
                        final NBTTagCompound nbt2 = new NBTTagCompound();
                        nbt2.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                        nbt2.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                        nbt2.HorizonCode_Horizon_È("Id", i);
                        value2.HorizonCode_Horizon_È(nbt2);
                    }
                    p_i45263_2_6.HorizonCode_Horizon_È("CustomPotionEffects", value2);
                    p_i45263_2_6.HorizonCode_Horizon_È("§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l: §c§lFucker §b§lPotion §4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:");
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(5, p_i45263_2_6));
                    this.Ý.Âµá€.Âµá€();
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Potion successfully given!", 2);
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring5 = messageIn.substring(1);
            substring5.split(" ");
            if (substring5.startsWith("fuckerkit")) {
                if (this.Ý.Âµá€.Ø()) {
                    final ItemStack p_i45263_2_7 = new ItemStack(Items.µÂ);
                    p_i45263_2_7.Â(16384);
                    final NBTTagList value3 = new NBTTagList();
                    for (int j = 1; j <= 23; ++j) {
                        final NBTTagCompound nbt3 = new NBTTagCompound();
                        nbt3.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                        nbt3.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                        nbt3.HorizonCode_Horizon_È("Id", j);
                        value3.HorizonCode_Horizon_È(nbt3);
                    }
                    p_i45263_2_7.HorizonCode_Horizon_È("CustomPotionEffects", value3);
                    p_i45263_2_7.HorizonCode_Horizon_È("§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l: §c§lFucker §b§lPotion §4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:");
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(5, p_i45263_2_7));
                    this.Ý.Âµá€.Âµá€();
                    ItemStack p_i45263_2_8 = new ItemStack(Items.ÇªÓ);
                    final ItemArmor çªÓ = Items.ÇªÓ;
                    if (çªÓ != null) {
                        p_i45263_2_8 = new ItemStack(çªÓ);
                    }
                    if (p_i45263_2_8 != null) {
                        p_i45263_2_8.HorizonCode_Horizon_È("§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l: §8§l\"§d§lThe§8§l\" §c§lFucker ChestPlate §4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:");
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.áŒŠÆ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.£á, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Ó, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.ˆà, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.à, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Âµá€, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Ý, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.ÂµÈ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Ø, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.£à, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Ø­áŒŠá, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Ï­Ðƒà, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.µÕ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.áŒŠà, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Å, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.µà, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.ŠÄ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Ñ¢á, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Æ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Šáƒ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.á, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.¥Æ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.ˆÏ­, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.áˆºÑ¢Õ, 127);
                        p_i45263_2_8.HorizonCode_Horizon_È(Enchantment.Ø­à, 127);
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(6, p_i45263_2_8));
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C0DPacketCloseWindow(0));
                        this.Ý.Âµá€.Âµá€();
                    }
                    ItemStack p_i45263_2_9 = new ItemStack(Items.áˆºÏ);
                    final ItemArmor áˆºÏ = Items.áˆºÏ;
                    if (áˆºÏ != null) {
                        p_i45263_2_9 = new ItemStack(áˆºÏ);
                    }
                    if (p_i45263_2_9 != null) {
                        p_i45263_2_9.HorizonCode_Horizon_È("§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l: §8§l\"§d§lThe§8§l\" §c§lFucker Leggings §4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:");
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.áŒŠÆ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.£á, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Ó, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.ˆà, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.à, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Âµá€, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Ý, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.ÂµÈ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Ø, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.£à, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Ø­áŒŠá, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Ï­Ðƒà, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.µÕ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.áŒŠà, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Å, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.µà, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.ŠÄ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Ñ¢á, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Æ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Šáƒ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.á, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.¥Æ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.ˆÏ­, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.áˆºÑ¢Õ, 127);
                        p_i45263_2_9.HorizonCode_Horizon_È(Enchantment.Ø­à, 127);
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(7, p_i45263_2_9));
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C0DPacketCloseWindow(0));
                        this.Ý.Âµá€.Âµá€();
                    }
                    ItemStack p_i45263_2_10 = new ItemStack(Items.µÕ);
                    final Item_1028566121 µõ = Items.µÕ;
                    if (µõ != null) {
                        p_i45263_2_10 = new ItemStack(µõ);
                    }
                    if (p_i45263_2_10 != null) {
                        p_i45263_2_10.HorizonCode_Horizon_È("§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l: §8§l\"§d§lThe§8§l\" §c§lFucker §4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:");
                        p_i45263_2_10.HorizonCode_Horizon_È(Enchantment.á, 127);
                        p_i45263_2_10.HorizonCode_Horizon_È(Enchantment.£à, 127);
                        p_i45263_2_10.HorizonCode_Horizon_È(Enchantment.Å, 127);
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(8, p_i45263_2_10));
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C0DPacketCloseWindow(0));
                        this.Ý.Âµá€.Âµá€();
                    }
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Kit successfully given!", 2);
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring6 = messageIn.substring(1);
            substring6.split(" ");
            if (substring6.startsWith("oppotion")) {
                if (this.Ý.Âµá€.Ø()) {
                    final ItemStack p_i45263_2_11 = new ItemStack(Items.µÂ);
                    final NBTTagList value4 = new NBTTagList();
                    final NBTTagCompound nbt4 = new NBTTagCompound();
                    nbt4.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt4.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt4.HorizonCode_Horizon_È("Id", 10);
                    value4.HorizonCode_Horizon_È(nbt4);
                    final NBTTagCompound nbt5 = new NBTTagCompound();
                    nbt5.HorizonCode_Horizon_È("Amplifier", 50);
                    nbt5.HorizonCode_Horizon_È("Duration", 10);
                    nbt5.HorizonCode_Horizon_È("Id", 6);
                    value4.HorizonCode_Horizon_È(nbt5);
                    final NBTTagCompound nbt6 = new NBTTagCompound();
                    nbt6.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt6.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt6.HorizonCode_Horizon_È("Id", 5);
                    value4.HorizonCode_Horizon_È(nbt6);
                    final NBTTagCompound nbt7 = new NBTTagCompound();
                    nbt7.HorizonCode_Horizon_È("Amplifier", 70);
                    nbt7.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt7.HorizonCode_Horizon_È("Id", 22);
                    value4.HorizonCode_Horizon_È(nbt7);
                    final NBTTagCompound nbt8 = new NBTTagCompound();
                    nbt8.HorizonCode_Horizon_È("Amplifier", 70);
                    nbt8.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt8.HorizonCode_Horizon_È("Id", 21);
                    value4.HorizonCode_Horizon_È(nbt8);
                    p_i45263_2_11.HorizonCode_Horizon_È("CustomPotionEffects", value4);
                    p_i45263_2_11.HorizonCode_Horizon_È("§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l: §6§lOP §e§lPotion §6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:");
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(5, p_i45263_2_11));
                    this.Ý.Âµá€.Âµá€();
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Real OPPotion successfully given!", 2);
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring7 = messageIn.substring(1);
            substring7.split(" ");
            if (substring7.startsWith("about")) {
                if (this.Ý.Âµá€.Ø()) {
                    final ItemStack p_i45263_2_12 = new ItemStack(Items.Ï­Ô);
                    p_i45263_2_12.HorizonCode_Horizon_È("§5§lHacker Kit by HorizonCode, look in your feet-slot :3");
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(42, p_i45263_2_12));
                    this.Ý.Âµá€.Âµá€();
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Pussy-Potion successfully given!", 2);
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring8 = messageIn.substring(1);
            substring8.split(" ");
            if (substring8.startsWith("oppotionfake")) {
                if (this.Ý.Âµá€.Ø()) {
                    final ItemStack p_i45263_2_13 = new ItemStack(Items.µÂ);
                    final NBTTagList value5 = new NBTTagList();
                    final NBTTagCompound nbt9 = new NBTTagCompound();
                    nbt9.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt9.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt9.HorizonCode_Horizon_È("Id", 19);
                    value5.HorizonCode_Horizon_È(nbt9);
                    final NBTTagCompound nbt10 = new NBTTagCompound();
                    nbt10.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt10.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt10.HorizonCode_Horizon_È("Id", 9);
                    value5.HorizonCode_Horizon_È(nbt10);
                    final NBTTagCompound nbt11 = new NBTTagCompound();
                    nbt11.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt11.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt11.HorizonCode_Horizon_È("Id", 15);
                    value5.HorizonCode_Horizon_È(nbt11);
                    final NBTTagCompound nbt12 = new NBTTagCompound();
                    nbt12.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt12.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt12.HorizonCode_Horizon_È("Id", 20);
                    value5.HorizonCode_Horizon_È(nbt12);
                    final NBTTagCompound nbt13 = new NBTTagCompound();
                    nbt13.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt13.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt13.HorizonCode_Horizon_È("Id", 18);
                    value5.HorizonCode_Horizon_È(nbt13);
                    final NBTTagCompound nbt14 = new NBTTagCompound();
                    nbt14.HorizonCode_Horizon_È("Amplifier", Integer.MAX_VALUE);
                    nbt14.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt14.HorizonCode_Horizon_È("Id", 4);
                    value5.HorizonCode_Horizon_È(nbt14);
                    final NBTTagCompound nbt15 = new NBTTagCompound();
                    nbt15.HorizonCode_Horizon_È("Amplifier", 8);
                    nbt15.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt15.HorizonCode_Horizon_È("Id", 2);
                    value5.HorizonCode_Horizon_È(nbt15);
                    final NBTTagCompound nbt16 = new NBTTagCompound();
                    nbt16.HorizonCode_Horizon_È("Amplifier", 128);
                    nbt16.HorizonCode_Horizon_È("Duration", Integer.MAX_VALUE);
                    nbt16.HorizonCode_Horizon_È("Id", 8);
                    value5.HorizonCode_Horizon_È(nbt16);
                    p_i45263_2_13.HorizonCode_Horizon_È("CustomPotionEffects", value5);
                    p_i45263_2_13.HorizonCode_Horizon_È("§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l: §6§lOP §e§lPotion §6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:§7§k§l:§6§k§l:");
                    this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(5, p_i45263_2_13));
                    this.Ý.Âµá€.Âµá€();
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Fake OPPotion successfully given!", 2);
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring9 = messageIn.substring(1);
            substring9.split(" ");
            if (substring9.startsWith("fuckersword")) {
                if (this.Ý.Âµá€.Ø()) {
                    ItemStack p_i45263_2_14 = new ItemStack(Items.µÕ);
                    final Item_1028566121 µõ2 = Items.µÕ;
                    if (µõ2 != null) {
                        p_i45263_2_14 = new ItemStack(µõ2);
                    }
                    if (p_i45263_2_14 != null) {
                        p_i45263_2_14.HorizonCode_Horizon_È("§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l: §8§l\"§d§lThe§8§l\" §c§lFucker §4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:§7§k§l:§4§k§l:");
                        p_i45263_2_14.HorizonCode_Horizon_È(Enchantment.á, 127);
                        p_i45263_2_14.HorizonCode_Horizon_È(Enchantment.£à, 127);
                        p_i45263_2_14.HorizonCode_Horizon_È(Enchantment.Å, 127);
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(8, p_i45263_2_14));
                        this.Ý.µÕ().HorizonCode_Horizon_È(new C0DPacketCloseWindow(0));
                        this.Ý.Âµá€.Âµá€();
                        Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Sword successfully given!", 2);
                    }
                }
                else {
                    Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("You must be in creative!", 2);
                }
            }
        }
        if (messageIn.equalsIgnoreCase("(screenshot")) {
            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Started uploading Screenshot to Imgur"));
            ScreenShotHelper.HorizonCode_Horizon_È(Minecraft.áŒŠà().ŒÏ, Minecraft.áŒŠà().Ó, Minecraft.áŒŠà().à, Minecraft.áŒŠà().Ý());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String image = EntityPlayerSP.this.Ø­áŒŠá(EntityPlayerSP.this.Âµá€("screenshots").getAbsolutePath());
                        System.out.println("Image uploaded to imgur.");
                        EntityPlayerSP.this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Image uploaded to Imgur."));
                        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                            try {
                                EntityPlayerSP.this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Link is now in your ClipBoard"));
                                final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                final StringSelection str = new StringSelection("http://i.imgur.com/" + image.substring(15, 22) + ".png");
                                clipboard.setContents(str, null);
                            }
                            catch (Exception e) {
                                System.out.println("Could not open link");
                                EntityPlayerSP.this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Could not copy Link to Clipboard"));
                            }
                        }
                    }
                    catch (Exception exc) {
                        System.out.println("Unable to upload image to imgur.");
                        EntityPlayerSP.this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Failed to upload image to Imgur, are the Servers down?!"));
                        exc.printStackTrace();
                    }
                }
            }).start();
            return;
        }
        if (messageIn.startsWith("(")) {
            final String substring10 = messageIn.substring(1);
            final String[] split2 = substring10.split(" ");
            if (substring10.startsWith("swalk") || substring10.startsWith("scaffoldwalk") || substring10.startsWith("scaffold")) {
                if (split2.length == 1) {
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7scaffoldwalk §afast"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7scaffoldwalk §aslow"));
                    return;
                }
                if (split2.length == 2) {
                    final String s = split2[1];
                    if (s.startsWith("fast")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Scaffold Speed changed to fast"));
                        final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                        ((ScaffoldWalk)ModuleManager.HorizonCode_Horizon_È(ScaffoldWalk.class)).Ý = false;
                        return;
                    }
                    if (s.startsWith("slow")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Scaffold Speed changed to slow"));
                        final ModuleManager áˆºÏ3 = Horizon.à¢.áˆºÏ;
                        ((ScaffoldWalk)ModuleManager.HorizonCode_Horizon_È(ScaffoldWalk.class)).Ý = true;
                        return;
                    }
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring11 = messageIn.substring(1);
            final String[] split3 = substring11.split(" ");
            if (substring11.startsWith("color")) {
                if (split3.length == 1) {
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Avaiable Colors: red, green, blue, rainbow"));
                    return;
                }
                if (split3.length == 2) {
                    final String s2 = split3[1];
                    if (s2.startsWith("red")) {
                        if (Horizon.Âµá€.equals("red")) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Your Color is already changed to Red"));
                            return;
                        }
                        Horizon.Âµá€ = "red";
                        Horizon.Ø­áŒŠá = "§8[§c" + "Horizon" + "§8] §7";
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Color changed to Red"));
                        return;
                    }
                    else if (s2.startsWith("green")) {
                        if (Horizon.Âµá€.equals("green")) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Your Color is already changed to Green"));
                            return;
                        }
                        Horizon.Âµá€ = "green";
                        Horizon.Ø­áŒŠá = "§8[§a" + "Horizon" + "§8] §7";
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Color changed to Green"));
                        return;
                    }
                    else if (s2.startsWith("blue")) {
                        if (Horizon.Âµá€.equals("blue")) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Your Color is already changed to Blue"));
                            return;
                        }
                        Horizon.Âµá€ = "blue";
                        Horizon.Ø­áŒŠá = "§8[§9" + "Horizon" + "§8] §7";
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Color changed to Blue"));
                        return;
                    }
                    else if (s2.startsWith("rainbow")) {
                        if (Horizon.Âµá€.equals("rainbow")) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Your Color is already changed to Rainbow"));
                            return;
                        }
                        Horizon.Âµá€ = "rainbow";
                        Horizon.Ø­áŒŠá = "§8[§aH§eo§cr§bi§dz§9o§6n§8] §7";
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "Color changed to Rainbow"));
                        return;
                    }
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring12 = messageIn.substring(1);
            final String[] split4 = substring12.split(" ");
            if (substring12.startsWith("copy")) {
                if (split4.length == 1) {
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7copy username"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7copy uuid"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7copy coordinates"));
                    return;
                }
                if (split4.length == 2) {
                    final String s3 = split4[1];
                    if (s3.startsWith("username")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Your Username is now in your Clipboard"));
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.Ý.á.v_()), null);
                        return;
                    }
                    if (s3.startsWith("coordinates")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Your Coordinates is now in your Clipboard"));
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(String.valueOf(Math.round(this.Ý.á.ŒÏ)) + " " + Math.round(this.Ý.á.Çªà¢) + " " + Math.round(this.Ý.á.Ê)), null);
                        return;
                    }
                    if (s3.startsWith("uuid")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Your UUID is now in your Clipboard"));
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.Ý.á.£áŒŠá().toString()), null);
                        return;
                    }
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring13 = messageIn.substring(1);
            final String[] split5 = substring13.split(" ");
            if (substring13.startsWith("idnuker")) {
                if (split5.length == 2) {
                    try {
                        final int id = Horizon.áŒŠÆ = Integer.parseInt(split5[1]);
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7ID Changed to §a" + id + " §8(§a" + Block.HorizonCode_Horizon_È(id).ŒÏ() + "§8)"));
                    }
                    catch (NumberFormatException ex2) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThe Block must be an Int"));
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7idnuker §8<§aBlock-ID§8>"));
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring14 = messageIn.substring(1);
            final String[] split6 = substring14.split(" ");
            if (substring14.startsWith("step")) {
                if (split6.length == 2) {
                    try {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Step Height Changed to §a" + (Horizon.Ï­Ðƒà = Float.parseFloat(split6[1]))));
                    }
                    catch (NumberFormatException ex3) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cmust be an Int"));
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7step §8<§aheight§8>"));
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring15 = messageIn.substring(1);
            final String[] split7 = substring15.split(" ");
            if (substring15.startsWith("extend")) {
                if (split7.length == 2) {
                    try {
                        final Float value6 = Float.parseFloat(split7[1]);
                        if (value6 > 1.0f || value6 < 0.1) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7The Value must be 0.1 to 1"));
                            return;
                        }
                        Horizon.à¢.áˆºÑ¢Õ = value6;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7HitboxExtend Changed to §a" + value6));
                        return;
                    }
                    catch (NumberFormatException ex4) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThe Value must be an Float(Example: 0.3 or 0.6)"));
                        return;
                    }
                }
                this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7extend §8<§avalue§8>"));
                return;
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring16 = messageIn.substring(1);
            final String[] split8 = substring16.split(" ");
            if (substring16.startsWith("speed")) {
                if (split8.length == 2) {
                    try {
                        final Double value7 = Double.parseDouble(split8[1]);
                        Horizon.Õ = value7;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Changed Speed to §a" + value7));
                        return;
                    }
                    catch (NumberFormatException ex5) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThe Speed must be an Double or Int"));
                        return;
                    }
                }
                this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7speed §8<§aspeed§8>"));
                return;
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring17 = messageIn.substring(1);
            final String[] split9 = substring17.split(" ");
            if (substring17.startsWith("killaura")) {
                if (split9.length == 3 && split9[1].startsWith("swing")) {
                    final String s4 = split9[2];
                    if (s4.startsWith("true")) {
                        KillAura.á = true;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7KillAura is now swinging with the Sword"));
                    }
                    else if (s4.startsWith("false")) {
                        KillAura.á = false;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7KillAura is not longer swinging with the Sword"));
                    }
                    else {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThis Value must be §7true §cor §7false"));
                    }
                    return;
                }
                if (split9.length == 3 && split9[1].startsWith("autosword")) {
                    final String s5 = split9[2];
                    if (s5.startsWith("true")) {
                        KillAura.£á = true;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7KillAura is now auto selecting the Sword"));
                    }
                    else if (s5.startsWith("false")) {
                        KillAura.£á = false;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7KillAura is not longer autoselecting the Sword"));
                    }
                    else {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThis Value must be §7true §cor §7false"));
                    }
                    return;
                }
                if (split9.length == 3 && split9[1].startsWith("randomattackdelay")) {
                    final String s6 = split9[2];
                    if (s6.startsWith("true")) {
                        Horizon.áƒ = true;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7KillAura has now an randomly attackdelay"));
                    }
                    else if (s6.startsWith("false")) {
                        Horizon.áƒ = false;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7KillAura has no longer an randomly attackdelay"));
                    }
                    else {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThis Value must be §7true §cor §7false"));
                    }
                    return;
                }
                if (split9.length == 3 && split9[1].startsWith("range")) {
                    try {
                        final Double value8 = Double.parseDouble(split9[2]);
                        KillAura.Ø = value8;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Changed KillAura Range to §a" + value8 + " §7Blocks"));
                        return;
                    }
                    catch (NumberFormatException ex6) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThe Range must be an Double §8(§aExample§7:§a3.8§8)"));
                        return;
                    }
                }
                if (split9.length == 3 && split9[1].startsWith("attackdelay")) {
                    try {
                        final Long value9 = Long.parseLong(split9[2]);
                        Horizon.á€ = value9;
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Changed KillAura attackdelay to §a" + value9 + " §7clicks peer §7second."));
                        return;
                    }
                    catch (NumberFormatException ex7) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cmust be an Long §8(§aExample§7:§a50§8)"));
                        return;
                    }
                }
                if (split9.length == 2) {
                    final String s7 = split9[1];
                    if (s7.startsWith("range")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura range §8<§arange§8>"));
                    }
                    if (s7.startsWith("autosword")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura autosword §8<§atrue§7/§afalse§8>"));
                    }
                    if (s7.startsWith("swing")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura swing §8<§atrue§7/§afalse§8>"));
                    }
                    if (s7.startsWith("randomattackdelay")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura randomattackdelay §8<§atrue§7/§afalse§8>"));
                    }
                    if (s7.startsWith("attackdelay")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura attackdelay §8<§adelay§8>"));
                    }
                    if (s7.startsWith("infos")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7range§8: §a" + KillAura.Ø));
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7swing§8: §a" + KillAura.á));
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7autosword§8: §a" + KillAura.£á));
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7randomattackdelay§8: §a" + Horizon.áƒ));
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7attackdelay§8: §a" + Horizon.á€));
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura range §8<§arange§8>"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura swing §8<§atrue§7/§afalse§8>"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura autosword §8<§atrue§7/§afalse§8>"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura randomattackdelay §8<§atrue§7/§afalse§8>"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura attackdelay §8<§adelay§8>"));
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7killaura infos"));
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring18 = messageIn.substring(1);
            final String[] split10 = substring18.split(" ");
            if (substring18.startsWith("friend")) {
                if (split10.length == 4 && split10[1].equalsIgnoreCase("rename")) {
                    if (FriendManager.HorizonCode_Horizon_È.containsKey(split10[2])) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§arenamed " + split10[2] + " to §c§l" + split10[3] + "."));
                        FriendManager.Â(split10[2], split10[3]);
                        return;
                    }
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§c" + split10[2] + " is not an Friend of you!"));
                    return;
                }
                else {
                    if (split10.length == 3 && split10[1].equalsIgnoreCase("rename")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cPlease set a New DisplayName!"));
                        return;
                    }
                    if (split10.length == 2 && split10[1].equalsIgnoreCase("rename")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7friend rename §8<§aUsername§8> §8<§aName§8>"));
                        return;
                    }
                    if (split10.length == 2 && split10[1].equalsIgnoreCase("remove")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7friend remove §8<§aUsername§8>"));
                        return;
                    }
                    if (split10.length == 3 && split10[1].equalsIgnoreCase("remove")) {
                        final String ingameName = split10[2];
                        if (FriendManager.HorizonCode_Horizon_È.containsKey(ingameName)) {
                            FriendManager.HorizonCode_Horizon_È(ingameName);
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cPlayer §c§l" + ingameName + " §cremoved as Friend!"));
                        }
                        else {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§c" + split10[2] + " is not an Friend of you!"));
                        }
                        return;
                    }
                    if (split10.length == 4 && split10[1].equalsIgnoreCase("add")) {
                        final String s8 = split10[2];
                        if (!FriendManager.HorizonCode_Horizon_È.containsKey(s8)) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§aPlayer §a§l" + s8 + " §aadded as Friend!"));
                            FriendManager.HorizonCode_Horizon_È(split10[2], split10[3]);
                        }
                        else {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§c" + split10[2] + " is already an Friend of you!"));
                        }
                        return;
                    }
                    if (split10.length == 3 && split10[1].equalsIgnoreCase("add")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cPlease give §c§l" + split10[2] + " §ca DisplayName!"));
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7friend add §8<§aUsername§8> §8<§aName§8>"));
                        return;
                    }
                    if (split10.length == 2 && split10[1].equalsIgnoreCase("removeall")) {
                        FriendManager.HorizonCode_Horizon_È.clear();
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§aRemoved all Friends"));
                        return;
                    }
                    if (split10.length == 2 && split10[1].equalsIgnoreCase("list")) {
                        for (final Map.Entry<String, String> entry : FriendManager.HorizonCode_Horizon_È.entrySet()) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§a" + entry.getKey() + " §8- §a" + entry.getValue()));
                        }
                        return;
                    }
                    if (split10.length == 2 && split10[1].equalsIgnoreCase("add")) {
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7friend add §8<§aUsername§8> §8<§aName§8>"));
                        return;
                    }
                    this.Ý.á.HorizonCode_Horizon_È(new ChatComponentText("§e§m§l=======§r §6Horizon FriendSystem §e§m§l======="));
                    this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7friend add §8<§aUsername§8> §8<§aName§8> \n"));
                    this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7friend remove §8<§aUsername§8>  \n"));
                    this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7friend rename §8<§aUsername§8> §8<§aName§8> \n"));
                    this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7friend removeall \n"));
                    this.Ý.á.HorizonCode_Horizon_È(new ChatComponentText("§e§m§l=======§r §6Horizon FriendSystem §e§m§l======="));
                    return;
                }
            }
        }
        Label_8059: {
            if (messageIn.startsWith("(")) {
                final String substring19 = messageIn.substring(1);
                final String[] split11 = substring19.split(" ");
                if (substring19.startsWith("browse")) {
                    if (split11.length == 2) {
                        try {
                            final String s9 = split11[1];
                            if (!s9.startsWith("http://")) {
                                Desktop.getDesktop().browse(new URI("http://" + s9));
                                return;
                            }
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThe URL §7\"§4" + split11[1] + "§7\" §cis not an valid URL"));
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§eExample: google.com ,youtube.com"));
                            return;
                        }
                        catch (IOException ex8) {
                            break Label_8059;
                        }
                        catch (URISyntaxException ex9) {
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cThe URL §7\"§4" + split11[1] + "§7\" §cis not an valid URL"));
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§eExample: google.com ,youtube.com"));
                            return;
                        }
                    }
                    this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§8(§7browse §8<§aurl§8>"));
                    return;
                }
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring20 = messageIn.substring(1);
            substring20.split(" ");
            if (substring20.startsWith("hp")) {
                if (this.Ý.ÇŽÉ()) {
                    Utiils.HorizonCode_Horizon_È(1);
                }
                else {
                    Utiils.HorizonCode_Horizon_È();
                }
                this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7You damaged your self!"));
                return;
            }
        }
        if (messageIn.startsWith("(")) {
            final String substring21 = messageIn.substring(1);
            final String[] split12 = substring21.split(" ");
            if (substring21.startsWith("bind")) {
                if (split12.length == 1) {
                    this.Ý.á.HorizonCode_Horizon_È(new ChatComponentText("§e§m§l=======§r §6Horizon KeyBinding §e§m§l======="));
                    this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7bind reset §8<§amodule§8>"));
                    this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7bind §8<§amodule§8> §8<§abind§8>"));
                    this.Ý.á.HorizonCode_Horizon_È(new ChatComponentText("§e§m§l=======§r §6Horizon KeyBinding §e§m§l======="));
                }
                else if (split12.length == 2) {
                    this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7bind"));
                }
                else if (split12.length == 3) {
                    if (split12[1].equalsIgnoreCase("reset")) {
                        final ModuleManager áˆºÏ4 = Horizon.à¢.áˆºÏ;
                        for (final Mod mod : ModuleManager.HorizonCode_Horizon_È) {
                            if (mod.ÂµÈ().HorizonCode_Horizon_È().equalsIgnoreCase(split12[2])) {
                                Horizon.à¢.Ø­Âµ.HorizonCode_Horizon_È(mod);
                                this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Resetted the keybind from module §8\"§a" + mod.ÂµÈ().HorizonCode_Horizon_È() + "§8\"§7!"));
                                Horizon.à¢.áˆºÏ.Ý();
                                return;
                            }
                        }
                        this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Module §8\"§a" + split12[2] + "§8\" §7not found."));
                        return;
                    }
                    final ModuleManager áˆºÏ5 = Horizon.à¢.áˆºÏ;
                    for (final Mod mod2 : ModuleManager.HorizonCode_Horizon_È) {
                        final String s10 = split12[1];
                        final String horizonCode_Horizon_È = mod2.ÂµÈ().HorizonCode_Horizon_È();
                        final String s11 = split12[2];
                        if (horizonCode_Horizon_È.equalsIgnoreCase(s10)) {
                            if (Horizon.à¢.Ø­Âµ.HorizonCode_Horizon_È(s11) == 0 || horizonCode_Horizon_È.equalsIgnoreCase("Gui")) {
                                this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§cKeybind not found."));
                                return;
                            }
                            Horizon.à¢.Ø­Âµ.HorizonCode_Horizon_È(mod2, Horizon.à¢.Ø­Âµ.HorizonCode_Horizon_È(s11));
                            this.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + "§7Bound module §8\"§a" + horizonCode_Horizon_È + "§8\" §7to key " + s11));
                            Horizon.à¢.áˆºÏ.Ý();
                        }
                    }
                }
            }
        }
        if (messageIn.equalsIgnoreCase("(help")) {
            this.Ý.á.HorizonCode_Horizon_È(new ChatComponentText("§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§r §6Horizon Help §8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l="));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7bind §a- §7shows you the bind menu"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7browse §8<§7url§8> §a- §7opens a website"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7hp §8<§7hp§8> §a- §7damage your self"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7friend §a- §7opens the friend help menu"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7color §a- §7change color of prefixes etc."));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7killaura §a- §7opens the killaura options help menu"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7speed §8<§7speed§8> §a- §7change speed (default 2.6)"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7step §8<§7height§8> §a- §7changes the step height (default 1.5)"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7extend §8<§7value§8> §a- §7changes the hitbox extend (default 0.35)"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7copy §a- §7opens the copy help menu"));
            this.HorizonCode_Horizon_È(new ChatComponentText("§8(§7screenshot §a- §7takes a screenshot and uploads it to imgur"));
            this.Ý.á.HorizonCode_Horizon_È(new ChatComponentText("§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§r §6Horizon Help §8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l=§8§m§l=§7§m§l="));
        }
        if (messageIn.startsWith("(")) {
            return;
        }
        final ModuleManager áˆºÏ6 = Horizon.à¢.áˆºÏ;
        if (ModuleManager.HorizonCode_Horizon_È(ReverseSpeak.class).áˆºÑ¢Õ()) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C01PacketChatMessage(new StringBuffer(messageIn).reverse().toString()));
        }
        else {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C01PacketChatMessage(messageIn));
        }
    }
    
    @Override
    public void b_() {
        super.b_();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0APacketAnimation());
    }
    
    @Override
    public void µà() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C16PacketClientStatus(C16PacketClientStatus.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
    }
    
    @Override
    protected void Â(final DamageSource p_180431_1_, final float n) {
        if (!this.HorizonCode_Horizon_È(p_180431_1_)) {
            this.áˆºÑ¢Õ(this.Ï­Ä() - n);
        }
    }
    
    public void ˆà() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0DPacketCloseWindow(this.Ï­Ï.Ø­áŒŠá));
        this.¥Æ();
    }
    
    public void ¥Æ() {
        this.Ø­Ñ¢Ï­Ø­áˆº.Â((ItemStack)null);
        super.ˆà();
        this.Ý.HorizonCode_Horizon_È((GuiScreen)null);
    }
    
    public void b_(final float n) {
        if (this.áˆºá) {
            final float áˆºà = this.Ï­Ä() - n;
            if (áˆºà <= 0.0f) {
                this.áˆºÑ¢Õ(n);
                if (áˆºà < 0.0f) {
                    this.ˆÉ = this.ˆà¢ / 2;
                }
            }
            else {
                this.áˆºà = áˆºà;
                this.áˆºÑ¢Õ(this.Ï­Ä());
                this.ˆÉ = this.ˆà¢;
                this.Â(DamageSource.ÂµÈ, áˆºà);
                final int n2 = 10;
                this.ÇŽá = n2;
                this.µà = n2;
            }
        }
        else {
            this.áˆºÑ¢Õ(n);
            this.áˆºá = true;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StatBase p_71064_1_, final int p_71064_2_) {
        if (p_71064_1_ != null && p_71064_1_.Ø) {
            super.HorizonCode_Horizon_È(p_71064_1_, p_71064_2_);
        }
    }
    
    @Override
    public void Ø­à() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C13PacketPlayerAbilities(this.áˆºáˆºáŠ));
    }
    
    @Override
    public boolean µÕ() {
        return true;
    }
    
    protected void Æ() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this, C0BPacketEntityAction.HorizonCode_Horizon_È.Ó, (int)(this.ŒÏ() * 100.0f)));
    }
    
    public void Šáƒ() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this, C0BPacketEntityAction.HorizonCode_Horizon_È.à));
    }
    
    public void Ý(final String ï­Ó) {
        this.Ï­Ó = ï­Ó;
    }
    
    public String Ï­Ðƒà() {
        return this.Ï­Ó;
    }
    
    public StatFileWriter c_() {
        return this.µÐƒÓ;
    }
    
    @Override
    public void Â(final IChatComponent p_146227_1_) {
        this.Ý.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(p_146227_1_);
    }
    
    @Override
    protected boolean Â(final double x, final double y, final double z) {
        if (this.ÇªÓ) {
            return false;
        }
        final BlockPos blockPos = new BlockPos(x, y, z);
        final double n = x - blockPos.HorizonCode_Horizon_È();
        final double n2 = z - blockPos.Ý();
        if (!this.Ø­áŒŠá(blockPos)) {
            int n3 = -1;
            double n4 = 9999.0;
            if (this.Ø­áŒŠá(blockPos.Ø()) && n < n4) {
                n4 = n;
                n3 = 0;
            }
            if (this.Ø­áŒŠá(blockPos.áŒŠÆ()) && 1.0 - n < n4) {
                n4 = 1.0 - n;
                n3 = 1;
            }
            if (this.Ø­áŒŠá(blockPos.Ó()) && n2 < n4) {
                n4 = n2;
                n3 = 4;
            }
            if (this.Ø­áŒŠá(blockPos.à()) && 1.0 - n2 < n4) {
                n3 = 5;
            }
            final float n5 = 0.1f;
            if (n3 == 0) {
                this.ÇŽÉ = -n5;
            }
            if (n3 == 1) {
                this.ÇŽÉ = n5;
            }
            if (n3 == 4) {
                this.ÇŽÕ = -n5;
            }
            if (n3 == 5) {
                this.ÇŽÕ = n5;
            }
        }
        return false;
    }
    
    private boolean Ø­áŒŠá(final BlockPos pos) {
        return !this.Ï­Ðƒà.Â(pos).Ý().Ø() && !this.Ï­Ðƒà.Â(pos.Ø­áŒŠá()).Ý().Ø();
    }
    
    @Override
    public void Â(final boolean sprinting) {
        super.Â(sprinting);
        this.à = (sprinting ? 600 : 0);
    }
    
    public void HorizonCode_Horizon_È(final float œó, final int çžø, final int áœšé) {
        this.ŒÓ = œó;
        this.ÇŽØ = çžø;
        this.áŒŠÉ = áœšé;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent p_146227_1_) {
        this.Ý.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(p_146227_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int n, final String s) {
        return n <= 0;
    }
    
    @Override
    public BlockPos £á() {
        return new BlockPos(this.ŒÏ + 0.5, this.Çªà¢ + 0.5, this.Ê + 0.5);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String soundName, final float volume, final float pitch) {
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, soundName, volume, pitch, false);
    }
    
    @Override
    public boolean ŠÄ() {
        return true;
    }
    
    public boolean Ñ¢á() {
        return this.Æ != null && this.Æ instanceof EntityHorse && ((EntityHorse)this.Æ).ŠÂµÏ();
    }
    
    public float ŒÏ() {
        return this.Ñ¢È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntitySign p_i1097_1_) {
        this.Ý.HorizonCode_Horizon_È(new GuiEditSign(p_i1097_1_));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final CommandBlockLogic p_i45032_1_) {
        this.Ý.HorizonCode_Horizon_È(new GuiCommandBlock(p_i45032_1_));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack p_i1080_2_) {
        if (p_i1080_2_.HorizonCode_Horizon_È() == Items.ŒÓ) {
            this.Ý.HorizonCode_Horizon_È(new GuiScreenBook(this, p_i1080_2_, true));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInventory p_i45503_2_) {
        final String s = (p_i45503_2_ instanceof IInteractionObject) ? ((IInteractionObject)p_i45503_2_).Ø­áŒŠá() : "minecraft:container";
        if ("minecraft:chest".equals(s)) {
            this.Ý.HorizonCode_Horizon_È(new GuiChest(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45503_2_));
        }
        else if ("minecraft:hopper".equals(s)) {
            this.Ý.HorizonCode_Horizon_È(new GuiHopper(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45503_2_));
        }
        else if ("minecraft:furnace".equals(s)) {
            this.Ý.HorizonCode_Horizon_È(new GuiFurnace(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45503_2_));
        }
        else if ("minecraft:brewing_stand".equals(s)) {
            this.Ý.HorizonCode_Horizon_È(new GuiBrewingStand(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45503_2_));
        }
        else if ("minecraft:beacon".equals(s)) {
            this.Ý.HorizonCode_Horizon_È(new GuiBeacon(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45503_2_));
        }
        else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
            this.Ý.HorizonCode_Horizon_È(new GuiChest(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45503_2_));
        }
        else {
            this.Ý.HorizonCode_Horizon_È(new GuiDispenser(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45503_2_));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityHorse p_i1093_3_, final IInventory p_i1093_2_) {
        this.Ý.HorizonCode_Horizon_È(new GuiScreenHorseInventory(this.Ø­Ñ¢Ï­Ø­áˆº, p_i1093_2_, p_i1093_3_));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInteractionObject p_i45502_3_) {
        final String ø­áŒŠá = p_i45502_3_.Ø­áŒŠá();
        if ("minecraft:crafting_table".equals(ø­áŒŠá)) {
            this.Ý.HorizonCode_Horizon_È(new GuiCrafting(this.Ø­Ñ¢Ï­Ø­áˆº, this.Ï­Ðƒà));
        }
        else if ("minecraft:enchanting_table".equals(ø­áŒŠá)) {
            this.Ý.HorizonCode_Horizon_È(new GuiEnchantment(this.Ø­Ñ¢Ï­Ø­áˆº, this.Ï­Ðƒà, p_i45502_3_));
        }
        else if ("minecraft:anvil".equals(ø­áŒŠá)) {
            this.Ý.HorizonCode_Horizon_È(new GuiRepair(this.Ø­Ñ¢Ï­Ø­áˆº, this.Ï­Ðƒà));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IMerchant p_i45500_2_) {
        this.Ý.HorizonCode_Horizon_È(new GuiMerchant(this.Ø­Ñ¢Ï­Ø­áˆº, p_i45500_2_, this.Ï­Ðƒà));
    }
    
    @Override
    public void Â(final Entity p_178926_1_) {
        this.Ý.Å.HorizonCode_Horizon_È(p_178926_1_, EnumParticleTypes.áˆºÑ¢Õ);
    }
    
    @Override
    public void Ý(final Entity p_178926_1_) {
        this.Ý.Å.HorizonCode_Horizon_È(p_178926_1_, EnumParticleTypes.ÂµÈ);
    }
    
    @Override
    public boolean Çªà¢() {
        return this.Â != null && this.Â.Ø­áŒŠá && !this.ÇŽÈ;
    }
    
    public void Ê() {
        super.Ê();
        if (this.ÇŽÉ()) {
            this.£áƒ = this.Â.HorizonCode_Horizon_È;
            this.Ï­áˆºÓ = this.Â.Â;
            this.ÐƒÂ = this.Â.Ý;
            this.áˆºÑ¢Õ = this.Ø;
            this.ÂµÈ = this.áŒŠÆ;
            this.áŒŠÆ += (float)((this.áƒ - this.áŒŠÆ) * 0.5);
            this.Ø += (float)((this.É - this.Ø) * 0.5);
        }
    }
    
    protected boolean ÇŽÉ() {
        return this.Ý.ÇŽá€() == this;
    }
    
    @Override
    public void ˆÏ­() {
        if (this.à > 0) {
            --this.à;
            if (this.à == 0) {
                this.Â(false);
            }
        }
        if (this.Ó > 0) {
            --this.Ó;
        }
        this.ˆÏ­ = this.á;
        if (this.ˆÓ) {
            if (this.Ý.¥Æ != null && !this.Ý.¥Æ.Ø­áŒŠá()) {
                this.Ý.HorizonCode_Horizon_È((GuiScreen)null);
            }
            if (this.á == 0.0f) {
                this.Ý.£ÂµÄ().HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("portal.trigger"), this.ˆáƒ.nextFloat() * 0.4f + 0.8f));
            }
            this.á += 0.0125f;
            if (this.á >= 1.0f) {
                this.á = 1.0f;
            }
            this.ˆÓ = false;
        }
        else if (this.HorizonCode_Horizon_È(Potion.ÂµÈ) && this.Â(Potion.ÂµÈ).Â() > 60) {
            this.á += 0.006666667f;
            if (this.á > 1.0f) {
                this.á = 1.0f;
            }
        }
        else {
            if (this.á > 0.0f) {
                this.á -= 0.05f;
            }
            if (this.á < 0.0f) {
                this.á = 0.0f;
            }
        }
        if (this.áŒŠáŠ > 0) {
            --this.áŒŠáŠ;
        }
        final boolean ý = this.Â.Ý;
        final boolean ø­áŒŠá = this.Â.Ø­áŒŠá;
        final float n = 0.8f;
        final boolean b = this.Â.Â >= n;
        this.Â.HorizonCode_Horizon_È();
        if (this.Ñ¢Ó() && !this.áˆºÇŽØ()) {
            final MovementInput â = this.Â;
            final float horizonCode_Horizon_È = â.HorizonCode_Horizon_È;
            final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
            â.HorizonCode_Horizon_È = horizonCode_Horizon_È * (ModuleManager.HorizonCode_Horizon_È(NoSlow.class).áˆºÑ¢Õ() ? 1.8f : 0.2f);
            final MovementInput â2 = this.Â;
            final float â3 = â2.Â;
            final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
            â2.Â = â3 * (ModuleManager.HorizonCode_Horizon_È(NoSlow.class).áˆºÑ¢Õ() ? 1.8f : 0.2f);
            this.Ó = 0;
        }
        this.Â(this.ŒÏ - this.áŒŠ * 0.35, this.£É().Â + 0.5, this.Ê + this.áŒŠ * 0.35);
        this.Â(this.ŒÏ - this.áŒŠ * 0.35, this.£É().Â + 0.5, this.Ê - this.áŒŠ * 0.35);
        this.Â(this.ŒÏ + this.áŒŠ * 0.35, this.£É().Â + 0.5, this.Ê - this.áŒŠ * 0.35);
        this.Â(this.ŒÏ + this.áŒŠ * 0.35, this.£É().Â + 0.5, this.Ê + this.áŒŠ * 0.35);
        final boolean b2 = this.ŠÏ­áˆºá().HorizonCode_Horizon_È() > 6.0f || this.áˆºáˆºáŠ.Ý;
        if (this.ŠÂµà && !ø­áŒŠá && !b && this.Â.Â >= n && !this.ÇªÂµÕ() && b2 && !this.Ñ¢Ó() && !this.HorizonCode_Horizon_È(Potion.µà)) {
            if (this.Ó <= 0 && !this.Ý.ŠÄ.Œáƒ.Ø­áŒŠá()) {
                this.Ó = 7;
            }
            else {
                this.Â(true);
            }
        }
        if (!this.ÇªÂµÕ() && this.Â.Â >= n && b2 && !this.Ñ¢Ó() && !this.HorizonCode_Horizon_È(Potion.µà) && this.Ý.ŠÄ.Œáƒ.Ø­áŒŠá()) {
            this.Â(true);
        }
        if (this.ÇªÂµÕ() && (this.Â.Â < n || this.¥à || !b2)) {
            this.Â(false);
        }
        final ModuleManager áˆºÏ3 = Horizon.à¢.áˆºÏ;
        if ((!ModuleManager.HorizonCode_Horizon_È(Sprint.class).áˆºÑ¢Õ() || (!this.Ý.ŠÄ.ÇªÉ.HorizonCode_Horizon_È && !this.Ý.ŠÄ.ÇŽà.HorizonCode_Horizon_È && !this.Ý.ŠÄ.ŠÏ­áˆºá.HorizonCode_Horizon_È && !this.Ý.ŠÄ.ŠáˆºÂ.HorizonCode_Horizon_È)) && this.ÇªÂµÕ() && (this.Â.Â < n || this.¥à || !b2)) {
            this.Â(false);
        }
        if (this.áˆºáˆºáŠ.Ý) {
            if (this.Ý.Âµá€.ÂµÈ()) {
                if (!this.áˆºáˆºáŠ.Â) {
                    this.áˆºáˆºáŠ.Â = true;
                    this.Ø­à();
                }
            }
            else if (!ý && this.Â.Ý) {
                if (this.ˆÐƒØ == 0) {
                    this.ˆÐƒØ = 7;
                }
                else {
                    this.áˆºáˆºáŠ.Â = !this.áˆºáˆºáŠ.Â;
                    this.Ø­à();
                    this.ˆÐƒØ = 0;
                }
            }
        }
        if (this.áˆºáˆºáŠ.Â && this.ÇŽÉ()) {
            if (this.Â.Ø­áŒŠá) {
                this.ˆá -= this.áˆºáˆºáŠ.HorizonCode_Horizon_È() * 3.0f;
            }
            if (this.Â.Ý) {
                this.ˆá += this.áˆºáˆºáŠ.HorizonCode_Horizon_È() * 3.0f;
            }
        }
        if (this.Ñ¢á()) {
            if (this.ŠáŒŠà¢ < 0) {
                ++this.ŠáŒŠà¢;
                if (this.ŠáŒŠà¢ == 0) {
                    this.Ñ¢È = 0.0f;
                }
            }
            if (ý && !this.Â.Ý) {
                this.ŠáŒŠà¢ = -10;
                this.Æ();
            }
            else if (!ý && this.Â.Ý) {
                this.ŠáŒŠà¢ = 0;
                this.Ñ¢È = 0.0f;
            }
            else if (ý) {
                ++this.ŠáŒŠà¢;
                if (this.ŠáŒŠà¢ < 10) {
                    this.Ñ¢È = this.ŠáŒŠà¢ * 0.1f;
                }
                else {
                    this.Ñ¢È = 0.8f + 2.0f / (this.ŠáŒŠà¢ - 9) * 0.1f;
                }
            }
        }
        else {
            this.Ñ¢È = 0.0f;
        }
        super.ˆÏ­();
        if (this.ŠÂµà && this.áˆºáˆºáŠ.Â && !this.Ý.Âµá€.ÂµÈ()) {
            this.áˆºáˆºáŠ.Â = false;
            this.Ø­à();
        }
    }
    
    public final String Ø­áŒŠá(final String s) throws Exception {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("https://api.imgur.com/3/image").openConnection();
        final BufferedImage read = ImageIO.read(new File(s));
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(read, "png", byteArrayOutputStream);
        final String string = String.valueOf(URLEncoder.encode("image", "UTF-8")) + "=" + URLEncoder.encode(Base64.encodeBase64String(byteArrayOutputStream.toByteArray()), "UTF-8");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Client-ID " + "f9e1d0e7e40d13f");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.connect();
        final StringBuilder sb = new StringBuilder();
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
        outputStreamWriter.write(string);
        outputStreamWriter.flush();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }
        outputStreamWriter.close();
        bufferedReader.close();
        return sb.toString();
    }
    
    public final File Âµá€(final String s) {
        final File[] listFiles = new File(s).listFiles(new FileFilter() {
            @Override
            public boolean accept(final File file) {
                return file.isFile();
            }
        });
        long lastModified = Long.MIN_VALUE;
        File file = null;
        File[] array;
        for (int length = (array = listFiles).length, i = 0; i < length; ++i) {
            final File file2 = array[i];
            if (file2.lastModified() > lastModified) {
                file = file2;
                lastModified = file2.lastModified();
            }
        }
        return file;
    }
    
    public void Ó(final String msg) {
        this.HorizonCode_Horizon_È(new ChatComponentText(msg));
    }
    
    private void ÐƒÇŽà() {
    }
}
