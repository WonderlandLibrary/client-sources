using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200002C RID: 44
[DefaultEvent("SelectedIndexChanged")]
internal class Control18 : Control
{
	// Token: 0x14000006 RID: 6
	// (add) Token: 0x060002B6 RID: 694 RVA: 0x0000D680 File Offset: 0x0000B880
	// (remove) Token: 0x060002B7 RID: 695 RVA: 0x0000D6B8 File Offset: 0x0000B8B8
	public event Control18.Delegate7 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control18.Delegate7 @delegate = this.delegate7_0;
			Control18.Delegate7 delegate2;
			do
			{
				delegate2 = @delegate;
				Control18.Delegate7 value2 = (Control18.Delegate7)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control18.Delegate7>(ref this.delegate7_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control18.Delegate7 @delegate = this.delegate7_0;
			Control18.Delegate7 delegate2;
			do
			{
				delegate2 = @delegate;
				Control18.Delegate7 value2 = (Control18.Delegate7)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control18.Delegate7>(ref this.delegate7_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x060002B8 RID: 696 RVA: 0x0000D6F0 File Offset: 0x0000B8F0
	public Control18()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		base.Size = new Size(202, 26);
		this.bitmap_0 = new Bitmap(1, 1);
		this.graphics_0 = Graphics.FromImage(this.bitmap_0);
		this.method_0();
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(50, 50, 50));
		this.solidBrush_1 = new SolidBrush(Color.FromArgb(55, 55, 55));
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_2 = new Pen(Color.FromArgb(65, 65, 65));
	}

	// Token: 0x170000E9 RID: 233
	// (get) Token: 0x060002B9 RID: 697 RVA: 0x0000D7C0 File Offset: 0x0000B9C0
	// (set) Token: 0x060002BA RID: 698 RVA: 0x0000D7D8 File Offset: 0x0000B9D8
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = Math.Max(Math.Min(value, this.Int32_2), 0);
			base.Invalidate();
		}
	}

	// Token: 0x170000EA RID: 234
	// (get) Token: 0x060002BB RID: 699 RVA: 0x0000D7F8 File Offset: 0x0000B9F8
	// (set) Token: 0x060002BC RID: 700 RVA: 0x0000D810 File Offset: 0x0000BA10
	public int Int32_1
	{
		get
		{
			return this.int_1;
		}
		set
		{
			this.int_1 = value;
			this.int_0 = Math.Max(Math.Min(this.int_0, this.Int32_2), 0);
			base.Invalidate();
		}
	}

	// Token: 0x170000EB RID: 235
	// (get) Token: 0x060002BD RID: 701 RVA: 0x0000D83C File Offset: 0x0000BA3C
	public int Int32_2
	{
		get
		{
			return checked(this.Int32_1 - 1);
		}
	}

	// Token: 0x170000EC RID: 236
	// (get) Token: 0x060002BE RID: 702 RVA: 0x0000D854 File Offset: 0x0000BA54
	// (set) Token: 0x060002BF RID: 703 RVA: 0x0000D86C File Offset: 0x0000BA6C
	public virtual Font Font
	{
		get
		{
			return this.method_2();
		}
		set
		{
			this.method_3(value);
			this.method_0();
			base.Invalidate();
		}
	}

	// Token: 0x060002C0 RID: 704 RVA: 0x0000D884 File Offset: 0x0000BA84
	private void method_0()
	{
		this.int_2 = checked(this.graphics_0.MeasureString("000 ..", this.System.Windows.Forms.Control.Font).ToSize().Width + 10);
	}

	// Token: 0x060002C1 RID: 705 RVA: 0x0000D8C0 File Offset: 0x0000BAC0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			if (this.int_0 >= 4)
			{
				if (this.int_0 <= 3 || this.int_0 >= this.Int32_2 - 3)
				{
					int num = 0;
					do
					{
						bool bool_ = num == 0 && this.Int32_2 > 4;
						this.method_1(num * this.int_2, this.Int32_2 - (4 - num), bool_, false);
						num++;
					}
					while (num <= 4);
				}
				else
				{
					int num2 = 0;
					do
					{
						bool bool_ = num2 == 0;
						bool bool_2 = num2 == 4;
						this.method_1(num2 * this.int_2, this.int_0 + num2 - 2, bool_, bool_2);
						num2++;
					}
					while (num2 <= 4);
				}
			}
			else
			{
				int num3 = Math.Min(this.Int32_2, 4);
				for (int i = 0; i <= num3; i++)
				{
					bool bool_2 = i == 4 && this.Int32_2 > 4;
					this.method_1(i * this.int_2, i, false, bool_2);
				}
			}
		}
	}

	// Token: 0x060002C2 RID: 706 RVA: 0x0000D9E0 File Offset: 0x0000BBE0
	private void method_1(int int_3, int int_4, bool bool_0, bool bool_1)
	{
		checked
		{
			this.rectangle_0 = new Rectangle(int_3, 0, this.int_2 - 4, base.Height - 1);
			this.graphicsPath_0 = Class9.smethod_3(this.rectangle_0, 7);
			this.graphicsPath_1 = Class9.smethod_2(this.rectangle_0.X + 1, this.rectangle_0.Y + 1, this.rectangle_0.Width - 2, this.rectangle_0.Height - 2, 7);
			string text = Conversions.ToString(int_4 + 1);
			if (bool_0)
			{
				text = ".. " + text;
			}
			if (bool_1)
			{
				text += " ..";
			}
			this.size_0 = Class9.graphics_0.MeasureString(text, this.System.Windows.Forms.Control.Font).ToSize();
			this.point_0 = new Point(this.rectangle_0.X + (this.rectangle_0.Width / 2 - this.size_0.Width / 2), this.rectangle_0.Y + (this.rectangle_0.Height / 2 - this.size_0.Height / 2));
			if (int_4 != this.int_0)
			{
				Class9.graphics_0.FillPath(this.solidBrush_1, this.graphicsPath_0);
				Class9.graphics_0.DrawString(text, this.System.Windows.Forms.Control.Font, Brushes.Black, (float)(this.point_0.X + 1), (float)(this.point_0.Y + 1));
				Class9.graphics_0.DrawString(text, this.System.Windows.Forms.Control.Font, Brushes.WhiteSmoke, this.point_0);
				Class9.graphics_0.DrawPath(this.pen_2, this.graphicsPath_1);
				Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_0);
			}
			else
			{
				Class9.graphics_0.FillPath(this.solidBrush_0, this.graphicsPath_0);
				Font font = new Font(this.System.Windows.Forms.Control.Font, FontStyle.Underline);
				Class9.graphics_0.DrawString(text, font, Brushes.Black, (float)(this.point_0.X + 1), (float)(this.point_0.Y + 1));
				Class9.graphics_0.DrawString(text, font, Brushes.WhiteSmoke, this.point_0);
				font.Dispose();
				Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_1);
				Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_0);
			}
		}
	}

	// Token: 0x060002C3 RID: 707 RVA: 0x0000DC44 File Offset: 0x0000BE44
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		checked
		{
			if (e.Button == MouseButtons.Left)
			{
				int num = this.int_0;
				int num2;
				if (this.int_0 >= 4)
				{
					if (this.int_0 > 3 && this.int_0 < this.Int32_2 - 3)
					{
						num2 = e.X / this.int_2;
						int num3 = num2;
						if (num3 != 2)
						{
							if (num3 >= 2)
							{
								if (num3 > 2)
								{
									num2 = num + (num2 - 2);
								}
							}
							else
							{
								num2 = num - (2 - num2);
							}
						}
						else
						{
							num2 = num;
						}
					}
					else
					{
						num2 = this.Int32_2 - (4 - e.X / this.int_2);
					}
				}
				else
				{
					num2 = e.X / this.int_2;
				}
				if (num2 < this.int_1 && num2 != num)
				{
					this.Int32_0 = num2;
					Control18.Delegate7 @delegate = this.delegate7_0;
					if (@delegate != null)
					{
						@delegate(this, null);
					}
				}
			}
			base.OnMouseDown(e);
		}
	}

	// Token: 0x060002C4 RID: 708 RVA: 0x0000DD28 File Offset: 0x0000BF28
	Font method_2()
	{
		return base.Font;
	}

	// Token: 0x060002C5 RID: 709 RVA: 0x0000DD30 File Offset: 0x0000BF30
	void method_3(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x04000156 RID: 342
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Control18.Delegate7 delegate7_0;

	// Token: 0x04000157 RID: 343
	private Bitmap bitmap_0;

	// Token: 0x04000158 RID: 344
	private Graphics graphics_0;

	// Token: 0x04000159 RID: 345
	private int int_0;

	// Token: 0x0400015A RID: 346
	private int int_1;

	// Token: 0x0400015B RID: 347
	private int int_2;

	// Token: 0x0400015C RID: 348
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400015D RID: 349
	private GraphicsPath graphicsPath_1;

	// Token: 0x0400015E RID: 350
	private Rectangle rectangle_0;

	// Token: 0x0400015F RID: 351
	private Size size_0;

	// Token: 0x04000160 RID: 352
	private Point point_0;

	// Token: 0x04000161 RID: 353
	private Pen pen_0;

	// Token: 0x04000162 RID: 354
	private Pen pen_1;

	// Token: 0x04000163 RID: 355
	private Pen pen_2;

	// Token: 0x04000164 RID: 356
	private SolidBrush solidBrush_0;

	// Token: 0x04000165 RID: 357
	private SolidBrush solidBrush_1;

	// Token: 0x0200002D RID: 45
	// (Invoke) Token: 0x060002C9 RID: 713
	public delegate void Delegate7(object sender, EventArgs e);
}
