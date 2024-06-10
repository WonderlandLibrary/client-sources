using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x0200001C RID: 28
	[DefaultEvent("TextChanged")]
	internal class FlatTextBox : Control
	{
		// Token: 0x1700007E RID: 126
		// (get) Token: 0x06000164 RID: 356 RVA: 0x0000345D File Offset: 0x0000165D
		// (set) Token: 0x06000165 RID: 357 RVA: 0x00003465 File Offset: 0x00001665
	    public virtual TextBox TB { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007F RID: 127
		// (get) Token: 0x06000166 RID: 358 RVA: 0x0000346E File Offset: 0x0000166E
		// (set) Token: 0x06000167 RID: 359 RVA: 0x00003476 File Offset: 0x00001676
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

		// Token: 0x17000080 RID: 128
		// (get) Token: 0x06000168 RID: 360 RVA: 0x00003493 File Offset: 0x00001693
		// (set) Token: 0x06000169 RID: 361 RVA: 0x0000349B File Offset: 0x0000169B
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

		// Token: 0x17000081 RID: 129
		// (get) Token: 0x0600016A RID: 362 RVA: 0x000034B8 File Offset: 0x000016B8
		// (set) Token: 0x0600016B RID: 363 RVA: 0x000034C0 File Offset: 0x000016C0
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

		// Token: 0x17000082 RID: 130
		// (get) Token: 0x0600016C RID: 364 RVA: 0x000034DD File Offset: 0x000016DD
		// (set) Token: 0x0600016D RID: 365 RVA: 0x000034E5 File Offset: 0x000016E5
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

		// Token: 0x17000083 RID: 131
		// (get) Token: 0x0600016E RID: 366 RVA: 0x00003502 File Offset: 0x00001702
		// (set) Token: 0x0600016F RID: 367 RVA: 0x0000797C File Offset: 0x00005B7C
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
				checked
				{
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
		}

		// Token: 0x17000084 RID: 132
		// (get) Token: 0x06000170 RID: 368 RVA: 0x0000350A File Offset: 0x0000170A
		// (set) Token: 0x06000171 RID: 369 RVA: 0x00003512 File Offset: 0x00001712
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

		// Token: 0x17000085 RID: 133
		// (get) Token: 0x06000172 RID: 370 RVA: 0x0000352F File Offset: 0x0000172F
		// (set) Token: 0x06000173 RID: 371 RVA: 0x000079D0 File Offset: 0x00005BD0
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
				checked
				{
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
		}

		// Token: 0x06000174 RID: 372 RVA: 0x00003537 File Offset: 0x00001737
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			if (!base.Controls.Contains(this.TB))
			{
				base.Controls.Add(this.TB);
			}
		}

		// Token: 0x06000175 RID: 373 RVA: 0x00003563 File Offset: 0x00001763
		private void OnBaseTextChanged(object s, EventArgs e)
		{
			this.Text = this.TB.Text;
		}

		// Token: 0x06000176 RID: 374 RVA: 0x00007A3C File Offset: 0x00005C3C
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

		// Token: 0x06000177 RID: 375 RVA: 0x00007A94 File Offset: 0x00005C94
		protected override void OnResize(EventArgs e)
		{
			this.TB.Location = new Point(5, 5);
			checked
			{
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
		}

		// Token: 0x17000086 RID: 134
		// (get) Token: 0x06000178 RID: 376 RVA: 0x00003576 File Offset: 0x00001776
		// (set) Token: 0x06000179 RID: 377 RVA: 0x0000357E File Offset: 0x0000177E
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

		// Token: 0x17000087 RID: 135
		// (get) Token: 0x0600017A RID: 378 RVA: 0x00003576 File Offset: 0x00001776
		// (set) Token: 0x0600017B RID: 379 RVA: 0x0000357E File Offset: 0x0000177E
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

		// Token: 0x0600017C RID: 380 RVA: 0x00003587 File Offset: 0x00001787
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600017D RID: 381 RVA: 0x0000359D File Offset: 0x0000179D
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x0600017E RID: 382 RVA: 0x000035BF File Offset: 0x000017BF
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			this.TB.Focus();
			base.Invalidate();
		}

		// Token: 0x0600017F RID: 383 RVA: 0x000035E1 File Offset: 0x000017E1
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000180 RID: 384 RVA: 0x00007B00 File Offset: 0x00005D00
		public FlatTextBox()
		{
			this.State = MouseState.None;
			this._TextAlign = HorizontalAlignment.Left;
			this._MaxLength = 32767;
			this._BaseColor = Color.FromArgb(60, 60, 60);
			this._TextColor = Color.FromArgb(192, 192, 192);
			this._BorderColor = Color.FromArgb(0, 170, 220);
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
			checked
			{
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
		}

		// Token: 0x06000181 RID: 385 RVA: 0x00007CCC File Offset: 0x00005ECC
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
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
		}

		// Token: 0x04000069 RID: 105
		private int W;

		// Token: 0x0400006A RID: 106
		private int H;

		// Token: 0x0400006B RID: 107
		private MouseState State;

		// Token: 0x0400006D RID: 109
		private HorizontalAlignment _TextAlign;

		// Token: 0x0400006E RID: 110
		private int _MaxLength;

		// Token: 0x0400006F RID: 111
		private bool _ReadOnly;

		// Token: 0x04000070 RID: 112
		private bool _UseSystemPasswordChar;

		// Token: 0x04000071 RID: 113
		private bool _Multiline;

		// Token: 0x04000072 RID: 114
		private Color _BaseColor;

		// Token: 0x04000073 RID: 115
		private Color _TextColor;

		// Token: 0x04000074 RID: 116
		private Color _BorderColor;
	}
}
