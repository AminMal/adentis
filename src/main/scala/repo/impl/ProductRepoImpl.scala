package repo.impl

import repo.algebra.ProductRepo
import repo.table.ProductTable

class ProductRepoImpl extends ProductRepo {

  import repo.DatabaseProfile.profile.api._
  private final val db = bootstrap.db
  private final val Products = ProductTable.Products

}
