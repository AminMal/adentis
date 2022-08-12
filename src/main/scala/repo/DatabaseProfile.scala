package repo

import slick.jdbc.JdbcProfile

trait DatabaseProfile {
  val profile: JdbcProfile
}

object DatabaseProfile extends DatabaseProfile {
  override val profile: JdbcProfile = slick.jdbc.PostgresProfile
}
