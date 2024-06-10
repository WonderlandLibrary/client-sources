using System;
using System.IO;

namespace Client
{
	// Token: 0x02000307 RID: 775
	public class GClass7
	{
		// Token: 0x060037BD RID: 14269 RVA: 0x0002F448 File Offset: 0x0002D648
		public GClass7()
		{
			this.uint_1 = uint.MaxValue;
			int num = 0;
			while ((long)num < 4L)
			{
				this.struct3_0[num] = new Struct3(6);
				num++;
			}
		}

		// Token: 0x060037BE RID: 14270 RVA: 0x0002F540 File Offset: 0x0002D740
		private void method_0(uint uint_4)
		{
			if (this.uint_1 != uint_4)
			{
				this.uint_1 = uint_4;
				this.uint_2 = Math.Max(this.uint_1, 1U);
				uint uint_5 = Math.Max(this.uint_2, 4096U);
				this.gclass8_0.method_0(uint_5);
			}
		}

		// Token: 0x060037BF RID: 14271 RVA: 0x000022AF File Offset: 0x000004AF
		private void method_1(int int_0, int int_1)
		{
			if (int_0 > 8)
			{
				throw new ArgumentException("lp > 8");
			}
			if (int_1 > 8)
			{
				throw new ArgumentException("lc > 8");
			}
			this.class743_0.method_0(int_0, int_1);
		}

		// Token: 0x060037C0 RID: 14272 RVA: 0x0002F58C File Offset: 0x0002D78C
		private void method_2(int int_0)
		{
			if (int_0 > 4)
			{
				throw new ArgumentException("pb > Base.KNumPosStatesBitsMax");
			}
			uint num = 1U << int_0;
			this.class742_0.method_0(num);
			this.class742_1.method_0(num);
			this.uint_3 = num - 1U;
		}

		// Token: 0x060037C1 RID: 14273 RVA: 0x0002F5D0 File Offset: 0x0002D7D0
		private void method_3(Stream stream_0, Stream stream_1)
		{
			this.class744_0.method_0(stream_0);
			this.gclass8_0.method_1(stream_1, false);
			for (uint num = 0U; num < 12U; num += 1U)
			{
				for (uint num2 = 0U; num2 <= this.uint_3; num2 += 1U)
				{
					uint num3 = (num << 4) + num2;
					this.struct2_0[(int)num3].method_0();
					this.struct2_5[(int)num3].method_0();
				}
				this.struct2_1[(int)num].method_0();
				this.struct2_2[(int)num].method_0();
				this.struct2_3[(int)num].method_0();
				this.struct2_4[(int)num].method_0();
			}
			this.class743_0.method_1();
			for (uint num = 0U; num < 4U; num += 1U)
			{
				this.struct3_0[(int)num].method_0();
			}
			for (uint num = 0U; num < 114U; num += 1U)
			{
				this.struct2_6[(int)num].method_0();
			}
			this.class742_0.method_1();
			this.class742_1.method_1();
			this.struct3_1.method_0();
		}

		// Token: 0x060037C2 RID: 14274 RVA: 0x0002F6F0 File Offset: 0x0002D8F0
		public void method_4(Stream stream_0, Stream stream_1, long long_0)
		{
			this.method_3(stream_0, stream_1);
			Class741.Struct0 @struct = default(Class741.Struct0);
			@struct.method_0();
			uint num = 0U;
			uint num2 = 0U;
			uint num3 = 0U;
			uint num4 = 0U;
			ulong num5 = 0UL;
			if (0L < long_0)
			{
				if (this.struct2_0[(int)((int)@struct.uint_0 << 4)].method_1(this.class744_0) != 0U)
				{
					throw new InvalidDataException("IsMatchDecoders");
				}
				@struct.method_1();
				byte byte_ = this.class743_0.method_3(this.class744_0, 0U, 0);
				this.gclass8_0.method_5(byte_);
				num5 += 1UL;
			}
			while (num5 < (ulong)long_0)
			{
				uint num6 = (uint)num5 & this.uint_3;
				if (this.struct2_0[(int)((@struct.uint_0 << 4) + num6)].method_1(this.class744_0) == 0U)
				{
					byte byte_2 = this.gclass8_0.method_6(0U);
					byte byte_3;
					if (!@struct.method_5())
					{
						byte_3 = this.class743_0.method_4(this.class744_0, (uint)num5, byte_2, this.gclass8_0.method_6(num));
					}
					else
					{
						byte_3 = this.class743_0.method_3(this.class744_0, (uint)num5, byte_2);
					}
					this.gclass8_0.method_5(byte_3);
					@struct.method_1();
					num5 += 1UL;
				}
				else
				{
					uint num8;
					if (this.struct2_1[(int)@struct.uint_0].method_1(this.class744_0) == 1U)
					{
						if (this.struct2_2[(int)@struct.uint_0].method_1(this.class744_0) == 0U)
						{
							if (this.struct2_5[(int)((@struct.uint_0 << 4) + num6)].method_1(this.class744_0) == 0U)
							{
								@struct.method_4();
								this.gclass8_0.method_5(this.gclass8_0.method_6(num));
								num5 += 1UL;
								continue;
							}
						}
						else
						{
							uint num7;
							if (this.struct2_3[(int)@struct.uint_0].method_1(this.class744_0) == 0U)
							{
								num7 = num2;
							}
							else
							{
								if (this.struct2_4[(int)@struct.uint_0].method_1(this.class744_0) == 0U)
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
						num8 = this.class742_1.method_2(this.class744_0, num6) + 2U;
						@struct.method_3();
					}
					else
					{
						num4 = num3;
						num3 = num2;
						num2 = num;
						num8 = 2U + this.class742_0.method_2(this.class744_0, num6);
						@struct.method_2();
						uint num9 = this.struct3_0[(int)Class741.smethod_0(num8)].method_1(this.class744_0);
						if (num9 >= 4U)
						{
							int num10 = (int)((num9 >> 1) - 1U);
							num = (2U | (num9 & 1U)) << num10;
							if (num9 < 14U)
							{
								num += Struct3.smethod_0(this.struct2_6, num - num9 - 1U, this.class744_0, num10);
							}
							else
							{
								num += this.class744_0.method_2(num10 - 4) << 4;
								num += this.struct3_1.method_2(this.class744_0);
							}
						}
						else
						{
							num = num9;
						}
					}
					if ((ulong)num < (ulong)this.gclass8_0.uint_4 + num5 && num < this.uint_2)
					{
						this.gclass8_0.method_4(num, num8);
						num5 += (ulong)num8;
					}
					else
					{
						if (num != 4294967295U)
						{
							throw new InvalidDataException("rep0");
						}
						IL_359:
						this.gclass8_0.method_3();
						this.gclass8_0.method_2();
						this.class744_0.method_1();
						return;
					}
				}
			}
			goto IL_359;
		}

		// Token: 0x060037C3 RID: 14275 RVA: 0x0002FA78 File Offset: 0x0002DC78
		public void method_5(byte[] byte_0)
		{
			if (byte_0.Length < 5)
			{
				throw new ArgumentException("properties.Length < 5");
			}
			int int_ = (int)(byte_0[0] % 9);
			byte b = byte_0[0] / 9;
			int int_2 = (int)(b % 5);
			int num = (int)(b / 5);
			if (num > 4)
			{
				throw new ArgumentException("pb > Base.kNumPosStatesBitsMax");
			}
			uint num2 = 0U;
			for (int i = 0; i < 4; i++)
			{
				num2 += (uint)((uint)byte_0[1 + i] << i * 8);
			}
			this.method_0(num2);
			this.method_1(int_2, int_);
			this.method_2(num);
		}

		// Token: 0x04000112 RID: 274
		private uint uint_0 = 1U;

		// Token: 0x04000113 RID: 275
		private readonly GClass8 gclass8_0 = new GClass8();

		// Token: 0x04000114 RID: 276
		private readonly Class744 class744_0 = new Class744();

		// Token: 0x04000115 RID: 277
		private readonly Struct2[] struct2_0 = new Struct2[192];

		// Token: 0x04000116 RID: 278
		private readonly Struct2[] struct2_1 = new Struct2[12];

		// Token: 0x04000117 RID: 279
		private readonly Struct2[] struct2_2 = new Struct2[12];

		// Token: 0x04000118 RID: 280
		private readonly Struct2[] struct2_3 = new Struct2[12];

		// Token: 0x04000119 RID: 281
		private readonly Struct2[] struct2_4 = new Struct2[12];

		// Token: 0x0400011A RID: 282
		private readonly Struct2[] struct2_5 = new Struct2[192];

		// Token: 0x0400011B RID: 283
		private readonly Struct3[] struct3_0 = new Struct3[4];

		// Token: 0x0400011C RID: 284
		private readonly Struct2[] struct2_6 = new Struct2[114];

		// Token: 0x0400011D RID: 285
		private Struct3 struct3_1 = new Struct3(4);

		// Token: 0x0400011E RID: 286
		private readonly GClass7.Class742 class742_0 = new GClass7.Class742();

		// Token: 0x0400011F RID: 287
		private readonly GClass7.Class742 class742_1 = new GClass7.Class742();

		// Token: 0x04000120 RID: 288
		private readonly GClass7.Class743 class743_0 = new GClass7.Class743();

		// Token: 0x04000121 RID: 289
		private uint uint_1;

		// Token: 0x04000122 RID: 290
		private uint uint_2;

		// Token: 0x04000123 RID: 291
		private uint uint_3;

		// Token: 0x02000308 RID: 776
		private class Class742
		{
			// Token: 0x060037C4 RID: 14276 RVA: 0x0002FAF4 File Offset: 0x0002DCF4
			public void method_0(uint uint_1)
			{
				for (uint num = this.uint_0; num < uint_1; num += 1U)
				{
					this.struct3_0[(int)num] = new Struct3(3);
					this.struct3_1[(int)num] = new Struct3(3);
				}
				this.uint_0 = uint_1;
			}

			// Token: 0x060037C5 RID: 14277 RVA: 0x0002FB40 File Offset: 0x0002DD40
			public void method_1()
			{
				this.struct2_0.method_0();
				for (uint num = 0U; num < this.uint_0; num += 1U)
				{
					this.struct3_0[(int)num].method_0();
					this.struct3_1[(int)num].method_0();
				}
				this.struct2_1.method_0();
				this.struct3_2.method_0();
			}

			// Token: 0x060037C6 RID: 14278 RVA: 0x0002FBA4 File Offset: 0x0002DDA4
			public uint method_2(Class744 class744_0, uint uint_1)
			{
				if (this.struct2_0.method_1(class744_0) == 0U)
				{
					return this.struct3_0[(int)uint_1].method_1(class744_0);
				}
				uint num = 8U;
				if (this.struct2_1.method_1(class744_0) == 0U)
				{
					num += this.struct3_1[(int)uint_1].method_1(class744_0);
				}
				else
				{
					num += 8U;
					num += this.struct3_2.method_1(class744_0);
				}
				return num;
			}

			// Token: 0x04000124 RID: 292
			private Struct2 struct2_0;

			// Token: 0x04000125 RID: 293
			private Struct2 struct2_1;

			// Token: 0x04000126 RID: 294
			private readonly Struct3[] struct3_0 = new Struct3[16];

			// Token: 0x04000127 RID: 295
			private readonly Struct3[] struct3_1 = new Struct3[16];

			// Token: 0x04000128 RID: 296
			private Struct3 struct3_2 = new Struct3(8);

			// Token: 0x04000129 RID: 297
			private uint uint_0;
		}

		// Token: 0x02000309 RID: 777
		private class Class743
		{
			// Token: 0x060037C8 RID: 14280 RVA: 0x0002FC10 File Offset: 0x0002DE10
			public void method_0(int int_2, int int_3)
			{
				if (this.struct1_0 != null && this.int_0 == int_3 && this.int_1 == int_2)
				{
					return;
				}
				this.int_1 = int_2;
				this.uint_1 = (1U << int_2) - 1U;
				this.int_0 = int_3;
				uint num = 1U << this.int_0 + this.int_1;
				this.struct1_0 = new GClass7.Class743.Struct1[num];
				for (uint num2 = 0U; num2 < num; num2 += 1U)
				{
					this.struct1_0[(int)num2].method_0();
				}
			}

			// Token: 0x060037C9 RID: 14281 RVA: 0x0002FC90 File Offset: 0x0002DE90
			public void method_1()
			{
				uint num = 1U << this.int_0 + this.int_1;
				for (uint num2 = 0U; num2 < num; num2 += 1U)
				{
					this.struct1_0[(int)num2].method_1();
				}
			}

			// Token: 0x060037CA RID: 14282 RVA: 0x0000230A File Offset: 0x0000050A
			private uint method_2(uint uint_2, byte byte_0)
			{
				return ((uint_2 & this.uint_1) << this.int_0) + (uint)(byte_0 >> 8 - this.int_0);
			}

			// Token: 0x060037CB RID: 14283 RVA: 0x0000232C File Offset: 0x0000052C
			public byte method_3(Class744 class744_0, uint uint_2, byte byte_0)
			{
				return this.struct1_0[(int)this.method_2(uint_2, byte_0)].method_2(class744_0);
			}

			// Token: 0x060037CC RID: 14284 RVA: 0x00002347 File Offset: 0x00000547
			public byte method_4(Class744 class744_0, uint uint_2, byte byte_0, byte byte_1)
			{
				return this.struct1_0[(int)this.method_2(uint_2, byte_0)].method_3(class744_0, byte_1);
			}

			// Token: 0x0400012A RID: 298
			private uint uint_0 = 1U;

			// Token: 0x0400012B RID: 299
			private GClass7.Class743.Struct1[] struct1_0;

			// Token: 0x0400012C RID: 300
			private int int_0;

			// Token: 0x0400012D RID: 301
			private int int_1;

			// Token: 0x0400012E RID: 302
			private uint uint_1;

			// Token: 0x0200030A RID: 778
			private struct Struct1
			{
				// Token: 0x060037CE RID: 14286 RVA: 0x00002373 File Offset: 0x00000573
				public void method_0()
				{
					this.struct2_0 = new Struct2[768];
				}

				// Token: 0x060037CF RID: 14287 RVA: 0x0002FCD0 File Offset: 0x0002DED0
				public void method_1()
				{
					for (int i = 0; i < 768; i++)
					{
						this.struct2_0[i].method_0();
					}
				}

				// Token: 0x060037D0 RID: 14288 RVA: 0x0002FD00 File Offset: 0x0002DF00
				public byte method_2(Class744 class744_0)
				{
					uint num = 1U;
					do
					{
						num = (num << 1 | this.struct2_0[(int)num].method_1(class744_0));
					}
					while (num < 256U);
					return (byte)num;
				}

				// Token: 0x060037D1 RID: 14289 RVA: 0x0002FD30 File Offset: 0x0002DF30
				public byte method_3(Class744 class744_0, byte byte_0)
				{
					uint num = 1U;
					for (;;)
					{
						uint num2 = (uint)(byte_0 >> 7 & 1);
						byte_0 = (byte)(byte_0 << 1);
						uint num3 = this.struct2_0[(int)((1U + num2 << 8) + num)].method_1(class744_0);
						num = (num << 1 | num3);
						if (num2 != num3)
						{
							break;
						}
						if (num >= 256U)
						{
							goto IL_5C;
						}
					}
					while (num < 256U)
					{
						num = (num << 1 | this.struct2_0[(int)num].method_1(class744_0));
					}
					IL_5C:
					return (byte)num;
				}

				// Token: 0x0400012F RID: 303
				private Struct2[] struct2_0;
			}
		}
	}
}
