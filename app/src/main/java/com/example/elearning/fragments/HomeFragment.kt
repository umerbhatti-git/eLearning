package com.example.elearning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elearning.R
import com.example.elearning.adapters.MainRVAdapter
import com.example.elearning.databinding.FragmentHomeBinding
import com.example.elearning.models.Result
import com.example.elearning.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeFragment : Fragment(), MainRVAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var controller: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressCircular.visibility = View.VISIBLE
        controller = Navigation.findNavController(view)
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getData()
                response.let {
                    binding.progressCircular.visibility = View.GONE
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerView.adapter =
                        MainRVAdapter(it.results, this@HomeFragment, requireContext())
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onItemClick(course: Result) {
        val bundle = Bundle()
        bundle.putParcelable("course", course)
        controller.navigate(R.id.action_firstFragment_to_courseDetail, bundle)
    }
}