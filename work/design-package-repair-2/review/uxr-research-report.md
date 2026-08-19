# User Research Report · 实景空间标尺

> Source identity: `spatial-ruler-design-run-3` | Active artifact revision: **1** | Source: PM r1, user requirement, official rules, and bounded prior evidence observations re-evaluated in this run.

> Role: `research_analyst` | Active revision: 1 | Workflow stage: `research`
> Upstream: `pm-requirement-spec.md` r1, 用户原始材料, `knowledge/official-rules.json` v2.2.0 | Observation date: 2026-08-16

## 1. Direct Description of Outputs

本文档产出五类证据、三个竞品/邻近产品的多维对比、域模型、事实与缺口分离的 Persona/Journey。外部产品只被用来识别需求基线与反模式，不作为布局、状态图、组件或视觉复用来源。

## 2. Research Goals and Questions

- **Assumptions to be validated**：PM A1–A8，尤其是 Shared Space 下平面/网格可用性、张掌清除的误触风险、地面基准可用性、<2 cm 目标的环境边界。
- **Research methods performed**：用户需求案头分析；PICO 官方开发资源与本技能 SSOT 规则查询；Apple Measure、AR Ruler App、magicplan 的第一方产品文档案头对比；AR 测量误差研究摘要核对。
- **Methods not performed / explicit gap**：未做访谈、可用性测试、PICO 真机实测或竞品上手录屏。
- **Planned sample**：设计前可用性样本建议 12 人：6 名家居/DIY 用户、4 名现场勘测人员、2 名低视力/轻度运动受限用户；XR 新手/熟练者各半。该数量是测试计划，不是已收集样本。

## 3. Five Categories of Research Evidence

| ID | Category | Evidence / Gap (claim) | Source | Type | Scope | Confidence | Observation Time | Validation Plan |
|---|---|---|---|---|---|---|---|---|
| E-M1 | market | Apple 的第一方指南证明两点长度、矩形/面积、高度与照片；AR Ruler 的 iOS App Store 页面只用于证明页面明确列出的长度/距离/角度、面积/周长、多点路径、高度和体积任务。其单位能力、Android 范围、操作路径、视觉密度及身体/深度价值均未被本运行证实 | Apple Measure 官方指南；AR Ruler App iOS App Store first-party listing | official + explicit gaps | iOS 同任务邻近产品；其余维度=gap | high for exact listed functions / low for gaps | accessed 2026-08-16 | 仅以已核验功能检查任务覆盖；单位来自用户需求而非竞品；交互/视觉/空间能力必须另做实机核验 |
| E-M2 | market | magicplan 官方文档将房间扫描绑定到 project/floor 流程，并支持在平面/对象上附加注释或图片、连接 Bluetooth 激光/数字卷尺和自定义导出 | magicplan Help Center exact pages §3B S-M3a/S-M3b/S-M3c | official | 房屋勘测/地面图邻近工作流 | high | accessed 2026-08-15 | 使用户完成测量后命名和找回任务，观察是否需要更重的项目结构 |
| E-U1 | user | 用户明确需要食指放点、捏合完成、挥手撤销、张掌清除及右下悬浮控制面板 | 用户原始需求 | user_supplied | 本产品 | high | 2026-08-15 | 在独立可用性测试中测误触率、识别率、学习时间 |
| E-U2 | user | 目标用户的年龄、身高、主利手、XR 经验、无障碍需求与实际会话时长尚未知 | none | assumption (gap) | 目标用户群 | low | 2026-08-15 | 按计划样本招募并记录人口学、能力和任务日志 |
| E-D1 | domain | Apple 建议在定义良好、0.5–3 m 距离的物体上测量，并明示“测量为近似值”；说明量测结果需要范围与不确定性提示 | Apple Support: Measure dimensions with iPhone | official | iPhone Measure，作为域风险参照 | high | accessed 2026-08-15 | PICO 真机按距离、材质、光照分层测试并生成可用范围 |
| E-D2 | domain | AR 平面测量可用于估算，但所查学术摘要不支持将其默认等同手工精度 | Linköping University, Department of Computer and Information Science, thesis on AR measurement of flat-object surface area using ARKit 1.5 vertical plane detection; stable PDF URL in §3B S-D2; author/year not visible in indexed excerpt, retained as gap | external | 平面表面面积 AR 测量 | medium | PDF accessed 2026-08-15 | 获取完整书目页；目标硬件上和校准卷尺/激光测距仪对比，报告 MAE/P95 |
| E-P1 | platform | PICO Spatial SDK 的 Sense Pack 提供空间锚、空间网格与平面检测；Tracking Pack 提供 HMD/控制器/手部跟踪 | PICO Developer, “Project structure and dependency configuration”, dependency module table; §3B S-P1 | official | Spatial SDK v0.13.x resource family | high | accessed 2026-08-15 | 下游用实际 BOM/API 版本做编译与 capability query |
| E-P2 | platform | PICO 官方资源页声明 Spatial SDK 支持 Shared Space 与 Full Space，以 Kotlin/Android 构建，且官方模拟器可做 PC 端快速测试 | PICO Developer resources, “Build native spatial apps with PICO Spatial SDK”; §3B S-P2 | official | PICO Spatial SDK resources, v0.13.x listing observed | high | accessed 2026-08-15 | 环境检查后使用当前安装版本生成项目 |
| E-P3 | platform | 技能 SSOT 明确 Shared Space 只能包含 Planar/Volumetric，含 Stage 意味着已进入 Full Space | `knowledge/official-rules.json` PICO-SPACESTATE-001/002 | official/project SSOT | 本设计工作流 | high | v2.2.0, read 2026-08-15 | Stage 9 仅阻断“被放在 Shared Space 内”、“缺少明确进入价值/动作”或“缺少稳定返回”的 Stage；允许冻结的 Full Space Stage Mixed 测量 |
| E-P4 | platform | 空间锚定文档 `Limitations` 明示持久 Spatial Anchor 仅 Full Space/Stage 可用；当前设计因此仅在 Full Space Stage Mixed 会话中贴合，历史不恢复世界姿态 | PICO Developer, “Spatial anchor”, section `Limitations`; §3B S-P4 | official | Spatial SDK page retrieved 2026-08-15; page version not printed in crawler extract | high | accessed 2026-08-15 | 下游记录实际 SDK 版本并重查限制 |
| E-P5 | platform | **历史证据**：Spatial Mesh API 0.11.7 `MeshAnchor` 标注 `@RequiredFullSpace`；当前架构不再由此旧版推断决定，而由 E-P6 的本地 0.13 证据决定 | PICO Spatial API 0.11.7, package `com.pico.spatial.sense.mesh`, type `MeshAnchor`; §3B S-P5 | official | API 0.11.7 historical reference | high for 0.11.7; superseded for current architecture by E-P6 | accessed 2026-08-15 | 仅作版本演化参考；当前 0.13 实现以 E-P6 和构建验证为准 |
| E-P6 | platform | 官方本地 Spatial SDK 0.13 API reference 明示 `PlaneAnchor`、`MeshAnchor`、`HandTrackingData`、`HandTrackingProvider` 有 `RequiredFullSpace`；PlaneTrackingManager/MeshTrackingManager 本身未见该注解。**架构推断**：返回/订阅 RequiredFullSpace anchor 的 manager workflow 应在 Full Space 运行 | local official API references §3B S-P6a–S-P6c | official fact + explicitly labeled inference | installed Spatial SDK 0.13 reference on current host | high for annotations; medium for workflow inference pending build | verified 2026-08-15 | 下游编译与 capability query 复核；架构冻结为 Shared Space Volumetric 入口 + explicit Full Space Stage Mixed 测量 |
| E-S1 | safety | 当平面置信不足、跟踪丢失或标点超出平面时，结果不得伪装为精确值；数据信任需包含新鲜/降级/不可用状态 | `official-rules.json` PICO-DATA-001/002 + Apple approximate warning | official/project SSOT | 实时平面/跟踪数据 | high | read 2026-08-15 | 触发丢跟、平面更新和法线突变样例，检查可见降级结果 |
| E-S2 | safety | 必须提供 Reduce Motion、控制器回退、文字缩放、稳定退出；禁止自动移动虚拟相机与持续闪烁 | `official-rules.json` PICO-ACCESS-001–004, PICO-MOTION-001/002 | official/project SSOT | 本设计工作流 | high | v2.2.0, read 2026-08-15 | 设计系统与预览逐项映射，真机后续验证 |
| E-S3 | safety | 挥手撤销和张掌清除的真实误触率未知；不应将张掌直接执行破坏性清除 | none | assumption (gap) | 手势交互 | low | 2026-08-15 | 保留显式 Dialog；真机每人≥30 次日常动作与命令动作测试 |

- **Boundary of market evidence usage**：市场证据仅构成功能覆盖、安全风险和差异化机会，不产生 UI 结构事实。
- **Handling source conflicts**：用户的“Shared Space + Spatial Mesh + 手势测量”与本地 0.13 API 的 `RequiredFullSpace` 直接冲突。冻结边界为：Shared Space Volumetric 只承载入口/模式预备/历史；用户显式进入 Full Space / Stage Mixed 后才启用 Plane/Mesh/hand 并完成测量；关闭 Stage 稳定回到 Shared Space。历史只存数值记录/截图，不恢复世界姿态。

## 3A. Competitive Benchmark

| # | Competitor / Platform | Feature needs | Interaction experience | Visual experience (observation only) | Spatial-capability usage | Source / type / time |
|---:|---|---|---|---|---|---|
| C1 | Apple Measure / iPhone, iPad | 手动两点；自动矩形与面积；直边导向；刻度视图；照片。官方说明为近似测量 | 观察：中心准星对准后点击 Add，移动设备再点击。**中信心推断**：手机屏幕与实物视野分离；学习成本未测 | 相机画面上低密度线/数字，中心准星明确；不作为本项目视觉来源 | 利用平面、尺度、距离，但是屏幕中介的 2D 窗口，无手势射线或头显共位标尺 | Apple Support, official, accessed 2026-08-15: https://support.apple.com/en-euro/guide/iphone/iphd8ac2cfea/ios |
| C2 | AR Ruler App / iOS（Android 未核验） | **仅有第一方页面明列并由本运行采用为证据的功能**：长度/距离/角度、面积/周长、多点路径、高度、体积。单位能力、房间扫描细节与 Android 差异均不作为事实 | **Evidence gap / assumption, low**：页面未证明工具切换、点按路径、学习成本或错误恢复；须实机录屏核验 | **Evidence gap / assumption, low**：页面未证明信息密度、层级、可读性或控件结构；须逐屏标注/实机观察 | **Evidence gap / assumption, low**：页面未证明身体参与、深度利用或头显共位价值；不得由功能名推断空间实现 | App Store first-party listing, accessed 2026-08-16: https://apps.apple.com/us/app/ar-ruler-digital-tape-measure/id1326773975；interaction/visual/spatial source=none |
| C3 | magicplan / iOS-centered scanning | 房间周边扫描；角点/墙面模式；层高；门窗；撤销；现场照片；项目、注释、导出及外部测距仪校正 | 观察：先校准，保持站位，按墙/角引导扫描，流程比两点放置更长。**中信心推断**：对两点快量可能过重，待同任务时长比较 | 观察：扫描网格、墙面高亮和项目化结果；不复用其屏幕布局 | 利用 AR/LiDAR 识别墙面、角点与房间结构；主任务为整体空间建模 | magicplan official sources: §3B S-M3a (scan/photos), S-M3b (project/notes/external measure), S-M3c (export); accessed 2026-08-15 |

**Per-product absorb / avoid distillation**

| # | Strengths worth absorbing (needs/opportunity only) | Weaknesses / anti-patterns to avoid |
|---:|---|---|
| C1 | 两点快速路径、矩形自动化、边缘导向、测量近似性诚实告知 | 让用户为对齐反复移动屏幕；数值脱离实物观察 |
| C2 | 仅吸收 App Store 已核验的功能覆盖检查：长度/距离/角度、面积/周长、多点路径、高度、体积 | 不吸收或声称“多单位”；不从 interaction/visual/spatial gaps 推导工具盘、层级或空间反模式；不把营销精度用语当可信证据 |
| C3 | 扫描质量引导、错点撤销、照片留档、项目/房间语义、外部高精度工具校正思维 | 用完整房间建模流程压迫简单的两点快量；对特定硬件的能力不加区分 |

- **Our differentiation opportunities**：将“相机画面里的线”变成“戴显者可绕观、与表面共位、在任务上下文中保留的标尺”；以射线预览点+平面质量提示+可撤销的精细放点，取代为对齐而反复移动手机；以“快量为主、保存为后”的流程避免专业房间建模过重；对精度、平面状态与持久能力保持可见边界。
- **Sample and gap notes**：样本数=3，满足门槛；均为手机/平板邻近产品，非 PICO Shared Space 直接竞品。未做真机并列实验，所以不对数值精度、延迟、学习成本做竞品排名。
- **Absorption-boundary declaration**：仅吸收需求和机会，不复用任何竞品布局、状态图、组件组合或视觉。

## 3B. Reproducible Source Register

| ID | Canonical source | Version / exact evidence locus | Retrieved | Stability note |
|---|---|---|---|---|
| S-D2 | https://www.diva-portal.org/smash/get/diva2%3A1222105/FULLTEXT01.pdf | PDF abstract: feasibility/precision of measuring flat real-world surface area using ARKit 1.5 vertical plane detection; complete author/year metadata not present in indexed excerpt | 2026-08-15 | bibliographic gap retained; claim scoped to abstract only |
| S-P1 | https://developer.picoxr.com/document/spatial-sdk/project-structure-and-dependency-configuration/ | Section `Dependency configuration` → module table: Sense Pack / Tracking Pack; resource family observed with v0.13.x page | 2026-08-15 | downstream must record installed BOM exact version |
| S-P2 | https://developer.picoxr.com/resources/ | `Build native spatial apps with PICO Spatial SDK` and v0.13.x resource listing | 2026-08-15 | product-resource page is time-sensitive |
| S-P4 | https://developer.picoxr.com/document/spatial-sdk/spatial-anchor/ | Section `Limitations`: anchors can only be used in Full Space / Stage | 2026-08-15 | page extract did not expose semantic version; recheck exact installed SDK |
| S-P5 | https://developer.picoxr.com/spatial-api/0.11.7/sensepack/sense/com.pico.spatial.sense.mesh/index.html | API 0.11.7 → `MeshAnchor` → `@RequiredFullSpace` | 2026-08-15 | fact is valid for 0.11.7; 0.13.x compatibility is explicitly unknown |
| S-P6a | `C:/Users/Administrator/AppData/Local/PICO/sdk/0.13/agent-vault/api-reference/com.pico.spatial.sense.plane.md` | `PlaneAnchor` near line 14 → `RequiredFullSpace`; `PlaneTrackingManager` near line 180 has no such annotation | 2026-08-15 | local official 0.13 API snapshot; anchor annotation and manager declaration kept separate |
| S-P6b | `C:/Users/Administrator/AppData/Local/PICO/sdk/0.13/agent-vault/api-reference/com.pico.spatial.sense.mesh.md` | `MeshAnchor` near line 13 → `RequiredFullSpace`; `MeshTrackingManager` near line 125 has no such annotation | 2026-08-15 | local official 0.13 API snapshot; anchor annotation and manager declaration kept separate |
| S-P6c | `C:/Users/Administrator/AppData/Local/PICO/sdk/0.13/agent-vault/api-reference/com.pico.spatial.tracking.hand.md` | `HandTrackingData` near line 204 and `HandTrackingProvider` near line 248 → `RequiredFullSpace` | 2026-08-15 | local official 0.13 API snapshot; exact loci retained |
| S-M3a | https://help.magicplan.app/scan-a-room-in-seconds-using-lidar | project/floor entry, corner/wall scan, height, undo, photo, lighting and exit confirmation | 2026-08-15 | official workflow page |
| S-M3b | https://help.magicplan.app/about-magicplan | Bluetooth laser/digital tape connection; notes/pictures and object/project capabilities | 2026-08-15 | official product overview |
| S-M3c | https://help.magicplan.app/customize-your-exports | export customization and displayed floor/room dimensions | 2026-08-15 | official export page |

> This run re-evaluated each carried observation against its exact locus. No prior verdict or revision is active, and no market observation creates a UI fact.

## 4. Domain Model

- **Domain workflow**：（1）确认环境和安全边界 →（2）等待/引导平面检测 →（3）选模式/单位 →（4）以射线预览命中与平面质量 →（5）放置点并观察反馈 →（6）按模式继续放点/完成 →（7）审核数值和可信性 →（8）命名保存/截图或清除 →（9）查看历史。
- **Decision variables**：当前模式；所需点数；点的空间坐标与所属 planeId；平面类型/法线/范围/置信；射线命中是否稳定；跟踪年龄；点到平面的偏差；矩形拟合残差；地面基准；原始米制值与显示单位；用户是否明确完成/清除。
- **Data entities and timeliness**：`PlaneObservation` (实时，丢失即降级)；`RayHitPreview` (每帧/受节流的实时流)；`MeasurePoint` (放置后会话级稳定)；`Segment`/`Polyline`/`RectangleFit`/`HeightProjection` (点变化后立即重算)；`MeasurementSession` (会话级)；`MeasurementRecord` (本地持久)；`ScreenshotReceipt` (异步成功/失败)；`UnitPreference` (用户偏好，不改变原始米制存储)。
- **Specialized risks**：平面漂移导致错尺寸；在不同平面上强行做面积；未找到地面却输出高度；单位误读；撤销/清除误触；保存截图失败却显示成功；把估算值用于切割、电气、结构等高风险工程。
- **User mental model**：传统卷尺的“起点—尺带—终点—读数”；折线是多段长度之和；面积是长×宽；高度是相对地面的垂直差。虚拟体验必须在这些模型上说明“当前是预览、已放点还是已完成”。
- **Mature patterns**：放点前预览；平面/边缘导向；最后一步可撤销；原始值与显示单位分离；保存有明确 receipt；不确定性可见。
- **Anti-patterns**：将测量线投影到错误平面；在失去跟踪时冻结数字却不告知；单靠蓝/红表达可用性；即时执行张掌清除；为显示“空间感”增加无任务价值的悬浮面板；宣称 Web 逻辑预览等同真机精度。

## 5. Personas

### Persona P1：家居快量用户（证据级：需求推导，待访谈）

| Dimension | Content |
|---|---|
| Basic information | 成人家居/DIY 用户；XR 经验新手到中等；年龄分布未知 |
| Use scenario and frequency | 搬家、买家具、挂画、收纳规划；低频，每次 1–10 分钟是待验证假设 |
| Goals / motivations | 不去找卷尺，快速判断“能否放下/是否对齐” |
| Pain points / frustrations | 一人拉卷尺不便；边缘难对齐；手机屏幕挡住现场观察 |
| Spatial usage habits | 站立/转身；可能为看见终点而走动，需安全提醒 |
| Accessibility needs | 未知；默认支持文字缩放、形状+文字双通道、控制器回退 |
| Key quote | 用户原话：“替代传统卷尺” |

### Persona P2：现场轻勘测用户（证据级：需求+邻近产品工作流，待访谈）

| Dimension | Content |
|---|---|
| Basic information | 室内/展陈/物业/轻施工现场人员；专业度中等；XR 经验未知 |
| Use scenario and frequency | 现场记录多个墙面、家具间距或高度；中频，可连续 15–30 分钟（待验证） |
| Goals / motivations | 减少纸笔转录错误，让数值与截图关联，回到工作台后能找回 |
| Pain points / frustrations | 多个数值混淆；误差被后续放大；手套/光照/材质影响跟踪 |
| Spatial usage habits | 站立与小范围移动；可能长时间抬手，需尽量减少持续举手 |
| Accessibility needs | 可能需要手套或控制器替代；需高对比数字与语义化错误 |
| Key quote | 无可验证的访谈原话；明确证据缺口 |

**Persona claim evidence ledger (item-level)**

| Persona · claim | Evidence ID / type | Confidence | Validation plan |
|---|---|---|---|
| P1 成人家居/DIY 与搬家/挂画/收纳场景 | PM user-supplied requirement; assumption for demographics | medium for scenarios / low for demographics | 招募 5–8 名家居快量用户核对年龄、频率与任务 |
| P1 每次 1–10 分钟 | E-U1 gap / assumption | low | 任务日志记录首测、重测、命名/截图时长 |
| P1 不找卷尺与边缘对齐痛点 | 用户“替代传统卷尺” + E-U1 gap | medium / low for specific pain ranking | 半结构访谈并排序痛点 |
| P1 站立/转身与走动 | PM posture + E-U2 gap | medium / low for distribution | 目标场景观察并记录姿态占比 |
| P2 现场轻勘测角色与多对象记录 | PM record/history requirement + C3 adjacent workflow | medium | 访谈 5 名物业/展陈/轻施工人员 |
| P2 15–30 分钟、持续举手、手套影响 | E-U2 gap / assumption | low | 真机任务时长、抬手占空比、手套/控制器 A/B |
| P2 转录错误与截图关联目标 | PM record/screenshot requirement; assumption about current error rate | medium / low for error incidence | 观察现有纸笔/手机流程，记录错配率 |
| P1/P2 accessibility needs | no direct user evidence; safety/accessibility assumption | low | 包含低视力、色觉、动作限制参与者的可用性测试 |

## 6. Journey Map

| Stage | Awareness / entry | First hands-on | Core use | Completion / trust check | Exit / return |
|---|---|---|---|---|---|
| User goal | 快速开始 | 知道表面已可测 | 将点放在正确边缘 | 确信数值可用 | 保存并稳定退出 |
| User behavior | 启动、授权、选模式 | 扫视墙/桌/地，试放首点 | 持续放点、撤销、捏合完成 | 阅读结果/单位/置信，命名或截图 | 回到准备态或打开历史 |
| Touchpoint | 准备窗口 | 淡网格+射线命中预览 | 共位 3D 点/线/刻度+辅助控制 | 完成数值+保存/clear Dialog | 历史列表/稳定退出 |
| Thought | “权限为什么需要？” | “现在点下去会在哪？” | “这个点在同一个面上吗？” | “这个数真能用吗？” | “稍后能找到吗？” |
| Emotion | 😐 | 😞→😀 | 😐 | 😐/😞 | 😀 |
| Pain point | 权限和空间状态不清 | 平面迟迟未识别；首点误放 | 误触、遮挡、漂移 | 精度不确定；截图失败 | 记录命名不清/未保存 |
| Opportunity | 逐项权限解释 | 扫描指引+命中质量+允许调整 | 低遮挡、末点优先撤销、手势双通道 | 准确度/降级标签+成功 receipt | 默认名+可编辑命名+历史返回 |

**Journey evidence/confidence ledger (item-level)**

| Journey row | Evidence / confidence | Validation plan |
|---|---|---|
| User goal / behavior / touchpoint | PM core tasks + domain workflow; medium | 任务走查逐步核对进入、标点、完成、保存、退出 |
| Thought | E-U1/E-U2 explicit user-evidence gaps; low assumption | Think-aloud 访谈，逐句保留/删除 |
| Emotion | no source; low assumption, not a finding | SAM/5-point affect rating across five stages |
| Pain point | PM risks + E-D1/D2 + assumptions; medium for tracking/accuracy, low for naming emotion | 错误注入测试与任务后访谈 |
| Opportunity | design implication, not user fact; derived from above | 原型 A/B and task success measurement |

- **Emotional low point**：首个点放置前无法确认“命中的是什么面、点会落哪里”；必须先修复，否则后续精确数字也不可信。
- **Key opportunity summary**：把“放点前的置信”和“完成后的可信度”设计成一级信息；将破坏性手势改为请求，不直接执行。

## 7. Key Findings

| # | Finding | Evidence | Confidence | Design Implication |
|---:|---|---|---|---|
| F1 | 功能广度是市场基线，差异应来自戴显共位、低遮挡和信任反馈 | E-M1/M2, C1–C3 | high | 概念选择必须引用空间反事实，不做工具盘移植 |
| F2 | 当前 0.13 能力边界已解析为混合空间状态 | E-P3/E-P4/E-P6 (E-P5 仅历史证据) | high | Shared Space Volumetric 入口/历史 → 显式 Full Space Stage Mixed 测量 → 稳定返回；跨会话世界姿态不恢复 |
| F3 | 精度不是一个无条件常数 | E-D1/D2, PM A2/A8 | high | 每个测量结果携带置信/降级语义；<2 cm 必须真机实验 |
| F4 | 张掌清除需要安全门 | E-S3, PM A4 | medium | 手势只打开确认 Dialog；提供取消和稳定返回 |
| F5 | **待验证假设**：长时间抬手可能不可接受，不能作为默认前提 | E-U2 explicit gap; P2 claim ledger | low | 放点先设计为短动作并提供控制器/面板回退；用真机抬手占空比/疲劳评分验证后再升级为发现 |
| F6 | 原始数据和显示单位必须分离 | 域语义 + R6 | high | 内部米制存储，cm/m/in 仅格式化，历史切换不产生累积换算误差 |

## 8. Wearing Posture and Field-of-View Insights

- **Usual posture**：站立+小范围转身是高信心需求推导；实际分布是证据缺口。
- **Arm range of motion**：无 PICO 真机样本值；设计不要求持续举手，将交互压缩为射线对准+短促点击/捏合。
- **Central field of view**：技能 SSOT 要求核心内容在 65°×40° 清晰视场内，次要内容不超过 85°×55°；具体占角待 Stage 9 计算。
- **Fatigue threshold**：无已测 PICO 阈值。设计测试假设单次快量 1–10 min，长任务到 20 min 提示休息；不将该值宣称为官方限制。

## 9. Eye-Hand Interaction Usability

- **Gaze / hand-ray + pinch hit rate**：未测（显式 gap）。验收计划：每人 40 次标点，报告首次命中率、误触率、放点用时和边缘误差。
- **Low-load interaction assumption**：手部可在不操作时自然放下；该假设待姿态观察。
- **Mis-touch and feedback**：必须存在预览命中点、放点瞬时的尺度+形状反馈、撤销可见结果；不只用颜色。

## 10. Duration Baseline Data

| Decision Type | Duration Anchor | Source / status |
|---|---:|---|
| Plane readiness glance | target ≤1.5 s to identify ready/degraded/unavailable | project test target; evidence gap, validate with P1/P2 sample |
| Mode identification | target ≤2 s among four modes | project test target; evidence gap |
| Point placement feedback | visual response <100 ms | user acceptance target; requires device telemetry |
| Fine-tuning dwell | target ≤3 s from preview to committed point | project test target; evidence gap |
| Undo recovery | target ≤2 s from error recognition to restored state | project quality target; validate in prototype + device |
| Clear confirmation | deliberate 1–5 s, no auto-confirm | safety-derived design range; validate accidental activation |

## 11. Motion Sickness / Fatigue and Safety

- **Risk scenarios**：虚拟标尺随跟踪噪声抖动；大范围网格闪烁；数字面牌突然跳转朝向；用户为找命中而倒退；长时间举手。
- **High Motion label**：no。产品禁止自动虚拟相机移动，所有动效为局部、有界、可 Reduce Motion。
- **Recommended duration/rest cadence**：设计测试假设 20 min 后提示休息；这是本项目风险控制假设，不是 PICO 官方阈值。
- **Physical safety**：Shared Space 是默认入口/返回；测量由用户显式进入 Full Space Stage Mixed；系统 Back/完成/取消关闭 Stage 并返回 Shared Space。不引导倒退；命中不可用时显式停止放点。

## 12. Minimum Completeness Gate

| Check Item | Minimum Pass Condition | Evidence Anchor | Verdict |
|---|---|---|---|
| Five categories | market/user/domain/platform/safety each has evidence or explicit gap | §3 E-M1–E-S3 | pass |
| Competitive benchmark | ≥3 products；四维逐格均有 evidence 或显式 gap，gap 未向下游晋升为事实 | §3A C1–C3 + absorb/avoid | pass |
| Domain model | workflow, decisions, entities/timeliness, risks, mental model, anti-patterns | §4 | pass |
| User evidence | personas and journey expose source level/gaps | §5–§7 | pass |
| Quantitative and safety | durations/posture/input/safety have values, sources or explicit gaps | §8–§11 | pass |

| Field | Value |
|---|---|
| minimumCompletenessGate | pass |

## 13. Delivery and Recipients

- **Deliverables**：research evidence + domain model revision 1.
- **Recipients**：PM 用于 Stage 3 质量合约；Task/Interaction/Visual 角色只能引用需求与机会层证据。
- **Role trace**：`research_analyst` 未选择布局、视觉或最终概念。
