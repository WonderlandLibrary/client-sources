// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.gui.click.elements.KeybindMods;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.gui.click.elements.Dropdown;
import com.krazzzzymonkey.catalyst.gui.click.listener.SliderChangeListener;
import com.krazzzzymonkey.catalyst.gui.click.elements.Slider;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.gui.click.listener.CheckButtonClickListener;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.gui.click.elements.CheckButton;
import com.krazzzzymonkey.catalyst.value.BooleanValue;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.gui.click.listener.ComponentClickListener;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.elements.ExpandingButton;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.gui.click.elements.Frame;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.utils.visual.GLUtils;
import com.krazzzzymonkey.catalyst.gui.click.ClickGui;

public class GuiManager extends ClickGui
{
    private static final /* synthetic */ int[] ll;
    
    private static boolean lIII(final int llllllllIIIIIlI) {
        return llllllllIIIIIlI == 0;
    }
    
    private static boolean lIl(final int llllllllIIIlIll, final int llllllllIIIlIlI) {
        return llllllllIIIlIll < llllllllIIIlIlI;
    }
    
    private static boolean lll(final Object llllllllIIIIlll, final Object llllllllIIIIllI) {
        return llllllllIIIIlll == llllllllIIIIllI;
    }
    
    private static void ll() {
        (ll = new int[8])[0] = (0x32 ^ 0x26);
        GuiManager.ll[1] = ((0x20 ^ 0x1) & ~(0xE ^ 0x2F));
        GuiManager.ll[2] = (0xB9 ^ 0xAE) + (0xC ^ 0x10) - (0x91 ^ 0xBF) + (16 + 97 - 83 + 135);
        GuiManager.ll[3] = (0x94 ^ 0xC7 ^ (0xA2 ^ 0x95));
        GuiManager.ll[4] = " ".length();
        GuiManager.ll[5] = (78 + 121 - 116 + 77 ^ 38 + 123 + 5 + 8);
        GuiManager.ll[6] = (0xCE ^ 0xC6);
        GuiManager.ll[7] = (0x50 ^ 0x5A);
    }
    
    public void Initialization() {
        this.addCategoryPanels();
    }
    
    private static boolean llI(final int llllllllIIIIlII) {
        return llllllllIIIIlII != 0;
    }
    
    static {
        ll();
    }
    
    private void addCategoryPanels() {
        final int llllllllIlIlIIl = GLUtils.getScreenWidth();
        int llllllllIlIlIII = GuiManager.ll[0];
        int llllllllIlIIlll = GuiManager.ll[0];
        final short llllllllIlIIIlI = (Object)ModuleCategory.values();
        final byte llllllllIlIIIIl = (byte)llllllllIlIIIlI.length;
        byte llllllllIlIIIII = (byte)GuiManager.ll[1];
        while (lIl(llllllllIlIIIII, llllllllIlIIIIl)) {
            final ModuleCategory llllllllIlIlIll = llllllllIlIIIlI[llllllllIlIIIII];
            final int llllllllIllIIII = GuiManager.ll[2];
            final int llllllllIlIllll = GuiManager.ll[3];
            int llllllllIlIlllI = GuiManager.ll[1];
            final String llllllllIlIllIl = String.valueOf(new StringBuilder().append(Character.toString(llllllllIlIlIll.toString().toLowerCase().charAt(GuiManager.ll[1])).toUpperCase()).append(llllllllIlIlIll.toString().toLowerCase().substring(GuiManager.ll[4])));
            final Frame llllllllIlIllII = new Frame(llllllllIlIlIII, llllllllIlIIlll, llllllllIlIllll, llllllllIllIIII, llllllllIlIllIl);
            final String llllllllIIllIIl = (String)HackManager.getHacks().iterator();
            while (llI(((Iterator)llllllllIIllIIl).hasNext() ? 1 : 0)) {
                final Modules llllllllIllIIIl = ((Iterator<Modules>)llllllllIIllIIl).next();
                if (lll(llllllllIllIIIl.getCategory(), llllllllIlIlIll)) {
                    final ExpandingButton llllllllIllIIll = new ExpandingButton(GuiManager.ll[1], GuiManager.ll[1], llllllllIlIllll, GuiManager.ll[5], llllllllIlIllII, llllllllIllIIIl.getName(), llllllllIllIIIl) {
                        @Override
                        public void onUpdate() {
                            this.setEnabled(llllllllIllIIIl.isToggled());
                        }
                    };
                    llllllllIllIIll.addListner(new ComponentClickListener() {
                        @Override
                        public void onComponenetClick(final Component llIIIIlIlIlIlll, final int llIIIIlIlIlIllI) {
                            llllllllIllIIIl.toggle();
                        }
                    });
                    llllllllIllIIll.setEnabled(llllllllIllIIIl.isToggled());
                    if (lIII(llllllllIllIIIl.getValues().isEmpty() ? 1 : 0)) {
                        final Iterator<Value> iterator = llllllllIllIIIl.getValues().iterator();
                        while (llI(iterator.hasNext() ? 1 : 0)) {
                            final Value llllllllIllIlII = iterator.next();
                            if (llI((llllllllIllIlII instanceof BooleanValue) ? 1 : 0)) {
                                final BooleanValue llllllllIllllII = (BooleanValue)llllllllIllIlII;
                                final CheckButton llllllllIlllIll = new CheckButton(GuiManager.ll[1], GuiManager.ll[1], llllllllIllIIll.getDimension().width, GuiManager.ll[5], llllllllIllIIll, llllllllIllllII.getName(), llllllllIllllII.getValue(), null);
                                llllllllIlllIll.addListeners(new CheckButtonClickListener() {
                                    @Override
                                    public void onCheckButtonClick(final CheckButton lllllIIIlIlllII) {
                                        final byte lllllIIIlIllIIl = (byte)llllllllIllIIIl.getValues().iterator();
                                        while (llIlIl(((Iterator)lllllIIIlIllIIl).hasNext() ? 1 : 0)) {
                                            final Value lllllIIIlIllllI = ((Iterator<Value>)lllllIIIlIllIIl).next();
                                            if (llIlIl(lllllIIIlIllllI.getName().equals(llllllllIllllII.getName()) ? 1 : 0)) {
                                                lllllIIIlIllllI.setValue(lllllIIIlIlllII.isEnabled());
                                            }
                                            "".length();
                                            if (null != null) {
                                                return;
                                            }
                                        }
                                    }
                                    
                                    private static boolean llIlIl(final int lllllIIIlIlIllI) {
                                        return lllllIIIlIlIllI != 0;
                                    }
                                });
                                llllllllIllIIll.addComponent(llllllllIlllIll);
                                "".length();
                                if (((0x56 ^ 0x51 ^ ((0x53 ^ 0x7) & ~(0x22 ^ 0x76))) & (31 + 131 - 133 + 120 ^ 1 + 138 - 53 + 60 ^ -" ".length())) < 0) {
                                    return;
                                }
                            }
                            else if (llI((llllllllIllIlII instanceof NumberValue) ? 1 : 0)) {
                                final NumberValue llllllllIlllIlI = (NumberValue)llllllllIllIlII;
                                final Slider llllllllIlllIIl = new Slider(llllllllIlllIlI.getMin(), llllllllIlllIlI.getMax(), llllllllIlllIlI.getValue(), llllllllIllIIll, llllllllIlllIlI.getName());
                                llllllllIlllIIl.addListener(new SliderChangeListener() {
                                    @Override
                                    public void onSliderChange(final Slider llIlIIIIIIlIllI) {
                                        final Exception llIlIIIIIIlIlIl = (Exception)llllllllIllIIIl.getValues().iterator();
                                        while (llIlIIlIl(((Iterator)llIlIIIIIIlIlIl).hasNext() ? 1 : 0)) {
                                            final Value llIlIIIIIIllIlI = ((Iterator<Value>)llIlIIIIIIlIlIl).next();
                                            if (llIlIIlIl(llIlIIIIIIllIlI.getName().equals(llllllllIllIlII.getName()) ? 1 : 0)) {
                                                llIlIIIIIIllIlI.setValue(llIlIIIIIIlIllI.getValue());
                                            }
                                            "".length();
                                            if (((0xD3 ^ 0x98 ^ (0xC7 ^ 0xA5)) & (0x3F ^ 0x51 ^ (0x56 ^ 0x11) ^ -" ".length())) == "  ".length()) {
                                                return;
                                            }
                                        }
                                    }
                                    
                                    private static boolean llIlIIlIl(final int llIlIIIIIIlIIlI) {
                                        return llIlIIIIIIlIIlI != 0;
                                    }
                                });
                                llllllllIllIIll.addComponent(llllllllIlllIIl);
                                "".length();
                                if (null != null) {
                                    return;
                                }
                            }
                            else if (llI((llllllllIllIlII instanceof ModeValue) ? 1 : 0)) {
                                final Dropdown llllllllIllIllI = new Dropdown(GuiManager.ll[1], GuiManager.ll[1], llllllllIlIllll, GuiManager.ll[5], llllllllIlIllII, llllllllIllIlII.getName());
                                final ModeValue llllllllIllIlIl = (ModeValue)llllllllIllIlII;
                                final boolean llllllllIIlIIlI = (Object)llllllllIllIlIl.getModes();
                                final int llllllllIIlIIIl = llllllllIIlIIlI.length;
                                String llllllllIIlIIII = (String)GuiManager.ll[1];
                                while (lIl((int)llllllllIIlIIII, llllllllIIlIIIl)) {
                                    final Mode llllllllIllIlll = llllllllIIlIIlI[llllllllIIlIIII];
                                    final CheckButton llllllllIlllIII = new CheckButton(GuiManager.ll[1], GuiManager.ll[1], llllllllIllIIll.getDimension().width, GuiManager.ll[5], llllllllIllIIll, llllllllIllIlll.getName(), llllllllIllIlll.isToggled(), llllllllIllIlIl);
                                    llllllllIlllIII.addListeners(new CheckButtonClickListener() {
                                        private static final /* synthetic */ int[] lllIIl;
                                        
                                        @Override
                                        public void onCheckButtonClick(final CheckButton lllIIlIIlIIIlIl) {
                                            final Exception lllIIlIIlIIIIlI = (Object)llllllllIllIlIl.getModes();
                                            final String lllIIlIIlIIIIIl = (String)lllIIlIIlIIIIlI.length;
                                            long lllIIlIIlIIIIII = GuiManager$5.lllIIl[0];
                                            while (llIIllll((int)lllIIlIIlIIIIII, (int)lllIIlIIlIIIIIl)) {
                                                final Mode lllIIlIIlIIIlll = lllIIlIIlIIIIlI[lllIIlIIlIIIIII];
                                                if (llIlIIII(lllIIlIIlIIIlll.getName().equals(llllllllIllIlll.getName()) ? 1 : 0)) {
                                                    lllIIlIIlIIIlll.setToggled(lllIIlIIlIIIlIl.isEnabled());
                                                }
                                                ++lllIIlIIlIIIIII;
                                                "".length();
                                                if (((0xE5 ^ 0xA8) & ~(0xD4 ^ 0x99)) != ((0xB0 ^ 0x95) & ~(0x4C ^ 0x69))) {
                                                    return;
                                                }
                                            }
                                        }
                                        
                                        private static void llIIlllI() {
                                            (lllIIl = new int[1])[0] = ((0x27 ^ 0x1D) & ~(0x37 ^ 0xD));
                                        }
                                        
                                        private static boolean llIIllll(final int lllIIlIIIllllII, final int lllIIlIIIlllIll) {
                                            return lllIIlIIIllllII < lllIIlIIIlllIll;
                                        }
                                        
                                        static {
                                            llIIlllI();
                                        }
                                        
                                        private static boolean llIlIIII(final int lllIIlIIIlllIIl) {
                                            return lllIIlIIIlllIIl != 0;
                                        }
                                    });
                                    llllllllIllIllI.addComponent(llllllllIlllIII);
                                    ++llllllllIIlIIII;
                                    "".length();
                                    if ("  ".length() <= ((0x19 ^ 0x12 ^ (0x47 ^ 0x5F)) & (111 + 118 - 102 + 4 ^ 130 + 37 - 113 + 90 ^ -" ".length()))) {
                                        return;
                                    }
                                }
                                llllllllIllIIll.addComponent(llllllllIllIllI);
                            }
                            "".length();
                            if (((0x0 ^ 0x4B) & ~(0xD5 ^ 0x9E)) != 0x0) {
                                return;
                            }
                        }
                    }
                    final KeybindMods llllllllIllIIlI = new KeybindMods(GuiManager.ll[1], GuiManager.ll[1], GuiManager.ll[6], GuiManager.ll[5], llllllllIllIIll, llllllllIllIIIl);
                    llllllllIllIIll.addComponent(llllllllIllIIlI);
                    llllllllIlIllII.addComponent(llllllllIllIIll);
                    ++llllllllIlIlllI;
                }
                "".length();
                if ("   ".length() <= 0) {
                    return;
                }
            }
            if (lIl(llllllllIlIlIII + llllllllIlIllll + GuiManager.ll[7], llllllllIlIlIIl)) {
                llllllllIlIlIII += llllllllIlIllll + GuiManager.ll[7];
                "".length();
                if (((141 + 89 - 162 + 121 ^ 96 + 141 - 228 + 135) & (0x5C ^ 0x1F ^ (0x1 ^ 0x6F) ^ -" ".length())) != 0x0) {
                    return;
                }
            }
            else {
                llllllllIlIlIII = GuiManager.ll[0];
                llllllllIlIIlll += 60;
            }
            llllllllIlIllII.setMaximizible((boolean)(GuiManager.ll[4] != 0));
            llllllllIlIllII.setPinnable((boolean)(GuiManager.ll[4] != 0));
            this.addFrame(llllllllIlIllII);
            ++llllllllIlIIIII;
            "".length();
            if (" ".length() != " ".length()) {
                return;
            }
        }
    }
}
