using System;

namespace Client
{
	// Token: 0x02000305 RID: 773
	internal abstract class Class741
	{
		// Token: 0x060037B5 RID: 14261 RVA: 0x00002210 File Offset: 0x00000410
		public static uint smethod_0(uint uint_9)
		{
			uint_9 -= 2U;
			if (uint_9 < 4U)
			{
				return uint_9;
			}
			return 3U;
		}

		// Token: 0x060037B6 RID: 14262 RVA: 0x000208C0 File Offset: 0x0001EAC0
		protected Class741()
		{
			uint num = 1951662569U;
			do
			{
				base..ctor();
			}
			while (1075202719U >= num);
		}

		// Token: 0x04000101 RID: 257
		public const uint uint_0 = 12U;

		// Token: 0x04000102 RID: 258
		public const int int_0 = 6;

		// Token: 0x04000103 RID: 259
		private const int int_1 = 2;

		// Token: 0x04000104 RID: 260
		public const uint uint_1 = 4U;

		// Token: 0x04000105 RID: 261
		public const uint uint_2 = 2U;

		// Token: 0x04000106 RID: 262
		public const int int_2 = 4;

		// Token: 0x04000107 RID: 263
		public const uint uint_3 = 4U;

		// Token: 0x04000108 RID: 264
		public const uint uint_4 = 14U;

		// Token: 0x04000109 RID: 265
		public const uint uint_5 = 128U;

		// Token: 0x0400010A RID: 266
		public const int int_3 = 4;

		// Token: 0x0400010B RID: 267
		public const uint uint_6 = 16U;

		// Token: 0x0400010C RID: 268
		public const int int_4 = 3;

		// Token: 0x0400010D RID: 269
		public const int int_5 = 3;

		// Token: 0x0400010E RID: 270
		public const int int_6 = 8;

		// Token: 0x0400010F RID: 271
		public const uint uint_7 = 8U;

		// Token: 0x04000110 RID: 272
		public const uint uint_8 = 8U;

		// Token: 0x02000306 RID: 774
		public struct Struct0
		{
			// Token: 0x060037B7 RID: 14263 RVA: 0x0000221E File Offset: 0x0000041E
			public void method_0()
			{
				this.uint_0 = 0U;
			}

			// Token: 0x060037B8 RID: 14264 RVA: 0x00002227 File Offset: 0x00000427
			public void method_1()
			{
				if (this.uint_0 < 4U)
				{
					this.uint_0 = 0U;
					return;
				}
				if (this.uint_0 < 10U)
				{
					this.uint_0 -= 3U;
					return;
				}
				this.uint_0 -= 6U;
			}

			// Token: 0x060037B9 RID: 14265 RVA: 0x00002261 File Offset: 0x00000461
			public void method_2()
			{
				this.uint_0 = ((this.uint_0 < 7U) ? 7U : 10U);
			}

			// Token: 0x060037BA RID: 14266 RVA: 0x00002277 File Offset: 0x00000477
			public void method_3()
			{
				this.uint_0 = ((this.uint_0 < 7U) ? 8U : 11U);
			}

			// Token: 0x060037BB RID: 14267 RVA: 0x0000228D File Offset: 0x0000048D
			public void method_4()
			{
				this.uint_0 = ((this.uint_0 < 7U) ? 9U : 11U);
			}

			// Token: 0x060037BC RID: 14268 RVA: 0x000022A4 File Offset: 0x000004A4
			public bool method_5()
			{
				return this.uint_0 < 7U;
			}

			// Token: 0x04000111 RID: 273
			public uint uint_0;
		}
	}
}
