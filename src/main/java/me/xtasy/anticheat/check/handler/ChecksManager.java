package me.xtasy.anticheat.check.handler;

import me.xtasy.anticheat.check.*;
import java.util.*;
import me.xtasy.anticheat.data.*;
import com.google.common.collect.*;
import me.xtasy.anticheat.check.impl.killaura.*;
import me.xtasy.anticheat.check.impl.velocity.*;
import me.xtasy.anticheat.check.impl.groundspoof.*;
import me.xtasy.anticheat.check.impl.speed.*;
import me.xtasy.anticheat.check.impl.inventory.*;
import me.xtasy.anticheat.check.impl.timer.*;
import me.xtasy.anticheat.check.impl.aim.*;
import me.xtasy.anticheat.check.impl.autoclicker.*;
import me.xtasy.anticheat.check.impl.badpackets.*;
import me.xtasy.anticheat.check.impl.gravity.*;
import me.xtasy.anticheat.check.impl.scaffold.*;
import me.xtasy.anticheat.check.impl.hitbox.*;
import me.xtasy.anticheat.check.impl.motion.*;
import me.xtasy.anticheat.check.impl.fly.*;
import me.xtasy.anticheat.check.impl.step.*;
import me.xtasy.anticheat.check.impl.jesus.*;
import me.xtasy.anticheat.check.impl.range.*;
import me.xtasy.anticheat.check.impl.sprint.*;

public class ChecksManager
{
    private final ClassToInstanceMap<Check> checkRegistry;
    
    public Collection<Check> getChecks() {
        return (Collection<Check>)Collections.unmodifiableCollection((Collection<? extends Check>)this.checkRegistry.values());
    }
    
    public <T extends Check> T getCheck(final Class<T> clazz) {
        return (T)this.checkRegistry.getInstance((Class)clazz);
    }
    
    public ChecksManager(final PlayerData playerData) {
        this.checkRegistry = (ClassToInstanceMap<Check>)new ImmutableClassToInstanceMap.Builder().put((Class)KillAuraA.class, (Object)new KillAuraA(playerData)).put((Class)KillAuraB.class, (Object)new KillAuraB(playerData)).put((Class)KillAuraC.class, (Object)new KillAuraC(playerData)).put((Class)KillAuraD.class, (Object)new KillAuraD(playerData)).put((Class)KillAuraE.class, (Object)new KillAuraE(playerData)).put((Class)VelocityA.class, (Object)new VelocityA(playerData)).put((Class)GroundSpoofA.class, (Object)new GroundSpoofA(playerData)).put((Class)GroundSpoofB.class, (Object)new GroundSpoofB(playerData)).put((Class)SpeedA.class, (Object)new SpeedA(playerData)).put((Class)InventoryA.class, (Object)new InventoryA(playerData)).put((Class)InventoryB.class, (Object)new InventoryB(playerData)).put((Class)InventoryC.class, (Object)new InventoryC(playerData)).put((Class)InventoryD.class, (Object)new InventoryD(playerData)).put((Class)TimerA.class, (Object)new TimerA(playerData)).put((Class)AimA.class, (Object)new AimA(playerData)).put((Class)AimB.class, (Object)new AimB(playerData)).put((Class)AimC.class, (Object)new AimC(playerData)).put((Class)AimD.class, (Object)new AimD(playerData)).put((Class)AimE.class, (Object)new AimE(playerData)).put((Class)AimF.class, (Object)new AimF(playerData)).put((Class)AimG.class, (Object)new AimG(playerData)).put((Class)AimH.class, (Object)new AimH(playerData)).put((Class)AimI.class, (Object)new AimI(playerData)).put((Class)AimJ.class, (Object)new AimJ(playerData)).put((Class)AimK.class, (Object)new AimK(playerData)).put((Class)AimL.class, (Object)new AimL(playerData)).put((Class)AimM.class, (Object)new AimM(playerData)).put((Class)AimN.class, (Object)new AimN(playerData)).put((Class)AutoClickerA.class, (Object)new AutoClickerA(playerData)).put((Class)AutoClickerB.class, (Object)new AutoClickerB(playerData)).put((Class)AutoClickerC.class, (Object)new AutoClickerC(playerData)).put((Class)AutoClickerD.class, (Object)new AutoClickerD(playerData)).put((Class)AutoClickerE.class, (Object)new AutoClickerE(playerData)).put((Class)AutoClickerF.class, (Object)new AutoClickerF(playerData)).put((Class)AutoClickerG.class, (Object)new AutoClickerG(playerData)).put((Class)AutoClickerH.class, (Object)new AutoClickerH(playerData)).put((Class)AutoClickerI.class, (Object)new AutoClickerI(playerData)).put((Class)AutoClickerJ.class, (Object)new AutoClickerJ(playerData)).put((Class)BadPacketsA.class, (Object)new BadPacketsA(playerData)).put((Class)BadPacketsB.class, (Object)new BadPacketsB(playerData)).put((Class)BadPacketsC.class, (Object)new BadPacketsC(playerData)).put((Class)BadPacketsD.class, (Object)new BadPacketsD(playerData)).put((Class)BadPacketsE.class, (Object)new BadPacketsE(playerData)).put((Class)BadPacketsF.class, (Object)new BadPacketsF(playerData)).put((Class)BadPacketsG.class, (Object)new BadPacketsG(playerData)).put((Class)BadPacketsH.class, (Object)new BadPacketsH(playerData)).put((Class)GravityA.class, (Object)new GravityA(playerData)).put((Class)ScaffoldA.class, (Object)new ScaffoldA(playerData)).put((Class)HitBoxA.class, (Object)new HitBoxA(playerData)).put((Class)HitBoxB.class, (Object)new HitBoxB(playerData)).put((Class)MotionXZ.class, (Object)new MotionXZ(playerData)).put((Class)MotionY.class, (Object)new MotionY(playerData)).put((Class)FlyA.class, (Object)new FlyA(playerData)).put((Class)StepA.class, (Object)new StepA(playerData)).put((Class)JesusA.class, (Object)new JesusA(playerData)).put((Class)RangeA.class, (Object)new RangeA(playerData)).put((Class)SprintA.class, (Object)new SprintA(playerData)).put((Class)SprintB.class, (Object)new SprintB(playerData)).put((Class)SprintC.class, (Object)new SprintC(playerData)).put((Class)SprintD.class, (Object)new SprintD(playerData)).build();
    }
}
