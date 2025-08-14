package lab;
import java.util.*;
public final class Sequence implements Command {
    private final List<Command> commands;private final Set<String> requirements;private int index;
    public Sequence(List<Command> commands){
        this.commands=List.copyOf(commands);var union=new HashSet<String>();
        for(var c:commands)union.addAll(c.requirements());requirements=Set.copyOf(union);
    }
    public Set<String> requirements(){return requirements;}
    public void initialize(){index=0;if(!commands.isEmpty())commands.get(0).initialize();}
    public void execute(){
        if(finished())return;
        var command=commands.get(index);command.execute();
        if(command.finished()){command.end(false);index++;if(!finished())commands.get(index).initialize();}
    }
    public boolean finished(){return index>=commands.size();}
    public void end(boolean interrupted){if(interrupted&&!finished())commands.get(index).end(true);}
}
