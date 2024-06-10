using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000078 RID: 120
[DesignerGenerated]
public partial class hitmarker : Form
{
	// Token: 0x06000576 RID: 1398 RVA: 0x0001C518 File Offset: 0x0001A718
	public hitmarker()
	{
		base.Load += this.hitmarker_Load;
		this.InitializeComponent();
	}

	// Token: 0x06000577 RID: 1399 RVA: 0x0001C538 File Offset: 0x0001A738
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

	// Token: 0x17000194 RID: 404
	// (get) Token: 0x06000579 RID: 1401 RVA: 0x0001C808 File Offset: 0x0001AA08
	// (set) Token: 0x0600057A RID: 1402 RVA: 0x0001C810 File Offset: 0x0001AA10
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

	// Token: 0x17000195 RID: 405
	// (get) Token: 0x0600057B RID: 1403 RVA: 0x0001C854 File Offset: 0x0001AA54
	// (set) Token: 0x0600057C RID: 1404 RVA: 0x0001C85C File Offset: 0x0001AA5C
	internal virtual Control27 FlatGroupBox1
	{
		[CompilerGenerated]
		get
		{
			return this._FlatGroupBox1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			MouseEventHandler value2 = new MouseEventHandler(this.method_1);
			Control27 flatGroupBox = this._FlatGroupBox1;
			if (flatGroupBox != null)
			{
				flatGroupBox.MouseMove -= value2;
			}
			this._FlatGroupBox1 = value;
			flatGroupBox = this._FlatGroupBox1;
			if (flatGroupBox != null)
			{
				flatGroupBox.MouseMove += value2;
			}
		}
	}

	// Token: 0x17000196 RID: 406
	// (get) Token: 0x0600057D RID: 1405 RVA: 0x0001C8A0 File Offset: 0x0001AAA0
	// (set) Token: 0x0600057E RID: 1406 RVA: 0x0001C8A8 File Offset: 0x0001AAA8
	internal virtual GControl28 LogInGroupBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x0600057F RID: 1407 RVA: 0x0001C8B4 File Offset: 0x0001AAB4
	private void hitmarker_Load(object sender, EventArgs e)
	{
		this.Timer_0.Start();
		base.TopMost = true;
	}

	// Token: 0x06000580 RID: 1408 RVA: 0x0001C8C8 File Offset: 0x0001AAC8
	private void method_0(object sender, EventArgs e)
	{
		base.Location = Class1.Class2_0.Overlay_0.Location;
		base.Size = Class1.Class2_0.Overlay_0.Size;
	}

	// Token: 0x06000581 RID: 1409 RVA: 0x0001C8F4 File Offset: 0x0001AAF4
	private void method_1(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			Control27 flatGroupBox;
			(flatGroupBox = this.FlatGroupBox1).Location = flatGroupBox.Location + checked(new Size(e.X - this.point_0.X, e.Y - this.point_0.Y));
		}
		else
		{
			this.point_0 = e.Location;
		}
	}

	// Token: 0x040002C4 RID: 708
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("Timer1")]
	private Timer timer_0;

	// Token: 0x040002C7 RID: 711
	private Point point_0;
}
