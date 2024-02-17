package com.catvasiliy.mydic.domain.use_case.settings

import com.catvasiliy.mydic.data.local.preferences.PreferencesRepository
import com.catvasiliy.mydic.domain.model.settings.TranslationSendingInterval
import javax.inject.Inject

class UpdateTranslationSendingIntervalUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(interval: TranslationSendingInterval) {
        preferencesRepository.setTranslationSendingInterval(interval)
    }
}