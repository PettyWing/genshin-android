package com.youer.genshin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.youer.genshin.constants.Constants

/**
 * @author youer
 * @date 9/20/23
 */
abstract class BaseFragment : Fragment() {
    var uid: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            uid = getLong(Constants.KEY_UID)
        }
    }
}