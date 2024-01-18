package com.catvasiliy.mydic.data.remote

import com.catvasiliy.mydic.data.remote.model.ApiDefinition
import com.catvasiliy.mydic.data.remote.model.ApiExample
import com.catvasiliy.mydic.data.remote.model.ApiAlternativeTranslation
import com.catvasiliy.mydic.data.remote.model.ApiTranslation
import com.catvasiliy.mydic.domain.model.translation.AlternativeTranslation
import com.catvasiliy.mydic.domain.model.translation.Definition
import com.catvasiliy.mydic.domain.model.translation.Example
import com.catvasiliy.mydic.domain.model.translation.ExtendedTranslation
import java.util.*

fun ApiTranslation.toExtendedTranslation(): ExtendedTranslation {
    return ExtendedTranslation(
        translationText = primaryTranslation[0].translationText,
        sourceText = primaryTranslation[0].sourceText,
        sourceTransliteration = primaryTranslation[1].sourceTransliteration,
        alternativeTranslations = alternativeTranslations.flatMap { alternativeTranslation ->
            alternativeTranslation.toAlternativeTranslationsList()
        },
        definitions = definitions.flatMap { definitions ->
            definitions.toDefinitionsList()
        },
        examples = examples.toExamplesList(),
        translatedAtMillis = Date().time
    )
}

private fun ApiAlternativeTranslation.toAlternativeTranslationsList(): List<AlternativeTranslation> {
    return entries.map { alternativeTranslation ->
        AlternativeTranslation(
            translationText = alternativeTranslation.translationText,
            partOfSpeech = partOfSpeech,
            synonyms = alternativeTranslation.synonyms
        )
    }
}

private fun ApiDefinition.toDefinitionsList(): List<Definition> {
    return entries.map { definition ->
        Definition(
            definitionText = definition.definitionText,
            partOfSpeech = partOfSpeech,
            exampleText = definition.exampleText
        )
    }
}

private fun ApiExample.toExamplesList(): List<Example> {
    return entries.map { example ->
        Example(
            exampleText = example.exampleText
        )
    }
}