package com.dzakyadlh.storytell

import com.dzakyadlh.storytell.data.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "created at: + $i",
                "name: $i",
                "description: $i",
                id = "id: $i",
            )
            items.add(story)
        }
        return items
    }
}