# 实景空间标尺 · Design Package Index

> Source identity: `spatial-ruler-design-run-4-20260816`  
> Package scope: PICO Spatial app design and interactive Web preview only  
> Status: Stage 17 passed; main-thread acceptance `HOST-ACCEPT-20260816-224210-CST` recorded; downstream app generation allowed

## Delivery artifacts

- [Product requirements](./pm-requirement-spec.md) — active r2, SHA-256 `b67a14c8d7b673afb85bf3e96966adcedaa70932de1d52e265c12614e52bdfff`
- [Research and domain evidence](./uxr-research-report.md) — active r1, SHA-256 `ff5cb0377e0f6e05c5857d5dbde6c352e3f1b9ea798d52a4946c0e4bbc42393f`
- [Interaction and spatial specification](./interaction-spatial-spec.md) — active r7, SHA-256 `d20a91e538d505b523c8b93d1222678fa5ca3f33c4f4012bef7ffed6d5f2f950`
- [Visual system specification](./visual-system-spec.md) — active r3, SHA-256 `36aab4698e72c0dc85da41c7f73aa8ad42285999239b2e01c00b73b52fd55420`
- [Design critique and gate record](./design-critique-report.md) — active r15 plus host acceptance, SHA-256 `33640ad66e02516bd148147d4887f62d6725482d3eb53f3ea5abe0615e4c17dc`
- [Preview QA report](./preview-qa-report.md) — active r9, SHA-256 `b5efc49dbbf2fa8d80c3edd290a6670b0636b114dfa240dea8a5c007a3d65d93`
- [Interactive preview](../preview.html) — active r6, SHA-256 `90de3902454a08d56a9bafc70573d93f3b2c1fcbc9df3ecbc8382487270c6203`
- [Execution trace](./execution-trace.md) — live audit ledger; its hash changes when Stage 17 and main-thread acceptance are recorded.

## Visual evidence

- [Stage 13 preview](./preview-evidence-stage13.png)
- [Stage 13 Large tier](./preview-evidence-stage13-large.png)
- [CR-14A behavior patch](./preview-evidence-cr14a-r3.png)

These are Web-preview evidence, not PICO emulator or physical-device captures.

## Active gate evidence before Stage 17

| Gate | Invocation | Result |
|---|---|---|
| Stage 4 problem/evidence | `eir-stage4-r4-20260816-5d41c3a9` | pass |
| Stage 7 spatial concept | `spatial-concept-review-run4-b-ebf3da23-a291-454f-aef4-b6acdc0ed09d` | pass |
| Stage 12 design system | `dcr-stage12-run4-b-5bcc29e5-29a7-418a-8fd4-a76e2a6e7a3d` | pass |
| Stage 14 preview implementation | `prototype-qa-run4-stage14-rerun4-c510fe92-ea4b-4318-a055-2d0f5cf77cc3` | pass |
| Stage 15 delivery self-review | `delivery-self-review-run4-stage15-final-459159e4-8c0e-45bf-9f62-a877b0e66383` | pass |
| Stage 17 delivery readiness | `delivery-readiness-run4-stage17-70330ed3-a7b1-4735-9eaf-2062feededd0` | pass |

Frozen active denominators: 17 states; 31 authored/34 concrete transitions; 69 elements; 107 exact bindings; 22 variants; 62 component states; 8 stacking cases; 4 responsive/reduced-motion scenarios; 437/437 Preview QA cells pass.

## Scope boundary and handoff

This package does not contain Android/Kotlin runtime implementation, an APK, PICO emulator evidence, physical-device evidence, spatial-anchor accuracy measurements, gesture-latency measurements, or 60 fps measurements. Those remain downstream implementation and device-validation work. Stage 17 recommends `designStatus=ready_for_design_delivery`; main-thread acceptance `HOST-ACCEPT-20260816-224210-CST` sets `downstreamAppGenerationAllowed=yes` without changing that evidence boundary.
