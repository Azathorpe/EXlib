#version 330 core
in vec3 vPos;
out vec3 color;

void main() {
    color = vec3(vPos.x, vPos.y, vPos.z);
}