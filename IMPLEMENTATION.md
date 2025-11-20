# 实现细节 (Implementation Details)

## 项目架构

### 文件结构
```
android_shortcuts/
├── app/
│   ├── build.gradle.kts              # 应用级构建配置
│   ├── proguard-rules.pro            # ProGuard混淆规则
│   └── src/main/
│       ├── AndroidManifest.xml       # 应用清单
│       ├── java/com/example/androidshortcuts/
│       │   ├── MainActivity.kt
│       │   ├── ShowPayCodeActivity.kt
│       │   ├── ShowReceiveCodeActivity.kt
│       │   └── OtherActivity.kt
│       └── res/
│           ├── drawable/             # 矢量图标
│           │   ├── ic_launcher_background.xml
│           │   ├── ic_launcher_foreground.xml
│           │   ├── ic_pay.xml
│           │   ├── ic_receive.xml
│           │   └── ic_other.xml
│           ├── layout/               # 布局文件
│           │   ├── activity_main.xml
│           │   ├── activity_show_pay_code.xml
│           │   ├── activity_show_receive_code.xml
│           │   └── activity_other.xml
│           ├── mipmap-*/             # 启动图标（多种分辨率）
│           ├── values/
│           │   ├── colors.xml
│           │   ├── strings.xml
│           │   └── themes.xml
│           └── xml/
│               └── shortcuts.xml     # 快捷方式定义
├── build.gradle.kts                  # 项目级构建配置
├── gradle.properties                 # Gradle属性配置
├── settings.gradle.kts               # 项目设置
└── gradlew / gradlew.bat            # Gradle包装器脚本
```

## 核心功能实现

### 1. 快捷方式配置 (Shortcuts Configuration)

#### shortcuts.xml
定义了三个静态应用快捷方式：

```xml
<shortcuts xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 收款码快捷方式 -->
    <shortcut android:shortcutId="receive_code"
              android:icon="@drawable/ic_receive"
              android:shortcutShortLabel="@string/shortcut_receive_short">
        <intent android:targetClass="...ShowReceiveCodeActivity" />
    </shortcut>
    
    <!-- 付款码快捷方式 -->
    <shortcut android:shortcutId="pay_code"
              android:icon="@drawable/ic_pay"
              android:shortcutShortLabel="@string/shortcut_pay_short">
        <intent android:targetClass="...ShowPayCodeActivity" />
    </shortcut>
    
    <!-- 其他功能快捷方式 -->
    <shortcut android:shortcutId="other"
              android:icon="@drawable/ic_other"
              android:shortcutShortLabel="@string/shortcut_other_short">
        <intent android:targetClass="...OtherActivity" />
    </shortcut>
</shortcuts>
```

#### AndroidManifest.xml
在MainActivity中注册快捷方式：

```xml
<activity android:name=".MainActivity" android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
    
    <!-- 关键配置：关联shortcuts.xml -->
    <meta-data
        android:name="android.app.shortcuts"
        android:resource="@xml/shortcuts" />
</activity>
```

### 2. ShowPayCodeActivity 实现

这是最重要的Activity，实现了图片加载功能。

#### 关键特性：
- 使用ViewBinding进行视图绑定
- 使用Glide加载网络图片
- 显示加载进度
- 禁用缓存以获取新图片

#### 代码实现：
```kotlin
class ShowPayCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowPayCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPayCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPayCodeImage()
    }

    private fun loadPayCodeImage() {
        binding.progressBar.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://picsum.photos/200/300")
            .diskCacheStrategy(DiskCacheStrategy.NONE)  // 禁用磁盘缓存
            .skipMemoryCache(true)                       // 禁用内存缓存
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(...): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
                override fun onResourceReady(...): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.ivPayCode)
    }
}
```

### 3. ViewBinding 配置

在 `app/build.gradle.kts` 中启用：

```kotlin
android {
    buildFeatures {
        viewBinding = true
    }
}
```

每个Activity都使用ViewBinding：
```kotlin
private lateinit var binding: ActivityShowPayCodeBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShowPayCodeBinding.inflate(layoutInflater)
    setContentView(binding.root)
}
```

### 4. 布局设计

#### activity_show_pay_code.xml
使用ConstraintLayout实现响应式布局：

```xml
<androidx.constraintlayout.widget.ConstraintLayout>
    <!-- 标题 -->
    <TextView android:id="@+id/tvTitle"
              android:text="@string/pay_code_title"
              android:textSize="24sp" />
    
    <!-- 图片视图：宽度为父布局80%，保持1:1比例 -->
    <ImageView android:id="@+id/ivPayCode"
               app:layout_constraintWidth_percent="0.8"
               app:layout_constraintDimensionRatio="1:1" />
    
    <!-- 加载进度条：居中显示在图片上 -->
    <ProgressBar android:id="@+id/progressBar"
                 android:visibility="visible" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

## 依赖配置

### app/build.gradle.kts

```kotlin
dependencies {
    // AndroidX核心库
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // Glide图片加载库
    implementation("com.github.bumptech.glide:glide:4.16.0")
    
    // 测试依赖
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

### build.gradle.kts (项目级)

```kotlin
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.3.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}
```

## 使用流程

### 1. 安装应用
```bash
./gradlew installDebug
```

### 2. 测试快捷方式

1. 在设备桌面找到应用图标
2. **长按图标** 2-3秒
3. 会弹出快捷菜单，显示三个选项：
   - 📱 收款码
   - 💳 付款码
   - ⚙️ 其他

### 3. 测试付款码功能

1. 点击"付款码"快捷方式
2. 应用会直接打开ShowPayCodeActivity
3. 会看到：
   - 顶部显示"付款码"标题
   - 中间显示加载进度条
   - 几秒后图片从 https://picsum.photos/200/300 加载完成
   - 每次打开都会获取不同的随机图片

## 技术要点

### Android Shortcuts API
- **最低版本要求**: API 25 (Android 7.1)
- **静态快捷方式**: 在XML中定义，最多5个
- **动态快捷方式**: 可通过代码动态添加/删除
- 本项目使用静态快捷方式

### Glide 配置
- **DiskCacheStrategy.NONE**: 不缓存到磁盘
- **skipMemoryCache(true)**: 不缓存到内存
- **RequestListener**: 监听加载状态
- 适合需要每次获取新内容的场景

### ViewBinding 优势
- 类型安全
- 避免findViewById
- 编译时检查
- 自动生成绑定类

## 常见问题

### Q: 为什么长按图标没有显示快捷方式？
A: 确保：
1. 设备运行Android 7.1+
2. 使用的Launcher支持快捷方式
3. AndroidManifest.xml中正确配置了meta-data

### Q: 图片加载失败怎么办？
A: 检查：
1. AndroidManifest.xml中有INTERNET权限
2. 设备连接到互联网
3. 图片URL可访问

### Q: 如何修改图片URL？
A: 编辑 `ShowPayCodeActivity.kt`，修改load()方法中的URL：
```kotlin
.load("https://your-image-url.com/image.jpg")
```

## 扩展建议

### 1. 添加动态快捷方式
```kotlin
val shortcutManager = getSystemService(ShortcutManager::class.java)
val shortcut = ShortcutInfo.Builder(this, "dynamic_id")
    .setShortLabel("动态快捷方式")
    .setIntent(Intent(this, TargetActivity::class.java))
    .build()
shortcutManager.addDynamicShortcuts(listOf(shortcut))
```

### 2. 添加本地图片处理
可以使用Glide的transform功能：
```kotlin
.transform(CircleCrop())  // 圆形裁剪
.transform(RoundedCorners(20))  // 圆角
```

### 3. 添加错误处理UI
在加载失败时显示错误提示：
```kotlin
override fun onLoadFailed(...): Boolean {
    binding.progressBar.visibility = View.GONE
    Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
    return false
}
```

## License
MIT License
