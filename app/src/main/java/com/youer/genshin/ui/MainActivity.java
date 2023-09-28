package com.youer.genshin.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.youer.genshin.constants.Constants;
import com.youer.genshin.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private long uid;
    private ActivityMainBinding binding;
    private TabLayoutMediator mediator;
    private final String[] tabs = new String[] {"首页", "圣遗物", "我的"};
    private final Class[] fragments = new Class[] {MainFragment.class, RelicsFragment.class, MyFragment.class};
    private int activeColor = Color.parseColor("#ff678f");
    private int normalColor = Color.parseColor("#666666");
    private int normalSize = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uid = getIntent().getLongExtra(Constants.KEY_UID, 0);
        initView();
    }

    private void initView() {
        binding.viewPager.setOffscreenPageLimit(3);
        //Adapter
        binding.viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                try {
                    Fragment fragment = (Fragment)fragments[position].newInstance();
                    Bundle args = new Bundle();
                    args.putLong(Constants.KEY_UID, uid);
                    fragment.setArguments(args);
                    return fragment;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });
        //viewPager 页面切换监听
        binding.viewPager.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                TextView tabView = new TextView(MainActivity.this);

                int[][] states = new int[2][];
                states[0] = new int[] {android.R.attr.state_selected};
                states[1] = new int[] {};

                int[] colors = new int[] {activeColor, normalColor};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                tabView.setText(tabs[position]);
                tabView.setTextSize(normalSize);
                tabView.setGravity(Gravity.CENTER);
                tabView.setTextColor(colorStateList);

                tab.setCustomView(tabView);
            }
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();
    }

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = binding.tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
                TextView tabView = (TextView)tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        mediator.detach();
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }
}