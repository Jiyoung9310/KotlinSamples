package jiyoung.example.kotlin.com.kotlinsamples.filter

data class Question(var authorName: String?, var authorJobTitle: String?, var authorAvatar: String?, var date: String?, var text: String?, var tags: List<Tag>?) {

    fun hasTag(string: String): Boolean {
        for (tag in tags!!) {
            if (tag.getText() == string) {
                return true
            }
        }

        return false
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Question) return false

        val question = o as Question?

        if (if (authorName != null) authorName != question!!.authorName else question!!.authorName != null)
            return false
        if (if (authorJobTitle != null) authorJobTitle != question.authorJobTitle else question.authorJobTitle != null)
            return false
        if (if (authorAvatar != null) authorAvatar != question.authorAvatar else question.authorAvatar != null)
            return false
        if (if (date != null) date != question.date else question.date != null)
            return false
        if (if (text != null) text != question.text else question.text != null)
            return false
        return if (tags != null) tags == question.tags else question.tags == null

    }

    override fun hashCode(): Int {
        var result = if (authorName != null) authorName!!.hashCode() else 0
        result = 31 * result + if (authorJobTitle != null) authorJobTitle!!.hashCode() else 0
        result = 31 * result + if (authorAvatar != null) authorAvatar!!.hashCode() else 0
        result = 31 * result + if (date != null) date!!.hashCode() else 0
        result = 31 * result + if (text != null) text!!.hashCode() else 0
        result = 31 * result + if (tags != null) tags!!.hashCode() else 0
        return result
    }
}