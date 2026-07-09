package utilities



open class OperationResult {
    data class Success(val value: Double) : OperationResult()
    data class Failure(val message: String) : OperationResult()
}


inline fun OperationResult.onSuccess(block: (Double) -> Unit): OperationResult {
    if (this is OperationResult.Success) block(value)
    return this
}

inline fun OperationResult.onFailure(block: (String) -> Unit): OperationResult {
    if (this is OperationResult.Failure) block(message)
    return this
}
