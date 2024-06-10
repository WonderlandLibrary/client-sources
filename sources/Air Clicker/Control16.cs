using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000029 RID: 41
[DefaultEvent("ValueChanged")]
internal class Control16 : Control
{
	// Token: 0x14000005 RID: 5
	// (add) Token: 0x0600029B RID: 667 RVA: 0x0000C4DC File Offset: 0x0000A6DC
	// (remove) Token: 0x0600029C RID: 668 RVA: 0x0000C514 File Offset: 0x0000A714
	public event Control16.Delegate6 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control16.Delegate6 @delegate = this.delegate6_0;
			Control16.Delegate6 delegate2;
			do
			{
				delegate2 = @delegate;
				Control16.Delegate6 value2 = (Control16.Delegate6)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control16.Delegate6>(ref this.delegate6_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control16.Delegate6 @delegate = this.delegate6_0;
			Control16.Delegate6 delegate2;
			do
			{
				delegate2 = @delegate;
				Control16.Delegate6 value2 = (Control16.Delegate6)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control16.Delegate6>(ref this.delegate6_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x170000E6 RID: 230
	// (get) Token: 0x0600029D RID: 669 RVA: 0x0000C54C File Offset: 0x0000A74C
	public string String_0
	{
		get
		{
			return this.stringBuilder_0.ToString();
		}
	}

	// Token: 0x170000E7 RID: 231
	// (get) Token: 0x0600029E RID: 670 RVA: 0x0000C568 File Offset: 0x0000A768
	public string String_1
	{
		get
		{
			return BitConverter.ToString(this.byte_0).Replace("-", "");
		}
	}

	// Token: 0x0600029F RID: 671 RVA: 0x0000C594 File Offset: 0x0000A794
	public Control16()
	{
		this.stringBuilder_0 = new StringBuilder();
		this.random_0 = new Random();
		this.int_0 = 9;
		this.int_1 = 8;
		this.int_4 = -1;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.pen_0 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_1 = new Pen(Color.FromArgb(24, 24, 24));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(30, 30, 30));
	}

	// Token: 0x060002A0 RID: 672 RVA: 0x0000C630 File Offset: 0x0000A830
	protected virtual void OnHandleCreated(EventArgs e)
	{
		this.method_0();
		base.OnHandleCreated(e);
	}

	// Token: 0x060002A1 RID: 673 RVA: 0x0000C640 File Offset: 0x0000A840
	private void method_0()
	{
		checked
		{
			this.rectangle_0 = new Rectangle(5, 5, base.Width - 10, base.Height - 10);
			this.int_2 = this.rectangle_0.Width / this.int_0;
			this.int_3 = this.rectangle_0.Height / this.int_0;
			this.rectangle_0.Width = this.int_2 * this.int_0;
			this.rectangle_0.Height = this.int_3 * this.int_0;
			this.rectangle_0.X = base.Width / 2 - this.rectangle_0.Width / 2;
			this.rectangle_0.Y = base.Height / 2 - this.rectangle_0.Height / 2;
			this.byte_0 = new byte[this.int_2 * this.int_3 - 1 + 1];
			int num = this.byte_0.Length - 1;
			for (int i = 0; i <= num; i++)
			{
				this.byte_0[i] = (byte)this.random_0.Next(100);
			}
			base.Invalidate();
		}
	}

	// Token: 0x060002A2 RID: 674 RVA: 0x0000C760 File Offset: 0x0000A960
	protected virtual void OnSizeChanged(EventArgs e)
	{
		this.method_0();
	}

	// Token: 0x060002A3 RID: 675 RVA: 0x0000C768 File Offset: 0x0000A968
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		this.method_1(e);
	}

	// Token: 0x060002A4 RID: 676 RVA: 0x0000C774 File Offset: 0x0000A974
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.method_1(e);
		base.OnMouseDown(e);
	}

	// Token: 0x060002A5 RID: 677 RVA: 0x0000C784 File Offset: 0x0000A984
	private void method_1(MouseEventArgs mouseEventArgs_0)
	{
		checked
		{
			if ((mouseEventArgs_0.Button == MouseButtons.Left || mouseEventArgs_0.Button == MouseButtons.Right) && this.rectangle_0.Contains(mouseEventArgs_0.Location))
			{
				this.bool_0 = (mouseEventArgs_0.Button == MouseButtons.Right);
				this.int_4 = this.method_2(mouseEventArgs_0.X, mouseEventArgs_0.Y);
				if (this.int_4 != this.int_5)
				{
					bool flag = this.int_4 % this.int_2 != 0;
					bool flag2 = this.int_4 % this.int_2 != this.int_2 - 1;
					this.method_3(this.int_4 - this.int_2);
					if (flag)
					{
						this.method_3(this.int_4 - 1);
					}
					this.method_3(this.int_4);
					if (flag2)
					{
						this.method_3(this.int_4 + 1);
					}
					this.method_3(this.int_4 + this.int_2);
					this.stringBuilder_0.Append(this.byte_0[this.int_4].ToString("X"));
					if (this.stringBuilder_0.Length > 32)
					{
						this.stringBuilder_0.Remove(0, 2);
					}
					Control16.Delegate6 @delegate = this.delegate6_0;
					if (@delegate != null)
					{
						@delegate(this);
					}
					this.int_5 = this.int_4;
					base.Invalidate();
				}
			}
		}
	}

	// Token: 0x060002A6 RID: 678 RVA: 0x0000C8F4 File Offset: 0x0000AAF4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
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
			this.pathGradientBrush_0.FocusScales = new PointF(0.9f, 0.5f);
			Class9.graphics_0.FillPath(this.pathGradientBrush_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_1);
			Class9.graphics_0.SmoothingMode = SmoothingMode.None;
			int num = this.byte_0.Length - 1;
			for (int i = 0; i <= num; i++)
			{
				int num2 = Math.Max((int)this.byte_0[i], 75);
				int num3 = i % this.int_2 * this.int_0 + this.rectangle_0.X;
				int num4 = i / this.int_2 * this.int_0 + this.rectangle_0.Y;
				this.solidBrush_1 = new SolidBrush(Color.FromArgb(num2, num2, num2));
				Class9.graphics_0.FillRectangle(this.solidBrush_0, num3 + 1, num4 + 1, this.int_1, this.int_1);
				Class9.graphics_0.FillRectangle(this.solidBrush_1, num3, num4, this.int_1, this.int_1);
				this.solidBrush_1.Dispose();
			}
		}
	}

	// Token: 0x060002A7 RID: 679 RVA: 0x0000CADC File Offset: 0x0000ACDC
	private int method_2(int int_6, int int_7)
	{
		return checked((int_7 - this.rectangle_0.Y) / this.int_0 * this.int_2 + (int_6 - this.rectangle_0.X) / this.int_0);
	}

	// Token: 0x060002A8 RID: 680 RVA: 0x0000CB1C File Offset: 0x0000AD1C
	private void method_3(int int_6)
	{
		checked
		{
			if (int_6 > -1 && int_6 < this.byte_0.Length)
			{
				if (this.bool_0)
				{
					this.byte_0[int_6] = (byte)this.random_0.Next(100);
				}
				else
				{
					this.byte_0[int_6] = (byte)this.random_0.Next(100, 256);
				}
			}
		}
	}

	// Token: 0x0400012C RID: 300
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private Control16.Delegate6 delegate6_0;

	// Token: 0x0400012D RID: 301
	private StringBuilder stringBuilder_0;

	// Token: 0x0400012E RID: 302
	private string string_0;

	// Token: 0x0400012F RID: 303
	private Random random_0;

	// Token: 0x04000130 RID: 304
	private int int_0;

	// Token: 0x04000131 RID: 305
	private int int_1;

	// Token: 0x04000132 RID: 306
	private Rectangle rectangle_0;

	// Token: 0x04000133 RID: 307
	private int int_2;

	// Token: 0x04000134 RID: 308
	private int int_3;

	// Token: 0x04000135 RID: 309
	private byte[] byte_0;

	// Token: 0x04000136 RID: 310
	private int int_4;

	// Token: 0x04000137 RID: 311
	private int int_5;

	// Token: 0x04000138 RID: 312
	private bool bool_0;

	// Token: 0x04000139 RID: 313
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400013A RID: 314
	private GraphicsPath graphicsPath_1;

	// Token: 0x0400013B RID: 315
	private Pen pen_0;

	// Token: 0x0400013C RID: 316
	private Pen pen_1;

	// Token: 0x0400013D RID: 317
	private SolidBrush solidBrush_0;

	// Token: 0x0400013E RID: 318
	private SolidBrush solidBrush_1;

	// Token: 0x0400013F RID: 319
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x0200002A RID: 42
	// (Invoke) Token: 0x060002AC RID: 684
	public delegate void Delegate6(object sender);
}
