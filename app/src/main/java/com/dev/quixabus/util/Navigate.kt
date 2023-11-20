package com.dev.quixabus.util

import android.content.Context
import android.content.Intent
import com.dev.quixabus.ui.activity.AgendaActivity
import com.dev.quixabus.ui.activity.AmigosActivity
import com.dev.quixabus.ui.activity.FeedActivity
import com.dev.quixabus.ui.activity.ItinerarioActivity
import com.dev.quixabus.ui.activity.LinksActivity

class Navigate {

    fun toAgenda(context: Context) {
        val intent = Intent(context, AgendaActivity::class.java)
        context.startActivity(intent)
    }

    fun toItinerario(context: Context) {
        val intent = Intent(context, ItinerarioActivity::class.java)
        context.startActivity(intent)
    }

    fun toLinksUteis(context: Context) {
        val intent = Intent(context, LinksActivity::class.java)
        context.startActivity(intent)
    }

    fun toFeed(context: Context) {
        val intent = Intent(context, FeedActivity::class.java)
        context.startActivity(intent)
    }

    fun toAmigos(context: Context) {
        val intent = Intent(context, AmigosActivity::class.java)
        context.startActivity(intent)
    }
}