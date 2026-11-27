package com.azathorpe.utils.struct.game.gameobj;

/**
 * 组件基类
 */
public abstract class Component {
    protected GameObject owner;

    public GameObject getOwner() {
        return owner;
    }

    protected void setOwner(GameObject owner) {
        this.owner = owner;
    }
}
