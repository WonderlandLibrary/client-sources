using System;
using System.IO;

// Token: 0x02000046 RID: 70
public class 788371D4
{
	// Token: 0x06000254 RID: 596 RVA: 0x002FF374 File Offset: 0x0017F774
	public 788371D4()
	{
		this.6D633408 = uint.MaxValue;
		int num = 0;
		while ((long)num < 4L)
		{
			this.2B087737[num] = new 4D960C7C(6);
			num++;
		}
	}

	// Token: 0x06000255 RID: 597 RVA: 0x002FF46C File Offset: 0x0017F86C
	private void 632972C2(uint 7EE72F28)
	{
		if (this.6D633408 != 7EE72F28)
		{
			this.6D633408 = 7EE72F28;
			this.4E0C6194 = Math.Max(this.6D633408, 1U);
			uint 3F2F772C = Math.Max(this.4E0C6194, 4096U);
			this.153F2530.1AE52179(3F2F772C);
		}
	}

	// Token: 0x06000256 RID: 598 RVA: 0x002FF4BC File Offset: 0x0017F8BC
	private void 66DF78BD(int 4E8E07D0, int 5DDB3DB1)
	{
		if (4E8E07D0 > 8)
		{
			throw new ArgumentException("lp > 8");
		}
		if (5DDB3DB1 > 8)
		{
			throw new ArgumentException("lc > 8");
		}
		this.7A0C1522.64770E1F(4E8E07D0, 5DDB3DB1);
	}

	// Token: 0x06000257 RID: 599 RVA: 0x002FF4F0 File Offset: 0x0017F8F0
	private void 33B43F8C(int 01FB2CAA)
	{
		if (01FB2CAA > 4)
		{
			throw new ArgumentException("pb > Base.KNumPosStatesBitsMax");
		}
		uint num = 1U << 01FB2CAA;
		this.0E3B1B5A.3FBA1723(num);
		this.2529752A.3FBA1723(num);
		this.3FF360E7 = num - 1U;
	}

	// Token: 0x06000258 RID: 600 RVA: 0x002FF538 File Offset: 0x0017F938
	private void 7A7D3031(Stream 1D27347D, Stream 44C81EF6)
	{
		this.71A37DE5.33131963(1D27347D);
		this.153F2530.4F787D3B(44C81EF6, false);
		for (uint num = 0U; num < 12U; num += 1U)
		{
			for (uint num2 = 0U; num2 <= this.3FF360E7; num2 += 1U)
			{
				uint num3 = (num << 4) + num2;
				this.10FC4A10[(int)num3].23B12C49();
				this.7E781F99[(int)num3].23B12C49();
			}
			this.39FF021F[(int)num].23B12C49();
			this.65A63C48[(int)num].23B12C49();
			this.55EF0CC6[(int)num].23B12C49();
			this.07917932[(int)num].23B12C49();
		}
		this.7A0C1522.49D17826();
		for (uint num = 0U; num < 4U; num += 1U)
		{
			this.2B087737[(int)num].21437AF6();
		}
		for (uint num = 0U; num < 114U; num += 1U)
		{
			this.566C7FF7[(int)num].23B12C49();
		}
		this.0E3B1B5A.42BE245A();
		this.2529752A.42BE245A();
		this.36861F37.21437AF6();
	}

	// Token: 0x06000259 RID: 601 RVA: 0x002FF668 File Offset: 0x0017FA68
	public void 15AB62ED(Stream 056B707B, Stream 1BAF3DD3, long 71593963)
	{
		this.7A7D3031(056B707B, 1BAF3DD3);
		72CC5C7C.38970249 71593964 = default(72CC5C7C.38970249);
		71593964.77F53917();
		uint num = 0U;
		uint num2 = 0U;
		uint num3 = 0U;
		uint num4 = 0U;
		ulong num5 = 0UL;
		if (num5 < (ulong)71593963)
		{
			if (this.10FC4A10[(int)((int)71593964.0C2C3337 << 4)].65AA23FF(this.71A37DE5) != 0U)
			{
				throw new InvalidDataException("IsMatchDecoders");
			}
			71593964.7FF47A84();
			byte 5123386C = this.7A0C1522.2309489D(this.71A37DE5, 0U, 0);
			this.153F2530.427E7B6A(5123386C);
			num5 += 1UL;
		}
		while (num5 < (ulong)71593963)
		{
			uint num6 = (uint)num5 & this.3FF360E7;
			if (this.10FC4A10[(int)((71593964.0C2C3337 << 4) + num6)].65AA23FF(this.71A37DE5) == 0U)
			{
				byte b = this.153F2530.280B366B(0U);
				byte 5123386C2;
				if (!71593964.077A578B())
				{
					5123386C2 = this.7A0C1522.561B44DE(this.71A37DE5, (uint)num5, b, this.153F2530.280B366B(num));
				}
				else
				{
					5123386C2 = this.7A0C1522.2309489D(this.71A37DE5, (uint)num5, b);
				}
				this.153F2530.427E7B6A(5123386C2);
				71593964.7FF47A84();
				num5 += 1UL;
			}
			else
			{
				uint num8;
				if (this.39FF021F[(int)71593964.0C2C3337].65AA23FF(this.71A37DE5) == 1U)
				{
					if (this.65A63C48[(int)71593964.0C2C3337].65AA23FF(this.71A37DE5) == 0U)
					{
						if (this.7E781F99[(int)((71593964.0C2C3337 << 4) + num6)].65AA23FF(this.71A37DE5) == 0U)
						{
							71593964.39730959();
							this.153F2530.427E7B6A(this.153F2530.280B366B(num));
							num5 += 1UL;
							continue;
						}
					}
					else
					{
						uint num7;
						if (this.55EF0CC6[(int)71593964.0C2C3337].65AA23FF(this.71A37DE5) == 0U)
						{
							num7 = num2;
						}
						else
						{
							if (this.07917932[(int)71593964.0C2C3337].65AA23FF(this.71A37DE5) == 0U)
							{
								num7 = num3;
							}
							else
							{
								num7 = num4;
								num4 = num3;
							}
							num3 = num2;
						}
						num2 = num;
						num = num7;
					}
					num8 = this.2529752A.246B7EFB(this.71A37DE5, num6) + 2U;
					71593964.010C506E();
				}
				else
				{
					num4 = num3;
					num3 = num2;
					num2 = num;
					num8 = 2U + this.0E3B1B5A.246B7EFB(this.71A37DE5, num6);
					71593964.138C0BBA();
					uint num9 = this.2B087737[(int)72CC5C7C.07440852(num8)].40786810(this.71A37DE5);
					if (num9 >= 4U)
					{
						int num10 = (int)((num9 >> 1) - 1U);
						num = (2U | (num9 & 1U)) << num10;
						if (num9 < 14U)
						{
							num += 4D960C7C.1961544E(this.566C7FF7, num - num9 - 1U, this.71A37DE5, num10);
						}
						else
						{
							num += this.71A37DE5.2DF32143(num10 - 4) << 4;
							num += this.36861F37.791C1DE9(this.71A37DE5);
						}
					}
					else
					{
						num = num9;
					}
				}
				if ((ulong)num >= (ulong)this.153F2530.70AF2E6E + num5 || num >= this.4E0C6194)
				{
					if (num != 4294967295U)
					{
						throw new InvalidDataException("rep0");
					}
					break;
				}
				else
				{
					this.153F2530.6C742E61(num, num8);
					num5 += (ulong)num8;
				}
			}
		}
		this.153F2530.432C1D1E();
		this.153F2530.15DF4A99();
		this.71A37DE5.353536EC();
	}

	// Token: 0x0600025A RID: 602 RVA: 0x002FF9FC File Offset: 0x0017FDFC
	public void 0DC77243(byte[] 68237B3D)
	{
		if (68237B3D.Length < 5)
		{
			throw new ArgumentException("properties.Length < 5");
		}
		int 5DDB3DB = (int)(68237B3D[0] % 9);
		byte b = 68237B3D[0] / 9;
		int 4E8E07D = (int)(b % 5);
		int num = (int)(b / 5);
		if (num > 4)
		{
			throw new ArgumentException("pb > Base.kNumPosStatesBitsMax");
		}
		uint num2 = 0U;
		for (int i = 0; i < 4; i++)
		{
			num2 += (uint)((uint)68237B3D[1 + i] << i * 8);
		}
		this.632972C2(num2);
		this.66DF78BD(4E8E07D, 5DDB3DB);
		this.33B43F8C(num);
	}

	// Token: 0x04000174 RID: 372
	private uint 080F62AE = 1U;

	// Token: 0x04000175 RID: 373
	private readonly 17AE5F89 153F2530 = new 17AE5F89();

	// Token: 0x04000176 RID: 374
	private readonly 75061222 71A37DE5 = new 75061222();

	// Token: 0x04000177 RID: 375
	private readonly 3E043A9C[] 10FC4A10 = new 3E043A9C[192];

	// Token: 0x04000178 RID: 376
	private readonly 3E043A9C[] 39FF021F = new 3E043A9C[12];

	// Token: 0x04000179 RID: 377
	private readonly 3E043A9C[] 65A63C48 = new 3E043A9C[12];

	// Token: 0x0400017A RID: 378
	private readonly 3E043A9C[] 55EF0CC6 = new 3E043A9C[12];

	// Token: 0x0400017B RID: 379
	private readonly 3E043A9C[] 07917932 = new 3E043A9C[12];

	// Token: 0x0400017C RID: 380
	private readonly 3E043A9C[] 7E781F99 = new 3E043A9C[192];

	// Token: 0x0400017D RID: 381
	private readonly 4D960C7C[] 2B087737 = new 4D960C7C[4];

	// Token: 0x0400017E RID: 382
	private readonly 3E043A9C[] 566C7FF7 = new 3E043A9C[114];

	// Token: 0x0400017F RID: 383
	private 4D960C7C 36861F37 = new 4D960C7C(4);

	// Token: 0x04000180 RID: 384
	private readonly 788371D4.14FF39E1 0E3B1B5A = new 788371D4.14FF39E1();

	// Token: 0x04000181 RID: 385
	private readonly 788371D4.14FF39E1 2529752A = new 788371D4.14FF39E1();

	// Token: 0x04000182 RID: 386
	private readonly 788371D4.42C8002E 7A0C1522 = new 788371D4.42C8002E();

	// Token: 0x04000183 RID: 387
	private uint 6D633408;

	// Token: 0x04000184 RID: 388
	private uint 4E0C6194;

	// Token: 0x04000185 RID: 389
	private uint 3FF360E7;

	// Token: 0x0200006E RID: 110
	private class 14FF39E1
	{
		// Token: 0x060003F2 RID: 1010 RVA: 0x00304DF0 File Offset: 0x001851F0
		public void 3FBA1723(uint 42AC1269)
		{
			for (uint num = this.4A5F3D3A; num < 42AC1269; num += 1U)
			{
				this.47943E7F[(int)num] = new 4D960C7C(3);
				this.4CE74E25[(int)num] = new 4D960C7C(3);
			}
			this.4A5F3D3A = 42AC1269;
		}

		// Token: 0x060003F3 RID: 1011 RVA: 0x00304E40 File Offset: 0x00185240
		public void 42BE245A()
		{
			this.34C56013.23B12C49();
			for (uint num = 0U; num < this.4A5F3D3A; num += 1U)
			{
				this.47943E7F[(int)num].21437AF6();
				this.4CE74E25[(int)num].21437AF6();
			}
			this.47757062.23B12C49();
			this.733B3EFB.21437AF6();
		}

		// Token: 0x060003F4 RID: 1012 RVA: 0x00304EA8 File Offset: 0x001852A8
		public uint 246B7EFB(75061222 53F63973, uint 15427CF0)
		{
			if (this.34C56013.65AA23FF(53F63973) == 0U)
			{
				return this.47943E7F[(int)15427CF0].40786810(53F63973);
			}
			uint num = 8U;
			if (this.47757062.65AA23FF(53F63973) == 0U)
			{
				num += this.4CE74E25[(int)15427CF0].40786810(53F63973);
			}
			else
			{
				num += 8U;
				num += this.733B3EFB.40786810(53F63973);
			}
			return num;
		}

		// Token: 0x040001D0 RID: 464
		private 3E043A9C 34C56013;

		// Token: 0x040001D1 RID: 465
		private 3E043A9C 47757062;

		// Token: 0x040001D2 RID: 466
		private readonly 4D960C7C[] 47943E7F = new 4D960C7C[16];

		// Token: 0x040001D3 RID: 467
		private readonly 4D960C7C[] 4CE74E25 = new 4D960C7C[16];

		// Token: 0x040001D4 RID: 468
		private 4D960C7C 733B3EFB = new 4D960C7C(8);

		// Token: 0x040001D5 RID: 469
		private uint 4A5F3D3A;
	}

	// Token: 0x0200006F RID: 111
	private class 42C8002E
	{
		// Token: 0x060003F6 RID: 1014 RVA: 0x00304F4C File Offset: 0x0018534C
		public void 64770E1F(int 3D10287B, int 45E27BA9)
		{
			if (this.265672B8 != null && this.60851D33 == 45E27BA9 && this.18140B22 == 3D10287B)
			{
				return;
			}
			this.18140B22 = 3D10287B;
			this.63996F3E = (1U << 3D10287B) - 1U;
			this.60851D33 = 45E27BA9;
			uint num = 1U << this.60851D33 + this.18140B22;
			this.265672B8 = new 788371D4.42C8002E.1CF86FB7[num];
			for (uint num2 = 0U; num2 < num; num2 += 1U)
			{
				this.265672B8[(int)num2].73D13D72();
			}
		}

		// Token: 0x060003F7 RID: 1015 RVA: 0x00304FDC File Offset: 0x001853DC
		public void 49D17826()
		{
			uint num = 1U << this.60851D33 + this.18140B22;
			for (uint num2 = 0U; num2 < num; num2 += 1U)
			{
				this.265672B8[(int)num2].3DF546AB();
			}
		}

		// Token: 0x060003F8 RID: 1016 RVA: 0x00305020 File Offset: 0x00185420
		private uint 669415F4(uint 6990069C, byte 7DE564B1)
		{
			return ((6990069C & this.63996F3E) << this.60851D33) + (uint)(7DE564B1 >> 8 - this.60851D33);
		}

		// Token: 0x060003F9 RID: 1017 RVA: 0x00305044 File Offset: 0x00185444
		public byte 2309489D(75061222 628549D2, uint 4B3B5402, byte 39DC522E)
		{
			return this.265672B8[(int)this.669415F4(4B3B5402, 39DC522E)].667D0F18(628549D2);
		}

		// Token: 0x060003FA RID: 1018 RVA: 0x00305060 File Offset: 0x00185460
		public byte 561B44DE(75061222 0C4872BE, uint 67401F7B, byte 4D3339BF, byte 6831410C)
		{
			return this.265672B8[(int)this.669415F4(67401F7B, 4D3339BF)].1B424A43(0C4872BE, 6831410C);
		}

		// Token: 0x040001D6 RID: 470
		private uint 089A7F7E = 1U;

		// Token: 0x040001D7 RID: 471
		private 788371D4.42C8002E.1CF86FB7[] 265672B8;

		// Token: 0x040001D8 RID: 472
		private int 60851D33;

		// Token: 0x040001D9 RID: 473
		private int 18140B22;

		// Token: 0x040001DA RID: 474
		private uint 63996F3E;

		// Token: 0x02000070 RID: 112
		private struct 1CF86FB7
		{
			// Token: 0x060003FC RID: 1020 RVA: 0x00305090 File Offset: 0x00185490
			public void 73D13D72()
			{
				this.7E622CF2 = new 3E043A9C[768];
			}

			// Token: 0x060003FD RID: 1021 RVA: 0x003050A4 File Offset: 0x001854A4
			public void 3DF546AB()
			{
				for (int i = 0; i < 768; i++)
				{
					this.7E622CF2[i].23B12C49();
				}
			}

			// Token: 0x060003FE RID: 1022 RVA: 0x003050D8 File Offset: 0x001854D8
			public byte 667D0F18(75061222 52720B46)
			{
				uint num = 1U;
				do
				{
					num = (num << 1 | this.7E622CF2[(int)num].65AA23FF(52720B46));
				}
				while (num < 256U);
				return (byte)num;
			}

			// Token: 0x060003FF RID: 1023 RVA: 0x0030510C File Offset: 0x0018550C
			public byte 1B424A43(75061222 79D47B7A, byte 054D176F)
			{
				uint num = 1U;
				for (;;)
				{
					uint num2 = (uint)(054D176F >> 7 & 1);
					054D176F = (byte)(054D176F << 1);
					uint num3 = this.7E622CF2[(int)((1U + num2 << 8) + num)].65AA23FF(79D47B7A);
					num = (num << 1 | num3);
					if (num2 != num3)
					{
						break;
					}
					if (num >= 256U)
					{
						goto IL_6B;
					}
				}
				while (num < 256U)
				{
					num = (num << 1 | this.7E622CF2[(int)num].65AA23FF(79D47B7A));
				}
				IL_6B:
				return (byte)num;
			}

			// Token: 0x040001DB RID: 475
			private 3E043A9C[] 7E622CF2;
		}
	}
}
