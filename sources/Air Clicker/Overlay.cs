using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000E5 RID: 229
[DesignerGenerated]
public partial class Overlay : Form
{
	// Token: 0x0600098E RID: 2446 RVA: 0x0002CCB0 File Offset: 0x0002AEB0
	public Overlay()
	{
		base.Load += this.Overlay_Load;
		this.intptr_0 = Overlay.FindWindow(null, "Minecraft 1.7.10");
		this.InitializeComponent();
	}

	// Token: 0x0600098F RID: 2447 RVA: 0x0002CCE4 File Offset: 0x0002AEE4
	[DebuggerNonUserCode]
	protected virtual void Dispose(bool disposing)
	{
		try
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
		}
		finally
		{
			base.Dispose(disposing);
		}
	}

	// Token: 0x1700029A RID: 666
	// (get) Token: 0x06000991 RID: 2449 RVA: 0x0002CDBC File Offset: 0x0002AFBC
	// (set) Token: 0x06000992 RID: 2450 RVA: 0x0002CDC4 File Offset: 0x0002AFC4
	internal virtual Timer Timer_0
	{
		[CompilerGenerated]
		get
		{
			return this.timer_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_0);
			Timer timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_0 = value;
			timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x06000993 RID: 2451
	[DllImport("user32.dll", SetLastError = true)]
	private static extern IntPtr FindWindow(string string_1, string string_2);

	// Token: 0x06000994 RID: 2452
	[DllImport("user32.dll", SetLastError = true)]
	private static extern int GetWindowLong(IntPtr intptr_1, int int_0);

	// Token: 0x06000995 RID: 2453
	[DllImport("user32.dll", SetLastError = true)]
	private static extern bool GetWindowRect(IntPtr intptr_1, out Overlay.GStruct1 gstruct1_1);

	// Token: 0x06000996 RID: 2454
	[DllImport("user32.dll")]
	private static extern int SetWindowLong(IntPtr intptr_1, int int_0, int int_1);

	// Token: 0x06000997 RID: 2455 RVA: 0x0002CE08 File Offset: 0x0002B008
	private void Overlay_Load(object sender, EventArgs e)
	{
		Class1.Class2_0.Hitmarker_0.Show();
		base.Opacity = 0.4;
		base.ShowInTaskbar = true;
		base.TransparencyKey = Color.Wheat;
		base.TopMost = true;
		this.Timer_0.Enabled = true;
		base.FormBorderStyle = FormBorderStyle.None;
		int windowLong = Overlay.GetWindowLong(this.method_2(), -20);
		Overlay.SetWindowLong(this.method_2(), -20, windowLong | 524288 | 32);
		Overlay.GetWindowRect(this.intptr_0, out this.gstruct1_0);
		base.Size = checked(new Size(this.gstruct1_0.int_2 - this.gstruct1_0.int_0, this.gstruct1_0.int_3 - this.gstruct1_0.int_1));
		base.Top = this.gstruct1_0.int_1;
	}

	// Token: 0x06000998 RID: 2456 RVA: 0x0002CEE4 File Offset: 0x0002B0E4
	private void method_0(object sender, EventArgs e)
	{
		Overlay.GetWindowRect(this.intptr_0, out this.gstruct1_0);
		base.Top = this.gstruct1_0.int_1;
		base.Left = this.gstruct1_0.int_0;
		base.Size = checked(new Size(this.gstruct1_0.int_2 - this.gstruct1_0.int_0, this.gstruct1_0.int_3 - this.gstruct1_0.int_1));
	}

	// Token: 0x06000999 RID: 2457 RVA: 0x0002CF60 File Offset: 0x0002B160
	private void method_1(object sender, EventArgs e)
	{
	}

	// Token: 0x0600099A RID: 2458 RVA: 0x0002CF64 File Offset: 0x0002B164
	IntPtr method_2()
	{
		return base.Handle;
	}

	// Token: 0x040004A5 RID: 1189
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("Timer1")]
	private Timer timer_0;

	// Token: 0x040004A6 RID: 1190
	private Overlay.GStruct1 gstruct1_0;

	// Token: 0x040004A7 RID: 1191
	public const string string_0 = "Minecraft 1.7.10";

	// Token: 0x040004A8 RID: 1192
	private IntPtr intptr_0;

	// Token: 0x020000E6 RID: 230
	public struct GStruct1
	{
		// Token: 0x040004A9 RID: 1193
		public int int_0;

		// Token: 0x040004AA RID: 1194
		public int int_1;

		// Token: 0x040004AB RID: 1195
		public int int_2;

		// Token: 0x040004AC RID: 1196
		public int int_3;
	}
}
