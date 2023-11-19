package com.dev.quixabus.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.quixabus.R
import com.dev.quixabus.dao.FeedDao
import com.dev.quixabus.dao.PostDao
import com.dev.quixabus.databinding.ActivityFeedBinding
import com.dev.quixabus.model.FeedItem
import com.dev.quixabus.ui.recyclerview.adapter.FeedAdapter
import com.dev.quixabus.ui.recyclerview.adapter.SwipeGesture
import com.dev.quixabus.util.TopBar

class FeedActivity : AppCompatActivity(R.layout.activity_feed), FeedAdapter.ClickFeedItem {

    private val binding by lazy {
        ActivityFeedBinding.inflate(layoutInflater)
    }

    private val dao = FeedDao()
    private val postDao = PostDao()
    private var adapter: FeedAdapter = FeedAdapter(this, feedItems = dao.buscaFeed(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraRecyclerView()
        configuraSwipe()
        TopBar().configura(supportFragmentManager, R.id.activity_feed_fragment_top_bar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualizar(dao.buscaFeed())
        configuraFab()
    }

    override fun clickFeedItem(feedItem: FeedItem) {
        vaiParaEditarPost(feedItem.post.id)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityFeedRv
        recyclerView.adapter = adapter
    }

    private fun configuraSwipe() {
        val recyclerView = binding.activityFeedRv

        val swipe: SwipeGesture = object : SwipeGesture(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        val position = viewHolder.adapterPosition
                        val id = adapter.buscaItem(position).post.id
                        adapter.deletarItem(position)
                        postDao.deletar(id)
                    }
                    ItemTouchHelper.RIGHT -> {
                        val position = viewHolder.adapterPosition
                        val id = adapter.buscaItem(position).post.id
                        adapter.deletarItem(position)
                        postDao.deletar(id)
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun configuraFab() {
        val fab = binding.activityFeedFab
        fab.setOnClickListener {
            vaiParaCadastrarPost()
        }
    }

    private fun vaiParaCadastrarPost() {
        val intent = Intent(this, CadastrarPostActivity::class.java)
        startActivity(intent)
    }

    private fun vaiParaEditarPost(id: Int) {
        val intent = Intent(this, EditarPostActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}