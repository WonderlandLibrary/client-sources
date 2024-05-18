package net.minecraft.client.model;

public class ModelBanner extends ModelBase
{
    public ModelRenderer bannerSlate;
    public ModelRenderer bannerStand;
    public ModelRenderer bannerTop;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void renderBanner() {
        this.bannerSlate.rotationPointY = -32.0f;
        this.bannerSlate.render(0.0625f);
        this.bannerStand.render(0.0625f);
        this.bannerTop.render(0.0625f);
    }
    
    public ModelBanner() {
        this.textureWidth = (0x5B ^ 0x1B);
        this.textureHeight = (0x64 ^ 0x24);
        (this.bannerSlate = new ModelRenderer(this, "".length(), "".length())).addBox(-10.0f, 0.0f, -2.0f, 0x14 ^ 0x0, 0xF ^ 0x27, " ".length(), 0.0f);
        (this.bannerStand = new ModelRenderer(this, 0x2C ^ 0x0, "".length())).addBox(-1.0f, -30.0f, -1.0f, "  ".length(), 0x3C ^ 0x16, "  ".length(), 0.0f);
        (this.bannerTop = new ModelRenderer(this, "".length(), 0x0 ^ 0x2A)).addBox(-10.0f, -32.0f, -1.0f, 0xB2 ^ 0xA6, "  ".length(), "  ".length(), 0.0f);
    }
}
