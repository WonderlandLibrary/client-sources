using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x0200001B RID: 27
[DefaultEvent("CheckedChanged")]
internal class Control8 : Control
{
	// Token: 0x14000001 RID: 1
	// (add) Token: 0x0600024F RID: 591 RVA: 0x00009E14 File Offset: 0x00008014
	// (remove) Token: 0x06000250 RID: 592 RVA: 0x00009E4C File Offset: 0x0000804C
	public event Control8.Delegate2 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control8.Delegate2 @delegate = this.delegate2_0;
			Control8.Delegate2 delegate2;
			do
			{
				delegate2 = @delegate;
				Control8.Delegate2 value2 = (Control8.Delegate2)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control8.Delegate2>(ref this.delegate2_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control8.Delegate2 @delegate = this.delegate2_0;
			Control8.Delegate2 delegate2;
			do
			{
				delegate2 = @delegate;
				Control8.Delegate2 value2 = (Control8.Delegate2)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control8.Delegate2>(ref this.delegate2_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x06000251 RID: 593 RVA: 0x00009E84 File Offset: 0x00008084
	public Control8()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.pen_0 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_1 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_2 = new Pen(Color.Black, 2f);
		this.pen_3 = new Pen(Color.FromArgb(135, 169, 193), 2f);
	}

	// Token: 0x170000DC RID: 220
	// (get) Token: 0x06000252 RID: 594 RVA: 0x00009F14 File Offset: 0x00008114
	// (set) Token: 0x06000253 RID: 595 RVA: 0x00009F1C File Offset: 0x0000811C
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			Control8.Delegate2 @delegate = this.delegate2_0;
			if (@delegate != null)
			{
				@delegate(this);
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000254 RID: 596 RVA: 0x00009F48 File Offset: 0x00008148
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.graphicsPath_0 = Class9.smethod_2(0, 2, base.Height - 5, base.Height - 5, 5);
			this.graphicsPath_1 = Class9.smethod_2(1, 3, base.Height - 7, base.Height - 7, 5);
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
			if (this.bool_0)
			{
				Class9.graphics_0.DrawLine(this.pen_2, 5, base.Height - 9, 8, base.Height - 7);
				Class9.graphics_0.DrawLine(this.pen_2, 7, base.Height - 7, base.Height - 8, 7);
				Class9.graphics_0.DrawLine(this.pen_3, 4, base.Height - 10, 7, base.Height - 8);
				Class9.graphics_0.DrawLine(this.pen_3, 6, base.Height - 8, base.Height - 9, 6);
			}
			this.sizeF_0 = Class9.graphics_0.MeasureString(this.Text, this.Font);
			this.pointF_0 = new PointF((float)(base.Height - 3), unchecked((float)(base.Height / 2) - this.sizeF_0.Height / 2f));
		}
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.Black, this.pointF_0.X + 1f, this.pointF_0.Y + 1f);
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.WhiteSmoke, this.pointF_0);
	}

	// Token: 0x06000255 RID: 597 RVA: 0x0000A1A8 File Offset: 0x000083A8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.Boolean_0 = !this.Boolean_0;
	}

	// Token: 0x040000BC RID: 188
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control8.Delegate2 delegate2_0;

	// Token: 0x040000BD RID: 189
	private bool bool_0;

	// Token: 0x040000BE RID: 190
	private GraphicsPath graphicsPath_0;

	// Token: 0x040000BF RID: 191
	private GraphicsPath graphicsPath_1;

	// Token: 0x040000C0 RID: 192
	private SizeF sizeF_0;

	// Token: 0x040000C1 RID: 193
	private PointF pointF_0;

	// Token: 0x040000C2 RID: 194
	private Pen pen_0;

	// Token: 0x040000C3 RID: 195
	private Pen pen_1;

	// Token: 0x040000C4 RID: 196
	private Pen pen_2;

	// Token: 0x040000C5 RID: 197
	private Pen pen_3;

	// Token: 0x040000C6 RID: 198
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x0200001C RID: 28
	// (Invoke) Token: 0x06000259 RID: 601
	public delegate void Delegate2(object sender);
}
