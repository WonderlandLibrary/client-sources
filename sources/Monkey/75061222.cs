using System;
using System.IO;

// Token: 0x02000047 RID: 71
internal class 75061222
{
	// Token: 0x0600025B RID: 603 RVA: 0x002FFA84 File Offset: 0x0017FE84
	public void 33131963(Stream 17567988)
	{
		this.0A4156D8 = 17567988;
		this.7A9150D0 = 0U;
		this.4B380F17 = uint.MaxValue;
		for (int i = 0; i < 5; i++)
		{
			this.7A9150D0 = (this.7A9150D0 << 8 | (uint)((byte)this.0A4156D8.ReadByte()));
		}
	}

	// Token: 0x0600025C RID: 604 RVA: 0x002FFAD4 File Offset: 0x0017FED4
	public void 353536EC()
	{
		this.0A4156D8 = null;
	}

	// Token: 0x0600025D RID: 605 RVA: 0x002FFAE0 File Offset: 0x0017FEE0
	public uint 2DF32143(int 2867735A)
	{
		uint num = this.4B380F17;
		uint num2 = this.7A9150D0;
		uint num3 = 0U;
		for (int i = 2867735A; i > 0; i--)
		{
			num >>= 1;
			uint num4 = num2 - num >> 31;
			num2 -= (num & num4 - 1U);
			num3 = (num3 << 1 | 1U - num4);
			if (num < 16777216U)
			{
				num2 = (num2 << 8 | (uint)((byte)this.0A4156D8.ReadByte()));
				num <<= 8;
			}
		}
		this.4B380F17 = num;
		this.7A9150D0 = num2;
		return num3;
	}

	// Token: 0x04000186 RID: 390
	private uint 440054FE = 1U;

	// Token: 0x04000187 RID: 391
	public const uint 12DC59AA = 16777216U;

	// Token: 0x04000188 RID: 392
	public uint 4B380F17;

	// Token: 0x04000189 RID: 393
	public uint 7A9150D0;

	// Token: 0x0400018A RID: 394
	public Stream 0A4156D8;
}
