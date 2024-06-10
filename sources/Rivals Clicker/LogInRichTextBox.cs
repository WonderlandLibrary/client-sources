using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000029 RID: 41
	public class LogInRichTextBox : Control
	{
		// Token: 0x170000F8 RID: 248
		// (get) Token: 0x060002AF RID: 687 RVA: 0x000032CE File Offset: 0x000014CE
		// (set) Token: 0x060002B0 RID: 688 RVA: 0x000032D8 File Offset: 0x000014D8
		private virtual RichTextBox TB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000F9 RID: 249
		// (get) Token: 0x060002B1 RID: 689 RVA: 0x0000FA9C File Offset: 0x0000DC9C
		// (set) Token: 0x060002B2 RID: 690 RVA: 0x000032E1 File Offset: 0x000014E1
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x170000FA RID: 250
		// (get) Token: 0x060002B3 RID: 691 RVA: 0x0000FAB4 File Offset: 0x0000DCB4
		// (set) Token: 0x060002B4 RID: 692 RVA: 0x000032EB File Offset: 0x000014EB
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x170000FB RID: 251
		// (get) Token: 0x060002B5 RID: 693 RVA: 0x0000FACC File Offset: 0x0000DCCC
		// (set) Token: 0x060002B6 RID: 694 RVA: 0x000032F5 File Offset: 0x000014F5
		[Category("Colours")]
		public Color TextColour
		{
			get
			{
				return this._TextColour;
			}
			set
			{
				this._TextColour = value;
			}
		}

		// Token: 0x060002B7 RID: 695 RVA: 0x000032FF File Offset: 0x000014FF
		public void AppendText(string AppendingText)
		{
			this.TB.Focus();
			this.TB.AppendText(AppendingText);
			base.Invalidate();
		}

		// Token: 0x170000FC RID: 252
		// (get) Token: 0x060002B8 RID: 696 RVA: 0x0000FAE4 File Offset: 0x0000DCE4
		// (set) Token: 0x060002B9 RID: 697 RVA: 0x00003322 File Offset: 0x00001522
		public override string Text
		{
			get
			{
				return this.TB.Text;
			}
			set
			{
				this.TB.Text = value;
				base.Invalidate();
			}
		}

		// Token: 0x060002BA RID: 698 RVA: 0x00003339 File Offset: 0x00001539
		protected override void OnBackColorChanged(EventArgs e)
		{
			base.OnBackColorChanged(e);
			this.TB.BackColor = this.BackColor;
			base.Invalidate();
		}

		// Token: 0x060002BB RID: 699 RVA: 0x0000335D File Offset: 0x0000155D
		protected override void OnForeColorChanged(EventArgs e)
		{
			base.OnForeColorChanged(e);
			this.TB.ForeColor = this.ForeColor;
			base.Invalidate();
		}

		// Token: 0x060002BC RID: 700 RVA: 0x00003381 File Offset: 0x00001581
		protected override void OnSizeChanged(EventArgs e)
		{
			base.OnSizeChanged(e);
			this.TB.Size = checked(new Size(base.Width - 10, base.Height - 11));
		}

		// Token: 0x060002BD RID: 701 RVA: 0x000033AF File Offset: 0x000015AF
		protected override void OnFontChanged(EventArgs e)
		{
			base.OnFontChanged(e);
			this.TB.Font = this.Font;
		}

		// Token: 0x060002BE RID: 702 RVA: 0x000033CC File Offset: 0x000015CC
		public void TextChanges()
		{
			this.TB.Text = this.Text;
		}

		// Token: 0x060002BF RID: 703 RVA: 0x0000FB04 File Offset: 0x0000DD04
		public LogInRichTextBox()
		{
			base.TextChanged += delegate(object a0, EventArgs a1)
			{
				this.TextChanges();
			};
			this.TB = new RichTextBox();
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			RichTextBox tb = this.TB;
			tb.Multiline = true;
			tb.BackColor = this._BaseColour;
			tb.ForeColor = this._TextColour;
			tb.Text = string.Empty;
			tb.BorderStyle = BorderStyle.None;
			tb.Location = new Point(5, 5);
			tb.Font = new Font("Segeo UI", 9f);
			tb.Size = checked(new Size(base.Width - 10, base.Height - 10));
			base.Controls.Add(this.TB);
			base.Size = new Size(135, 35);
			this.DoubleBuffered = true;
		}

		// Token: 0x060002C0 RID: 704 RVA: 0x0000FC24 File Offset: 0x0000DE24
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Rectangle Base = checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1));
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this._BaseColour);
			graphics.DrawRectangle(new Pen(this._BorderColour, 2f), base.ClientRectangle);
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		}

		// Token: 0x0400010E RID: 270
		private Color _BaseColour;

		// Token: 0x0400010F RID: 271
		private Color _TextColour;

		// Token: 0x04000110 RID: 272
		private Color _BorderColour;
	}
}
