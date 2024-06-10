using System;
using System.Runtime.InteropServices;

namespace Client
{
	// Token: 0x020002DC RID: 732
	public class GClass1
	{
		// Token: 0x060035B1 RID: 13745 RVA: 0x000207C4 File Offset: 0x0001E9C4
		public GClass1()
		{
			if (GClass1.uint_0 == null)
			{
				GClass1.uint_0 = new uint[256];
				for (int i = 0; i < 256; i++)
				{
					uint num = (uint)i;
					for (int j = 0; j < 8; j++)
					{
						if ((num & 1U) == 1U)
						{
							num = (num >> 1 ^ 3988292384U);
						}
						else
						{
							num >>= 1;
						}
					}
					GClass1.uint_0[i] = num;
				}
			}
		}

		// Token: 0x060035B2 RID: 13746 RVA: 0x0002082C File Offset: 0x0001EA2C
		public uint method_0(IntPtr intptr_0, uint uint_1)
		{
			uint num = 0U;
			int num2 = 0;
			while ((long)num2 < (long)((ulong)uint_1))
			{
				num = (GClass1.uint_0[(int)(((uint)Marshal.ReadByte(new IntPtr(intptr_0.ToInt64() + (long)num2)) ^ num) & 255U)] ^ num >> 8);
				num2++;
			}
			return ~num;
		}

		// Token: 0x040000AF RID: 175
		private static uint[] uint_0;
	}
}
