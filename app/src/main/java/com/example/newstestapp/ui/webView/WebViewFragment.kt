package com.example.newstestapp.ui.webView

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newstestapp.databinding.FragmentWebViewBinding
import android.view.MotionEvent
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.newstestapp.R


class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater)
        val selectedArticleUrl = arguments?.getString("url", "").toString()
        binding.webView.loadUrl(selectedArticleUrl)
//        binding.webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && binding.webView.canGoBack()) {
////                this.findNavController().popBackStack()
//                binding.webView.goBack()
//                return@OnKeyListener true
//            }
//            false
//        })

        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(R.id.navigation_home, false)
                }
            })

        return binding.root
    }
}