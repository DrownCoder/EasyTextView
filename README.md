基于iconfont拓展的TextView(支持左右设置Text,设置Shape,设置span等)  

![Wercker](https://img.shields.io/wercker/ci/wercker/docs.svg)
[![](https://jitpack.io/v/DrownCoder/EasyTextView.svg)](https://jitpack.io/#DrownCoder/EasyTextView)
![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)
### 项目介绍
* 还在苦于iconfont左右两边还需要设置文字只能加两个TextView？
* 还在苦于需要设置各种大同小异的边框而new Shape文件吗？
* 还在苦于设置点击效果而多种样式吗？
>一个基于iconfont拓展的支持多种功能的轻量级TextView，减少布局嵌套，减少定义shape文件，基本涵盖常规需要多个TextView实现的功能。
### 支持的特性
* 使用简单
* 链式调用
* 支持给iconfont左右设置文字
* 支持xml中直接设置常用shape的所有属性
* 支持分别设置iconfont，左文字，右文字的颜色（Selector也可以）
* 支持分别设置iconfont，左文字，右文字字号
* 支持文字和iconfont居中
* 支持左右文字设置多个span
* 支持设置iconfont和左右文字的padding
* 丰富的api,所有属性都支持xml和java调用

### Download
#### Step1
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### Step2
Step 2. Add the dependency
```
dependencies {
		implementation 'com.github.DrownCoder:EasyTextView:v1.0'
	}
```
### 效果
 <img src="https://github.com/DrownCoder/EasyTextView/blob/master/image.png" width = "362" height = "642" alt="自由发挥想象空间" align=center />  
 
### 使用  
#### xml属性
```
<com.study.xuan.library.widget.EasyTextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="&#xe627;"
            android:textColor="#494949"
            android:textSize="14dp"
            app:iconColor="@android:color/holo_red_light"
            app:strokeColor="@android:color/holo_red_light"
            app:strokeWidth="1dp"
            app:textPadding="5dp"
            app:textRight="关注"
            app:totalRadius="12dp" />
```
```
<declare-styleable name="EasyTextView">
        //Shape中的Type：RECTANGLE：0(默认)，OVAL（1），LINE（2）
        <attr name="shapeType" format="integer" />
        //四个角的圆角
        <attr name="totalRadius"  format="dimension"/>
        //左上角
        <attr name="radiusTopLeft" format="dimension"/>
        //左下角
        <attr name="radiusBottomLeft" format="dimension"/>
        //右上角
        <attr name="radiusTopRight" format="dimension"/>
        //右下角
        <attr name="radiusBottomRight" format="dimension"/>
        //边线的颜色
        <attr name="strokeColor"  format="color"/>
        //边线的宽度
        <attr name="strokeWidth" format="dimension" />
        //填充的颜色
        <attr name="soildBac" format="color"/>
        //中间的iconfont距离左右Text的内边距
        <attr name="textPadding" format="dimension"/>
        //左边的文字
        <attr name="textLeft" format="string"/>
        //右边的文字
        <attr name="textRight" format="string"/>
        //中间的iconfont的color（注意TextColor属性会覆盖）
        <attr name="iconColor" format="reference|color"/>
        //左边文字的color（注意TextColor属性会覆盖）
        <attr name="textLeftColor" format="reference|color"/>
        //右边文字的color（注意TextColor属性会覆盖）
        <attr name="textRightColor" format="reference|color"/>
        //左边文字的大小（iconfont的大小用TextSize即可，不会覆盖）
        <attr name="textLeftSize" format="dimension"/>
        //右边文字的大小（iconfont的大小用TextSize即可，不会覆盖）
        <attr name="textRightSize" format="dimension"/>
    </declare-styleable>
```
#### java Api
```
//少属性可以直接设置
etvGet.setTextRight(已关注);
//多属性可以链式调用，减少重复绘制，节省性能，注意最后build()
etvGet.strokeWidth(DensityUtils.dp2px(context, 1))
                    .strokeColor(Color.parseColor("#ffe849"))
                    .solid(Color.parseColor("#e8264a"))
                    .icon(context.getText(R.string.icon_font_check).toString())
                    .textRight("关注").build();
```
```
/**
  * 非链式调用api
  */
public void setType(int type);
public void setStrokeWidth(int value);
public void setStrokeColor(@ColorInt int color);
public void setSolid(int soild);
public void setIconColor(int color);
public void setTextLeft(CharSequence textLeft);
public void setTextRight(CharSequence textRight);
public void setTextLeftColor(int color);
public void setTextRightColor(int color);
public void setTextLeftSize(float leftSize);
public void setTextRightSize(float rightSize);
public void setIcon(String iconText);
public void clearSpan();
public void addSpanLeft(Object object, int start, int end, int flags);
public void addSpanLeft(List<Object> objects, int start, int end, int flags);
public void addSpanRight(List<Object> objects, int start, int end, int flags);
public void addSpanRight(Object object, int start, int end, int flags);
```
#### More
动态设置Shape，由[SupperShape](https://github.com/DrownCoder/SupperShape)实现，可以动态通过java代码给任何View设置Shape，不需要再写shape.xml文件，欢迎使用。
本项目仅基于TextView进行封装，其他View可按照需求进行封装，原理比较简单，大家可以查看源码。
    
详细使用说明参考[Wiki](https://github.com/DrownCoder/EasyTextView/wiki)

### Future
* 支持xml中设置文字的点击效果
* 支持iconfont上下设置文字

### License
```
Copyright 2017 [DrownCoder] 

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
