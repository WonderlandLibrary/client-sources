using System;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x02000075 RID: 117
public class GControl18 : Control
{
	// Token: 0x1700018E RID: 398
	// (get) Token: 0x0600055D RID: 1373 RVA: 0x0001BAC4 File Offset: 0x00019CC4
	// (set) Token: 0x0600055E RID: 1374 RVA: 0x0001BACC File Offset: 0x00019CCC
	private virtual Timer Timer_0
	{
		[CompilerGenerated]
		get
		{
			return this.timer_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_1);
			Timer timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_0 = value;
			timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700018F RID: 399
	// (get) Token: 0x0600055F RID: 1375 RVA: 0x0001BB10 File Offset: 0x00019D10
	// (set) Token: 0x06000560 RID: 1376 RVA: 0x0001BB28 File Offset: 0x00019D28
	public GControl18.GEnum1 GEnum1_0
	{
		get
		{
			return this.genum1_0;
		}
		set
		{
			this.genum1_0 = value;
		}
	}

	// Token: 0x17000190 RID: 400
	// (get) Token: 0x06000561 RID: 1377 RVA: 0x0001BB34 File Offset: 0x00019D34
	// (set) Token: 0x06000562 RID: 1378 RVA: 0x0001BB4C File Offset: 0x00019D4C
	public virtual string Text
	{
		get
		{
			return this.method_2();
		}
		set
		{
			this.method_3(value);
			if (this.string_0 != null)
			{
				this.string_0 = value;
			}
		}
	}

	// Token: 0x17000191 RID: 401
	// (get) Token: 0x06000563 RID: 1379 RVA: 0x0001BB68 File Offset: 0x00019D68
	// (set) Token: 0x06000564 RID: 1380 RVA: 0x0001BB74 File Offset: 0x00019D74
	public bool Boolean_0
	{
		get
		{
			return !base.Visible;
		}
		set
		{
			base.Visible = value;
		}
	}

	// Token: 0x06000565 RID: 1381 RVA: 0x0001BB80 File Offset: 0x00019D80
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x06000566 RID: 1382 RVA: 0x0001BB90 File Offset: 0x00019D90
	public void method_0(GControl18.GEnum1 genum1_1, string string_1, int int_0)
	{
		this.genum1_0 = genum1_1;
		this.System.Windows.Forms.Control.Text = string_1;
		this.Boolean_0 = true;
		this.Timer_0.Interval = int_0;
		this.Timer_0.Enabled = true;
	}

	// Token: 0x06000567 RID: 1383 RVA: 0x0001BBC0 File Offset: 0x00019DC0
	public GControl18()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor, true);
		this.BackColor = Color.Transparent;
		base.Size = new Size(500, 30);
		this.DoubleBuffered = true;
		this.Timer_0 = new Timer();
	}

	// Token: 0x06000568 RID: 1384 RVA: 0x0001BC10 File Offset: 0x00019E10
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rect = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			Rectangle r = new Rectangle(20, 5, base.Width - 21, base.Height - 11);
			Rectangle r2 = new Rectangle(7, 7, 20, 20);
			Rectangle rect2 = new Rectangle(5, 5, 20, 20);
			graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.Clear(Color.Transparent);
			base.OnPaint(e);
			switch (this.genum1_0)
			{
			case GControl18.GEnum1.Info:
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(30, 0, 0, 255)), rect);
				graphics.DrawRectangle(new Pen(Color.FromArgb(0, 0, 0)), rect);
				graphics.DrawString(this.System.Windows.Forms.Control.Text, new Font("Segoe UI", 11f, FontStyle.Regular), new SolidBrush(Color.FromArgb(245, 245, 245)), r, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				graphics.DrawString("i", new Font("Segoe UI", 12f), new SolidBrush(Color.FromArgb(245, 245, 245)), r2, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			case GControl18.GEnum1.Success:
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(30, 0, 255, 0)), rect);
				graphics.DrawRectangle(new Pen(Color.FromArgb(0, 0, 0)), rect);
				graphics.DrawString(this.System.Windows.Forms.Control.Text, new Font("Segoe UI", 11f, FontStyle.Regular), new SolidBrush(Color.FromArgb(245, 245, 245)), r, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				graphics.DrawString("ü", new Font("Wingdings", 14f), new SolidBrush(Color.FromArgb(245, 245, 245)), r2, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			case GControl18.GEnum1.Error:
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(30, 255, 0, 0)), rect);
				graphics.DrawRectangle(new Pen(Color.FromArgb(0, 0, 0)), rect);
				graphics.DrawString(this.System.Windows.Forms.Control.Text, new Font("Segoe UI", 11f, FontStyle.Regular), new SolidBrush(Color.FromArgb(245, 245, 245)), r, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				graphics.DrawString("r", new Font("Marlett", 10f), new SolidBrush(Color.FromArgb(245, 245, 245)), r2, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			graphics.DrawEllipse(new Pen(Color.FromArgb(255, 255, 255)), rect2);
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x06000569 RID: 1385 RVA: 0x0001BF6C File Offset: 0x0001A16C
	private void method_1(object sender, EventArgs e)
	{
		this.Boolean_0 = false;
		this.Timer_0.Enabled = false;
	}

	// Token: 0x0600056A RID: 1386 RVA: 0x0001BF84 File Offset: 0x0001A184
	string method_2()
	{
		return base.Text;
	}

	// Token: 0x0600056B RID: 1387 RVA: 0x0001BF8C File Offset: 0x0001A18C
	void method_3(string string_1)
	{
		base.Text = string_1;
	}

	// Token: 0x040002B9 RID: 697
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("_mytimer")]
	private Timer timer_0;

	// Token: 0x040002BA RID: 698
	private GControl18.GEnum1 genum1_0;

	// Token: 0x040002BB RID: 699
	private string string_0;

	// Token: 0x02000076 RID: 118
	public enum GEnum1
	{
		// Token: 0x040002BD RID: 701
		Info,
		// Token: 0x040002BE RID: 702
		Success,
		// Token: 0x040002BF RID: 703
		Error
	}
}
