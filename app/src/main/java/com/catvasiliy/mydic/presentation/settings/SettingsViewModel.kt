package com.catvasiliy.mydic.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catvasiliy.mydic.domain.model.preferences.TranslationSendingInterval
import com.catvasiliy.mydic.domain.model.preferences.TranslationSendingPreferences
import com.catvasiliy.mydic.domain.use_case.settings.GetTranslationSendingPreferencesUseCase
import com.catvasiliy.mydic.domain.use_case.settings.ToggleTranslationSendingUseCase
import com.catvasiliy.mydic.domain.use_case.settings.UpdateTranslationSendingIntervalUseCase
import com.catvasiliy.mydic.presentation.settings.translation_sending.TranslationSendingAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getTranslationSendingPreferencesUseCase: GetTranslationSendingPreferencesUseCase,
    private val toggleTranslationSendingUseCase: ToggleTranslationSendingUseCase,
    private val updateTranslationSendingIntervalUseCase: UpdateTranslationSendingIntervalUseCase,
    private val alarmScheduler: TranslationSendingAlarmScheduler
) : ViewModel() {

    val state = getTranslationSendingPreferencesUseCase()
        .map { translationSendingPreferences ->
            SettingsState(
                translationSendingPreferences = translationSendingPreferences
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsState()
        )

    fun toggleTranslationSending(
        isSendingEnabled: Boolean,
        sendingInterval: TranslationSendingInterval
    ) {
        viewModelScope.launch {

            if (isSendingEnabled) {
                alarmScheduler.schedule(sendingInterval)
            } else {
                alarmScheduler.cancel()
            }

            toggleTranslationSendingUseCase(
                TranslationSendingPreferences(
                    isSendingEnabled = isSendingEnabled,
                    sendingInterval = sendingInterval
                )
            )
        }
    }

    fun updateTranslationSendingInterval(interval: TranslationSendingInterval) {
        viewModelScope.launch {
            updateTranslationSendingIntervalUseCase(interval)
        }
    }
}