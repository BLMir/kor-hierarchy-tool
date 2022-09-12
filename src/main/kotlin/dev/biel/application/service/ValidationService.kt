package dev.biel.application.service

import dev.biel.application.common.getRoot
import dev.biel.presentation.exception.BadJsonRequestException

class ValidationService {

    private var validationErrorList = mutableListOf<ErrorValidations>()

    fun start(hierarchy: Map<String, String>): Unit {
        validateMultipleRoots(hierarchy)
        validateInfiniteLoops(hierarchy)

        if (validationErrorList.size > 0) {
            throw BadJsonRequestException(message = validationErrorList.toPrettyString())
        }
    }

    private fun List<ErrorValidations>.toPrettyString(): String {
        return "Validation Errors \n" +
                this.map {
                    " Validation Type : ${it.message} \n" +
                            " Elements affected: ${it.elements} \n"
                }.toString()
    }

    private fun validateMultipleRoots(hierarchyList: Map<String, String>) {
        if (hierarchyList.getRoot().size > 1) {
            validationErrorList.add(ErrorValidations(
                message = "There are more than one root (CEO)",
                elements = hierarchyList.getRoot().map { it }
            ))
        }
    }

    private fun validateInfiniteLoops(hierarchyList: Map<String, String>) {
        if (hierarchyList.getRoot().isEmpty()) {
            validationErrorList.add(
                ErrorValidations(
                    message = "There is an infinite loop ",
                    elements = null
                )
            )
        }
    }
}

data class ErrorValidations(val message: String, val elements: List<String>?)
