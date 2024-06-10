using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000021 RID: 33
	[DefaultEvent("TextChanged")]
	internal class FlatTextBox : Control
	{
		// Token: 0x170000B5 RID: 181
		// (get) Token: 0x0600026A RID: 618 RVA: 0x00011DD5 File Offset: 0x0000FFD5
		// (set) Token: 0x0600026B RID: 619 RVA: 0x00011DDD File Offset: 0x0000FFDD
		private virtual TextBox TB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000B6 RID: 182
		// (get) Token: 0x0600026C RID: 620 RVA: 0x00011DE6 File Offset: 0x0000FFE6
		// (set) Token: 0x0600026D RID: 621 RVA: 0x00011DEE File Offset: 0x0000FFEE
		[Category("Options")]
		public HorizontalAlignment TextAlign
		{
			get
			{
				return this._TextAlign;
			}
			set
			{
				this._TextAlign = value;
				if (this.TB != null)
				{
					this.TB.TextAlign = value;
				}
			}
		}

		// Token: 0x170000B7 RID: 183
		// (get) Token: 0x0600026E RID: 622 RVA: 0x00011E0B File Offset: 0x0001000B
		// (set) Token: 0x0600026F RID: 623 RVA: 0x00011E13 File Offset: 0x00010013
		[Category("Options")]
		public int MaxLength
		{
			get
			{
				return this._MaxLength;
			}
			set
			{
				this._MaxLength = value;
				if (this.TB != null)
				{
					this.TB.MaxLength = value;
				}
			}
		}

		// Token: 0x170000B8 RID: 184
		// (get) Token: 0x06000270 RID: 624 RVA: 0x00011E30 File Offset: 0x00010030
		// (set) Token: 0x06000271 RID: 625 RVA: 0x00011E38 File Offset: 0x00010038
		[Category("Options")]
		public bool ReadOnly
		{
			get
			{
				return this._ReadOnly;
			}
			set
			{
				this._ReadOnly = value;
				if (this.TB != null)
				{
					this.TB.ReadOnly = value;
				}
			}
		}

		// Token: 0x170000B9 RID: 185
		// (get) Token: 0x06000272 RID: 626 RVA: 0x00011E55 File Offset: 0x00010055
		// (set) Token: 0x06000273 RID: 627 RVA: 0x00011E5D File Offset: 0x0001005D
		[Category("Options")]
		public bool UseSystemPasswordChar
		{
			get
			{
				return this._UseSystemPasswordChar;
			}
			set
			{
				this._UseSystemPasswordChar = value;
				if (this.TB != null)
				{
					this.TB.UseSystemPasswordChar = value;
				}
			}
		}

		// Token: 0x170000BA RID: 186
		// (get) Token: 0x06000274 RID: 628 RVA: 0x00011E7A File Offset: 0x0001007A
		// (set) Token: 0x06000275 RID: 629 RVA: 0x00011E84 File Offset: 0x00010084
		[Category("Options")]
		public bool Multiline
		{
			get
			{
				return this._Multiline;
			}
			set
			{
				this._Multiline = value;
				if (this.TB != null)
				{
					this.TB.Multiline = value;
					if (value)
					{
						this.TB.Height = base.Height - 11;
						return;
					}
					base.Height = this.TB.Height + 11;
				}
			}
		}

		// Token: 0x170000BB RID: 187
		// (get) Token: 0x06000276 RID: 630 RVA: 0x00011ED8 File Offset: 0x000100D8
		// (set) Token: 0x06000277 RID: 631 RVA: 0x00011EE0 File Offset: 0x000100E0
		[Category("Options")]
		public override string Text
		{
			get
			{
				return base.Text;
			}
			set
			{
				base.Text = value;
				if (this.TB != null)
				{
					this.TB.Text = value;
				}
			}
		}

		// Token: 0x170000BC RID: 188
		// (get) Token: 0x06000278 RID: 632 RVA: 0x00011EFD File Offset: 0x000100FD
		// (set) Token: 0x06000279 RID: 633 RVA: 0x00011F08 File Offset: 0x00010108
		[Category("Options")]
		public override Font Font
		{
			get
			{
				return base.Font;
			}
			set
			{
				base.Font = value;
				if (this.TB != null)
				{
					this.TB.Font = value;
					this.TB.Location = new Point(3, 5);
					this.TB.Width = base.Width - 6;
					if (!this._Multiline)
					{
						base.Height = this.TB.Height + 11;
					}
				}
			}
		}

		// Token: 0x0600027A RID: 634 RVA: 0x00011F71 File Offset: 0x00010171
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			if (!base.Controls.Contains(this.TB))
			{
				base.Controls.Add(this.TB);
			}
		}

		// Token: 0x0600027B RID: 635 RVA: 0x00011F9D File Offset: 0x0001019D
		private void OnBaseTextChanged(object s, EventArgs e)
		{
			this.Text = this.TB.Text;
		}

		// Token: 0x0600027C RID: 636 RVA: 0x00011FB0 File Offset: 0x000101B0
		private void OnBaseKeyDown(object s, KeyEventArgs e)
		{
			if (e.Control && e.KeyCode == Keys.A)
			{
				this.TB.SelectAll();
				e.SuppressKeyPress = true;
			}
			if (e.Control && e.KeyCode == Keys.C)
			{
				this.TB.Copy();
				e.SuppressKeyPress = true;
			}
		}

		// Token: 0x0600027D RID: 637 RVA: 0x00012008 File Offset: 0x00010208
		protected override void OnResize(EventArgs e)
		{
			this.TB.Location = new Point(5, 5);
			this.TB.Width = base.Width - 10;
			if (this._Multiline)
			{
				this.TB.Height = base.Height - 11;
			}
			else
			{
				base.Height = this.TB.Height + 11;
			}
			base.OnResize(e);
		}

		// Token: 0x170000BD RID: 189
		// (get) Token: 0x0600027E RID: 638 RVA: 0x00012074 File Offset: 0x00010274
		// (set) Token: 0x0600027F RID: 639 RVA: 0x0001207C File Offset: 0x0001027C
		[Category("Colors")]
		public Color TextColor
		{
			get
			{
				return this._TextColor;
			}
			set
			{
				this._TextColor = value;
			}
		}

		// Token: 0x170000BE RID: 190
		// (get) Token: 0x06000280 RID: 640 RVA: 0x00012074 File Offset: 0x00010274
		// (set) Token: 0x06000281 RID: 641 RVA: 0x0001207C File Offset: 0x0001027C
		public override Color ForeColor
		{
			get
			{
				return this._TextColor;
			}
			set
			{
				this._TextColor = value;
			}
		}

		// Token: 0x06000282 RID: 642 RVA: 0x00012085 File Offset: 0x00010285
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000283 RID: 643 RVA: 0x0001209B File Offset: 0x0001029B
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x06000284 RID: 644 RVA: 0x000120BD File Offset: 0x000102BD
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x06000285 RID: 645 RVA: 0x000120DF File Offset: 0x000102DF
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000286 RID: 646 RVA: 0x000120F8 File Offset: 0x000102F8
		public FlatTextBox()
		{
			this.State = MouseState.None;
			this._TextAlign = HorizontalAlignment.Left;
			this._MaxLength = 32767;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._TextColor = Color.FromArgb(192, 192, 192);
			this._BorderColor = Helpers._FlatColor;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			this.TB = new TextBox();
			this.TB.Font = new Font("Segoe UI", 10f);
			this.TB.Text = this.Text;
			this.TB.BackColor = this._BaseColor;
			this.TB.ForeColor = this._TextColor;
			this.TB.MaxLength = this._MaxLength;
			this.TB.Multiline = this._Multiline;
			this.TB.ReadOnly = this._ReadOnly;
			this.TB.UseSystemPasswordChar = this._UseSystemPasswordChar;
			this.TB.BorderStyle = BorderStyle.None;
			this.TB.Location = new Point(5, 5);
			this.TB.Width = base.Width - 10;
			this.TB.Cursor = Cursors.IBeam;
			if (this._Multiline)
			{
				this.TB.Height = base.Height - 11;
			}
			else
			{
				base.Height = this.TB.Height + 11;
			}
			this.TB.TextChanged += this.OnBaseTextChanged;
			this.TB.KeyDown += this.OnBaseKeyDown;
		}

		// Token: 0x06000287 RID: 647 RVA: 0x000122B8 File Offset: 0x000104B8
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			Rectangle rect = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			this.TB.BackColor = this._BaseColor;
			this.TB.ForeColor = this._TextColor;
			g.FillRectangle(new SolidBrush(this._BaseColor), rect);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x0400013F RID: 319
		private int W;

		// Token: 0x04000140 RID: 320
		private int H;

		// Token: 0x04000141 RID: 321
		private MouseState State;

		// Token: 0x04000143 RID: 323
		private HorizontalAlignment _TextAlign;

		// Token: 0x04000144 RID: 324
		private int _MaxLength;

		// Token: 0x04000145 RID: 325
		private bool _ReadOnly;

		// Token: 0x04000146 RID: 326
		private bool _UseSystemPasswordChar;

		// Token: 0x04000147 RID: 327
		private bool _Multiline;

		// Token: 0x04000148 RID: 328
		private Color _BaseColor;

		// Token: 0x04000149 RID: 329
		private Color _TextColor;

		// Token: 0x0400014A RID: 330
		private Color _BorderColor;
	}
}
