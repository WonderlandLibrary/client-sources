using System;
using System.IO;

namespace Client
{
	// Token: 0x0200030B RID: 779
	internal class Class744
	{
		// Token: 0x060037D2 RID: 14290 RVA: 0x0002FD9C File Offset: 0x0002DF9C
		public void method_0(Stream stream_1)
		{
			this.stream_0 = stream_1;
			this.uint_3 = 0U;
			this.uint_2 = uint.MaxValue;
			for (int i = 0; i < 5; i++)
			{
				this.uint_3 = (this.uint_3 << 8 | (uint)((byte)this.stream_0.ReadByte()));
			}
		}

		// Token: 0x060037D3 RID: 14291 RVA: 0x00002385 File Offset: 0x00000585
		public void method_1()
		{
			this.stream_0 = null;
		}

		// Token: 0x060037D4 RID: 14292 RVA: 0x0002FDE8 File Offset: 0x0002DFE8
		public uint method_2(int int_0)
		{
			uint num = this.uint_2;
			uint num2 = this.uint_3;
			uint num3 = 0U;
			for (int i = int_0; i > 0; i--)
			{
				num >>= 1;
				uint num4 = num2 - num >> 31;
				num2 -= (num & num4 - 1U);
				num3 = (num3 << 1 | 1U - num4);
				if (num < 16777216U)
				{
					num2 = (num2 << 8 | (uint)((byte)this.stream_0.ReadByte()));
					num <<= 8;
				}
			}
			this.uint_2 = num;
			this.uint_3 = num2;
			return num3;
		}

		// Token: 0x04000130 RID: 304
		private uint uint_0 = 1U;

		// Token: 0x04000131 RID: 305
		public const uint uint_1 = 16777216U;

		// Token: 0x04000132 RID: 306
		public uint uint_2;

		// Token: 0x04000133 RID: 307
		public uint uint_3;

		// Token: 0x04000134 RID: 308
		public Stream stream_0;
	}
}
