using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200005B RID: 91
[DesignerGenerated]
public partial class Cpsdisplay : Form
{
	// Token: 0x060004AD RID: 1197 RVA: 0x000169D0 File Offset: 0x00014BD0
	public Cpsdisplay()
	{
		base.Load += this.Cpsdisplay_Load;
		this.int_0 = 0;
		this.intptr_0 = Cpsdisplay.FindWindow(null, "Minecraft 1.7.10");
		this.InitializeComponent();
	}

	// Token: 0x060004AE RID: 1198 RVA: 0x00016A08 File Offset: 0x00014C08
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

	// Token: 0x1700016E RID: 366
	// (get) Token: 0x060004B0 RID: 1200 RVA: 0x00016CD0 File Offset: 0x00014ED0
	// (set) Token: 0x060004B1 RID: 1201 RVA: 0x00016CD8 File Offset: 0x00014ED8
	internal virtual GClass4 LogInLabel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700016F RID: 367
	// (get) Token: 0x060004B2 RID: 1202 RVA: 0x00016CE4 File Offset: 0x00014EE4
	// (set) Token: 0x060004B3 RID: 1203 RVA: 0x00016CEC File Offset: 0x00014EEC
	internal virtual GClass4 LogInLabel2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000170 RID: 368
	// (get) Token: 0x060004B4 RID: 1204 RVA: 0x00016CF8 File Offset: 0x00014EF8
	// (set) Token: 0x060004B5 RID: 1205 RVA: 0x00016D00 File Offset: 0x00014F00
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

	// Token: 0x17000171 RID: 369
	// (get) Token: 0x060004B6 RID: 1206 RVA: 0x00016D44 File Offset: 0x00014F44
	// (set) Token: 0x060004B7 RID: 1207 RVA: 0x00016D4C File Offset: 0x00014F4C
	internal virtual Timer Timer_1
	{
		[CompilerGenerated]
		get
		{
			return this.timer_1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_1);
			Timer timer = this.timer_1;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_1 = value;
			timer = this.timer_1;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000172 RID: 370
	// (get) Token: 0x060004B8 RID: 1208 RVA: 0x00016D90 File Offset: 0x00014F90
	// (set) Token: 0x060004B9 RID: 1209 RVA: 0x00016D98 File Offset: 0x00014F98
	internal virtual Timer Timer_2
	{
		[CompilerGenerated]
		get
		{
			return this.timer_2;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_2);
			Timer timer = this.timer_2;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_2 = value;
			timer = this.timer_2;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000173 RID: 371
	// (get) Token: 0x060004BA RID: 1210 RVA: 0x00016DDC File Offset: 0x00014FDC
	// (set) Token: 0x060004BB RID: 1211 RVA: 0x00016DE4 File Offset: 0x00014FE4
	internal virtual Timer Timer_3
	{
		[CompilerGenerated]
		get
		{
			return this.timer_3;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_3);
			Timer timer = this.timer_3;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_3 = value;
			timer = this.timer_3;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x060004BC RID: 1212
	[DllImport("user32.dll", SetLastError = true)]
	private static extern IntPtr FindWindow(string string_1, string string_2);

	// Token: 0x060004BD RID: 1213
	[DllImport("user32.dll", SetLastError = true)]
	private static extern int GetWindowLong(IntPtr intptr_1, int int_1);

	// Token: 0x060004BE RID: 1214
	[DllImport("user32.dll", SetLastError = true)]
	private static extern bool GetWindowRect(IntPtr intptr_1, out Overlay.GStruct1 gstruct1_1);

	// Token: 0x060004BF RID: 1215
	[DllImport("user32.dll")]
	private static extern int SetWindowLong(IntPtr intptr_1, int int_1, int int_2);

	// Token: 0x060004C0 RID: 1216 RVA: 0x00016E28 File Offset: 0x00015028
	private void Cpsdisplay_Load(object sender, EventArgs e)
	{
		this.Timer_1.Start();
		base.ShowInTaskbar = true;
		base.TransparencyKey = Color.Wheat;
		base.TopMost = true;
		this.Timer_2.Start();
		this.Timer_0.Enabled = true;
		base.FormBorderStyle = FormBorderStyle.None;
		int windowLong = Cpsdisplay.GetWindowLong(this.method_4(), -20);
		Cpsdisplay.SetWindowLong(this.method_4(), -20, windowLong | 524288 | 32);
		Cpsdisplay.GetWindowRect(this.intptr_0, out this.gstruct1_0);
		base.Size = checked(new Size(this.gstruct1_0.int_2 - this.gstruct1_0.int_0, this.gstruct1_0.int_3 - this.gstruct1_0.int_1));
		base.Top = this.gstruct1_0.int_1;
	}

	// Token: 0x060004C1 RID: 1217 RVA: 0x00016EFC File Offset: 0x000150FC
	private void method_0(object sender, EventArgs e)
	{
		Cpsdisplay.GetWindowRect(this.intptr_0, out this.gstruct1_0);
		base.Top = this.gstruct1_0.int_1;
		base.Left = this.gstruct1_0.int_0;
		base.Size = checked(new Size(this.gstruct1_0.int_2 - this.gstruct1_0.int_0, this.gstruct1_0.int_3 - this.gstruct1_0.int_1));
	}

	// Token: 0x060004C2 RID: 1218 RVA: 0x00016F78 File Offset: 0x00015178
	private void method_1(object sender, EventArgs e)
	{
		this.int_0 = 0;
		this.LogInLabel2.Text = Conversions.ToString(this.int_0);
	}

	// Token: 0x060004C3 RID: 1219 RVA: 0x00016F98 File Offset: 0x00015198
	private void method_2(object sender, EventArgs e)
	{
		MouseButtons mouseButtons = Control.MouseButtons;
		if (mouseButtons == MouseButtons.Left)
		{
			this.Timer_3.Start();
			this.Timer_2.Stop();
		}
	}

	// Token: 0x060004C4 RID: 1220 RVA: 0x00016FCC File Offset: 0x000151CC
	private void method_3(object sender, EventArgs e)
	{
		checked
		{
			if (Control.MouseButtons == MouseButtons.None)
			{
				this.int_0++;
				this.LogInLabel2.Text = Conversions.ToString(this.int_0);
				this.Timer_2.Start();
				this.Timer_3.Stop();
			}
		}
	}

	// Token: 0x060004C5 RID: 1221 RVA: 0x0001701C File Offset: 0x0001521C
	IntPtr method_4()
	{
		return base.Handle;
	}

	// Token: 0x04000279 RID: 633
	[AccessedThroughProperty("Timer1")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Timer timer_0;

	// Token: 0x0400027A RID: 634
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("Timer2")]
	private Timer timer_1;

	// Token: 0x0400027B RID: 635
	[AccessedThroughProperty("Timer3")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Timer timer_2;

	// Token: 0x0400027C RID: 636
	[CompilerGenerated]
	[AccessedThroughProperty("Timer4")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Timer timer_3;

	// Token: 0x0400027D RID: 637
	private Overlay.GStruct1 gstruct1_0;

	// Token: 0x0400027E RID: 638
	private int int_0;

	// Token: 0x0400027F RID: 639
	public const string string_0 = "Minecraft 1.7.10";

	// Token: 0x04000280 RID: 640
	private IntPtr intptr_0;

	// Token: 0x0200005C RID: 92
	public struct GStruct0
	{
		// Token: 0x04000281 RID: 641
		public int int_0;

		// Token: 0x04000282 RID: 642
		public int int_1;

		// Token: 0x04000283 RID: 643
		public int int_2;

		// Token: 0x04000284 RID: 644
		public int int_3;
	}
}
