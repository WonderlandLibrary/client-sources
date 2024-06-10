using System;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000D8 RID: 216
public class GControl43 : TabControl
{
	// Token: 0x17000291 RID: 657
	// (get) Token: 0x0600094E RID: 2382 RVA: 0x0002B99C File Offset: 0x00029B9C
	// (set) Token: 0x0600094F RID: 2383 RVA: 0x0002B9B4 File Offset: 0x00029BB4
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x14000027 RID: 39
	// (add) Token: 0x06000950 RID: 2384 RVA: 0x0002B9C4 File Offset: 0x00029BC4
	// (remove) Token: 0x06000951 RID: 2385 RVA: 0x0002BA00 File Offset: 0x00029C00
	public event GControl43.GDelegate22 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl43.GDelegate22 gdelegate = this.gdelegate22_0;
			GControl43.GDelegate22 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl43.GDelegate22 value2 = (GControl43.GDelegate22)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl43.GDelegate22>(ref this.gdelegate22_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl43.GDelegate22 gdelegate = this.gdelegate22_0;
			GControl43.GDelegate22 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl43.GDelegate22 value2 = (GControl43.GDelegate22)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl43.GDelegate22>(ref this.gdelegate22_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x17000292 RID: 658
	// (get) Token: 0x06000952 RID: 2386 RVA: 0x0002BA3C File Offset: 0x00029C3C
	// (set) Token: 0x06000953 RID: 2387 RVA: 0x0002BA54 File Offset: 0x00029C54
	public GControl43.GEnum14 GEnum14_0
	{
		get
		{
			return this.genum14_0;
		}
		set
		{
			this.genum14_0 = value;
			GControl43.GDelegate22 gdelegate = this.gdelegate22_0;
			if (gdelegate != null)
			{
				gdelegate();
			}
		}
	}

	// Token: 0x06000954 RID: 2388 RVA: 0x0002BA78 File Offset: 0x00029C78
	protected void method_0()
	{
		base.Invalidate();
		GControl43.GEnum14 genum = this.GEnum14_0;
		if (genum != GControl43.GEnum14.Light)
		{
			if (genum == GControl43.GEnum14.Dark)
			{
				this.color_2 = Color.FromArgb(50, 50, 50);
				this.color_1 = Color.FromArgb(35, 35, 35);
				this.ForeColor = Color.White;
			}
		}
		else
		{
			this.color_2 = Color.White;
			this.color_1 = Color.FromArgb(200, 200, 200);
			this.ForeColor = Color.Black;
		}
	}

	// Token: 0x06000955 RID: 2389 RVA: 0x0002BAFC File Offset: 0x00029CFC
	public GControl43()
	{
		this.Event_0 += this.method_0;
		base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
		base.SetStyle(ControlStyles.ResizeRedraw, true);
		base.SetStyle(ControlStyles.UserPaint, true);
		base.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.FromArgb(50, 50, 50);
		this.ForeColor = Color.White;
		this.Color_0 = Color.DodgerBlue;
	}

	// Token: 0x06000956 RID: 2390 RVA: 0x0002BB78 File Offset: 0x00029D78
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		base.OnPaint(e);
		try
		{
			base.SelectedTab.BackColor = this.color_1;
		}
		catch (Exception ex)
		{
		}
		graphics.Clear(this.color_2);
		checked
		{
			int num = base.TabPages.Count - 1;
			for (int i = 0; i <= num; i++)
			{
				Rectangle rectangle = new Rectangle(base.GetTabRect(i).X, base.GetTabRect(i).Y + 3, base.GetTabRect(i).Width + 2, base.GetTabRect(i).Height);
				graphics.FillRectangle(new SolidBrush(this.color_1), rectangle);
				graphics.DrawString(base.TabPages[i].Text, new Font("Segoe UI Semilight", 9.75f), new SolidBrush(this.ForeColor), rectangle, new StringFormat
				{
					LineAlignment = StringAlignment.Center,
					Alignment = StringAlignment.Center
				});
			}
			graphics.FillRectangle(new SolidBrush(this.color_1), 0, base.ItemSize.Height, base.Width, base.Height);
			if (base.SelectedIndex != -1)
			{
				Rectangle rectangle2 = new Rectangle(base.GetTabRect(base.SelectedIndex).X - 2, base.GetTabRect(base.SelectedIndex).Y, base.GetTabRect(base.SelectedIndex).Width + 4, base.GetTabRect(base.SelectedIndex).Height);
				graphics.FillRectangle(new SolidBrush(this.Color_0), rectangle2);
				graphics.DrawString(base.TabPages[base.SelectedIndex].Text, new Font("Segoe UI Semilight", 9.75f), new SolidBrush(this.ForeColor), rectangle2, new StringFormat
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

	// Token: 0x04000480 RID: 1152
	private Color color_0;

	// Token: 0x04000481 RID: 1153
	private Color color_1;

	// Token: 0x04000482 RID: 1154
	private Color color_2;

	// Token: 0x04000483 RID: 1155
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GControl43.GDelegate22 gdelegate22_0;

	// Token: 0x04000484 RID: 1156
	private GControl43.GEnum14 genum14_0;

	// Token: 0x020000D9 RID: 217
	public enum GEnum14
	{
		// Token: 0x04000486 RID: 1158
		Light,
		// Token: 0x04000487 RID: 1159
		Dark
	}

	// Token: 0x020000DA RID: 218
	// (Invoke) Token: 0x0600095A RID: 2394
	public delegate void GDelegate22();
}
