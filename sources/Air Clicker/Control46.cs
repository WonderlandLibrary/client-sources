using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000BD RID: 189
[DefaultEvent("CheckedChanged")]
internal class Control46 : Control
{
	// Token: 0x17000274 RID: 628
	// (get) Token: 0x0600089F RID: 2207 RVA: 0x00028EF8 File Offset: 0x000270F8
	// (set) Token: 0x060008A0 RID: 2208 RVA: 0x00028F00 File Offset: 0x00027100
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x1400001F RID: 31
	// (add) Token: 0x060008A1 RID: 2209 RVA: 0x00028F10 File Offset: 0x00027110
	// (remove) Token: 0x060008A2 RID: 2210 RVA: 0x00028F4C File Offset: 0x0002714C
	public event Control46.Delegate16 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control46.Delegate16 @delegate = this.delegate16_0;
			Control46.Delegate16 delegate2;
			do
			{
				delegate2 = @delegate;
				Control46.Delegate16 value2 = (Control46.Delegate16)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control46.Delegate16>(ref this.delegate16_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control46.Delegate16 @delegate = this.delegate16_0;
			Control46.Delegate16 delegate2;
			do
			{
				delegate2 = @delegate;
				Control46.Delegate16 value2 = (Control46.Delegate16)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control46.Delegate16>(ref this.delegate16_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x060008A3 RID: 2211 RVA: 0x00028F84 File Offset: 0x00027184
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.Location.X;
		base.Invalidate();
	}

	// Token: 0x060008A4 RID: 2212 RVA: 0x00028FB4 File Offset: 0x000271B4
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.bool_0 = !this.bool_0;
		base.Focus();
		Control46.Delegate16 @delegate = this.delegate16_0;
		if (@delegate != null)
		{
			@delegate(this);
		}
		base.OnMouseDown(e);
	}

	// Token: 0x060008A5 RID: 2213 RVA: 0x00028FF0 File Offset: 0x000271F0
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 16;
		this.graphicsPath_0 = new GraphicsPath();
		GraphicsPath graphicsPath = this.graphicsPath_0;
		graphicsPath.AddArc(0, 0, 10, 10, 180f, 90f);
		checked
		{
			graphicsPath.AddArc(base.Width - 11, 0, 10, 10, -90f, 90f);
			graphicsPath.AddArc(base.Width - 11, base.Height - 11, 10, 10, 0f, 90f);
			graphicsPath.AddArc(0, base.Height - 11, 10, 10, 90f, 90f);
			graphicsPath.CloseAllFigures();
			base.Invalidate();
		}
	}

	// Token: 0x060008A6 RID: 2214 RVA: 0x000290A4 File Offset: 0x000272A4
	public Control46()
	{
		this.bool_0 = false;
		base.Width = 148;
		base.Height = 16;
		this.Font = new Font("Microsoft Sans Serif", 9f);
		this.DoubleBuffered = true;
	}

	// Token: 0x060008A7 RID: 2215 RVA: 0x000290E4 File Offset: 0x000272E4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		graphics.Clear(base.Parent.BackColor);
		if (this.bool_0)
		{
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(66, 76, 85)), new Rectangle(0, 0, 16, 16));
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(66, 76, 85)), new Rectangle(1, 1, 14, 14));
		}
		else
		{
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(66, 76, 85)), new Rectangle(0, 0, 16, 16));
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(66, 76, 85)), new Rectangle(1, 1, 14, 14));
		}
		if (!base.Enabled)
		{
			if (this.bool_0)
			{
				graphics.DrawString("a", new Font("Marlett", 16f), new SolidBrush(Color.Gray), new Point(-5, -3));
			}
		}
		else if (this.bool_0)
		{
			graphics.DrawString("a", new Font("Marlett", 16f), new SolidBrush(Color.FromArgb(181, 41, 42)), new Point(-5, -3));
		}
		graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(116, 125, 132)), new Point(20, 0));
	}

	// Token: 0x04000425 RID: 1061
	private int int_0;

	// Token: 0x04000426 RID: 1062
	private bool bool_0;

	// Token: 0x04000427 RID: 1063
	private GraphicsPath graphicsPath_0;

	// Token: 0x04000428 RID: 1064
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control46.Delegate16 delegate16_0;

	// Token: 0x020000BE RID: 190
	// (Invoke) Token: 0x060008AB RID: 2219
	public delegate void Delegate16(object sender);
}
