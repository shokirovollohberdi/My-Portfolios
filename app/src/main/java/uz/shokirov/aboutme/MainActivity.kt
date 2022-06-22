package uz.shokirov.aboutme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import nl.joery.animatedbottombar.AnimatedBottomBar
import uz.shokirov.aboutme.adapter.FragmentAdapter
import uz.shokirov.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setAdapter()

        binding.animatedBottom.onTabSelected = {
            when (it.title) {
                "About me" -> {
                    binding.viewpager.currentItem = 0
                }
                "My portfolios" -> {
                    binding.viewpager.currentItem = 1
                }

            }
        }

    }

    private fun setAdapter() {
        var adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter
        binding.viewpager.isUserInputEnabled = false
    }
}