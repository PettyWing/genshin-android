package com.youer.genshin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.youer.genshin.constants.Constants
import com.youer.genshin.presenter.MainPresenter

/**
 * @author youer
 * @date 9/20/23
 */
abstract class BaseFragment(presenter:MainPresenter) : Fragment() {
    var uid: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            uid = getLong(Constants.KEY_UID)
        }
    }
}