using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000027 RID: 39
[DefaultEvent("Scroll")]
internal class Control15 : Control
{
	// Token: 0x14000004 RID: 4
	// (add) Token: 0x0600028A RID: 650 RVA: 0x0000BEBC File Offset: 0x0000A0BC
	// (remove) Token: 0x0600028B RID: 651 RVA: 0x0000BEF4 File Offset: 0x0000A0F4
	public event Control15.Delegate5 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control15.Delegate5 @delegate = this.delegate5_0;
			Control15.Delegate5 delegate2;
			do
			{
				delegate2 = @delegate;
				Control15.Delegate5 value2 = (Control15.Delegate5)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control15.Delegate5>(ref this.delegate5_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control15.Delegate5 @delegate = this.delegate5_0;
			Control15.Delegate5 delegate2;
			do
			{
				delegate2 = @delegate;
				Control15.Delegate5 value2 = (Control15.Delegate5)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control15.Delegate5>(ref this.delegate5_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x170000E3 RID: 227
	// (get) Token: 0x0600028C RID: 652 RVA: 0x0000BF30 File Offset: 0x0000A130
	// (set) Token: 0x0600028D RID: 653 RVA: 0x0000BF48 File Offset: 0x0000A148
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			if (value < 0)
			{
				throw new Exception("Property value is not valid.");
			}
			this.int_0 = value;
			if (value > this.int_2)
			{
				this.int_2 = value;
			}
			if (value > this.int_1)
			{
				this.int_1 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x170000E4 RID: 228
	// (get) Token: 0x0600028E RID: 654 RVA: 0x0000BF98 File Offset: 0x0000A198
	// (set) Token: 0x0600028F RID: 655 RVA: 0x0000BFB0 File Offset: 0x0000A1B0
	public int Int32_1
	{
		get
		{
			return this.int_1;
		}
		set
		{
			if (value < 0)
			{
				throw new Exception("Property value is not valid.");
			}
			this.int_1 = value;
			if (value < this.int_2)
			{
				this.int_2 = value;
			}
			if (value < this.int_0)
			{
				this.int_0 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x170000E5 RID: 229
	// (get) Token: 0x06000290 RID: 656 RVA: 0x0000C000 File Offset: 0x0000A200
	// (set) Token: 0x06000291 RID: 657 RVA: 0x0000C018 File Offset: 0x0000A218
	public int Int32_2
	{
		get
		{
			return this.int_2;
		}
		set
		{
			if (value != this.int_2)
			{
				if (value > this.int_1 || value < this.int_0)
				{
					throw new Exception("Property value is not valid.");
				}
				this.int_2 = value;
				base.Invalidate();
				Control15.Delegate5 @delegate = this.delegate5_0;
				if (@delegate != null)
				{
					@delegate(this);
				}
			}
		}
	}

	// Token: 0x06000292 RID: 658 RVA: 0x0000C074 File Offset: 0x0000A274
	public Control15()
	{
		this.int_1 = 10;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		base.Height = 17;
		this.pen_0 = new Pen(Color.FromArgb(183, 88, 206));
		this.pen_1 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_2 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_3 = new Pen(Color.FromArgb(65, 65, 65));
	}

	// Token: 0x06000293 RID: 659 RVA: 0x0000C110 File Offset: 0x0000A310
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.graphicsPath_0 = Class9.smethod_2(0, 5, base.Width - 1, 10, 5);
			this.graphicsPath_1 = Class9.smethod_2(1, 6, base.Width - 3, 8, 5);
			this.rectangle_0 = new Rectangle(0, 7, base.Width - 1, 5);
			this.linearGradientBrush_0 = new LinearGradientBrush(this.rectangle_0, Color.FromArgb(45, 45, 45), Color.FromArgb(50, 50, 50), 90f);
			this.int_3 = (int)Math.Round(unchecked(checked((double)(this.int_2 - this.int_0) / (double)(this.int_1 - this.int_0)) * (double)(checked(base.Width - 11))));
			this.rectangle_1 = new Rectangle(this.int_3, 0, 10, 20);
			Class9.graphics_0.SetClip(this.graphicsPath_1);
			Class9.graphics_0.FillRectangle(this.linearGradientBrush_0, this.rectangle_0);
			this.rectangle_2 = new Rectangle(1, 7, this.rectangle_1.X + this.rectangle_1.Width - 2, 8);
			this.linearGradientBrush_1 = new LinearGradientBrush(this.rectangle_2, Color.FromArgb(183, 88, 206), Color.FromArgb(183, 88, 206), 90f);
			Class9.graphics_0.SmoothingMode = SmoothingMode.None;
			Class9.graphics_0.FillRectangle(this.linearGradientBrush_1, this.rectangle_2);
			Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
			int num = this.rectangle_2.Width - 15;
			for (int i = 0; i <= num; i += 5)
			{
				Class9.graphics_0.DrawLine(this.pen_0, i, 0, i + 15, base.Height);
			}
			Class9.graphics_0.ResetClip();
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_2, this.graphicsPath_1);
			this.graphicsPath_2 = Class9.smethod_3(this.rectangle_1, 5);
			this.graphicsPath_3 = Class9.smethod_2(this.rectangle_1.X + 1, this.rectangle_1.Y + 1, this.rectangle_1.Width - 2, this.rectangle_1.Height - 2, 5);
			this.linearGradientBrush_2 = new LinearGradientBrush(base.ClientRectangle, Color.FromArgb(60, 60, 60), Color.FromArgb(55, 55, 55), 90f);
			Class9.graphics_0.FillPath(this.linearGradientBrush_2, this.graphicsPath_2);
			Class9.graphics_0.DrawPath(this.pen_2, this.graphicsPath_2);
			Class9.graphics_0.DrawPath(this.pen_3, this.graphicsPath_3);
		}
	}

	// Token: 0x06000294 RID: 660 RVA: 0x0000C3DC File Offset: 0x0000A5DC
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			this.int_3 = checked((int)Math.Round(unchecked(checked((double)(this.int_2 - this.int_0) / (double)(this.int_1 - this.int_0)) * (double)(checked(base.Width - 11)))));
			this.rectangle_1 = new Rectangle(this.int_3, 0, 10, 20);
			this.bool_0 = this.rectangle_1.Contains(e.Location);
		}
		base.OnMouseDown(e);
	}

	// Token: 0x06000295 RID: 661 RVA: 0x0000C460 File Offset: 0x0000A660
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		checked
		{
			if (this.bool_0 && e.X > -1 && e.X < base.Width + 1)
			{
				this.Int32_2 = this.int_0 + (int)Math.Round(unchecked((double)(checked(this.int_1 - this.int_0)) * ((double)e.X / (double)base.Width)));
			}
			base.OnMouseMove(e);
		}
	}

	// Token: 0x06000296 RID: 662 RVA: 0x0000C4CC File Offset: 0x0000A6CC
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.bool_0 = false;
		base.OnMouseUp(e);
	}

	// Token: 0x04000118 RID: 280
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control15.Delegate5 delegate5_0;

	// Token: 0x04000119 RID: 281
	private int int_0;

	// Token: 0x0400011A RID: 282
	private int int_1;

	// Token: 0x0400011B RID: 283
	private int int_2;

	// Token: 0x0400011C RID: 284
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400011D RID: 285
	private GraphicsPath graphicsPath_1;

	// Token: 0x0400011E RID: 286
	private GraphicsPath graphicsPath_2;

	// Token: 0x0400011F RID: 287
	private GraphicsPath graphicsPath_3;

	// Token: 0x04000120 RID: 288
	private Rectangle rectangle_0;

	// Token: 0x04000121 RID: 289
	private Rectangle rectangle_1;

	// Token: 0x04000122 RID: 290
	private Rectangle rectangle_2;

	// Token: 0x04000123 RID: 291
	private int int_3;

	// Token: 0x04000124 RID: 292
	private Pen pen_0;

	// Token: 0x04000125 RID: 293
	private Pen pen_1;

	// Token: 0x04000126 RID: 294
	private Pen pen_2;

	// Token: 0x04000127 RID: 295
	private Pen pen_3;

	// Token: 0x04000128 RID: 296
	private LinearGradientBrush linearGradientBrush_0;

	// Token: 0x04000129 RID: 297
	private LinearGradientBrush linearGradientBrush_1;

	// Token: 0x0400012A RID: 298
	private LinearGradientBrush linearGradientBrush_2;

	// Token: 0x0400012B RID: 299
	private bool bool_0;

	// Token: 0x02000028 RID: 40
	// (Invoke) Token: 0x0600029A RID: 666
	public delegate void Delegate5(object sender);
}
