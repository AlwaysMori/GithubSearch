package com.nanda.githubsearch.adapter

import com.nanda.githubsearch.ui.PageDetail
import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.fragment.app.Fragment


class AdapterSectionPagerForDetail(activity: FragmentActivity, private val appName: String) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return PageDetail().apply {
            arguments = Bundle().apply {
                putInt(PageDetail.ARG_SECTION_NUMBER, position + 1)
                putString(PageDetail.ARG_NAME, appName)
            }
        }
    }
}
