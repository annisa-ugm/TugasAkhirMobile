package com.example.tugasakhirmobile.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirmobile.database.DatabaseInstance
import com.example.tugasakhirmobile.database.FavoriteDao
import com.example.tugasakhirmobile.databinding.FragmentUserFavoriteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserFavoriteFragment : Fragment() {

    private var _binding: FragmentUserFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var prefManager: PrefManager
    private lateinit var adapter: UserFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pastikan binding tidak null
        binding?.let {
            val favoriteDao = DatabaseInstance.getDatabase(requireContext()).favoriteDao()
            prefManager = PrefManager.getInstance(requireContext())
            val username = prefManager.getUsername()

            adapter = UserFavoriteAdapter(favoriteDao, username)
            it.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
            it.rvFavorite.adapter = adapter

            loadFavorites(favoriteDao, username)
        } ?: run {
            Log.e("UserFavoriteFragment", "Binding is null")
        }
    }

    private fun loadFavorites(favoriteDao: FavoriteDao, username: String) {
        binding?.progressBar?.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val favorites = favoriteDao.getFavoritesByUsername(username)
            withContext(Dispatchers.Main) {
                binding?.progressBar?.visibility = View.GONE
                adapter.setFavorites(favorites)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
