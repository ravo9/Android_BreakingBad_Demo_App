package breakingbbaddemoapp.models

import com.google.gson.annotations.SerializedName

class CompleteCharacterObject(
    id: Int,
    name: String,
    nickname: String?,
    imageUrl: String?,
    breakingBadSeasonAppearance: List<Int>,
    betterCallSaulSeasonAppearance: List<Int>,

    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("occupation")
    val occupations: List<String>,

    @SerializedName("status")
    val status: String,

    @SerializedName("portrayed")
    val portrayed: String,

    @SerializedName("category")
    val category: String

) : SimplifiedCharacterObject(id, name, nickname, imageUrl, breakingBadSeasonAppearance, betterCallSaulSeasonAppearance)