package lab;
import java.util.*;
public class FeatureTest {
    static void check(boolean b){if(!b)throw new AssertionError("command lifecycle test failed");}
    public static void main(String[] args){
        var events=new ArrayList<String>();var scheduler=new Scheduler();
        var sequence=new Sequence(List.of(new TimedCommand("a","drive",1,events::add),new TimedCommand("b","drive",1,events::add)));
        scheduler.schedule(sequence);scheduler.tick();scheduler.tick();scheduler.tick();
        check(events.equals(List.of("a:initialize","a:execute:1","a:complete","b:initialize","b:execute:1","b:complete")));
        check(sequence.requirements().equals(Set.of("drive")));
        var empty=new Sequence(List.of());scheduler.schedule(empty);scheduler.tick();check(empty.finished());
        events.clear();
        var late=new TimedCommand("late","drive",1,events::add);
        Command callback=new Command(){
            public Set<String> requirements(){return Set.of("drive");}
            public void initialize(){}public void execute(){scheduler.schedule(late);}
            public boolean finished(){return true;}public void end(boolean interrupted){}
        };
        scheduler.schedule(callback);scheduler.tick();
        check(events.equals(List.of("late:initialize")));
        scheduler.tick();check(events.contains("late:complete"));
        boolean rejected=false;try{new TimedCommand("bad","drive",0,events::add);}catch(IllegalArgumentException e){rejected=true;}check(rejected);
        System.out.println("Sequence ordering, requirements, empty commands and deferred scheduling pass");
    }
}
