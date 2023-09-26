package com.rodrigonovoa.rickandmortycharacters.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rodrigonovoa.rickandmortycharacters.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        if (args != null) {
            val exampleInt = args.getInt("characterIdBundle")
            detailViewModel.getCharacterById(exampleInt)
        }

        detailViewModel.detail.observe(requireActivity(), Observer { detail ->
            Timber.i(detail.toString())
        })
    }
}