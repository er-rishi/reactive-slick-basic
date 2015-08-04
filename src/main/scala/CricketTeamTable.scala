/**
 *
 * @author Rishi
 */
import slick.lifted.{ ProvenShape, ForeignKeyQuery }
import slick.driver.PostgresDriver.api._

case class CricketTeam(id: Int, name: String)

class CricketTeamMapping(tag: Tag) extends Table[CricketTeam](tag, "teams") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def * = (id, name) <> (CricketTeam.tupled, CricketTeam.unapply)

}

