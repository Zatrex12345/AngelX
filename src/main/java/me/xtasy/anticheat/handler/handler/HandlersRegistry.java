package me.xtasy.anticheat.handler.handler;

import me.xtasy.anticheat.interf.*;
import me.xtasy.anticheat.handler.*;
import me.xtasy.anticheat.handler.impl.*;
import java.util.*;
import me.xtasy.anticheat.util.message.*;
import java.util.stream.*;
import java.util.function.*;

public class HandlersRegistry implements Startable
{
    private static final Class<? extends HandlersManager>[] HANDLERS;
    private final Queue<HandlersManager> handlers;

    static {
        HANDLERS = new Class[] { PacketHandler.class, MovementHandler.class };
    }
    
    public Queue<HandlersManager> getHandlers() {
        return this.handlers;
    }
    
    private void start(final Class clazz) {
        try {
            this.handlers.add((HandlersManager) clazz.newInstance());

        }
        catch (InstantiationException | IllegalAccessException ex) {

            ex.printStackTrace();
        }
    }
    
    public HandlersRegistry() {
        this.handlers = new LinkedList<HandlersManager>();
    }
    
    @Override
    public void start() {
        LogUtil.log("Starting AngelX Manager!");
        Stream.of(HandlersRegistry.HANDLERS).forEach(this::start);
    }
}
