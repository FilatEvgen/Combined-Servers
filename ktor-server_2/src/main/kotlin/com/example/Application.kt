import com.example.models.ReceiveModel
import com.example.models.RemoteModel
import com.example.utils.JsonUtils
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    val server2 = embeddedServer(Netty, 8081) {
        routing {
            install(ContentNegotiation) {
                json()
            }
            post("/") {
                val requestBody = call.receive<ReceiveModel>()
                val jsonObject =
                    JsonUtils.toJson(RemoteModel(requestBody.firstName, lastName = "Филатов"), RemoteModel.serializer())
                call.respondText(jsonObject, ContentType.Application.Json)
            }
        }
    }
    server2.start(wait = true)
}