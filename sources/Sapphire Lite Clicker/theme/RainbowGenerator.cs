using FlatUI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

// All of the theme folder was taken from a now deleted repo from years ago.

namespace Sapphire_LITE.theme {
    public class RainbowGenerator {
        private float hue;
        private const float saturation = 0.3f;
        private const float lightness = 0.6f;

        public RainbowGenerator() {
            hue = 0;
        }

        public System.Drawing.Color Next() {
            hue += 0.01f;
            if (hue > 1) hue -= 1;

            return HSLToRGB(hue, saturation, lightness);
        }

        private System.Drawing.Color HSLToRGB(float h, float s, float l) {
            float r, g, b;

            if (s == 0) {
                r = g = b = l;
            } else {
                float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
                float p = 2 * l - q;
                r = HueToRGB(p, q, h + 1 / 3.0f);
                g = HueToRGB(p, q, h);
                b = HueToRGB(p, q, h - 1 / 3.0f);
            }

            return System.Drawing.Color.FromArgb((int)(r * 255), (int)(g * 255), (int)(b * 255));
        }

        private float HueToRGB(float p, float q, float t) {
            if (t < 0) t += 1;
            if (t > 1) t -= 1;
            if (t < 1 / 6.0f) return p + (q - p) * 6 * t;
            if (t < 1 / 2.0f) return q;
            if (t < 2 / 3.0f) return p + (q - p) * (2 / 3.0f - t) * 6;
            return p;
        }
    }
}
