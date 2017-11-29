package es.im4gine.albertogr.tabbarsample

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import es.im4gine.albertogr.tabbarsample.extensions.active
import es.im4gine.albertogr.tabbarsample.extensions.disableShiftMode
import es.im4gine.albertogr.tabbarsample.helpers.BottomNavigationPosition
import es.im4gine.albertogr.tabbarsample.helpers.createFragment
import es.im4gine.albertogr.tabbarsample.helpers.findNavigationPositionById
import es.im4gine.albertogr.tabbarsample.helpers.getTag
import es.im4gine.albertogr.tabbarsample.view.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val KEY_POSITION = "keyPosition"

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.HOME

    private lateinit var toolbar: Toolbar

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveInstanceState(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        setSupportActionBar(toolbar)
        setupBottomNavigation()
        initFragment(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        // Store the current navigation position.
        outState?.putInt(KEY_POSITION, navPosition.id)
        super.onSaveInstanceState(outState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navPosition = findNavigationPositionById(item.itemId)
        return switchFragment(navPosition.createFragment(), navPosition.getTag())
    }

    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        // Restore the current navigation position.
        savedInstanceState?.also {
            val id = it.getInt(KEY_POSITION, BottomNavigationPosition.HOME.id)
            navPosition = findNavigationPositionById(id)
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.disableShiftMode()
        bottomNavigation.active(navPosition.position)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: switchFragment(HomeFragment.newInstance(), HomeFragment.TAG)
    }

    /**
     * Immediately execute transactions with FragmentManager#executePendingTransactions.
     */
    private fun switchFragment(fragment: Fragment, tag: String): Boolean {
        if (fragment.isAdded) return false
        detachFragment()
        attachFragment(fragment, tag)
        supportFragmentManager.executePendingTransactions()
        return true
    }

    private fun detachFragment() {
        supportFragmentManager.findFragmentById(R.id.container)?.also {
            supportFragmentManager.beginTransaction().detach(it).commit()
        }
    }

    private fun attachFragment(fragment: Fragment, tag: String) {
        if (fragment.isDetached) {
            supportFragmentManager.beginTransaction().attach(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment, tag).commit()
        }
        // Set a transition animation for this transaction.
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

}
