## 1、描述
**`StatusBarUtils`是Android状态栏工具类，支持设置状态栏颜色、设置状态栏沉浸式、状态栏字符颜色深色切换；并对`小米`、`魅族`、`OPPO`等机型进行适配。**

## 2、方法
|方法名|描述|机型支持
|:---:|:---:|:---:|
| setStatusBarColor | 设置状态栏颜色|支持4.4以上的所有机型
| setStatusBarColorLight |  设置状态栏颜色,并且状态栏字符颜色变为深|支持6.0以上的普通机型<br>支持5.1以上的oppo机型<br>支持4.4以上的小米机型<br>支持4.4以上的魅族机型
| setStatusBarTransparen |  设置状态栏沉浸式|支持4.4以上的所有机型
| setStatusBarTransparenLight |  设置状态栏沉浸式,并且状态栏字符颜色变为深|支持6.0以上的普通机型<br>支持5.1以上的oppo机型<br>支持4.4以上的小米机型<br>支持4.4以上的魅族机型
| setStatusBarLightStatus |  设置状态栏字符颜色状态|支持6.0以上的普通机型<br>支持5.1以上的oppo机型<br>支持所有小米机型<br>支持所有魅族机型
| getStatusBarColor |  获取状态栏颜色|5.0以上所有机型可以正确获取,以下默认为黑色
| getStatusBarHeight | 获取状态栏高度|支持所有机型

## 3、gradle
add the dependency:
```gradle
dependencies {
    ...
    
    implementation 'com.github.xubo.statusbarutils:StatusBarUtils:1.0.0'
}
```









