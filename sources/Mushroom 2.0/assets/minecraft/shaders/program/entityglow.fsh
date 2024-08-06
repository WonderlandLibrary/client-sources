#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D Sampler2;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 BlurDir;
uniform float Radius;

out vec4 fragColor;

void main() {
  vec4 sampler = texture(DiffuseSampler, texCoord);
  if (sampler.a >= 1.0) {
    fragColor = vec4(sampler.rgb, (BlurDir.y == 1.0 ? 0.1 : 1.0));
  } else {
    vec3 blurs = sampler.rgb;
    float totalColor = 0.0;
    float alpha = sampler.a;
    if (alpha > 0.0) totalColor++;
    float gradient = 1 / (Radius + 1.0);
    vec2 oneStep = oneTexel * BlurDir;
    float interval = 2.0;
    for (float r = -Radius; r < 0; r += interval) {
      vec4 sample = texture(DiffuseSampler, texCoord + oneStep * r);
      if (sample.a > 0.0) {
        interval = 1.0;
        totalColor++;
        blurs += sample.rgb;
        alpha = max(alpha, sample.a + r * gradient);
      }
    }
    interval = -2.0;
    for (float r = Radius; r > 0; r += interval) {
      vec4 sample = texture(DiffuseSampler, texCoord + oneStep * r);
      if (sample.a > 0.0) {
        interval = -1.0;
        totalColor++;
        blurs += sample.rgb;
        alpha = max(alpha, sample.a - r * gradient);
      }
    }
    fragColor = alpha > 0.0 ? vec4(blurs * 1 / totalColor, alpha + (BlurDir.y == 1.0 ? gradient : 0.0)) : vec4(0.0);
  }
}
