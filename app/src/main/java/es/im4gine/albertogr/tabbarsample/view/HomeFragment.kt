package es.im4gine.albertogr.tabbarsample.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.im4gine.albertogr.tabbarsample.R

/**
 * Created by albertogr on 29/11/2017.
 */
class HomeFragment : Fragment() {

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.title_home)
        val view = inflater?.inflate(R.layout.fragment_home, container, false)
        return view
    }

}