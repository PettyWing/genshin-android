package com.youer.genshin.ui

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.youer.genshin.constants.Constants
import com.youer.genshin.databinding.ActivityMainBinding
import com.youer.genshin.presenter.MainPresenter
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var uid by Delegates.notNull<Long>()
    private lateinit var binding: ActivityMainBinding
    private var mediator: TabLayoutMediator? = null
    private val tabs = arrayOf("首页", "圣遗物", "我的")
    private val fragments = arrayOf<Class<*>>(MainFragment::class.java, RelicsFragment::class.java, MyFragment::class.java)
    private val activeColor = Color.parseColor("#ff678f")
    private val normalColor = Color.parseColor("#666666")
    private val normalSize = 14
    private lateinit var presenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        presenter = MainPresenter(this)
        setContentView(binding.root)
        uid = intent.getLongExtra(Constants.KEY_UID, 0)
        initView()
    }

    private fun initView() {
        binding.viewPager.apply {
            offscreenPageLimit = 3
            //viewPager 页面切换监听
            registerOnPageChangeCallback(changeCallback)
            adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun createFragment(position: Int): Fragment {
                    fragments[position].getDeclaredConstructor(MainPresenter::class.java).apply {
                        isAccessible = true
                        return (newInstance(presenter) as Fragment).apply {
                            val args = Bundle()
                            args.putLong(Constants.KEY_UID, uid)
                            arguments = args
                        }
                    }
                }

                override fun getItemCount(): Int {
                    return tabs.size
                }
            }
        }
        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager, TabConfigurationStrategy { tab, position -> //这里可以自定义TabView
            val states = Stream.of(intArrayOf(R.attr.state_selected), intArrayOf()).collect(Collectors.toList());
            val colorStateList = ColorStateList(states.toTypedArray(), intArrayOf(activeColor, normalColor))
            tab.customView = TextView(this@MainActivity).apply {
                text = tabs[position]
                textSize = normalSize.toFloat()
                gravity = Gravity.CENTER
                setTextColor(colorStateList)
            }
        })
        //要执行这一句才是真正将两者绑定起来
        mediator?.attach()
    }

    private val changeCallback: OnPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            //可以来设置选中时tab的大小
            val tabCount = binding.tabLayout.tabCount
            for (i in 0 until tabCount) {
                val tab = binding.tabLayout.getTabAt(i)
                val tabView = tab?.customView as TextView?
                if (tab?.position == position) {
                    tabView?.typeface = Typeface.DEFAULT_BOLD
                } else {
                    tabView?.typeface = Typeface.DEFAULT
                }
            }
        }
    }

    override fun onDestroy() {
        mediator?.detach()
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback)
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}