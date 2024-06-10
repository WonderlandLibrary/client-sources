using System;
using System.Runtime.InteropServices;

// Token: 0x0200003E RID: 62
public class 28A15A11
{
	// Token: 0x060001CC RID: 460 RVA: 0x002F0C78 File Offset: 0x00171078
	public 28A15A11()
	{
		if (28A15A11.59391A38 == null)
		{
			28A15A11.59391A38 = new uint[256];
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
				28A15A11.59391A38[i] = num;
			}
		}
	}

	// Token: 0x060001CD RID: 461 RVA: 0x002F0CF4 File Offset: 0x001710F4
	public uint 67473C0A(IntPtr 65BE5915, uint 346B2E4A)
	{
		uint num = 0U;
		int num2 = 0;
		while ((long)num2 < (long)((ulong)346B2E4A))
		{
			num = (28A15A11.59391A38[(int)(((uint)Marshal.ReadByte(new IntPtr(65BE5915.ToInt64() + (long)num2)) ^ num) & 255U)] ^ num >> 8);
			num2++;
		}
		return ~num;
	}

	// Token: 0x04000149 RID: 329
	private static uint[] 59391A38;
}
