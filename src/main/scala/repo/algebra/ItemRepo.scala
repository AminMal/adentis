package repo.algebra

import models.Item

import scala.concurrent.Future

/**
 * This trait is the algebra/interface to the item repository, although it's not used
 * in the application right now, I thought it might be useful to include it in the infrastructure of the application database
 */
trait ItemRepo {

  def getById(id: Long): Future[Option[Item]]

  def insert(item: Item): Future[Item]

  def update(item: Item): Future[Int]

}
