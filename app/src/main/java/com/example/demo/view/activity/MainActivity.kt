package com.example.demo.view.activity

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.R
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.util.BaseFragment
import com.example.demo.viewmodel.ApplicationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ApplicationViewModel by viewModels<ApplicationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        intent.data?.let { handleDeeplink(it) }

        val fragments = listOf(
            BaseFragment.newInstance(
                layoutRes = R.layout.fragment_movie_list_base,
                toolbarId = R.id.toolbar_movie_list,
                navHostId = R.id.nav_host_container
            ),
            BaseFragment.newInstance(
                layoutRes = R.layout.fragment_movie_favourites_base,
                toolbarId = R.id.toolbar_movie_favourites,
                navHostId = R.id.nav_host_container
            ),
            BaseFragment.newInstance(
                layoutRes = R.layout.fragment_profile_base,
                toolbarId = R.id.toolbar_profile,
                navHostId = R.id.nav_host_container
            )
        )

        val adapter = PagerAdapter(
            fragments = fragments,
            fragmentManager = supportFragmentManager,
            lifecycle = lifecycle
        )

        binding.viewPager.adapter = adapter

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movie_list -> {
                    binding.viewPager.currentItem = 0
                }

                R.id.movie_favourites -> {
                    binding.viewPager.currentItem = 1
                }

                R.id.profile -> {
                    binding.viewPager.currentItem = 2
                }
            }

            true
        }
    }

    private fun handleDeeplink(uri: Uri) {
        val movieId = uri.getQueryParameter("id")

        viewModel.movieId = movieId
    }

}

class PagerAdapter(
    private val fragments: List<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}