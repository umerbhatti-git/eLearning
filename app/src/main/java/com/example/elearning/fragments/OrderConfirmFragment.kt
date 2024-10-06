package com.example.elearning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elearning.adapters.OrderConfirmRVAdapter
import com.example.elearning.databinding.FragmentOrderConfirmBinding
import com.example.elearning.room.MyDatabase
import com.example.elearning.room.courses.CoursesRepository
import com.example.elearning.room.user.UserRepository
import com.example.elearning.viewModels.CoursesViewModel
import com.example.elearning.viewModels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderConfirmFragment : Fragment() {

    private lateinit var binding: FragmentOrderConfirmBinding
    private lateinit var coursesViewModel: CoursesViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderConfirmBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseDao =
            MyDatabase.getDatabase(requireContext().applicationContext).coursesDao()
        val repository = CoursesRepository(courseDao)
        coursesViewModel = CoursesViewModel(repository)

        coursesViewModel.allCourses.observe(viewLifecycleOwner) { result ->
            binding.rvCourses.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCourses.adapter = OrderConfirmRVAdapter(result)
        }

        val userDao = MyDatabase.getDatabase(requireContext().applicationContext).userDao()
        val userRepo = UserRepository(userDao)
        userViewModel = UserViewModel(userRepo)

        CoroutineScope(Dispatchers.IO).launch {
            userViewModel.getUser()?.apply {
                binding.tvName.text = "${this.firstName} ${this.lastName},"
            }
        }
    }

    override fun onDestroyView() {
        CoroutineScope(Dispatchers.IO).launch {
            coursesViewModel.deleteAllCourses()
        }
        super.onDestroyView()
    }
}