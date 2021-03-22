package breakingbbaddemoapp.utils

import breakingbbaddemoapp.models.SimplifiedCharacterObject

class FilteringTools {

    fun filterResults(list: List<SimplifiedCharacterObject>,
                      filterNamePhrase: String?,
                      filterSeason: Int?
    ): List<SimplifiedCharacterObject> {
        return filterResultsByName(list, filterNamePhrase)
//        filterResultsBySeason(list, filterSeason)
    }

    private fun filterResultsByName(list: List<SimplifiedCharacterObject>,
                                    filterNamePhrase: String?
    ): List<SimplifiedCharacterObject> {
        if (!filterNamePhrase.isNullOrEmpty()) {
            return list.filter { (it.name.toLowerCase()).contains(filterNamePhrase.toLowerCase()) }
        } else {
            return list
        }
    }

//    private fun filterResultsBySeason(list: List<SimplifiedCharacterObject>,
//                                      filterSeason: Int?
//    ): List<SimplifiedCharacterObject> {
//        if (filterSeason != null) {
//            return list.filter { it.app.contains(filterNamePhrase) }
//        } else {
//            return list
//        }
//    }
}