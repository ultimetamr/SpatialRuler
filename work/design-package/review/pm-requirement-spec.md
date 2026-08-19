# Spatial App Requirement Spec · 实景空间标尺

> Role: `product_strategist` | Active revision: 5 | Workflow stages: `intent` → `quality_contract` + bounded change requests CR-02/CR-03/CR-04 | Sources: 用户原始需求; `uxr-research-report.md` r4; local Spatial SDK 0.13 API reference verification supplied by main thread
>
> 本修订已冻结意图与质量合约；不声称已获得 PICO 运行时或设备验证。

## 1. Direct Description of Outputs

本文档承载：（1）已冻结的产品意图；（2）Stage 3 已冻结的质量合约。产品目标是让戴着头显的用户在保持对实景的观察时，直接把测量决策绑定到物理平面与空间位置。

## 2. Background and Problem

- **One-sentence requirement description**：一个基于现实空间锚定的 AR 测量工具，用食指射线标记实体表面上的点，生成低遮挡的 3D 标尺，完成直线、连续路径、矩形面积与垂直高度测量，并保存记录与截图。
- **Target users**：家居布置、DIY 与轻装修用户；搬家/收纳规划者；现场快速勘测的设计、物业、展陈与施工辅助人员。不定位为计量检定或生命安全关键的专业仪器。
- **Use scenarios**：在共享空间中面向墙面、桌面、地面、家具边缘或空间间隙，一边看现实物体一边标记；完成后快速命名、保存或截图，后续在历史记录中查看。
- **Wearing posture**：以站立、小范围转身与移动为主；也允许坐姿测量桌面。需避免诱导倒退、探身或靠近障碍物。
- **Frequency and duration**：假设为低到中频，单次 1–10 分钟；专业勘测可连续 15–30 分钟。会话时长与疲劳风险将在可用性测试中校验。
- **Preliminary judgment of spatial necessity**：核心输入是现实平面上的三维交点，核心输出是与物理表面共位的线、点、刻度与数值；普通 2D 界面无法在不中断观察的情况下给出这种空间共位反馈。

## 3. Key Moment

- **The moment a screen cannot achieve**：用户的手部射线命中墙面/桌面，两次食指点击后，一条带刻度的半透明标尺从起点生长到终点，沿检测平面法线做毫米级视觉偏移以防闪烁，数值面向用户弹跳显示；用户可同时看见实物边缘和测量结果。
- **Placement on the immersion spectrum**：默认入口、模式预备与历史位于 Shared Space 的 `WindowContainer Volumetric`；需要 Plane/Spatial Mesh/hand tracking 的实际测量通过用户显式动作进入 Full Space 的 `Stage Mixed`。该切换是本地 Spatial SDK 0.13 API `RequiredFullSpace` 限制的必要适配，不是为了制造沉浸感。
- **Entry path**：从 Shared Space Volumetric 准备窗口进入，解释测量需要的空间数据/手势权限和 Full Space 切换；用户选模式并显式点击“开始空间测量”后进入 Stage Mixed；完成/取消/系统返回关闭 Stage 并回到 Shared Space。

## 4. Product Research (baseline anchors)

| Dimension | Content | Source / Status |
|---|---|---|
| Competitor feature matrix | Apple Measure、AR Ruler App、magicplan 已覆盖两点/面积/高度/路径/截图或项目化中的多项；差异化需来自戴显共位、低遮挡与数据信任 | UXR §3A C1–C3 |
| Decision duration baseline | 平面状态识别目标 ≤1.5 s，四模式识别 ≤2 s，放点反馈 <100 ms，调整到提交 ≤3 s；前三者中只有 <100 ms 是用户给定验收值，其余为项目测试目标 | UXR §10 |
| Industry safety / comfort conventions | Reduce Motion、控制器回退、文字缩放、稳定退出；禁止自动相机运动/持续闪烁；Shared Space 不包含 Stage | UXR E-P3/E-S1/E-S2; official-rules v2.2.0 |

## 5. Intent Definition (frozen items)

- **Domain / sub-domain**：空间工具 / 实景测量与空间标注。
- **Application identity**：实景空间标尺；包名 `com.spatialapps.spatialruler`。
- **Risk level**：中等。主要风险是错误测量被用于采购/施工决策、平面丢失造成误导、使用者因追随射线而碰撞现实障碍。
- **Default space**：`Shared Space`（Volumetric 入口/历史）；核心测量为用户显式进入的 `Full Space / Stage Mixed`。关闭 Stage 必须稳定返回 Shared Space。
- **Core scenario list**：两点直线测量；多点折线与总长；矩形四角面积；从地平面到标记点的垂直高；单位切换；撤销/清除；命名保存和查看历史；截图留存。
- **Core decisions**：当前射线命中的表面是否稳定；标记点是否放置在正确平面/边缘；当前数据精度是否足以保存；当前模式是否完成；是否要保留或清除结果。
- **Data**：空间点、平面 ID/姿态/法线、网格命中信心、测量模式、线段与总长、面积/高度、单位、时间戳、用户命名、截图 URI。本地历史为持久数据；平面/手势为实时数据。
- **AI**：不需要生成式 AI 或云端推理。平面分类和手部跟踪使用平台感知能力，不将其宣称为产品自有 AI。
- **Sensors / permissions**：手部跟踪/射线、平面检测、Spatial Mesh/空间感知；本地数据存储与系统截图/媒体保存所需授权。具体权限名称和版本兼容性待下游 SDK 文档核验。
- **Collaboration**：本期不假设多人共享同一测量会话；多人/多设备同步属显式非目标。
- **Persistence boundary (frozen)**：世界姿态与贴面测量实体只在当前 Full Space 测量会话中有效。历史仅保存数值、单位、模式、名称、时间和截图 URI，**不在跨会话时恢复世界姿态或重现空间标尺**。Shared Space 兼容的持久重定位在有明确 API 证据前是 non-goal。

### 5.1 Verified Platform Facts (not assumptions)

| Fact ID | Verified fact | Exact evidence locus | Design consequence |
|---|---|---|---|
| PF-01 | Spatial SDK 0.13 `PlaneAnchor` is annotated `RequiredFullSpace`; `PlaneTrackingManager` itself is not annotated in the local reference | `C:/Users/Administrator/AppData/Local/PICO/sdk/0.13/agent-vault/api-reference/com.pico.spatial.sense.plane.md`, `PlaneAnchor` near line 14; manager near line 180 | **Architectural inference:** a workflow returning/subscribing to RequiredFullSpace plane anchors belongs in Full Space; recheck by build/capability test |
| PF-02 | Spatial SDK 0.13 `MeshAnchor` is annotated `RequiredFullSpace`; `MeshTrackingManager` itself is not annotated | `C:/Users/Administrator/AppData/Local/PICO/sdk/0.13/agent-vault/api-reference/com.pico.spatial.sense.mesh.md`, `MeshAnchor` near line 13; manager near line 125 | Same bounded inference and verification for mesh workflow |
| PF-03 | Spatial SDK 0.13 `HandTrackingData` and `HandTrackingProvider` are annotated `RequiredFullSpace` | `C:/Users/Administrator/AppData/Local/PICO/sdk/0.13/agent-vault/api-reference/com.pico.spatial.tracking.hand.md`, declarations near lines 204 and 248 | Hand-ray/pinch/gesture measurement belongs in Full Space |

## 6. Assumptions List

| # | Assumption | Confidence | Impact | Validation Plan |
|---:|---|---|---|---|
| A1a | 根据 PF-01/PF-02，实际构建中的 plane/mesh manager 工作流不会在 Shared Space 暴露可用 anchor 结果 | high | 决定测量进入 Stage Mixed | 实际 0.13 BOM 编译 + Shared/Full Space capability query；行为与推断不符时发起冻结架构变更 |
| A1b | 根据 PF-03，手跟踪主输入只在 Full Space 测量会话可用 | high | Shared Space 入口不依赖食指/捏合/挥手/张掌 | 实际设备 capability query；全流程提供控制器回退 |
| A1c | 未找到 Shared Space 兼容的持久世界重定位 API | high | 历史不恢复测量几何的世界姿态 | 保持“只存数值/截图” non-goal 边界；未来有明确兼容证据才发起独立变更请求 |
| A1e | 截图/媒体保存的具体 API 和权限尚未验证 | medium | 影响截图流程，不改变测量容器架构 | 下游编译+媒体文件打开实验；失败时显示 receipt 不误报 |
| A2 | 普通室内光照与具纹理墙/桌面能产生稳定平面与网格 | medium | 直接影响 <2 cm 目标和贴面效果 | 对白墙、玻璃、低光、纹理桌面分层采样，记录命中置信与误差 |
| A3 | 用户能理解“食指点击放点、捏合完成”的区别 | medium | 误触会放大测量误差 | 新手首次任务测试，记录首个有效点和首次完成时间 |
| A4 | 挥手撤销与张开手掌清除可与日常动作可靠区分 | low | 误撤销/误清除是高损失操作 | 原型中加二次意图判定，真机通过误触率测试决定是否保留；清除必须二次确认 |
| A5 | 高度模式可以检测到地平面作为垂直投影基准 | medium | 无地平面时无法给出可信高度 | 将“地面未确认”作为阻断状态；验证不同地材和台阶环境 |
| A6 | 面积模式按“同一检测平面上的矩形四角”进行，而非任意四边形 | high | 决定校正方式与错误提示 | 用户验收时确认“矩形”语义；显示拟合边与偏差提示 |
| A7 | 历史记录和截图默认仅本机保存，无云端同步 | high | 影响隐私和跨设备期待 | 下游验收检查无网络时完成所有保存/查看流程 |
| A8 | <2 cm、<100 ms、60 fps 是目标环境下的系统级验收目标，设计阶段只能定义测量方法 | high | 不得以 Web 预览宣称达标 | 下游在目标设备/模拟器分别采集精度、输入时延、帧时数据 |

## 7. Quality Contract

### 7.1 Required business / user outcomes

1. **O1 可放置**：用户只能在有效平面命中上放置点，放置前看见命中位置、平面类型与可用/降级/不可用状态。
2. **O2 四模式完成**：直线、连续、矩形面积、垂直高度各有进入、标点、撤销、完成、异常、退出流程。
3. **O3 贴合且可信**：线/点/刻度与检测平面共位，对漂移、平面丢失、异面点或地面未就绪不伪造成功值。
4. **O4 值可读可换算**：显示值面向用户，原始米制数据在 cm/m/in 切换中保持等价。
5. **O5 可恢复**：任何模式可撤销最后一点；张掌只请求清除，经 Dialog 显式确认后执行。
6. **O6 可留存**：完成结果可命名保存，历史可查看，截图成功/失败有 receipt，离线时保持本地能力。
7. **O7 安全可退**：Shared Space 为默认入口/返回；Full Space 测量只由用户显式进入，关闭 Stage 始终可达并回到 Shared Space；不诱导倒退/探身。
8. **O8 持久语义正确**：历史只复现数值记录/截图，不宣称恢复世界姿态。

### 7.2 Success / efficiency criteria

| ID | Criterion | Design-stage evidence | Downstream/device evidence |
|---|---|---|---|
| Q-E1 | 从平面 ready 到第一个有效点：P75 ≤3 s | 预览可见且流程无多余步骤 | 真机可用性计时 |
| Q-E2 | 直线测量：P75 ≤10 s（不含首次权限/扫描） | Web 逻辑路径可见 | 真机任务计时 |
| Q-E3 | 撤销恢复：P75 ≤2 s | 控制面板+手势两通道可见 | 真机误点任务 |
| Q-E4 | 清除误执行=0/60 次非目标张掌，取消保留数据=100% | Dialog 阻断高风险转换 | 真机手势误触实验 |
| Q-P1 | 1 m 范围测量误差 <2 cm | 仅定义实验与降级信息 | 标准物多材质/光照，报告 MAE/P95 |
| Q-P2 | 手势事件到首帧反馈 <100 ms | 反馈不依赖异步保存 | 设备事件-帧时间戳 |
| Q-P3 | 测量态稳定 60 fps | 低面数线/点，网格默认 5% 且可降级 | Perfetto/系统帧时真机证据 |
| Q-D1 | 命名保存成功后重启可找回；截图 receipt 不误报 | 数据状态/失败路径可见 | Room/媒体存储集成测试 |

### 7.3 Risks and must-not-fail items

- 不在平面未 ready、射线无命中、跟踪丢失、高度无地面、面积点异面时输出“已完成精确值”。
- 不将 <2 cm、<100 ms、60 fps 作为 Web 预览可验证结论。
- 不将张掌、跟踪短暂丢失或单次挥手直接转化为不可逆操作。
- 不宣称用于计量检定、结构/电气/切割等安全关键工程。
- 不依赖颜色单通道；降级/错误必须有形状/文字标签。

### 7.4 Default visible primary-window orientation

- 默认 Shared Space 只显示 **1 个 Volumetric WindowContainer**，承载准备、模式选择与历史。
- Full Space 测量时只存在 **1 个 Stage Mixed** 主测量上下文；关闭它立即返回 Shared Space 入口，两者不同时作为并列主窗口。
- 右下高频命令是否使用 Toolbar/InlineControl/None，必须由 Stage 9 附着决策矩阵确定，本合约不默认添加 Toolbar。

### 7.5 Domain-specialized component orientation

核心组件必须覆盖：平面质量/命中预览；标尺线+端点+分级刻度；面向用户的数值标签；四模式进度；单位选择；撤销/清除/截图；保存命名；历史项；可信度/错误。每个核心组件必须有完整八段结构。

### 7.6 Real-time data trust orientation

- 平面/射线数据状态为 `fresh / degraded / unavailable`，由人可读标签+形状+颜色组合表达。
- 放置后的点保留源 `planeId`、放置时间、命中距离/法线和质量状态；平面更新后显式重算或降级。
- 截图/保存为 `idle / pending / success / failure`，未获得存储 receipt 不得显示成功。
- 离线时保持所有核心测量和历史；本期无网络数据依赖。

### 7.7 PICO platform and spatial-design hard constraints

- Shared Space 部分只使用 Volumetric；测量通过显式动作切换到 Full Space / Stage Mixed，满足 Plane/Mesh/hand 的 `RequiredFullSpace`。系统 Back/完成/取消关闭 Stage 回到 Shared Space。
- Planar 尺寸须完成内容类型→scene tier→官方 1280×720dp 基线→清晰视场→可读/可点下限→default/min/max 方法链；合法范围 320×180–2700×1800dp，Planar depth=640dp，命中目标≥56×56dp，正文≥12dp。
- 必须提供 Reduce Motion、Controller Fallback、textScaling、stableExit；禁止自动虚拟相机移动、持续闪烁。
- 任何 Toolbar/TabBar/Subwindow/SpatialPopup/Augment/Sheet/Dialog/Coachmark 都必须经过附着决策矩阵并比较 None/InlineControl。
- 玻璃/Vibrant 是 PICO 系统能力；Web 预览只做语义近似，不伪装成系统材质实现。

### 7.8 Originality requirement

根据 UXR §3A “Our differentiation opportunities”：吸收 C1 的两点快速任务/近似性告知、C2 的模式覆盖、C3 的扫描质量引导/可撤销/留档机会；避免手机屏幕对齐负担、高密度工具盘、过重房间建模流程。差异必须由戴显共位、表面质量可见、低遮挡尺标与会话级空间连续性证明；不复用三个竞品的布局/状态/组件/视觉。

### 7.9 Design / readability / downstream-implementation acceptance plan

1. 六个核心 Markdown 各自通过 Minimum Completeness Gate，17 阶段 receipt 时序和修订链完整。
2. 至少 3 个实质不同概念，决策矩阵和拒绝理由完整；结构布局由任务/数据/视场推导。
3. 所有核心组件通过八段结构审查；状态/转换/异常/高风险 Dialog/Reduce Motion/响应等级均可实现。
4. `preview.html` 是单文件、可交互的 Web 逻辑验证；Coverage Manifest 与五个映射表分母一致，QA 独立重建差异=0。
5. 设备精度/时延/帧率/舒适/物理安全统一标为 `not_performed`，由下游目标设备验证。

## 8. Requirements Traceability (intent-level seed)

| Requirement | Intended design node | Validation method |
|---|---|---|
| R1 直线测量 | two-point marking + anchored ruler | 用已知 1 m 标准物多位姿比对，误差 <2 cm |
| R2 贴平面测量 | plane-aware hit + surface lock | 墙/桌/地三类平面观察无明显飘空，记录法线偏差 |
| R3 连续测量 | polyline point chain + total length | 添加≥4点，核对各段和与总长 |
| R4 面积测量 | four-corner rectangle fit | 标准矩形面积比对，异面点被阻断 |
| R5 高度测量 | floor baseline + vertical projection | 已知高度比对，无地面时显式阻断 |
| R6 cm/m/in 单位 | unit selector + consistent formatter | 切换后检查数学等价且不改变原始米制数据 |
| R7 测量记录 | naming/save/history states | 重启后记录可读，名称、数值、时间一致 |
| R8 截图保存 | screenshot action + saved receipt | 操作后可见成功/失败反馈，媒体文件可打开 |
| R9 撤销 | undo point stack | 每模式撤销最后一点并正确重算 |
| R10 清除 | palm clear request + confirmation dialog | 张掌不直接破坏数据，取消后保留现状 |
| R11 四种模式 | explicit mode selection + mode progress | 各模式均有进入、标点、完成、异常、退出路径 |
| R12 低遮挡视觉 | translucent ruler/grid + billboard label | 对复杂现实背景做可读与遮挡评估 |
| R13 性能/延迟 | low-poly geometry + event-to-render telemetry plan | 目标设备采集 60 fps 和 <100 ms，Web 仅验证状态逻辑 |

## 9. Minimum Completeness Gate

| Check Item | Minimum Pass Condition | Evidence Anchor | Verdict |
|---|---|---|---|
| Background and intent | six foundation items and frozen fields complete | §2–§5 | pass |
| Assumption governance | every unknown has confidence, impact, validation plan | §6 A1–A8 | pass |
| Quality contract | nine contract items complete after research | §7.1–§7.9 | pass |
| Requirements traceability | every mandatory requirement mapped | §8 R1–R13 | pass |

| Field | Value |
|---|---|
| minimumCompletenessGate | pass |

> CR-02 change control: 该修订根据主线程已核验的本地 0.13 API `RequiredFullSpace` 证据，将“全程 Shared Space 测量”更正为“Shared Space Volumetric 入口/历史 + 显式 Full Space Stage Mixed 测量”，并冻结不恢复跨会话世界姿态。这是能力约束修复，不是为增加沉浸感。
>
> CR-03 change control: 将 0.13 API 注解事实与 manager workflow 的架构推断分离，不再声称 PlaneTrackingManager/MeshTrackingManager 本身有 `RequiredFullSpace`。
>
> CR-04 change control: 清理质量合约“待冻结”旧叙事，与 UXR r4 的最终空间状态证据保持一致。

## 10. Delivery and Recipients

- **Current deliverable**：frozen intent + quality contract revision 5.
- **Recipients**：`evidence_integrity_reviewer`，通过后交给 Task/Interaction/Visual roles.
- **Role trace**：Stages `intent` and `quality_contract` / `product_strategist` / 未越界到布局、组件或最终视觉决策。
