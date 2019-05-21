基于iconfont拓展的TextView(支持左右设置Text,设置Shape,设置span等)  

![continuousphp](https://img.shields.io/continuousphp/git-hub/doctrine/dbal/master.svg)
[![](https://jitpack.io/v/drowncoder/easytextview.svg)](https://jitpack.io/#drowncoder/easytextview)
![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)
### 项目介绍
* 还在苦于iconfont左右两边还需要设置文字只能加两个TextView？
* 还在苦于需要设置各种大同小异的边框而new Shape文件吗？
* 还在苦于设置点击效果而多种样式吗？
>一个基于iconfont拓展的支持多种功能的轻量级TextView，减少布局嵌套，减少定义shape文件，基本涵盖常规需要多个TextView实现的功能。(最多支持左中右三段文字)
### 支持的特性
* 使用简单
* 链式调用
* 支持给iconfont左右设置文字
* 支持xml中直接设置常用shape的所有属性
* 支持渐变背景色，边线，纯色背景色，圆角等
* 支持分别设置iconfont，左文字，右文字的颜色（Selector也可以）、字号、样式（粗体、斜体）
* 支持左边文字，中间文字，右边文字都设置iconfont
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
		implementation 'com.github.DrownCoder:EasyTextView:v1.13'
	}
```
### 效果
 <img src="https://github.com/DrownCoder/EasyTextView/blob/master/EasyTextView_Demo.jpg" width = "362" height = "642" alt="自由发挥想象空间" align=center />
 
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
        <attr name="topLeft" format="dimension"/>
        //左下角
        <attr name="bottomLeft" format="dimension"/>
        //右上角
        <attr name="topRight" format="dimension"/>
        //右下角
        <attr name="bottomRight" format="dimension"/>
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
	//设置左边文字样式
	<attr name="textLeftStyle">
            <enum name="bold" value="1" />
            <enum name="italic" value="2" />
        </attr>
	//设置右边文字样式
        <attr name="textRightStyle">
            <enum name="bold" value="1" />
            <enum name="italic" value="2" />
        </attr>
	//设置中间文字样式
        <attr name="textCenterStyle">
            <enum name="bold" value="1" />
            <enum name="italic" value="2" />
        </attr>
        <attr name="autoMaxHeight" format="boolean" />
     //渐变色方向
        <attr name="gradientOrientation">
            <enum name="top_bottom" value="0" />
            <enum name="tp_bl" value="1" />
            <enum name="right_left" value="2" />
            <enum name="br_tl" value="3" />
            <enum name="bottom_top" value="4" />
            <enum name="bl_tr" value="5" />
            <enum name="left_right" value="6" />
            <enum name="tl_br" value="7" />
        </attr>
     //渐变色的起始色，中间色，末尾色
        <attr name="startSolid" format="reference|color" />
        <attr name="centerSolid" format="reference|color" />
        <attr name="endSolid" format="reference|color" />
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
public EasyTextView textLeftStyle(int textLeftStyle);
public EasyTextView textRightStyle(int textRightStyle);
public EasyTextView textCenterStyle(int textCenterStyle);
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

### 版本更新
v1.15
新增api修复左下和右下圆角颠倒的bug  
v1.14
>新增xml中支持设置渐变色  

v1.13
>1.修改icon类型从String改为charsequence
>2.进一步拓展JAVA的API
>3.补充Error信息

v1.12
>1.修复center_vertical时文字重叠问题  
>2.新增boolean型autoMaxHeight属性,当为true时解决以下问题  
>-修复左右文字大小大于中间文字大小高度不准问题  
>-修复英文由于基线导致展示不全问题  

v1.11  
>修复右侧文字颜色失效bug

v1.1 
>1.中间的文字支持字符串(原来只支持iconfont)  
>2.左边文字，中间文字，右边文字分别支持粗体和斜体  
>3.左边文字，中间文字，右边文字支持xml中设置iconfont(原来xml中只支持中间文字设置)  
>4.优化了代码  

[详细版本信息](https://github.com/DrownCoder/EasyTextView/releases)

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
