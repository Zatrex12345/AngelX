package me.xtasy.anticheat.check.checks;

import me.xtasy.anticheat.check.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.data.*;

public class RotationCheck extends Check
{

    
    public void run(final RotationUpdate rotationUpdate) {
    }
    
    public RotationCheck(final PlayerData playerData) {
        super(playerData, RotationUpdate.class);
    }
}
