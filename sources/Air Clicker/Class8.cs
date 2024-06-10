using System;
using System.Runtime.InteropServices;

// Token: 0x02000012 RID: 18
internal class Class8 : IDisposable
{
	// Token: 0x170000CC RID: 204
	// (get) Token: 0x06000206 RID: 518 RVA: 0x00008864 File Offset: 0x00006A64
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
	}

	// Token: 0x06000207 RID: 519
	[DllImport("kernel32.dll")]
	private static extern bool CreateTimerQueueTimer(ref IntPtr intptr_1, IntPtr intptr_2, Class8.Delegate1 delegate1_1, IntPtr intptr_3, uint uint_0, uint uint_1, uint uint_2);

	// Token: 0x06000208 RID: 520
	[DllImport("kernel32.dll")]
	private static extern bool DeleteTimerQueueTimer(IntPtr intptr_1, IntPtr intptr_2, IntPtr intptr_3);

	// Token: 0x06000209 RID: 521 RVA: 0x0000886C File Offset: 0x00006A6C
	public void method_0(uint uint_0, uint uint_1, Class8.Delegate1 delegate1_1)
	{
		if (!this.bool_0)
		{
			this.delegate1_0 = delegate1_1;
			bool flag;
			if (!(flag = Class8.CreateTimerQueueTimer(ref this.intptr_0, IntPtr.Zero, this.delegate1_0, IntPtr.Zero, uint_0, uint_1, 0U)))
			{
				this.method_2("CreateTimerQueueTimer");
			}
			this.bool_0 = flag;
		}
	}

	// Token: 0x0600020A RID: 522 RVA: 0x000088C0 File Offset: 0x00006AC0
	public void method_1()
	{
		if (this.bool_0)
		{
			bool flag;
			if (!(flag = Class8.DeleteTimerQueueTimer(IntPtr.Zero, this.intptr_0, IntPtr.Zero)) && Marshal.GetLastWin32Error() != 997)
			{
				this.method_2("DeleteTimerQueueTimer");
			}
			this.bool_0 = !flag;
		}
	}

	// Token: 0x0600020B RID: 523 RVA: 0x0000891C File Offset: 0x00006B1C
	private void method_2(string string_0)
	{
		throw new Exception(string.Format("{0} failed. Win32Error: {1}", string_0, Marshal.GetLastWin32Error()));
	}

	// Token: 0x0600020C RID: 524 RVA: 0x00008938 File Offset: 0x00006B38
	public void Dispose()
	{
		this.method_1();
	}

	// Token: 0x0400007E RID: 126
	private bool bool_0;

	// Token: 0x0400007F RID: 127
	private IntPtr intptr_0;

	// Token: 0x04000080 RID: 128
	private Class8.Delegate1 delegate1_0;

	// Token: 0x02000013 RID: 19
	// (Invoke) Token: 0x06000210 RID: 528
	public delegate void Delegate1(IntPtr r1, bool r2);
}
