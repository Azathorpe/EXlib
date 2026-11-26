package com.azathorpe.utils.struct.game;

public abstract class Component {
    protected GameObject owner;

    public GameObject getOwner() {
        return owner;
    }

    protected void setOwner(GameObject owner) {
        this.owner = owner;
    }
}
