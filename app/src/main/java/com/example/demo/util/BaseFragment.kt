package com.example.demo.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.demo.R

class BaseFragment : Fragment() {

    private val defaultInt = -1
    private var layoutRes: Int = -1
    private var toolbarId: Int = -1
    private var navHostId: Int = -1
    private val appBarConfig = AppBarConfiguration(
        setOf(
            R.id.movie_list_fragment,
            R.id.movie_favourites_fragment,
            R.id.profile_fragment
        )
    )

    companion object {

        private const val KEY_LAYOUT = "layout_key"
        private const val KEY_TOOLBAR = "toolbar_key"
        private const val KEY_NAV_HOST = "nav_host_key"

        fun newInstance(
            @LayoutRes layoutRes: Int,
            toolbarId: Int,
            navHostId: Int
        ) = BaseFragment().apply {
            arguments = bundleOf(
                KEY_LAYOUT to layoutRes,
                KEY_TOOLBAR to toolbarId,
                KEY_NAV_HOST to navHostId
            )
        }
    }

    override fun onStart() {
        super.onStart()
        if (toolbarId == defaultInt || navHostId == defaultInt) return

        val toolbar = requireActivity().findViewById<Toolbar>(toolbarId)
        val navController = requireActivity().findNavController(navHostId)

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfig)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutRes = it.getInt(KEY_LAYOUT)
            toolbarId = it.getInt(KEY_TOOLBAR)
            navHostId = it.getInt(KEY_NAV_HOST)
        } ?: return
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = if (layoutRes == defaultInt) {
        null
    } else {
        inflater.inflate(layoutRes, container, false)
    }
}