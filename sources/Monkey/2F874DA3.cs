using System;

// Token: 0x0200003F RID: 63
public class 2F874DA3
{
	// Token: 0x060001CE RID: 462 RVA: 0x002F0D40 File Offset: 0x00171140
	public 2F874DA3()
	{
		this.4E817971 = 504916828U;
	}

	// Token: 0x060001CF RID: 463 RVA: 0x002F0D54 File Offset: 0x00171154
	public uint 64646B54(uint 599F175B)
	{
		uint num = 599F175B ^ this.4E817971;
		this.4E817971 = (6328135E.73D8138B(this.4E817971, 7) ^ num);
		return num;
	}

	// Token: 0x0400014A RID: 330
	private uint 4E817971;
}
