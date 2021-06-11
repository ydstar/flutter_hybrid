package com.android.hybrid.flutter.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.android.hybrid.util.AppGlobals
import com.bumptech.glide.Glide

/**
 * 将Native UI 嵌入到Flutter中去使用,需要准备4件套
 * 1.FImageView,要嵌入到Flutter的iOS view 或者 Android view
 * 2.FImageViewController,FImageView的控制器,用来创建和管理FImageView
 * 3.FImageViewFactory,用于向Flutter提供FImageView
 * 4.FImageViewPlugin,用于向Flutter注册FImageView
 *
 * 接下来,我们就以图片为例,把ImageView控件嵌入到Flutter中进行使用
 */
class FImageView @JvmOverloads constructor(
    context: Context = AppGlobals.get()!!,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    fun setUrl(url: String) {
        Glide.with(this).load(url).into(this)
    }
}