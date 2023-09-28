package com.youer.genshin.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.youer.genshin.constants.Constants;

/**
 * @author youer
 * @date 9/20/23
 */
public class BaseFragment extends Fragment {

    Long uid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            uid = args.getLong(Constants.KEY_UID);
        }
    }

}