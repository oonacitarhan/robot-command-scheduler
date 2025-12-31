# Usage

Requires a full JDK 17+ and Python 3.10+ for portable build orchestration.

```sh
python3 scripts/build.py
java -cp build/classes lab.Main 6
python3 scripts/test.py
```

The optional tick count limits simulation duration. Events carry a simulation tick, command name, lifecycle phase, and execution count. Commands still active at the end are cancelled.
