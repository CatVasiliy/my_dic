package com.catvasiliy.mydic.presentation.util

import com.catvasiliy.mydic.presentation.model.preferences.UiSourceLanguageFilteringInfo
import com.catvasiliy.mydic.presentation.model.translation.UiExtendedLanguage
import com.catvasiliy.mydic.presentation.model.translation.UiLanguage
import com.catvasiliy.mydic.presentation.model.translation.UiTranslationListItem

fun List<UiTranslationListItem>.filterBySourceTextContains(
    query: String
): List<UiTranslationListItem> {
    return if (query.isNotBlank()) {
        filter { it.sourceText.contains(query) }
    } else {
        this
    }
}

fun List<UiTranslationListItem>.filterBySourceLanguage(
    filteringInfo: UiSourceLanguageFilteringInfo
): List<UiTranslationListItem> {
    return filteringInfo.run {
        when (filteringInfo) {
            is UiSourceLanguageFilteringInfo.LanguageAny ->
                this@filterBySourceLanguage
            is UiSourceLanguageFilteringInfo.LanguageUnknown ->
                this@filterBySourceLanguage.filterByUnknownSourceLanguage()

            is UiSourceLanguageFilteringInfo.LanguageKnown ->
                this@filterBySourceLanguage.filterByKnownSourceLanguage(filteringInfo.language)
        }
    }
}

private fun List<UiTranslationListItem>.filterByKnownSourceLanguage(
    sourceLanguage: UiLanguage
): List<UiTranslationListItem> {
    return filter { translation ->
        if (translation.sourceLanguage is UiExtendedLanguage.Known) {
            translation.sourceLanguage.language == sourceLanguage
        } else {
            false
        }
    }
}

private fun List<UiTranslationListItem>.filterByUnknownSourceLanguage(): List<UiTranslationListItem> {
    return filter { translation ->
        translation.sourceLanguage !is UiExtendedLanguage.Known
    }
}