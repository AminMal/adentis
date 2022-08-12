package bootstrap

import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.JdbcBackend.Database

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object Bootstrap {

  implicit val ec: ExecutionContext =
    ExecutionContext.fromExecutor(
      Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())
    )

  val config: Config = ConfigFactory.load(getClass.getClassLoader)
  val db = Database.forConfig("db")
}
