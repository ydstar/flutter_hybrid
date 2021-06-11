package com.android.hybrid.fragment

import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import com.android.hybrid.R

import com.tab.bottom.kit.TabBottomKitLayout
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 15:20
 * Email: hydznsqk@163.com
 * Des:
 */
class HomeFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rl_title.visibility=View.VISIBLE
        title_line.visibility=View.VISIBLE
        title.setText("首页")
        val scrollView = view.findViewById<ScrollView>(R.id.scroll_view)
        TabBottomKitLayout.clipBottomPadding(scrollView)
    }
}
