package lab;
import java.util.*;
public class Scheduler {
    private final Map<Command,Set<String>> active=new LinkedHashMap<>();
    public void schedule(Command command){
        if(active.containsKey(command))return;
        Set<String> required=Set.copyOf(command.requirements());
        for(var entry:new ArrayList<>(active.entrySet())){
            if(!Collections.disjoint(required,entry.getValue())){
                active.remove(entry.getKey());entry.getKey().end(true);
            }
        }
        command.initialize();active.put(command,required);
    }
    public void tick(){
        for(Command command:new ArrayList<>(active.keySet())){
            if(!active.containsKey(command))continue;
            command.execute();
            if(command.finished()){active.remove(command);command.end(false);}
        }
    }
    public void cancelAll(){
        var commands=new ArrayList<>(active.keySet());active.clear();
        for(Command command:commands)command.end(true);
    }
}
