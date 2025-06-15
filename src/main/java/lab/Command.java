package lab;
import java.util.*;
public interface Command {
        Set<String> requirements();
        void initialize();
        void execute();
        boolean finished();
        void end(boolean interrupted);
    }
