using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000030 RID: 48
[DefaultEvent("Scroll")]
internal class Control20 : Control
{
	// Token: 0x14000008 RID: 8
	// (add) Token: 0x060002E7 RID: 743 RVA: 0x0000E608 File Offset: 0x0000C808
	// (remove) Token: 0x060002E8 RID: 744 RVA: 0x0000E644 File Offset: 0x0000C844
	public event Control20.Delegate9 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control20.Delegate9 @delegate = this.delegate9_0;
			Control20.Delegate9 delegate2;
			do
			{
				delegate2 = @delegate;
				Control20.Delegate9 value2 = (Control20.Delegate9)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control20.Delegate9>(ref this.delegate9_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control20.Delegate9 @delegate = this.delegate9_0;
			Control20.Delegate9 delegate2;
			do
			{
				delegate2 = @delegate;
				Control20.Delegate9 value2 = (Control20.Delegate9)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control20.Delegate9>(ref this.delegate9_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x170000F4 RID: 244
	// (get) Token: 0x060002E9 RID: 745 RVA: 0x0000E67C File Offset: 0x0000C87C
	// (set) Token: 0x060002EA RID: 746 RVA: 0x0000E694 File Offset: 0x0000C894
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
			this.method_1();
		}
	}

	// Token: 0x170000F5 RID: 245
	// (get) Token: 0x060002EB RID: 747 RVA: 0x0000E6E4 File Offset: 0x0000C8E4
	// (set) Token: 0x060002EC RID: 748 RVA: 0x0000E6FC File Offset: 0x0000C8FC
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
			this.method_1();
		}
	}

	// Token: 0x170000F6 RID: 246
	// (get) Token: 0x060002ED RID: 749 RVA: 0x0000E74C File Offset: 0x0000C94C
	// (set) Token: 0x060002EE RID: 750 RVA: 0x0000E778 File Offset: 0x0000C978
	public int Int32_2
	{
		get
		{
			int result;
			if (this.bool_0)
			{
				result = this.int_2;
			}
			else
			{
				result = this.int_0;
			}
			return result;
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
				this.method_2();
				Control20.Delegate9 @delegate = this.delegate9_0;
				if (@delegate != null)
				{
					@delegate(this);
				}
			}
		}
	}

	// Token: 0x170000F7 RID: 247
	// (get) Token: 0x060002EF RID: 751 RVA: 0x0000E7D4 File Offset: 0x0000C9D4
	// (set) Token: 0x060002F0 RID: 752 RVA: 0x0000E7EC File Offset: 0x0000C9EC
	public int Int32_3
	{
		get
		{
			return this.int_3;
		}
		set
		{
			if (value < 1)
			{
				throw new Exception("Property value is not valid.");
			}
			this.int_3 = value;
		}
	}

	// Token: 0x170000F8 RID: 248
	// (get) Token: 0x060002F1 RID: 753 RVA: 0x0000E808 File Offset: 0x0000CA08
	// (set) Token: 0x060002F2 RID: 754 RVA: 0x0000E820 File Offset: 0x0000CA20
	public int Int32_4
	{
		get
		{
			return this.int_4;
		}
		set
		{
			if (value < 1)
			{
				throw new Exception("Property value is not valid.");
			}
			this.int_4 = value;
		}
	}

	// Token: 0x060002F3 RID: 755 RVA: 0x0000E83C File Offset: 0x0000CA3C
	public Control20()
	{
		this.int_1 = 100;
		this.int_3 = 1;
		this.int_4 = 10;
		this.int_5 = 16;
		this.int_6 = 24;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		base.Height = 18;
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(55, 55, 55));
		this.solidBrush_1 = new SolidBrush(Color.FromArgb(24, 24, 24));
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(65, 65, 65));
		this.pen_2 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_3 = new Pen(Color.FromArgb(40, 40, 40));
	}

	// Token: 0x060002F4 RID: 756 RVA: 0x0000E91C File Offset: 0x0000CB1C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.Clear(this.BackColor);
		this.graphicsPath_0 = this.method_0(6, 4, false);
		this.graphicsPath_1 = this.method_0(7, 5, false);
		Class9.graphics_0.FillPath(this.solidBrush_0, this.graphicsPath_1);
		Class9.graphics_0.FillPath(this.solidBrush_1, this.graphicsPath_0);
		checked
		{
			this.graphicsPath_2 = this.method_0(base.Width - 11, 4, true);
			this.graphicsPath_3 = this.method_0(base.Width - 10, 5, true);
			Class9.graphics_0.FillPath(this.solidBrush_0, this.graphicsPath_3);
			Class9.graphics_0.FillPath(this.solidBrush_1, this.graphicsPath_2);
			if (this.bool_0)
			{
				Class9.graphics_0.FillRectangle(this.solidBrush_0, this.rectangle_3);
				Class9.graphics_0.DrawRectangle(this.pen_0, this.rectangle_3);
				Class9.graphics_0.DrawRectangle(this.pen_1, this.rectangle_3.X + 1, this.rectangle_3.Y + 1, this.rectangle_3.Width - 2, this.rectangle_3.Height - 2);
				int num = this.rectangle_3.X + this.rectangle_3.Width / 2 - 3;
				int num2 = 0;
				do
				{
					int num3 = num + num2 * 3;
					Class9.graphics_0.DrawLine(this.pen_0, num3, this.rectangle_3.Y + 5, num3, this.rectangle_3.Bottom - 5);
					Class9.graphics_0.DrawLine(this.pen_1, num3 + 1, this.rectangle_3.Y + 5, num3 + 1, this.rectangle_3.Bottom - 5);
					num2++;
				}
				while (num2 <= 2);
			}
			Class9.graphics_0.DrawRectangle(this.pen_2, 0, 0, base.Width - 1, base.Height - 1);
			Class9.graphics_0.DrawRectangle(this.pen_3, 1, 1, base.Width - 3, base.Height - 3);
		}
	}

	// Token: 0x060002F5 RID: 757 RVA: 0x0000EB34 File Offset: 0x0000CD34
	private GraphicsPath method_0(int int_8, int int_9, bool bool_2)
	{
		GraphicsPath graphicsPath = new GraphicsPath();
		int num = 5;
		int num2 = 9;
		checked
		{
			if (!bool_2)
			{
				graphicsPath.AddLine(int_8 + num, int_9, int_8 + num, int_9 + num2);
				graphicsPath.AddLine(int_8 + num, int_9 + num2, int_8 + 1, int_9 + num);
			}
			else
			{
				graphicsPath.AddLine(int_8, int_9 + 1, int_8, int_9 + num2 + 1);
				graphicsPath.AddLine(int_8, int_9 + num2, int_8 + num - 1, int_9 + num);
			}
			graphicsPath.CloseFigure();
			return graphicsPath;
		}
	}

	// Token: 0x060002F6 RID: 758 RVA: 0x0000EBA0 File Offset: 0x0000CDA0
	protected virtual void OnSizeChanged(EventArgs e)
	{
		this.method_1();
	}

	// Token: 0x060002F7 RID: 759 RVA: 0x0000EBA8 File Offset: 0x0000CDA8
	private void method_1()
	{
		this.rectangle_0 = new Rectangle(0, 0, this.int_5, base.Height);
		checked
		{
			this.rectangle_1 = new Rectangle(base.Width - this.int_5, 0, this.int_5, base.Height);
			this.rectangle_2 = new Rectangle(this.rectangle_0.Right + 1, 0, base.Width - this.int_5 * 2 - 1, base.Height);
			this.bool_0 = (this.int_1 - this.int_0 > this.rectangle_2.Width);
			if (this.bool_0)
			{
				this.rectangle_3 = new Rectangle(0, 1, this.int_6, base.Height - 3);
			}
			Control20.Delegate9 @delegate = this.delegate9_0;
			if (@delegate != null)
			{
				@delegate(this);
			}
			this.method_2();
		}
	}

	// Token: 0x060002F8 RID: 760 RVA: 0x0000EC80 File Offset: 0x0000CE80
	private void method_2()
	{
		this.rectangle_3.X = checked((int)Math.Round(unchecked(this.method_3() * (double)(checked(this.rectangle_2.Width - this.int_6)))) + this.rectangle_0.Width);
		base.Invalidate();
	}

	// Token: 0x060002F9 RID: 761 RVA: 0x0000ECC0 File Offset: 0x0000CEC0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		checked
		{
			if (e.Button == MouseButtons.Left && this.bool_0)
			{
				if (this.rectangle_0.Contains(e.Location))
				{
					this.int_7 = this.int_2 - this.int_3;
				}
				else if (this.rectangle_1.Contains(e.Location))
				{
					this.int_7 = this.int_2 + this.int_3;
				}
				else
				{
					if (this.rectangle_3.Contains(e.Location))
					{
						this.bool_1 = true;
						base.OnMouseDown(e);
						return;
					}
					if (e.X >= this.rectangle_3.X)
					{
						this.int_7 = this.int_2 + this.int_4;
					}
					else
					{
						this.int_7 = this.int_2 - this.int_4;
					}
				}
				this.Int32_2 = Math.Min(Math.Max(this.int_7, this.int_0), this.int_1);
				this.method_2();
			}
			base.OnMouseDown(e);
		}
	}

	// Token: 0x060002FA RID: 762 RVA: 0x0000EDCC File Offset: 0x0000CFCC
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		checked
		{
			if (this.bool_1 && this.bool_0)
			{
				int num = e.X - this.rectangle_0.Width - this.int_6 / 2;
				int num2 = this.rectangle_2.Width - this.int_6;
				this.int_7 = (int)Math.Round(unchecked((double)num / (double)num2 * (double)(checked(this.int_1 - this.int_0)))) + this.int_0;
				this.Int32_2 = Math.Min(Math.Max(this.int_7, this.int_0), this.int_1);
				this.method_2();
			}
			base.OnMouseMove(e);
		}
	}

	// Token: 0x060002FB RID: 763 RVA: 0x0000EE74 File Offset: 0x0000D074
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.bool_1 = false;
		base.OnMouseUp(e);
	}

	// Token: 0x060002FC RID: 764 RVA: 0x0000EE84 File Offset: 0x0000D084
	private double method_3()
	{
		return checked((double)(this.int_2 - this.int_0) / (double)(this.int_1 - this.int_0));
	}

	// Token: 0x04000180 RID: 384
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Control20.Delegate9 delegate9_0;

	// Token: 0x04000181 RID: 385
	private int int_0;

	// Token: 0x04000182 RID: 386
	private int int_1;

	// Token: 0x04000183 RID: 387
	private int int_2;

	// Token: 0x04000184 RID: 388
	private int int_3;

	// Token: 0x04000185 RID: 389
	private int int_4;

	// Token: 0x04000186 RID: 390
	private int int_5;

	// Token: 0x04000187 RID: 391
	private int int_6;

	// Token: 0x04000188 RID: 392
	private Rectangle rectangle_0;

	// Token: 0x04000189 RID: 393
	private Rectangle rectangle_1;

	// Token: 0x0400018A RID: 394
	private Rectangle rectangle_2;

	// Token: 0x0400018B RID: 395
	private Rectangle rectangle_3;

	// Token: 0x0400018C RID: 396
	private bool bool_0;

	// Token: 0x0400018D RID: 397
	private bool bool_1;

	// Token: 0x0400018E RID: 398
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400018F RID: 399
	private GraphicsPath graphicsPath_1;

	// Token: 0x04000190 RID: 400
	private GraphicsPath graphicsPath_2;

	// Token: 0x04000191 RID: 401
	private GraphicsPath graphicsPath_3;

	// Token: 0x04000192 RID: 402
	private Pen pen_0;

	// Token: 0x04000193 RID: 403
	private Pen pen_1;

	// Token: 0x04000194 RID: 404
	private Pen pen_2;

	// Token: 0x04000195 RID: 405
	private Pen pen_3;

	// Token: 0x04000196 RID: 406
	private SolidBrush solidBrush_0;

	// Token: 0x04000197 RID: 407
	private SolidBrush solidBrush_1;

	// Token: 0x04000198 RID: 408
	private int int_7;

	// Token: 0x02000031 RID: 49
	// (Invoke) Token: 0x06000300 RID: 768
	public delegate void Delegate9(object sender);
}
