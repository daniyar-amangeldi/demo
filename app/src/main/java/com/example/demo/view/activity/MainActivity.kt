package com.example.demo.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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
import com.example.demo.view.util.EventManager
import com.example.demo.view.util.RemoteConfigManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        startActivity(intent)

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
                    EventManager(this).log("MovieListTabClicked")
                }

                R.id.movie_favourites -> {
                    binding.viewPager.currentItem = 1
                    EventManager(this).log("MovieFavouritesTabClicked")
                }

                R.id.profile -> {
                    binding.viewPager.currentItem = 2
                    EventManager(this).log("ProfileTabClicked")
                }
            }

            true
        }

        val isNewDesign = RemoteConfigManager(this).getBoolean("is_new_design")
        println("Firebase RC: isNewDesign=$isNewDesign")

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result
            println("FCM Token: $token")
        }
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