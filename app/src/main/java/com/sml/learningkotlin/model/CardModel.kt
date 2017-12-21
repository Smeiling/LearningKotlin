package com.sml.learningkotlin.model

import com.avos.avoscloud.AVObject
import com.sml.learningkotlin.utils.CardType
import com.sml.learningkotlin.utils.Utils

/**
 * Created by Smeiling on 2017/11/9.
 */
class CardModel : AVObject {
    var title: String? = null
    var content: String? = null
    var date: String? = null
    var timestamp: Long = 0
    var cardType: CardType = CardType.THEME_BLUE


    constructor(title: String, content: String, date: String, cardType: String) {
        this.title = title
        this.content = content
        this.date = date
        this.timestamp = Utils.getTimestampFromDate(date, "yyyyMMdd")
        this.cardType = CardType.valueOf(cardType)
    }

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