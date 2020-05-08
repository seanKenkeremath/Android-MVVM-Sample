package com.seank.kotlinflowplayground.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seank.kotlinflowplayground.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : MainViewModelFactory

    private lateinit var viewModel : MainViewModel

    private lateinit var cardTextView : TextView
    private lateinit var loadingView : View
    private lateinit var errorView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardTextView = findViewById(R.id.text)
        loadingView = findViewById(R.id.loading)
        errorView = findViewById(R.id.error)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.firstCard.observe(this, Observer {
            cardTextView.text = it.name
        })
        viewModel.showLoading.observe(this, Observer {
            loadingView.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.error.observe(this, Observer {
            errorView.visibility = if (it != null) View.VISIBLE else View.GONE
            errorView.text = it
        })
        viewModel.showContent.observe(this, Observer {
            cardTextView.visibility = if (it) View.VISIBLE else View.GONE
        })


        viewModel.fetchCard()
    }
}
