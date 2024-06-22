import com.example.models.ReceiveModel
import com.example.models.RemoteModel
import com.example.utils.JsonUtils
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*

fun main() {
    val server3 = embeddedServer(Netty, 8082) {
        routing {
            install(ContentNegotiation) {
                json()
            }
            post("/") {
                val requestBody = call.receive<ReceiveModel>()
                val jsonObject = JsonUtils.toJson(
                    RemoteModel(requestBody.firstName, requestBody.lastName, birth = "16.06.1997"),
                    RemoteModel.serializer()
                )
                call.respondText(jsonObject, ContentType.Application.Json)
            }
        }
    }
    server3.start(wait = true)
}