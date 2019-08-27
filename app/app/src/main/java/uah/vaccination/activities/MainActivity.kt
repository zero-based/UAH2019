package uah.vaccination.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import uah.vaccination.R
import uah.vaccination.fragments.ChildrenFragment
import uah.vaccination.fragments.HomeFragment
import uah.vaccination.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navBar: BottomNavigationView = findViewById(R.id.nav_view)
        navBar.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navBar.selectedItemId = R.id.navigation_home
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_children -> {
                switchFragment(ChildrenFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                switchFragment(SettingsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }
}
