using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x0200006C RID: 108
public class GControl11 : Control
{
	// Token: 0x17000182 RID: 386
	// (get) Token: 0x0600051F RID: 1311 RVA: 0x0001985C File Offset: 0x00017A5C
	// (set) Token: 0x06000520 RID: 1312 RVA: 0x00019874 File Offset: 0x00017A74
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			if (value <= this.int_1)
			{
				if (value >= 0)
				{
					this.int_0 = value;
				}
				else
				{
					this.int_0 = 0;
				}
			}
			else
			{
				this.int_0 = this.int_1;
			}
			base.Invalidate();
		}
	}

	// Token: 0x17000183 RID: 387
	// (get) Token: 0x06000521 RID: 1313 RVA: 0x000198AC File Offset: 0x00017AAC
	// (set) Token: 0x06000522 RID: 1314 RVA: 0x000198C4 File Offset: 0x00017AC4
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
				this.int_1 = 1;
			}
			else
			{
				this.int_1 = value;
			}
			if (value < this.int_0)
			{
				this.int_0 = this.int_1;
			}
			base.Invalidate();
		}
	}

	// Token: 0x17000184 RID: 388
	// (get) Token: 0x06000523 RID: 1315 RVA: 0x000198FC File Offset: 0x00017AFC
	// (set) Token: 0x06000524 RID: 1316 RVA: 0x00019904 File Offset: 0x00017B04
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

	// Token: 0x06000525 RID: 1317 RVA: 0x00019914 File Offset: 0x00017B14
	public GControl11()
	{
		this.bool_0 = false;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		base.Size = new Size(20, 250);
		this.DoubleBuffered = true;
		this.int_1 = 100;
	}

	// Token: 0x06000526 RID: 1318 RVA: 0x00019968 File Offset: 0x00017B68
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			int num = (int)Math.Round(unchecked((double)(checked(base.Height - 1)) * ((double)this.int_0 / (double)this.int_1)));
			Rectangle rectangle = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			Rectangle rectangle_ = new Rectangle(4, base.Height - num + 4, base.Width - 9, num - 9);
			base.OnPaint(e);
			graphics.Clear(this.BackColor);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.FillPath(new SolidBrush(Color.FromArgb(41, 41, 41)), Class22.smethod_0(rectangle, 5));
			graphics.DrawPath(new Pen(Color.FromArgb(0, 0, 0)), Class22.smethod_0(rectangle, 5));
			if (num != 0)
			{
				try
				{
					graphics.FillPath(new SolidBrush(Color.FromArgb(128, 128, 128)), Class22.smethod_0(rectangle_, 7));
				}
				catch (Exception ex)
				{
				}
			}
			if (this.bool_0)
			{
				graphics.DrawString(string.Format("{0}%", this.int_0), new Font("Segoe UI", 8f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x040002AC RID: 684
	private int int_0;

	// Token: 0x040002AD RID: 685
	private int int_1;

	// Token: 0x040002AE RID: 686
	private bool bool_0;
}
