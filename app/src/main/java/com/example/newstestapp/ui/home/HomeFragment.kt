package com.example.newstestapp.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newstestapp.R
import com.example.newstestapp.databinding.FragmentHomeBinding
import com.example.newstestapp.ui.views.MarginDecoration
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()
    private val marginDecoration: MarginDecoration by inject()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getTopNews()
        viewModel.getNewsFromNet()

        binding.newsList.adapter = HomeNewsAdapter(HomeNewsAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        binding.newsList.addItemDecoration(marginDecoration)

        binding.articleImage.setOnClickListener {
            viewModel.displayPropertyDetails()
        }
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    R.id.action_navigation_home_to_navigation_web_view,
                    bundleOf("url" to it.url)
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    private fun View.hideKeyboard(){
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}