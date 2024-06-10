using System;

namespace Client
{
	// Token: 0x0200030C RID: 780
	internal struct Struct2
	{
		// Token: 0x060037D6 RID: 14294 RVA: 0x0000239D File Offset: 0x0000059D
		public void method_0()
		{
			this.uint_1 = 1024U;
		}

		// Token: 0x060037D7 RID: 14295 RVA: 0x0002FE5C File Offset: 0x0002E05C
		public uint method_1(Class744 class744_0)
		{
			uint num = (class744_0.uint_2 >> 11) * this.uint_1;
			if (class744_0.uint_3 < num)
			{
				class744_0.uint_2 = num;
				this.uint_1 += 2048U - this.uint_1 >> 5;
				if (class744_0.uint_2 < 16777216U)
				{
					class744_0.uint_3 = (class744_0.uint_3 << 8 | (uint)((byte)class744_0.stream_0.ReadByte()));
					class744_0.uint_2 <<= 8;
				}
				return 0U;
			}
			class744_0.uint_2 -= num;
			class744_0.uint_3 -= num;
			this.uint_1 -= this.uint_1 >> 5;
			if (class744_0.uint_2 < 16777216U)
			{
				class744_0.uint_3 = (class744_0.uint_3 << 8 | (uint)((byte)class744_0.stream_0.ReadByte()));
				class744_0.uint_2 <<= 8;
			}
			return 1U;
		}

		// Token: 0x04000135 RID: 309
		private const int int_0 = 11;

		// Token: 0x04000136 RID: 310
		private const uint uint_0 = 2048U;

		// Token: 0x04000137 RID: 311
		private const int int_1 = 5;

		// Token: 0x04000138 RID: 312
		private uint uint_1;
	}
}
