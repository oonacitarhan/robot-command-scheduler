package lab;
import java.util.*;
public class Main {
    public static void main(String[] args){
        try{
            if(args.length>1)throw new IllegalArgumentException("usage: lab.Main [ticks]");
            int ticks=args.length==0?6:Integer.parseInt(args[0]);
            if(ticks<1||ticks>10000)throw new IllegalArgumentException("ticks must be 1..10000");
            var scheduler=new Scheduler();var events=new ArrayList<String>();
            var drive=new Sequence(List.of(new TimedCommand("approach","drive",2,events::add),new TimedCommand("align","drive",2,events::add)));
            scheduler.schedule(drive);scheduler.schedule(new TimedCommand("intake","intake",3,events::add));
            for(int i=0;i<ticks;i++){scheduler.tick();for(String event:events)System.out.println(i+","+event);events.clear();}
            scheduler.cancelAll();for(String event:events)System.out.println(ticks+","+event);
        }catch(Exception error){System.err.println(error.getMessage());System.exit(2);}
    }
}
