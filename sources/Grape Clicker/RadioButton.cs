using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	[DefaultEvent("CheckedChanged")]
	internal class RadioButton : Control
	{
		private MouseState State;

		private int W;

		private int H;

		private Xh0kO1ZCmA.RadioButton._Options O;

		private bool _Checked;

		private Color _BaseColor;

		private Color _BorderColor;

		private Color _TextColor;

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
				Xh0kO1ZCmA.RadioButton.CheckedChangedEventHandler checkedChangedEventHandler = this.CheckedChanged;
				if (checkedChangedEventHandler != null)
				{
					checkedChangedEventHandler(this);
				}
				base.Invalidate();
			}
		}

		[Category("Options")]
		public Xh0kO1ZCmA.RadioButton._Options Options
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

		public RadioButton()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._BorderColor = Helpers._FlatColor;
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Cursor = Cursors.Hand;
			base.Size = new System.Drawing.Size(100, 22);
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.Font = new System.Drawing.Font("Segoe UI", 10f);
		}

		private void InvalidateControls()
		{
			IEnumerator enumerator = null;
			if (base.IsHandleCreated && this._Checked)
			{
				try
				{
					enumerator = base.Parent.Controls.GetEnumerator();
					while (enumerator.MoveNext())
					{
						Control current = (Control)enumerator.Current;
						if (current == this || !(current is Xh0kO1ZCmA.RadioButton))
						{
							continue;
						}
						((Xh0kO1ZCmA.RadioButton)current).Checked = false;
						base.Invalidate();
					}
				}
				finally
				{
					if (enumerator is IDisposable)
					{
						(enumerator as IDisposable).Dispose();
					}
				}
			}
		}

		protected override void OnClick(EventArgs e)
		{
			if (!this._Checked)
			{
				this.Checked = true;
			}
			base.OnClick(e);
		}

		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			this.InvalidateControls();
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			Rectangle rectangle = new Rectangle(0, 2, checked(base.Height - 5), checked(base.Height - 5));
			Rectangle rectangle1 = new Rectangle(4, 6, checked(this.H - 12), checked(this.H - 12));
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			Xh0kO1ZCmA.RadioButton._Options o = this.O;
			if (o == Xh0kO1ZCmA.RadioButton._Options.Style1)
			{
				g.FillEllipse(new SolidBrush(this._BaseColor), rectangle);
				MouseState state = this.State;
				if (state == MouseState.Over)
				{
					g.DrawEllipse(new Pen(this._BorderColor), rectangle);
				}
				else if (state == MouseState.Down)
				{
					g.DrawEllipse(new Pen(this._BorderColor), rectangle);
				}
				if (this.Checked)
				{
					g.FillEllipse(new SolidBrush(this._BorderColor), rectangle1);
				}
			}
			else if (o == Xh0kO1ZCmA.RadioButton._Options.Style2)
			{
				g.FillEllipse(new SolidBrush(this._BaseColor), rectangle);
				MouseState mouseState = this.State;
				if (mouseState == MouseState.Over)
				{
					g.DrawEllipse(new Pen(this._BorderColor), rectangle);
					g.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rectangle);
				}
				else if (mouseState == MouseState.Down)
				{
					g.DrawEllipse(new Pen(this._BorderColor), rectangle);
					g.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rectangle);
				}
				if (this.Checked)
				{
					g.FillEllipse(new SolidBrush(this._BorderColor), rectangle1);
				}
			}
			g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
			g = null;
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 22;
		}

		public event Xh0kO1ZCmA.RadioButton.CheckedChangedEventHandler CheckedChanged;

		[Flags]
		public enum _Options
		{
			Style1,
			Style2
		}

		public delegate void CheckedChangedEventHandler(object sender);
	}
}