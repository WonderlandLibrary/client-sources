using System;

// Token: 0x02000049 RID: 73
internal struct 4D960C7C
{
	// Token: 0x06000261 RID: 609 RVA: 0x002FFC70 File Offset: 0x00180070
	public 4D960C7C(int 1AB62466)
	{
		this.04CB1261 = 1AB62466;
		this.65F67341 = new 3E043A9C[1 << 1AB62466];
	}

	// Token: 0x06000262 RID: 610 RVA: 0x002FFC8C File Offset: 0x0018008C
	public void 21437AF6()
	{
		uint num = 1U;
		while ((ulong)num < (ulong)(1L << (this.04CB1261 & 31)))
		{
			this.65F67341[(int)num].23B12C49();
			num += 1U;
		}
	}

	// Token: 0x06000263 RID: 611 RVA: 0x002FFCC8 File Offset: 0x001800C8
	public uint 40786810(75061222 69514A89)
	{
		uint num = 1U;
		for (int i = this.04CB1261; i > 0; i--)
		{
			num = (num << 1) + this.65F67341[(int)num].65AA23FF(69514A89);
		}
		return num - (1U << this.04CB1261);
	}

	// Token: 0x06000264 RID: 612 RVA: 0x002FFD14 File Offset: 0x00180114
	public uint 791C1DE9(75061222 55242FFD)
	{
		uint num = 1U;
		uint num2 = 0U;
		for (int i = 0; i < this.04CB1261; i++)
		{
			uint num3 = this.65F67341[(int)num].65AA23FF(55242FFD);
			num <<= 1;
			num += num3;
			num2 |= num3 << i;
		}
		return num2;
	}

	// Token: 0x06000265 RID: 613 RVA: 0x002FFD64 File Offset: 0x00180164
	public static uint 1961544E(3E043A9C[] 5F29436F, uint 6E711E60, 75061222 125F26E8, int 63EE186B)
	{
		uint num = 1U;
		uint num2 = 0U;
		for (int i = 0; i < 63EE186B; i++)
		{
			uint num3 = 5F29436F[(int)(6E711E60 + num)].65AA23FF(125F26E8);
			num <<= 1;
			num += num3;
			num2 |= num3 << i;
		}
		return num2;
	}

	// Token: 0x0400018F RID: 399
	private readonly 3E043A9C[] 65F67341;

	// Token: 0x04000190 RID: 400
	private readonly int 04CB1261;
}
