package lab;
import java.util.*;
import java.util.function.Consumer;
public class TimedCommand implements Command {
    private final String name,subsystem;private final int duration;
    private final Consumer<String> events;private int ticks;
    public TimedCommand(String name,String subsystem,int duration,Consumer<String> events){
        if(duration<1)throw new IllegalArgumentException("duration must be positive");
        this.name=Objects.requireNonNull(name);this.subsystem=Objects.requireNonNull(subsystem);
        this.duration=duration;this.events=Objects.requireNonNull(events);
    }
    public Set<String> requirements(){return Set.of(subsystem);}
    public void initialize(){ticks=0;events.accept(name+":initialize");}
    public void execute(){ticks++;events.accept(name+":execute:"+ticks);}
    public boolean finished(){return ticks>=duration;}
    public void end(boolean interrupted){events.accept(name+(interrupted?":interrupted":":complete"));}
}
