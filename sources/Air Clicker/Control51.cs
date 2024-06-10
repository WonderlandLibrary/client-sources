using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000C4 RID: 196
[DefaultEvent("ValueChanged")]
internal class Control51 : Control
{
	// Token: 0x1700027E RID: 638
	// (get) Token: 0x060008DF RID: 2271 RVA: 0x00029E2C File Offset: 0x0002802C
	// (set) Token: 0x060008E0 RID: 2272 RVA: 0x00029E44 File Offset: 0x00028044
	public int Int32_0
	{
		get
		{
			return this.int_1;
		}
		set
		{
			if (value >= this.int_2)
			{
				value = checked(this.int_2 - 10);
			}
			if (this.int_3 < value)
			{
				this.int_3 = value;
			}
			this.int_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x1700027F RID: 639
	// (get) Token: 0x060008E1 RID: 2273 RVA: 0x00029E80 File Offset: 0x00028080
	// (set) Token: 0x060008E2 RID: 2274 RVA: 0x00029E98 File Offset: 0x00028098
	public int Int32_1
	{
		get
		{
			return this.int_2;
		}
		set
		{
			if (value <= this.int_1)
			{
				value = checked(this.int_1 + 10);
			}
			if (this.int_3 > value)
			{
				this.int_3 = value;
			}
			this.int_2 = value;
			base.Invalidate();
		}
	}

	// Token: 0x14000021 RID: 33
	// (add) Token: 0x060008E3 RID: 2275 RVA: 0x00029ED4 File Offset: 0x000280D4
	// (remove) Token: 0x060008E4 RID: 2276 RVA: 0x00029F0C File Offset: 0x0002810C
	public event Control51.Delegate18 Event_0
	{
		[CompilerGenerated]
		add
		{
			Control51.Delegate18 @delegate = this.delegate18_0;
			Control51.Delegate18 delegate2;
			do
			{
				delegate2 = @delegate;
				Control51.Delegate18 value2 = (Control51.Delegate18)Delegate.Combine(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control51.Delegate18>(ref this.delegate18_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
		[CompilerGenerated]
		remove
		{
			Control51.Delegate18 @delegate = this.delegate18_0;
			Control51.Delegate18 delegate2;
			do
			{
				delegate2 = @delegate;
				Control51.Delegate18 value2 = (Control51.Delegate18)Delegate.Remove(delegate2, value);
				@delegate = Interlocked.CompareExchange<Control51.Delegate18>(ref this.delegate18_0, value2, delegate2);
			}
			while (@delegate != delegate2);
		}
	}

	// Token: 0x17000280 RID: 640
	// (get) Token: 0x060008E5 RID: 2277 RVA: 0x00029F48 File Offset: 0x00028148
	// (set) Token: 0x060008E6 RID: 2278 RVA: 0x00029F60 File Offset: 0x00028160
	public int Int32_2
	{
		get
		{
			return this.int_3;
		}
		set
		{
			if (this.int_3 != value)
			{
				if (value < this.int_1)
				{
					this.int_3 = this.int_1;
				}
				else if (value > this.int_2)
				{
					this.int_3 = this.int_2;
				}
				else
				{
					this.int_3 = value;
				}
				base.Invalidate();
				Control51.Delegate18 @delegate = this.delegate18_0;
				if (@delegate != null)
				{
					@delegate();
				}
			}
		}
	}

	// Token: 0x17000281 RID: 641
	// (get) Token: 0x060008E7 RID: 2279 RVA: 0x00029FCC File Offset: 0x000281CC
	// (set) Token: 0x060008E8 RID: 2280 RVA: 0x00029FE4 File Offset: 0x000281E4
	public Control51.Enum13 Enum13_0
	{
		get
		{
			return this.enum13_0;
		}
		set
		{
			this.enum13_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000282 RID: 642
	// (get) Token: 0x060008E9 RID: 2281 RVA: 0x00029FF4 File Offset: 0x000281F4
	// (set) Token: 0x060008EA RID: 2282 RVA: 0x0002A014 File Offset: 0x00028214
	[Browsable(false)]
	public float Single_0
	{
		get
		{
			return (float)((double)this.int_3 / (double)this.enum13_0);
		}
		set
		{
			this.Int32_2 = checked((int)Math.Round((double)(unchecked(value * (float)this.enum13_0))));
		}
	}

	// Token: 0x17000283 RID: 643
	// (get) Token: 0x060008EB RID: 2283 RVA: 0x0002A02C File Offset: 0x0002822C
	// (set) Token: 0x060008EC RID: 2284 RVA: 0x0002A034 File Offset: 0x00028234
	public bool Boolean_0
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x060008ED RID: 2285 RVA: 0x0002A044 File Offset: 0x00028244
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		checked
		{
			if (this.bool_0 && e.X > -1 && e.X < base.Width + 1)
			{
				this.Int32_2 = this.int_1 + (int)Math.Round(unchecked((double)(checked(this.int_2 - this.int_1)) * ((double)e.X / (double)base.Width)));
			}
		}
	}

	// Token: 0x060008EE RID: 2286 RVA: 0x0002A0B0 File Offset: 0x000282B0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		checked
		{
			if (e.Button == MouseButtons.Left)
			{
				this.int_0 = (int)Math.Round(unchecked(checked((double)(this.int_3 - this.int_1) / (double)(this.int_2 - this.int_1)) * (double)(checked(base.Width - 11))));
				this.rectangle_2 = new Rectangle(this.int_0, 0, 25, 25);
				this.bool_0 = this.rectangle_2.Contains(e.Location);
				base.Focus();
				if (this.bool_1)
				{
					this.Int32_2 = this.int_1 + (int)Math.Round(unchecked((double)(checked(this.int_2 - this.int_1)) * ((double)e.X / (double)base.Width)));
				}
			}
		}
	}

	// Token: 0x060008EF RID: 2287 RVA: 0x0002A178 File Offset: 0x00028378
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x060008F0 RID: 2288 RVA: 0x0002A188 File Offset: 0x00028388
	public Control51()
	{
		this.size_0 = new Size(14, 14);
		this.int_1 = 0;
		this.int_2 = 10;
		this.int_3 = 0;
		this.bool_1 = false;
		this.enum13_0 = Control51.Enum13.By1;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.DoubleBuffer, true);
		base.Size = new Size(80, 22);
		this.MinimumSize = new Size(47, 22);
	}

	// Token: 0x060008F1 RID: 2289 RVA: 0x0002A1F8 File Offset: 0x000283F8
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 22;
	}

	// Token: 0x060008F2 RID: 2290 RVA: 0x0002A20C File Offset: 0x0002840C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		graphics.Clear(base.Parent.BackColor);
		graphics.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.rectangle_3 = new Rectangle(7, 10, base.Width - 16, 2);
			this.rectangle_1 = new Rectangle(1, 10, base.Width - 3, 2);
			try
			{
				this.int_0 = (int)Math.Round(unchecked(checked((double)(this.int_3 - this.int_1) / (double)(this.int_2 - this.int_1)) * (double)base.Width));
			}
			catch (Exception ex)
			{
			}
			this.rectangle_2 = new Rectangle(this.int_0, 0, 3, 20);
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(124, 131, 137)), this.rectangle_1);
			this.rectangle_0 = new Rectangle(0, 10, this.rectangle_2.X + this.rectangle_2.Width - 4, 3);
			graphics.ResetClip();
			graphics.SmoothingMode = SmoothingMode.Default;
			graphics.DrawRectangle(new Pen(Color.FromArgb(124, 131, 137)), this.rectangle_1);
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), this.rectangle_0);
			graphics.ResetClip();
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.FillEllipse(new SolidBrush(Color.FromArgb(181, 41, 42)), this.rectangle_3.X + (int)Math.Round(unchecked((double)this.rectangle_3.Width * ((double)this.Int32_2 / (double)this.Int32_1))) - (int)Math.Round((double)this.size_0.Width / 2.0), this.rectangle_3.Y + (int)Math.Round((double)this.rectangle_3.Height / 2.0) - (int)Math.Round((double)this.size_0.Height / 2.0), this.size_0.Width, this.size_0.Height);
			graphics.DrawEllipse(new Pen(Color.FromArgb(181, 41, 42)), this.rectangle_3.X + (int)Math.Round(unchecked((double)this.rectangle_3.Width * ((double)this.Int32_2 / (double)this.Int32_1))) - (int)Math.Round((double)this.size_0.Width / 2.0), this.rectangle_3.Y + (int)Math.Round((double)this.rectangle_3.Height / 2.0) - (int)Math.Round((double)this.size_0.Height / 2.0), this.size_0.Width, this.size_0.Height);
		}
	}

	// Token: 0x04000438 RID: 1080
	private Rectangle rectangle_0;

	// Token: 0x04000439 RID: 1081
	private Rectangle rectangle_1;

	// Token: 0x0400043A RID: 1082
	private Rectangle rectangle_2;

	// Token: 0x0400043B RID: 1083
	private bool bool_0;

	// Token: 0x0400043C RID: 1084
	private int int_0;

	// Token: 0x0400043D RID: 1085
	private Size size_0;

	// Token: 0x0400043E RID: 1086
	private Rectangle rectangle_3;

	// Token: 0x0400043F RID: 1087
	private int int_1;

	// Token: 0x04000440 RID: 1088
	private int int_2;

	// Token: 0x04000441 RID: 1089
	private int int_3;

	// Token: 0x04000442 RID: 1090
	private bool bool_1;

	// Token: 0x04000443 RID: 1091
	private Control51.Enum13 enum13_0;

	// Token: 0x04000444 RID: 1092
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private Control51.Delegate18 delegate18_0;

	// Token: 0x020000C5 RID: 197
	public enum Enum13
	{
		// Token: 0x04000446 RID: 1094
		By1 = 1,
		// Token: 0x04000447 RID: 1095
		By10 = 10,
		// Token: 0x04000448 RID: 1096
		By100 = 100,
		// Token: 0x04000449 RID: 1097
		By1000 = 1000
	}

	// Token: 0x020000C6 RID: 198
	// (Invoke) Token: 0x060008F6 RID: 2294
	public delegate void Delegate18();
}
