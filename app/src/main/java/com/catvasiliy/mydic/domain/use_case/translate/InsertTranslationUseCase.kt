package com.catvasiliy.mydic.domain.use_case.translate

import com.catvasiliy.mydic.domain.model.translation.Translation
import com.catvasiliy.mydic.domain.repository.TranslateRepository
import javax.inject.Inject

class InsertTranslationUseCase @Inject constructor(
    private val translateRepository: TranslateRepository
) {

    suspend operator fun invoke(translation: Translation) {
        if (translation.isMissingTranslation) {
            translateRepository.insertMissingTranslation(translation)
        } else {
            translateRepository.insertExtendedTranslation(translation)
        }
    }
}