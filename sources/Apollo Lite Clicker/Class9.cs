using System;
using System.Runtime.InteropServices;
using System.Threading;

// Token: 0x0200000F RID: 15
internal class Class9
{
	// Token: 0x06000072 RID: 114
	[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
	public static extern void mouse_event(uint uint_4, uint uint_5, uint uint_6, uint uint_7, uint uint_8);

	// Token: 0x06000074 RID: 116 RVA: 0x00007674 File Offset: 0x00005874
	public void method_0()
	{
		new Thread(new ThreadStart(Class9.<>c.<>9.method_0)).Start();
	}

	// Token: 0x06000075 RID: 117 RVA: 0x000076AC File Offset: 0x000058AC
	public void method_1()
	{
		new Thread(new ThreadStart(Class9.<>c.<>9.method_1)).Start();
	}

	// Token: 0x06000076 RID: 118 RVA: 0x000076E4 File Offset: 0x000058E4
	public void method_2()
	{
		new Thread(new ThreadStart(Class9.<>c.<>9.method_2)).Start();
	}

	// Token: 0x06000077 RID: 119 RVA: 0x0000771C File Offset: 0x0000591C
	public void method_3()
	{
		new Thread(new ThreadStart(Class9.<>c.<>9.method_3)).Start();
	}

	// Token: 0x040000C9 RID: 201
	private const uint uint_0 = 2u;

	// Token: 0x040000CA RID: 202
	private const uint uint_1 = 4u;

	// Token: 0x040000CB RID: 203
	private const uint uint_2 = 8u;

	// Token: 0x040000CC RID: 204
	private const uint uint_3 = 16u;

	// Token: 0x040000CD RID: 205
	private Thread thread_0;
}
