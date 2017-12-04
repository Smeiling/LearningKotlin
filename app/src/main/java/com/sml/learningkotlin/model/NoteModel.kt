package com.sml.learningkotlin.model

import com.avos.avoscloud.AVObject

/**
 * Created by Smeiling on 2017/11/9.
 */
class NoteModel : AVObject {
    var title: String? = null
    var content: String? = null
    var date: String? = null

    constructor(title: String, content: String, date: String) {
        this.title = title
        this.content = content
        this.date = date
    }

    constructor(content: String, date: String) {
        this.content = content
        this.date = date
    }

    constructor()

}