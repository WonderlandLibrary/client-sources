using System;
using System.IO;

namespace Client
{
	// Token: 0x0200030E RID: 782
	public class GClass8
	{
		// Token: 0x060037DD RID: 14301 RVA: 0x000023C4 File Offset: 0x000005C4
		public void method_0(uint uint_5)
		{
			if (this.uint_1 != uint_5)
			{
				this.byte_0 = new byte[uint_5];
			}
			this.uint_1 = uint_5;
			this.uint_0 = 0U;
			this.uint_2 = 0U;
		}

		// Token: 0x060037DE RID: 14302 RVA: 0x000023F0 File Offset: 0x000005F0
		public void method_1(Stream stream_1, bool bool_0)
		{
			this.method_2();
			this.stream_0 = stream_1;
			if (!bool_0)
			{
				this.uint_2 = 0U;
				this.uint_0 = 0U;
				this.uint_4 = 0U;
			}
		}

		// Token: 0x060037DF RID: 14303 RVA: 0x00002417 File Offset: 0x00000617
		public void method_2()
		{
			this.method_3();
			this.stream_0 = null;
		}

		// Token: 0x060037E0 RID: 14304 RVA: 0x0003004C File Offset: 0x0002E24C
		public void method_3()
		{
			uint num = this.uint_0 - this.uint_2;
			if (num == 0U)
			{
				return;
			}
			this.stream_0.Write(this.byte_0, (int)this.uint_2, (int)num);
			if (this.uint_0 >= this.uint_1)
			{
				this.uint_0 = 0U;
			}
			this.uint_2 = this.uint_0;
		}

		// Token: 0x060037E1 RID: 14305 RVA: 0x000300A4 File Offset: 0x0002E2A4
		public void method_4(uint uint_5, uint uint_6)
		{
			uint num = this.uint_0 - uint_5 - 1U;
			if (num >= this.uint_1)
			{
				num += this.uint_1;
			}
			while (uint_6 > 0U)
			{
				if (num >= this.uint_1)
				{
					num = 0U;
				}
				byte[] array = this.byte_0;
				uint num2 = this.uint_0;
				this.uint_0 = num2 + 1U;
				array[(int)num2] = this.byte_0[(int)num++];
				if (this.uint_0 >= this.uint_1)
				{
					this.method_3();
				}
				uint_6 -= 1U;
			}
		}

		// Token: 0x060037E2 RID: 14306 RVA: 0x0003011C File Offset: 0x0002E31C
		public void method_5(byte byte_1)
		{
			byte[] array = this.byte_0;
			uint num = this.uint_0;
			this.uint_0 = num + 1U;
			array[(int)num] = byte_1;
			if (this.uint_0 >= this.uint_1)
			{
				this.method_3();
			}
		}

		// Token: 0x060037E3 RID: 14307 RVA: 0x00030158 File Offset: 0x0002E358
		public byte method_6(uint uint_5)
		{
			uint num = this.uint_0 - uint_5 - 1U;
			if (num >= this.uint_1)
			{
				num += this.uint_1;
			}
			return this.byte_0[(int)num];
		}

		// Token: 0x0400013B RID: 315
		private byte[] byte_0;

		// Token: 0x0400013C RID: 316
		private uint uint_0;

		// Token: 0x0400013D RID: 317
		private uint uint_1;

		// Token: 0x0400013E RID: 318
		private uint uint_2;

		// Token: 0x0400013F RID: 319
		private Stream stream_0;

		// Token: 0x04000140 RID: 320
		private uint uint_3 = 1U;

		// Token: 0x04000141 RID: 321
		public uint uint_4;
	}
}
