import com.example.models.ReceiveModel
import com.example.utils.JsonUtils
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val client = HttpClient(Apache) {
    engine {
        // Разрешаем следовать редиректам
        followRedirects = true
    }
}

fun main() {
    val server = embeddedServer(Netty, 8080) {
        routing {
            post("/") {
                val jsonObject = JsonUtils.toJson(ReceiveModel("Евгений"), ReceiveModel.serializer())
                val response1 = clientPost(8081, jsonObject)
                val response2 = clientPost(8082, response1)
                call.respondText(response2, ContentType.Application.Json)
            }
        }
    }
    server.start(wait = true)
}

suspend fun clientPost(port: Int, body: String): String {
    return client.post("http://localhost:$port/") {
        // Добавил заголов ContentType для решения проблемы медия
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        // Устанавливаем тело запроса в формате JSON
        setBody(body)
    }.bodyAsText()
}


