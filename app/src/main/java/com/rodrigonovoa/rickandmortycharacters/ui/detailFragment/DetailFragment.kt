package com.rodrigonovoa.rickandmortycharacters.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rodrigonovoa.rickandmortycharacters.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModel()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCharacterDetailFromId()
        viewListeners()
        observers()
    }

    private fun getCharacterDetailFromId() {
        val args = arguments
        if (args != null) {
            val characterId = args.getInt("characterIdBundle")
            detailViewModel.getCharacterById(characterId)
        }
    }

    private fun viewListeners() {
        binding.imvBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun observers() {
        detailViewModel.detail.observe(requireActivity(), Observer { detail ->
            binding.tvName.text = detail.name
            binding.tvStatus.text = "Status: " + detail.status
            binding.tvSpecies.text = "Specie: " + detail.species
            binding.tvOrigin.text = "Origin: " + detail.origin.name
            binding.tvGender.text = "Gender: " + detail.gender
            if(isAdded) Glide.with(requireContext()).load(detail.image).into(binding.imvCharacter);
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}