package com.developer.valyutaapp.ui.favorite_edit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.FAVORITE_CONVERTER
import com.developer.valyutaapp.core.common.FAVORITE_VALUTE
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentFavoritesBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.FavoriteAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewBinding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    private val prefs: SharedPreference by inject()

    val favorite = arguments?.getString("favorite")

    private var valuteList: MutableList<Valute> = ArrayList()
    private lateinit var sortAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favorite = arguments?.getString("favorite")
        sortAdapter = FavoriteAdapter(requireContext(), favorite, valuteList, ::onItemValute)
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
            lifecycleScope.launchWhenCreated {
                viewModel.getFavoriteLocalValutes().collect {
                    getAllValuteSuccess(it)
                }
            }
        } else if (FAVORITE_CONVERTER == favorite) {
            lifecycleScope.launchWhenCreated {
                viewModel.getAllConverterLocalValutes().collect {
                    getAllValuteSuccess(it)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllValuteSuccess(valutes: List<Valute>) {
        valuteList.clear()
        valuteList.addAll(valutes)
        sortAdapter.notifyDataSetChanged()
    }

    private fun onItemValute(item: Valute, position: Int) {
        viewModel.updateLocalValute(item)
    }
}