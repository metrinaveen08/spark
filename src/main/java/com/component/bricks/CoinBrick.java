package com.component.bricks;

import com.file.Parser;
import xtorq.Component;
import xtorq.GameObject;

public class CoinBrick extends Brick {

    @Override
    public void brickHit(GameObject player) {

    }

    @Override
    public Component copy() {
        return new CoinBrick();
    }

    @Override
    public String serialize(int tabSize) {
        return beginObjectProperty("CoinBrick", tabSize) +
                closeObjectProperty(tabSize);
    }

    public static CoinBrick deserialize() {
        Parser.consumeEndObjectProperty();

        return new CoinBrick();
    }
}
