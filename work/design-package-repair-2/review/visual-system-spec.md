# Visual System Spec · 实景空间标尺

> Source identity: `spatial-ruler-design-run-3` | Active revision: **4** | Completed stage: `design_system` bounded review repair round 2/final | Sources: approved `Surface Signal Thread / Surface Trace` visual r1; interaction r8; PM r2; UXR r1; Stage 12 Attempt B findings.

## 1. Direct Description of Outputs

本修订保留本运行 Stage 8 批准的“Surface Signal Thread / 表面光轨”，并将其落为可消费的视觉 token、窗口结构、七个核心组件完整结构块、材料/深度、数据语义与资产契约。

## 2. Spatial Visual Direction Candidates

| Direction | Spatial thesis | First-view composition | Container relationships | Depth plan | Information hierarchy | Interaction cues | Spatial value | Dashboard risk |
|---|---|---|---|---|---|---|---|---|
| V1 表面信号线 / Surface Signal Thread（visual alias: Surface Trace） | “实物是纸，标尺是光学墨迹”；只在当前决策需要的表面上留下几何 | 首见仍是现实墙/桌/地；命中位置仅出现一个带法线短线的空心点；放两点后亮蓝半透明线沿表面生长，数值在中点上方小幅离面 | Shared Space hub 只是入口/历史；Stage Mixed 中没有大主面板，仅有就近测量实体和视野右下辅助命令 | environment=0; plane grid=+1mm; ruler=+3mm along normal; ticks=+4mm; endpoint=+6mm; billboard value=near user but angularly adjacent, never over endpoint | 1 实物边缘 → 2 命中点/当前线 → 3 当前数值/可信状态 → 4 次级刻度 → 5 命令 | hollow-dot=preview; filled dot=pinned; dashed+text=degraded; growing line=commit continuity; palm clear opens explicit Dialog | 高：位置/距离/尺度直接共位，不建第二个 3D 副本 | 低：量测场景没有卡片网格；历史才使用单列阅读面 |
| V2 玻璃仪表 Glass Instrument | “精密仪器由一块可信控制台统一”；世界几何是面板的附属视图 | 中央厚玻璃面板显示模式、数值、质量、历史；现实中仅留简化线 | 一个大面板持续随视野，测量线在其后面 | panel +40cm visual depth; measurement world layer +3mm | 面板数值 > 质量 > 世界线 | 按钮 hover/按下明显，但射线命中被面板竞争 | 中：数值清晰，但共位证据被面板分离 | 高：接近通用 dashboard，可能遮挡实物并诱导把复杂度塞入面板 |
| V3 测量星座 Survey Constellation | “每个点是空间节点，数值是节点间的星图”；强调可绕观的深度 | 多个发光端点、浮动数值和弧形引导线叠在房间中 | Stage 中完全依赖 3D 标签，弱化 2D 辅助面 | points at +1–5cm, labels +5–15cm, ambient connectors | active point > path > labels > environment | 大发光点和环形脉冲为交互暗示 | 高：深度/方位强，但超出“贴面”所需范围 | 中：非 dashboard，但有浮窗化/科幻装饰化风险 |

### 2.1 Direction comparison against project semantics

| Criterion | V1 Surface Trace | V2 Glass Instrument | V3 Constellation |
|---|---|---|---|
| Real-object legibility | highest: marks hug surfaces and minimize fill | low: large panel competes with object | medium: no panel but floating marks cover depth |
| Trust-state legibility | high with shape+text at measurement | high on panel but detached from source | medium; too many labels risk ambiguity |
| Low-occlusion brief | strongest | weakest | medium |
| Motion comfort | local bounded growth/pop only | stable panel but head-relative attention load | pulse/float risk; Reduce Motion would remove much of identity |
| Market differentiation foil | avoids C1 screen mediation and C3 full-room overlay; C2 visual/spatial gaps supply no style premise | recreates a generic tool console | visually novel but drifts toward decorative XR |
| Implementation risk | moderate, low-poly geometry and billboard labels | moderate, UI-heavy but straightforward | high, label collision and occlusion management |

- **Selected direction / approved visual reference**: **V1 Surface Signal Thread / 表面光轨（Surface Trace alias）**. It is the only option where the real edge remains the first view, the trust state is adjacent to its source, and the distinctive spatial value is achieved without a dashboard or decorative floating constellation. The current-run Stage 8 structured design-effect review passed on visual r1 + interaction r3 + PM r2 + UXR r1.
- **Rejected V2**: despite excellent 2D readability, it turns the real surface into supporting content, violates the low-occlusion premise, and converges on a generic instrument console. Only UXR C1 supports a screen-mediated observation; C2 visual/interaction/spatial behavior remains an explicit gap and supplies no visual premise.
- **Rejected V3**: it uses more depth than the decision needs, increases label collision/occlusion, and its identity depends on motion/pulse that degrades poorly under Reduce Motion.

### 2.2 Preview/render instruction for structured design-effect review

- Render a real wall/table scene with no central dashboard. Show one active straight measurement: #00D4FF line at 42% alpha, 8mm endpoints, 10cm short ticks, 50cm long ticks + numeric label; a 5% blue plane grid only around the active work area.
- Value label: 18sp-equivalent white, black outline/backing, billboard toward viewer, offset so it never covers an endpoint. Show a dashed+warning-triangle degraded example beside a stable circle example to verify non-color semantics.
- Show a tiny peripheral command cluster only as a blurred silhouette; do not finalize its attachment before Stage 9.
- Include bright/dark passthrough variants, Reduce Motion still frame, and one screenshot receipt state. No faux CSS claim of PICO system material.

### 2.3 Approval record

| Field | Value |
|---|---|
| approvalMethod | structured design-effect review |
| reviewedDirection | V1 Surface Signal Thread / Surface Trace |
| reviewBasis | spatial composition, hierarchy, domain expression, interaction legibility, PICO nativeness, aesthetic maturity, handoff clarity |
| invocationId | `DER-run3-stage8-20260816` |
| evidenceRebuilt | yes |
| reviewedRevision | visual r1; interaction r3; PM r2; UXR r1 |
| verdict | pass |
| boundary | aesthetic/design-direction only; runtime/device/comfort/contrast/performance not validated |
| downstream goals | adaptive cyan contrast; restrained four-mode grammar; no-pulse Reduce Motion; peripheral command scope; grid falloff and label-collision rules |

## 3. Design Tokens

### 3.1 Typography

| role | family | size(sp) | line(sp) | weight | Use |
|---|---|---:|---:|---:|---|
| display | sans | 28 | 34 | 600 | hub title only |
| title | sans | 20 | 26 | 600 | window/component heading |
| metric | mono | 18 | 24 | 650 | measurement value; black 2px-equivalent outline in Stage |
| body | sans | 15 | 22 | 500 | instruction/form copy |
| caption | sans | 12 | 17 | freshness, units, record metadata |

CJK at 15sp or lower uses Medium (500). Text scaling 1.0–1.5× reflows/scrolls; nothing shrinks below 12sp.

### 3.2 Tokens and color semantics

| token | value | use |
|---|---|---|
| `accent` | `#00D4FF` | active valid geometry, focus |
| `accentBright` | `#7AEAFF` | adaptive outline on dark/complex passthrough |
| `surfaceInk` | `#07151CE8` | solid readability backing |
| `surfacePanel` | `#10252EE6` | matte dialog/Stage label backing |
| `textPrimary` | `#FFFFFF` | primary text |
| `textSecondary` | `#C9D7DC` | secondary text |
| `warning` | `#FFD166` | degraded/aging |
| `danger` | `#FF6577` | error/destructive |
| `success` | `#66E3A4` | saved/captured |
| `grid` | `#00D4FF0D` | local plane grid, 5% alpha |

| semantic key | color | shape | human label | desc | aliases[] |
|---|---|---|---|---|---|
| `ready` | `#00D4FF` | circle | 表面可测 | fresh stable hit | `fresh`, `ready`, `稳定`, `可测` |
| `aging` | `#FFD166` | triangle | 表面信号变弱 | observation old but visible | `aging`, `degraded`, `变弱`, `降级` |
| `stale` | `#FFD166` | dashed | 跟踪已暂停 | not current; commit blocked | `stale`, `offline`, `丢失`, `暂停` |
| `complete` | `#66E3A4` | diamond | 测量完成 | valid completed geometry | `complete`, `saved`, `完成`, `已保存` |
| `error` | `#FF6577` | square | 需要处理 | permission/fit/save/capture error | `error`, `conflicting`, `denied`, `失败`, `冲突` |
| `pending` | `#C9D7DC` | dashed | 正在处理 | loading/scanning/pending operation | `loading`, `pending`, `scanning`, `buffering`, `处理中`, `正在` |
| `unavailable` | `#FF6577` | square | 暂不可用 | capability/hit/permission unavailable | `unavailable`, `permission_denied`, `restricted`, `不可用`, `未授权` |

### 3.3 Materials, environment adaptation and scale

| material | desc | treatment | glassStyle | opacity |
|---|---|---|---|---:|
| `hubShell` | Shared Space hub window | glass | Thick | 0.92 |
| `controlShell` | MR command window | glass | Thickest | 0.94 |
| `focusMatte` | Dialog, Stage value/trust backing | matte | none | 0.92 |
| `worldGeometry` | low-poly unlit ruler/grid | opaque | none | line 0.42; points 0.92; grid 0.05 |

- System glass is used only inside WindowContainers. Web `backdrop-filter` is a visual approximation, never PICO device evidence. Stage geometry/value labels use matte or opaque assets, not glass.
- Vibrant: hub body text `light` and control labels `ultralight` on monochrome system glass; propagation terminates at any image/screenshot thumbnail. If Vibrant is disabled/unsupported, explicit `surfaceInk` backing and `textPrimary` replace `Color.Vibrant` so text never falls back to black.
- Adaptive cyan geometry: sample three points under the projected line. On dark/medium backgrounds use `accent`; on bright/textured or local contrast <4.5:1 use `accentBright` plus a 1mm `#001014CC` outer silhouette. At low confidence, dash+triangle+text replaces any color-only distinction. The 5% grid keeps an 8cm fade band and is hidden on highly textured/unstable mesh.

| scale | values |
|---|---|
| spacing | `xs=4`, `s=8`, `m=16`, `l=24`, `xl=32` dp |
| radius | `s=12`, `m=20`, `l=32` dp |
| iconSize | `s=20`, `m=28`, `l=36` dp |
| hitTarget | `min=56` dp |
| world | point=8mm; line=3mm visual width; tickShort=12mm; tickLong=22mm; valueOffset=35mm |

## 4. Visual Grammar

- Concept: **Surface Trace / 表面光轨**. Reality is the substrate; virtual marks are local evidence.
- Order: real edge → hit/geometry → value+trust → ticks → peripheral commands. One active value and one total maximum.
- Geometry by mode: line=one cyan segment; path=joined segments with total at last point; area=outline+5% fill and one centroid value; height=vertical segment with floor glyph and top value. No constellation of labels.
- Reduce Motion identity: immediate line + filled endpoint + complete diamond preserves commit without growth/pulse.
- Rejected alternatives remain V2 central glass dashboard and V3 floating constellation (§2.1).

## 5. Window Layout and Component Definition

### 5.0 Window shells

#### `C-HUB` Volumetric

| Field | Content |
|---|---|
| form | Volumetric, uniformly resizable |
| default / min / max | 0.72×0.44×0.28m / 0.56×0.34×0.22m / 0.96×0.58×0.36m, from interaction §9 |
| content inset | 24dp-equivalent all sides on readable face |
| usable face min/default/max | 0.524×0.304m / 0.684×0.404m / 0.924×0.544m after 18mm (24dp-equivalent at project 0.75mm/dp calibration) inset per side |
| docked attachment | none |

```text
┌──────────── C-HUB readable face ────────────┐
│ Region H1 ┌╌ title/status ┐                 │
│ gap 16dp  └╌ HubWorkspace navigation ╌────┐ │
│ Region H2 ┊ HubWorkspace start/history    ┊ │
│ Region H3 ┊ action + persistence note     ┊ │
│           └╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┘ │
└────────────────────────────────────────────┘
         3D ruler token depth ≤0.28m
```

Grid: one column, rows 48/56/1fr/72dp-equivalent, 16dp gaps. All window regions map to `HubWorkspace`; decorative ruler token is a render element inside its block, not a second component. Large uses 4-column modes/list+detail; default 2×2/single detail; compact one-column/internal scroll. Usable-face values are project calibration, not PICO physical constants.

#### `C-CONTROL` Planar

| Field | Content |
|---|---|
| form | Planar; depth fixed 640dp |
| windowBounds default / min / max | 720×420dp / 560×360dp / 960×540dp, from interaction §9 |
| hostContent after 96dp title | 720×324dp / 560×264dp / 960×444dp |
| safeContent after 24dp inset | 672×276dp / 512×216dp / 912×396dp; all component sizing uses this denominator |
| docked attachment | none; InlineControls only |

```text
┌──────────── C-CONTROL 672×276 safeContent ─────┐
│ R1 ┌╌ SurfaceReadiness compact ╌────────────┐ │
│ R2 ┊ MeasureControlPanel primary row        ┊ │
│ R3 ┊ secondary row OR StatusReceipt         ┊ │
│ R4 ┊ Large-only receipt                     ┊ │
│    └╌ DecisionDialog or RecordComposer ╌────┘ │
└───────────────────────────────────────────────┘
```

Normal composition: readiness + primary + secondary. Receipt composition keeps readiness and primary/stable-exit controls, and **replaces** the secondary row at Compact/Regular; it never coexists with that row. Arithmetic with maximum 96dp overflow/error receipt: Compact `36+8+56+8+96=204≤216`; Regular `36+12+72+12+96=228≤276`; Large `36+16+72+16+96=236≤396` (receipt may use R4). Dialog/RecordComposer temporarily spans the whole safeContent. Compact preserves 56dp targets. The 1280×720 baseline calibration and 0.75mm/dp angular basis are authoritative in interaction §9.2.

### 5.1 Component `HubWorkspace`

| Field | Content |
|---|---|
| derivedFromTasks | T01, T02, T04, T11, T12 |
| derivedFromData | `UnitPreference`, `MeasurementRecord[]`, permission/space-state |
| purpose | Prepare a measurement and read persisted numeric history without implying pose restoration |
| layoutRole | `primary_hero` in `C-HUB` |
| priority | primary |
| runtimeRole | `sessionNavigationAndDecision` |

**Anatomy · Layout (anatomy.layout)**

```text
┌──────────────────────────────────────┐
│ A ┌╌ 实景空间标尺 ╌┐ ┌╌ Shared Space ╌┐ │
│ B ┌╌ 开始测量 ╌┐ ┌╌ 历史记录 ╌┐          │
│ C ┌╌ mode/unit or record list/detail ╌──┐ │
│ D ┌╌ primary action ╌┐ ┌╌ trust note ╌┐ │
│ E ─── low-poly ruler token behind face ─ │
└──────────────────────────────────────┘
```

Grid 1 column, A 48, B 56, C 1fr, D 72dp-equivalent; 16dp gaps, left alignment; E occupies depth behind face and accepts no input.

**Anatomy · Sizing (sizing)**

| Tier | Width × Height | Window tier / fit |
|---|---|---|
| Large | 0.91×0.53m face | `C-HUB` max usable 0.924×0.544m; fit margin 14×14mm |
| Regular | 0.67×0.39m face | default usable 0.684×0.404m; fit margin 14×14mm |
| Compact | 0.51×0.29m face | min usable 0.524×0.304m; fit margin 14×14mm; one-column/internal scroll |

**Anatomy · Internal Metrics (metrics)**

| Metric | Value | Source / notes |
|---|---|---|
| background | none | parent `hubShell` glass only |
| radius | 20dp | radius.m |
| padding | 24dp all | spacing.l |
| gap | 16dp regions; 8dp inline | spacing.m/s |
| stroke | 1dp `#FFFFFF29` for selectable cells | token |
| icon | 28dp | iconSize.m |
| primary text | title 20/26/600 | typography.title |
| value/secondary | body 15/22; caption 12/17 | typography |
| hitTarget | 56×56dp minimum | platform floor |

**Render Elements `renderSpec.elements[]`**

| id | Visible label | type | bind | state / semantic role |
|---|---|---|---|---|
| `hub-title` | 实景空间标尺 | text | none | orientation |
| `hub-space` | 共享空间 | status label | `spaceState.current` | human-readable space state |
| `hub-route-start` | 开始测量 | segmented button | `hub.route` | selected/focused navigation |
| `hub-route-history` | 历史记录 | segmented button | `hub.route` | selected/focused navigation |
| `hub-mode` | 直线/连续/面积/高度 | four choice cells | `session.mode` | actionable selection |
| `hub-unit` | cm / m / in | segmented choice | `unitPreference.displayUnit` | actionable formatting |
| `hub-stage-notice` | 测量将进入 Full Space | text+arrow | `capability.requiredSpace` | trust boundary |
| `hub-start` | 开始测量 | primary button | `capability.startEnabled` | explicit Stage entry |
| `hub-records` | 测量记录 | list | `records.items` | history loading/empty/error/overflow |
| `hub-record-rename` | 重命名 | row action | `records.selectedId` | enters H1R in-hub edit |
| `hub-record-delete` | 删除记录 | row destructive request | `records.selectedId` | enters H1D in-hub confirm; never deletes directly |
| `hub-record-name-input` | 名称 | inline text field | `historyDraft.name` | H1R only |
| `hub-record-save` | 保存名称 | button | `historyDraft.valid` | H1R only |
| `hub-record-cancel` | 取消 | button | none | H1R/H1D safe default |
| `hub-record-delete-confirm` | 删除这条记录 | destructive button | `records.selectedId` | H1D explicit confirm only |
| `hub-record-note` | 记录不恢复空间位置 | text | none | persistence boundary |
| `hub-record-shot-link` | 打开截图 | link | `records.selected.screenshotUri` | history only; stable URI owner; hidden when null/invalid |
| `hub-token` | — | low-poly ruler | `hub.route` | decorative, hidden for Reduce Motion unchanged |

**Data Bindings `dataBindings[]`**

| Source path | Target | fallback | kind |
|---|---|---|---|
| `spaceState.current` | `hub-space.text` | `共享空间` | semantic |
| `session.mode` | `hub-mode.selected` | `line` | semantic |
| `unitPreference.displayUnit` | `hub-unit.selected` | `cm` | display-only |
| `capability.requiredSpace` | `hub-stage-notice.text` | `测量需要 Full Space` | semantic |
| `capability.startEnabled` | `hub-start.enabled` | disabled + `权限不可用` | semantic |
| `records.loadState` | `hub-records.state` | error + retry | semantic |
| `records.items` | `hub-records.rows` | empty guidance | display-only |
| `records.items[].recordId` | row key + selected record identity | hide row actions if absent; never substitute array index | semantic |
| `records.items[].valueFormatted` | row metric | `—` | display-only |
| `records.selected.screenshotUri` | `hub-record-shot-link.href` + selected thumbnail | hide link + `无截图`; reject foreign-session/malformed URI | semantic ownership |
| `records.selectedId` | rename/delete action scope | disable actions + `请先选择记录` | semantic |
| `records.writeState` | rename/delete recovery | preserve list/selection + retry | semantic |
| `historyDraft.name` | in-hub name input | selected name; preserve on failure | semantic |
| `historyDraft.valid` | Save enabled/reason | disabled + `请输入名称` | semantic |

**Variants `variants`**

- `start`: mode/unit, Stage notice and Start action; primary focus Start.
- `history`: local record list, stable rename/delete requests, no Restore action; primary focus latest/selected record.
- `historyRename`: C region becomes name input + Save/Cancel inside C-HUB; no `C-CONTROL`/Stage created.
- `historyDelete`: C region becomes selected-record consequence + Cancel/Delete inside C-HUB; no external Dialog host.

**States `states`**

| State | Trigger | visual params | size change | motion | accessibility |
|---|---|---|---|---|---|
| default | start route | Thick glass, normal stroke | none | none | heading hierarchy |
| focused | gaze choice | accent outline, 1.04× max | ≤4% target only | 90ms | focus announcement |
| selected/editing | mode/unit/route chosen | filled accent 18%, check mark | none | 120ms | selected text |
| disabled | permission unavailable | 55% opacity, lock+reason | none | none | reason announced |
| loading | records requested | 3 row skeletons | fixed C region | 160ms fade; RM immediate | `正在读取记录` |
| empty | no records | ruler outline + `还没有记录` | none | none | Start remains reachable |
| error | DB read failure | error square + Retry | none | none | error text + action |
| overflow | >5 records/text scale | internal vertical scroll | C scroll only | inertial; RM unchanged | scroll semantics |
| history-editing | H1R | selected row replaced by input + Save/Cancel | C region only | 120ms; RM immediate | focus trapped within in-hub edit until save/cancel |
| history-delete-confirm | H1D | warning square+record name+safe default | C region only | 160ms; RM immediate | explicit local-only consequence; Back cancels |

State stacking precedence: `disabled > loading > error > selected > focused > default`; selected+focused keeps check mark and adds outline; loading ignores focus.

### 5.2 Component `SurfaceReadiness`

| Field | Content |
|---|---|
| derivedFromTasks | T03, T05, T06, T07 |
| derivedFromData | `PlaneObservation`, `RayHitPreview`, tracking freshness, fit residual |
| purpose | Prove where a point will land and block false precision when evidence is weak |
| layoutRole | `critical_primary` at hit + compact status in `C-CONTROL` |
| priority | primary |
| runtimeRole | `placementTrustGuard` |

**Anatomy · Layout (anatomy.layout)**

```text
surface local coordinates (meters)
      normal cue 20mm
          ↑
  24cm local grid (outer 8cm fade) ─ (○ hit halo 8–16mm) ─ label +35mm
                       └ dashed boundary when stale
compact mirror: [shape] 表面可测 · 0.2秒前
```

World origin=ray hit; XY follows plane tangent, +Z follows normal. Grid is clipped to a 0.24×0.24m neighborhood and fades in outer 0.08m. Compact mirror is one row in `C-CONTROL` R1.

**Anatomy · Sizing (sizing)**

| Tier | Width × Height | Window/Stage tier / fit |
|---|---|---|
| Large mirror | 912×36dp | `C-CONTROL` max `safeContent` 912×396, fits R1 |
| Regular mirror | 672×36dp | default `safeContent` 672×276, fits R1 |
| Compact mirror | 512×36dp | min `safeContent` 512×216, fits R1 |
| Regular world | grid max 0.24×0.24m; label max 0.18×0.05m | Stage has no window tier; local-only, core FOV |
| Constrained world | grid 0.12×0.12m; label 0.14×0.05m | Stage N/A to default/min/max; surface extent/FOV constraint applies |

**Anatomy · Internal Metrics (metrics)**

| Metric | Value | Source / notes |
|---|---|---|
| background | world none; compact none | glass not valid in Stage |
| radius | world N/A (geometry); compact 12dp | shape/scale |
| padding | label 8×12dp-equivalent; compact 0 | readability |
| gap | 8dp-equivalent shape↔text | spacing.s |
| stroke | halo 2mm; grid 1mm; stale dash 12/8mm | world scale |
| icon | 20dp compact; 8mm world shape | iconSize.s |
| primary text | body 15/22/500 | state label |
| value/secondary | caption 12/17 | freshness |
| hitTarget | N/A: component is read-only evidence; actionable recovery is in control panel | explicit reason |

**Render Elements `renderSpec.elements[]`**

| id | Visible label | type | bind | state / semantic role |
|---|---|---|---|---|
| `surface-grid` | — | local grid geometry | `plane.extent`, `plane.confidence` | environmental evidence |
| `hit-halo` | — | circle/dashed hit glyph | `rayHit.state` | preview position |
| `normal-cue` | — | short line | `plane.normal` | orientation cue |
| `surface-shape` | circle/triangle/dashed/square | semantic glyph | `tracking.trustState` | non-color state |
| `surface-label` | 表面可测/信号变弱/跟踪已暂停/需要处理 | text | `tracking.trustState` | human label |
| `surface-freshness` | 0.2秒前 | text | `plane.ageMs` | timeliness |
| `surface-reason` | 移动射线到已识别表面 | text | `rayHit.blockReason` | recovery instruction |
| `mesh-hit-cue` | 网格命中可用/网格辅助不可用 | glyph+text | `mesh.hitConfidence`, `mesh.availability` | auxiliary source; hidden only when plane path is independently fresh |

**Data Bindings `dataBindings[]`**

| Source path | Target | fallback | kind |
|---|---|---|---|
| `plane.extent` | `surface-grid.clip` | hide grid | display-only |
| `plane.state` | scanning/loading primitive | `loading` until first observation | semantic |
| `plane.type` | state label/height-area guard | `未识别表面类型` | semantic |
| `plane.normal` | `normal-cue.orientation` | hide cue + error state | semantic |
| `plane.confidence` | grid opacity/state | `partial` | semantic |
| `mesh.hitConfidence` | `mesh-hit-cue.shape/label`, combined commit guard | `unavailable`; fail closed when mesh is required by placement provenance | semantic |
| `mesh.availability` | cue visibility/source label | `unavailable`; never infer confidence from plane | semantic |
| `placement.sourceKind` | plane-only vs mesh-assisted provenance/guard | unknown→unavailable, commit/Finish false | semantic |
| `plane.ageMs` | `surface-freshness.text` | `状态未知` | semantic |
| `rayHit.position` | halo transform | hide + reason | semantic |
| `rayHit.state` | halo fill/dash | `unavailable` | semantic |
| `rayHit.blockReason` | reason text | `未命中可用表面` | display-only |
| `tracking.trustState` | shape/color/label | `需要处理` | semantic |
| `tracking.providerState` | error shape/retry-exit reason | `unavailable` | semantic |
| `geometry.conflictState` | conflicting label/offending cue | `none` | semantic |

**Variants `variants`**

- `worldHit`: grid, halo, normal, adjacent label.
- `controlCompact`: state glyph, human label, freshness only.
- `fitGuard`: area residual or height floor warning replaces generic reason.

**States `states`**

| State | Trigger | visual params | size change | motion | accessibility |
|---|---|---|---|---|---|
| loading/scanning | no plane yet | hollow dashed ring + `正在识别表面` | grid hidden | static/RM same | live-region polite |
| fresh | age under threshold, valid hit | circle, accent, grid 5% | grid extent adapts | MO-01 | `表面可测` |
| aging | age near threshold | triangle, warning, grid 2% | none | 120ms crossfade; RM instant | `表面信号变弱` |
| stale/offline | threshold exceeded | dashed, warning, grid hidden | none | MO-08 | `跟踪已暂停`; commits disabled |
| conflicting | fit/plane mismatch | square+danger, offending cue | reason label expands ≤2 lines | none | exact offending point announced |
| focused | gaze is on recovery control, not read-only glyph | compact row links focus to Scan/Exit control | none | 90ms | no false interactable role |
| boundary-disabled | no floor / out of extent | dashed boundary + reason | none | none | cause and recovery spoken |
| error | provider/capability error | square+`需要处理` | grid hidden | none | Exit always available |

State stacking precedence: `error/conflicting > stale > aging > loading > fresh`; focus is rendered only on a linked actionable control, never on the world evidence itself.

Combined placement guard: `meshRequired = placement.sourceKind == mesh_assisted`; `canCommit = planeFresh && rayHit.valid && placement.sourceKind known && (!meshRequired || (mesh.availability==available && mesh.hitConfidence>=projectMeshThreshold))`. `canComplete` also requires every committed point to retain valid provenance. `projectMeshThreshold=0.70` is a project starting assumption pending device calibration, not a platform constant. Unknown source/availability/confidence fails closed, labels `网格辅助不可用`, and keeps Undo/Back available.

### 5.3 Component `SpatialRuler`

| Field | Content |
|---|---|
| derivedFromTasks | T05, T06, T07, T08 |
| derivedFromData | `MeasurePoint[]`, `Segment[]`, `Polyline`, `RectangleFit`, `HeightProjection`, `MeasurementSession` |
| purpose | Render and edit trustworthy surface-aligned length/path/area/height evidence |
| layoutRole | `primary_spatial_subject` in Stage Mixed |
| priority | primary |
| runtimeRole | `measurementGeometryEditor` |

**Anatomy · Layout (anatomy.layout)**

```text
plane tangent XY / +Z normal
 P0●──ticks──segment──●Pn    value/trust at +35mm
       └ preview dashed ─○ hit
 path: joined segments + total at Pn
 area: four-point outline + 5% fill + centroid value
 height: floor glyph ┴ + vertical line + top value
```

Points are world anchors for the active session only. Line is +3mm, ticks +4mm, endpoints +6mm from the plane; the value is +35mm. Value billboard faces viewer but remains projected adjacent to geometry; collision priority active value > total > major tick label.

**Anatomy · Sizing (sizing)**

| Tier | Width × Height | Stage tier / fit |
|---|---|---|
| Regular | content bbox = point bbox + 0.08m margin; labels ≤0.22×0.06m | active surface inside 65°×40° core when possible |
| Compact | hide minor tick labels; label ≤0.18×0.06m | small surface / distant view |
| Constrained | endpoints+line+one value only; no grid/minor ticks | label collision or peripheral edge; still ≥12sp equivalent |

**Anatomy · Internal Metrics (metrics)**

| Metric | Value | Source / notes |
|---|---|---|
| background | none for geometry; `focusMatte` for value capsule | Stage cannot use glass |
| radius | value capsule 12dp-equivalent | radius.s |
| padding | value 8×12dp-equivalent | spacing.s |
| gap | value offset 35mm; label leader 8mm | world scale |
| stroke | line 3mm at 42%; outer silhouette 1mm when needed | approved direction §2 / adaptive contrast |
| icon | endpoints 8mm; floor/complete glyph 12mm | world scale |
| primary text | metric 18/24/650 + black outline | typography.metric |
| value/secondary | caption 12/17 for trust/unit | typography.caption |
| hitTarget | endpoint edit target 16mm visual / 56dp-equivalent interaction volume | platform floor mapping |

**Render Elements `renderSpec.elements[]`**

| id | Visible label | type | bind | state / semantic role |
|---|---|---|---|---|
| `ruler-preview` | — | dashed segment | `rayHit.position`, `points.last` | editing preview |
| `ruler-points` | — | endpoint collection | `points[]` | pinned/selected/boundary-invalid |
| `ruler-segments` | — | line collection | `segments[]` | committed geometry |
| `ruler-ticks` | 10cm / 50cm labels | tick collection | `segments[].lengthMeters`, `unitPreference.displayUnit` | scale evidence |
| `ruler-area-fill` | — | polygon fill | `rectangleFit.corners` | area variant only |
| `ruler-floor` | 地面 | floor glyph | `height.floorPoint` | height baseline |
| `ruler-value` | 125.4 cm | billboard metric | `measurement.formattedValue` | primary result |
| `ruler-total` | 总长 3.42 m | billboard metric | `polyline.totalMeters` | path total |
| `ruler-trust` | 表面可测/跟踪已暂停 | glyph+label | `measurement.trustState` | non-color trust |
| `ruler-unit` | cm / m / in | suffix | `unitPreference.displayUnit` | display format |

**Data Bindings `dataBindings[]`**

| Source path | Target | fallback | kind |
|---|---|---|---|
| `points[]` | endpoint transforms | empty prompt | semantic |
| `points[].planeId` | same-plane/session guard; not raw text | `unknown` → block area/mark partial | semantic |
| `points[].placedAt` | point order/undo provenance | append order; `时间未知` in accessible detail | semantic |
| `points[].rayDistanceMeters` | commit range guard | `unknown` → mark partial | semantic |
| `points[].surfaceNormal` | projection/orientation guard | use current plane normal only while fresh; otherwise stale | semantic |
| `points[].qualityState` | endpoint shape/trust aggregation | `需要处理` | semantic |
| `segments[]` | line/ticks | hide until 2 points | semantic |
| `rayHit.position` | preview endpoint | hide + readiness reason | semantic |
| `polyline.totalMeters` | `ruler-total.text` | `—` | display-only |
| `rectangleFit.corners` | area outline/fill | invalid outline + reason | semantic |
| `rectangleFit.residualMeters` | trust/complete guard | `无法确认矩形` | semantic |
| `geometry.planeDeviationMeters` | offending point/complete guard | `点未贴合表面` | semantic |
| `height.floorPoint` | floor glyph/baseline | `尚未找到地面` | semantic |
| `height.verticalMeters` | value | `—` | display-only |
| `measurement.rawMeters` | formatter input | never use cached formatted value | semantic |
| `measurement.formattedValue` | value text | `—` | display-only |
| `measurement.trustState` | trust glyph/label/style | `需要处理` | semantic |
| `measurement.calculationState` | value visibility/error reason | `error` if unknown at completion | semantic |
| `tracking.updatePending` | last geometry dash/value suppression | `false` | semantic |
| `editor.selectedPointIndex` | endpoint selected ring/edit target | none selected | semantic |
| `layout.labelCollision` | label priority hide/leader | `false`; constrained rule if unknown | semantic |
| `unitPreference.displayUnit` | suffix/tick interval format | `cm` | display-only |

**Variants `variants`**

- `line`: exactly two endpoints; ticks at 0.10m, major label each 0.50m.
- `path`: N≥2 endpoints; one active-segment value plus one total; completed segment labels dim.
- `area`: four corners projected to one plane; outline + 5% fill; one centroid area and one active-edge length.
- `height`: floor glyph, vertical projection and upper value; no slanted-distance value.

**States `states`**

| State | Trigger | visual params | size change | motion | accessibility |
|---|---|---|---|---|---|
| empty | zero points | only readiness/hit preview | bbox follows hit | MO-01/RM static | mode-specific instruction |
| loading/buffering | tracking update pending | last geometry dashed 50%; no new value | none | MO-08 | `正在更新表面` |
| editing/dragging | preview or endpoint selected | hollow preview; selected endpoint ring | edit target expands to 56dp-equivalent | MO-01 | announces point index/validity |
| committed | point appended | filled endpoint + segment | bbox grows with data | MO-02/03 | persistent filled shape |
| complete | mode guard valid | complete diamond + value matte | one value added | MO-04 | `测量完成` |
| boundary-disabled | insufficient points/floor/fit | Finish disabled; offending point square | reason max 2 lines | none | exact requirement announced |
| stale | tracking lost | all geometry dashed; `not current` label | none | MO-08 | value prefixed `上次结果` |
| error/conflicting | plane mismatch/calculation error | danger square; no trustworthy value | value replaced by reason | none | Undo/Reposition actions named |
| overflow/collision | labels overlap/FOV edge | lower priority labels hide; leader shifts value | geometry unchanged | 120ms reposition; RM instant | hidden tick labels remain nonessential |

State stacking precedence: `error/conflicting > stale > boundary-disabled > editing > complete > committed > empty`; focused endpoint can coexist with editing but not stale/error.

### 5.4 Component `MeasureControlPanel`

| Field | Content |
|---|---|
| derivedFromTasks | T04, T06, T08, T10, T11 |
| derivedFromData | active mode/unit, point stack, validity, capture state, unsaved state |
| purpose | Keep high-frequency and safety actions visible without competing with world evidence |
| layoutRole | `primary_control` in `C-CONTROL` R2–R3 |
| priority | primary |
| runtimeRole | `measurementCommandController` |

**Anatomy · Layout (anatomy.layout)**

```text
┌─────────────────────────────────────┐
│ A [↶ 撤销] [✓ 完成________] [← 返回] │
│ B [模式⌄] [cm|m|in] [清除] [截图]   │
│ C compact overflow: 模式/单位/清除/截图 │
└─────────────────────────────────────┘
```

Grid default 2 rows: A 1fr/2fr/1fr, B 1fr/1.5fr/1fr/1fr; 12dp gaps. Compact keeps A and replaces B with two 56dp popover/overflow triggers; Large merges A/B into one ordered band.

**Anatomy · Sizing (sizing)**

| Tier | Width × Height | Window tier / fit |
|---|---|---|
| Large | 912×72dp | max `safeContent` 912×396; one row |
| Regular | 672×156dp | default `safeContent` 672×276; two rows |
| Compact | 512×120dp | min `safeContent` 512×216; 56dp primary + 8dp gap + 56dp secondary |

**Anatomy · Internal Metrics (metrics)**

| Metric | Value | Source / notes |
|---|---|---|
| background | none | parent controlShell glass |
| radius | 20dp group; 12dp buttons | radius.m/s |
| padding | 8dp row; 12×16dp buttons | spacing.s/m |
| gap | 12dp targets; 8dp icon-text | scale |
| stroke | 1dp `#FFFFFF33`; focused 2dp accent | tokens |
| icon | 28dp | iconSize.m |
| primary text | body 15/22/600 | actions |
| value/secondary | caption 12/17 | mode/unit status |
| hitTarget | 56×56dp minimum | all actions |

**Render Elements `renderSpec.elements[]`**

| id | Visible label | type | bind | state / semantic role |
|---|---|---|---|---|
| `ctl-undo` | 撤销 | button | `points.canUndo` | reversible action |
| `ctl-finish` | 完成 | primary button | `geometry.canComplete` | complete action |
| `ctl-back` | 返回 | button | `session.hasUnsavedWork` | stable exit |
| `ctl-mode` | 直线/连续/面积/高度 | menu/choice | `session.mode` | mode decision |
| `ctl-unit` | cm / m / in | segmented choice | `unitPreference.displayUnit` | format decision |
| `ctl-clear` | 清除 | destructive request button | `points.count` | opens D0 only |
| `ctl-capture` | 截图 | button | `capture.state` | async request |
| `ctl-overflow` | 更多 | menu button | `window.tier` | compact only |
| `ctl-save` | 保存 | primary button | `session.canSave` | M2 resultReview; triggers TR-12 |
| `ctl-new` | 新测量 | button | `session.canStartNew` | M2 resultReview; triggers TR-25 |

**Data Bindings `dataBindings[]`**

| Source path | Target | fallback | kind |
|---|---|---|---|
| `points.canUndo` | undo enabled/reason | disabled `没有可撤销的点` | semantic |
| `geometry.canComplete` | finish enabled/reason | disabled + missing condition | semantic |
| `session.hasUnsavedWork` | Back routing | safe immediate exit | semantic |
| `session.mode` | mode label/selected | `直线` | semantic |
| `unitPreference.displayUnit` | selected unit | `cm` | display-only |
| `points.count` | clear enabled/Dialog scope | 0 disables | semantic |
| `capture.state` | capture enabled/progress | `unavailable` + reason | semantic |
| `window.tier` | overflow layout | regular | semantic |
| `receipt.state` | secondary-row visibility | `idle` shows secondary; active receipt replaces it | semantic |
| `interaction.focusedId` | focused target outline/accessibility | none | semantic |
| `interaction.pressedId` | pressed target scale | none | semantic |
| `control.popoverOpen` | compact popover variant/focus trap | `false` | semantic |
| `session.canSave` | Save enabled/reason | disabled + `结果尚未完成` | semantic |
| `session.canStartNew` | New enabled/reason | disabled while async write/capture blocks replacement | semantic |

**Variants `variants`**

- `regular`: two rows, seven visible targets.
- `large`: one row; all labels visible.
- `compact`: Undo/Finish/Back visible; mode/unit in one popover, clear/capture in `更多`; no gesture-only command.
- `receiptOccupied`: keep primary Undo/Finish/Back row; hide secondary row while sibling StatusReceipt occupies its row; Large may place receipt below without hiding secondary.
- `resultReview`: primary `[Undo][Save][New][Back]`; secondary only `[Unit][Screenshot]`, so six actions remain under the ≤7 density ceiling. `Save`→D1, `New`→TR-25; Finish/Mode/Clear are not shown.

**States `states`**

| State | Trigger | visual params | size change | motion | accessibility |
|---|---|---|---|---|---|
| default | active Stage | neutral glass controls | tier-defined | none | named buttons |
| focused | gaze target | 2dp accent + 1.04× | target only | 90ms | name+state announced |
| pressed/selected | pinch/trigger | 0.96×; selected fill 18% | none | 70ms | selected unit/mode text |
| disabled/boundary | no undo/invalid finish | 55% opacity + reason | none | none | no hover; reason announced |
| loading | Stage/capture init | capture spinner only; other controls remain | none | 160ms; RM static icon | `正在截图` |
| editing | mode/unit popover open | originating target selected; popover focused | compact adds overlay within panel | 160ms; RM immediate | focus trapped in popover |
| empty | zero points | Undo/Clear disabled; Start guidance in status | none | none | Finish requirement announced |
| error | action failed | target danger square + StatusReceipt | none | MO-07 | Retry/Dismiss reachable |
| overflow | compact tier | `更多` contains clear/capture | B collapses | none | menu order preserved |
| result-review | state M2 | Save/New replace Finish; six-action density | same tier bounds | 120ms/RM immediate | Save/New exact roles announced |

State stacking precedence: `Dialog focus > loading/error on affected action > disabled > pressed/selected > focused > default`; one action loading never disables Back.

### 5.5 Component `DecisionDialog`

| Field | Content |
|---|---|
| derivedFromTasks | T08, T11 |
| derivedFromData | point count, unsaved result, requested destructive action |
| purpose | Separate a gesture/request from irreversible clear or discard |
| layoutRole | `critical_modal` spanning `C-CONTROL` |
| priority | secondary |
| runtimeRole | `riskConfirmation` |

**Anatomy · Layout (anatomy.layout)**

```text
┌──────────────────────────────┐
│ A ┌╌ warning shape ╌┐ title  │
│ B scope / consequence copy   │
│ C [取消 / 继续测量] [清除/丢弃] │
└──────────────────────────────┘
```

Grid one column. Regular/Large rows 40/1fr/56dp with 8dp row gaps and 24dp outer padding; Compact rows 40/1fr/56dp with 8dp gaps and 16dp outer padding. Actions are two equal columns with 8dp gap; modal traps focus and dims underlying commands. Compact arithmetic at 1.5× text: 52 title + 44 body + 56 actions + 16 gaps + 32 padding = 200dp ≤ 208dp.

**Anatomy · Sizing (sizing)**

| Tier | Width × Height | Window tier / fit |
|---|---|---|
| Regular | 520×220dp | default `safeContent` 672×276; calculated content ≤204dp at 1.0× |
| Compact | 464×208dp | min `safeContent` 512×216; 1.5× calculated 200dp, actions remain 56dp |
| Large | 560×240dp | max `safeContent` 912×396; no extra information added |

**Anatomy · Internal Metrics (metrics)**

| Metric | Value | Source / notes |
|---|---|---|
| background | customColor `#10252EF5` | focusMatte; not glass+custom stacked |
| radius | 32dp | radius.l |
| padding | 24dp regular/large; 16dp compact | spacing.l/m |
| gap | 8dp rows/actions | spacing.s |
| stroke | 1dp `#FF657766` | danger token |
| icon | 28dp | iconSize.m |
| primary text | title 20/26/600 | title |
| value/secondary | body 15/22 | body |
| hitTarget | 56×56dp | both actions |

**Render Elements `renderSpec.elements[]`**

| id | Visible label | type | bind | state / semantic role |
|---|---|---|---|---|
| `dialog-shape` | 警告 | triangle/square glyph | `decision.kind` | non-color risk |
| `dialog-title` | 清除所有标记？/放弃未保存结果？/切换到面积？ | heading | `decision.kind`, `modeChange.requestedMode` | human risk statement |
| `dialog-scope` | 将清除 4 个点/1 个未保存结果 | body text | `decision.scopeLabel` | consequence |
| `dialog-cancel` | 取消/继续测量 | button | none | safe default |
| `dialog-confirm` | 清除所有/丢弃并返回 | destructive button | `decision.kind` | explicit irreversible action |

**Data Bindings `dataBindings[]`**

| Source path | Target | fallback | kind |
|---|---|---|---|
| `decision.kind` | title/glyph/confirm label | close Dialog safely | semantic |
| `decision.scopeLabel` | scope text | `当前测量内容` | display-only |
| `points.count` | clear scope | `0 个点` and confirm disabled | semantic |
| `session.hasUnsavedWork` | discard guard | cancel/close | semantic |
| `modeChange.priorState` | Cancel/Back return target | missing → close safely to M0 without clearing | semantic |
| `modeChange.requestedMode` | title/confirm/target state | missing → confirm disabled | semantic |

**Variants `variants`**

- `clear`: confirms session point/geometry deletion, returns M0.
- `discardExit`: confirms unsaved discard and Stage close, returns H0.
- `modeChange`: if points exist, confirms reset then remains in Stage with new mode.

**States `states`**

| State | Trigger | visual params | size change | motion | accessibility |
|---|---|---|---|---|---|
| default | request opens | matte 96%, focus on Cancel | variant text only | MO-06 | modal role/title |
| focused | gaze action | outline; confirm uses danger square | none | 90ms | action+consequence |
| pressed | pinch action | 0.96× target | none | 70ms | click feedback |
| disabled | scope empty/state changed | confirm 55% + reason | none | none | reason announced |
| loading | destructive transaction | progress beside confirm; Cancel disabled only after commit | none | RM static icon | `正在处理` |
| error | transaction fail | square+error text replaces B scope; Retry/Cancel | none; body scrolls if needed | MO-07 | error and recovery |
| empty | decision no longer applicable | auto-close to prior state | N/A | none | `操作已取消` |
| overflow | 1.5× text | internal body scroll; actions fixed | within 464×208dp | none | logical reading order |

State stacking precedence: `error > loading > disabled > pressed > focused > default`; Dialog always stacks above controls and below system permission UI.

### 5.6 Component `RecordComposer`

| Field | Content |
|---|---|
| derivedFromTasks | T07, T09 |
| derivedFromData | completed `MeasurementSession`, record name, raw meters, unit, time, screenshot URI |
| purpose | Name and persist an honest numeric record without world-pose fields |
| layoutRole | `focused_form` spanning `C-CONTROL` |
| priority | secondary |
| runtimeRole | `measurementRecordEditor` |

**Anatomy · Layout (anatomy.layout)**

```text
┌────────────────────────────────┐
│ A 保存测量          125.4 cm ◇ │
│ B 名称 [╌ 客厅桌宽________ ╌]   │
│ C 时间/模式 · 截图状态           │
│ D [取消]              [保存]     │
└────────────────────────────────┘
```

Grid one column. Regular/Large rows 36/56/32/56dp with 12dp gaps and 20dp outer padding: 36+56+32+56+36+40=256dp ≤ Regular 268dp. Compact collapses C metadata into an accessible screenshot/status glyph in A and uses rows 52/56/56dp, 8dp gaps, 16dp padding: 52+56+56+16+32=212dp ≤216dp even at 1.5×. B is the only text input; actions stay fixed.

**Anatomy · Sizing (sizing)**

| Tier | Width × Height | Window tier / fit |
|---|---|---|
| Regular | 600×268dp | default `safeContent` 672×276; 1.0× calculated 256dp |
| Compact | 512×216dp | min `safeContent` 512×216; C folds into A; 1.5× calculated 212dp |
| Large | 640×284dp | max `safeContent` 912×396; no extra fields added |

**Anatomy · Internal Metrics (metrics)**

| Metric | Value | Source / notes |
|---|---|---|
| background | customColor `#10252EF5` | focusMatte |
| radius | 32dp | radius.l |
| padding | 20dp regular/large; 16dp compact | calibrated within safeContent |
| gap | 12dp regular rows; 8dp compact; 16dp action columns | scale |
| stroke | input 1dp `#FFFFFF33`, focus 2dp accent | tokens |
| icon | 20dp | iconSize.s |
| primary text | title 20/26; metric 18/24 mono | typography |
| value/secondary | body 15/22; caption 12/17 | typography |
| hitTarget | 56×56dp buttons/input row | platform floor |

**Render Elements `renderSpec.elements[]`**

| id | Visible label | type | bind | state / semantic role |
|---|---|---|---|---|
| `record-title` | 保存测量 | heading | none | form orientation |
| `record-value` | 125.4 cm | metric | `session.formattedValue` | read-only result |
| `record-trust` | 测量完成 | diamond+label | `session.trustState` | trust |
| `record-name` | 名称 | text field | `recordDraft.name` | editable decision |
| `record-meta` | 直线 · 22:35 | caption | `session.mode`, `session.completedAt` | context |
| `record-shot` | 截图已关联/无截图 | status | `session.screenshotUri` | attachment status |
| `record-shot-link` | 打开截图 | link | `session.screenshotUri` | real `href`; current-session URI only; hidden when null/invalid |
| `record-cancel` | 取消 | button | none | preserve unsaved result |
| `record-save` | 保存 | primary button | `recordDraft.valid` | persistence action |

**Data Bindings `dataBindings[]`**

| Source path | Target | fallback | kind |
|---|---|---|---|
| `session.rawMeters` | persistence payload/formatter | block save if null | semantic |
| `session.formattedValue` | value text | `—` | display-only |
| `session.trustState` | trust shape/label | `需要处理` | semantic |
| `recordDraft.name` | input value | generated `未命名测量 22:35` | semantic |
| `recordDraft.valid` | Save enabled | disabled + field reason | semantic |
| `session.mode` | meta text | `测量` | display-only |
| `session.completedAt` | localized time | `时间未知` | display-only |
| `session.screenshotUri` | `record-shot` status + `record-shot-link.href` | `无截图`; hide link; foreign session rejected | semantic ownership |
| `capture.sessionId` | screenshot-link ownership guard | mismatch → hide/reject link | semantic |
| `recordWrite.state` | form state/receipt | preserve draft on error | semantic |

**Variants `variants`**

- `create`: generated default name, Save writes a new record.

**States `states`**

| State | Trigger | visual params | size change | motion | accessibility |
|---|---|---|---|---|---|
| default | form opens | value+input+actions | tier-defined | MO-06 | focus starts on name |
| focused/editing | gaze+pinch field | 2dp accent, caret/keyboard route | keyboard may occlude; panel shifts within quadrant | system text input | field label/value announced |
| selected | all name text selected | selection fill 22% | none | none | selection announced |
| disabled | result invalid/write unavailable | Save 55% + reason | none | none | reason announced |
| loading | write pending | Save progress; input preserved | none | static under RM | `正在保存` |
| empty | blank name | field warning; generated-name option | none | none | explicit validation |
| error | DB write fails | square+`保存失败`; Retry replaces C metadata row | none | MO-07 | draft preserved |
| overflow | 1.5× text/long name | input horizontal edit; meta ellipsis with full accessible value | actions fixed | none | no hidden required info |

State stacking precedence: `error > loading > disabled > editing/selected > focused > default`; keyboard/system permission UI stacks above form; Stage geometry stays behind and inert.

`StatusReceipt.recordWrite` owns the bounded composer row only: pending/error/success replaces `record-meta` + `record-shot`, never the name field/value/Cancel. It does not coexist as a second floating panel. Error preserves the draft; success requires positive `recordWrite.recordId`.

### 5.7 Component `StatusReceipt`

| Field | Content |
|---|---|
| derivedFromTasks | T09, T10 |
| derivedFromData | `ScreenshotReceipt`, record-write receipt |
| purpose | State async pending/success/failure truthfully and provide retry/dismiss |
| layoutRole | `status_and_recovery`: Compact/Regular replaces R3 secondary row; Large uses R4 |
| priority | secondary |
| runtimeRole | `asyncOperationReceipt` |

**Anatomy · Layout (anatomy.layout)**

```text
┌─────────────────────────────────┐
│ A [shape/progress] B status text │
│                     C [retry][×] │
└─────────────────────────────────┘
```

Grid one row 36dp icon / 1fr text / auto actions; 8dp gap; error may add one caption line under B. Only one receipt is visible; newer operation queues after resolution.

**Anatomy · Sizing (sizing)**

| Tier | Width × Height | Window tier / fit |
|---|---|---|
| Regular | 672×56dp success / 76dp error / 96dp overflow | replaces secondary row; assembly 228≤276 at 96dp |
| Compact | 512×72dp normal / 96dp overflow | replaces secondary row; assembly 204≤216 at 96dp |
| Large | 912×56dp normal / 96dp overflow | R4; assembly 236≤396 at 96dp |

**Anatomy · Internal Metrics (metrics)**

| Metric | Value | Source / notes |
|---|---|---|
| background | customColor `#07151CE8` | surfaceInk, not glass stacked |
| radius | 12dp | radius.s |
| padding | 8×12dp | spacing.s |
| gap | 8dp | spacing.s |
| stroke | 1dp semantic color at 55% | token |
| icon | 20dp | iconSize.s |
| primary text | body 15/22 | typography.body |
| value/secondary | caption 12/17 | reason/storage note |
| hitTarget | Retry/Dismiss 56×56dp | platform floor |

**Render Elements `renderSpec.elements[]`**

| id | Visible label | type | bind | state / semantic role |
|---|---|---|---|---|
| `receipt-shape` | 进行中/完成/需要处理 | progress/circle/diamond/square | `receipt.state` | non-color state |
| `receipt-message` | 正在保存截图/截图已保存/保存失败 | text | `receipt.operation`, `receipt.state` | human result |
| `receipt-detail` | 可在历史记录中查看/存储不可用 | caption | `receipt.reason` | trust/recovery |
| `receipt-link` | 打开截图 | link | `receipt.uri` | screenshot success only; href must match current capture session |
| `receipt-retry` | 重试 | button | `receipt.retryable` | error action |
| `receipt-dismiss` | 关闭 | button | `receipt.dismissible` | recovery |

**Data Bindings `dataBindings[]`**

| Source path | Target | fallback | kind |
|---|---|---|---|
| `receipt.operation` | message verb | `操作` | display-only |
| `receipt.state` | shape/color/label/message | error `状态未知` | semantic |
| `receipt.reason` | detail text | `请重试` on error | display-only |
| `receipt.uri` | `receipt-link.href` + success attachment | hide link; never fake saved | semantic |
| `capture.requestId` | pending/success correlation | mismatch → ignore receipt, remain pending/error | semantic |
| `capture.sessionId` | current-session ownership guard | mismatch → reject URI + error | semantic |
| `recordWrite.recordId` | record-write success gate/detail | missing/nonpositive → error; never show saved | semantic |
| `receipt.retryable` | Retry visibility/enabled | hidden | semantic |
| `receipt.dismissible` | Dismiss enabled | true after terminal state | semantic |
| `receipt.updatedAt` | accessible freshness | `时间未知` | display-only |
| `capture.returnState` | success/dismiss routing | origin active state required; otherwise safe M0 | semantic |

**Variants `variants`**

- `screenshot`: Stage capture pending/success/error; success links URI to current session.
- `recordWrite`: D1 session-record create pending/success/error in legal Full Space `C-CONTROL`; H1R rename is exclusively owned by `HubWorkspace` inline state and never uses this receipt.

**States `states`**

| State | Trigger | visual params | size change | motion | accessibility |
|---|---|---|---|---|---|
| loading | request accepted | static progress glyph + `正在…` | regular 56dp | MO-07; RM static glyph | live-region polite |
| success | positive system receipt + URI/write id | diamond+success; auto-dismiss 2.5s | 56dp | MO-07; RM immediate | message remains in history where relevant |
| error | terminal failure | square+danger; Retry/Dismiss | 76dp | MO-07; persistent | live-region assertive |
| empty | no operation | component not rendered | 0 | none | no empty focus stop |
| focused | Retry/Dismiss gaze | 2dp outline | target ≤1.04× | 90ms | action label |
| pressed | action pinch | 0.96× | none | 70ms | click feedback |
| disabled | retry unavailable | Retry hidden/disabled + reason | none | none | reason text |
| overflow | long reason/text scale | two-line detail + ellipsis with accessible full text | max 96dp | none | full value announced |

State stacking precedence: `error > loading > success > empty`; focused/pressed applies only to actions; latest terminal receipt supersedes an older success but never an unresolved error.

### 5.8 Component structure integrity checklist

| Core Component | Base | layout | sizing | metrics | renderSpec | bindings | variants | states+precedence | Verdict |
|---|---|---|---|---|---|---|---|---|---|
| HubWorkspace | yes | yes | yes | yes | yes | yes | yes | yes | pass |
| SurfaceReadiness | yes | yes | yes | yes | yes | yes | yes | yes | pass |
| SpatialRuler | yes | yes | yes | yes | yes | yes | yes | yes | pass |
| MeasureControlPanel | yes | yes | yes | yes | yes | yes | yes | yes | pass |
| DecisionDialog | yes | yes | yes | yes | yes | yes | yes | yes | pass |
| RecordComposer | yes | yes | yes | yes | yes | yes | yes | yes | pass |
| StatusReceipt | yes | yes | yes | yes | yes | yes | yes | yes | pass |

### 5.9 Coverage reconciliation

#### Table A · Data entity / decision variable → binding

| Entity / variable | Timeliness | Catching binding | Presentation / semantic method | Gap disposition |
|---|---|---|---|---|
| current mode | session | HubWorkspace/Control `session.mode` | selected human label | binding present |
| required point count | per mode | Control/Ruler `geometry.canComplete`, `points[]` | Finish guard + point glyphs | binding present |
| point coordinates | session stable after commit | Ruler `points[]` | world endpoints | binding present |
| point planeId | session/live validation | Ruler `points[].planeId` | same-plane guard; raw ID intentionally not shown because no user decision value | binding present |
| point placedAt | session | Ruler `points[].placedAt` | point order/undo provenance; accessible time fallback | binding present |
| point ray distance | commit/session | Ruler `points[].rayDistanceMeters` | range guard; partial when missing | binding present |
| point surface normal | commit/session | Ruler `points[].surfaceNormal` | projection/orientation guard | binding present |
| point quality state | commit/session | Ruler `points[].qualityState` | endpoint shape + aggregate trust | binding present |
| plane type | live | Readiness `plane.type` | human `墙面/桌面/地面` and height/area guard | binding present |
| plane extent | live | Readiness `plane.extent` | clipped grid | binding present |
| plane normal | live | Readiness `plane.normal` | normal cue | binding present |
| plane confidence | live | Readiness `plane.confidence` | state shape/color/label | binding present |
| mesh hit confidence/availability | live | Readiness `mesh.hitConfidence`, `mesh.availability` | auxiliary source glyph/label + fail-closed combined guard | binding present |
| ray hit stability/position | per frame | Readiness/Ruler `rayHit.*` | preview halo/segment | binding present |
| tracking age | live ms | Readiness `plane.ageMs` | freshness caption/state | binding present |
| point-to-plane deviation | on point/update | Ruler `geometry.planeDeviationMeters` | offending point + reason | binding present |
| rectangle residual | point-change recompute | Ruler `rectangleFit.residualMeters` | completion guard | binding present |
| floor baseline | live/session | Ruler `height.floorPoint` | floor glyph/reason | binding present |
| raw meter value | point-change/session | Ruler/RecordComposer `measurement/session.rawMeters` | semantic source, never direct colored display | binding present |
| display unit | preference/session | Hub/Control/Ruler `unitPreference.displayUnit` | cm/m/in label | binding present |
| explicit complete/clear | event/session | Control `geometry.canComplete`; Dialog `decision.kind` | actionable guard/confirmation | binding present |
| `PlaneObservation` | live, degrades immediately | SurfaceReadiness plane bindings | grid/shape/freshness | binding present |
| `RayHitPreview` | per frame/throttled | SurfaceReadiness/Ruler rayHit bindings | hollow hit/preview | binding present |
| `MeasurePoint` | session | SpatialRuler `points[]` | endpoint/stack | binding present |
| `Segment/Polyline` | immediate recompute | SpatialRuler `segments[]/polyline.totalMeters` | lines/ticks/total | binding present |
| `RectangleFit` | immediate recompute | SpatialRuler `rectangleFit.*` | outline/fill/guard | binding present |
| `HeightProjection` | immediate recompute | SpatialRuler `height.*` | floor/vertical/value | binding present |
| `MeasurementSession` | session | Ruler/Control/RecordComposer `session.*` | geometry/result/save | binding present |
| `MeasurementRecord` | local persistent | HubWorkspace `records.items` | history list/detail, no pose | binding present |
| `MeasurementRecord.recordId` | persistent write result | StatusReceipt `recordWrite.recordId`; Hub `records.items[].recordId` row key/selection | positive-id success gate | binding present |
| `CaptureQueueItem` request/session/return | async | StatusReceipt `capture.requestId`, `capture.sessionId`, `capture.returnState` | correlation/ownership/routing | binding present |
| current-session/record screenshot URI | async→persistent | Receipt `receipt.uri`; RecordComposer `session.screenshotUri`; Hub `records.selected.screenshotUri` | real href; null/foreign URI hidden/rejected | binding present |
| `ScreenshotReceipt` | async | StatusReceipt `receipt.*` | pending/success/error + owned link | binding present |
| `UnitPreference` | persistent preference | Hub/Control/Ruler unit binding | display-only formatting | binding present |

#### Table B · Task decision output → component interaction

| Task/output | Type | Catching component + element + behavior | Gap disposition |
|---|---|---|---|
| T01 start prerequisite | actionable | Hub `hub-mode`, `hub-unit`, `hub-start` select/activate | covered |
| T02 explicit Full Space entry | actionable | Hub `hub-start` creates Stage only on activation | covered |
| T03 ready/degraded/unavailable | actionable guard | Readiness `surface-*`; Control Finish/commit guards | covered |
| T04 mode + unit | actionable | Hub/Control `hub/ctl-mode`, `hub/ctl-unit` | covered |
| T05 commit/wait/reposition | actionable | Ruler `ruler-preview/points`; ray tap/trigger | covered |
| T06 continue/finish/blocked | actionable | Control `ctl-finish`; Ruler trust/validity | covered |
| T07 accept/undo/new/discard | actionable | accept→`ctl-save`/RecordComposer; undo→`ctl-undo`; new→`ctl-new`/TR-25; discard→`ctl-back`→D2 explicit confirm; Ruler result remains visible | covered |
| T08 undo/request clear/cancel/confirm | actionable | Control `ctl-undo/clear`; DecisionDialog actions | covered |
| T09 save/retry/cancel | actionable | Control `ctl-save`→D1; RecordComposer input/save/cancel; success requires `recordWrite.recordId>0`; StatusReceipt Retry preserves draft | covered |
| T10 capture/retry/dismiss | actionable | Control `ctl-capture`; StatusReceipt correlates requestId/sessionId and owns `receipt-link`; Retry/Dismiss preserve `capture.returnState` | covered |
| T11 close Stage/return | actionable | Control `ctl-back` + DecisionDialog discard; stable H0 | covered |
| T12 find/read/rename/delete | actionable | Hub `hub-records`, `hub-record-rename`, `hub-record-delete`, `hub-record-name-input/save/cancel`, `hub-record-delete-confirm`; H1R/H1D remain inside C-HUB | covered; no restore action or C-CONTROL by design |

#### Table C · Primary component runtime substates

| Primary → subcomponent | Runtime substate | Rendering primitive | Data binding |
|---|---|---|---|
| Hub→record list | loading | 3 skeleton rows | `records.loadState=loading` |
| Hub→record list | empty | outline + guidance | `records.items=[]` |
| Hub→record list | error | square + Retry | `records.loadState=error` |
| Hub→route/mode | editing/selected | check + fill | `hub.route`, `session.mode` |
| Hub→Start | boundary-disabled | lock+reason | `capability.startEnabled=false` |
| Hub→record list | overflow | internal scroll | `records.items.length>5` |
| Hub→history inline editor | history-editing / history-delete-confirm | name input+Save/Cancel / warning+Delete/Cancel | `historyDraft.name`, `historyDraft.valid`, `records.selectedId` |
| Readiness→grid | loading/scanning | hollow dashed ring | `plane.state=loading` |
| Readiness→hit | fresh | circle+grid | `tracking.trustState=ready` |
| Readiness→hit | aging | triangle+2% grid | `tracking.trustState=aging` |
| Readiness→hit | stale/offline | dashed+reason | `tracking.trustState=stale` |
| Readiness→provider | error | square+exit/retry reason | `tracking.providerState=error` |
| Readiness→fit guard | conflicting | square+offending cue | `geometry.conflictState`, `rectangleFit.residualMeters` |
| Readiness→boundary | disabled | dashed boundary | `rayHit.blockReason` |
| Ruler→points | empty | preview only | `points=[]` |
| Ruler→geometry | loading/buffering | dashed last geometry | `tracking.updatePending` |
| Ruler→endpoint | editing/dragging | selected ring | `editor.selectedPointIndex` |
| Ruler→segment | committed | filled endpoint/line | `points[]`, `segments[]` |
| Ruler→result | complete | diamond+metric | `measurement.formattedValue` |
| Ruler→geometry | stale | dashed `上次结果` | `measurement.trustState=stale` |
| Ruler→calculation | error | square+no trustworthy value | `measurement.calculationState=error` |
| Ruler→point provenance | conflicting | offending point square+Undo | `geometry.planeDeviationMeters`, `points[].planeId` |
| Ruler→completion boundary | boundary-disabled | offending point/floor/fit square + exact recovery reason | `geometry.canComplete=false`, `rayHit.blockReason`, `height.floorPoint`, `rectangleFit.residualMeters` |
| Ruler→labels | overflow/collision | priority hide/leader | `layout.labelCollision=true` |
| Control→commands | empty | Undo/Clear disabled | `points.count=0` |
| Control→action | focused/pressed | outline/0.96× | `interaction.focusedId/pressedId` |
| Control→Finish | boundary-disabled | disabled+reason | `geometry.canComplete=false` |
| Control→popover | editing | focused overlay | `control.popoverOpen=true` |
| Control→Capture | loading | spinner | `capture.state=pending` |
| Control→action | error | square+receipt | `capture.state=error` |
| Control→secondary row | overflow | More menu | `window.tier=compact` |
| Control→result controls | result-review | Save/New buttons replace Finish | `session.canSave`, `session.canStartNew` |
| Control→mode/unit | selected | check/fill and human label; raw meters unchanged on unit change | `session.mode`, `unitPreference.displayUnit` |

## 6. Material and Depth Semantics

| Layer | treatment | glassStyle | opacity | content | contrast handling |
|---|---|---|---:|---|---|
| C-HUB shell | glass | Thick | 0.92 | preparation/history | Vibrant light; fallback `surfaceInk` |
| C-CONTROL shell | glass | Thickest | 0.94 | commands/status | Vibrant ultralight; fallback `surfaceInk` |
| Dialog/form/receipt | matte/customColor | none | 0.92–0.96 | risk/body/form | white on `#10252E/#07151C`; shape+text semantics |
| Stage value label | matte | none | 0.92 | metric/trust | white + black outline/backing |
| world ruler | opaque geometry | none | line .42, points .92 | spatial evidence | adaptive cyan + outer silhouette |
| plane grid | opaque geometry | none | .05 max | local readiness | hidden when texture/confidence makes it noisy |

Depth: environment 0; grid +1mm; line +3mm; ticks +4mm; endpoints +6mm; label +35mm toward viewer. The grid is 0.24×0.24m with an outer 0.08m fade. Nearer means current decision. Stage/Volumetric 3D never relies on glass. No TabBar, so its known glass issue is not applicable. Screenshot/image regions terminate Vibrant and use an opaque border/backing.

## 7. Data Display and Semantic Contract

`displayOnlyPaths[]`: `measurement.formattedValue`, `polyline.totalFormatted`, `record.name`, `record.completedAt`, `record.modeLabel`, `record.screenshotUri`, `unitPreference.displayUnit`, `receipt.reason`, `plane.ageFormatted`. Values are localized human copy, never database enums.

`semanticEnumPaths[]`: `plane.state`, `tracking.trustState`, `tracking.providerState`, `rayHit.state`, `measurement.trustState`, `receipt.state`, `records.loadState`, `permission.state`; each maps to §3.2 aliases and human labels.

| Source enum path | Source value(s) | semantic key | Visible human label / fallback |
|---|---|---|---|
| `plane.state` | `loading`, `scanning` | pending | 正在识别表面 |
| `plane.state` | `fresh`, `ready` | ready | 表面可测 |
| `plane.state` | `partial` | aging | 表面信息不完整 |
| `rayHit.state` | `fresh`, `valid` | ready | 可放置标记点 |
| `rayHit.state` | `unavailable`, `out_of_extent` | unavailable | 未命中可用表面 |
| `tracking.trustState` | `ready`, `fresh` | ready | 表面可测 |
| `tracking.trustState` | `aging`, `degraded`, `partial` | aging | 表面信号变弱 |
| `tracking.trustState` | `stale`, `offline` | stale | 跟踪已暂停 |
| `tracking.providerState` | `error` | error | 空间跟踪需要处理 |
| `measurement.trustState` | `complete`, `saved` | complete | 测量完成 |
| `measurement.trustState` | `ready`, `fresh` | ready | 当前测量有效 |
| `measurement.trustState` | `aging`, `partial` | aging | 测量依据变弱 |
| `measurement.trustState` | `stale`, `offline` | stale | 上次结果；跟踪已暂停 |
| `measurement.trustState` | `conflicting`, `error` | error | 测量需要处理 |
| `receipt.state` | `idle` | ready | no visible receipt; component omitted |
| `receipt.state` | `loading`, `pending` | pending | 正在保存 |
| `receipt.state` | `success` | complete | 已保存 |
| `receipt.state` | `error`, `failed` | error | 保存失败 |
| `records.loadState` | `loading` | pending | 正在读取记录 |
| `records.loadState` | `success`, `empty` | ready | 记录已就绪 / 还没有记录 |
| `records.loadState` | `error` | error | 记录读取失败 |
| `permission.state` | `granted` | ready | 权限已允许 |
| `permission.state` | `permission_denied`, `denied`, `restricted` | unavailable | 需要空间感知权限 |
| any semantic path | null/unknown | unavailable | 状态未知；相关高风险动作禁用 |

| Data state | Source/update | Trust / presentation |
|---|---|---|
| loading | provider/DB/capture request start | skeleton/progress + human operation; no result claim |
| fresh | `plane.ageMs≤250ms`, provider connected, all geometric guards true | ready circle + `表面可测`; commit allowed |
| aging | observation approaches threshold | warning triangle + age; commit policy may block at configured boundary |
| stale/offline | threshold exceeded/provider lost | dashed `跟踪已暂停`; last value prefixed `上次结果`; commit blocked |
| partial | mesh available but plane/normal/extent incomplete | triangle + missing requirement; no precise completion |
| conflicting | cross-plane/fit residual conflict | square+offending point; result blocked |
| permission_denied | user/system denies | Shared Space explanation + Settings route; no retained Stage |
| error | calculation/DB/capture/provider failure | square + reason + retry/undo/exit |

Project freshness defaults (to validate on device; not claimed as a platform constant): `fresh = plane.ageMs ≤250ms` with provider connected and all geometric guards true; `aging = 251–750ms`; `stale = >750ms`. Provider disconnect/loss becomes stale immediately without waiting 750ms. Only `fresh` permits point commit/Finish. `aging` keeps preview visible but blocks commit/Finish with `表面信号变弱`; `stale/offline` shows last-known dashed geometry with `上次结果`. Missing/unknown age, provider state, planeId, normal or quality is **fail-closed**: map to `unavailable`, suppress a trustworthy new value, disable commit/Finish, and keep Undo/Back available.

Trust policy: raw meters are canonical; display-unit changes reformat raw data only. Freshness/trust is adjacent to spatial value and uses the project thresholds above. Stale values never masquerade as live. Capture/save success requires a positive receipt. History never contains/restores world-pose fields. Results are estimates; the `<2cm` target is a future device-validation criterion, not a design claim.

### 7.1 Persistence and capture ownership schema

| Object / field | Required value and owner | Success assertion | Error / recovery assertion |
|---|---|---|---|
| `MeasurementRecord.recordId` | Room-generated stable id | positive id in `recordWrite.success`; record appears after process restart | DB error leaves `recordDraft` intact; no success receipt; Retry repeats same intent |
| `name/mode/rawMeters/rawSquareMeters/displayUnit/completedAt` | current completed session snapshot | exact fields persist offline; unit never rewrites raw meters | invalid/missing raw value fails closed; draft remains editable |
| `worldPoseRestored` | constant `false`; world pose/point geometry are absent from schema | history renders “记录不恢复空间位置” | no Restore action or anchor/entity creation from a record |
| `CaptureQueueItem.requestId/sessionId/returnState` | current measurement session owns request | pending receipt names request; completion routes to exact `returnState` | permission/queue/media failure keeps geometry and offers Retry/Dismiss |
| `session.screenshotUri` | nullable `content://` URI belonging only to the current session until record write | preview renders a real clickable link `<a data-preview-id="record-shot-link" href="{session.screenshotUri}">打开截图</a>`; open succeeds; later record owns the same URI | null=`无截图`; malformed/foreign-session URI is rejected and never attached |
| `MeasurementRecord.screenshotUri` | copied only from its creating session after successful Room write | history thumbnail/link resolves the same current-session URI | rename does not move URI ownership; delete removes only local record association per storage policy |

`receipt.operation` distinguishes `recordWrite`, `captureQueue`, and `mediaWrite`; success/error copy and retry action are operation-specific. A later request queues behind the visible receipt and cannot overwrite current ownership.

| Formatting rule | Input | Output | fallback | states |
|---|---|---|---|---|
| length cm | raw meters | `125.4 cm` (1 decimal) | `—` | fresh/complete; stale prefix |
| length m | raw meters | `1.254 m` (3 decimals) | `—` | same |
| length in | raw meters | `49.37 in` (2 decimals) | `—` | same |
| area | raw m² + unit | `1.25 m²` / `12500 cm²` / `1937.50 in²` | `—` | valid fit only |
| total path | segment meters | `总长 3.42 m` | hide until ≥2 points | active/complete |
| freshness | age ms | `<1秒前` or seconds | `状态未知` | fresh/aging/stale |
| record time | completedAt | localized `8月15日 22:35` | `时间未知` | stored |
| missing screenshot | null URI | `无截图` | same | stored |
| permission | denied enum | `需要空间感知权限` | Settings action | denied |

## 8. PICO Platform Numeric Spec

- Corner radius: 12/20/32dp project tiers; window focus panels use 32dp.
- Minimum body: 12sp; default body 15sp; measurement 18sp.
- Interaction hit target: 56×56dp minimum.
- Clear zones: core 65°×40°; secondary no larger than 85°×55°.

## 9. Asset Delivery

| Asset | Format/spec | Budget/scale | Use / owner |
|---|---|---|---|
| action/status icons | tintable SVG, 28dp visual box, 2dp stroke | 20/28/36dp tiers | Hub/Control/Dialog/Receipt |
| endpoint sphere | procedural/engine primitive | 8mm diameter, ≤96 triangles | SpatialRuler |
| ruler/ticks/grid | procedural line/quad primitives | segment/tick low-poly; active grid ≤24×24 cells | SpatialRuler/Readiness |
| hub ruler token | glTF or primitives | ≤1,000 triangles, 1m metric origin centered; one LOD ≤300 triangles | HubWorkspace decorative preview |
| screenshot thumbnail | URI-decoded image with opaque backing | max 512px preview; source retained by system | Hub history |
| motion assets | none baked; parameter timeline MO-01–08 | durations in interaction §13 | all |
| audio | optional mono UI cues, 48kHz, non-spatial for window commands; spatial click at committed point only | ≤120ms, respects system mute | commit/save/error |
| environment | none | passthrough is system-provided | Stage Mixed |

Names: `action_undo_outline.svg`, `status_stale_dashed.svg`, `ruler_endpoint.mesh`, etc. Colors are runtime tokens, not baked. Actual PICO import formats remain implementation-owned.

## 10. Minimum Completeness Gate

| Check Item | Evidence | Verdict |
|---|---|---|
| Visual direction | §2 V1–V3; §2.1 comparison; two rejection reasons; DER approval | pass |
| Visual language | §3–§4 structured tokens, typography, semantics, materials, scale | pass |
| Window structure | §5.0 two shells, ASCII/Grid, mapping and reflow | pass |
| Component structure | §5.1–§5.7 seven complete blocks; §5.8 checklist | pass |
| Coverage reconciliation | §5.9 A/B/C itemized with no gaps | pass |
| Semantics and trust | §6–§8 materials/depth/data/trust/platform numbers | pass |

| Field | Value |
|---|---|
| minimumCompletenessGate | pass |

## 11. Delivery and Recipients

- Current deliverable: complete visual/design-system contract r4.
- Recipient: independent design coherence review, then preview engineer. No runtime/device evidence is claimed.
