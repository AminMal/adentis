package repo.impl

import models.Item
import repo.algebra.ItemRepo
import repo.table.ItemTable

import scala.concurrent.Future

class ItemRepoImpl extends ItemRepo {

  import repo.DatabaseProfile.profile.api._

  private final val Items = ItemTable.Items
  private final val db = bootstrap.db

  override def getById(id: Long): Future[Option[Item]] = db.run {
    Items.filter(_.id === id)
      .result
      .headOption
  }

  override def insert(item: Item): Future[Item] = db.run {
    (Items returning (Items map (_.id))).into((item, id) => item.copy(id = id)) += item
  }

  override def update(item: Item): Future[Int] = db.run {
    Items.filter(_.id === item.id).update(item)
  }

}
