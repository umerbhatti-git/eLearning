package com.example.elearning.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.vvalidator.form
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.example.elearning.R
import com.example.elearning.databinding.FragmentCourseDetailBinding
import com.example.elearning.models.Result
import com.example.elearning.models.UserModel
import com.example.elearning.room.MyDatabase
import com.example.elearning.room.courses.CoursesRepository
import com.example.elearning.room.user.UserRepository
import com.example.elearning.utils.SharedPreferenceHelper
import com.example.elearning.viewModels.CoursesViewModel
import com.example.elearning.viewModels.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CourseDetailFragment : Fragment() {

    private lateinit var binding: FragmentCourseDetailBinding
    private lateinit var sharedPrefHelper: SharedPreferenceHelper
    private lateinit var coursesViewModel: CoursesViewModel
    private lateinit var userViewModel: UserViewModel
    private var course: Result? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        course = requireArguments().getParcelable("course") as? Result
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        sharedPrefHelper = SharedPreferenceHelper(requireContext())
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        course?.let {
            binding.tvTitle.text = it.title
            binding.tvPrice.text = "$${it.price}"
            binding.tvDetail.text = it.details
        }

        val courseDao =
            MyDatabase.getDatabase(requireContext().applicationContext).coursesDao()
        val repository = CoursesRepository(courseDao)
        coursesViewModel = CoursesViewModel(repository)

        coursesViewModel.allCourses.observe(viewLifecycleOwner) { result ->
            course?.let { courseItem ->
                val isInCart = result.any { it.id == courseItem.id }
                if (!isInCart) {
                    binding.btnCart.isEnabled = true
                    binding.btnCart.text = "Add to cart"
                } else {
                    binding.btnCart.isEnabled = false
                    binding.btnCart.text = "Already in cart"
                }
            }
        }

        val userDao = MyDatabase.getDatabase(requireContext().applicationContext).userDao()
        val userRepo = UserRepository(userDao)
        userViewModel = UserViewModel(userRepo)

        binding.btnCart.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = userViewModel.getUser()  // Fetch user data
                withContext(Dispatchers.Main) {
                    if (user == null) {
                        AwesomeDialog.build(requireActivity())
                            .title(
                                "Online Class Enrollment",
                                titleColor = ContextCompat.getColor(requireContext(), R.color.blue)
                            )
                            .body(
                                "Please enroll for online classes to proceed",
                                color = ContextCompat.getColor(requireContext(), R.color.blue)
                            )
                            .onPositive(
                                "Yes",
                                buttonBackgroundColor = R.drawable.rounded_corner_green
                            ) {
                                showOnlineClassesDialog()
                            }
                            .onNegative(
                                "No",
                                buttonBackgroundColor = R.drawable.rounded_corner_red
                            )

                    } else {
                        // Insert course and show toast on the main thread
                        course?.let { it1 ->
                            CoroutineScope(Dispatchers.IO).launch {
                                coursesViewModel.insert(it1)
                            }
                            Toast.makeText(
                                context,
                                "Course added to the cart",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun showOnlineClassesDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.AlertDialogLight)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.online_class_forum, null)
        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()

        val firstName = view.findViewById<TextInputEditText>(R.id.et_first_name)
        val lastName = view.findViewById<TextInputEditText>(R.id.et_last_name)
        val phone = view.findViewById<TextInputEditText>(R.id.et_phone)
        val email = view.findViewById<TextInputEditText>(R.id.et_email)
        val enrollBtn = view.findViewById<AppCompatButton>(R.id.btn_enroll)

        form {
            input(firstName) {
                isNotEmpty()
            }
            input(lastName) {
                isNotEmpty()
            }
            input(phone) {
                isNumber()
            }
            input(email) {
                isEmail()
            }
            submitWith(enrollBtn) {
                dialog.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    userViewModel.insert(
                        UserModel(
                            firstName.text.toString(),
                            lastName.text.toString(),
                            phone.text.toString(),
                            email.text.toString()
                        )
                    )
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "You have been successfully enrolled for online classes",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        dialog.show()
    }
}