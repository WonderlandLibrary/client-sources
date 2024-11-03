#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

void main( void ) {
    vec2 uv = (gl_FragCoord.xy / resolution.xy) * 2.0 - 1.0;
    uv.y *= resolution.y / resolution.x;
    
    float dist = length(uv);
    float angle = atan(uv.y, uv.x);
    
    // 创建中心波纹效果
    float rippleEffect = sin(8.0 * dist - time * 2.0) * 0.1 / dist;
    
    // 彩虹色效果
    vec3 color = 0.5 + 0.5 * cos(time + uv.xyx + vec3(0, 2.0, 4.0));
    
    // 根据波纹效果调整颜色
    color += rippleEffect;
    
    // 输出颜色
    gl_FragColor = vec4(color, 1.0);
}
