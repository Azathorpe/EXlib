package com.azathorpe.utils.struct.game.manage;

import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;

import java.util.List;

public class MousePicker {
    private BoxCollider2D hovered = null;   // 上一帧鼠标所在的 Box

    public void update(List<BoxCollider2D> colliders, double mouseX, double mouseY) {
        // 找当前鼠标命中的 Box（多个重叠取最上层，这里取第一个/最后一个）
        BoxCollider2D current = null;
        for (BoxCollider2D c : colliders) {
            if (c.contains((float) mouseX, (float) mouseY)) {
                current = c;   // 或按层级选最上层
                break;
            }
        }

        // 对比上一帧
        if (current != hovered) {
            if (hovered != null) hovered.onMouseExit();
            if (current != null) current.onMouseEnter();
            hovered = current;
        }
    }
}