using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static Dope.Theme.ThemeUtils;

namespace Dope.Theme
{
    public class Slots : Control
    {
        private MouseState State = MouseState.None;
        private bool Checked_;
        private int Key_;

        public Slots()
        {
            SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.OptimizedDoubleBuffer, true);
            DoubleBuffered = true;
        }

        [Browsable(true)]
        [Category("Action")]
        [Description("When Check change")]
        public event EventHandler CheckChange;

        [Browsable(true)]
        [Category("Action")]
        [Description("When Key change")]
        public event EventHandler KeyChange;

        public bool Checked
        {
            get { return Checked_; }
            set
            {
                Checked_ = value;
                CheckChange?.Invoke(this, new EventArgs());
                Invalidate();
            }
        }

        public int Key
        {
            get { return Key_; }
            set
            {
                Key_ = value;
                KeyChange?.Invoke(this, new EventArgs());
            }
        }

        protected override void OnMouseDown(MouseEventArgs e)
        {
            base.OnMouseDown(e);
            State = MouseState.Down;
            if (e.Button == MouseButtons.Right)
            {
                Text = "...";
                Focus();
            }
            if (e.Button == MouseButtons.Left)
            {
                Checked = !Checked;
            }
            Invalidate();
        }

        protected override void OnMouseUp(MouseEventArgs e)
        {
            base.OnMouseUp(e);
            State = MouseState.Over;
            Invalidate();
        }

        protected override void OnMouseEnter(EventArgs e)
        {
            base.OnMouseEnter(e);
            State = MouseState.Over;
            Invalidate();
        }

        protected override void OnMouseLeave(EventArgs e)
        {
            base.OnMouseLeave(e);
            State = MouseState.None;
            Invalidate();
        }

        protected override void OnKeyDown(KeyEventArgs e)
        {
            base.OnKeyDown(e);
            if (e.KeyCode != Keys.Back)
            {
                Key = (int)e.KeyCode;
                Text = GetCharsFromKeys(e.KeyCode, false);
                if (Text == "")
                {
                    Text = "";
                }
            }
            else
            {
                Key = 0;
                Text = "";
            }
            FindForm().ActiveControl = null;
            Invalidate();
        }

        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            Width = Height;
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            ///basic setup
            Bitmap B = new Bitmap(Width, Height);
            Graphics G = Graphics.FromImage(B);
            G.SmoothingMode = SmoothingMode.HighQuality;
            G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;

            int W = Width - 1;
            int H = Height - 1;
            //Box
            if (Checked)
            {
                G.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220,220)),
                    RoundRec(new Rectangle(0,0,W,H),5));
            }
            else
            {
                G.DrawPath(new Pen(new SolidBrush(Color.FromArgb(255, 220, 220, 220))), 
                    RoundRec(new Rectangle(0,0,W,H),5));
            }

            if (State == MouseState.Over || Focused)
            {
                Font f = new Font(Font.FontFamily, 8f);
                int TW = (int)G.MeasureString(Text, f).Width - 1;
                int TH = (int)G.MeasureString(Text, f).Height - 1;

                G.DrawString(Text, f, new SolidBrush(Checked ? BackColor : ForeColor), 
                    (int)(Width/2 - TW/2), (int)(Height/2 - TH/2));
            }

            base.OnPaint(e);
            G.Dispose();
            e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
            e.Graphics.DrawImageUnscaled(B, 0, 0);
            B.Dispose();
        }
    }
}
