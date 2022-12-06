package com.bignerdranch.android.photogallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class PhotoPageFragment : Fragment() {
    private val args: PhotoPageFragment by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoGalleryViewModel.uiState.collect { state ->
                    binding.photoGrid.adapter = PhotoListAdapter(
                        state.images
                    ) { photoPageUri ->
                        CustomTabsIntent.Builder()
                            .setToolbarColor(
                                ContextCompat.getColor(
                                    requireContext(), R.color.colorPrimary
                                )
                            )
                            .setShowTitle(true)
                            .build()
                            .launchUrl(requireContext(), photoPageUri)
                    }
                    searchView?.setQuery(state.query, false)
                    updatePollingState(state.isPolling)
                }
            }
        }
    }
}


