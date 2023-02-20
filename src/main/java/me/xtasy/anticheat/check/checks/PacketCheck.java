package me.xtasy.anticheat.check.checks;

import me.xtasy.anticheat.check.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;

public abstract class PacketCheck extends Check
{


    public abstract void run(PacketEvent packetEvent);

    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public PacketCheck(final PlayerData playerData) {
        super(playerData, PacketEvent.class);
    }
}
