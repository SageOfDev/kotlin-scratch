package kafka

import mu.KotlinLogging
import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class PublishKafkaAdapter (
    private val producer: Producer<String, String> = KafkaProducer(Properties().apply { //TODO: inject Properties
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    })
) {
    private val topic = "my-topic"  // TODO: inject topic name
    private val logger = KotlinLogging.logger {}

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            close()
        })
    }

    suspend fun publishMyMessage(myMessage: MyMessage) {
        val recordMetadata = sendMyMessageAsync(myMessage)
        logger.info { "myMessage:${myMessage.key} successfully published at ${recordMetadata.topic()}:${recordMetadata.partition()}" }
    }

    private suspend fun sendMyMessageAsync(myMessage: MyMessage): RecordMetadata {
        val record = ProducerRecord(topic, myMessage.key, myMessage.body)
        return producer.suspendSend(record)
    }

    private fun close() {
        producer.flush()
        producer.close()
        logger.debug { "KafkaMyMessagePublishAdapter closed successfully" }
    }

}

class MyMessage (
    val key: String,
    val body: String
)
