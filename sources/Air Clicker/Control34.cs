using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x0200004C RID: 76
internal class Control34 : Control
{
	// Token: 0x1700013C RID: 316
	// (get) Token: 0x06000404 RID: 1028 RVA: 0x0001368C File Offset: 0x0001188C
	// (set) Token: 0x06000405 RID: 1029 RVA: 0x00013694 File Offset: 0x00011894
	private virtual Timer Timer_0
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
			EventHandler value2 = new EventHandler(this.method_1);
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

	// Token: 0x1700013D RID: 317
	// (get) Token: 0x06000406 RID: 1030 RVA: 0x000136D8 File Offset: 0x000118D8
	// (set) Token: 0x06000407 RID: 1031 RVA: 0x000136F0 File Offset: 0x000118F0
	[Category("Options")]
	public Control34.Enum6 Enum6_0
	{
		get
		{
			return this.enum6_0;
		}
		set
		{
			this.enum6_0 = value;
		}
	}

	// Token: 0x1700013E RID: 318
	// (get) Token: 0x06000408 RID: 1032 RVA: 0x000136FC File Offset: 0x000118FC
	// (set) Token: 0x06000409 RID: 1033 RVA: 0x00013714 File Offset: 0x00011914
	[Category("Options")]
	public virtual string Text
	{
		get
		{
			return this.method_2();
		}
		set
		{
			this.method_3(value);
			if (this.string_0 != null)
			{
				this.string_0 = value;
			}
		}
	}

	// Token: 0x1700013F RID: 319
	// (get) Token: 0x0600040A RID: 1034 RVA: 0x00013730 File Offset: 0x00011930
	// (set) Token: 0x0600040B RID: 1035 RVA: 0x0001373C File Offset: 0x0001193C
	[Category("Options")]
	public bool Boolean_0
	{
		get
		{
			return !base.Visible;
		}
		set
		{
			base.Visible = value;
		}
	}

	// Token: 0x0600040C RID: 1036 RVA: 0x00013748 File Offset: 0x00011948
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x0600040D RID: 1037 RVA: 0x00013758 File Offset: 0x00011958
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 42;
	}

	// Token: 0x0600040E RID: 1038 RVA: 0x0001376C File Offset: 0x0001196C
	public void method_0(Control34.Enum6 enum6_1, string string_1, int int_3)
	{
		this.enum6_0 = enum6_1;
		this.System.Windows.Forms.Control.Text = string_1;
		this.Boolean_0 = true;
		this.Timer_0 = new Timer();
		this.Timer_0.Interval = int_3;
		this.Timer_0.Enabled = true;
	}

	// Token: 0x0600040F RID: 1039 RVA: 0x000137A8 File Offset: 0x000119A8
	private void method_1(object sender, EventArgs e)
	{
		this.Boolean_0 = false;
		this.Timer_0.Enabled = false;
		this.Timer_0.Dispose();
	}

	// Token: 0x06000410 RID: 1040 RVA: 0x000137C8 File Offset: 0x000119C8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x06000411 RID: 1041 RVA: 0x000137E0 File Offset: 0x000119E0
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000412 RID: 1042 RVA: 0x000137F8 File Offset: 0x000119F8
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000413 RID: 1043 RVA: 0x00013810 File Offset: 0x00011A10
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x06000414 RID: 1044 RVA: 0x00013828 File Offset: 0x00011A28
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_2 = e.X;
		base.Invalidate();
	}

	// Token: 0x06000415 RID: 1045 RVA: 0x00013844 File Offset: 0x00011A44
	protected virtual void OnClick(EventArgs e)
	{
		base.OnClick(e);
		this.Boolean_0 = false;
	}

	// Token: 0x06000416 RID: 1046 RVA: 0x00013854 File Offset: 0x00011A54
	public Control34()
	{
		this.enum2_0 = Enum2.None;
		this.color_0 = Color.FromArgb(60, 85, 79);
		this.color_1 = Color.FromArgb(35, 169, 110);
		this.color_2 = Color.FromArgb(87, 71, 71);
		this.color_3 = Color.FromArgb(254, 142, 122);
		this.color_4 = Color.FromArgb(70, 91, 94);
		this.color_5 = Color.FromArgb(97, 185, 186);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.FromArgb(60, 70, 73);
		base.Size = new Size(576, 42);
		base.Location = new Point(10, 61);
		this.Font = new Font("Segoe UI", 10f);
		this.Cursor = Cursors.Hand;
	}

	// Token: 0x06000417 RID: 1047 RVA: 0x00013948 File Offset: 0x00011B48
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Rectangle rect = new Rectangle(0, 0, this.int_0, this.int_1);
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			switch (this.enum6_0)
			{
			case Control34.Enum6.Success:
			{
				graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
				graphics_.FillEllipse(new SolidBrush(this.color_1), new Rectangle(8, 9, 24, 24));
				graphics_.FillEllipse(new SolidBrush(this.color_0), new Rectangle(10, 11, 20, 20));
				graphics_.DrawString("ü", new Font("Wingdings", 22f), new SolidBrush(this.color_1), new Rectangle(7, 7, this.int_0, this.int_1), Class16.stringFormat_0);
				graphics_.DrawString(this.System.Windows.Forms.Control.Text, this.Font, new SolidBrush(this.color_1), new Rectangle(48, 12, this.int_0, this.int_1), Class16.stringFormat_0);
				graphics_.FillEllipse(new SolidBrush(Color.FromArgb(35, Color.Black)), new Rectangle(this.int_0 - 30, this.int_1 - 29, 17, 17));
				graphics_.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.color_0), new Rectangle(this.int_0 - 28, 16, this.int_0, this.int_1), Class16.stringFormat_0);
				Enum2 @enum = this.enum2_0;
				if (@enum == Enum2.Over)
				{
					graphics_.DrawString("r", new Font("Marlett", 8f), new SolidBrush(Color.FromArgb(25, Color.White)), new Rectangle(this.int_0 - 28, 16, this.int_0, this.int_1), Class16.stringFormat_0);
				}
				break;
			}
			case Control34.Enum6.Error:
			{
				graphics_.FillRectangle(new SolidBrush(this.color_2), rect);
				graphics_.FillEllipse(new SolidBrush(this.color_3), new Rectangle(8, 9, 24, 24));
				graphics_.FillEllipse(new SolidBrush(this.color_2), new Rectangle(10, 11, 20, 20));
				graphics_.DrawString("r", new Font("Marlett", 16f), new SolidBrush(this.color_3), new Rectangle(6, 11, this.int_0, this.int_1), Class16.stringFormat_0);
				graphics_.DrawString(this.System.Windows.Forms.Control.Text, this.Font, new SolidBrush(this.color_3), new Rectangle(48, 12, this.int_0, this.int_1), Class16.stringFormat_0);
				graphics_.FillEllipse(new SolidBrush(Color.FromArgb(35, Color.Black)), new Rectangle(this.int_0 - 32, this.int_1 - 29, 17, 17));
				graphics_.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.color_2), new Rectangle(this.int_0 - 30, 17, this.int_0, this.int_1), Class16.stringFormat_0);
				Enum2 enum2 = this.enum2_0;
				if (enum2 == Enum2.Over)
				{
					graphics_.DrawString("r", new Font("Marlett", 8f), new SolidBrush(Color.FromArgb(25, Color.White)), new Rectangle(this.int_0 - 30, 15, this.int_0, this.int_1), Class16.stringFormat_0);
				}
				break;
			}
			case Control34.Enum6.Info:
			{
				graphics_.FillRectangle(new SolidBrush(this.color_4), rect);
				graphics_.FillEllipse(new SolidBrush(this.color_5), new Rectangle(8, 9, 24, 24));
				graphics_.FillEllipse(new SolidBrush(this.color_4), new Rectangle(10, 11, 20, 20));
				graphics_.DrawString("¡", new Font("Segoe UI", 20f, FontStyle.Bold), new SolidBrush(this.color_5), new Rectangle(12, -4, this.int_0, this.int_1), Class16.stringFormat_0);
				graphics_.DrawString(this.System.Windows.Forms.Control.Text, this.Font, new SolidBrush(this.color_5), new Rectangle(48, 12, this.int_0, this.int_1), Class16.stringFormat_0);
				graphics_.FillEllipse(new SolidBrush(Color.FromArgb(35, Color.Black)), new Rectangle(this.int_0 - 32, this.int_1 - 29, 17, 17));
				graphics_.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.color_4), new Rectangle(this.int_0 - 30, 17, this.int_0, this.int_1), Class16.stringFormat_0);
				Enum2 enum3 = this.enum2_0;
				if (enum3 == Enum2.Over)
				{
					graphics_.DrawString("r", new Font("Marlett", 8f), new SolidBrush(Color.FromArgb(25, Color.White)), new Rectangle(this.int_0 - 30, 17, this.int_0, this.int_1), Class16.stringFormat_0);
				}
				break;
			}
			}
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x06000418 RID: 1048 RVA: 0x00013F2C File Offset: 0x0001212C
	string method_2()
	{
		return base.Text;
	}

	// Token: 0x06000419 RID: 1049 RVA: 0x00013F34 File Offset: 0x00012134
	void method_3(string string_1)
	{
		base.Text = string_1;
	}

	// Token: 0x04000221 RID: 545
	private int int_0;

	// Token: 0x04000222 RID: 546
	private int int_1;

	// Token: 0x04000223 RID: 547
	private Control34.Enum6 enum6_0;

	// Token: 0x04000224 RID: 548
	private string string_0;

	// Token: 0x04000225 RID: 549
	private Enum2 enum2_0;

	// Token: 0x04000226 RID: 550
	private int int_2;

	// Token: 0x04000227 RID: 551
	[AccessedThroughProperty("T")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Timer timer_0;

	// Token: 0x04000228 RID: 552
	private Color color_0;

	// Token: 0x04000229 RID: 553
	private Color color_1;

	// Token: 0x0400022A RID: 554
	private Color color_2;

	// Token: 0x0400022B RID: 555
	private Color color_3;

	// Token: 0x0400022C RID: 556
	private Color color_4;

	// Token: 0x0400022D RID: 557
	private Color color_5;

	// Token: 0x0200004D RID: 77
	[Flags]
	public enum Enum6
	{
		// Token: 0x0400022F RID: 559
		Success = 0,
		// Token: 0x04000230 RID: 560
		Error = 1,
		// Token: 0x04000231 RID: 561
		Info = 2
	}
}
