using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x0200002E RID: 46
[DefaultEvent("Scroll")]
internal class Control19 : Control
{
	// Token: 0x14000007 RID: 7
	// (add) Token: 0x060002CA RID: 714 RVA: 0x0000DD3C File Offset: 0x0000BF3C
	// (remove) Token: 0x060002CB RID: 715 RVA: 0x0000DD74 File Offset: 0x0000BF74
	public event Control19.Delegate8 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control19.Delegate8 @delegate = this.delegate8_0;
			Control19.Delegate8 delegate2;
			do
			{
				delegate2 = @delegate;
				Control19.Delegate8 value2 = (Control19.Delegate8)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control19.Delegate8>(ref this.delegate8_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control19.Delegate8 @delegate = this.delegate8_0;
			Control19.Delegate8 delegate2;
			do
			{
				delegate2 = @delegate;
				Control19.Delegate8 value2 = (Control19.Delegate8)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control19.Delegate8>(ref this.delegate8_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x170000ED RID: 237
	// (get) Token: 0x060002CC RID: 716 RVA: 0x0000DDAC File Offset: 0x0000BFAC
	// (set) Token: 0x060002CD RID: 717 RVA: 0x0000DDC4 File Offset: 0x0000BFC4
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

	// Token: 0x170000EE RID: 238
	// (get) Token: 0x060002CE RID: 718 RVA: 0x0000DE14 File Offset: 0x0000C014
	// (set) Token: 0x060002CF RID: 719 RVA: 0x0000DE2C File Offset: 0x0000C02C
	public int Int32_1
	{
		get
		{
			return this.int_1;
		}
		set
		{
			if (value < 1)
			{
				value = 1;
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

	// Token: 0x170000EF RID: 239
	// (get) Token: 0x060002D0 RID: 720 RVA: 0x0000DE68 File Offset: 0x0000C068
	// (set) Token: 0x060002D1 RID: 721 RVA: 0x0000DE94 File Offset: 0x0000C094
	public int Int32_2
	{
		get
		{
			int result;
			if (!this.bool_0)
			{
				result = this.int_0;
			}
			else
			{
				result = this.int_2;
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
				Control19.Delegate8 @delegate = this.delegate8_0;
				if (@delegate != null)
				{
					@delegate(this);
				}
			}
		}
	}

	// Token: 0x170000F0 RID: 240
	// (get) Token: 0x060002D2 RID: 722 RVA: 0x0000DEF0 File Offset: 0x0000C0F0
	// (set) Token: 0x060002D3 RID: 723 RVA: 0x0000DEF8 File Offset: 0x0000C0F8
	public double Double_0 { get; set; }

	// Token: 0x170000F1 RID: 241
	// (get) Token: 0x060002D4 RID: 724 RVA: 0x0000DF04 File Offset: 0x0000C104
	public double Double_1
	{
		get
		{
			double result;
			if (this.bool_0)
			{
				result = this.method_3();
			}
			else
			{
				result = 0.0;
			}
			return result;
		}
	}

	// Token: 0x170000F2 RID: 242
	// (get) Token: 0x060002D5 RID: 725 RVA: 0x0000DF30 File Offset: 0x0000C130
	// (set) Token: 0x060002D6 RID: 726 RVA: 0x0000DF48 File Offset: 0x0000C148
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

	// Token: 0x170000F3 RID: 243
	// (get) Token: 0x060002D7 RID: 727 RVA: 0x0000DF64 File Offset: 0x0000C164
	// (set) Token: 0x060002D8 RID: 728 RVA: 0x0000DF7C File Offset: 0x0000C17C
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

	// Token: 0x060002D9 RID: 729 RVA: 0x0000DF98 File Offset: 0x0000C198
	public Control19()
	{
		this.int_1 = 100;
		this.int_3 = 1;
		this.int_4 = 10;
		this.int_5 = 16;
		this.int_6 = 24;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		base.Width = 18;
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(55, 55, 55));
		this.solidBrush_1 = new SolidBrush(Color.FromArgb(24, 24, 24));
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(65, 65, 65));
		this.pen_2 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_3 = new Pen(Color.FromArgb(40, 40, 40));
	}

	// Token: 0x060002DA RID: 730 RVA: 0x0000E078 File Offset: 0x0000C278
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.Clear(this.BackColor);
		this.graphicsPath_0 = this.method_0(4, 6, false);
		this.graphicsPath_1 = this.method_0(5, 7, false);
		Class9.graphics_0.FillPath(this.solidBrush_0, this.graphicsPath_1);
		Class9.graphics_0.FillPath(this.solidBrush_1, this.graphicsPath_0);
		checked
		{
			this.graphicsPath_2 = this.method_0(4, base.Height - 11, true);
			this.graphicsPath_3 = this.method_0(5, base.Height - 10, true);
			Class9.graphics_0.FillPath(this.solidBrush_0, this.graphicsPath_3);
			Class9.graphics_0.FillPath(this.solidBrush_1, this.graphicsPath_2);
			if (this.bool_0)
			{
				Class9.graphics_0.FillRectangle(this.solidBrush_0, this.rectangle_3);
				Class9.graphics_0.DrawRectangle(this.pen_0, this.rectangle_3);
				Class9.graphics_0.DrawRectangle(this.pen_1, this.rectangle_3.X + 1, this.rectangle_3.Y + 1, this.rectangle_3.Width - 2, this.rectangle_3.Height - 2);
				int num = this.rectangle_3.Y + this.rectangle_3.Height / 2 - 3;
				int num2 = 0;
				do
				{
					int num3 = num + num2 * 3;
					Class9.graphics_0.DrawLine(this.pen_0, this.rectangle_3.X + 5, num3, this.rectangle_3.Right - 5, num3);
					Class9.graphics_0.DrawLine(this.pen_1, this.rectangle_3.X + 5, num3 + 1, this.rectangle_3.Right - 5, num3 + 1);
					num2++;
				}
				while (num2 <= 2);
			}
			Class9.graphics_0.DrawRectangle(this.pen_2, 0, 0, base.Width - 1, base.Height - 1);
			Class9.graphics_0.DrawRectangle(this.pen_3, 1, 1, base.Width - 3, base.Height - 3);
		}
	}

	// Token: 0x060002DB RID: 731 RVA: 0x0000E290 File Offset: 0x0000C490
	private GraphicsPath method_0(int int_8, int int_9, bool bool_2)
	{
		GraphicsPath graphicsPath = new GraphicsPath();
		int num = 9;
		int num2 = 5;
		checked
		{
			if (bool_2)
			{
				graphicsPath.AddLine(int_8 + 1, int_9, int_8 + num + 1, int_9);
				graphicsPath.AddLine(int_8 + num, int_9, int_8 + num2, int_9 + num2 - 1);
			}
			else
			{
				graphicsPath.AddLine(int_8, int_9 + num2, int_8 + num, int_9 + num2);
				graphicsPath.AddLine(int_8 + num, int_9 + num2, int_8 + num2, int_9);
			}
			graphicsPath.CloseFigure();
			return graphicsPath;
		}
	}

	// Token: 0x060002DC RID: 732 RVA: 0x0000E2F8 File Offset: 0x0000C4F8
	protected virtual void OnSizeChanged(EventArgs e)
	{
		this.method_1();
	}

	// Token: 0x060002DD RID: 733 RVA: 0x0000E300 File Offset: 0x0000C500
	private void method_1()
	{
		this.rectangle_0 = new Rectangle(0, 0, base.Width, this.int_5);
		checked
		{
			this.rectangle_1 = new Rectangle(0, base.Height - this.int_5, base.Width, this.int_5);
			this.rectangle_2 = new Rectangle(0, this.rectangle_0.Bottom + 1, base.Width, base.Height - this.int_5 * 2 - 1);
			this.bool_0 = (this.int_1 - this.int_0 > this.rectangle_2.Height);
			if (this.bool_0)
			{
				this.rectangle_3 = new Rectangle(1, 0, base.Width - 3, this.int_6);
			}
			Control19.Delegate8 @delegate = this.delegate8_0;
			if (@delegate != null)
			{
				@delegate(this);
			}
			this.method_2();
		}
	}

	// Token: 0x060002DE RID: 734 RVA: 0x0000E3D8 File Offset: 0x0000C5D8
	private void method_2()
	{
		this.rectangle_3.Y = checked((int)Math.Round(unchecked(this.method_3() * (double)(checked(this.rectangle_2.Height - this.int_6)))) + this.rectangle_0.Height);
		base.Invalidate();
	}

	// Token: 0x060002DF RID: 735 RVA: 0x0000E418 File Offset: 0x0000C618
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		checked
		{
			if (e.Button == MouseButtons.Left && this.bool_0)
			{
				if (!this.rectangle_0.Contains(e.Location))
				{
					if (!this.rectangle_1.Contains(e.Location))
					{
						if (this.rectangle_3.Contains(e.Location))
						{
							this.bool_1 = true;
							base.OnMouseDown(e);
							return;
						}
						if (e.Y < this.rectangle_3.Y)
						{
							this.int_7 = this.int_2 - this.int_4;
						}
						else
						{
							this.int_7 = this.int_2 + this.int_4;
						}
					}
					else
					{
						this.int_7 = this.int_2 + this.int_3;
					}
				}
				else
				{
					this.int_7 = this.int_2 - this.int_3;
				}
				this.Int32_2 = Math.Min(Math.Max(this.int_7, this.int_0), this.int_1);
				this.method_2();
			}
			base.OnMouseDown(e);
		}
	}

	// Token: 0x060002E0 RID: 736 RVA: 0x0000E524 File Offset: 0x0000C724
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		checked
		{
			if (this.bool_1 && this.bool_0)
			{
				int num = e.Y - this.rectangle_0.Height - this.int_6 / 2;
				int num2 = this.rectangle_2.Height - this.int_6;
				this.int_7 = (int)Math.Round(unchecked((double)num / (double)num2 * (double)(checked(this.int_1 - this.int_0)))) + this.int_0;
				this.Int32_2 = Math.Min(Math.Max(this.int_7, this.int_0), this.int_1);
				this.method_2();
			}
			base.OnMouseMove(e);
		}
	}

	// Token: 0x060002E1 RID: 737 RVA: 0x0000E5CC File Offset: 0x0000C7CC
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.bool_1 = false;
		base.OnMouseUp(e);
	}

	// Token: 0x060002E2 RID: 738 RVA: 0x0000E5DC File Offset: 0x0000C7DC
	private double method_3()
	{
		return checked((double)(this.int_2 - this.int_0) / (double)(this.int_1 - this.int_0));
	}

	// Token: 0x04000166 RID: 358
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control19.Delegate8 delegate8_0;

	// Token: 0x04000167 RID: 359
	private int int_0;

	// Token: 0x04000168 RID: 360
	private int int_1;

	// Token: 0x04000169 RID: 361
	private int int_2;

	// Token: 0x0400016A RID: 362
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private double double_0;

	// Token: 0x0400016B RID: 363
	private int int_3;

	// Token: 0x0400016C RID: 364
	private int int_4;

	// Token: 0x0400016D RID: 365
	private int int_5;

	// Token: 0x0400016E RID: 366
	private int int_6;

	// Token: 0x0400016F RID: 367
	private Rectangle rectangle_0;

	// Token: 0x04000170 RID: 368
	private Rectangle rectangle_1;

	// Token: 0x04000171 RID: 369
	private Rectangle rectangle_2;

	// Token: 0x04000172 RID: 370
	private Rectangle rectangle_3;

	// Token: 0x04000173 RID: 371
	private bool bool_0;

	// Token: 0x04000174 RID: 372
	private bool bool_1;

	// Token: 0x04000175 RID: 373
	private GraphicsPath graphicsPath_0;

	// Token: 0x04000176 RID: 374
	private GraphicsPath graphicsPath_1;

	// Token: 0x04000177 RID: 375
	private GraphicsPath graphicsPath_2;

	// Token: 0x04000178 RID: 376
	private GraphicsPath graphicsPath_3;

	// Token: 0x04000179 RID: 377
	private Pen pen_0;

	// Token: 0x0400017A RID: 378
	private Pen pen_1;

	// Token: 0x0400017B RID: 379
	private Pen pen_2;

	// Token: 0x0400017C RID: 380
	private Pen pen_3;

	// Token: 0x0400017D RID: 381
	private SolidBrush solidBrush_0;

	// Token: 0x0400017E RID: 382
	private SolidBrush solidBrush_1;

	// Token: 0x0400017F RID: 383
	private int int_7;

	// Token: 0x0200002F RID: 47
	// (Invoke) Token: 0x060002E6 RID: 742
	public delegate void Delegate8(object sender);
}
