package breakingbbaddemoapp.utils

class StringFormatter {

    fun formatListString(base: String, fallbackText: String, list: List<Any>): String {
        val listStringPart = if (list.isEmpty()) {
            fallbackText
        } else {
            val listAsString = list.toString()
            val listAsStringLength = listAsString.length
            listAsString.substring(1, listAsStringLength - 1)
        }
        return formatBaseAndValueString(base, listStringPart)
    }

    fun formatBaseAndValueString(base: String, value: String): String {
        return (base + ": " + value)
    }

}