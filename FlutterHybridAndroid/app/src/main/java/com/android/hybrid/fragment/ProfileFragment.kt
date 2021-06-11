package com.android.hybrid.fragment

import android.os.Bundle
import android.view.View
import com.android.hybrid.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 15:20
 * Email: hydznsqk@163.com
 * Des:
 */
class ProfileFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_profile

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rl_title.visibility=View.VISIBLE
        title_line.visibility=View.VISIBLE
        title.setText("我的")
    }
}
