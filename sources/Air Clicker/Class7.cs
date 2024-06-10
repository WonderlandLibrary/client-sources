using System;
using System.Collections.Generic;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200000E RID: 14
[StandardModule]
internal sealed class Class7
{
	// Token: 0x060001F7 RID: 503 RVA: 0x000085EC File Offset: 0x000067EC
	private static void smethod_0(IntPtr intptr_0, bool bool_1)
	{
		Class7.bool_0 = (Class7.int_0 >= 50);
		if (Class7.bool_0)
		{
			Class7.int_0 = 0;
		}
		object obj = Class7.list_0;
		checked
		{
			lock (obj)
			{
				int num = Class7.list_0.Count - 1;
				for (int i = 0; i <= num; i++)
				{
					Class7.list_0[i](Class7.bool_0);
				}
			}
			Class7.int_0 += 10;
		}
	}

	// Token: 0x060001F8 RID: 504 RVA: 0x00008680 File Offset: 0x00006880
	private static void smethod_1()
	{
		if (Class7.list_0.Count == 0)
		{
			Class7.class8_0.method_1();
		}
		else
		{
			Class7.class8_0.method_0(0U, 10U, new Class8.Delegate1(Class7.smethod_0));
		}
	}

	// Token: 0x060001F9 RID: 505 RVA: 0x000086B8 File Offset: 0x000068B8
	public static void smethod_2(Class7.Delegate0 delegate0_0)
	{
		object obj = Class7.list_0;
		lock (obj)
		{
			if (!Class7.list_0.Contains(delegate0_0))
			{
				Class7.list_0.Add(delegate0_0);
				Class7.smethod_1();
			}
		}
	}

	// Token: 0x060001FA RID: 506 RVA: 0x00008710 File Offset: 0x00006910
	public static void smethod_3(Class7.Delegate0 delegate0_0)
	{
		object obj = Class7.list_0;
		lock (obj)
		{
			if (Class7.list_0.Contains(delegate0_0))
			{
				Class7.list_0.Remove(delegate0_0);
				Class7.smethod_1();
			}
		}
	}

	// Token: 0x04000071 RID: 113
	private static int int_0;

	// Token: 0x04000072 RID: 114
	private static bool bool_0;

	// Token: 0x04000073 RID: 115
	public static Class8 class8_0 = new Class8();

	// Token: 0x04000074 RID: 116
	private const int int_1 = 50;

	// Token: 0x04000075 RID: 117
	private const int int_2 = 10;

	// Token: 0x04000076 RID: 118
	private static List<Class7.Delegate0> list_0 = new List<Class7.Delegate0>();

	// Token: 0x0200000F RID: 15
	// (Invoke) Token: 0x060001FE RID: 510
	public delegate void Delegate0(bool invalidate);
}
