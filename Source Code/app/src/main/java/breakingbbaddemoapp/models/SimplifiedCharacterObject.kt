package breakingbbaddemoapp.models

import com.google.gson.annotations.SerializedName

open class SimplifiedCharacterObject(
    @SerializedName("char_id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("nickname")
    val nickname: String?,

    @SerializedName("img")
    val imageUrl: String?,

    @SerializedName("appearance")
    val breakingBadSeasonAppearance: List<Int>,

    @SerializedName("better_call_saul_appearance")
    val betterCallSaulSeasonAppearance: List<Int>
)