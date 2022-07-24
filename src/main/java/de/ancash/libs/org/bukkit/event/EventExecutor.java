package de.ancash.libs.org.bukkit.event;

public interface EventExecutor {
    public void execute(Event event) throws EventException;
}
