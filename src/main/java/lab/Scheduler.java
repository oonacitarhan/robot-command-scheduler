package lab;
import java.util.*;
public class Scheduler {
    private boolean ticking=false;
    private final Queue<Runnable> pending=new ArrayDeque<>();
    private final Map<Command,Set<String>> active=new LinkedHashMap<>();
    public void schedule(Command command){
        Objects.requireNonNull(command);
        if(ticking){pending.add(()->schedule(command));return;}
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
        if(ticking)throw new IllegalStateException("recursive tick");
        ticking=true;
        try{
        for(Command command:new ArrayList<>(active.keySet())){
            if(!active.containsKey(command))continue;
            command.execute();
            if(command.finished()){active.remove(command);command.end(false);}
        }
        }finally{ticking=false;}
        while(!pending.isEmpty())pending.remove().run();
    }
    public void cancelAll(){
        if(ticking){pending.add(this::cancelAll);return;}
        var commands=new ArrayList<>(active.keySet());active.clear();
        for(Command command:commands)command.end(true);
    }
}
