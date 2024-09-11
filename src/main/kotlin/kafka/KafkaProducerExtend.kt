package kafka

import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <K : Any, V : Any> Producer<K, V>.suspendSend(record: ProducerRecord<K, V>): RecordMetadata {
    return suspendCoroutine { continuation ->
        send(record) { metadata, exception ->
            if (exception != null) {
                continuation.resumeWithException(exception)
            } else {
                continuation.resume(metadata)
            }
        }
    }
}