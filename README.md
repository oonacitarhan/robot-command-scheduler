# Robot Command Scheduler

A modular command scheduler with timed commands, sequences, subsystem ownership and deterministic simulation traces.

**Java** · Reusable core · CLI examples · Regression checks · Reproducible benchmark

> **Provenance:** created with AI assistance on **2026-11-01**.
> All commit author and committer dates are synthetic and distributed across
> November 2024–October 2026. They do not establish work on those dates.

## Quick start

```sh
python3 scripts/build.py
java -cp build/classes lab.Main 6
```

## Repository map

- `src/main/java/lab/` — public domain classes and CLI.
- `src/test/java/lab/` — reference checks, feature checks and benchmark.
- `scripts/` — portable source build and test runner.
- `examples/` — reproducible inputs or expected traces.

## How it works

The scheduler holds a snapshot of each active command's subsystem requirements. Conflicting commands are interrupted before a replacement starts. Sequence commands retain the union of child requirements. Scheduling and cancellation requested during a tick are deferred until tick completion so callbacks cannot corrupt iteration.

## Verification

```sh
sh scripts/check.sh
```

Two executable regression suites and CLI integration checks exercise reference cases, invalid inputs, and relevant invariants.
The complete local run is in [docs/verification.txt](docs/verification.txt).
The [checks workflow](.github/workflows/check.yml) executes the same source checks.

## Boundaries

Simulation only; no robot hardware or WPILib integration. Execution is single-threaded. Callbacks must not throw or recursively trigger ticks. Subsystems are string identifiers, not physical resource locks. A child that finishes before its first execute call still receives one execute call in the current tick model.

See [architecture](docs/architecture.md), [usage](docs/usage.md),
[release notes](CHANGELOG.md), and [provenance](PROVENANCE.json).

## License

MIT — [LICENSE](LICENSE).
