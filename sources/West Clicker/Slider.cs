using System;
using System.Drawing;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace AnyDesk
{
    class Slider : Panel
    {
        private float value;
        public float Value
        {
            get
            {
                return value;
            }
            set
            {
                SetValue(value);
            }
        }
        private void SetValue(float v)
        {
            if (v < 0.0f) v = 0.0f;
            else if (v > 1.0f) v = 1.0f;
            value = v;
        }

        private Brush brush;
        private Graphics g;

        public Slider()
        {
            brush = new SolidBrush(Color.FromArgb(28, 74, 191));
        }

        protected override void OnMouseMove(MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                SetValue((float)e.Location.X / this.Width);
                OnPaint(null);
            }
            base.OnMouseMove(e);
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            if (g == null) g = CreateGraphics();
            g.Clear(this.BackColor);
            g.FillRectangle(new SolidBrush(Color.FromArgb(28, 74, 191)), 1, 1, (this.Width) * value, this.Height);
        }
    }
}
