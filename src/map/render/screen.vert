varying vec4 color;
uniform vec3 light;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;

    color = gl_Color;
}