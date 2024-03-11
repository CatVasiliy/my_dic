package com.catvasiliy.mydic.domain.model.preferences.translation_sorting

import kotlinx.serialization.Serializable

@Serializable
sealed interface SortingOrder {
    @Serializable object Ascending : SortingOrder
    @Serializable object Descending : SortingOrder
}
