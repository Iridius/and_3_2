package ru.netology.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.R
import ru.netology.adapter.PostsAdapter
import ru.netology.databinding.ActivityMainBinding
import ru.netology.databinding.CardPostBinding
import ru.netology.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter {
            viewModel.likeById(it.id)
        }

        binding.list.adapter = adapter
        viewModel.data.observe(this, { posts ->
            adapter.list = posts
        })

        /*
        viewModel.data.observe(this, { posts ->
            posts.map { post ->
                CardPostBinding.inflate(layoutInflater).apply {
                    txtTitle.text = post.title
                    txtSubtitle.text = post.subTitle

                    imgLikes.setImageResource(
                        if (post.hasAutoLike) R.drawable.ic_baseline_favorite_24
                        else R.drawable.ic_baseline_favorite_border_24
                    )

                    txtLikes.text = formatNumber(post.likes)
                    txtShares.text = formatNumber(post.shares)
                    txtViews.text = formatNumber(post.views)

                    imgLikes.setOnClickListener {
                        viewModel.likeById(post.id)
                    }

                    imgShares.setOnClickListener {
                        viewModel.shareById(post.id)
                    }

                    imgViews.setOnClickListener {
                        viewModel.viewById(post.id)
                    }
                }.root
            }.forEach {
                binding.root.addView(it)
            }
        })*/
    }
}

fun formatNumber(number: Int): String {
    return when {
        number >= 1_000_000 -> roundNumber(number / 1_000_000.0) + "M"
        number >= 1_000 -> roundNumber(number / 1_000.0) + "K"
        else -> number.toString()
    }
}

fun roundNumber(number: Double): String {
    val value = number.toString().take(3)

    return when {
        value.endsWith(".") -> value.take(2)
        value.endsWith(".0") -> value.take(1)
        else -> value
    }
}