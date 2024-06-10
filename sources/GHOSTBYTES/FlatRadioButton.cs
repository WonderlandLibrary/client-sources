using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000016 RID: 22
	[DefaultEvent("CheckedChanged")]
	internal class FlatRadioButton : Control
	{
		// Token: 0x14000002 RID: 2
		// (add) Token: 0x06000135 RID: 309 RVA: 0x00006F80 File Offset: 0x00005180
		// (remove) Token: 0x06000136 RID: 310 RVA: 0x00006FB8 File Offset: 0x000051B8
		public event FlatRadioButton.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x17000076 RID: 118
		// (get) Token: 0x06000137 RID: 311 RVA: 0x000032F1 File Offset: 0x000014F1
		// (set) Token: 0x06000138 RID: 312 RVA: 0x00006FF0 File Offset: 0x000051F0
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				this.InvalidateControls();
				FlatRadioButton.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
				if (checkedChangedEvent != null)
				{
					checkedChangedEvent(this);
				}
				base.Invalidate();
			}
		}

        private void CheckedChangedEvent(object sender)
        {
            throw new NotImplementedException();
        }

        // Token: 0x06000139 RID: 313 RVA: 0x000032F9 File Offset: 0x000014F9
        protected override void OnClick(EventArgs e)
		{
			if (!this._Checked)
			{
				this.Checked = true;
			}
			base.OnClick(e);
		}

		// Token: 0x0600013A RID: 314 RVA: 0x00007024 File Offset: 0x00005224
		private void InvalidateControls()
		{
			if (base.IsHandleCreated && this._Checked)
			{
				try
				{
					foreach (object obj in base.Parent.Controls)
					{
						Control control = (Control)obj;
						if (control != this && control is RadioButton)
						{
							((RadioButton)control).Checked = false;
							base.Invalidate();
						}
					}
				}
				finally
				{
					
				}
			}
		}

		// Token: 0x0600013B RID: 315 RVA: 0x00003311 File Offset: 0x00001511
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			this.InvalidateControls();
		}

		// Token: 0x17000077 RID: 119
		// (get) Token: 0x0600013C RID: 316 RVA: 0x0000331F File Offset: 0x0000151F
		// (set) Token: 0x0600013D RID: 317 RVA: 0x00003327 File Offset: 0x00001527
		[Category("Options")]
		public FlatRadioButton._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		// Token: 0x0600013E RID: 318 RVA: 0x00003330 File Offset: 0x00001530
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 22;
		}

		// Token: 0x17000078 RID: 120
		// (get) Token: 0x0600013F RID: 319 RVA: 0x00003341 File Offset: 0x00001541
		// (set) Token: 0x06000140 RID: 320 RVA: 0x00003349 File Offset: 0x00001549
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		// Token: 0x17000079 RID: 121
		// (get) Token: 0x06000141 RID: 321 RVA: 0x00003352 File Offset: 0x00001552
		// (set) Token: 0x06000142 RID: 322 RVA: 0x0000335A File Offset: 0x0000155A
		[Category("Colors")]
		public Color BorderColor
		{
			get
			{
				return this._BorderColor;
			}
			set
			{
				this._BorderColor = value;
			}
		}

		// Token: 0x06000143 RID: 323 RVA: 0x00003363 File Offset: 0x00001563
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000144 RID: 324 RVA: 0x00003379 File Offset: 0x00001579
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000145 RID: 325 RVA: 0x0000338F File Offset: 0x0000158F
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000146 RID: 326 RVA: 0x000033A5 File Offset: 0x000015A5
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000147 RID: 327 RVA: 0x000070B0 File Offset: 0x000052B0
		public FlatRadioButton()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(60, 60, 60);
			this._BorderColor = Color.FromArgb(0, 170, 220);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Cursor = Cursors.Hand;
			base.Size = new Size(100, 22);
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.Font = new Font("Segoe UI", 10f);
		}

		// Token: 0x06000148 RID: 328 RVA: 0x00007160 File Offset: 0x00005360
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
				this.W = base.Width - 1;
				this.H = base.Height - 1;
				Rectangle rect = new Rectangle(0, 2, base.Height - 5, base.Height - 5);
				Rectangle rect2 = new Rectangle(4, 6, this.H - 12, this.H - 12);
				Graphics g = Helpers.G;
				g.SmoothingMode = SmoothingMode.HighQuality;
				g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				g.Clear(this.BackColor);
				FlatRadioButton._Options o = this.O;
				if (o != FlatRadioButton._Options.Style1)
				{
					if (o == FlatRadioButton._Options.Style2)
					{
						g.FillEllipse(new SolidBrush(this._BaseColor), rect);
						MouseState state = this.State;
						if (state != MouseState.Over)
						{
							if (state == MouseState.Down)
							{
								g.DrawEllipse(new Pen(this._BorderColor), rect);
								g.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
							}
						}
						else
						{
							g.DrawEllipse(new Pen(this._BorderColor), rect);
							g.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
						}
						if (this.Checked)
						{
							g.FillEllipse(new SolidBrush(this._BorderColor), rect2);
						}
						if (!base.Enabled)
						{
							g.FillEllipse(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
							g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(48, 119, 91)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
						}
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
					}
				}
				else
				{
					g.FillEllipse(new SolidBrush(this._BaseColor), rect);
					MouseState state2 = this.State;
					if (state2 != MouseState.Over)
					{
						if (state2 == MouseState.Down)
						{
							g.DrawEllipse(new Pen(this._BorderColor), rect);
						}
					}
					else
					{
						g.DrawEllipse(new Pen(this._BorderColor), rect);
					}
					if (this.Checked)
					{
						g.FillEllipse(new SolidBrush(this._BorderColor), rect2);
					}
					if (!base.Enabled)
					{
						g.FillEllipse(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
						g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(140, 142, 143)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
					}
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
				}
				base.OnPaint(e);
				Helpers.G.Dispose();
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
				Helpers.B.Dispose();
			}
		}

		// Token: 0x04000051 RID: 81
		private MouseState State;

		// Token: 0x04000052 RID: 82
		private int W;

		// Token: 0x04000053 RID: 83
		private int H;

		// Token: 0x04000054 RID: 84
		private FlatRadioButton._Options O;

		// Token: 0x04000055 RID: 85
		private bool _Checked;

		// Token: 0x04000057 RID: 87
		private Color _BaseColor;

		// Token: 0x04000058 RID: 88
		private Color _BorderColor;

		// Token: 0x04000059 RID: 89
		private Color _TextColor;

		// Token: 0x02000017 RID: 23
		// (Invoke) Token: 0x0600014C RID: 332
		public delegate void CheckedChangedEventHandler(object sender);

		// Token: 0x02000018 RID: 24
		[Flags]
		public enum _Options
		{
			// Token: 0x0400005B RID: 91
			Style1 = 0,
			// Token: 0x0400005C RID: 92
			Style2 = 1
		}
	}
}
