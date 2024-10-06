package com.example.elearning.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.elearning.R
import com.example.elearning.databinding.ActivityMainBinding
import com.example.elearning.room.MyDatabase
import com.example.elearning.room.courses.CoursesRepository
import com.example.elearning.viewModels.CoursesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var coursesViewModel: CoursesViewModel

    private val mainDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            binding.toolbarTitle.text = destination.label.toString()
            binding.rlCart.visibility =
                if (destination.id == R.id.cart || destination.id == R.id.orderSuccess) View.GONE else View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener(mainDestinationChangedListener)

        val courseDao =
            MyDatabase.getDatabase(applicationContext).coursesDao()
        val repository = CoursesRepository(courseDao)
        coursesViewModel = CoursesViewModel(repository)
        coursesViewModel.allCourses.observe(this@MainActivity) { result ->
            if (result.isNullOrEmpty()) {
                binding.ivCart.isEnabled = false
                binding.tvCounter.visibility = View.GONE
            } else {
                binding.ivCart.isEnabled = true
                binding.tvCounter.visibility = View.VISIBLE
                binding.tvCounter.text = result.size.toString()
            }
        }

        binding.ivCart.setOnClickListener {
            navController.navigate(R.id.cart)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)
            ?.findNavController()
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }
}