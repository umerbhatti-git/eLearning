package com.example.elearning.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.example.elearning.R
import com.example.elearning.adapters.CartRVAdapter
import com.example.elearning.databinding.FragmentCartBinding
import com.example.elearning.models.Result
import com.example.elearning.room.MyDatabase
import com.example.elearning.room.courses.CoursesRepository
import com.example.elearning.viewModels.CoursesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class CartFragment : Fragment(), CartRVAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var coursesViewModel: CoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
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
            if (!result.isNullOrEmpty()) {
                binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
                binding.rvItems.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                binding.rvItems.adapter = CartRVAdapter(result, this@CartFragment)
                var subTotal = 0.0
                for (res in result) {
                    subTotal += res.price.toFloat()
                }
                binding.tvSubTotal.text = String.format(Locale.getDefault(), "$%.2f", subTotal)
                binding.tvTotal.text = String.format(Locale.getDefault(), "$%.2f", (subTotal + 20))
            } else {
                Navigation.findNavController(view).popBackStack()
            }
        }
        binding.btnCheckout.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_cart_to_order_success)
        }
    }

    override fun onItemClick(course: Result) {
        AwesomeDialog.build(requireActivity())
            .title(
                "Remove Course",
                titleColor = ContextCompat.getColor(requireContext(), R.color.blue)
            )
            .body(
                "Are you sure you want to remove this course?",
                color = ContextCompat.getColor(requireContext(), R.color.blue)
            )
            .onPositive(
                "Yes",
                buttonBackgroundColor = R.drawable.rounded_corner_green
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    coursesViewModel.deleteCourse(course.id)
                }
            }
            .onNegative(
                "No",
                buttonBackgroundColor = R.drawable.rounded_corner_red
            )
    }
}