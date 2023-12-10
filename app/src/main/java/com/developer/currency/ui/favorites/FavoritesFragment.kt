package com.developer.currency.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.core.base.BaseAdapter
import com.developer.currency.core.common.FAVORITE_CONVERTER
import com.developer.currency.core.common.FAVORITE_VALUTE
import com.developer.currency.core.utils.launchAndCollectIn
import com.developer.currency.databinding.FragmentFavoritesBinding
import com.developer.currency.domain.entities.Valute
import com.developer.currency.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val viewBinding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var sortAdapter: BaseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favorite = arguments?.getString("favorite")
        sortAdapter = BaseAdapter(listOf(FavoriteAdapter(favorite, ::onItemValute)))
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = sortAdapter
        }
    }

    private fun setupViewModel() {
        val favorite = arguments?.getString("favorite")
        if (FAVORITE_VALUTE == favorite) {
            viewModel.getFavoriteLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
                getAllValuteSuccess(it)
            }
        } else if (FAVORITE_CONVERTER == favorite) {
            viewModel.getAllConverterLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
                getAllValuteSuccess(it)
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        sortAdapter.submitList(valutes)
    }

    private fun onItemValute(item: Valute, position: Int) {
        viewModel.updateLocalValute(item)
        sortAdapter.notifyItemChanged(position)
    }
}