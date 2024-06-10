using System;

// Token: 0x02000048 RID: 72
internal struct 3E043A9C
{
	// Token: 0x0600025F RID: 607 RVA: 0x002FFB6C File Offset: 0x0017FF6C
	public void 23B12C49()
	{
		this.60F4145D = 1024U;
	}

	// Token: 0x06000260 RID: 608 RVA: 0x002FFB7C File Offset: 0x0017FF7C
	public uint 65AA23FF(75061222 3DCA757B)
	{
		uint num = (3DCA757B.4B380F17 >> 11) * this.60F4145D;
		if (3DCA757B.7A9150D0 < num)
		{
			3DCA757B.4B380F17 = num;
			this.60F4145D += 2048U - this.60F4145D >> 5;
			if (3DCA757B.4B380F17 < 16777216U)
			{
				3DCA757B.7A9150D0 = (3DCA757B.7A9150D0 << 8 | (uint)((byte)3DCA757B.0A4156D8.ReadByte()));
				3DCA757B.4B380F17 <<= 8;
			}
			return 0U;
		}
		3DCA757B.4B380F17 -= num;
		3DCA757B.7A9150D0 -= num;
		this.60F4145D -= this.60F4145D >> 5;
		if (3DCA757B.4B380F17 < 16777216U)
		{
			3DCA757B.7A9150D0 = (3DCA757B.7A9150D0 << 8 | (uint)((byte)3DCA757B.0A4156D8.ReadByte()));
			3DCA757B.4B380F17 <<= 8;
		}
		return 1U;
	}

	// Token: 0x0400018B RID: 395
	private const int 74E2222A = 11;

	// Token: 0x0400018C RID: 396
	private const uint 7B0664C8 = 2048U;

	// Token: 0x0400018D RID: 397
	private const int 472C0AF4 = 5;

	// Token: 0x0400018E RID: 398
	private uint 60F4145D;
}
