package uz.shokirov.aboutme.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.shokirov.aboutme.fragments.AboutFragment
import uz.shokirov.aboutme.fragments.PortfolioFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AboutFragment()
            }
            1 -> {
                PortfolioFragment()
            }
            else -> {
                return AboutFragment()
            }
        }
    }
}