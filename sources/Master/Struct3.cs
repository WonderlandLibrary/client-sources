using System;

namespace Client
{
	// Token: 0x0200030D RID: 781
	internal struct Struct3
	{
		// Token: 0x060037D8 RID: 14296 RVA: 0x000023AA File Offset: 0x000005AA
		public Struct3(int int_1)
		{
			this.int_0 = int_1;
			this.struct2_0 = new Struct2[1 << int_1];
		}

		// Token: 0x060037D9 RID: 14297 RVA: 0x0002FF48 File Offset: 0x0002E148
		public void method_0()
		{
			uint num = 1U;
			while ((ulong)num < (ulong)(1L << (this.int_0 & 31)))
			{
				this.struct2_0[(int)num].method_0();
				num += 1U;
			}
		}

		// Token: 0x060037DA RID: 14298 RVA: 0x0002FF80 File Offset: 0x0002E180
		public uint method_1(Class744 class744_0)
		{
			uint num = 1U;
			for (int i = this.int_0; i > 0; i--)
			{
				num = (num << 1) + this.struct2_0[(int)num].method_1(class744_0);
			}
			return num - (1U << this.int_0);
		}

		// Token: 0x060037DB RID: 14299 RVA: 0x0002FFC4 File Offset: 0x0002E1C4
		public uint method_2(Class744 class744_0)
		{
			uint num = 1U;
			uint num2 = 0U;
			for (int i = 0; i < this.int_0; i++)
			{
				uint num3 = this.struct2_0[(int)num].method_1(class744_0);
				num <<= 1;
				num += num3;
				num2 |= num3 << i;
			}
			return num2;
		}

		// Token: 0x060037DC RID: 14300 RVA: 0x0003000C File Offset: 0x0002E20C
		public static uint smethod_0(Struct2[] struct2_1, uint uint_0, Class744 class744_0, int int_1)
		{
			uint num = 1U;
			uint num2 = 0U;
			for (int i = 0; i < int_1; i++)
			{
				uint num3 = struct2_1[(int)(uint_0 + num)].method_1(class744_0);
				num <<= 1;
				num += num3;
				num2 |= num3 << i;
			}
			return num2;
		}

		// Token: 0x04000139 RID: 313
		private readonly Struct2[] struct2_0;

		// Token: 0x0400013A RID: 314
		private readonly int int_0;
	}
}
