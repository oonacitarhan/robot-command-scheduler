# Architecture

The scheduler holds a snapshot of each active command's subsystem requirements. Conflicting commands are interrupted before a replacement starts. Sequence commands retain the union of child requirements. Scheduling and cancellation requested during a tick are deferred until tick completion so callbacks cannot corrupt iteration.

## Data flow

- `src/main/java/lab/` — public domain classes and CLI.
- `src/test/java/lab/` — reference checks, feature checks and benchmark.
- `scripts/` — portable source build and test runner.
- `examples/` — reproducible inputs or expected traces.

## Limits

Simulation only; no robot hardware or WPILib integration. Execution is single-threaded. Callbacks must not throw or recursively trigger ticks. Subsystems are string identifiers, not physical resource locks. A child that finishes before its first execute call still receives one execute call in the current tick model.
