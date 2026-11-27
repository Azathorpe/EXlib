package com.azathorpe.utils.struct.game;

import com.azathorpe.utils.struct.game.components.BoxCollider2D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhysicsSystem {
    /**
     * 上一帧处于碰撞的 pair 集合
     */
    private final Set<CollisionPair> previousPairs = new HashSet<>();

    public void update(List<BoxCollider2D> colliders) {
        Set<CollisionPair> currentPairs = new HashSet<>();

        // 两两检测（O(n²)，Pong 这种物体数完全够）
        for (int i = 0; i < colliders.size(); i++) {
            for (int j = i + 1; j < colliders.size(); j++) {
                BoxCollider2D a = colliders.get(i);
                BoxCollider2D b = colliders.get(j);
                if (a.getOwner() == b.getOwner()) continue;   // 同一物体跳过

                if (a.intersects(b)) {
                    CollisionPair pair = new CollisionPair(a, b);
                    currentPairs.add(pair);

                    if (previousPairs.contains(pair)) {
                        a.onStayCollider2D(b);
                        b.onStayCollider2D(a);
                    } else {
                        a.onEnterCollider2D(b);
                        b.onEnterCollider2D(a);
                    }
                }
            }
        }

        // 上帧碰、本帧不碰 → exit
        for (CollisionPair pair : previousPairs) {
            if (!currentPairs.contains(pair)) {
                pair.a.onExitCollider2D(pair.b);
                pair.b.onExitCollider2D(pair.a);
            }
        }

        // 更新状态
        previousPairs.clear();
        previousPairs.addAll(currentPairs);
    }
}