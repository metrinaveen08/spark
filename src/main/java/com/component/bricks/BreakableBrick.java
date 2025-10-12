package com.component.bricks;

import com.component.PlayerController;
import com.component.enums.PlayerType;
import com.dataStructure.AssetPool;
import com.file.Parser;
import xtorq.Component;
import xtorq.GameObject;
import xtorq.Window;

public class BreakableBrick extends Brick {
    @Override
    public void brickHit(GameObject player) {
        PlayerController playerController = player.getComponent(PlayerController.class);
        if (playerController.type == PlayerType.BIG || playerController.type == PlayerType.FIRE) {
            AssetPool.getSound("assets/sounds/break_block.ogg").play();
            Window.getScene().deleteGameObject(this.gameObject);
        } else {
            AssetPool.getSound("assets/sounds/bump.ogg").play();
        }
    }

    @Override
    public Component copy() {
        return new BreakableBrick();
    }

    @Override
    public String serialize(int tabSize) {
        return beginObjectProperty("BreakableBrick", tabSize) +
                closeObjectProperty(tabSize);
    }

    public static BreakableBrick deserialize() {
        Parser.consumeEndObjectProperty();

        return new BreakableBrick();
    }
}
