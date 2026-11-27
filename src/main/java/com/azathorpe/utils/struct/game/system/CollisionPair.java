package com.azathorpe.utils.struct.game.system;

import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;

public class CollisionPair {
    final BoxCollider2D a, b;
    CollisionPair(BoxCollider2D x, BoxCollider2D y) {
        // 用 identityHashCode 或 UUID 保证顺序稳定
        if (System.identityHashCode(x) <= System.identityHashCode(y)) {
            a = x; b = y;
        } else {
            a = y; b = x;
        }
    }
    @Override public boolean equals(Object o) {
        if (!(o instanceof CollisionPair p)) return false;
        return a == p.a && b == p.b;
    }
    @Override public int hashCode() {
        return System.identityHashCode(a) * 31 + System.identityHashCode(b);
    }
}