package com.sml.learningkotlin.model

import com.avos.avoscloud.AVObject
import com.sml.learningkotlin.utils.Utils

/**
 * Created by Smeiling on 2017/11/9.
 */
class NoteModel : AVObject {
    var title: String? = null
    var content: String? = null
    var date: String? = null
    var timestamp: Long = 0

    constructor(title: String, content: String, date: String) {
        this.title = title
        this.content = content
        this.date = date
        this.timestamp = Utils.getTimestampFromDate(date, "yyyyMMdd")
    }

    constructor(content: String, date: String) {
        this.content = content
        this.date = date
        this.timestamp = Utils.getTimestampFromDate(date, "yyyyMMdd")
    }

    constructor()

}