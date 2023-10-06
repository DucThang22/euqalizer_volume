package com.example.hahalolofake.ui.main_v2.tablayout

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.hahalolofake.R
import com.google.android.material.tabs.TabLayout

class DucThangTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    private var titles: List<String> = ArrayList()

    init {
        init()
    }

    private fun init() {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                applyTabStyle(tab, R.drawable.tablayout_item_pressed, Color.WHITE)
            }

            override fun onTabUnselected(tab: Tab) {
                applyTabStyle(tab, R.drawable.tablayout_item_normal, ContextCompat.getColor(context, R.color.color_7E7A99))
            }

            override fun onTabReselected(tab: Tab) {
                // Handle tab reselection if needed
            }
        })
    }

    private fun applyTabStyle(tab: Tab, backgroundDrawable: Int, textColor: Int) {
        val customView: View? = tab.customView
        customView?.findViewById<TextView>(R.id.tab_layout_text)?.apply {
            setBackgroundResource(backgroundDrawable)
            setTextColor(textColor)
        }
    }

    fun setTitles(titles: List<String>) {
        this.titles = titles
        removeAllTabs()

        for (title in this.titles) {
            val tab = newTab()
            tab.customView = View.inflate(context, R.layout.tab_layout_item, null)

            tab.customView?.findViewById<TextView>(R.id.tab_layout_text)?.apply {
                text = title
            }

            addTab(tab)
        }
    }
}