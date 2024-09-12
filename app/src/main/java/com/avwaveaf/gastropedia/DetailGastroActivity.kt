package com.avwaveaf.gastropedia

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.avwaveaf.gastropedia.databinding.ActivityDetailGastroBinding
import com.bumptech.glide.Glide

class DetailGastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGastroBinding

    private var currentGastronomy: Gastronomy? = null

    companion object {
        const val EXTRA_GASTRO = "extra_gastro"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailGastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getExtraGastro()
        bindData()
    }

    private fun bindData() {
        if (currentGastronomy != null) {
            Glide.with(this)
                .load(currentGastronomy!!.imgUrl)
                .into(binding.imgGastro)

            binding.tvGastroTitle.text = currentGastronomy!!.name
            binding.tvGastroDescription.text = currentGastronomy!!.description
            binding.bannerTitleEntry.text = currentGastronomy!!.category
            binding.bannerTitleIngredientEntry.text = currentGastronomy!!.ingredients
            binding.tvGastroHistoryContent.text = currentGastronomy!!.history

            binding.fabShare.setOnClickListener {
                shareGastronomyDetails()
            }
        }
    }

    private fun shareGastronomyDetails() {
        val shareText = """
        Check out this amazing gastronomy!

        Name: ${currentGastronomy?.name}
        Description: ${currentGastronomy?.description}
        Category: ${currentGastronomy?.category}
        Ingredients: ${currentGastronomy?.ingredients}
        History: ${currentGastronomy?.history}
        
        Shared from GastroPedia App!
    """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        try {
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: Exception) {
            Toast.makeText(this, "No apps available to share", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getExtraGastro() {
        currentGastronomy = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_GASTRO, Gastronomy::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_GASTRO)
        }
    }
}
