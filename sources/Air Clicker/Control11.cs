using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000021 RID: 33
[DefaultEvent("CheckedChanged")]
internal class Control11 : Control
{
	// Token: 0x14000003 RID: 3
	// (add) Token: 0x0600026C RID: 620 RVA: 0x0000B098 File Offset: 0x00009298
	// (remove) Token: 0x0600026D RID: 621 RVA: 0x0000B0D4 File Offset: 0x000092D4
	public event Control11.Delegate4 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control11.Delegate4 @delegate = this.delegate4_0;
			Control11.Delegate4 delegate2;
			do
			{
				delegate2 = @delegate;
				Control11.Delegate4 value2 = (Control11.Delegate4)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control11.Delegate4>(ref this.delegate4_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control11.Delegate4 @delegate = this.delegate4_0;
			Control11.Delegate4 delegate2;
			do
			{
				delegate2 = @delegate;
				Control11.Delegate4 value2 = (Control11.Delegate4)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control11.Delegate4>(ref this.delegate4_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x0600026E RID: 622 RVA: 0x0000B10C File Offset: 0x0000930C
	public Control11()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.pen_0 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_1 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_2 = new Pen(Color.FromArgb(65, 65, 65));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(24, 24, 24));
		this.solidBrush_1 = new SolidBrush(Color.FromArgb(85, 85, 85));
		this.solidBrush_2 = new SolidBrush(Color.FromArgb(65, 65, 65));
		this.solidBrush_3 = new SolidBrush(Color.FromArgb(51, 181, 229));
		this.solidBrush_4 = new SolidBrush(Color.FromArgb(40, 40, 40));
		this.stringFormat_0 = new StringFormat();
		this.stringFormat_0.LineAlignment = StringAlignment.Center;
		this.stringFormat_0.Alignment = StringAlignment.Near;
		this.stringFormat_1 = new StringFormat();
		this.stringFormat_1.LineAlignment = StringAlignment.Center;
		this.stringFormat_1.Alignment = StringAlignment.Far;
		base.Size = new Size(56, 24);
		this.MinimumSize = base.Size;
		this.MaximumSize = base.Size;
	}

	// Token: 0x170000DE RID: 222
	// (get) Token: 0x0600026F RID: 623 RVA: 0x0000B25C File Offset: 0x0000945C
	// (set) Token: 0x06000270 RID: 624 RVA: 0x0000B264 File Offset: 0x00009464
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			Control11.Delegate4 @delegate = this.delegate4_0;
			if (@delegate != null)
			{
				@delegate(this);
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000271 RID: 625 RVA: 0x0000B290 File Offset: 0x00009490
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.graphicsPath_0 = Class9.smethod_2(0, 0, base.Width - 1, base.Height - 1, 7);
			this.graphicsPath_1 = Class9.smethod_2(1, 1, base.Width - 3, base.Height - 3, 7);
			this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
			this.pathGradientBrush_0.CenterColor = Color.FromArgb(50, 50, 50);
			this.pathGradientBrush_0.SurroundColors = new Color[]
			{
				Color.FromArgb(45, 45, 45)
			};
			this.pathGradientBrush_0.FocusScales = new PointF(0.3f, 0.3f);
			Class9.graphics_0.FillPath(this.pathGradientBrush_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_1);
			this.rectangle_0 = new Rectangle(5, 0, base.Width - 10, base.Height + 2);
			this.rectangle_1 = new Rectangle(6, 1, base.Width - 10, base.Height + 2);
			this.rectangle_2 = new Rectangle(1, 1, base.Width / 2 - 1, base.Height - 3);
			if (!this.bool_0)
			{
				Class9.graphics_0.DrawString("Off", this.Font, this.solidBrush_0, this.rectangle_1, this.stringFormat_1);
				Class9.graphics_0.DrawString("Off", this.Font, this.solidBrush_1, this.rectangle_0, this.stringFormat_1);
			}
			else
			{
				Class9.graphics_0.DrawString("On", this.Font, Brushes.Black, this.rectangle_1, this.stringFormat_0);
				Class9.graphics_0.DrawString("On", this.Font, Brushes.WhiteSmoke, this.rectangle_0, this.stringFormat_0);
				ref Rectangle ptr = ref this.rectangle_2;
				this.rectangle_2.X = ptr.X + (base.Width / 2 - 1);
			}
			this.graphicsPath_2 = Class9.smethod_3(this.rectangle_2, 7);
			this.graphicsPath_3 = Class9.smethod_2(this.rectangle_2.X + 1, this.rectangle_2.Y + 1, this.rectangle_2.Width - 2, this.rectangle_2.Height - 2, 7);
			this.linearGradientBrush_0 = new LinearGradientBrush(base.ClientRectangle, Color.FromArgb(60, 60, 60), Color.FromArgb(55, 55, 55), 90f);
			Class9.graphics_0.FillPath(this.linearGradientBrush_0, this.graphicsPath_2);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_2);
			Class9.graphics_0.DrawPath(this.pen_2, this.graphicsPath_3);
			this.int_0 = this.rectangle_2.X + this.rectangle_2.Width / 2 - 3;
			int num = 0;
			do
			{
				if (this.bool_0)
				{
					Class9.graphics_0.FillRectangle(this.solidBrush_0, this.int_0 + num * 5, 7, 2, base.Height - 14);
				}
				else
				{
					Class9.graphics_0.FillRectangle(this.solidBrush_2, this.int_0 + num * 5, 7, 2, base.Height - 14);
				}
				Class9.graphics_0.SmoothingMode = SmoothingMode.None;
				if (!this.bool_0)
				{
					Class9.graphics_0.FillRectangle(this.solidBrush_4, this.int_0 + num * 5, 7, 2, base.Height - 14);
				}
				else
				{
					Class9.graphics_0.FillRectangle(this.solidBrush_3, this.int_0 + num * 5, 7, 2, base.Height - 14);
				}
				Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
				num++;
			}
			while (num <= 1);
		}
	}

	// Token: 0x06000272 RID: 626 RVA: 0x0000B69C File Offset: 0x0000989C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.Boolean_0 = !this.Boolean_0;
		base.OnMouseDown(e);
	}

	// Token: 0x040000EC RID: 236
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control11.Delegate4 delegate4_0;

	// Token: 0x040000ED RID: 237
	private bool bool_0;

	// Token: 0x040000EE RID: 238
	private GraphicsPath graphicsPath_0;

	// Token: 0x040000EF RID: 239
	private GraphicsPath graphicsPath_1;

	// Token: 0x040000F0 RID: 240
	private GraphicsPath graphicsPath_2;

	// Token: 0x040000F1 RID: 241
	private GraphicsPath graphicsPath_3;

	// Token: 0x040000F2 RID: 242
	private Pen pen_0;

	// Token: 0x040000F3 RID: 243
	private Pen pen_1;

	// Token: 0x040000F4 RID: 244
	private Pen pen_2;

	// Token: 0x040000F5 RID: 245
	private SolidBrush solidBrush_0;

	// Token: 0x040000F6 RID: 246
	private SolidBrush solidBrush_1;

	// Token: 0x040000F7 RID: 247
	private SolidBrush solidBrush_2;

	// Token: 0x040000F8 RID: 248
	private SolidBrush solidBrush_3;

	// Token: 0x040000F9 RID: 249
	private SolidBrush solidBrush_4;

	// Token: 0x040000FA RID: 250
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x040000FB RID: 251
	private LinearGradientBrush linearGradientBrush_0;

	// Token: 0x040000FC RID: 252
	private Rectangle rectangle_0;

	// Token: 0x040000FD RID: 253
	private Rectangle rectangle_1;

	// Token: 0x040000FE RID: 254
	private Rectangle rectangle_2;

	// Token: 0x040000FF RID: 255
	private StringFormat stringFormat_0;

	// Token: 0x04000100 RID: 256
	private StringFormat stringFormat_1;

	// Token: 0x04000101 RID: 257
	private int int_0;

	// Token: 0x02000022 RID: 34
	// (Invoke) Token: 0x06000276 RID: 630
	public delegate void Delegate4(object sender);
}
