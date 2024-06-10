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

// Token: 0x02000055 RID: 85
[DefaultEvent("Scroll")]
internal class Control39 : Control
{
	// Token: 0x0600047E RID: 1150 RVA: 0x00015C2C File Offset: 0x00013E2C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (e.Button == MouseButtons.Left)
		{
			this.int_2 = checked((int)Math.Round(unchecked(checked((double)(this.int_5 - this.int_3) / (double)(this.int_4 - this.int_3)) * (double)(checked(base.Width - 11)))));
			this.rectangle_0 = new Rectangle(this.int_2, 0, 10, 20);
			this.bool_0 = this.rectangle_0.Contains(e.Location);
		}
	}

	// Token: 0x0600047F RID: 1151 RVA: 0x00015CB0 File Offset: 0x00013EB0
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		checked
		{
			if (this.bool_0 && e.X > -1 && e.X < base.Width + 1)
			{
				this.Int32_2 = this.int_3 + (int)Math.Round(unchecked((double)(checked(this.int_4 - this.int_3)) * ((double)e.X / (double)base.Width)));
			}
		}
	}

	// Token: 0x06000480 RID: 1152 RVA: 0x00015D1C File Offset: 0x00013F1C
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x17000163 RID: 355
	// (get) Token: 0x06000481 RID: 1153 RVA: 0x00015D2C File Offset: 0x00013F2C
	// (set) Token: 0x06000482 RID: 1154 RVA: 0x00015D44 File Offset: 0x00013F44
	public Control39.Enum7 Enum7_0
	{
		get
		{
			return this.enum7_0;
		}
		set
		{
			this.enum7_0 = value;
		}
	}

	// Token: 0x17000164 RID: 356
	// (get) Token: 0x06000483 RID: 1155 RVA: 0x00015D50 File Offset: 0x00013F50
	// (set) Token: 0x06000484 RID: 1156 RVA: 0x00015D68 File Offset: 0x00013F68
	[Category("Colors")]
	public Color Color_0
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x17000165 RID: 357
	// (get) Token: 0x06000485 RID: 1157 RVA: 0x00015D74 File Offset: 0x00013F74
	// (set) Token: 0x06000486 RID: 1158 RVA: 0x00015D8C File Offset: 0x00013F8C
	[Category("Colors")]
	public Color Color_1
	{
		get
		{
			return this.color_3;
		}
		set
		{
			this.color_3 = value;
		}
	}

	// Token: 0x1400000C RID: 12
	// (add) Token: 0x06000487 RID: 1159 RVA: 0x00015D98 File Offset: 0x00013F98
	// (remove) Token: 0x06000488 RID: 1160 RVA: 0x00015DD0 File Offset: 0x00013FD0
	public event Control39.Delegate13 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control39.Delegate13 @delegate = this.delegate13_0;
			Control39.Delegate13 delegate2;
			do
			{
				delegate2 = @delegate;
				Control39.Delegate13 value2 = (Control39.Delegate13)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control39.Delegate13>(ref this.delegate13_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control39.Delegate13 @delegate = this.delegate13_0;
			Control39.Delegate13 delegate2;
			do
			{
				delegate2 = @delegate;
				Control39.Delegate13 value2 = (Control39.Delegate13)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control39.Delegate13>(ref this.delegate13_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x17000166 RID: 358
	// (get) Token: 0x06000489 RID: 1161 RVA: 0x00015E0C File Offset: 0x0001400C
	// (set) Token: 0x0600048A RID: 1162 RVA: 0x00015E10 File Offset: 0x00014010
	public int Int32_0
	{
		get
		{
			return 0;
		}
		set
		{
			if (value < 0)
			{
			}
			this.int_3 = value;
			if (value > this.int_5)
			{
				this.int_5 = value;
			}
			if (value > this.int_4)
			{
				this.int_4 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x17000167 RID: 359
	// (get) Token: 0x0600048B RID: 1163 RVA: 0x00015E4C File Offset: 0x0001404C
	// (set) Token: 0x0600048C RID: 1164 RVA: 0x00015E64 File Offset: 0x00014064
	public int Int32_1
	{
		get
		{
			return this.int_4;
		}
		set
		{
			if (value >= 0)
			{
			}
			this.int_4 = value;
			if (value < this.int_5)
			{
				this.int_5 = value;
			}
			if (value < this.int_3)
			{
				this.int_3 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x17000168 RID: 360
	// (get) Token: 0x0600048D RID: 1165 RVA: 0x00015EA0 File Offset: 0x000140A0
	// (set) Token: 0x0600048E RID: 1166 RVA: 0x00015EB8 File Offset: 0x000140B8
	public int Int32_2
	{
		get
		{
			return this.int_5;
		}
		set
		{
			if (value != this.int_5)
			{
				if (value > this.int_4 || value < this.int_3)
				{
				}
				this.int_5 = value;
				base.Invalidate();
				Control39.Delegate13 @delegate = this.delegate13_0;
				if (@delegate != null)
				{
					@delegate(this);
				}
			}
		}
	}

	// Token: 0x17000169 RID: 361
	// (get) Token: 0x0600048F RID: 1167 RVA: 0x00015F08 File Offset: 0x00014108
	// (set) Token: 0x06000490 RID: 1168 RVA: 0x00015F10 File Offset: 0x00014110
	public bool Boolean_0
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
		}
	}

	// Token: 0x06000491 RID: 1169 RVA: 0x00015F1C File Offset: 0x0001411C
	protected virtual void OnKeyDown(KeyEventArgs e)
	{
		base.OnKeyDown(e);
		checked
		{
			if (e.KeyCode == Keys.Subtract)
			{
				if (this.Int32_2 != 0)
				{
					this.Int32_2--;
				}
			}
			else if (e.KeyCode == Keys.Add && this.Int32_2 != this.int_4)
			{
				this.Int32_2++;
			}
		}
	}

	// Token: 0x06000492 RID: 1170 RVA: 0x00015F84 File Offset: 0x00014184
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x06000493 RID: 1171 RVA: 0x00015F94 File Offset: 0x00014194
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 23;
	}

	// Token: 0x06000494 RID: 1172 RVA: 0x00015FA8 File Offset: 0x000141A8
	public Control39()
	{
		this.int_4 = 10;
		this.bool_1 = false;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Class16.color_0;
		this.color_2 = Color.FromArgb(25, 27, 29);
		this.color_3 = Color.FromArgb(23, 148, 92);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Height = 18;
		this.BackColor = Color.FromArgb(60, 70, 73);
	}

	// Token: 0x06000495 RID: 1173 RVA: 0x00016038 File Offset: 0x00014238
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Rectangle rect = new Rectangle(1, 6, this.int_0 - 2, 8);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath2 = new GraphicsPath();
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			this.int_2 = (int)Math.Round(unchecked(checked((double)(this.int_5 - this.int_3) / (double)(this.int_4 - this.int_3)) * (double)(checked(this.int_0 - 10))));
			this.rectangle_0 = new Rectangle(this.int_2, 0, 10, 20);
			this.rectangle_1 = new Rectangle(this.int_2, 4, 11, 14);
			graphicsPath.AddRectangle(rect);
			graphics_.SetClip(graphicsPath);
			graphics_.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, 7, this.int_0, 8));
			graphics_.FillRectangle(new SolidBrush(this.color_1), new Rectangle(0, 7, this.rectangle_0.X + this.rectangle_0.Width, 8));
			graphics_.ResetClip();
			HatchBrush brush = new HatchBrush(HatchStyle.Plaid, this.Color_1, this.color_1);
			graphics_.FillRectangle(brush, new Rectangle(-10, 7, this.rectangle_0.X + this.rectangle_0.Width, 8));
			Control39.Enum7 @enum = this.Enum7_0;
			if (@enum != Control39.Enum7.Slider)
			{
				if (@enum == Control39.Enum7.Knob)
				{
					graphicsPath2.AddEllipse(this.rectangle_1);
					graphics_.FillPath(new SolidBrush(this.color_2), graphicsPath2);
				}
			}
			else
			{
				graphicsPath2.AddRectangle(this.rectangle_0);
				graphics_.FillPath(new SolidBrush(this.color_2), graphicsPath2);
			}
			if (this.Boolean_0)
			{
				graphics_.DrawString(Conversions.ToString(this.Int32_2), new Font("Segoe UI", 8f), Brushes.White, new Rectangle(1, 6, this.int_0, this.int_1), new StringFormat
				{
					Alignment = StringAlignment.Far,
					LineAlignment = StringAlignment.Far
				});
			}
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x0400025A RID: 602
	private int int_0;

	// Token: 0x0400025B RID: 603
	private int int_1;

	// Token: 0x0400025C RID: 604
	private int int_2;

	// Token: 0x0400025D RID: 605
	private bool bool_0;

	// Token: 0x0400025E RID: 606
	private Rectangle rectangle_0;

	// Token: 0x0400025F RID: 607
	private Rectangle rectangle_1;

	// Token: 0x04000260 RID: 608
	private Control39.Enum7 enum7_0;

	// Token: 0x04000261 RID: 609
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Control39.Delegate13 delegate13_0;

	// Token: 0x04000262 RID: 610
	private int int_3;

	// Token: 0x04000263 RID: 611
	private int int_4;

	// Token: 0x04000264 RID: 612
	private int int_5;

	// Token: 0x04000265 RID: 613
	private bool bool_1;

	// Token: 0x04000266 RID: 614
	private Color color_0;

	// Token: 0x04000267 RID: 615
	private Color color_1;

	// Token: 0x04000268 RID: 616
	private Color color_2;

	// Token: 0x04000269 RID: 617
	private Color color_3;

	// Token: 0x02000056 RID: 86
	[Flags]
	public enum Enum7
	{
		// Token: 0x0400026B RID: 619
		Slider = 0,
		// Token: 0x0400026C RID: 620
		Knob = 1
	}

	// Token: 0x02000057 RID: 87
	// (Invoke) Token: 0x06000499 RID: 1177
	public delegate void Delegate13(object sender);
}
