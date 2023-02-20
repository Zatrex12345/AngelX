package me.xtasy.anticheat.check.checks;

import me.xtasy.anticheat.check.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.data.*;

public class PositionCheck extends Check
{

    
    public PositionCheck(final PlayerData playerData) {
        super(playerData, PositionUpdate.class);
    }
    

    public void run(final PositionUpdate positionUpdate) {
    }
}
