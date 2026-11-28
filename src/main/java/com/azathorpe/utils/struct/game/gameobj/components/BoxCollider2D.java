package com.azathorpe.utils.struct.game.gameobj.components;

import com.azathorpe.utils.struct.game.gameobj.Component;
import com.azathorpe.utils.struct.game.gameobj.components.base.Transform;

/**
 * 2D 碰撞箱
 */
public abstract class BoxCollider2D extends Component implements MouseInteractive {
    /**
     * 碰撞箱尺寸（局部半宽/半高，不含位置）
     */
    public float halfWidth = 0.2f;
    public float halfHeight = 0.2f;

    /**
     * 是否作为触发器（不阻挡，只回调）
     */
    public boolean isTrigger = false;

    /**
     * 从 owner 的 Transform 取世界位置，得到 AABB
     */
    public float getCenterX() {
        return getOwner().getComponent(Transform.class).position.x;
    }

    public float getCenterY() {
        return getOwner().getComponent(Transform.class).position.y;
    }

    // AABB 相交判断
    public boolean intersects(BoxCollider2D other) {
        return Math.abs(getCenterX() - other.getCenterX()) < (halfWidth + other.halfWidth)
                && Math.abs(getCenterY() - other.getCenterY()) < (halfHeight + other.halfHeight);
    }

    /**
     * 世界坐标点是否在 AABB 内
     */
    public boolean contains(float x, float y) {
        return Math.abs(x - getCenterX()) <= halfWidth
                && Math.abs(y - getCenterY()) <= halfHeight;
    }

    // 回调（子类实现）
    public void onEnterCollider2D(BoxCollider2D other) {
    }

    public void onStayCollider2D(BoxCollider2D other) {
    }

    public void onExitCollider2D(BoxCollider2D other) {
    }
}