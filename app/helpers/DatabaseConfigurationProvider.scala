package helpers

import javax.inject.Provider

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.postgresql.util.URLParser


class DatabaseConfigurationProvider extends Provider[Configuration] {
  private val databaseConfiguration : Configuration = System.getenv("DATABASE_URL") match {
    case url : String => URLParser.parse(url)
    case _ => new Configuration(
      username = "postgres" ,
      database = Some("postgresql_async_app_development")
    )
  }

  lazy val get: Configuration = databaseConfiguration
}
