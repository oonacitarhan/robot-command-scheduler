package lab;
import java.util.*;
public class Benchmark{public static void main(String[]args){
long start=System.nanoTime();
var scheduler=new Scheduler();for(int i=0;i<10000;i++){scheduler.schedule(new TimedCommand("job","drive",1,event->{}));scheduler.tick();}
System.out.println("benchmark_seconds,"+(System.nanoTime()-start)/1e9);
}}
