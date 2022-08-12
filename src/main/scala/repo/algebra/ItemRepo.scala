package repo.algebra

import models.Item

import scala.concurrent.Future

trait ItemRepo {

  def getById(id: Long): Future[Option[Item]]
}
