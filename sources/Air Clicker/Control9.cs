using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x0200001D RID: 29
[DefaultEvent("CheckedChanged")]
internal class Control9 : Control
{
	// Token: 0x14000002 RID: 2
	// (add) Token: 0x0600025A RID: 602 RVA: 0x0000A1BC File Offset: 0x000083BC
	// (remove) Token: 0x0600025B RID: 603 RVA: 0x0000A1F8 File Offset: 0x000083F8
	public event Control9.Delegate3 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control9.Delegate3 @delegate = this.delegate3_0;
			Control9.Delegate3 delegate2;
			do
			{
				delegate2 = @delegate;
				Control9.Delegate3 value2 = (Control9.Delegate3)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control9.Delegate3>(ref this.delegate3_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control9.Delegate3 @delegate = this.delegate3_0;
			Control9.Delegate3 delegate2;
			do
			{
				delegate2 = @delegate;
				Control9.Delegate3 value2 = (Control9.Delegate3)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control9.Delegate3>(ref this.delegate3_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x0600025C RID: 604 RVA: 0x0000A230 File Offset: 0x00008430
	public Control9()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.pen_0 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_1 = new Pen(Color.FromArgb(24, 24, 24));
	}

	// Token: 0x170000DD RID: 221
	// (get) Token: 0x0600025D RID: 605 RVA: 0x0000A288 File Offset: 0x00008488
	// (set) Token: 0x0600025E RID: 606 RVA: 0x0000A290 File Offset: 0x00008490
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			if (this.bool_0)
			{
				this.method_0();
			}
			Control9.Delegate3 @delegate = this.delegate3_0;
			if (@delegate != null)
			{
				@delegate(this);
			}
			base.Invalidate();
		}
	}

	// Token: 0x0600025F RID: 607 RVA: 0x0000A2CC File Offset: 0x000084CC
	private void method_0()
	{
		if (base.Parent != null)
		{
			try
			{
				foreach (object obj in base.Parent.Controls)
				{
					Control control = (Control)obj;
					if (control != this && control is Control9)
					{
						((Control9)control).Boolean_0 = false;
					}
				}
			}
			finally
			{
				IEnumerator enumerator;
				if (enumerator is IDisposable)
				{
					(enumerator as IDisposable).Dispose();
				}
			}
		}
	}

	// Token: 0x06000260 RID: 608 RVA: 0x0000A350 File Offset: 0x00008550
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		this.graphicsPath_0 = new GraphicsPath();
		checked
		{
			this.graphicsPath_0.AddEllipse(0, 2, base.Height - 5, base.Height - 5);
			this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
			this.pathGradientBrush_0.CenterColor = Color.FromArgb(50, 50, 50);
			this.pathGradientBrush_0.SurroundColors = new Color[]
			{
				Color.FromArgb(45, 45, 45)
			};
			this.pathGradientBrush_0.FocusScales = new PointF(0.3f, 0.3f);
			Class9.graphics_0.FillPath(this.pathGradientBrush_0, this.graphicsPath_0);
			Class9.graphics_0.DrawEllipse(this.pen_0, 0, 2, base.Height - 5, base.Height - 5);
			Class9.graphics_0.DrawEllipse(this.pen_1, 1, 3, base.Height - 7, base.Height - 7);
			if (this.bool_0)
			{
				Class9.graphics_0.FillEllipse(Brushes.Black, 6, 8, base.Height - 15, base.Height - 15);
				Class9.graphics_0.FillEllipse(Brushes.WhiteSmoke, 5, 7, base.Height - 15, base.Height - 15);
			}
			this.sizeF_0 = Class9.graphics_0.MeasureString(this.Text, this.Font);
			this.pointF_0 = new PointF((float)(base.Height - 3), unchecked((float)(base.Height / 2) - this.sizeF_0.Height / 2f));
		}
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.Black, this.pointF_0.X + 1f, this.pointF_0.Y + 1f);
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.WhiteSmoke, this.pointF_0);
	}

	// Token: 0x06000261 RID: 609 RVA: 0x0000A56C File Offset: 0x0000876C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.Boolean_0 = true;
		base.OnMouseDown(e);
	}

	// Token: 0x040000C7 RID: 199
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Control9.Delegate3 delegate3_0;

	// Token: 0x040000C8 RID: 200
	private bool bool_0;

	// Token: 0x040000C9 RID: 201
	private GraphicsPath graphicsPath_0;

	// Token: 0x040000CA RID: 202
	private SizeF sizeF_0;

	// Token: 0x040000CB RID: 203
	private PointF pointF_0;

	// Token: 0x040000CC RID: 204
	private Pen pen_0;

	// Token: 0x040000CD RID: 205
	private Pen pen_1;

	// Token: 0x040000CE RID: 206
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x0200001E RID: 30
	// (Invoke) Token: 0x06000265 RID: 613
	public delegate void Delegate3(object sender);
}
