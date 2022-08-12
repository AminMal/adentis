package repo.impl

import models.Item
import repo.algebra.ItemRepo
import repo.table.ItemTable

import scala.concurrent.Future

@Singleton
class ItemRepoImpl extends ItemRepo {

  import repo.DatabaseProfile.profile.api._

  private final val Items = ItemTable.Items
  private final val db = bootstrap.Bootstrap.db

  override def getById(id: Long): Future[Option[Item]] = db.run {
    Items.filter(_.id === id)
      .result
      .headOption
  }

}
