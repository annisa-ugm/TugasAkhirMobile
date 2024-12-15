package com.example.tugasakhirmobile.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirmobile.R
import com.example.tugasakhirmobile.database.DatabaseInstance
import com.example.tugasakhirmobile.database.FavoriteDao
import com.example.tugasakhirmobile.databinding.FragmentUserHomeBinding
import com.example.tugasakhirmobile.model.Furniture
import com.example.tugasakhirmobile.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.Observer


class UserHomeFragment : Fragment(R.layout.fragment_user_home) {

    private var _binding: FragmentUserHomeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding tidak boleh null")

    private lateinit var userFurnitureAdapter: UserFurnitureAdapter
    private lateinit var prefManager: PrefManager
    private lateinit var favoriteDao: FavoriteDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUserHomeBinding.bind(view)

        prefManager = PrefManager.getInstance(requireContext())

        val database = DatabaseInstance.getDatabase(requireContext())
        favoriteDao = database.favoriteDao()

        binding.rvFurniture.layoutManager = LinearLayoutManager(requireContext())
        userFurnitureAdapter = UserFurnitureAdapter(favoriteDao, prefManager)
        binding.rvFurniture.adapter = userFurnitureAdapter

        fetchFurnitureData()
    }

    private fun fetchFurnitureData() {
        var apiCall: Call<List<Furniture>>? = null

        binding.progressBar.visibility = View.VISIBLE
        val apiService = ApiClient.getInstance()

        apiCall = apiService.getAllFurniture()
        apiCall?.enqueue(object : Callback<List<Furniture>> {
            override fun onResponse(
                call: Call<List<Furniture>>,
                response: Response<List<Furniture>>
            ) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let { furnitureList ->
                        userFurnitureAdapter.setFurniture(furnitureList)
                    }
                } else {
                    _binding?.let {
                        Toast.makeText(requireContext(), "Failed to load furniture data", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Furniture>>, t: Throwable) {
                Log.e("UserHomeFragment", "Error fetching furniture data", t)
                _binding?.progressBar?.visibility = View.GONE
                _binding?.let {
                    Toast.makeText(requireContext(), "Error fetching furniture data: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
