package helpers

import javax.inject.{Inject, Provider}

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import com.github.mauricio.async.db.postgresql.util.URLParser


class PostgreSQLConnectionFactoryProvider @Inject() (conf: Configuration) extends Provider[PostgreSQLConnectionFactory] {

  lazy val get: PostgreSQLConnectionFactory = new PostgreSQLConnectionFactory( conf )

}
