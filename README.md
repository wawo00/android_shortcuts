# android_shortcuts
长按弹出快捷方式

## 项目说明

这是一个 Android Demo 应用，演示了如何实现类似支付宝的桌面长按快捷方式功能。

### 功能特性

- **桌面快捷方式**: 长按应用图标显示三个快捷选项
  - 收款码 (Receive Code)
  - 付款码 (Pay Code)
  - 其他 (Other)
- **付款码页面**: 点击"付款码"会打开 ShowPayCodeActivity
  - 包含一个 ImageView
  - 自动从 https://picsum.photos/200/300 加载图片
  - 显示加载进度条
  
### 技术栈

- **Android Gradle Plugin (AGP)**: 8.3.2
- **Gradle**: 8.5
- **语言**: Kotlin
- **UI绑定**: ViewBinding
- **图片加载**: Glide 4.16.0
- **最低SDK**: 25 (Android 7.1)
- **目标SDK**: 34 (Android 14)

### 项目结构

```
app/
├── src/main/
│   ├── AndroidManifest.xml          # 应用清单，包含快捷方式配置
│   ├── java/com/example/androidshortcuts/
│   │   ├── MainActivity.kt          # 主启动Activity
│   │   ├── ShowPayCodeActivity.kt   # 付款码Activity (使用Glide加载图片)
│   │   ├── ShowReceiveCodeActivity.kt  # 收款码Activity
│   │   └── OtherActivity.kt         # 其他功能Activity
│   └── res/
│       ├── layout/                  # 布局文件
│       ├── xml/shortcuts.xml        # 快捷方式定义
│       └── drawable/                # 图标资源
```

### 构建和运行

1. 克隆项目
```bash
git clone https://github.com/wawo00/android_shortcuts.git
cd android_shortcuts
```

2. 使用 Android Studio 打开项目

3. 等待 Gradle 同步完成

4. 运行应用到设备或模拟器

5. 长按应用图标查看快捷方式

### 快捷方式使用

1. 安装应用后，长按桌面图标
2. 会弹出三个选项：收款码、付款码、其他
3. 点击"付款码"将直接打开付款码页面，并自动加载显示图片

### 关键实现

#### 1. AndroidManifest.xml 配置
```xml
<meta-data
    android:name="android.app.shortcuts"
    android:resource="@xml/shortcuts" />
```

#### 2. shortcuts.xml 定义
定义了三个静态快捷方式，每个快捷方式指向不同的Activity。

#### 3. ShowPayCodeActivity 图片加载
使用 Glide 加载网络图片，配置了：
- 禁用磁盘缓存（每次获取新图片）
- 禁用内存缓存
- 加载监听器（显示/隐藏进度条）

### 权限说明

应用需要以下权限：
- `INTERNET`: 用于从网络加载图片

### 依赖库

- AndroidX Core KTX
- AndroidX AppCompat
- Material Components
- ConstraintLayout
- Glide (图片加载)

### License

MIT License

