# 实景空间标尺

<img src="app/src/main/res/mipmap-xxxhdpi/ic_spatial_ruler_v2.png" width="128" alt="实景空间标尺应用图标">

基于 PICO Spatial SDK 的实景空间测量工具，包名 `com.spatialapps.spatialruler`。应用在 Shared Space 中提供历史与模式中心，测量时进入 Mixed Stage，通过 Plane Detection、Spatial Mesh 和 HandTracking 放置贴合现实表面的低面数 ECS 标尺。

## 功能

- 直线、连续折线、四点面积、垂直高度测量
- 厘米、米、英寸切换
- 10cm 短刻度、50cm 长刻度与数字标签
- 食指射线 + 捏合标记、长捏合完成、挥手撤销、张掌清除
- Room 历史记录、命名、改名、删除
- MediaStore 测量快照保存
- SpatialUI 控制面板，透明低面数 ECS 几何与平面网格

## 技术栈

- Kotlin 2.1.20、Android、Jetpack Compose
- PICO Spatial SDK 6.0.0、SpatialUI、Spatial ECS
- Plane Detection、Spatial Mesh、HandTracking
- Room、MediaStore

## 项目结构

- `app/src/main/java/.../spatial`：平面追踪、手势输入与三维标尺渲染
- `app/src/main/java/.../domain`：距离、面积、高度与刻度计算
- `app/src/main/java/.../data`：Room 测量历史和截图仓库
- `app/src/main/java/.../ui`：Shared Space 中心与测量控制面板
- `app/src/test`：测量计算、射线和平面交互回归测试

## 构建与安装

```powershell
.\gradlew.bat testDebugUnitTest assembleDebug
pico-cli app install app\build\outputs\apk\debug\app-debug.apk --device <DEVICE_ID> --grant-permissions
pico-cli app launch com.spatialapps.spatialruler --activity .platform.LaunchActivity --device <DEVICE_ID>
```

真机必须已经完成房间扫描，且系统手势追踪可用。Plane/Mesh/Hand 数据仅在 Full Space 状态提供，因此应用把管理中心留在 Shared Space，把测量放在 Mixed Stage。

## 截图说明

应用内“截图”保存的是包含当前标记点、线段、模式与结果的测量快照。PICO OS 当前拒绝 ADB 读取空间合成层，因此它不包含透视相机画面；系统侧 `pico-cli capture screenshot` 和 `capture record` 的失败证据记录在设备验证报告中。
