# MoreTextView
这是TextView长文本的展开与收起显示。这个库扩展了TextView的版本。使用方便。

![demo](https://github.com/selfimgr/MoreTextView/blob/master/moretext.gif)

# 使用说明

## 添加依赖
```
implementation 'com.chaowen.util:moretextview:1.0'
```

## 布局中xml使用
```
 <com.chaowen.moretextview.MoreTextView
        android:id="@+id/moreTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/moretext"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```



