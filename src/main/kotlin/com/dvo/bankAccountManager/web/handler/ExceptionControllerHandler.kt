package com.dvo.bankAccountManager.web.handler

import com.dvo.bankAccountManager.exception.EntityExistsException
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.web.model.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerHandler {
    private val log = LoggerFactory.getLogger(ExceptionControllerHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun notValid(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessages = ex.bindingResult
            .allErrors
            .mapNotNull { it.defaultMessage }

        val message = errorMessages.joinToString("; ")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message))
    }


    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFound(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        log.warn("Сущность не найдена: {}", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(ex.message ?: "Сущность не найдена"))
    }

    @ExceptionHandler(EntityExistsException::class)
    fun handleFound(ex: EntityExistsException): ResponseEntity<ErrorResponse> {
        log.warn("Сущность уже существует: {}", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(ex.message ?: "Сущность уже существует"))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingRequestParam(ex: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        val message = "Параметр запроса '${ex.parameterName}' обязателен"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(message))
    }

    @ExceptionHandler(Exception::class)
    fun handleOtherExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        log.error("Необработанная ошибка", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("Внутренняя ошибка сервера"))
    }
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        log.warn("Некорректный аргумент: {}", ex.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ex.message ?: "Некорректный аргумент"))
    }
}