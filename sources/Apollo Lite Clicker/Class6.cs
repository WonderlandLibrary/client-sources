using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Threading;

// Token: 0x02000004 RID: 4
internal class Class6 : IDisposable
{
	// Token: 0x14000001 RID: 1
	// (add) Token: 0x0600000D RID: 13 RVA: 0x00002198 File Offset: 0x00000398
	// (remove) Token: 0x0600000E RID: 14 RVA: 0x000021D0 File Offset: 0x000003D0
	public event EventHandler<EventArgs0> Event_0
	{
		[CompilerGenerated]
		add
		{
			EventHandler<EventArgs0> eventHandler = this.eventHandler_0;
			EventHandler<EventArgs0> eventHandler2 = eventHandler;
			EventHandler<EventArgs0> value2 = (EventHandler<EventArgs0>)Delegate.Combine(eventHandler2, value);
			eventHandler = Interlocked.CompareExchange<EventHandler<EventArgs0>>(ref this.eventHandler_0, value2, eventHandler2);
			if (eventHandler != eventHandler2)
			{
			}
		}
		[CompilerGenerated]
		remove
		{
			EventHandler<EventArgs0> eventHandler = this.eventHandler_0;
			EventHandler<EventArgs0> eventHandler2;
			do
			{
				eventHandler2 = eventHandler;
				EventHandler<EventArgs0> value2 = (EventHandler<EventArgs0>)Delegate.Remove(eventHandler2, value);
				eventHandler = Interlocked.CompareExchange<EventHandler<EventArgs0>>(ref this.eventHandler_0, value2, eventHandler2);
			}
			while (eventHandler != eventHandler2);
		}
	}

	// Token: 0x0600000F RID: 15 RVA: 0x0000220C File Offset: 0x0000040C
	public Class6()
	{
		this.intptr_0 = IntPtr.Zero;
		this.intptr_1 = IntPtr.Zero;
		this.delegate0_0 = new Class6.Delegate0(this.method_0);
		this.intptr_1 = Class6.LoadLibrary("User32");
		if (this.intptr_1 == IntPtr.Zero)
		{
			int lastWin32Error = Marshal.GetLastWin32Error();
			throw new Win32Exception(lastWin32Error, string.Format("Failed to load library 'User32.dll'. Error {0}: {1}.", lastWin32Error, new Win32Exception(Marshal.GetLastWin32Error()).Message));
		}
		this.intptr_0 = Class6.SetWindowsHookEx(13, this.delegate0_0, this.intptr_1, 0);
		if (this.intptr_0 == IntPtr.Zero)
		{
			int lastWin32Error2 = Marshal.GetLastWin32Error();
			throw new Win32Exception(lastWin32Error2, string.Format("Failed to adjust keyboard hooks for '{0}'. Error {1}: {2}.", Process.GetCurrentProcess().ProcessName, lastWin32Error2, new Win32Exception(Marshal.GetLastWin32Error()).Message));
		}
	}

	// Token: 0x06000010 RID: 16 RVA: 0x000022F8 File Offset: 0x000004F8
	protected virtual void vmethod_0(bool bool_0)
	{
		if (bool_0 && this.intptr_0 != IntPtr.Zero)
		{
			this.intptr_0 = IntPtr.Zero;
			this.delegate0_0 = (Class6.Delegate0)Delegate.Remove(this.delegate0_0, new Class6.Delegate0(this.method_0));
		}
		if (this.intptr_1 != IntPtr.Zero)
		{
			if (!Class6.FreeLibrary(this.intptr_1))
			{
				int lastWin32Error = Marshal.GetLastWin32Error();
				throw new Win32Exception(lastWin32Error, string.Format("Failed to unload library 'User32.dll'. Error {0}: {1}.", lastWin32Error, new Win32Exception(Marshal.GetLastWin32Error()).Message));
			}
			this.intptr_1 = IntPtr.Zero;
		}
	}

	// Token: 0x06000011 RID: 17 RVA: 0x000023A0 File Offset: 0x000005A0
	virtual ~Class6()
	{
		this.vmethod_0(false);
	}

	// Token: 0x06000012 RID: 18 RVA: 0x000023D0 File Offset: 0x000005D0
	public void Dispose()
	{
		this.vmethod_0(true);
		GC.SuppressFinalize(this);
	}

	// Token: 0x06000013 RID: 19
	[DllImport("kernel32.dll")]
	private static extern IntPtr LoadLibrary(string string_0);

	// Token: 0x06000014 RID: 20
	[DllImport("kernel32.dll", CharSet = CharSet.Auto)]
	private static extern bool FreeLibrary(IntPtr intptr_2);

	// Token: 0x06000015 RID: 21
	[DllImport("USER32", SetLastError = true)]
	private static extern IntPtr SetWindowsHookEx(int int_4, Class6.Delegate0 delegate0_1, IntPtr intptr_2, int int_5);

	// Token: 0x06000016 RID: 22
	[DllImport("USER32", SetLastError = true)]
	public static extern bool UnhookWindwowsHookEx(IntPtr intptr_2);

	// Token: 0x06000017 RID: 23
	[DllImport("USER32", SetLastError = true)]
	private static extern IntPtr CallNextHookEx(IntPtr intptr_2, int int_4, IntPtr intptr_3, IntPtr intptr_4);

	// Token: 0x06000018 RID: 24 RVA: 0x000023EC File Offset: 0x000005EC
	public IntPtr method_0(int int_4, IntPtr intptr_2, IntPtr intptr_3)
	{
		bool flag = false;
		int num = intptr_2.ToInt32();
		if (Enum.IsDefined(typeof(Class6.Enum0), num))
		{
			EventArgs0 eventArgs = new EventArgs0((Class6.Struct6)Marshal.PtrToStructure(intptr_3, typeof(Class6.Struct6)), (Class6.Enum0)num);
			EventHandler<EventArgs0> eventHandler = this.eventHandler_0;
			if (eventHandler != null)
			{
				eventHandler(this, eventArgs);
			}
			flag = eventArgs.Handled;
		}
		if (flag)
		{
			return (IntPtr)1;
		}
		return Class6.CallNextHookEx(IntPtr.Zero, int_4, intptr_2, intptr_3);
	}

	// Token: 0x04000005 RID: 5
	[CompilerGenerated]
	private EventHandler<EventArgs0> eventHandler_0;

	// Token: 0x04000006 RID: 6
	private IntPtr intptr_0;

	// Token: 0x04000007 RID: 7
	private IntPtr intptr_1;

	// Token: 0x04000008 RID: 8
	private Class6.Delegate0 delegate0_0;

	// Token: 0x04000009 RID: 9
	public const int int_0 = 13;

	// Token: 0x0400000A RID: 10
	public const int int_1 = 44;

	// Token: 0x0400000B RID: 11
	private const int int_2 = 8192;

	// Token: 0x0400000C RID: 12
	public const int int_3 = 32;

	// Token: 0x02000005 RID: 5
	// (Invoke) Token: 0x0600001A RID: 26
	private delegate IntPtr Delegate0(int nCode, IntPtr wParam, IntPtr lParam);

	// Token: 0x02000006 RID: 6
	public struct Struct6
	{
		// Token: 0x0400000D RID: 13
		public int int_0;

		// Token: 0x0400000E RID: 14
		public int int_1;

		// Token: 0x0400000F RID: 15
		public int int_2;

		// Token: 0x04000010 RID: 16
		public int int_3;

		// Token: 0x04000011 RID: 17
		public IntPtr intptr_0;
	}

	// Token: 0x02000007 RID: 7
	public enum Enum0
	{
		// Token: 0x04000013 RID: 19
		KeyDown = 256,
		// Token: 0x04000014 RID: 20
		KeyUp,
		// Token: 0x04000015 RID: 21
		SysKeyDown = 260,
		// Token: 0x04000016 RID: 22
		SysKeyUp
	}
}
