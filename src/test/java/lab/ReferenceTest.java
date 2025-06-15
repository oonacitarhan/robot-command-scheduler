package lab;
import java.util.*;
public class ReferenceTest {
    static class CounterCommand implements Command {
        final String subsystem; final int limit;
        int executions=0,starts=0,ends=0; boolean interrupted=false;
        CounterCommand(String subsystem,int limit){this.subsystem=subsystem;this.limit=limit;}
        public Set<String> requirements(){return Set.of(subsystem);}
        public void initialize(){starts++;executions=0;}
        public void execute(){executions++;}
        public boolean finished(){return executions>=limit;}
        public void end(boolean interrupted){ends++;this.interrupted=interrupted;}
    }
    static void require(boolean ok){if(!ok)throw new AssertionError("self-test failed");}
    public static void main(String[] args){
        var scheduler=new Scheduler();
        var drive=new CounterCommand("drive",3);
        var intake=new CounterCommand("intake",2);
        scheduler.schedule(drive);scheduler.schedule(drive);scheduler.schedule(intake);
        scheduler.tick();scheduler.tick();
        require(drive.starts==1&&drive.executions==2&&intake.ends==1&&!intake.interrupted);
        var replacement=new CounterCommand("drive",1);
        scheduler.schedule(replacement);require(drive.ends==1&&drive.interrupted);
        scheduler.tick();require(replacement.ends==1&&!replacement.interrupted);
        scheduler.tick();require(replacement.executions==1);
        scheduler.schedule(drive);scheduler.cancelAll();require(drive.ends==2&&drive.interrupted);
        System.out.println("Subsystem conflict, interruption, completion and cancellation checks passed");
    }
}
