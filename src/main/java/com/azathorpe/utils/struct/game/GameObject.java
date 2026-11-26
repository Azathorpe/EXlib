package com.azathorpe.utils.struct.game;

public class GameObject {
    Mesh mesh;
    Material material;
    Transform transform;

    public GameObject() {
    }

    public GameObject(Mesh mesh, Material material) {
        this.mesh = mesh;
    }


    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
