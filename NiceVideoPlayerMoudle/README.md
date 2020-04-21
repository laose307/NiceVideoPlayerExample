# 图片+视频轮播插件

##配置
### 第一步
在主项目配置插件版本控制，在主项目根目录的 `build.gradle `中添加下列代码：

```

buildscript {
    ......
}

allprojects {
    ......
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

//拷贝下面这段
ext {
    android = [
            compileSdkVersion: 29,
            minSdkVersion    : 16,
            targetSdkVersion : 29,
            buildToolsVersion:"28.0.3",
            versionCode      : 1,
            versionName      : "1.0.0"
    ]
    versions = [
            filedownloader : "1.7.5",
            androidx       : "1.1.0",
            glide          : "3.6.0",
    ]
    dependencies = [
            glide                       : "com.github.bumptech.glide:glide:${versions.glide}",
            androidx                    : "androidx.appcompat:appcompat:${versions.androidx}",
            filedownloader              : "com.liulishuo.filedownloader:library:${versions.filedownloader}",
    ]
}

```

### 第二步
在创建 Application，初始化下载配置

```
    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000) // set connection timeout.
                        .readTimeout(15000) // set read timeout.
                ))
                .commit();
    }

```

### 第三步
如果是android P上运行,在AndroidManifest.xml文件里， application字段里加入下面配置

1. android:requestLegacyExternalStorage="true"
2. android:usesCleartextTraffic="true"
3. android:networkSecurityConfig="@xml/network_security_config"
4.在 res 下新建一个 xml 目录，然后创建一个名为：network_security_config.xml 文件 ，该文件内容如下：

```
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>

```
5.完整代码的案例
```
    <application
        android:name=".MyApplication"
        android:requestLegacyExternalStorage="true"
        android:allowBackup="true"
        android:networkSecurityConfig="@xml/network_security_config"
        android:usesCleartextTraffic="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"/>
```
### 第四步
在AndroidManifest.xml文件里，注册activity
```
     <activity android:name="wsl.fjsoft.com.nicevideoplayermoudle.activity.Activity_ADVideo"/>

      <activity android:name="wsl.fjsoft.com.nicevideoplayermoudle.activity.WebActivity"/>

```

