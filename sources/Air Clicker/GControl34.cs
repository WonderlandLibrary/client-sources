using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x020000A1 RID: 161
public class GControl34 : Control
{
	// Token: 0x1700021C RID: 540
	// (get) Token: 0x0600076D RID: 1901 RVA: 0x00023130 File Offset: 0x00021330
	// (set) Token: 0x0600076E RID: 1902 RVA: 0x00023138 File Offset: 0x00021338
	private virtual RichTextBox RichTextBox_0 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700021D RID: 541
	// (get) Token: 0x0600076F RID: 1903 RVA: 0x00023144 File Offset: 0x00021344
	// (set) Token: 0x06000770 RID: 1904 RVA: 0x0002315C File Offset: 0x0002135C
	[Category("Colours")]
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x1700021E RID: 542
	// (get) Token: 0x06000771 RID: 1905 RVA: 0x00023168 File Offset: 0x00021368
	// (set) Token: 0x06000772 RID: 1906 RVA: 0x00023180 File Offset: 0x00021380
	[Category("Colours")]
	public Color Color_1
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x1700021F RID: 543
	// (get) Token: 0x06000773 RID: 1907 RVA: 0x0002318C File Offset: 0x0002138C
	// (set) Token: 0x06000774 RID: 1908 RVA: 0x000231A4 File Offset: 0x000213A4
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x17000220 RID: 544
	// (get) Token: 0x06000775 RID: 1909 RVA: 0x000231B0 File Offset: 0x000213B0
	// (set) Token: 0x06000776 RID: 1910 RVA: 0x000231CC File Offset: 0x000213CC
	public virtual string Text
	{
		get
		{
			return this.RichTextBox_0.Text;
		}
		set
		{
			this.RichTextBox_0.Text = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000777 RID: 1911 RVA: 0x000231E0 File Offset: 0x000213E0
	protected virtual void OnBackColorChanged(EventArgs e)
	{
		base.OnBackColorChanged(e);
		this.RichTextBox_0.BackColor = this.BackColor;
		base.Invalidate();
	}

	// Token: 0x06000778 RID: 1912 RVA: 0x00023200 File Offset: 0x00021400
	protected virtual void OnForeColorChanged(EventArgs e)
	{
		base.OnForeColorChanged(e);
		this.RichTextBox_0.ForeColor = this.ForeColor;
		base.Invalidate();
	}

	// Token: 0x06000779 RID: 1913 RVA: 0x00023220 File Offset: 0x00021420
	protected virtual void OnSizeChanged(EventArgs e)
	{
		base.OnSizeChanged(e);
		this.RichTextBox_0.Size = checked(new Size(base.Width - 10, base.Height - 11));
	}

	// Token: 0x0600077A RID: 1914 RVA: 0x0002324C File Offset: 0x0002144C
	protected virtual void OnFontChanged(EventArgs e)
	{
		base.OnFontChanged(e);
		this.RichTextBox_0.Font = this.Font;
	}

	// Token: 0x0600077B RID: 1915 RVA: 0x00023268 File Offset: 0x00021468
	public void method_0()
	{
		this.RichTextBox_0.Text = this.System.Windows.Forms.Control.Text;
	}

	// Token: 0x0600077C RID: 1916 RVA: 0x0002327C File Offset: 0x0002147C
	public GControl34()
	{
		base.TextChanged += this.GControl34_TextChanged;
		this.RichTextBox_0 = new RichTextBox();
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(255, 255, 255);
		this.color_2 = Color.FromArgb(35, 35, 35);
		RichTextBox richTextBox = this.RichTextBox_0;
		richTextBox.Multiline = true;
		richTextBox.BackColor = this.color_0;
		richTextBox.ForeColor = this.color_1;
		richTextBox.Text = string.Empty;
		richTextBox.BorderStyle = BorderStyle.None;
		richTextBox.Location = new Point(5, 5);
		richTextBox.Font = new Font("Segeo UI", 9f);
		richTextBox.Size = checked(new Size(base.Width - 10, base.Height - 10));
		base.Controls.Add(this.RichTextBox_0);
		base.Size = new Size(135, 35);
		this.DoubleBuffered = true;
	}

	// Token: 0x0600077D RID: 1917 RVA: 0x0002338C File Offset: 0x0002158C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rectangle = checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1));
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.color_0);
		graphics2.DrawRectangle(new Pen(this.color_2, 2f), base.ClientRectangle);
		base.OnPaint(e);
		graphics.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
		bitmap.Dispose();
	}

	// Token: 0x0600077E RID: 1918 RVA: 0x0002343C File Offset: 0x0002163C
	[DebuggerHidden]
	[CompilerGenerated]
	private void GControl34_TextChanged(object sender, EventArgs e)
	{
		this.method_0();
	}

	// Token: 0x0400037E RID: 894
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("TB")]
	private RichTextBox richTextBox_0;

	// Token: 0x0400037F RID: 895
	private Color color_0;

	// Token: 0x04000380 RID: 896
	private Color color_1;

	// Token: 0x04000381 RID: 897
	private Color color_2;
}
