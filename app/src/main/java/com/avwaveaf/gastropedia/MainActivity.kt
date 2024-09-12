package com.avwaveaf.gastropedia

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avwaveaf.gastropedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvGastronomy: RecyclerView

    private val listGastro = ArrayList<Gastronomy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)

        rvGastronomy = binding.rvGastro
        rvGastronomy.setHasFixedSize(true)

        listGastro.addAll(fetchGastroList())
        showRv()

        binding.fabClose.setOnClickListener {
            binding.bannerCard.visibility = View.GONE
        }
    }

    private fun showRv() {
        rvGastronomy.layoutManager = LinearLayoutManager(this)
        val gastroAdapter = ListGastroAdapter(listGastro) { selectedGastro ->
            val intent = Intent(this@MainActivity, DetailGastroActivity::class.java)
            intent.putExtra(DetailGastroActivity.EXTRA_GASTRO, selectedGastro)
            startActivity(intent)
        }
        rvGastronomy.adapter = gastroAdapter
    }

    private fun fetchGastroList(): List<Gastronomy> {
        val gastroNames = resources.getStringArray(R.array.gastronomy_name)
        val gastroDescriptions = resources.getStringArray(R.array.gastronomy_description)
        val gastroPhotos = resources.getStringArray(R.array.gastronomy_photo)
        val gastroCategories = resources.getStringArray(R.array.gastronomy_category)
        val gastroIngredients = resources.getStringArray(R.array.gastronomy_ingredients)
        val gastroHistory = resources.getStringArray(R.array.gastronomy_history)
        return gastroNames.indices.map { i ->
            Gastronomy(
                name = gastroNames[i],
                description = gastroDescriptions[i],
                imgUrl = gastroPhotos[i],
                category = gastroCategories[i],
                ingredients = gastroIngredients[i],
                history = gastroHistory[i]
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                navigateToAboutScreen()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToAboutScreen() {
        startActivity(Intent(this@MainActivity, AboutActivity::class.java))
    }
}
