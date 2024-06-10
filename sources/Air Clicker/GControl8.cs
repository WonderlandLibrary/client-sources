using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000069 RID: 105
public class GControl8 : Control
{
	// Token: 0x1700017D RID: 381
	// (get) Token: 0x0600050E RID: 1294 RVA: 0x00019124 File Offset: 0x00017324
	// (set) Token: 0x0600050F RID: 1295 RVA: 0x0001913C File Offset: 0x0001733C
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			if (value > this.int_1)
			{
				this.int_0 = this.int_1;
			}
			else if (value >= 0)
			{
				this.int_0 = value;
			}
			else
			{
				this.int_0 = 0;
			}
			base.Invalidate();
		}
	}

	// Token: 0x1700017E RID: 382
	// (get) Token: 0x06000510 RID: 1296 RVA: 0x00019174 File Offset: 0x00017374
	// (set) Token: 0x06000511 RID: 1297 RVA: 0x0001918C File Offset: 0x0001738C
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

	// Token: 0x1700017F RID: 383
	// (get) Token: 0x06000512 RID: 1298 RVA: 0x000191C4 File Offset: 0x000173C4
	// (set) Token: 0x06000513 RID: 1299 RVA: 0x000191CC File Offset: 0x000173CC
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

	// Token: 0x06000514 RID: 1300 RVA: 0x000191DC File Offset: 0x000173DC
	public GControl8()
	{
		this.bool_0 = false;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		base.Size = new Size(250, 20);
		this.DoubleBuffered = true;
		this.int_1 = 100;
	}

	// Token: 0x06000515 RID: 1301 RVA: 0x00019230 File Offset: 0x00017430
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			int num = (int)Math.Round(unchecked((double)(checked(base.Width - 1)) * ((double)this.int_0 / (double)this.int_1)));
			Rectangle rectangle_ = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			Rectangle rectangle_2 = new Rectangle(4, 4, num - 9, base.Height - 9);
			base.OnPaint(e);
			graphics.Clear(this.BackColor);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.FillPath(new SolidBrush(Color.FromArgb(41, 41, 41)), Class22.smethod_0(rectangle_, 5));
			graphics.DrawPath(new Pen(Color.FromArgb(0, 0, 0), 2f), Class22.smethod_0(rectangle_, 5));
			if (num != 0)
			{
				graphics.FillPath(new SolidBrush(Color.FromArgb(128, 128, 128)), Class22.smethod_0(rectangle_2, 7));
			}
			if (this.bool_0)
			{
				graphics.DrawString(string.Format("{0}%", this.int_0), new Font("Segoe UI", 10f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(10, 1, base.Width - 1, base.Height - 1), new StringFormat
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

	// Token: 0x040002A7 RID: 679
	private int int_0;

	// Token: 0x040002A8 RID: 680
	private int int_1;

	// Token: 0x040002A9 RID: 681
	private bool bool_0;
}
